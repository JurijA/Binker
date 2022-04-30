package be.kuleuven.binker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
//import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;


import be.kuleuven.objects.User;

public class LoginActivity extends AppCompatActivity {
    private TextView txtLoginUser;
    private TextView txtLoginPassword;
    private TextView txtNotify;

    private final List<User> listUsers = new ArrayList<>();
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a21pt408/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateUsers();
        setContentView(R.layout.activity_login);
        txtLoginUser =  findViewById(R.id.txtLoginUser);
        txtLoginPassword = findViewById(R.id.txtLoginPassword);
        txtNotify = findViewById(R.id.txtNotify);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onBtnLogin_Clicked(View caller){
        String inputUsername = txtLoginUser.getText()+"";
        String inputHashedPassword = sha256(txtLoginPassword.getText() +"");

        User user = new User(inputUsername, inputHashedPassword);

        if (userExists(user))
            txtNotify.setText(getString(R.string.login_notify_found));
        else txtNotify.setText(getString(R.string.login_notify_not_found));
    }
    public void onStringGoRegister_Clicked(View caller){
        // listUsers.forEach(System.out::println);
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void updateUsers(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest responds = getCredentials();
        requestQueue.add(responds);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean userExists(@NonNull User user){
        List<User> names =  listUsers
                .stream()
                .filter(user::equals)
                .collect(Collectors.toList());
        return !names.isEmpty();
    }

    public JsonArrayRequest getCredentials( ){
        String requestURL = SUBMIT_URL+"selectUsers";

        return new JsonArrayRequest(
                Request.Method.GET,
                requestURL, null,
                response -> {
                    try{
                        for (int i = 0; i < response.length(); i++){
                             User user = new User(
                                    response.getJSONObject(i).get("userName")+"",
                                    response.getJSONObject(i).get("userPassword")+""
                            );
                            listUsers.add(user);
                        }
                    }
                    catch (Exception e){
                        Log.d("JSONObject: ", e.getMessage(),e);
                    }
                }
                ,
                error -> Log.d("JSONError: ", error.getMessage(),error)

        );
    }

    public static String sha256(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            final StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                final String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }



}