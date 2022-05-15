package be.kuleuven.binker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import be.kuleuven.objects.User;

public class ContactActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        user = getIntent().getParcelableExtra("User");
        System.out.println("user contact: " + user);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onAddFriends_Clicked(View caller) {
        Intent intent = new Intent(this, AddFriendsActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }
}