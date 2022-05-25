package be.kuleuven.objects;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Friendship implements Parcelable {
    User a;
    User b;

    public Friendship(User a, User b) {
        this.a = a;
        this.b = b;
    }

    public User getA() {
        return a;
    }

    public User getB() {
        return b;
    }

    public static final Parcelable.Creator<Friendship> CREATOR = new Parcelable.Creator<Friendship>() {
        @Override
        public Friendship createFromParcel(Parcel in) {
            return new Friendship(
                    in.readParcelable(getClass().getClassLoader()),
                    in.readParcelable(getClass().getClassLoader()));
        }

        @Override
        public Friendship[] newArray(int size) {
            return new Friendship[size];
        }
    };

    protected Friendship(Parcel in) {
        setA(in.readParcelable(getClass().getClassLoader()));
        setA(in.readParcelable(getClass().getClassLoader()));
    }

    public void setA(User a) {
        this.a = a;
    }

    public void setB(User b) {
        this.b = b;
    }

    @Override
    public boolean equals(Object friendshipToTest) {
        if (this == friendshipToTest) return true;
        if (friendshipToTest == null || this.getClass() != friendshipToTest.getClass())
            return false;
        Friendship friendship = (Friendship) friendshipToTest;
        User userToTest = friendship.getA();
        User friendToTest = friendship.getB();
        User user = getA();
        User friend = getB();
        return userToTest.getId().equals(user.getId()) &&
                friendToTest.getId().equals(friend.getId());
        // can be shorter but this is a little more readable
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(getA(), flags);
        out.writeParcelable(getB(), flags);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "Friendship{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}
