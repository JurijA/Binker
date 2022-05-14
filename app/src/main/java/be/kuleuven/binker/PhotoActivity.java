package be.kuleuven.binker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PhotoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        recyclerView = findViewById(R.id.recyclerviewphoto);
        recyclerAdapter = new RecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onBtnBackToGroupActivity_Clicked(View caller){
        Intent intent = new Intent(this, GroupActivity.class);
        startActivity(intent);
    }
}