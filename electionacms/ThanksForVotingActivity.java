package com.hfad.electionacms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ThanksForVotingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks_for_voting);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),CandidateProfileActivity.class);
        startActivity(intent);
    }
}

