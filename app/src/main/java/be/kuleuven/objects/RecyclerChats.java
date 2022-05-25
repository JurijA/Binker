import be.kuleuven.binker.R;

public class RecyclerAdapterChats extends RecyclerView.Adapter<RecyclerAdapterChats.MessageViewHolder> {

    private final Friendship friendship;
    private final Context context;
    private final List<ChatMessage> chatMessageList;


    public RecyclerAdapterChats(Friendship friendship, List<ChatMessage> messageList, Context context) {
        this.context = context;
        this.friendship = friendship;
        this.chatMessageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessageList.get(position);
        holder.message.setText(chatMessage.getMessage());
        holder.day.setText(chatMessage.getDay());
        holder.time.setText(chatMessage.getTime());

    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }


    class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView message, day, time;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.text_chat_message_me);
            time = itemView.findViewById(R.id.text_chat_timestamp_me);
            day = itemView.findViewById(R.id.text_chat_date_me);

        }

    }
}