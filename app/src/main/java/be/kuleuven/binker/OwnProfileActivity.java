package be.kuleuven.binker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import be.kuleuven.objects.DataBaseHandler;
import be.kuleuven.objects.User;

public class OwnProfileActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_profile);
        ImageView OwnProfilePicture = findViewById(R.id.ImageOwnProfile);
        user = getIntent().getParcelableExtra("User");
        TextView OwnUserName = findViewById(R.id.TxtOwnNameInfo);
        TextView userSince = findViewById(R.id.TxtDateUserSince);
        OwnProfilePicture.setImageBitmap(DataBaseHandler.Base64ToBitmapToSize(user.getProfilePicture(),200));
        OwnUserName.setText(user.getName());
        char[] month_year_day = new char[11];
        user.getUserSince().getChars(0,11,month_year_day,0);
        userSince.setText(String.valueOf(month_year_day));


    }

    public void onBtnOwnProfileToFriends_CLicked (View caller){
        Intent intent = new Intent(this, FriendActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }


    public void onBtnDeleteAccount_Clicked(View caller){
        User user = DataBaseHandler.user;
        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
        dataBaseHandler.deleteUser(user);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }

}