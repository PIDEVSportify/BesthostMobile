package Entities;

public class User extends com.codename1.facebook.User {
    private Integer cin ;
    private String first_name,last_name,email,password,avatar,phone_number;


    public int getCin() {
        return cin;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name.substring(0,1).toUpperCase()+first_name.substring(1).toLowerCase();
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name.substring(0,1).toUpperCase()+last_name.substring(1).toLowerCase();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public User(int cin, String first_name, String last_name, String email, String password, String avatar) {
        this.cin = cin;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }

    public User ()
    {

    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    @Override
    public String toString() {
        return "User{" +
                "cin=" + cin +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
