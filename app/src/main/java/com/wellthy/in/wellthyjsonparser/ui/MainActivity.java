package com.wellthy.in.wellthyjsonparser.ui;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.wellthy.in.wellthyjsonparser.R;
import com.wellthy.in.wellthyjsonparser.communication.DownloadManager;
import com.wellthy.in.wellthyjsonparser.database.ContactDatabaseHandler;
import com.wellthy.in.wellthyjsonparser.model.Contacts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements HeadlessFragment.TaskStatusCallback {

    private String TAG = "JSON_DEBUG"+MainActivity.class.getSimpleName();
    public static ProgressDialog progressDialog;
    private ListView listView;
    public static String url = "http://bwellthy.getsandbox.com/users";
    public static ArrayList<HashMap<String, String>> contactList;
    Context activity ;
    private HeadlessFragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list);
//        new GetContacts().execute();


        FragmentManager mMgr = getFragmentManager();
        mFragment = (HeadlessFragment) mMgr
                .findFragmentByTag(HeadlessFragment.TAG_HEADLESS_FRAGMENT);

        if (mFragment == null) {
            mFragment = new HeadlessFragment();
            mMgr.beginTransaction()
                    .add(mFragment, HeadlessFragment.TAG_HEADLESS_FRAGMENT)
                    .commit();
        }
        mFragment.startBackgroundTask();
        activity=this;
    }

    /**
     * This method is called before an activity may be killed Store info in
     * bundle if required.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    // Background task Callbacks

    @Override
    public void onPreExecute() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onPostExecute() {
        // Dismiss the progress dialog
        if(progressDialog!=null) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
        ArrayList<Contacts> contactArray= new ArrayList<Contacts>();
        SharedPreferences sharedPreferences=getSharedPreferences("Contacts", Context.MODE_PRIVATE);
        Set<String> contactName= new HashSet<String>();
        Set<String> contactMobile= new HashSet<String>();
        for (HashMap<String,String> s:contactList
                ) {
            contactName.add(s.get("name"));
            contactMobile.add(s.get("mobileNumber"));
            Contacts contacts = new Contacts();
            contacts.setName(s.get("name"));
            contacts.setMobileNumber(s.get("mobileNumber"));
            contactArray.add(contacts);
        }
        SharedPreferences.Editor e=sharedPreferences.edit();
        e.putStringSet("name",contactName);
        e.putStringSet("mobileNumber",contactMobile);
        e.commit();
//            ContactDatabaseHandler db= new ContactDatabaseHandler();
//            db.addContacts(contactArray);

        ListAdapter adapter = new SimpleAdapter(
                MainActivity.this, contactList,
                R.layout.list_item, new String[]{"name",
                "mobileNumber"}, new int[]{R.id.name,
                R.id.mobileNumber});

        listView.setAdapter(adapter);
             if (mFragment != null)
            mFragment.updateExecutingStatus(false);
    }

    @Override
    public void onCancelled() {


    }

    @Override
    public void onProgressUpdate(int progress) {

    }
}

