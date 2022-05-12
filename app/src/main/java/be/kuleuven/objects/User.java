package be.kuleuven.objects;


import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class User {

    // constructor syntax -- important

    String[] keys = {"name", "password", "profilePicture", "birthday", "gender", "link", "location", "email"};
    Integer id;
    Map<String, String> info = new HashMap<>();

    public User(Integer id, String... info) {
        if (id != null) {
            this.id = id;
        }
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                this.info.put(keys[i], info[i]);
            }
        }
    }

    public User(String... info) {
        for (int i = 0; i < info.length; i++) {
            this.info.put(keys[i], info[i]);
        }
    }


    public boolean equalsLogin(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(info.get("name"), user.getName())
                && Objects.equals(info.get("password"), user.getPassword());
    }

    public boolean equalsRegister(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(info.get("email"), user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(info.get("name"), info.get("password"), info.get("profilePicture"));
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return info.get("password") == null ? "null" : info.get("password");
    }

    public void setPassword(String password) {
        info.put("password", password);
    }

    public String getName() {
        return info.get("name");
    }

    public void setName(String name) {
        info.put("name", name);
    }

    public String getBirthday() {
        return info.get("birthday");
    }

    public void setBirthday(String birthday) {
        info.put("birthday", birthday);
    }

    public String getGender() {
        return info.get("gender");
    }

    public void setGender(String gender) {
        info.put("gender", gender);
    }

    public String getLink() {
        return info.get("link");
    }

    public void setLink(String link) {
        info.put("link", link);
    }

    public String getLocation() {
        return info.get("location");
    }

    public void setLocation(String location) {
        info.put("location", location);
    }

    public Map<String, String> getInfo() {
        return info;
    }

    public String getEmail() {
        return info.get("email");
    }

    public void setEmail(String email) {
        info.put("email", email);
    }

    public String getProfilePicture() {
        return info.get("profilePicture");
    }

    public void setProfilePicture(String profilePicture) {
        info.put("profilePicture", profilePicture);
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "birthday='" + getBirthday() + '\'' +
                ", name='" + getName() + '\'' +
                ", gender='" + getGender() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", link='" + getLink() + '\'' +
                ", location='" + getLocation() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", id='" + getId() + '\'' +
                ", profilePicture='" + getProfilePicture() + '\'' +
                '}' + "\n";
    }
}
