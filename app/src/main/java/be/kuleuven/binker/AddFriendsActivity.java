
package be.kuleuven.binker;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;


public class AddFriendsActivity extends AppCompatActivity {

    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    // variables for imageview, edittext,
    // button, bitmap and qrencoder.
    private ImageView qrCodeIV;
    private EditText dataEdt;
    private Button generateQrBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        // initializing all variables.
        qrCodeIV = findViewById(R.id.idIVQrcode);
        dataEdt = findViewById(R.id.idEdt);
        generateQrBtn = findViewById(R.id.idBtnGenerateQR);

        // initializing onclick listener for button.
        generateQrBtn.setOnClickListener(
                v -> {
                    if (TextUtils.isEmpty(dataEdt.getText().toString())) {

                        // if the edittext inputs are empty then execute
                        // this method showing a toast message.
                        Toast.makeText(AddFriendsActivity.this, "Enter some text to generate QR Code", Toast.LENGTH_SHORT).show();
                    } else {
                        // below line is for getting
                        // the windowmanager service.
                        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

                        // initializing a variable for default display.
                        Display display = manager.getDefaultDisplay();

                        // creating a variable for point which
                        // is to be displayed in QR Code.
                        Point point = new Point();
                        display.getSize(point);

                        // getting width and
                        // height of a point
                        int width = point.x;
                        int height = point.y;

                        // generating dimension from width and height.
                        int dimen = Math.min(width, height);
                        dimen = dimen * 3 / 4;

                        // setting this dimensions inside our qr code
                        // encoder to generate our qr code.
                        qrgEncoder = new QRGEncoder(dataEdt.getText().toString(), null, QRGContents.Type.TEXT, dimen);
                        try {
                            // getting our qrcode in the form of bitmap.
                            bitmap = qrgEncoder.encodeAsBitmap();
                            // the bitmap is set inside our image
                            // view using .setimagebitmap method.
                            qrCodeIV.setImageBitmap(bitmap);
                        } catch (WriterException e) {
                            // this method is called for
                            // exception handling.
                            Log.e("Tag", e.toString());
                        }
                    }
                });
    }
}
