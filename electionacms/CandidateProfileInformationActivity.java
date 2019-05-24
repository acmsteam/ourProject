package com.hfad.electionacms;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.ProcessingInstruction;

import java.io.File;
import java.io.IOException;

public class CandidateProfileInformationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static TextView tv1;
    static TextView tv2;
    static TextView tv3;
    static TextView tv4;
    static TextView tv5;
    static TextView tv6;
    static ImageView iv;
    String Adh=LoginActivity.aadhar;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_candidate_profile_information );
        System.out.println("aadhar is:"+Adh);
        Toolbar toolbar = findViewById( R.id.toolbar );
        iv=findViewById( R.id.ivp );
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://elections-37945.appspot.com/elections/images/candidates/"+Adh+".jpg");
        try {
            final File localFile = File.createTempFile( "img","jpg" );
            mStorageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    iv.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e ) {
        }
        tv1=findViewById( R.id.na);
        tv2=findViewById( R.id.An);
        tv3=findViewById( R.id.partyname);
        tv4=findViewById( R.id.con);
        tv5=findViewById( R.id.pno);
        tv6=findViewById( R.id.sno);
        Bundle extras=getIntent().getExtras();
        /*tv1.setText("Name:"+extras.getString("name"));
        tv2.setText("Aadhaar number:"+ Adh);
        tv3.setText("Party name:"+CandidateProfileInformationActivity.partyname);
        tv4.setText("Constituency:"+CandidateProfileInformationActivity.constituency);
        tv5.setText("PNO:"+CandidateProfileInformationActivity.partyno);
        tv6.setText("SINO:"+CandidateProfileInformationActivity.sino);*/

        setSupportActionBar( toolbar );
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        NavigationView navigationView = findViewById( R.id.nav_view );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.hom) {
            Intent intent=new Intent();
            intent.setClassName(CandidateProfileInformationActivity.this,"com.hfad.electionacms.CandidateProfileActivity");
            startActivity(intent);
        }else if (id == R.id.cl) {
            Intent intent=new Intent();
            intent.setClassName(CandidateProfileInformationActivity.this,"com.hfad.electionacms.CandidateListActivity");
            startActivity(intent);
        } else if (id == R.id.log) {
            Intent intent = new Intent();
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            intent.putExtra( "EXIT", true );
            intent.setClassName(CandidateProfileInformationActivity.this, "com.hfad.electionacms.LoginActivity" );
            startActivity( intent );
        }
        else if (id == R.id.can) {
            Intent intent=new Intent();
            intent.setClassName(CandidateProfileInformationActivity.this,"com.hfad.electionacms.CandidatePromisesActivity");
            startActivity(intent);
        }
        else if(id ==R.id.stas){
            Intent intent=new Intent( );
            intent.setClassName( CandidateProfileInformationActivity.this,"com.hfad.electionacms.StatisticsIPAddressActivity" );
            startActivity(intent);
        }
        else if (id == R.id.abu) {
            Intent intent=new Intent();
            intent.setClassName(CandidateProfileInformationActivity.this,"com.hfad.electionacms.AboutUsActivity");
            startActivity(intent);
        }
        else if (id == R.id.prof) {
            Intent intent=new Intent();
            intent.setClassName(CandidateProfileInformationActivity.this,"com.hfad.electionacms.CandidateProfileInformationActivity");
            startActivity(intent);
        }
        else if (id == R.id.rul) {
            Intent intent=new Intent();
            intent.setClassName(CandidateProfileInformationActivity.this,"com.hfad.electionacms.RulesIPAddressActivity");
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
}
