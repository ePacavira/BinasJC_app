package ao.co.isptec.aplm.binasjc_app;

import static ao.co.isptec.aplm.binasjc_app.R.id.chatRecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class activity_chat extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages;
    private TextView contactNameTextView ;
    private EditText messageInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        contactNameTextView = findViewById(R.id.contact_name);
        messageInput = findViewById(R.id.messageInput);

        chatMessages = new ArrayList<>();

        //configurando o nome do contacto
        String contact_name = getIntent().getStringExtra("contact_name");
        if(contact_name != null){
            contactNameTextView.setText(contact_name);
        }


        // Adiciona  mensagens à lista
        chatMessages.add(new ChatMessage("10:30", "Olá Eunice Soleno, como estás?",true ));
        chatMessages.add(new ChatMessage("10:31", "Olá Eli, estou bem", true));

        chatAdapter = new ChatAdapter(chatMessages);
        chatRecyclerView.setAdapter(chatAdapter);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        for (ChatMessage msg : chatMessages) {
            Log.d("ActivityChat", "Message: " + msg.getMessage() + ", isSent: " + msg.isSent());
        }

        AppCompatImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_chat.this, activity_list_chat.class);
                startActivity(intent);

            }
        });

        // Ação do botão "Enviar"
        View btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(v -> sendMessage());

        
    }

    private void sendMessage() {
        String messageText = messageInput.getText().toString().trim();
        if (!messageText.isEmpty()) {
            chatMessages.add(new ChatMessage("Agora", messageText, true));
            chatAdapter.notifyItemInserted(chatMessages.size() - 1);

            // Limpar o campo de entrada e rolar para o fim
            messageInput.setText("");
            chatRecyclerView.scrollToPosition(chatMessages.size() - 1);
        }
    }
}