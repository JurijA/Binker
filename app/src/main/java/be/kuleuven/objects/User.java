package be.kuleuven.objects;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class User implements Parcelable {

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


    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


    public boolean equalsLogin(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(info.get("name"), user.getName())
                && Objects.equals(info.get("password"), user.getPassword());
    }

    protected User(Parcel in) {
        keys = in.createStringArray();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
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

    public User getUser(User o) {
        if (equalsLogin(o)) return this;
        return null;
    }

    public void setName(String name) {
        info.put("name", name);
    }

    public String getName() {
        return info.get("name") == null ? "null" : info.get("name");

    }

    public void setBirthday(String birthday) {
        info.put("birthday", birthday);
    }

    public String getBirthday() {


        return info.get("birthday") == null ? "null" : info.get("birthday");
    }

    public void setGender(String gender) {
        info.put("gender", gender);
    }

    public String getGender() {

        return info.get("gender") == null ? "null" : info.get("gender");
    }

    public void setLink(String link) {
        info.put("link", link);
    }

    public String getLink() {

        return info.get("link") == null ? "null" : info.get("link");
    }

    public void setLocation(String location) {
        info.put("location", location);
    }

    public Map<String, String> getInfo() {
        return info;
    }

    public String getLocation() {
        return info.get("location") == null ? "null" : info.get("location");
    }

    public void setEmail(String email) {
        info.put("email", email);
    }

    public String getEmail() {
        return info.get("email") == null ? "null" : info.get("email");
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

    public String getProfilePicture() {
        return info.get("profilePicture") == null ? "null" : info.get("profilePicture");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(getId());

        List<String> list = new ArrayList<>(info.values());
        out.writeStringList(list);
    }

}
