package pl.oltek.solek.foodmanagement;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class MojaLodowka extends AppCompatActivity {
    EditText editTextIloscProduktu;
    EditText editTextIloscMinProduktu;
    EditText editTextNazwaProduktu;
//EditText editTextDataWaznosci;
Button buttonDodajProdukt;
CalendarView kalendarz;
Spinner spinner;
Spinner spinnerJednostka;
ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter2;
DatabaseReference databaseProdukty;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moja_lodowka);

        spinner = (Spinner)findViewById(R.id.SpinnerKategorie);
        adapter = ArrayAdapter.createFromResource(this, R.array.Kategorie, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinnerJednostka = (Spinner)findViewById(R.id.spinnerJednostka);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.Jednostki, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJednostka.setAdapter(adapter2);

        String nazwaBazy = "NONAME";
        nazwaBazy = getIntent().getStringExtra("us").replace("@", "-").replace(".", "-");
        Toast.makeText(this, nazwaBazy, Toast.LENGTH_LONG).show();
        databaseProdukty = FirebaseDatabase.getInstance().getReference(nazwaBazy+"/Produkty");

        //editTextDataWaznosci = (EditText)findViewById(R.id.editTextDataWaznosci);
        editTextNazwaProduktu = (EditText)findViewById(R.id.editTextNazwaProduktu);
        editTextIloscMinProduktu = (EditText)findViewById(R.id.editTextIloscMin);
        editTextIloscProduktu = (EditText)findViewById(R.id.editTextIlosc);

        buttonDodajProdukt = (Button)findViewById(R.id.buttonDodajProdukt);
        kalendarz = (CalendarView)findViewById(R.id.kalendarz);
        kalendarz.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                data(i, i1, i2);
            }
        });
        buttonDodajProdukt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wprowadzProdukt();
            }
        });
    }

    String produktData; // tymczasowa data
    boolean wybranadata = false;

    public void data(int rok, int miesiac, int dzien){
        String data = String.valueOf(rok)+"/"+String.valueOf(miesiac+1)+"/"+String.valueOf(dzien);
        produktData = data;
        wybranadata = true;
        //System.out.println(data);
    }

    public void wprowadzProdukt(){
        String nazwaProduktu = editTextNazwaProduktu.getText().toString().trim();
        String produktIlosc = editTextIloscProduktu.getText().toString().trim();
        String produktKategoria = spinner.getSelectedItem().toString();
        String produktIloscMin = editTextIloscMinProduktu.getText().toString().trim();
        String produktJednostka = spinnerJednostka.getSelectedItem().toString();

        if(!TextUtils.isEmpty(nazwaProduktu)&&wybranadata==true){
        String id = databaseProdukty.push().getKey();

        Produkt produkt = new Produkt(id, nazwaProduktu, produktData, produktIlosc, produktKategoria, produktIloscMin, produktJednostka);
        databaseProdukty.child(id).setValue(produkt);
        Toast.makeText(this, "Dodano produkt!", Toast.LENGTH_LONG).show();

        }else if(wybranadata==false){
            Toast.makeText(this, "Wybierz datę ważności.", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Wprowadź nazwę produktu.", Toast.LENGTH_LONG).show();
        }
    }
}
