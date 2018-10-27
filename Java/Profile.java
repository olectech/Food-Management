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
import com.squareup.picasso.Picasso;

/**
 * Created by solek on 21.10.2018.
 */

public class Profile extends AppCompatActivity implements View.OnClickListener {

    String useremail, username, userphoto;
    Uri url;

    ImageView profilephoto;

    FirebaseAuth mAuth;

    TextView u, e;

    Button dodajbtn;
    Button zawartosctbtn;
    Button listabtn;
    Button terminybtn;
    Button logoutbtn;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        mAuth = FirebaseAuth.getInstance();

        u = (TextView)findViewById(R.id.profileusername);
        e = (TextView)findViewById(R.id.profileuseremail);

        dodajbtn = (Button)findViewById(R.id.dodajbtn);
        zawartosctbtn = (Button)findViewById(R.id.zawartosctbtn);
        listabtn = (Button)findViewById(R.id.listabtn);
        terminybtn = (Button)findViewById(R.id.terminybtn);
        logoutbtn = (Button)findViewById(R.id.logoutbtn);

        profilephoto = (ImageView)findViewById(R.id.profilephoto) ;

        dodajbtn.setOnClickListener(this);
        zawartosctbtn.setOnClickListener(this);
        listabtn.setOnClickListener(this);
        terminybtn.setOnClickListener(this);
        logoutbtn.setOnClickListener(this);

        useremail = getIntent().getStringExtra("useremail");
        username = getIntent().getStringExtra("username");
        userphoto = getIntent().getStringExtra("url");
        url = Uri.parse(userphoto);
        u.setText(username);
        e.setText(useremail);
        //System.out.println(url);
        //Picasso.get().load(url).into(profilephoto);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dodajbtn:
                Intent dodaj = new Intent(this, MojaLodowka.class); // nowa intencja
                startActivity(dodaj);
                Toast.makeText(this,"Dodaj?", Toast.LENGTH_LONG).show();
                break;
            case R.id.zawartosctbtn:
                Toast.makeText(this,"Zawartosc?", Toast.LENGTH_LONG).show();
                break;
            case R.id.listabtn:
                Toast.makeText(this,"Lista?", Toast.LENGTH_LONG).show();
                break;
            case R.id.terminybtn:
                Toast.makeText(this,"Terminy?", Toast.LENGTH_LONG).show();
                break;
            case R.id.logoutbtn:
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
