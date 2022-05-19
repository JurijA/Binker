package be.kuleuven.binker;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import be.kuleuven.objects.User;

public class DatabaseHandler extends AppCompatActivity {
    private static final String PREFIX_URL = "https://studev.groept.be/api/a21pt408/";
    private static final List<User> listUsers = new ArrayList<>();
    public static Integer USERS_TABLE_SIZE = 0;
    private static DatabaseHandler instance = null;
    private RequestQueue requestQueue;

    DatabaseHandler(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    // https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html
    public static synchronized DatabaseHandler getInstance(Context context) {
        if (instance == null) instance = new DatabaseHandler(context);
        return instance;
    }

    public static synchronized DatabaseHandler getInstance() {
        if (null == instance) {
            throw new IllegalStateException(DatabaseHandler.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

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

    public JsonArrayRequest addUser(User user) {
        return new JsonArrayRequest(
                Request.Method.GET,
                PREFIX_URL + "addUser/"
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
        );

    }

    public void getUsers() {
        requestQueue = Volley.newRequestQueue(this);
        String requestURL = PREFIX_URL + "selectUsers";
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
        RequestQueue requestQueue = Volley.newRequestQueue(DatabaseHandler.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                PREFIX_URL + "getUsersSize",
                null, response -> {
            JSONObject jsonObject;
            try {
                jsonObject = response.getJSONObject(0);
                USERS_TABLE_SIZE = Integer.parseInt(jsonObject.getString("SIZE"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, null
        );
        requestQueue.add(jsonArrayRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean userExists(@NonNull User user) {
        Set<User> names = listUsers
                .stream()
                .filter(user::equals)
                .collect(Collectors.toSet());
        return !names.isEmpty();
    }
}
