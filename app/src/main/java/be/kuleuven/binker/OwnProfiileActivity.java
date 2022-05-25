package be.kuleuven.binker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import be.kuleuven.objects.DataBaseHandler;
import be.kuleuven.objects.User;

public class OwnProfiileActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_profiile);
        ImageView OwnProfilePicture = findViewById(R.id.ImageOwnProfile);
        user = getIntent().getParcelableExtra("User");
        TextView OwnUserName = findViewById(R.id.TxtOwnNameInfo);
        TextView userSince = findViewById(R.id.TxtDateUserSince);
        OwnProfilePicture.setImageBitmap(DataBaseHandler.Base64ToBitmapToSize(user.getProfilePicture(),200));
        OwnUserName.setText(user.getName());
        userSince.setText(user.getUserSince());


    }

    public void onBtnOwnProfileToFriends_CLicked (View caller){
        Intent intent = new Intent(this, FriendActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }


}