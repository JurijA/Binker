package be.kuleuven.objects;

import androidx.annotation.NonNull;

public class Friendship {
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
        return userToTest.getEmail().equals(user.getEmail()) &&
                friendToTest.getEmail().equals(friend.getEmail());
        // can be shorter but this is a little more readable
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
