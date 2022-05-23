package be.kuleuven.binker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import be.kuleuven.objects.User;

public class FriendActivity extends AppCompatActivity {

    private User user;
    private static List<User> listUsers;
    RecyclerView recyclerView;
    RecyclerAdapterFriends recyclerAdapter;
    private RequestQueue requestQueue;
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a21pt122/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        user = getIntent().getParcelableExtra("User");
        listUsers = getIntent().getParcelableExtra("Users");
        System.out.println("conact: " + listUsers);
        System.out.println("user contact: " + user);
        recyclerView = findViewById(R.id.recyclerViewFriends);
        recyclerAdapter = new RecyclerAdapterFriends();
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onBtnGroups_Clicked (View caller){
        Intent intent = new Intent(this, GroupActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onBtnAddFriends_Clicked(View caller) {
        Intent intent = new Intent(this, AddFriendsActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }

    public void onBtnFriendChat_Clicked(View caller){
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    public void OnBtnPhotos_Clicked (View caller){
        Intent intent = new Intent(this, PhotoActivity.class);
        startActivity(intent);
    }

    public Integer getFriendsCount (User user){
        requestQueue = Volley.newRequestQueue(this);
        Integer Friendscount = 0;
        String requestURL = SUBMIT_URL + "getFriendsCount/" + user.getId();
        return Friendscount;
    }

    public void onBtnUploadPhoto_Clicked (View caller){
        Intent intent = new Intent(this, AddPhotoActivity.class);
        startActivity(intent);
    }
}