package be.kuleuven.binker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import be.kuleuven.objects.User;

public class AddPhotoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int selecter = 1000;
    ImageView PhotoToBeUploaded;
    Button ChooseBtn;
    private Bitmap bitmap;
    private User user;
    private static List<User> listUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        Spinner spinner = findViewById(R.id.SpinnerBeverages);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Drinks,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        ChooseBtn = findViewById(R.id.BtnChoosePicture);
        PhotoToBeUploaded = findViewById(R.id.THEPhoto);
        ChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, selecter);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == selecter && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Rescale the bitmap to 400px wide (avoid storing large images!)
                bitmap = getResizedBitmap( bitmap, 400 );

                //Setting image to ImageView
                PhotoToBeUploaded.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scale = ((float) newWidth) / width;

        // We create a matrix to transform the image
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create the new bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text;
        if(i != 0){
        text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();}

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void OnBtnBackToPhotos_Clicked (View caller){
        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }

}

