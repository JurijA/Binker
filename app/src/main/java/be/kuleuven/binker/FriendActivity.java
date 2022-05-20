package be.kuleuven.binker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
    }

    public void onBtnGroups_Clicked (View caller){
        Intent intent = new Intent(this, GroupActivity.class);
        startActivity(intent);
    }

    public void onBtnAddFriends_Clicked(View caller){
        Intent intent = new Intent(this, AddFriendsActivity.class);
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