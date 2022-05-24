package be.kuleuven.binker;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.appevents.ml.Utils;

public class RecyclerAdapterChat{

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_chat_user_other);
            timeText = (TextView) itemView.findViewById(R.id.text_chat_timestamp_other);
            nameText = (TextView) itemView.findViewById(R.id.text_chat_message_other);
            profileImage = (ImageView) itemView.findViewById(R.id.image_chat_profile_other);
        }

        void bind(UserMessage message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(Utils.formatDateTime(message.getCreatedAt()));
            nameText.setText(message.getSender().getNickname());

            // Insert the profile image from the URL into the ImageView.
            Utils.displayRoundImageFromUrl(context, message.getSender().getProfileUrl(), profileImage);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_chat_message_me);
            timeText = (TextView) itemView.findViewById(R.id.text_chat_timestamp_me);
        }

        void bind(UserMessage message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(Utils.formatDateTime(message.getCreatedAt()));
        }
    }

}
