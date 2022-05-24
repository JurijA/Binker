package be.kuleuven.binker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import be.kuleuven.objects.DataBaseHandler;
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Drinks, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        user = getIntent().getParcelableExtra("User");
        System.out.println(user);
        ChooseBtn = findViewById(R.id.BtnChoosePicture);
        PhotoToBeUploaded = findViewById(R.id.ImageUploadPhoto);
        PhotoToBeUploaded.setImageBitmap(DataBaseHandler.Base64ToBitmapToSize(
                user.getProfilePicture(), 400
        ));
        ChooseBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, selecter);

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
                bitmap = getResizedBitmap(bitmap, 400);

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
        if (i != 0) {
            text = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void btnFromProfileToContacts_Clicked(View caller) {
        Intent intent = new Intent(this, FriendActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }

    public void onUploadPhoto_Clicked(View caller) {
        user.setProfilePicture(DataBaseHandler.BitmapToBase64(bitmap));
        System.out.println("user pre" + user);
        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
        dataBaseHandler.updateUserProfilePicture(user);
        Toast.makeText(this, "Profile picture is updated", Toast.LENGTH_SHORT).show();
    }
}

