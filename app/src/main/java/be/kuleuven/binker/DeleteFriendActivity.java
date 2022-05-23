package be.kuleuven.binker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.kuleuven.objects.DataBaseHandler;
import be.kuleuven.objects.User;

public class DeleteFriendActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private User user;
    private List<User> friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        user = getIntent().getParcelableExtra("User");
        friends = DataBaseHandler.getFriendsFrom(user);
        System.out.println(friends);
    }
}
