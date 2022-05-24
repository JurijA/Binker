package be.kuleuven.objects;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class User implements Parcelable {

    // constructor syntax -- important
    public static boolean exists = false;
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

    protected User(Parcel in) {
        this.id = in.readInt();
        for (String key : keys) {
            info.put(key, in.readString());
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


    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(getId());
        out.writeString(getName());
        out.writeString(getPassword());
        out.writeString(getProfilePicture());
        out.writeString(getBirthday());
        out.writeString(getGender());
        out.writeString(getLink());
        out.writeString(getLocation());
        out.writeString(getEmail());
    }

    public void setExists(boolean exists) {
        User.exists = exists;
    }

    public boolean equalsLogin(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(this.getEmail(), user.getEmail())
                && Objects.equals(this.getPassword(), user.getPassword());
    }



    public boolean equalsRegister(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(info.get("email"), user.getEmail());
    }


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPassword() {
        return info.get("password") == null ? "password" : info.get("password");
    }


    public void setName(String name) {
        info.put("name", name);
    }

    public boolean hasEmail(String email) {
        return getEmail().equals(email);
    }

    public boolean hasId(Integer id) {
        return getId().equals(id);
    }


    public void setPassword(String password) {
        info.put("password", password);
    }

    public void setBirthday(String birthday) {
        info.put("birthday", birthday);
    }

    public String getName() {
        return info.get("name") == null ? "name" : info.get("name");

    }

    public void setGender(String gender) {
        info.put("gender", gender);
    }

    public String getBirthday() {
        return info.get("birthday") == null ? "birthday" : info.get("birthday");
    }

    public void setLink(String link) {
        info.put("link", link);
    }

    public String getGender() {
        return info.get("gender") == null ? "g" : info.get("gender");
    }

    public void setLocation(String location) {
        info.put("location", location);
    }

    public Map<String, String> getInfo() {
        return info;
    }

    public String getLink() {

        return info.get("link") == null ? "link" : info.get("link");
    }

    public void setEmail(String email) {
        info.put("email", email);
    }

    public String getLocation() {
        return info.get("location") == null ? "location" : info.get("location");
    }

    public void setProfilePicture(String profilePicture) {
        info.put("profilePicture", profilePicture);
    }

    public String getEmail() {
        return info.get("email") == null ? "email" : info.get("email");
    }

    public String getProfilePicture() {
        return info.get("profilePicture") == null ? "pic" : info.get("profilePicture");
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", profilePicture='" + getProfilePicture() + '\'' +
                ", birthday='" + getBirthday() + '\'' +
                ", gender='" + getGender() + '\'' +
                ", link='" + getLink() + '\'' +
                ", location='" + getLocation() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}' + "\n";
    }
}
