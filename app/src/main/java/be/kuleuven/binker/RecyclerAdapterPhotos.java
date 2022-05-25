package be.kuleuven.binker;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.objects.Photo;
import be.kuleuven.objects.User;

public class RecyclerAdapterPhotos extends RecyclerView.Adapter<RecyclerAdapterPhotos.ViewHolder> {

    public User user;
    public List<Photo> PhotosFromFriends = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.photo_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo pica = PhotosFromFriends.get(position);
        holder.name.setText(pica.getUser().getName());
        holder.photo.setImageBitmap(pica.getBitmapPhoto());
    }

    @Override
    public int getItemCount() {
        return PhotosFromFriends.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView photo;
        TextView name, beverage, likeCount;
        ImageButton profileFriend, likeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.THEPhoto);
            name = itemView.findViewById(R.id.TxtViewNameFriend);
            beverage = itemView.findViewById(R.id.TxtViewBeverage);
            profileFriend = itemView.findViewById(R.id.BtnProfileFriend);
            likeButton = itemView.findViewById(R.id.BtnLike);
            likeCount = itemView.findViewById(R.id.TxtLikesCount);
        }
    }

}
