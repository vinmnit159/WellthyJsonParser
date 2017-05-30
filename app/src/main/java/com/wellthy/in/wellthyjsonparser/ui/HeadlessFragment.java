package com.wellthy.in.wellthyjsonparser.ui;

/**
 * Created by Gulshan.Singh on 30-05-2017.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.wellthy.in.wellthyjsonparser.R;
import com.wellthy.in.wellthyjsonparser.communication.DownloadManager;
import com.wellthy.in.wellthyjsonparser.model.Contacts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class HeadlessFragment extends Fragment {

    public static final String TAG_HEADLESS_FRAGMENT = "headless_fragment";

    public static interface TaskStatusCallback {
        void onPreExecute();

        void onProgressUpdate(int progress);

        void onPostExecute();

        void onCancelled();
    }

    TaskStatusCallback mStatusCallback;
    GetContacts mBackgroundTask;
    boolean isTaskExecuting = false;

    /**
     * Called when a fragment is first attached to its activity.
     * onCreate(Bundle) will be called after this.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mStatusCallback = (TaskStatusCallback) activity;
    }

    /**
     * Called to do initial creation of a fragment.
     * This is called after onAttach(Activity) and before onCreateView(LayoutInflater, ViewGroup, Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    /**
     * Called when the fragment is no longer attached to its activity. This is called after onDestroy().
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mStatusCallback = null;
    }

//    private class BackgroundTask extends AsyncTask<Void, Integer, Void> {
//
//        @Override
//        protected void onPreExecute() {
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            int progress = 0;
//            while (progress < 100 && !isCancelled()) {
//                progress++;
//                publishProgress(progress);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            if (mStatusCallback != null)
//                mStatusCallback.onPostExecute();
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            if (mStatusCallback != null)
//                mStatusCallback.onProgressUpdate(values[0]);
//        }
//
//        @Override
//        protected void onCancelled(Void result) {
//            if (mStatusCallback != null)
//                mStatusCallback.onCancelled();
//        }
//
//    }
    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mStatusCallback != null)
                mStatusCallback.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            DownloadManager sh = new DownloadManager();
            String jsonStr = sh.downloadJson(MainActivity.url);


            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject c = jsonArray.getJSONObject(i);

                        String name = c.getString("name");
                        String mobile = c.getString("mobileNumber");

                        HashMap<String, String> contact = new HashMap<>();
                        contact.put("name", name);
                        contact.put("mobileNumber", mobile);
                        MainActivity.contactList.add(contact);
                    }
                } catch (final JSONException e) {


                }
            } else {


            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (mStatusCallback != null)
                mStatusCallback.onPostExecute();
        }

    }
    public void startBackgroundTask() {
        if (!isTaskExecuting) {
            mBackgroundTask = new GetContacts();
            mBackgroundTask.execute();
            isTaskExecuting = true;
        }
    }

    public void cancelBackgroundTask() {
        if (isTaskExecuting) {
            mBackgroundTask.cancel(true);
            isTaskExecuting = false;
        }
    }

    public void updateExecutingStatus(boolean isExecuting) {
        this.isTaskExecuting = isExecuting;
    }
}