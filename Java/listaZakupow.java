package pl.oltek.solek.foodmanagement;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class listaZakupow extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ListView listViewProdukty;
    List<Produkt> produktList;
    Spinner spinnerSortowanie;
    ArrayAdapter<CharSequence> adapter;

    DatabaseReference databaseProdukty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_zakupow);

        listViewProdukty = (ListView)findViewById(R.id.listViewProdukty);
        produktList = new ArrayList<>();

        spinnerSortowanie = (Spinner)findViewById(R.id.spinnerSortowanie);
        adapter = ArrayAdapter.createFromResource(this, R.array.Sortowanie, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSortowanie.setAdapter(adapter);
        spinnerSortowanie.setOnItemSelectedListener(this);

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
