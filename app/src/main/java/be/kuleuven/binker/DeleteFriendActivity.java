package be.kuleuven.binker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.kuleuven.interfaces.VolleyCallBack;
import be.kuleuven.objects.DataBaseHandler;
import be.kuleuven.objects.RecyclerAdapterDeleteFriends;
import be.kuleuven.objects.User;

public class DeleteFriendActivity extends AppCompatActivity {
    public static RecyclerView recyclerView;
    public static RecyclerAdapterDeleteFriends adapter;
    private final DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
    private List<User> friends;
    private User user;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_delete);
        recyclerView = findViewById(R.id.recyclerDeleteFriends);
        user = getIntent().getParcelableExtra("User");
        dataBaseHandler.getFriendsFromSynchronized(user,
                new VolleyCallBack() {

                    @Override
                    public void onSuccess() {

                        friends = DataBaseHandler.friends;
                        System.out.println("vrienden" + friends);
                        adapter = new RecyclerAdapterDeleteFriends(user, friends);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(DeleteFriendActivity.this));
                    }

                    @Override
                    public void onFail() {
                        System.out.println("fuck");
                    }
                }
        );
    }


    public void onFromDeleteFriendToAddFriends_Clicked(View caller) {
        Intent intent = new Intent(this, AddFriendsActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }

}
