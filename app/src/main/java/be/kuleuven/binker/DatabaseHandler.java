package be.kuleuven.binker;

import android.os.Build;

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
    private static final List<User> listUsers = new ArrayList<>();
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a21pt408/";
    private static Integer USERS_TABLE_SIZE;

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
                "https://studev.groept.be/api/a21pt408/addUser/"
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

    public void getAmountUsers() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                SUBMIT_URL + "getUsersSize",
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
