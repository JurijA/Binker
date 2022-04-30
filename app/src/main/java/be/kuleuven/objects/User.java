package be.kuleuven.objects;

import androidx.annotation.NonNull;

import java.util.Objects;

public class User {
    String name;
    String password;
    Integer age;
    String email;
    String profilePicture;

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }
    public User(String name){
        this.name = name;
    }
    public User(String name, String password,Integer age, String email,String profilePicture ){
        this.name = name;
        this.password = password;
        this.age = age;
        this.email = email;
        this.profilePicture = profilePicture;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, profilePicture);
    }

    public String getPassword() {
        if (password != null)
            return password;
        else return "";
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                '}';
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
