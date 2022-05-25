package be.kuleuven.binker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Size;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;

import be.kuleuven.objects.DataBaseHandler;
import be.kuleuven.objects.Photo;
import be.kuleuven.objects.User;

public class AddPhotoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static Integer MAX_POST_SIZE = 100000;

    private User user;
    private final Size photo = new Size(250, 250);
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        spinner = findViewById(R.id.SpinnerBeverages);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Drinks, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        user = getIntent().getParcelableExtra("User");
        Button button = findViewById(R.id.BtnChoosePicture);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Pick an image"), 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 1){
            ImageView imageView = findViewById(R.id.ImageUploadPhoto);

            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text;
        if (i != 0) {
            text = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
        }}

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    public void OnBtnBackToPhotos_Clicked(View caller){
        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onBtnUpload_Clicked(View caller) {
        ImageView PhotoToBeUploaded = findViewById(R.id.ImageUploadPhoto);

        BitmapDrawable drawable = (BitmapDrawable) PhotoToBeUploaded.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        bitmap = resizeBitmapToBase64Length(bitmap, 1000);


        Timestamp currentTime = new Timestamp(new Date().getTime());

        PhotoToBeUploaded.setImageBitmap(bitmap);
        String beverage = spinner.getSelectedItem().toString();
        Photo photo = new Photo(bitmap, user, beverage, currentTime, 0);
        new DataBaseHandler(this).uploadPhoto(photo);

        //Intent intent = new Intent(this, PhotoActivity.class);
        //intent.putExtra("User",user);
        //startActivity(intent);
    }

    public Bitmap resizeBitmapToBase64Length(Bitmap bitmap, Integer base64Length) {

        double scale = 1;
        int size = DataBaseHandler.bitmapToByteArray(bitmap).length;
        while (size * 1.35 > 1500) {
            bitmap = DataBaseHandler.resizeBitmap(bitmap,
                    Math.round(bitmap.getWidth() * scale),
                    Math.round(bitmap.getHeight() * scale));
            size = DataBaseHandler.bitmapToByteArray(bitmap).length;
            scale -= 0.02;

        }
        return bitmap;
    }

}

