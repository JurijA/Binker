package be.kuleuven.binker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.dependencies.QRGContents;
import be.kuleuven.dependencies.QRGEncoder;
import be.kuleuven.objects.Capture;
import be.kuleuven.objects.DataBaseHandler;
import be.kuleuven.objects.Friendship;
import be.kuleuven.objects.User;


public class AddFriendsActivity extends AppCompatActivity {

    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    List<User> userList = new ArrayList<>();
    List<Friendship> friendships = new ArrayList<>();
    DataBaseHandler dataBaseHandler = new DataBaseHandler(AddFriendsActivity.this);
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        userList = DataBaseHandler.userList;
        dataBaseHandler.getFriendShips();

        ImageView qrCodeUser = findViewById(R.id.idIVQrcode);

        this.user = getIntent().getParcelableExtra("User");

        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int dimen = Math.min(point.x, point.y);
        dimen = dimen * 3 / 4;
        qrgEncoder = new QRGEncoder(user.getEmail(), null, QRGContents.Type.TEXT, dimen);

        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            qrCodeUser.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.e("Tag", e.toString());
        }
    }

    public void onAddFriend_Clicked(View caller) {
        friendships = DataBaseHandler.friendShips;
        IntentIntegrator intentIntegrator = new IntentIntegrator(
                AddFriendsActivity.this
        );
        intentIntegrator.setPrompt("For flash use volume up button")
                .setBeepEnabled(true)
                .setOrientationLocked(true)
                .setCaptureActivity(Capture.class)
                .initiateScan();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );

        String email = intentResult.getContents(); // contents = email

        if (DataBaseHandler.isValidEmailAddress(email)) { // security
            if (DataBaseHandler.emailExists(email)) {       // email in db?
                User friend = DataBaseHandler.getUserFromEmail(email);
                Friendship friendship = new Friendship(user, friend);
                AlertDialog.Builder builder = new AlertDialog.Builder(AddFriendsActivity.this)
                        .setTitle("Add friend")
                        .setIcon(R.mipmap.ic_binker_launcher_round)
                        .setMessage("Do you want to add " + friend.getName() + " ?")
                        .setPositiveButton("Yes",
                                ((dialogInterface, i) -> {
                                    if (DataBaseHandler.friendShipExists(friendship)) {
                                        Toast.makeText(this, "You're already friends with " + friendship.getB().getName(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        dataBaseHandler.addFriendShip(friendship);
                                        Toast.makeText(this, "Successfully added " + friendship.getB().getName(), Toast.LENGTH_SHORT).show();

                                    }
                                }))
                        .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel());
                AlertDialog alert = builder.create();
                alert.setOnShowListener(arg0 -> {
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.red));
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.black));
                });
                alert.show();
            } else {
                Toast.makeText(getApplicationContext(), "Couldn't find user", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Not proper User", Toast.LENGTH_SHORT).show();
        }
    }
}
