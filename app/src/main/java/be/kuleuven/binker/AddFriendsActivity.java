
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

import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.dependencies.QRGContents;
import be.kuleuven.dependencies.QRGEncoder;
import be.kuleuven.objects.Capture;
import be.kuleuven.objects.User;


public class AddFriendsActivity extends AppCompatActivity {

    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    private User user;
    private ImageView qrCodeUser;
    private ImageView btnAddFriend;
    List<User> friends = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        qrCodeUser = findViewById(R.id.idIVQrcode);
        btnAddFriend = findViewById(R.id.imageAddFriend);
        user = getIntent().getParcelableExtra("User");
        System.out.println("user add friend: " + user);
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
        IntentIntegrator intentIntegrator = new IntentIntegrator(
                AddFriendsActivity.this
        );
        intentIntegrator.setPrompt("For flash use volume up button");
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setCaptureActivity(Capture.class);
        intentIntegrator.initiateScan();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );
        if (intentResult.getContents() != null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(AddFriendsActivity.this)
                    .setTitle("Add friend?")
                    .setIcon(R.mipmap.ic_binker_launcher_round)
                    .setMessage(intentResult.getContents())
                    .setPositiveButton("OK",
                            (dialogInterface, i) -> {

                            }
                    );
            builder.show();
        } else {
            Toast.makeText(getApplicationContext(), "Didn't find user", Toast.LENGTH_SHORT).show();
        }
    }

}
