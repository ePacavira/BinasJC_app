package ao.co.isptec.aplm.binasjc_app;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;
    private final List<ChatMessage> chatMessages;
    private TextView textView;


    public ChatAdapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @Override
    public int getItemViewType(int position) {
       if(chatMessages == null || chatMessages.isEmpty()){
           Log.e("ChatAdapter", "Chat messages list is null or empty");
           return -1;
       }
        boolean isSent = chatMessages.get(position).isSent();
        Log.d("ChatAdapter", "Position: " + position + ", isSent: " + isSent);
        return isSent ? VIEW_TYPE_SENT : VIEW_TYPE_RECEIVED;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            Log.d("ChatAdapter", "Creating sent message view holder");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_container_sent_message, parent, false);
            return new SentMessageViewHolder(view);
        } else if(viewType == VIEW_TYPE_RECEIVED){
            Log.d("ChatAdapter", "Creating received message view holder");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_container_received_message, parent, false);
            return new ReceivedMessageViewHolder(view);
        }else{
            Log.e("ChatAdapter", "Invalid view type: " + viewType);
            throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        if (holder instanceof SentMessageViewHolder) {
            ((SentMessageViewHolder) holder).bind(chatMessage);
        } else if (holder instanceof ReceivedMessageViewHolder) {
            ((ReceivedMessageViewHolder) holder).bind(chatMessage);
        }
    }

    @Override
    public int getItemCount() {
        Log.d("ChatAdapter", "Item count: " + (chatMessages != null ? chatMessages.size() : 0));
        return chatMessages != null ? chatMessages.size() : 0;
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView textMessage, textDateTime;

        public SentMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.textMessage);
            textDateTime = itemView.findViewById(R.id.textDateTime);
        }

        void bind(ChatMessage chatMessage) {
            textMessage.setText(chatMessage.getMessage());
            textDateTime.setText(chatMessage.getDateTime());
        }
    }

    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView textMessage, textDateTime;

        public ReceivedMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.textMessage);
            textDateTime = itemView.findViewById(R.id.textDateTime);
        }

        void bind(ChatMessage chatMessage) {
            textMessage.setText(chatMessage.getMessage());
            textDateTime.setText(chatMessage.getDateTime());
        }
    }
}

