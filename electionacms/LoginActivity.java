package com.hfad.electionacms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    public static String name;
    private static String LOG_TAG ="";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String aadhar="";
    public static String Adharcard_number="";
    private Button login_button,reg_button;
    EditText username;
    EditText getaadhar;
    EditText password;
    EditText et;
    Spinner spinner;
    String temp="";
    DocumentSnapshot doc;
    DocumentReference noteRef=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById( R.id.enter1 );
        password = (EditText) findViewById( R.id.enter2);
        getaadhar = (EditText) findViewById( R.id.enter_adhar_for_reg);

        login_button = (Button) findViewById(R.id.login_button);
        reg_button = (Button) findViewById(R.id.reg_button);


        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();

            }
        });

    }


    public void check(){
        DocumentReference user = db.collection("citizen").document(getaadhar.getText().toString());
        user.get().addOnCompleteListener(new OnCompleteListener< DocumentSnapshot >() {
            @Override
            public void onComplete( Task< DocumentSnapshot > task) {
                if (task.isSuccessful()) {
                    doc = task.getResult();
                    if (doc.exists()) {

                        Intent in = new Intent(getApplicationContext(), OTPActivity.class);
                        spinner = (Spinner)findViewById(R.id.spinner_voter_candidate);
                        final String myString = String.valueOf(spinner.getSelectedItem());
                        in.putExtra("aadhar", getaadhar.getText().toString());
                        System.out.println("Original spinner value is: "+myString);

                        if(myString.equals("मतदाता") || myString.equals("Voter")){
                            temp="Voter";
                        } else if(myString.equals("उम्मीदवार") || myString.equals("Candidate")){
                            temp="Candidate";
                        }
                        System.out.println("temp value:"+temp);
                        in.putExtra("type_of_user",temp);
                        startActivity(in);

                    }
                    else{
                        Toast.makeText(LoginActivity.this, "incorrect aadhar",Toast.LENGTH_SHORT).show();
                    }
                }}})
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {

                        Toast.makeText(LoginActivity.this, "Oops something went wrong",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    public void Read(View v)
    {
        //Candidate-Login
        spinner = (Spinner)findViewById(R.id.spinner_voter_candidate);
        final String myString = String.valueOf(spinner.getSelectedItem());

        System.out.println("Original spinner value is: "+myString);

        if(myString.equals("मतदाता") || myString.equals("Voter")){
            temp="Voter";
        } else if(myString.equals("उम्मीदवार") || myString.equals("Candidate")){
            temp="Candidate";
        }
        System.out.println("**********************************************************I am here! I am here! I am here! I am here! I am here! I am here! ");
        System.out.println("Spinner value====="+temp);
        if(temp.equals("Candidate")){
            aadhar = username.getText().toString().trim();
            noteRef = db.document("Candidate/"+aadhar);
            noteRef.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            System.out.println("here 1");
                            if (documentSnapshot.exists()) {
                                System.out.println("here 2");
                                String name = documentSnapshot.getString("Name");
                                String partyname = documentSnapshot.getString("party_name");
                                String constituency = documentSnapshot.getString("Assembly_Constituency");
                                String partyno = documentSnapshot.getString("Part_NO");
                                String sino = documentSnapshot.getString("SI_NO");
                                Intent intent = new Intent(LoginActivity.this, CandidateProfileActivity.class);
                                Bundle extras = new Bundle();
                                extras.putString("name",name);
                                extras.putString("partyname",partyname);
                                extras.putString("constituency",constituency);
                                extras.putString("partyno",partyno);
                                extras.putString("sino",sino);
                                intent.putExtras(extras);
                                startActivity(intent);



                                Log.d("name",name);
                                Log.d("partyname",partyname);


                            } else {
                                Log.d("error","errpr");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            //Log.d(TAG, e.toString());
                        }
                    });
        }
        else{
            System.out.println("You are a voter!");
            et = (EditText) findViewById( R.id.enter1 );
            Adharcard_number=et.getText().toString().trim();
            noteRef = db.document("citizen/"+Adharcard_number);
            noteRef.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                String Name = documentSnapshot.getString( "Name" );
                                String Area = documentSnapshot.getString( "Area" );
                                String Assembly_constituency= documentSnapshot.getString( "Assembly_constituency" );
                                System.out.println("NAME:"+Name);
                                System.out.println("Area:"+Area);
                                System.out.println("Assembly_constituency:"+Assembly_constituency);
                                Intent intent = new Intent( LoginActivity.this, VoterProfileActivity.class );
                                Bundle extras = new Bundle();
                                //extras.putString("Name", Name );
                                //extras.putString("Area", Area );
                                //extras.putString("Assembly_constituency",Assembly_constituency );
                                intent.putExtra("Name",Name);
                                //intent.putExtra("Area",Area);
                                intent.putExtra("Assembly_Constituency",Assembly_constituency);
                                intent.putExtra("Ano",Adharcard_number);
                                spinner = (Spinner)findViewById(R.id.spinner_voter_candidate);
                                final String myString = String.valueOf(spinner.getSelectedItem());
                                if(myString.equals("मतदाता") || myString.equals("Voter")){
                                    temp="Voter";
                                } else if(myString.equals("उम्मीदवार") || myString.equals("Candidate")){
                                    temp="Candidate";
                                }
                                intent.putExtra("type_of_user",temp);
                                intent.putExtra("aadhar",Adharcard_number);
                                startActivity( intent );
                                Log.d("name",Name);

                            } else {
                                Log.d("eop","errpr");
                            }
                        }


                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            //Log.d(TAG, e.toString());
                        }
                    });
        }


        /*Intent intent=new Intent(getApplicationContext(),HelloWorldActivity.class);

        startActivity(intent);*/
    }
}
