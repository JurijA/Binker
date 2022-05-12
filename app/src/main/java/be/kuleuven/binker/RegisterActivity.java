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

import org.json.JSONException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import be.kuleuven.objects.User;

//import android.widget.Button;

@RequiresApi(api = Build.VERSION_CODES.N)
public class RegisterActivity extends AppCompatActivity {
    private final List<User> listUsers = new ArrayList<>();
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a21pt122/";
    private static Integer AMOUNT_USERS;
    private static Integer FREE_ID;
    private TextView txtUsername, txtPassword, txtConfirmPassword, txtEmail, txtRegisterNotify;
    private RequestQueue requestQueue;

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
        setContentView(R.layout.activity_register);

        getAmountUsers();
        getUsers();

        txtUsername = findViewById(R.id.txtRegisterUser);
        txtPassword = findViewById(R.id.txtRegisterPass);
        txtConfirmPassword = findViewById(R.id.txtRegisterPassConf);
        txtEmail = findViewById(R.id.txtRegisterEmail);
        txtRegisterNotify = findViewById(R.id.txtRegisterNotify);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean userExists(@NonNull User user) {
        List<User> names = listUsers
                .stream()
                .filter(user::equals)
                .collect(Collectors.toList());
        return !names.isEmpty();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onBtnRegister_Clicked(View caller) {

        String username = txtUsername.getText() + "";
        String password = sha256(txtPassword.getText() + "");
        String conf_pass = sha256(txtConfirmPassword.getText() + "");
        String email = txtEmail.getText() + "";
        System.out.println(getFreeID(AMOUNT_USERS));
        User user = new User(
                getFreeID(AMOUNT_USERS),
                username,
                password + "",
                "pic",
                "birthday",
                "g",
                "link",
                "location",
                email + "");
        if (txtPassword.getText().length() >= 8) {
            if (!user.getName().equals("")) {
                if (password.equals(conf_pass)) {
                    user.setPassword(password);
                    if (isValidEmailAddress(txtEmail.getText() + "")) {
                        if (mailIsUnique(txtEmail.getText() + "")) {
                            addUser(user);
                            Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, R.string.register_email_already_used, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, R.string.register_unvalid_email, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, R.string.register_passwords_didnt_match, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, R.string.register_unvalid_username, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.register_password_too_short, Toast.LENGTH_SHORT).show();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    // [1,5,8,12, 45,99] -> 13 (size = 6 --> 6*2 = 12 next free = 13)
    // a lot of algorithms use this technique of doubling: hashmap,
    public Integer getFreeID(Integer someId) {
        if (idExists(someId)) {
            System.out.println(someId + " -> bestaat al");
            getFreeID(someId + 1);
        }
        return 2 * someId;
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean idExists(Integer amountUsers) {
        Set<User> ids = listUsers
                .stream()
                .filter(person -> Objects.equals(person.getId(), amountUsers))
                .collect(Collectors.toSet());
        return !ids.isEmpty();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean mailIsUnique(String email) {
        Set<User> ids = listUsers
                .stream()
                .filter(person -> person.getEmail().equals(email))
                .collect(Collectors.toSet());
        return ids.isEmpty();
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
                                AMOUNT_USERS = Integer.parseInt(response.getJSONObject(0).get("SIZE") + "");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> Log.d("JSONError: ", error.getMessage(), error)
                )
        );
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

}