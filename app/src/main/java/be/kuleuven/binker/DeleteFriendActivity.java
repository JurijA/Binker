package be.kuleuven.binker;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.interfaces.VolleyCallBack;
import be.kuleuven.objects.DataBaseHandler;
import be.kuleuven.objects.RecyclerAdapter;
import be.kuleuven.objects.User;

public class DeleteFriendActivity extends AppCompatActivity {
    public static RecyclerView recyclerView;
    public static RecyclerAdapter adapter;
    DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
    private List<User> friends = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_delete);
        recyclerView = findViewById(R.id.recyclerDeleteFriends);
        User user = getIntent().getParcelableExtra("User");
        dataBaseHandler.getFriendsFromSynchronized(user,
                new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        friends = DataBaseHandler.friends;

                        adapter = new RecyclerAdapter(user, friends);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(DeleteFriendActivity.this));

                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(DeleteFriendActivity.this, "Couldn't find friends", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

}
