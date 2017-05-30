package com.wellthy.in.wellthyjsonparser.database;

import com.wellthy.in.wellthyjsonparser.model.Contacts;
import com.wellthy.in.wellthyjsonparser.model.OnDeleteContactCallback;
import com.wellthy.in.wellthyjsonparser.model.OnGetAllContactsCallback;
import com.wellthy.in.wellthyjsonparser.model.OnGetContactCallback;
import com.wellthy.in.wellthyjsonparser.ui.WrllthyApplication;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;

/**
 * Created by Gulshan.Singh on 30-05-2017.
 */

public class ContactDatabaseHandler  {

        public void addContacts(ArrayList<Contacts> contacts) {
            final ArrayList<Contacts> contacts1=contacts;
            Realm realm = Realm.getInstance(WrllthyApplication.getInstance());
            final Contacts u = realm.createObject(Contacts.class);
            try {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for(int i = 0 ; i < 1000000; i++){
                            for (Contacts contact:contacts1
                                 ) {
                                u.setId(UUID.randomUUID().toString());
                                u.setName(contact.getName());
                                // realm.insert(u);
                            }

                        }
                    }
                });
            }
            catch (Exception e){

            }
        }

        public void deleteContactsById(String Id,OnDeleteContactCallback callback) {
        }

        public void deleteContactsByName(String name,OnDeleteContactCallback callback) {
        }

        public void deleteContactsByMobileNumber(String name,OnDeleteContactCallback callback) {
        }

        public void getContactsByName(String name,OnGetContactCallback callback) {
        }

        public void getContactsByMobileNumber(String name,OnGetContactCallback callback) {
        }

        public void getContactsById(String id,OnGetContactCallback callback) {
//            Realm realm = Realm.getInstance(WrllthyApplication.getInstance());
//            Contacts result = realm.where(Contacts.class).equalTo(RealmTable.ID, id).findFirst();
//            if (callback != null)
//                callback.onSuccess(results);

        }

        public void getAllContacts(OnGetAllContactsCallback callback) {
//            Realm realm = Realm.getInstance(WrllthyApplication.getInstance());
//            RealmQuery query = realm.where(Contacts.class);
//            RealmResults results = query.findAll();
//            if (callback != null)
//                callback.onSuccess(results);
        }

}
