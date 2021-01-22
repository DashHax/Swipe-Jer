package io.pakcik.assignment.group.swipejer;

public class User {
    public String id;
    public String userName;
    public String email;
    public String password;
    public String gender;
    public String location;
    public String phone_number;

    public User(String id, String userName, String email, String password, String gender, String location, String phone_number) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        //gender
        this.gender = gender;
        //location
        this.location = location;
        //phone number
        this.phone_number = phone_number;
    }

}
