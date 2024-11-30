package ao.co.isptec.aplm.binasjc_app;

import static ao.co.isptec.aplm.binasjc_app.R.id.chatRecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private TextView textView ;
    private String contact_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatMessages = new ArrayList<>();

        contact_name = getIntent().getStringExtra("contact_name");
        //Exibir o nome do contacto
        textView =findViewById(R.id.contact_name);
        textView.setText(contact_name);


        // Adiciona duas mensagens à lista
        chatMessages.add(new ChatMessage("10:30", "Olá Eunice Soleno, como estás?", false));
        chatMessages.add(new ChatMessage("10:31", "Olá Eli, estou bem", true));

        chatAdapter = new ChatAdapter(chatMessages);
        chatRecyclerView.setAdapter(chatAdapter);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        AppCompatImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_chat.this, activity_list_chat.class);
                startActivity(intent);

                /*if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }*/

            }
        });
    }
}