
package be.kuleuven.binker;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;

import be.kuleuven.objects.User;


public class AddFriendsActivity extends AppCompatActivity {

    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    private User user;
    private ImageView qrCodeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        qrCodeUser = findViewById(R.id.idIVQrcode);
        user = getIntent().getParcelableExtra("User");
        System.out.println("user add friend: " + user);
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int dimen = Math.min(point.x, point.y);
        dimen = dimen * 3 / 4;
        System.out.println("geparce user: " + user);
        qrgEncoder = new QRGEncoder(user.getEmail(), null, QRGContents.Type.TEXT, dimen);

        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            qrCodeUser.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.e("Tag", e.toString());
        }
    }
}
