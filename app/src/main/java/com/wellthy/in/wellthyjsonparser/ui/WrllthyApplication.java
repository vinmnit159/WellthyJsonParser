package com.wellthy.in.wellthyjsonparser.ui;

import android.app.Application;

import com.wellthy.in.wellthyjsonparser.model.ModelRealm;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Gulshan.Singh on 30-05-2017.
 */

public class WrllthyApplication extends Application {
    private static WrllthyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext()).setModules(new ModelRealm()).build();
        Realm.setDefaultConfiguration(config);
    }
    public static WrllthyApplication getInstance() {
        return instance;
    }
}
