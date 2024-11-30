package ao.co.isptec.aplm.binasjc_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class activity_list_chat extends AppCompatActivity {
    private ListView contactListview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        contactListview = findViewById(R.id.ContactList);

        //Simulando um conjunto de Contactos
        List<String> contacts = new ArrayList<>();
        contacts.add("Eunice Soleno");
        contacts.add("Eliane Watele");
        contacts.add("Emanuel Pacavira");

        //Criando um adaptador para a listview
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        contactListview.setAdapter(adapter);

        // Definindo o Listener para os cliques
        contactListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtém o contato clicado
                String clickedContact = (String) parent.getItemAtPosition(position);

                // Cria uma Intent para abrir outra atividade (exemplo)
                Intent intent = new Intent(activity_list_chat.this, activity_chat.class);
                intent.putExtra("contact_name", clickedContact);
                startActivity(intent);
                }
        });
    }

    }

