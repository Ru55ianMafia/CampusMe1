package campusme.campusme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordReset extends AppCompatActivity implements View.OnClickListener {

    EditText etSchoolEmail;
    TextView returnToLogin;
    Button bSendEmail;
    boolean schoolEmailFlag = false;
    String edu = "edu";

    //---------------------FIREBASE-------------------------------
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        etSchoolEmail = (EditText) findViewById(R.id.etSchoolEmail);
        bSendEmail = (Button) findViewById(R.id.bSendEmail);
        bSendEmail.setOnClickListener(this);
        returnToLogin = (TextView) findViewById(R.id.returnToLogin);
        returnToLogin.setOnClickListener(this);

        //------------MAKE CURSIVE--FORGOTPASSWORD----------------------
        String fontPath = "fonts/MarketingScript.ttf";
        TextView ForgotPassword = (TextView) findViewById(R.id.MCForgotPassword); //Header LOGIN
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        ForgotPassword.setTypeface(tf);//header
        //-------------------------------------------------------------

    }

    @Override
    public void onClick(View v) {

        String schoolEmail = etSchoolEmail.getText().toString().trim();

        if (!schoolEmail.contains("@")) {
            schoolEmailFlag = false;
            etSchoolEmail.setError("Please enter a valid School Email");
        } else if (schoolEmail.length() == 0) {
            etSchoolEmail.setError("Please enter a School Email");
            schoolEmailFlag = false;
        } else if (!schoolEmail.endsWith("." + edu)) {
            etSchoolEmail.setError("Please enter a valid School Email");
            schoolEmailFlag = false;
        } else {
            schoolEmailFlag = true;
            etSchoolEmail.setError(null);
        }


        switch (v.getId()) {
            case R.id.bSendEmail:
                if (schoolEmailFlag == true) {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.sendPasswordResetEmail(schoolEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        finish();
                                        Toast.makeText(PasswordReset.this, "Password Reset Email Sent. Please check your Email.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), CampusMe.class));
                                    } else{
                                        Toast.makeText(PasswordReset.this, "Account not registered or internet connection is unstable.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    break;
                }else{
                    break;
                }


            case R.id.returnToLogin:
                startActivity(new Intent(this, CampusMe.class));
                etSchoolEmail.setError(null);
                break;
        }//end switch
    }//onClick
}//class
