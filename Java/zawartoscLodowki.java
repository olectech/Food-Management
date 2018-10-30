package pl.oltek.solek.foodmanagement;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class zawartoscLodowki extends AppCompatActivity {

    ArrayList<Produkt> produktArrayList;


    DatabaseReference databaseProdukty;
    GridView gridview;

    String [] values = {
            "Piwo Tyskie",
            "Mleko czekoladowe 0.5L",
            "Szynka drobiowa",
            "Kabanos wieprzowy",
            "Parówki",
            "Pepsi",
            "Pasztet drobiowy",
            "Ser żółty",
            "Serek wiejski",
            "Kiełbasa żywiecka",
            "Pomidory",
            "Pasztet pieczony",
            "Jogurt",
            "Majonez",
            "Papryka",
            "Musztarda",
            "Jajka",
            "Rzodkiewka",
            "Salami",
            "Salceson",
    };

    int[] images = {
            R.drawable.ok,
            R.drawable.ok,
            R.drawable.ok,
            R.drawable.ok,
            R.drawable.ok,
            R.drawable.ok,
            R.drawable.ok,
            R.drawable.ok,
            R.drawable.ok,
            R.drawable.ok,
            R.drawable.ok,
            R.drawable.ok,
            R.drawable.ok,
            R.drawable.ok,
            R.drawable.ok,
            R.drawable.ok,
            R.drawable.ok,
            R.drawable.ok,
            R.drawable.ok,
            R.drawable.ok,
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zawartosc_lodowki);

        gridview = (GridView)findViewById(R.id.gridview);

        produktArrayList = new ArrayList<>();

        GridAdapter gridAdapter = new GridAdapter(this, images, values);

        gridview.setAdapter(gridAdapter);
        String nazwaBazy;
        nazwaBazy = getIntent().getStringExtra("us").replace("@", "-").replace(".", "-");
        //Toast.makeText(this, nazwaBazy, Toast.LENGTH_LONG).show();
        databaseProdukty = FirebaseDatabase.getInstance().getReference(nazwaBazy+"/Produkty");

        databaseProdukty.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produktArrayList.clear();
                for(DataSnapshot produkty_snaphot : dataSnapshot.getChildren()){
                    Produkt produkt = produkty_snaphot.getValue(Produkt.class);

                    produktArrayList.add(produkt);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }

}
