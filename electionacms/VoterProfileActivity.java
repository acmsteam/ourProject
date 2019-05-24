package com.hfad.electionacms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class VoterProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static TextView tv1;
    static TextView tv2;
    static String Ano=LoginActivity.Adharcard_number;
    static  String Name;
    static  String Area;
    static  String Assembly_constituency;
    Intent intent;
    GlobalsActivity g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_voter_profile );

        tv1=(TextView)findViewById(R.id.nam);
        tv2=(TextView)findViewById( R.id.aad );
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            Name=extras.getString("Name");
            Area= extras.getString( "Area" );
            Assembly_constituency= extras.getString( "Assembly_constituency" );
            tv1.setText(Name);
            tv2.setText(Ano);
        }
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            moveTaskToBack( true );
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.hom) {
            Intent intent=new Intent();
            intent.setClassName(VoterProfileActivity.this,"com.hfad.electionacms.LoginActivity");
            startActivity(intent);
        } else if (id == R.id.CL) {
            Intent intent=new Intent();
            intent.setClassName(VoterProfileActivity.this,"com.hfad.electionacms.CandidateListActivity");
            startActivity(intent);
        } else if (id == R.id.Stats) {
            Intent intent=new Intent();
            intent.setClassName(VoterProfileActivity.this,"com.hfad.electionacms.StatisticsIPAddressActivity");
            startActivity(intent);
        } else if (id == R.id.rules) {
            Intent intent=new Intent();
            intent.setClassName(VoterProfileActivity.this,"com.hfad.electionacms.RulesIPAddressActivity");
            startActivity(intent);
        }
        else if (id == R.id.abu) {
            Intent intent=new Intent();
            intent.setClassName(VoterProfileActivity.this,"com.hfad.electionacms.AboutUsActivity");
            startActivity(intent);
        }else if (id == R.id.prof) {
            Intent intent=new Intent();
            intent.setClassName(VoterProfileActivity.this,"com.hfad.electionacms.VoterProfileInformationActivity");
            intent.putExtra("Ano",Ano);
            System.out.println("putting Ano in hamburger:"+Ano);
            startActivity(intent);
        }
        else if (id == R.id.lo) {
            Intent intent=new Intent();
            intent.setClassName(VoterProfileActivity.this,"com.hfad.electionacms.LoginActivity");
            intent.putExtra( "EXIT",true );
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }


    public void methodxyz(View v)
    {
        Intent intent = getIntent();
        final String aadhar=intent.getStringExtra("aadhar");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("citizen")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("Aadharnumber: ",String.valueOf(document.get("Adharcard_number")));
                                if(String.valueOf(document.get("Adharcard_number")).equals(aadhar)) {
                                    Boolean votestatus=document.getBoolean("Vote_Status");
                                    //Button b=(Button)findViewById(R.id.VoteButton);
                                    if(votestatus==true)
                                    {
                                        Intent intent = new Intent(VoterProfileActivity.this, AlreadyVotedActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {

                                        Bundle bundle = new Bundle();
                                        bundle.putString("aadharcard", aadhar);
                                        Intent intent = new Intent(VoterProfileActivity.this, VotingListActivity.class);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                }
                            }
                        } else {
                            //Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void onProfileClick(View view){
        //System.out.println("Icon click extras values: "+name+" "+partyname+" "+partyno+" "+constituency+" "+sino);
        Intent intent=new Intent(getApplicationContext(),VoterProfileInformationActivity.class);
        intent.putExtra("Ano",LoginActivity.Adharcard_number);
        System.out.println("putting Ano in icon"+LoginActivity.Adharcard_number);
        startActivity(intent);
    }

    public void onStatisticsClick(View view){
        Intent intent = new Intent(getApplicationContext(),StatisticsIPAddressActivity.class);
        String type_of_user=intent.getStringExtra("type_of_user");
        intent.putExtra("type_of_user",type_of_user);
        System.out.println("2!$!$!$!$!$!!$!$!$!$$!$!$!$$$!!"+type_of_user);
        startActivity(intent);
    }

    public void onRulesClick(View view){
        Intent intent = new Intent(getApplicationContext(),RulesIPAddressActivity.class);
        startActivity(intent);
    }

    public void all(View v){
        intent.putExtra("AssemblyConstituency","*");
        startActivity(intent);
    }

    public void self(View v){
        intent.putExtra("AssemblyConstituency",g.userconsti);
        startActivity(intent);
    }

}
