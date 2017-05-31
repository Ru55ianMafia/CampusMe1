package campusme.campusme;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CampusMe extends AppCompatActivity implements View.OnClickListener {

    Button bLogin;
    EditText etSchoolEmail, etPassword;
    TextView RegisterFromLogin;
    TextView forgotPassword;
    boolean schoolEmailFlag = false;
    boolean passwordFlag = false;
    String edu = "edu";

    private FirebaseAuth loginAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_me);

        etSchoolEmail = (EditText) findViewById(R.id.etSchoolEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(this);
        RegisterFromLogin = (TextView) findViewById(R.id.RegisterFromLogin);
        RegisterFromLogin.setOnClickListener(this);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);

        //------------MAKE CURSIVE----LOGIN-------------------------------
        String fontPath = "fonts/MarketingScript.ttf";
        TextView Login = (TextView) findViewById(R.id.MCLogin); //Header LOGIN
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        Login.setTypeface(tf);//header
        //----------------------------------------------------------------
        loginAuth = FirebaseAuth.getInstance();


    }
    @Override
    public void onClick(View v) {

        final String schoolEmail = etSchoolEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        //----------SCHOOL EMAIL VALIDATION---------------------------------
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
        }
        //------------------PASSWORD VALIDATION------------------------------
        if (password.length() == 0) {
            passwordFlag = false;
            etPassword.setError("Please enter your Password");
        } else {
            passwordFlag = true;
        }


        switch (v.getId()) {
            case R.id.bLogin:
                if (passwordFlag == true && schoolEmailFlag == true) {
                    etPassword.setError(null);
                    etSchoolEmail.setError(null);
                    loginAuth.signInWithEmailAndPassword(schoolEmail, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override 
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(CampusMe.this, "Welcome " + schoolEmail + "!", Toast.LENGTH_SHORT).show(); //change to username from saved database object
                                        startActivity(new Intent(CampusMe.this, Profile.class));
                                    }else {
                                        Toast.makeText(CampusMe.this, "School Email or Password is incorrect or internet connection is unstable.", Toast.LENGTH_SHORT).show();
                                    }
                                   /* else if(user.isEmailVerified()==false){
                                        Toast.makeText(CampusMe.this, "Please verify your School Email through the verification email link you received.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(CampusMe.this, VerifyEmail.class));

                                    } */
                                }
                            });
                    break;
                } else{
                    break;
                }

            case R.id.RegisterFromLogin:
                etPassword.setError(null);
                etSchoolEmail.setError(null);
                startActivity(new Intent(CampusMe.this, Register.class));
                break;


            case R.id.forgotPassword:
                etPassword.setError(null);
                etSchoolEmail.setError(null);
                startActivity(new Intent(CampusMe.this, PasswordReset.class));
                break;

        }//end switch
        //end if validation
    }// end onClick
}//end class
