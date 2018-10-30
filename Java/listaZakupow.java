package pl.oltek.solek.foodmanagement;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class listaZakupow extends AppCompatActivity {

    ListView listViewProdukty;
    List<Produkt> produktList;

    DatabaseReference databaseProdukty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_zakupow);

        listViewProdukty = (ListView)findViewById(R.id.listViewProdukty);
        produktList = new ArrayList<>();

        String nazwaBazy = "NONAME";
        nazwaBazy = getIntent().getStringExtra("us").replace("@", "-").replace(".", "-");
        Toast.makeText(this, nazwaBazy, Toast.LENGTH_LONG).show();
        databaseProdukty = FirebaseDatabase.getInstance().getReference(nazwaBazy+"/Produkty");
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseProdukty.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produktList.clear();
                for(DataSnapshot produkty_snaphot : dataSnapshot.getChildren()){
                    Produkt produkt = produkty_snaphot.getValue(Produkt.class);

                    produktList.add(produkt);

                }
                ProduktyLista adapter = new ProduktyLista(listaZakupow.this, produktList);
                listViewProdukty.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}
