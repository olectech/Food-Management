package pl.oltek.solek.foodmanagement;

import android.content.Intent;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class terminyWaznosci extends AppCompatActivity {

    ListView listViewProduktyTerminy;
    List<Produkt> produktListTerminy;
    //Spinner spinnerSortowanieTerminy;
    //ArrayAdapter<CharSequence> adapterTerminy;

    DatabaseReference databaseProdukty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminy_waznosci);

        listViewProduktyTerminy = (ListView)findViewById(R.id.listViewProduktyTerminy);
        produktListTerminy = new ArrayList<>();

        //spinnerSortowanieTerminy = (Spinner)findViewById(R.id.spinnerSortowanieTerminy);
        //adapterTerminy = ArrayAdapter.createFromResource(this, R.array.KategorieSortowanie, android.R.layout.simple_spinner_item);
        //adapterTerminy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinnerSortowanieTerminy.setAdapter(adapterTerminy);
        //spinnerSortowanieTerminy.setOnItemSelectedListener(this);

        String nazwaBazy = "NONAME";
        nazwaBazy = getIntent().getStringExtra("us").replace("@", "-").replace(".", "-");
        Toast.makeText(this, nazwaBazy, Toast.LENGTH_LONG).show();
        databaseProdukty = FirebaseDatabase.getInstance().getReference(nazwaBazy+"/Produkty");
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseProdukty.orderByChild("produktData").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produktListTerminy.clear();
                for(DataSnapshot produkty_snaphot : dataSnapshot.getChildren()){
                    Produkt produkt = produkty_snaphot.getValue(Produkt.class);

                    String data = produkt.produktData;
                    String rok = data.substring(0,4);
                    String miesiac = data.substring(5,7);
                    String dzien = data.substring(8);

                    int a = Integer.parseInt(rok);
                    int b = Integer.parseInt(miesiac);
                    int c = Integer.parseInt(dzien);

                    Calendar thatDay = Calendar.getInstance();
                    thatDay.set(Calendar.DAY_OF_MONTH, c);
                    thatDay.set(Calendar.MONTH,b-1); // 0-11 so 1 less
                    thatDay.set(Calendar.YEAR, a);

                    Calendar today = Calendar.getInstance();

                    long diff = thatDay.getTimeInMillis() - today.getTimeInMillis(); //result in millis

                    long days = diff / (24 * 60 * 60 * 1000);
                    int wynik = (int)days;
                    if(wynik<0){
                        produkt.produktData = data+" Przeterminowane "+wynik+" dni.";
                    }
                    else if(wynik==0){
                        produkt.produktData = data+" (Dzisiaj mija termin!)";
                    }
                    else{
                        produkt.produktData = data+" (Pozostało "+wynik+" dni.)";
                    }
                    produktListTerminy.add(produkt);

                }
                ProduktyLista adapter = new ProduktyLista(terminyWaznosci.this, produktListTerminy);
                listViewProduktyTerminy.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}
