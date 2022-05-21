package be.kuleuven.binker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GroupActivity extends AppCompatActivity{

    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
    }

    public void onBtnFriends_Clicked (View caller){
        Intent intent = new Intent(this, FriendActivity.class);
        startActivity(intent);
    }
}