package be.kuleuven.binker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import be.kuleuven.dependencies.QRGContents;
import be.kuleuven.dependencies.QRGEncoder;
import be.kuleuven.interfaces.VolleyCallBack;
import be.kuleuven.objects.Capture;
import be.kuleuven.objects.DataBaseHandler;
import be.kuleuven.objects.Friendship;
import be.kuleuven.objects.User;


public class AddFriendsActivity extends AppCompatActivity {

    private final DataBaseHandler dataBaseHandler = new DataBaseHandler(AddFriendsActivity.this);
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        dataBaseHandler.getUsers();
        dataBaseHandler.getFriends();

        ImageView qrCodeUser = findViewById(R.id.idIVQrcode);

        user = getIntent().getParcelableExtra("User");
        QRGEncoder qrgEncoder = new QRGEncoder(user.getEmail(), null, QRGContents.Type.TEXT, 200);


        try {
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            qrCodeUser.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.e("Tag", e.toString());
        }
    }

    public void onAddFriend_Clicked(View caller) {
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
        System.out.println("-------------------");
        System.out.println(email);
        System.out.println("-------------------");
        if ((DataBaseHandler.isValidEmailAddress(email)) && email != null) { // security
            if (DataBaseHandler.emailExists(email)) {       // email in db?
                User friend = DataBaseHandler.getUserFromEmail(email);
                Friendship friendshipA = new Friendship(user, friend);
                Friendship friendshipB = new Friendship(friend, user);
                AlertDialog.Builder builder = new AlertDialog.Builder(AddFriendsActivity.this)
                        .setTitle("Add friend")
                        .setIcon(R.mipmap.ic_binker_launcher_round)
                        .setMessage("Do you want to add " + friend.getName() + " ?")
                        .setPositiveButton("Yes",
                                ((dialogInterface, i) -> dataBaseHandler.friendShipExists(friendshipA,
                                        new VolleyCallBack() {
                                            @Override
                                            public void onSuccess() {
                                                Toast.makeText(AddFriendsActivity.this,
                                                        "You're already friends with " +
                                                                friendshipA.getB().getName(),
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFail() {
                                                dataBaseHandler.addFriendShip(friendshipA);
                                                dataBaseHandler.addFriendShip(friendshipB);
                                                Toast.makeText(AddFriendsActivity.this,
                                                        "Successfully added " + friendshipA.getB().getName(),
                                                        Toast.LENGTH_SHORT).show();

                                            }
                                        })))
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

    public void onShowFriends_Clicked(View caller) {
        Intent intent = new Intent(this, DeleteFriendActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }

    public void onBtnAddFriendBack_Clicked(View caller) {
        Intent intent = new Intent(this, FriendActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }
}
