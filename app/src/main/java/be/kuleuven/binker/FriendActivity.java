package be.kuleuven.binker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import be.kuleuven.objects.User;

public class FriendActivity extends AppCompatActivity {

    private User user;
    private static List<User> listUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        user = getIntent().getParcelableExtra("User");
        listUsers = getIntent().getParcelableExtra("Users");
        System.out.println("conact: " + listUsers);
        System.out.println("user contact: " + user);
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
}