package be.kuleuven.binker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.kuleuven.objects.DataBaseHandler;
import be.kuleuven.objects.Friendship;

public class ChatActivity extends AppCompatActivity {
    private TextView TxtFriendName;
    private RecyclerView recyclerView;
    private Friendship friendship;
    private List<Message> messageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.recyclerViewChat);
        friendship = getIntent().getParcelableExtra("Friendship");
        TxtFriendName = findViewById(R.id.TxtFriendName);
        TxtFriendName.setText(friendship.getB().getName());

    }

    public void onBtnBackToContacts_Clicked(View caller){
        Intent intent = new Intent(this, FriendActivity.class);
        intent.putExtra("User", friendship.getA());
        startActivity(intent);
    }

    public void onBtnInfo_Clicked(View caller){
        Intent intent = new Intent(this, FriendInfoActivity.class);
        intent.putExtra("Friend", friendship.getB());
        startActivity(intent);
    }
}