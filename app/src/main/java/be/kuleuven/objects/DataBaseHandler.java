package be.kuleuven.objects;

import static com.android.volley.toolbox.Volley.newRequestQueue;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import be.kuleuven.interfaces.VolleyCallBack;


public class DataBaseHandler {
    private static final String SUBMIT_URL = "https://studev.groept.be/api/a21pt122/";
    public static List<User> userList = new ArrayList<>();
    public static List<Friendship> friendShips = new ArrayList<>();
    public static List<User> friends = new ArrayList<>();
    public static Integer USER_AMOUNT;
    public static User user = new User();
    public static User userFromId = new User();
    public Context context;

    public DataBaseHandler(Context context) {
        this.context = context;
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

    public static Bitmap Base64ToBitmapToSize(String base64, int width, int height) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return resizeBitmap(decodedByte, width, height);
    }

    public static Bitmap Base64ToBitmapToSize(String base64, int size) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return resizeBitmap(decodedByte, size, size);
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static User getUserFromId(Integer integer) {
        return userList
                .stream()
                .filter(user -> user.hasId(integer))
                .collect(Collectors.toList())
                .get(0);
    }

    public static User getUserFromEmail(String email) {
        return userList.stream()
                .filter(user -> user.hasEmail(email))
                .collect(Collectors.toList())
                .get(0);
    }

    public void deleteUser(User user){
        String url = SUBMIT_URL + "deleteUser/" +
                user.getId() + "";
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        url,
                        null,
                        null,
                        null));
    }

    public static String BitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static String getDate() {
        Date now = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(now);
    }

    public static Bitmap resizeBitmap(Bitmap b, long width, long height) {
        return Bitmap.createScaledBitmap(b, (int) width, (int) height, false);
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public void updateUserProfilePicture(User user) {
        String url = SUBMIT_URL + "updateUserProfilePicture/"
                + user.getProfilePicture() + "/"
                + user.getId() + "";
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.POST,
                        url,
                        null,
                        System.out::println,
                        error -> Log.d("DB: write picture", error.getMessage(), error)
                )
        );
    }

    public void getChat(Friendship friendship) {
        ProgressDialog progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();
        String url = SUBMIT_URL + "";
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void uploadPhoto(Photo photo) {
        ProgressDialog progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();

        String photoEncoded = DataBaseHandler.BitmapToBase64(photo.getBitmapPhoto());

        String url = SUBMIT_URL + "uploadPhoto";

        StringRequest submitRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    progressDialog.dismiss();
                    Toast.makeText(this.context, "Post request executed", Toast.LENGTH_SHORT).show();
                },
                error -> Toast.makeText(this.context, "Post request failed", Toast.LENGTH_LONG).show()
        ) { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user", photo.getUser().getId() + "");
                params.put("time", photo.getTimestamp().toString());
                params.put("likes", photo.getLikeCount() + "");
                params.put("drink", photo.getBeverage() + "");
                params.put("photo", photoEncoded);
                return params;
            }
        };
        newRequestQueue(this.context).add(submitRequest);
    }

    public void deleteFriendShip(Friendship friendship) {
        Integer idUser = friendship.getA().getId();
        Integer idFriend = friendship.getB().getId();
        String url = SUBMIT_URL + "deleteFriendShip/" +
                idUser + "/" +
                idFriend + "/" +
                idUser + "/" +
                idFriend;
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        url,
                        null,
                        null,
                        null));
    }

    public static boolean emailExists(String email) {
        return userList
                .stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    /**

    public void getPhotosFromFriends (User user, final VolleyCallBack volleyCallBack){
        String url = SUBMIT_URL + "getPhotosOfFriends/" + user.getId();
        Volley.newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                )
        );
    }
     */

    public void getFriendsFromSynchronized(User user, final VolleyCallBack volleyCallBack) {
        String url = SUBMIT_URL + "getFriendsFrom/" + user.getId();
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        url,
                        null,
                        response -> {

                            for (int i = 0; i < response.length(); i++) {

                                try {
                                    Integer idFriend = response.getJSONObject(i).getInt("friendId");

                                    int finalI = i;
                                    getUserFromId(idFriend, new VolleyCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            if (friends.stream().noneMatch(userFromId::equals)) {
                                                DataBaseHandler.friends.add(userFromId);
                                            }

                                            if (finalI == response.length() - 1)
                                                volleyCallBack.onSuccess();
                                        }

                                        @Override
                                        public void onFail() {
                                            volleyCallBack.onFail();
                                        }
                                    });
                                } catch (Exception e) {
                                    Log.d("JSON:", e.getMessage(), e);
                                }
                            }
                        },
                        System.out::println
                )
        );
    }


    public void getUserFromLogin(User user, final VolleyCallBack callBack) {
        ProgressDialog progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Checking, please wait...");
        progressDialog.show();
        System.out.println(user);
        String requestURL = SUBMIT_URL + "getUserFromLogin" + "/" +
                user.getEmail() + "" + "/" +
                user.getPassword() + "";
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        requestURL,
                        null,
                        response -> {
                            System.out.println(response);
                            if (response.length() > 0) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        DataBaseHandler.user = new User(
                                                response.getJSONObject(i).getInt("idUser"),
                                                response.getJSONObject(i).get("userName") + "",
                                                response.getJSONObject(i).get("userPassword") + "",
                                                response.getJSONObject(i).get("userProfilePicture") + "",
                                                response.getJSONObject(i).get("userBirthday") + "",
                                                response.getJSONObject(i).get("userGender") + "",
                                                response.getJSONObject(i).get("userLink") + "",
                                                response.getJSONObject(i).get("userLocation") + "",
                                                response.getJSONObject(i).get("userEmail") + "",
                                                response.getJSONObject(i).get("userSince") + ""
                                        );
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                progressDialog.dismiss();
                                callBack.onSuccess();
                            } else {
                                callBack.onFail();
                            }
                        },
                        error -> Log.d("DB: ", error.getMessage(), error)
                ));
    }

    public void friendShipExists(Friendship friendship, final VolleyCallBack volleyCallBack) {
        String requestURL = SUBMIT_URL + "getFriendsFrom/" +
                friendship.getA().getId() + "";
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        requestURL,
                        null,
                        response -> {
                            if (response.length() > 0) volleyCallBack.onSuccess();
                            else volleyCallBack.onFail();
                        },
                        e -> Log.d("DB: ", e.getMessage(), e)
                ));
    }

    public void getUserFromId(Integer id, final VolleyCallBack volleyCallBack) {
        String requestURL = SUBMIT_URL + "getUserFromId/" + id;
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        requestURL,
                        null,
                        response -> {

                            if (response.length() > 0) {
                                try {
                                    userFromId = new User(
                                            response.getJSONObject(0).getInt("idUser"),
                                            response.getJSONObject(0).get("userName") + "",
                                            response.getJSONObject(0).get("userPassword") + "",
                                            response.getJSONObject(0).get("userProfilePicture") + "",
                                            response.getJSONObject(0).get("userBirthday") + "",
                                            response.getJSONObject(0).get("userGender") + "",
                                            response.getJSONObject(0).get("userLink") + "",
                                            response.getJSONObject(0).get("userLocation") + "",
                                            response.getJSONObject(0).get("userEmail") + "",
                                            response.getJSONObject(0).get("userSince") + ""
                                    );

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                volleyCallBack.onSuccess();
                            } else volleyCallBack.onFail();
                        },
                        e -> Log.d("DB: ", e.getMessage(), e)
                ));
    }

    public void getFriends() {
        String requestURL = SUBMIT_URL + "selectFriends";
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.POST,
                        requestURL,
                        null,
                        response -> {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    Friendship friendship = new
                                            Friendship(
                                            getUserFromId(response.getJSONObject(i).getInt("idUserA")),
                                            getUserFromId(response.getJSONObject(i).getInt("idUserB"))
                                    );
                                    friendShips.add(friendship);
                                    System.out.println("does exost" + friendship);
                                }
                            } catch (Exception e) {
                                Log.d("JSONObject: ", e.getMessage(), e);
                            }
                        },
                        e -> Log.d("DB: ", e.getMessage(), e)
                ));
    }

    public void getUsers() {
        String requestURL = SUBMIT_URL + "selectUsers";
        newRequestQueue(this.context).add(
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
                                            response.getJSONObject(i).get("userEmail") + "",
                                            response.getJSONObject(i).get("userSince") + ""
                                    );
                                    userList.add(user);
                                }
                            } catch (Exception e) {
                                Log.d("JSONObject: ", e.getMessage(), e);
                            }
                        },
                        e -> Log.d("DB: ", e.getMessage(), e)
                ));
    }

    public void addFriendShip(Friendship friendship) {
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.GET,
                        SUBMIT_URL + "addFriendship/"
                                + friendship.getA().getId() + "" + "/"
                                + friendship.getB().getId() + ""
                        ,
                        null, null, System.out::println
                ));
    }
    public void addUser2(User user){
        ProgressDialog progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();

        String url = SUBMIT_URL + "addUser";

        StringRequest submitRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    progressDialog.dismiss();
                    Toast.makeText(this.context, "Succesfully registered", Toast.LENGTH_SHORT).show();
                },
                error -> Toast.makeText(this.context, "Failed to register. Try again.", Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("uid", user.getId() + "");
                params.put("username", user.getName()+"");
                params.put("upass", user.getPassword() + "");
                params.put("uprofpic", user.getProfilePicture() + "");
                params.put("uday", user.getBirthday()+"");
                params.put("ugender", user.getGender());
                params.put("ulink", user.getLink());
                params.put("uloc", user.getLocation());
                params.put("uemail", user.getEmail());
                params.put("usince", user.getUserSince());
                return params;
            }
        };
        newRequestQueue(this.context).add(submitRequest);
    }
    public void addUser(User user) {
        newRequestQueue(this.context).add(
                new JsonArrayRequest(
                        Request.Method.POST,
                        SUBMIT_URL + "addUser"
                                + user.getId() + "" + "/"
                                + user.getName() + "" + "/"
                                + user.getPassword() + "" + "/"
                                + user.getProfilePicture() + "" + "/"
                                + user.getBirthday() + "" + "/"
                                + user.getGender() + "" + "/"
                                + user.getLocation() + "" + "/"
                                + user.getLink() + "" + "/"
                                + user.getEmail() + "" + "/"
                                + user.getUserSince() + ""
                        ,
                        null, null,
                        error -> Log.d("JSONError: ", error.getMessage(), error)
                ));
    }

    public void getAmountUsers() {

        String url = SUBMIT_URL + "getUsersSize";
        newRequestQueue(this.context).add(
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