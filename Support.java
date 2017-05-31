package campusme.campusme;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Support extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle; //make the hamburger symbol at the top


    EditText etBody;
    EditText etSubject;
    Button bSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        etBody = (EditText) findViewById(R.id.etBody);
        etSubject = (EditText) findViewById(R.id.etSubject);
        bSend = (Button) findViewById(R.id.bSend);
        //------------------SEND EMAIL BUTTON----------------------------
        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String subject = etSubject.getText().toString();
                final String message = etBody.getText().toString();

                try {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"campusmeapp@gmail.com"});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "In-App : "+ subject);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, message);
                    emailIntent.setType("message/rfc822");
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException anfe){
                    Toast.makeText(Support.this, "No email client found.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //------------MAKE CURSIVE----PROFILE-----------------------------
        String fontPath = "fonts/MarketingScript.ttf";
        final TextView Profile = (TextView) findViewById(R.id.CMSupport); //Header Support
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        Profile.setTypeface(tf);//Support
        //-------------------------------------------------------------

        //-----------------------------NAVIGATION DRAWER----------------------------
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Support");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        //------------------------For NavigationBar to be able to click items---------------------------------\\
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_logout:
                        Toast.makeText(Support.this, "Logged Out of CampusMe.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Support.this, CampusMe.class));
                        break;

                    case R.id.nav_support:
                        startActivity(new Intent(Support.this, Support.class));
                        break;

                    case R.id.nav_me:
                        startActivity(new Intent(Support.this, Profile.class));
                        break;
                }
                return false;
            }
        });
    }

    //----------------------------HAMBURGER ICON-------------------------------------------
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    //-----------------------ALLOW USER TO ESCAPE THE EDIT TEXTS------------------------------------
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();
        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }
    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }
//----------------------------------------------------------------------------------------------------------------------


}
