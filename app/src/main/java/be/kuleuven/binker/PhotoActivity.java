package be.kuleuven.binker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.kuleuven.objects.User;

public class PhotoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapterPhotos recyclerAdapter;
    private User user;
    private static List<User> listUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        recyclerView = findViewById(R.id.recyclerViewPhoto);
        user = getIntent().getParcelableExtra("User");
        recyclerAdapter = new RecyclerAdapterPhotos();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void onBtnBackToFriendActivity_Clicked(View caller){
        Intent intent = new Intent(this, FriendActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }

    public void onBtnUploadPhoto_Clicked (View caller){
        Intent intent = new Intent(this, AddPhotoActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }

    public void onBtnRefresh_Clicked(View caller){
        Intent intent = new Intent(this, PhotoActivity.class);
        startActivity(intent);
    }
}