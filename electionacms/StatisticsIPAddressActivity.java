package com.hfad.electionacms;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class StatisticsIPAddressActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    WebView stat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_statistics_ip_address );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        stat=findViewById(R.id.stat);
        WebSettings webSettings = stat.getSettings();
        webSettings.setJavaScriptEnabled(true);
        StatisticsActivity webViewClient = new StatisticsActivity();
        stat.setWebViewClient(webViewClient);
        stat.loadUrl("http://192.168.0.7:3003/");
        NavigationView navigationView = findViewById( R.id.nav_view );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener( this );
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.stat.canGoBack()) {
            this.stat.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
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
            intent.setClassName(StatisticsIPAddressActivity.this,"com.hfad.electionacms.CandidateProfileActivity");
            startActivity(intent);
        }else if (id == R.id.cl) {
            Intent intent=new Intent();
            intent.setClassName(StatisticsIPAddressActivity.this,"com.hfad.electionacms.CandidateListActivity");
            startActivity(intent);
        } else if (id == R.id.log) {
            Intent intent=new Intent();
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            intent.putExtra( "EXIT",true );
            intent.setClassName(StatisticsIPAddressActivity.this,"com.hfad.electionacms.LoginActivity");
            startActivity(intent);
        }
        else if(id ==R.id.stas){
            Intent intent=new Intent( );
            intent.setClassName( StatisticsIPAddressActivity.this,"com.hfad.electionacms.StatisticsIPAddressActivity" );
            startActivity(intent);
        }
        else if (id == R.id.abu) {
            Intent intent=new Intent();
            intent.setClassName(StatisticsIPAddressActivity.this,"com.hfad.electionacms.AboutUsActivity");
            startActivity(intent);
        }
        else if (id == R.id.prof) {
            Intent intent=new Intent();
            String type_of_user=intent.getStringExtra("type_of_user");
            if(type_of_user.equals("Candidate"))
            intent.setClassName(StatisticsIPAddressActivity.this,"com.hfad.electionacms.CandidateProfileInformationActivity");
            else intent.setClassName(StatisticsIPAddressActivity.this,"com.hfad.electionacms.VoterProfileInformationActivity");
            startActivity(intent);
        }

        else if (id == R.id.can) {
            Intent intent=new Intent();
            intent.setClassName(StatisticsIPAddressActivity.this,"com.hfad.electionacms.CandidatePromisesActivity");
            startActivity(intent);
        }
        else if (id == R.id.rul) {
            Intent intent=new Intent();
            intent.setClassName(StatisticsIPAddressActivity.this,"com.hfad.electionacms.RulesIPAddressActivity");
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }
}
