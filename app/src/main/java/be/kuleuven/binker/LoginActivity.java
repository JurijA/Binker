package be.kuleuven.binker;

import static be.kuleuven.objects.DataBaseHandler.sha256;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;

import java.util.Collections;

import be.kuleuven.interfaces.VolleyCallBack;
import be.kuleuven.objects.DataBaseHandler;
import be.kuleuven.objects.User;

public class LoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    private TextView txtLoginEmail, txtLoginPassword;
    private AccessToken accessToken;
    DataBaseHandler dataBaseHandler = new DataBaseHandler(LoginActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        accessToken = AccessToken.getCurrentAccessToken();
        txtLoginEmail = findViewById(R.id.txtLoginEmail);
        txtLoginPassword = findViewById(R.id.txtLoginPassword);

        dataBaseHandler.getUsers();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "You canceled register", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull FacebookException e) {
                        Toast.makeText(LoginActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                accessToken,
                                (o, response) -> {
                                    try {
                                        if (o != null) {
                                            Toast.makeText(LoginActivity.this, "Successfully logged in with Facebook", Toast.LENGTH_SHORT).show();
                                            User user = new User(Integer.parseInt(o.getString("id")), o.getString("name"));
                                            dataBaseHandler.addUser(user);
                                            Intent intent = new Intent(LoginActivity.this, FriendActivity.class);
                                            intent.putExtra("User", user);
                                            startActivity(intent);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,link");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }
                });

    }

    public void onRegisterWithFacebook_Clicked(View caller) {
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Collections.singletonList("public_profile"));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onBtnLogin_Clicked(View caller) {

        String inputEmail = txtLoginEmail.getText() + "";
        String inputHashedPassword = sha256(txtLoginPassword.getText() + "");

        User user = new User();
        user.setEmail(inputEmail);
        user.setPassword(inputHashedPassword);
        dataBaseHandler.getUserFromLogin(
                user, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        User user = DataBaseHandler.user;
                        Intent intent = new Intent(LoginActivity.this, FriendActivity.class);
                        intent.putExtra("User", user);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(LoginActivity.this, R.string.login_fail, Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onStringGoRegister_Clicked(View caller) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}