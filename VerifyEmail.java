package campusme.campusme;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

public class VerifyEmail extends AppCompatActivity implements AdapterView.OnClickListener{

    Button bGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        bGoToLogin = (Button) findViewById(R.id.bGoToLogin);
        bGoToLogin.setOnClickListener(this);

        //------------MAKE CURSIVE----PROFILE-----------------------------
        String fontPath = "fonts/MarketingScript.ttf";
        TextView Profile = (TextView) findViewById(R.id.MCVerify); //Header Profile
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        Profile.setTypeface(tf);//Profile
        //-------------------------------------------------------------

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bGoToLogin:
                startActivity(new Intent(this, CampusMe.class));
                break;
        }
    }
}
