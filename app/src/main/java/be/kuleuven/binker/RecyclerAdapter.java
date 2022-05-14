package be.kuleuven.binker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.group_item,parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        holder.textView2.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageButton imageButton, imageButton2;
        TextView textView, textView2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.imageButton);
            imageButton2 = itemView.findViewById(R.id.imageButton2);
            textView = itemView.findViewById(R.id.GroupName);
            textView2 = itemView.findViewById(R.id.LastActive);
        }
    }
}