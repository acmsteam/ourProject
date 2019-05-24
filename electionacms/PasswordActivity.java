package com.hfad.electionacms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.util.*;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import android.widget.Toast;
import android.util.Log;

public class PasswordActivity extends AppCompatActivity {

    String aadhar;
    EditText pwd1;
    EditText pwd2;
    String myString;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        pwd1=(EditText)findViewById(R.id.password);
        pwd2=(EditText)findViewById(R.id.re_password);
        Intent in = getIntent();
        aadhar = in.getStringExtra("aadhar");
        myString=in.getStringExtra("type_of_user");
    }
    public void check(View v){
        final String TAG = "PasswordActivity";
        String pw1=pwd1.getText().toString();
        String pw2=pwd2.getText().toString();
        if(pw1.equals(pw2)){
            Log.d(TAG,pw2);
            Map<String,String> pwd=new HashMap<>();
            pwd.put("Password",pw1);
            pwd.put("User_type",myString);
            db.collection("citizen").document(aadhar).set(pwd, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {

                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(PasswordActivity.this, "Your password is set! Registeration completed.Please login",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PasswordActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {

                            Toast.makeText(PasswordActivity.this, "Oops something went wrong. Try again!",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PasswordActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    });
        }
        else{
            Log.d(TAG,"password not matched");
            Toast.makeText(PasswordActivity.this, "Password mismatch,enter password again",Toast.LENGTH_SHORT).show();
            pwd1.setText("");
            pwd2.setText("");
        }
    }

    public void back(View v){
        finish();
    }
}

