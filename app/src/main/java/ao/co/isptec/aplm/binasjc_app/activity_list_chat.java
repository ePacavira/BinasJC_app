package ao.co.isptec.aplm.binasjc_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class activity_list_chat extends AppCompatActivity {
    private RecyclerView contactRecyclerView;
    private activity_contact_adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        // Inicializar RecyclerView
        contactRecyclerView = findViewById(R.id.ContactList);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Simulando um conjunto de Contactos
        List<String> contacts = new ArrayList<>();
        contacts.add("Eunice Soleno");
        contacts.add("Eliane Watele");
        contacts.add("Emanuel Pacavira");

        // Criando um adaptador para a RecyclerView
        adapter = new activity_contact_adapter(contacts, this);
        contactRecyclerView.setAdapter(adapter);

        // botão de voltar
        ConstraintLayout btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(activity_list_chat.this, activity_main.class);
            startActivity(intent);
            finish(); // Opcional: fecha a atividade atual
        });
    }
}

class activity_contact_adapter extends RecyclerView.Adapter<ContactViewHolder> {
    private List<String> contactList;
    private activity_list_chat context;

    public activity_contact_adapter(List<String> contactList, activity_list_chat context) {
        this.contactList = contactList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Usar layout personalizado em vez de layout padrão do Android
        TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ContactViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        String contactName = contactList.get(position);
        holder.contactNameTextView.setText(contactName);

        holder.itemView.setOnClickListener(v -> {
            // Abre a atividade de chat ao clicar no item
            Intent intent = new Intent(context, activity_chat.class);
            intent.putExtra("contact_name", contactName);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
}

class ContactViewHolder extends RecyclerView.ViewHolder {
    TextView contactNameTextView;

    public ContactViewHolder(@NonNull TextView itemView) {
        super(itemView);
        this.contactNameTextView = itemView;
    }
}