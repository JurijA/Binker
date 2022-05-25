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

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import be.kuleuven.objects.DataBaseHandler;
import be.kuleuven.objects.User;

//import android.widget.Button;

@RequiresApi(api = Build.VERSION_CODES.N)
public class RegisterActivity extends AppCompatActivity {
    DataBaseHandler dataBaseHandler = new DataBaseHandler(RegisterActivity.this);
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
        List<User> listUsers = DataBaseHandler.userList;
        Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());
        Integer amountUsers = listUsers.size();
        String username = txtUsername.getText() + "";
        String password = sha256(txtPassword.getText() + "");
        String confirmPassword = sha256(txtConfirmPassword.getText() + "");
        String email = txtEmail.getText() + "";
        User user = new User(
                getFreeId(amountUsers),
                username,
                password + "",
                "+EAvAgj4QCABv9IRjSAiXgqXLAH5YQPyN9Ja9edANxAAAAAElFTkSuQmCC",
                "birthday",
                "g",
                "link",
                "location",
                email,
                currentTime + "");
        System.out.println(user);
        if (txtPassword.getText().length() >= 0) {
            if (!user.getName().equals("")) {
                if (password.equals(confirmPassword)) {
                    if (!DataBaseHandler.isValidEmailAddress(txtEmail.getText() + "")) {
                        if (DataBaseHandler.mailIsUnique(txtEmail.getText() + "")) {

                            dataBaseHandler.addUser(user);
                            Toast.makeText(this, "Welcome " + user.getName() + " ;)", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, FriendActivity.class);
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
        if (DataBaseHandler.idIsUnique(id)) {
            return id;
        } else {
            return getFreeId(2 * id);
        }
    }
}