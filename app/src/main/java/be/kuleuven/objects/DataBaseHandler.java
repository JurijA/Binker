package be.kuleuven.objects;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataBaseHandler {
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a21pt122/";
    public static List<User> userList;
    public static Integer USER_AMOUNT;
    public Context context;

    public DataBaseHandler(Context context) {
        this.context = context;
    }

    public static Boolean userExists(@NonNull User user) {
        List<User> names = userList
                .stream()
                .filter(user::equalsLogin)
                .collect(Collectors.toList());
        return !names.isEmpty();
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

    public List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        String requestURL = SUBMIT_URL + "selectUsers";
        Volley.newRequestQueue(this.context).add(
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
                                    userList.add(user);
                                    DataBaseHandler.userList = userList;
                                }
                            } catch (Exception e) {
                                Log.d("JSONObject: ", e.getMessage(), e);
                            }
                        },
                        System.out::println

                ));
        return userList;
    }

    public void addUser(User user) {
        Volley.newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        SUBMIT_URL + "addUser/"
                                + user.getId() + "" + "/"
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

    public Integer getAmountUsers() {

        String url = SUBMIT_URL + "getUsersSize";
        Volley.newRequestQueue(this.context).add(
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
        return USER_AMOUNT;
    }
}