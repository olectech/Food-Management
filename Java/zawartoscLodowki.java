package pl.oltek.solek.foodmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class zawartoscLodowki extends AppCompatActivity {

    //DatabaseReference databaseProdukty;
    GridView gridview;

    String [] values = {
            "Piwo Tyskie",
            "Mleko czekoladowe 0.5L",
            "Szynka drobiowa",
            "Kabanos wieprzowy",
            "Ok5",
            "Ok6",
            "Ok7",
            "Ok8",
            "Ok9",
            "Ok10",
            "Ok1",
            "Ok2",
            "Ok3",
            "Ok4",
            "Ok5",
            "Ok6",
            "Ok7",
            "Ok8",
            "Ok9",
            "Ok10",
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


        GridAdapter gridAdapter = new GridAdapter(this, images, values);

        gridview.setAdapter(gridAdapter);
        //String nazwaBazy;
        //nazwaBazy = getIntent().getStringExtra("us").replace("@", "-").replace(".", "-");
        //Toast.makeText(this, nazwaBazy, Toast.LENGTH_LONG).show();
        //databaseProdukty = FirebaseDatabase.getInstance().getReference(nazwaBazy+"/Produkty");


    }

}
