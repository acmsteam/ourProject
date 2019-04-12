package app.amazon.elections;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class loginActivity extends AppCompatActivity {
    private Button login_button,reg_button;
    EditText aadhar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_button=(Button)findViewById(R.id.login_button);
        reg_button=(Button)findViewById(R.id.reg_button);
        aadhar=(EditText)findViewById(R.id.enter_adhar_for_reg);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),loginActivity.class);
                startActivity(intent);
            }
        });

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),OTPActivity.class);
                //intent.putExtra("aadhar",aadhar.getText().toString());
                intent.putExtra("aadhar",aadhar.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }

}

