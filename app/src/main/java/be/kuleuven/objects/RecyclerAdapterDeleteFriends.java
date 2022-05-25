package be.kuleuven.objects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.kuleuven.binker.DeleteFriendActivity;
import be.kuleuven.binker.R;

public class RecyclerAdapterDeleteFriends extends RecyclerView.Adapter<RecyclerAdapterDeleteFriends.DeleteFriendViewHolder> {
    public static List<User> friendList;
    public User user;
    private static final Integer USER_ICON_SIZE = 80;

    public RecyclerAdapterDeleteFriends(User user, List<User> friendList) {
        this.user = user;
        RecyclerAdapterDeleteFriends.friendList = friendList;
    }

    @NonNull
    @Override
    public DeleteFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_row_delete, parent, false);
        return new DeleteFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterDeleteFriends.DeleteFriendViewHolder holder, int position) {
        User friend = friendList.get(position);
        holder.txtName.setText(friend.getName());
        holder.txtFavBeer.setText(friend.getEmail());
        holder.imageView.setImageBitmap(DataBaseHandler.Base64ToBitmapToSize(friend.getProfilePicture(), 100));

    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }


    class DeleteFriendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName, txtFavBeer;
        ImageView imageView;
        ImageButton btnDelete;

        public DeleteFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageUser);
            imageView.setMaxWidth(USER_ICON_SIZE);
            imageView.setMaxHeight(USER_ICON_SIZE);
            txtName = itemView.findViewById(R.id.txtNameFriend);
            txtFavBeer = itemView.findViewById(R.id.txtFavouriteBeer);
            btnDelete = itemView.findViewById(R.id.btnDeleteFriend);
            btnDelete.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            User user = DataBaseHandler.user;
            User friend = DataBaseHandler.friends.get(pos);
            DataBaseHandler dataBaseHandler = new DataBaseHandler(view.getContext());
            dataBaseHandler.deleteFriendShip(new Friendship(user, friend));
            RecyclerAdapterChatHeads.friendList.remove(pos);
            DeleteFriendActivity.adapter.notifyItemRemoved(pos);
            Toast.makeText(view.getContext(), "You removed " + friend.getName() + " from your friends", Toast.LENGTH_SHORT).show();

        }
    }
}