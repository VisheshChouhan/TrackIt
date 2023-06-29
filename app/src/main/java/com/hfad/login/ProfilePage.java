package com.hfad.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

public class ProfilePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

        //setting toggle button
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.nav_open_drawer,R.string.nav_close_drawer);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Attach the SectionPager to the View Pager
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        //Attach the ViewPager to the TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        //Getting the passed studentClass
        student = (Student) getIntent().getSerializableExtra("StudentClass");


        //Setting data in card-view in profile page
        TextView nameView = (TextView) findViewById(R.id.StudentName);
        nameView.setText(student.getName());

        TextView rollNoView = (TextView) findViewById(R.id.RollNo);
        rollNoView.setText(student.getRollNo());

        TextView branchView = (TextView) findViewById(R.id.Branch);
        branchView.setText(student.getBranch());

        TextView batchView = (TextView) findViewById(R.id.Batch);
        batchView.setText(student.getBatch());



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        Intent intent = null;
        switch (id)
        {
            case R.id.logout:
                intent = new Intent(this, LoginActivity.class);
                updateLastLoggedUser("BY_DEFAULT","BY_DEFAULT");


        }


        if(fragment!=null)
        {
           // FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
           // ft.replace(R.id.content_frame, fragment);
           // ft.commit();
        }
        else
        {
            startActivity(intent);
        }

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            switch (position)
            {
                case 0:
                    AttendanceFragment TempFrag = new AttendanceFragment();
                    TempFrag.setArguments(getIntent().getExtras());
                    return TempFrag;
                case 1:
                    ProgressFragment TempProgressFrag = new ProgressFragment();
                    TempProgressFrag.setArguments(getIntent().getExtras());
                    return TempProgressFrag;
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.tab1);
                case 1:
                    return getResources().getText(R.string.tab2);

            }
            return null;
        }
    }

    public void updateLastLoggedUser(String user, String pass)
    {
        ContentValues cred = new ContentValues();
        cred.put("USERNAME", user);
        cred.put("PASSWORD", pass);

        SQLiteOpenHelper writeDatabaseHelper = new loginDatabaseHelper(this);
        try {
            SQLiteDatabase db = writeDatabaseHelper.getWritableDatabase();
            db.update("LASTCREDENTIAL",cred,"_id = ?",new String[] {Integer.toString(1)} );
            db.close();
        }catch (SQLiteException e)
        {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();

        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
}