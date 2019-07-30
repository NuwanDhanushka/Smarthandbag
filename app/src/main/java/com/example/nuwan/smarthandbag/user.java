package com.example.nuwan.smarthandbag;

public class user {
    public String firstName,lastName,email,mobileNumber,deviceid;

    public user() {

    }

    public user(String firstName, String lastName, String email, String mobileNumber,String deviceId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.deviceid=deviceId;
    }
}
