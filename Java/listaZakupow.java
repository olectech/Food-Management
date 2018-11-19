package pl.oltek.solek.foodmanagement;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
        adapter = ArrayAdapter.createFromResource(this, R.array.KategorieSortowanie, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSortowanie.setAdapter(adapter);
        spinnerSortowanie.setOnItemSelectedListener(this);

        String nazwaBazy = "NONAME";
        nazwaBazy = getIntent().getStringExtra("us").replace("@", "-").replace(".", "-");
        //Toast.makeText(this, nazwaBazy, Toast.LENGTH_LONG).show();
        databaseProdukty = FirebaseDatabase.getInstance().getReference(nazwaBazy+"/Produkty");
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void updateProdukt(String produktId, String produktNazwa, String produktData, String produkIlosc, String produktKategoria, String produktIloscMin, String produktJednostka){

        Produkt produkt = new Produkt(produktId, produktNazwa, produktData, produkIlosc, produktKategoria, produktIloscMin, produktJednostka);
        databaseProdukty.child(produktId).setValue(produkt);

    }

    private void usunProdukt(String produktId){

        databaseProdukty.child(produktId).removeValue();

    }

private void pokazOkienko(final String produktId, final String produktNazwa, final String produktData, final String produkIlosc, final String produktKategoria, final String produktIloscMin, final String produktJednostka){

    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
    LayoutInflater inflater = getLayoutInflater();
    final View dialogView = inflater.inflate(R.layout.updatedialog, null);
    dialogBuilder.setView(dialogView);

    final EditText editTextDialog = (EditText) dialogView.findViewById(R.id.editTextDialog);
    final Button buttonOkDialog = (Button) dialogView.findViewById(R.id.buttonOkDialog);
    final Button buttonUsunDialog = (Button)dialogView.findViewById(R.id.buttonUsunDialog);

    buttonOkDialog.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String ilosc = editTextDialog.getText().toString().trim();
            updateProdukt(produktId, produktNazwa, produktData, ilosc, produktKategoria, produktIloscMin, produktJednostka);
        }
    });

    buttonUsunDialog.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            usunProdukt(produktId);
        }
    });

    dialogBuilder.setTitle("Edytuj: "+produktNazwa);

    AlertDialog alertDialog = dialogBuilder.create();
    alertDialog.show();

}
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        listViewProdukty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Produkt idProdukt = produktList.get(i);
                //idProdukt.produktIlosc = "666";
                //databaseProdukty.child(idProdukt.produktId).setValue(idProdukt);
                //System.out.println(idProdukt.produktNazwa);

                pokazOkienko(idProdukt.produktId, idProdukt.produktNazwa, idProdukt.produktData, idProdukt.produktIlosc, idProdukt.produktKategoria, idProdukt.produktIloscMin, idProdukt.produktJednostka);

            }
        });
        if(text.equals("Wszystko")){
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
        else{
            databaseProdukty.orderByChild("produktKategoria").equalTo(text).addValueEventListener(new ValueEventListener() {
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

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
