package be.kuleuven.binker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.objects.Group;
import be.kuleuven.objects.User;

public class GroupActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    private TextView txtNameGroup;
    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onBtnChat_Clicked(View caller){
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    public void onBtnPhotos_Clicked(View caller){
        Intent intent = new Intent(this, PhotoActivity.class);
        startActivity(intent);
    }

    public void onBtnFriends_Clicked (View caller){
        Intent intent = new Intent(this, FriendActivity.class);
        startActivity(intent);
    }
}