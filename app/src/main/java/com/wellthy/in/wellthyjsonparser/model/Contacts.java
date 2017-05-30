package com.wellthy.in.wellthyjsonparser.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Gulshan.Singh on 29-05-2017.
 */

public class Contacts extends RealmObject {

    public String getId() {
        return id;
    }

    @PrimaryKey
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Required
    private String name;
    @Required
    private String mobileNumber;

    public void setId(String id) {
        this.id = id;
    }
}
