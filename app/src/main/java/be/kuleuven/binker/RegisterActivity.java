package be.kuleuven.binker;

import static be.kuleuven.objects.DataBaseHandler.sha256;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.objects.DataBaseHandler;
import be.kuleuven.objects.User;

//import android.widget.Button;

@RequiresApi(api = Build.VERSION_CODES.N)
public class RegisterActivity extends AppCompatActivity {
    DataBaseHandler dataBaseHandler = new DataBaseHandler(RegisterActivity.this);
    private List<User> listUsers = new ArrayList<>();
    private TextView txtUsername, txtPassword, txtConfirmPassword, txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dataBaseHandler.getAmountUsers();
        dataBaseHandler.getUsers();

        txtUsername = findViewById(R.id.txtRegisterUser);
        txtPassword = findViewById(R.id.txtRegisterPass);
        txtConfirmPassword = findViewById(R.id.txtRegisterPassConf);
        txtEmail = findViewById(R.id.txtRegisterEmail);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onBtnRegister_Clicked(View caller) {
        listUsers = DataBaseHandler.userList;
        Integer amountUsers = listUsers.size();
        String username = txtUsername.getText() + "";
        String password = sha256(txtPassword.getText() + "");
        String confirmPassword = sha256(txtConfirmPassword.getText() + "");
        String email = txtEmail.getText() + "";
        User user = new User(
                getFreeId(amountUsers),
                username,
                password + "",
                "pic",
                "birthday",
                "g",
                "link",
                "location",
                email + "");
        System.out.println(user);
        if (txtPassword.getText().length() >= 0) {
            if (!user.getName().equals("")) {
                if (password.equals(confirmPassword)) {
                    if (!isValidEmailAddress(txtEmail.getText() + "")) {
                        if (mailIsUnique(txtEmail.getText() + "")) {

                            dataBaseHandler.addUser(user);

                            Toast.makeText(this, "Welcome " + user.getName() + " ;)", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(this, ContactActivity.class);
                            intent.putExtra("User", user);
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

    public Integer getFreeId(Integer id) {
        if (idIsUnique(id)) {
            return id;
        } else {
            return getFreeId(2 * id);
        }

    }

    public boolean idIsUnique(Integer id) {
        return listUsers
                .stream()
                .noneMatch(user -> user.getId().equals(id));
    }


    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean mailIsUnique(String email) {
        return listUsers
                .stream()
                .noneMatch(user -> user.getEmail().equals(email));
    }
}