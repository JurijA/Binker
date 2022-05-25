package be.kuleuven.objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.kuleuven.binker.R;

public class RecyclerAdapterChat extends RecyclerView.Adapter {

    private static final Integer SENT = 1;
    private static final Integer RECEIVED = 2;
    private final Friendship friendship;
    private final Context context;
    private final List<ChatMessage> chatMessageList;

    public RecyclerAdapterChat(Friendship friendship, List<ChatMessage> messageList, Context context) {
        this.context = context;
        this.friendship = friendship;
        this.chatMessageList = messageList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.me_message, parent, false);
            return new MeMessageViewHolder(view);
        } else if (viewType == RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.friend_message, parent, false);
            return new FriendMessageViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = (ChatMessage) chatMessageList.get(position);
        switch (holder.getItemViewType()) {
            case SENT:
                ((MeMessageViewHolder) holder).bind(message);
                break;
            case RECEIVED:
                ((FriendMessageViewHolder) holder).bind(message);
            default:
                System.out.println("oeoe");
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = chatMessageList.get(position);

        if (message.getUser().equals(friendship.getA())) {
            return SENT;
        } else {
            return RECEIVED;
        }
    }


    private class MeMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, hourAndMinuteText, dayAndMonthText;

        MeMessageViewHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.text_chat_message_me);
            dayAndMonthText = itemView.findViewById(R.id.text_chat_date_me);
            hourAndMinuteText = itemView.findViewById(R.id.text_chat_timestamp_me);

        }

        void bind(ChatMessage message) {
            messageText.setText(message.getMessage());
            dayAndMonthText.setText(message.getMonthAndDay());
            hourAndMinuteText.setText(message.getHourAndMinute());
        }
    }

    private class FriendMessageViewHolder extends RecyclerView.ViewHolder {

        TextView messageText, dayAndMonthText, hourAndMinuteText;


        public FriendMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_chat_message_me);
            dayAndMonthText = itemView.findViewById(R.id.text_chat_date_me);
            hourAndMinuteText = itemView.findViewById(R.id.text_chat_timestamp_me);

        }

        void bind(ChatMessage message) {
            messageText.setText(message.getMessage());

            dayAndMonthText.setText(message.getMonthAndDay());
            hourAndMinuteText.setText(message.getHourAndMinute());

        }

    }
}