package be.kuleuven.binker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import be.kuleuven.objects.User;

public class LoginActivity extends AppCompatActivity {
    private static final List<User> listUsers = new ArrayList<>();
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a21pt122/";
    private static Integer USER_AMOUNT;
    private static Integer FREE_ID;
    CallbackManager callbackManager;
    private TextView txtLoginUser, txtLoginPassword, txtNotify;
    private RequestQueue requestQueue;
    private AccessToken accessToken;

    public static String sha256(final String base) {
        try {
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
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        getAmountUsers();
        getUsers();

        callbackManager = CallbackManager.Factory.create();
        accessToken = AccessToken.getCurrentAccessToken();
        txtLoginUser = findViewById(R.id.txtLoginUser);
        txtLoginPassword = findViewById(R.id.txtLoginPassword);
        txtNotify = findViewById(R.id.txtNotify);

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onError(@NonNull FacebookException e) {

                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                accessToken,
                                (o, response) -> {
                                    try {
                                        if (o != null) {
                                            txtNotify.setText(o.getString("name"));
                                            User user = new User(Integer.parseInt(o.getString("id")), o.getString("name"));
                                            addUser(user);
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
        String inputUsername = txtLoginUser.getText() + "";
        String inputHashedPassword = sha256(txtLoginPassword.getText() + "");
        User user = new User(inputUsername, inputHashedPassword);
        System.out.println(user);
        if (userExists(user)) {
            Intent intent = new Intent(this, ContactActivity.class);
            intent.putExtra("email", user.getEmail());
            startActivity(intent);
            Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.login_fail, Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onStringGoRegister_Clicked(View caller) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean userExists(@NonNull User user) {
        System.out.println(listUsers);
        Set<User> names = listUsers
                .stream()
                .filter(user::equalsLogin)
                .collect(Collectors.toSet());
        System.out.println(names);
        return !names.isEmpty();
    }

    public void addUser(User user) {
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        SUBMIT_URL + "addUser/"
                                + user.getId() + "/"
                                + user.getName() + "" + "/"
                                + user.getPassword() + "" + "/"
                                + user.getProfilePicture() + "" + "/"
                                + user.getBirthday() + "" + "/"
                                + user.getGender() + "" + "/"
                                + user.getLocation() + "" + "/"
                                + user.getLink() + "" + "/"
                                + user.getEmail() + ""
                        ,
                        null, null, null
                ));
    }

    public void getAmountUsers() {
        requestQueue = Volley.newRequestQueue(this);
        String url = SUBMIT_URL + "getUsersSize";
        requestQueue.add(
                new JsonArrayRequest(
                        Request.Method.POST,
                        url,
                        null,
                        response -> {
                            try {
                                USER_AMOUNT = Integer.parseInt(response.getJSONObject(0).get("SIZE") + "");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> Log.d("JSONError: ", error.getMessage(), error)
                )
        );
    }

    public void getUsers() {
        requestQueue = Volley.newRequestQueue(this);
        String requestURL = SUBMIT_URL + "selectUsers";
        requestQueue.add(
                new JsonArrayRequest(
                        Request.Method.POST,
                        requestURL,
                        null,
                        response -> {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    User user = new User(
                                            response.getJSONObject(i).getInt("idUser"),
                                            response.getJSONObject(i).get("userName") + "",
                                            response.getJSONObject(i).get("userPassword") + "",
                                            response.getJSONObject(i).get("userProfilePicture") + "",
                                            response.getJSONObject(i).get("userBirthday") + "",
                                            response.getJSONObject(i).get("userGender") + "",
                                            response.getJSONObject(i).get("userLink") + "",
                                            response.getJSONObject(i).get("userLocation") + "",
                                            response.getJSONObject(i).get("userEmail") + ""
                                    );
                                    listUsers.add(user);
                                }

                            } catch (Exception e) {
                                Log.d("JSONObject: ", e.getMessage(), e);
                            }
                        }
                        ,
                        error -> Log.d("JSONError: ", error.getMessage(), error)

                ));
    }
}