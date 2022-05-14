package be.kuleuven.binker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GroupInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
    }

    public void onBtnBackToChat_Clicked (View caller){
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }
}