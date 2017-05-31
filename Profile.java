package campusme.campusme;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

public class Profile extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    Button bLogout;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle; //make the hamburger symbol at the top
    //--------------------------Botton Menu----------------------------------------
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bLogout = (Button) findViewById(R.id.bLogout);
        bLogout.setOnClickListener(this);

        //-----------------------------NAVIGATION DRAWER--------------------------------------------
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Me");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
       //---------------------------BOTTOM MENU ON CLICK--------------------------------------------
        /*
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.getMenu().getItem(0).setChecked(false);
        bottomNavigationView.getMenu().getItem(1).setChecked(false);
        bottomNavigationView.getMenu().getItem(2).setChecked(false);
        bottomNavigationView.getMenu().getItem(3).setChecked(false);

        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.bottom_campus:
                                break;

                            case R.id.bottom_me:
                                //do not do anything you re on this tab
                                break;

                            case R.id.bottom_dm:
                                break;

                            case R.id.bottom_feed:
                                break;
                        }
                        return true;
                    }
                });
        */
        //------------------------For NavigationBar to be able to click items---------------------------------\\
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_logout:
                        Toast.makeText(Profile.this, "Logged Out of CampusMe.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Profile.this, CampusMe.class));
                        break;

                    case R.id.nav_support:
                        startActivity(new Intent(Profile.this, Support.class));
                        break;

                    case R.id.nav_me:
                        startActivity(new Intent(Profile.this, Profile.class));
                        break;
                }
                return false;
            }
        });

        //------------MAKE CURSIVE----PROFILE-----------------------------
        String fontPath = "fonts/MarketingScript.ttf";
        TextView Profile = (TextView) findViewById(R.id.MCProfile); //Header Profile
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        Profile.setTypeface(tf);//Profile
        //-------------------------------------------------------------
        firebaseAuth = FirebaseAuth.getInstance();

    }

    //----------------------------HAMBURGER ICON-------------------------------------------
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
    //------------------------------------------------------------------------------------

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogout:
                FirebaseAuth.getInstance().signOut(); //logs off user
                Toast.makeText(Profile.this, "Logged Out of CampusMe.", Toast.LENGTH_SHORT).show(); //later replace with user signed out pull from database
                startActivity(new Intent(this, CampusMe.class));
                break;


        }
    }
}

// person, forumn/insert comment, chat, school, live help,add photo, mode edit, whatshot