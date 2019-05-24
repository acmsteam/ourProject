package com.hfad.electionacms;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class VoterProfileInformationActivity extends AppCompatActivity {

    String aadhar;
    EditText aadharNumber;
    EditText name;
    EditText father_name;
    EditText dob;
    EditText Mobile_no;
    EditText Hno;
    EditText Area;
    EditText District;
    EditText State;
    EditText Constituency;
    RadioButton male;
    RadioButton female;
    RadioButton others;
    EditText Street;
    EditText Gender;
    String myString;
    TextView final_text;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView im;
    StorageReference storageRef;
    FirebaseStorage storage;
    DocumentSnapshot doc;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_profile_information);

        im=(ImageView)findViewById(R.id.imageView);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        StorageReference pdfRef=storageRef.child("elections/images/party_symbol/bjplogo.png");

// Download directly from StorageReference using Glide
//// (See MyAppGlideModule for Loader registration)
        Glide.with(this )
                .load("https://firebasestorage.googleapis.com/v0/b/elections-37945.appspot.com/o/elections%2Fimages%2Fparty_symbol%2Fcongress.jpg?alt=media&token=d01fa0f3-9dfc-446b-aabb-4afd4bc18ce8")
                .into(im);


        boolean flag=false;
        final_text=(TextView)findViewById(R.id.final_result);
        Intent in = getIntent();
        aadhar = in.getStringExtra("Ano");
        myString=in.getStringExtra("type_of_user");
        System.out.println("++++++++++"+aadhar);
        DocumentReference user = db.collection("citizen").document(aadhar);
        user.get().addOnCompleteListener(new OnCompleteListener < DocumentSnapshot > () {
            @Override
            public void onComplete( Task < DocumentSnapshot > task) {
                if (task.isSuccessful()) {
                    doc = task.getResult();
                    if (doc.exists()) {
                        autofill();
                    }
                    else{
                        Toast.makeText(VoterProfileInformationActivity.this, "incorrect aadhar",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(VoterProfileInformationActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }}})
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {

                        Toast.makeText(VoterProfileInformationActivity.this, "Oops something went wrong",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(VoterProfileInformationActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    public void autofill(){
        setContentView(R.layout.activity_voter_profile_information);
        //  TextView text =(TextView) findViewById(R.id.link);
        aadharNumber = (EditText) findViewById(R.id.adhaarNumber);
        name = (EditText) findViewById(R.id.name);
        father_name = (EditText) findViewById(R.id.father_name);
        dob = (EditText) findViewById(R.id.dob);
        Mobile_no = (EditText) findViewById(R.id.Mobile_no);
        Hno = (EditText) findViewById(R.id.Hno);
        Area = (EditText) findViewById(R.id.Area);
        District = (EditText) findViewById(R.id.District);
        State = (EditText) findViewById(R.id.State);
        Constituency = (EditText) findViewById(R.id.Constituency);
        Street = (EditText) findViewById(R.id.Street);
        Gender = (EditText) findViewById(R.id.Gender);
        aadharNumber.setText((Long) doc.get("Adharcard_number") + "");
        Area.setText((String) doc.get("Area"));
        Constituency.setText((String) doc.get("Assembly_constituency"));
        dob.setText((String) doc.get("DOB"));
        District.setText((String) doc.get("District_name"));
        Hno.setText((String) doc.get("H_NO"));
        father_name.setText((String) doc.get("Husband_Father name"));
        name.setText((String) doc.get("Name"));
        Mobile_no.setText((Long) doc.get("Mobile_number") + " ");
        State.setText((String) doc.get("State"));
        if (((String) doc.get("Gender")).equals("Female")) {
            Gender.setText("F");
        } else if (((String) doc.get("Gender")).equals("Male")) {
            Gender.setText("M");
        } else {
            Gender.setText("Other");;
        }
        Street.setText((String) doc.get("Street"));
    }
}



