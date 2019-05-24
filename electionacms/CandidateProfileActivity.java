package com.hfad.electionacms;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

import static com.hfad.electionacms.LoginActivity.aadhar;

//import com.squareup.picasso.Picasso;

public class CandidateProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final String TAG="CandidateProfileActivity";
    static TextView tv1;
    static TextView tv2;
    static TextView tv3;
    static TextView tv4;
    static TextView tv5;
    static ImageView iv;
    String name;
    String partyname;
    String constituency;
    String partyno;
    String sino;
    boolean flag=false;
    GlobalsActivity g;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_candidate_profile );
        ReadFromDb();
        System.out.println("## First I should get printed! "+flag);
        tv1=(TextView)findViewById(R.id.nam);
        System.out.println("## here");
        tv2=(TextView)findViewById(R.id.partno);
        tv3=(TextView) findViewById(R.id.con);
        tv4=(TextView)findViewById(R.id.sno);
        tv5=(TextView)findViewById(R.id.pno);
        Bundle extras = getIntent().getExtras();
        //Intent intent= getIntent();
        //name = intent.getStringExtra("name");
        /*String partyname = intent.getStringExtra("partyname");
        String constituency = intent.getStringExtra("constituency");
        String partyno = intent.getStringExtra("partyno");
        String sino=intent.getStringExtra("sino");*/
        if(extras!=null) {
            //String image1=extras.getString("img")
            //String name = extras.getString( "name" );
            name = extras.getString("name");
            partyname = extras.getString( "partyname" );
            constituency = extras.getString( "constituency" );
            partyno = extras.getString( "partyno" );
            sino = extras.getString( "sino" );
            aadhar = extras.getString("aadhar");
            System.out.println("Retrieved extras in CandidateProfileActivity are: "+name+" "+partyname+" "+constituency+" "+partyno+" "+sino);
            extras.putString("name",name);
            extras.putString("partyname",partyname);
            extras.putString("constituency",constituency);
            extras.putString("partyno",partyno);
            extras.putString("sino",sino);
            extras.putString("aadhar",aadhar);
            System.out.println("extra values are set again!");

            //trying with intents
            Intent intent=getIntent();
            intent.putExtra("name",name);
            intent.putExtra("partyname",partyname);
            intent.putExtra("constituency",constituency);
            intent.putExtra("partyno",partyno);
            intent.putExtra("sino",sino);
            intent.putExtra("aadhar",aadhar);
            System.out.println("extra lol no!intent is set!");

//      Picasso.with(this).load("https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwisj96fj-jhAhXMdysKHbfpCUgQjRx6BAgBEAU&url=%2Furl%3Fsa%3Di%26source%3Dimages%26cd%3D%26ved%3D%26url%3Dhttps%253A%252F%252Fwww.pexels.com%252Fsearch%252Fbeauty%252F%26psig%3DAOvVaw391DINIIYhA-Sg1Shi5BUZ%26ust%3D1556174312321647&psig=AOvVaw391DINIIYhA-Sg1Shi5BUZ&ust=1556174312321647").into(iv);
            System.out.println("YEAH! extras is not null");
            tv1.setText( "Welcome "+name+"!" );
            tv2.setText( "Party Name: "+partyname );
            tv3.setText( "Constituency: "+constituency );
            //tv4.setText( partyno );
            //tv5.setText( sino );
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

        intent= new Intent(CandidateProfileActivity.this, CandidateListActivity.class);
        g=(GlobalsActivity) getApplicationContext();
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
            // Handle the camera action
            Intent intent=new Intent();
            intent.setClassName(CandidateProfileActivity.this,"com.hfad.electionacms.CandidateProfileActivity");
            startActivity(intent);
        } else if (id == R.id.cl) {
            Intent intent=new Intent();
            intent.setClassName(CandidateProfileActivity.this, "com.hfad.electionacms.CandidateListActivity" );
            startActivity(intent);
        } else if (id == R.id.log){
            Intent intent=new Intent();
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            intent.putExtra( "EXIT",true );
            intent.setClassName(CandidateProfileActivity.this, "com.hfad.electionacms.LoginActivity" );
            startActivity(intent);
        }
        else if (id == R.id.abu) {
            Intent intent=new Intent();
            intent.setClassName(CandidateProfileActivity.this,"com.hfad.electionacms.AboutUsActivity");
            startActivity(intent);
        }
        else if (id == R.id.prof) {
            Intent intent=new Intent();
            System.out.println("hamburger profile extras: "+name+" "+partyname+" "+partyno+" "+constituency+" "+sino);
            intent.setClassName(CandidateProfileActivity.this,"com.hfad.electionacms.CandidateProfileInformationActivity");
            startActivity(intent);
        }

        else if (id == R.id.can) {
            Intent intent=new Intent();
            intent.setClassName(CandidateProfileActivity.this,"com.hfad.electionacms.CandidatePromisesActivity");
            startActivity(intent);
        }
        else if (id == R.id.rul) {
            Intent intent=new Intent();
            intent.setClassName(CandidateProfileActivity.this,"com.hfad.electionacms.RulesIPAddressActivity");
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    //Blink
    private void blink() {
        //blink_status=true;
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 1000;
                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView txt = (TextView) findViewById(R.id.usage);
                        if (txt.getVisibility() == View.VISIBLE) {
                            txt.setVisibility(View.INVISIBLE);
                        } else {
                            txt.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();

    }
    public void ReadFromDb() {

        DocumentReference user = db.collection("Election_Day").document("Goshamahal");

        user.get().addOnCompleteListener(new OnCompleteListener< DocumentSnapshot >() {

            @Override
            public void onComplete( Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    String date[]=((String)doc.get("Election_day")).split("-");
                    //Log.d(TAG,"date "+date[0]);
                    Date d=new Date();
                    String dt[]=(d.toString()).split(" ");
                    int cd=Integer.parseInt(dt[2]);
                    int cy=Integer.parseInt(dt[5]);
                    int cm=d.getMonth()+1;
                    int month= Integer.parseInt(date[1]);
                    int year=Integer.parseInt(date[2]);
                    int day=Integer.parseInt(date[0]);
                    /*Log.d(TAG,"cuur date"+d.toString());
                    Log.d(TAG,"month"+cm+" "+month+" ");
                    Log.d(TAG,"date"+cd+" "+day+" ");
                    Log.d(TAG,"year"+cy+" "+year+" ");
                    Log.d(TAG,"is month equal"+(d.getMonth()==month));*/
                    if(cm==month && cy==year && day-cd<=3 && day-cd>=0){
                        flag=true;
                    }
                    if(cm==month&&cy==year&&day-cd<=3&&day-cd>=0){
                        blink();
                        //blink_status=true;
                    }


                    //Log.d(TAG,(Timestamp)("Election_day")+"hello");


                }

            }

        })

                .addOnFailureListener(new OnFailureListener() {

                    @Override

                    public void onFailure( Exception e) {

                    }

                });
    }

    //Blink

    public void onProfileClick(View view){
        System.out.println("Icon click extras values: "+name+" "+partyname+" "+partyno+" "+constituency+" "+sino);
        Intent intent=new Intent(getApplicationContext(),CandidateProfileInformationActivity.class);
        System.out.println("starting intent againnnnnnn!");
        startActivity(intent);
    }

    public void onOathClick(View view){
        Intent intent = new Intent(getApplicationContext(),CandidatePromisesActivity.class);
        startActivity(intent);
    }

    public void onStatisticsClick(View view){
        Intent intent = new Intent(getApplicationContext(),StatisticsIPAddressActivity.class);
        startActivity(intent);
    }

    public void onRulesClick(View view){
        Intent intent = new Intent(getApplicationContext(),RulesIPAddressActivity.class);
        startActivity(intent);
    }

    public void methodxyz(View v)
    {
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
                                        Intent intent = new Intent(CandidateProfileActivity.this, AlreadyVotedActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {

                                        Bundle bundle = new Bundle();
                                        bundle.putString("aadharcard", aadhar);
                                        Intent intent = new Intent(CandidateProfileActivity.this, VotingListActivity.class);
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

    public void self(View v){
        intent.putExtra("AssemblyConstituency",g.userconsti);
        startActivity(intent);
    }
}
