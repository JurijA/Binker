package be.kuleuven.binker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterPhotos extends RecyclerView.Adapter<RecyclerAdapterPhotos.ViewHolder>{

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.photo_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {}

    @Override
    public int getItemCount() {
        return 20;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView photo;
        TextView name,beverage;
        ImageButton profileFriend, likeButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.THEPhoto);
            name = itemView.findViewById(R.id.TxtViewNameFriend);
            beverage = itemView.findViewById(R.id.TxtViewBeverage);
            profileFriend = itemView.findViewById(R.id.BtnProfileFriend);
            likeButton = itemView.findViewById(R.id.BtnLike);
        }
    }

}
