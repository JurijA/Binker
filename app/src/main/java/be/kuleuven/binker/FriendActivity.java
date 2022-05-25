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
import be.kuleuven.objects.RecyclerAdapterChatHeads;
import be.kuleuven.objects.User;

public class FriendActivity extends AppCompatActivity {

    private final DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
    private RecyclerView recyclerView;
    private RecyclerAdapterChatHeads adapter;
    private List<User> friends;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        user = getIntent().getParcelableExtra("User");
        recyclerView = findViewById(R.id.recyclerViewFriends);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataBaseHandler.getFriendsFromSynchronized(user,
                new VolleyCallBack() {

                    @Override
                    public void onSuccess() {

                        friends = DataBaseHandler.friends;
                        adapter = new RecyclerAdapterChatHeads(user, friends, FriendActivity.this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(FriendActivity.this));
                    }

                    @Override
                    public void onFail() {
                        System.out.println("fuck");
                    }
                }
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onBtnAddFriends_Clicked(View caller) {
        Intent intent = new Intent(this, AddFriendsActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }

    public void onBtnFriendChat_Clicked(View caller){
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }

    public void OnBtnPhotos_Clicked (View caller){
        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }


    public void onBtnUploadPhoto_Clicked (View caller){
        Intent intent = new Intent(this, AddPhotoActivity.class);
        intent.putExtra("User", user);

        startActivity(intent);
    }
}