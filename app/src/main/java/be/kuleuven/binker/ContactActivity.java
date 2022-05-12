package be.kuleuven.binker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onStringGoRegister_Clicked(View caller) {
        Intent intent = new Intent(this, AddFriendsActivity.class);
        startActivity(intent);
    }
}