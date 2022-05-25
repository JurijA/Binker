package be.kuleuven.objects;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.kuleuven.binker.ChatActivity;
import be.kuleuven.binker.R;

public class RecyclerAdapterChatHeads extends RecyclerView.Adapter<RecyclerAdapterChatHeads.ChatHeadViewHolder> {
    private static final Integer USER_ICON_SIZE = 80;
    public static List<User> friendList;
    private final Context context;
    public User user;

    public RecyclerAdapterChatHeads(User user, List<User> friendList, Context context) {
        this.context = context;
        this.user = user;
        RecyclerAdapterChatHeads.friendList = friendList;
    }

    @NonNull
    @Override
    public ChatHeadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        return new ChatHeadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHeadViewHolder holder, int position) {
        User friend = friendList.get(position);
        holder.BtnToFriendChat.setText(friendList.get(position).getName());
        holder.ImageProfileFriend.setImageBitmap(DataBaseHandler.Base64ToBitmapToSize(friend.getProfilePicture(), 100));
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }


    class ChatHeadViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ImageProfileFriend;
        Button BtnToFriendChat;

        public ChatHeadViewHolder(@NonNull View itemView) {
            super(itemView);
            ImageProfileFriend = itemView.findViewById(R.id.ImageProfileFriend);
            ImageProfileFriend.setMaxWidth(USER_ICON_SIZE);
            ImageProfileFriend.setMaxHeight(USER_ICON_SIZE);
            BtnToFriendChat = itemView.findViewById(R.id.BtnToFriendChat);
            BtnToFriendChat.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("Friendship", new Friendship(user, friendList.get(getAdapterPosition())));
            context.startActivity(intent);
        }
    }
}