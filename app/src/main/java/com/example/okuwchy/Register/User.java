package com.example.okuwchy.Register;

public class User {

    public String email,username,password,location,studentID,school;

    public User() {
    }

    public User(String email, String username, String password,
                String location, String studentID, String school) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.location= location;
        this.studentID = studentID;
        this.school = school;
    }

}
