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

import com.android.volley.RequestQueue;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import be.kuleuven.objects.DataBaseHandler;
import be.kuleuven.objects.User;

public class LoginActivity extends AppCompatActivity {
    private static List<User> listUsers = new ArrayList<>();
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a21pt122/";
    private static Integer USER_AMOUNT;
    CallbackManager callbackManager;
    private TextView txtLoginUser, txtLoginPassword;
    private RequestQueue requestQueue;
    private AccessToken accessToken;
    DataBaseHandler dataBaseHandler = new DataBaseHandler(LoginActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dataBaseHandler.getUsers();
        dataBaseHandler.getAmountUsers();
        callbackManager = CallbackManager.Factory.create();
        accessToken = AccessToken.getCurrentAccessToken();
        txtLoginUser = findViewById(R.id.txtLoginUser);
        txtLoginPassword = findViewById(R.id.txtLoginPassword);

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
                                            Intent intent = new Intent(LoginActivity.this, ContactActivity.class);
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
        listUsers = DataBaseHandler.userList;
        USER_AMOUNT = DataBaseHandler.USER_AMOUNT;

        String inputUsername = txtLoginUser.getText() + "";
        String inputHashedPassword = sha256(txtLoginPassword.getText() + "");

        User user = new User(inputUsername, inputHashedPassword);

        if (DataBaseHandler.userExists(user)) {

            user = getUserFromLogin(user);

            Intent intent = new Intent(this, ContactActivity.class);
            intent.putExtra("User", user);
            startActivity(intent);

            Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.login_fail, Toast.LENGTH_SHORT).show();
        }
    }

    public User getUserFromLogin(User user) {
        return listUsers.stream()
                .filter(user::equalsLogin)
                .collect(Collectors.toList())
                .get(0);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onStringGoRegister_Clicked(View caller) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}