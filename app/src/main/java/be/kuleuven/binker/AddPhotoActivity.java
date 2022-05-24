package be.kuleuven.binker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import be.kuleuven.objects.DataBaseHandler;
import be.kuleuven.objects.User;

public class AddPhotoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        Spinner spinner = findViewById(R.id.SpinnerBeverages);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Drinks, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Button button = findViewById(R.id.BtnChoosePicture);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Pick an image"),1);
            }
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
        startActivity(intent);
    }

    public void onBtnUpload_Clicked(View caller){
        ImageView PhotoToBeUploaded = findViewById(R.id.ImageUploadPhoto);
        BitmapDrawable drawable = (BitmapDrawable) PhotoToBeUploaded.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        String encodedPhoto = DataBaseHandler.BitmapToBase64(bitmap);
        DataBaseHandler.uploadPhoto(user, String.valueOf(findViewById(Spinner)),encodedPhoto);

        Intent intent = new Intent(this, PhotoActivity.class);
        startActivity(intent);
    }
}

