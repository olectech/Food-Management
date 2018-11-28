package pl.oltek.solek.foodmanagement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by solek on 21.10.2018.
 */

public class Profile extends AppCompatActivity implements View.OnClickListener {

    String useremail, username, userphoto; // wyswietlanie info o profilu zmienne
    Uri url;
    TextView userid;

    ImageView profilephoto;

    FirebaseAuth mAuth; // autoryzacja firebase

    TextView u, e; // wyswietlanie info o profilu textview
//deklaracja przycisków
    Button dodajbtn;
    Button zawartosctbtn;
    Button listabtn;
    Button terminybtn;
    Button logoutbtn;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        mAuth = FirebaseAuth.getInstance();
// przyłączenie textview z layoutu
        u = (TextView)findViewById(R.id.profileusername);
        e = (TextView)findViewById(R.id.profileuseremail);
// przyłączenie buttonów
        dodajbtn = (Button)findViewById(R.id.dodajbtn);
        zawartosctbtn = (Button)findViewById(R.id.zawartosctbtn);
        listabtn = (Button)findViewById(R.id.listabtn);
        terminybtn = (Button)findViewById(R.id.terminybtn);
        logoutbtn = (Button)findViewById(R.id.logoutbtn);
// przyłączenie zdjęcia profilowego
        profilephoto = (ImageView)findViewById(R.id.profilephoto) ;
// onClick buttony
        dodajbtn.setOnClickListener(this);
        zawartosctbtn.setOnClickListener(this);
        listabtn.setOnClickListener(this);
        terminybtn.setOnClickListener(this);
        logoutbtn.setOnClickListener(this);
// pobranie danych z poprzedniej intencji nazwa, mail, adres zdjecia profilowego
        useremail = getIntent().getStringExtra("useremail");
        username = getIntent().getStringExtra("username");
        userphoto = getIntent().getStringExtra("url");
        url = Uri.parse(userphoto);
        u.setText("Nazwa użytkownika: "+"\t"+username);
        e.setText("E-mail: "+"\t"+useremail);
        //System.out.println(url);
        //Picasso.get().load(url).into(profilephoto);
        userid = (TextView)findViewById(R.id.userid);
        userid.setText("Nazwa bazy: "+"\t"+useremail.replace("@", "-").replace(".", "-"));
    }

    @Override
    public void onClick(View view) { // wybór akcji w zależności od naciśniętego buttona
        switch (view.getId()) {
            case R.id.dodajbtn:
                Intent dodaj = new Intent(this, MojaLodowka.class); // nowa intencja dodawanie produktów do bazy
                dodaj.putExtra("us", useremail);
                startActivity(dodaj);
                //Toast.makeText(this,"Dodaj?", Toast.LENGTH_LONG).show();
                break;
            case R.id.zawartosctbtn:
                Intent zawartosc = new Intent(this, zawartoscLodowki.class); // nowa intencja wykaz produktów w lodowce
                zawartosc.putExtra("us", useremail);
                startActivity(zawartosc);
                //Toast.makeText(this,"Zawartosc?", Toast.LENGTH_LONG).show();
                break;
            case R.id.listabtn:
                Intent lista = new Intent(this, listaZakupow.class); // nowa intencja lista zakupów
                lista.putExtra("us", useremail);
                startActivity(lista);
                //Toast.makeText(this,"Lista?", Toast.LENGTH_LONG).show();
                break;
            case R.id.terminybtn:
                Intent terminy = new Intent(this, terminyWaznosci.class); // nowa intencja terminy wazności
                terminy.putExtra("us", useremail);
                startActivity(terminy);
                //Toast.makeText(this,"Terminy?", Toast.LENGTH_LONG).show();
                break;
            case R.id.logoutbtn: // wylogowanie z aplikacji
                mAuth.signOut();
                FirebaseAuth.getInstance().signOut();
                Intent logout = new Intent(this, MainActivity.class);
                startActivity(logout);
                break;
        }
    }
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null) {
           Intent intent = new Intent(this, MainActivity.class);
           startActivity(intent);
        }
    }
}
