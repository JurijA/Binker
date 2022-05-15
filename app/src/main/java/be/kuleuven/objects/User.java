package be.kuleuven.objects;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
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

    public static String sha256(final String base) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            final StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                final String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

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

    public void setPassword(String password) {
        info.put("password", sha256(password));
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
