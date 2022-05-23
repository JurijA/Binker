package be.kuleuven.binker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PhotoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapterPhotos recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        recyclerView = findViewById(R.id.recyclerViewPhoto);
        recyclerAdapter = new RecyclerAdapterPhotos();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void onBtnBackToFriendActivity_Clicked(View caller){
        Intent intent = new Intent(this, FriendActivity.class);
        startActivity(intent);
    }
}