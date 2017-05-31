package campusme.campusme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity implements View.OnClickListener {

    Button bRegister;
    EditText etUsernameRegister, etPasswordRegister, etConfirmPasswordRegister, etSchoolEmailRegister; //need to implement search school also
    Spinner sState, sSchool, sGender;
    //------------------------BOOLEANS AND CASES----------------------------------
    boolean schoolEmailFlag = false;
    boolean usernameFlag = false;
    boolean passwordFlag = false;
    boolean confirmPasswordFlag = false;
    boolean genderFlag = false;
    boolean stateFlag = false;
    boolean schoolFlag = false;
    boolean usernameCheckFlag = true;
    boolean schoolCheckFlag = false;
    String edu = "edu";
    //------------------------FIREBASE AUTH---------------------------------------
    private FirebaseAuth firebaseAuth;
    private ProgressDialog registerProgress;
    private Firebase mRefUsers, mRefSchools;

    private DatabaseReference Database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Firebase.setAndroidContext(this);
        mRefUsers = new Firebase("https://campusme-59a81.firebaseio.com/CM_Users");
        mRefSchools = new Firebase("https://campusme-59a81.firebaseio.com/CM_Schools");

        Database = FirebaseDatabase.getInstance().getReference();

        //------------MAKE CURSIVE----REGISTER-----------------------------
        String fontPath = "fonts/MarketingScript.ttf";
        TextView Register = (TextView) findViewById(R.id.MCRegister); //Header REGISTER
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        Register.setTypeface(tf);//Register

        //----------------VARIABLES----------------------------------------------
        etUsernameRegister = (EditText) findViewById(R.id.etUsernameRegister);
        etPasswordRegister = (EditText) findViewById(R.id.etPasswordRegister);
        etConfirmPasswordRegister = (EditText) findViewById(R.id.etConfirmPasswordRegister);
        etSchoolEmailRegister = (EditText) findViewById(R.id.etSchoolEmailRegister);

        bRegister = (Button) findViewById(R.id.bRegister);
        bRegister.setOnClickListener(this);

        registerProgress = new ProgressDialog(this);//progress dialog when user registers to app
        firebaseAuth = FirebaseAuth.getInstance();
        //----------DROPDOWNS-------------------------------------------------\\
        sGender = (Spinner) findViewById(R.id.sGender);
        String[] genderList = new String[]{"", "Male", "Female"};
        final ArrayAdapter<String> adapterGender = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, genderList);
        sGender.setAdapter(adapterGender);

        sState = (Spinner) findViewById(R.id.sState);
        String[] stateList = new String[]{"", "Maryland", "Virginia", "Washington DC"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, stateList);
        sState.setAdapter(adapter);
        //--------SCHOOLS AND ADAPTERS-----------------------------------------\\
        sSchool = (Spinner) findViewById(R.id.sSchool);
        String[] defaultList = new String[]{""};
        final String[] marylandSchools1 = new String[]{"", "Towson University", "University of Maryland College Park", "Johns Hopkins University", "Montgomery College", "United States Naval Academy", "University of Maryland BC", "Community College of Baltimore County", "Loyola University", "University of Maryland UC", "University of Maryland Baltimore", "Prince George's Community College", "Anne Arundel Community College", "Bowie State University", "College of Southern Maryland", "Howard County Community College", "Coppin State University", "Frostburg State University", "University of Baltimore", "University of Maryland Eastern Shore", "Maryland Institute College of Arts", "Morgan State University", "Salisbury University", "St. Mary's College", "Goucher College", "McDaniel College", "Harford Community College", "Washington College", "Baltimore City Community College", "Mount St. Mary University", "Uniformed Services University", "Hagerstown Community College", "Notre Dame University", "Allegany College", "Chesapeake College", "St. John's College", "Washington Adventist University", "Carroll Community College", "Wor-Wic Community College", "Garrett College", "Peabody Institute", "Maryland University of Integrative Health", "Captiol Technology University", "North American Trade Schools", "Griggs International Academy"};
        String[] DCSchools1 = new String[]{"", "Georgetown University", "George Washington University", "Howard University", "American University", "Catholic University of America", "University of District of Columbia", "Strayer University", "Gallaudet University", "Paul H. Nitze", "Trinity Washington University", "National Defense University", "Graduate University", "Corcoran School of the Arts and Design", "National War College", "Wesley Theological Seminary", "National Intelligence University", "American University of Public Affairs", "American University School of Communication", "Institute of World Politics", "University of Potomoc", "Trachtenberg School", "George Washington University School of Nursing", "Dominican House Studies", "Howard University College of Dentistry", "Carrer Technical Institute", "Chicago School of Professional Psychology", "Benjamin T Rome School of Music"};
        String[] virginiaSchools = new String[]{"", "University of Virginia", "Virginia Tech", "Liberty University", "Virginia Commonwealth University", "George Mason University", "James Madison University", "Strayer University", "Northern Virginia Community College", "Old Dominion University", "University of Richmond", "Virginia State University", "Virginia Wesleyan College", "Norfolk State University", "College of William and Mary", "Virginia Military Institute", "Virginia Union University", "Radford University", "Hampton University", "Defense Acquisition University", "Thomas Nelson Community College", "Christopher Newport University", "Washington and Lee University", "J. Sargeant Reynolds Community College", "University of Mary Washington", "Tidewater Community College", "Longwood University", "Regent University", "John Tyler Community College", "Virginia Western Community College", "Shenandoah University", "Marymount University", "Germanna Community College", "Hampden Sydney College", "Central Virginia Community College", "Randolph Macon College", "Lord Fairfax Community College", "Bridgewater College", "ECPI University", "Roanoke College", "Eastern Virginia Medical School", "Stratford University", "Averette University", "New River Community College", "Sweet Briar College", "Emory and Henry College", "Hollins University", "Patrick Henry Community College", "Whytheville Community College", "Southside Virginia Community College"};
        final ArrayAdapter<String> adapterDefault = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, defaultList);
        final ArrayAdapter<String> adapterMaryland = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, marylandSchools1);
        final ArrayAdapter<String> adapterDC = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, DCSchools1);
        final ArrayAdapter<String> adapterVirginia = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, virginiaSchools);

        sSchool.setAdapter(adapterDefault);
//------------------SCHOOL TO STATE------------------------------------------------
        sState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    sSchool.setAdapter(adapterMaryland);
                    //-----------database grouping-------------------------------------------------------------------------
                   /* if(sSchool.getSelectedItem().toString().equals("Towson University")){
                        mRefSchools=new Firebase("https://campusme-59a81.firebaseio.com/CM_Schools/Maryland/Towson_University/");
                    }else if(sSchool.getSelectedItem().toString().equals("University of Maryland BC")){
                        mRefSchools=new Firebase("https://campusme-59a81.firebaseio.com/CM_Schools/Maryland/University_Maryland_BC/");
                    }*/

                    //---------------------------------------------------------------------------------------------------------
                } else if (position == 2) {
                    sSchool.setAdapter((adapterVirginia));
                } else if (position == 3) {
                    sSchool.setAdapter(adapterDC);
                } else {
                    sSchool.setAdapter(adapterDefault);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }// end onCreate Method


    @Override
    public void onClick(View v) {//NEED AT SOME POINT IMPLEMENT IF ALREADY EXISTS IN DB

        final String username = etUsernameRegister.getText().toString();
        final String schoolEmail = etSchoolEmailRegister.getText().toString().trim();
        String password = etPasswordRegister.getText().toString();
        String confirmPassword = etConfirmPasswordRegister.getText().toString();
        final String gender = sGender.getSelectedItem().toString();
        final String state = sState.getSelectedItem().toString();
        final String school = sSchool.getSelectedItem().toString();

        //ALL EMAIL CASES
        //EMAIL MUST BE UNIQUE FROM DATABASE CASE
        if (!schoolEmail.contains("@")) {
            schoolEmailFlag = false;
            etSchoolEmailRegister.setError("Please enter a valid School Email");
        } else if (schoolEmail.length() == 0) {
            etSchoolEmailRegister.setError("Please enter a School Email");
            schoolEmailFlag = false;
        } else if (!schoolEmail.endsWith("." + edu)) {
            etSchoolEmailRegister.setError("Please enter a valid School Email");
            schoolEmailFlag = false;
        } else {
            schoolEmailFlag = true;
        }
//--------------------------------------------------------------------------------------------------
        //USERNAME CASES

        if (username.equals("")) {
            usernameFlag = false;
            etUsernameRegister.setError("Please enter a Username");
        } else if (username.length() > 15) {
            usernameFlag = false;
            etUsernameRegister.setError("Username cannot be longer than 15 characters long");
        } else if (username.length() < 5) {
            usernameFlag = false;
            etUsernameRegister.setError("Username must be at least 5 characters long");
        } else {
            usernameFlag = true;
            etUsernameRegister.setError(null);
        }
//---------------------------------------------------------------------------------------------------
        //PASSWORD VALIDATION
        if (password.length() == 0) {
            passwordFlag = false;
            etPasswordRegister.setError("Please enter your Password");
        } else if (!password.matches(".*\\d.*")) { //needs to contain number
            passwordFlag = false;
            etPasswordRegister.setError("Password must contain a number");
        } else if (password.length() < 6) { //needs to contain number
            passwordFlag = false;
            etPasswordRegister.setError("Password must be at least 6 characters long");
        } else {
            passwordFlag = true;
            if (!confirmPassword.matches(password)) { //confirm password
                etConfirmPasswordRegister.setError("Passwords must match");
                confirmPasswordFlag = false;
            } else {
                confirmPasswordFlag = true;
            } //end if/else confirm password
        }
//----------------------------------------------------------------------------------------------------
        if (gender.equals("")) { //Gender CASE
            TextView errorText = (TextView) sGender.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Select your Gender");
            genderFlag = false;
        } else {
            genderFlag = true;
        }
//----------------------------------------------------------------------------------------------------
        if (state.equals("")) { //STATE CASE
            TextView errorText = (TextView) sState.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText("Select a State");
            stateFlag = false;
        } else {
            stateFlag = true;
        }
        //-----SCHOOL CANNOT BE EMPTY---------------------------------------------------------------
        if (school.equals("")) { //STATE CASE
            TextView errorTextSchools = (TextView) sSchool.getSelectedView();
            errorTextSchools.setError("");
            errorTextSchools.setTextColor(Color.RED);
            errorTextSchools.setText("Select a School");
            schoolFlag = false;
        } else {
            schoolFlag = true;
        }

        mRefUsers.child(username).addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    usernameCheckFlag = false;
                    etUsernameRegister.setError("\"" + username + "\"" + " has already been taken. Please choose another one.");
                } else {
                    usernameCheckFlag = true;
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        mRefSchools = new Firebase("https://campusme-59a81.firebaseio.com/CM_Schools/"+state+"/"+school+"/");
//-------------------------------------------IF ALL CONDITIONS MET------------------------------------
        if (usernameFlag == true && schoolEmailFlag == true && passwordFlag == true && confirmPasswordFlag == true && stateFlag == true && schoolFlag == true && usernameCheckFlag == true && genderFlag == true) {
            //encrypt values needed
            registerProgress.setMessage("Registering " + username + "...");
            registerProgress.show();
            switch (v.getId()) {
                case R.id.bRegister:
                    firebaseAuth.createUserWithEmailAndPassword(schoolEmail, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        //---------------------------SAVE TO CM_USERS----------------------------------\\
                                        Firebase mRefChild = mRefUsers.child(username);
                                        mRefChild.child("premium_package").setValue(false);
                                        mRefChild.child("school_email").setValue(schoolEmail);
                                        mRefChild.child("gender").setValue(gender);
                                        mRefChild.child("state").setValue(state);
                                        mRefChild.child("school").setValue(school);

                                        //---------------------------SAVE TO CM_SCHOOLS---------------------------------\\
                                        Firebase mRefChildSchools = mRefSchools.child(username);
                                        mRefChildSchools.child("school_email").setValue(schoolEmail);

                                        user.sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            finish();
                                                            Toast.makeText(Register.this, "Registered Successfully. Check your email", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(getApplicationContext(), VerifyEmail.class));
                                                        }
                                                    }
                                                });

                                            if (firebaseAuth.getCurrentUser() != null) {
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), VerifyEmail.class));
                                        }
                                    } else {
                                        Toast.makeText(Register.this, "Could not Register User. Try Again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    break;
            }//ends case switch
        } //ends the boolean values expression
    }//ends on click
}//ends class