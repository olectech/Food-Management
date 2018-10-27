package pl.oltek.solek.foodmanagement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    Button logoutbtn;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        mAuth = FirebaseAuth.getInstance();

        u = (TextView)findViewById(R.id.profileusername);
        e = (TextView)findViewById(R.id.profileuseremail);
        logoutbtn = (Button)findViewById(R.id.logoutbtn);
        profilephoto = (ImageView)findViewById(R.id.profilephoto) ;

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
        mAuth.signOut();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
