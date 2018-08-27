package me.cekpedia.models;

import android.widget.EditText;

/**
 * Created by rezadwihendarno on 06/04/2018.
 */

public class User {
    private String User;
    private String email;
    private String photoUrl;
    private String Uid;
    private String favourite;
    private String nohp;
    public User(String user, String email, String photoUrl, String uid, String favourite, String nohp) {
        this.User = user;
        this.email = email;
        this.photoUrl = photoUrl;
        this.Uid = uid;
        this.favourite = favourite;
        this.nohp = nohp;
    }
    public User(String user, String email, String photoUrl, String uid) {
        this.User = user;
        this.email = email;
        this.photoUrl = photoUrl;
        this.Uid = uid;
    }

    public User(String user, String email, String nohp) {

    }

    public User(EditText nama, EditText email, String photoUrl, String uid, String favorit, EditText nohp) {
        this.photoUrl = photoUrl;
        this.Uid = uid;
        this.favourite = favorit;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    //
//    public User(String email, boolean favourite, String uid) {
//        this.email = email;
//        this.favourite = favourite;
//
//        Uid = uid;
//    }
    public User(EditText nama, EditText email, EditText nohp){

    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
