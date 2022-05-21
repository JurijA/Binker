package be.kuleuven.objects;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

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
    public static List<User> userList = new ArrayList<>();
    public static List<Friendship> friendShips = new ArrayList<>();
    public static Integer USER_AMOUNT;
    public Context context;

    public DataBaseHandler(Context context) {
        this.context = context;
    }

    public static Boolean userExists(@NonNull User user) {
        return userList
                .stream()
                .anyMatch(user::equalsLogin);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean mailIsUnique(String email) {
        return userList
                .stream()
                .noneMatch(user -> user.getEmail().equals(email));
    }

    public static boolean idIsUnique(Integer id) {
        return userList
                .stream()
                .noneMatch(user -> user.getId().equals(id));
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static User getUserFromEmail(String email) {
        return userList.stream()
                .filter(user -> user.hasEmail(email))
                .collect(Collectors.toList())
                .get(0);
    }

    public static boolean emailExists(String email) {
        return userList
                .stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    public static boolean friendShipExists(Friendship friendship) {
        return friendShips
                .stream()
                .anyMatch(friendship::equals);
    }

    public static User getUserFromLogin(User user) {
        return userList.stream()
                .filter(user::equalsLogin)
                .collect(Collectors.toList())
                .get(0);
    }

    public void getFriendShips() {

        String requestURL = SUBMIT_URL + "selectFriends";
        Volley.newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.POST,
                        requestURL,
                        null,
                        response -> {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    User friendA = getUserFromId(response.getJSONObject(i).getInt("idUserA"));
                                    User friendB = getUserFromId(response.getJSONObject(i).getInt("idUserB"));
                                    if (friendA != null && friendB != null) {
                                        friendShips.add(new Friendship(friendA, friendB));
                                    }
                                }
                            } catch (Exception e) {
                                Log.d("JSONObject: ", e.getMessage(), e);
                            }
                        },
                        System.out::println

                ));

    }

    public User getUserFromId(Integer id) {
        return userList.stream()
                .filter(user -> user.hasId(id))
                .collect(Collectors.toList())
                .get(0);
    }

    public void getUsers() {
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
                                    System.out.println("User: " + i + " : " + user);
                                    userList.add(user);

                                }
                                System.out.println("DB list in class= " + userList);
                            } catch (Exception e) {
                                Log.d("JSONObject: ", e.getMessage(), e);
                            }
                        },
                        System.out::println

                ));
    }

    public void addFriendShip(Friendship friendship) {
        Volley.newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        SUBMIT_URL + "addFriendship/"
                                + friendship.getA().getId() + "" + "/"
                                + friendship.getB().getId() + ""
                        ,
                        null, null, null
                ));
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

    public void getAmountUsers() {

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
    }
}