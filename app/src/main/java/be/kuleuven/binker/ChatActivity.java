package be.kuleuven.binker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    public void onBtnBackToGroups_Clicked(View caller){
        Intent intent = new Intent(this, GroupActivity.class);
        startActivity(intent);
    }

    public void onBtnInfo_Clicked(View caller){
        Intent intent = new Intent(this, GroupInfoActivity.class);
        startActivity(intent);
    }
}