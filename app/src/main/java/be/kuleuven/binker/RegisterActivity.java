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
                "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",
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

                            dataBaseHandler.addUser2(user);
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