package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

import retrofit.RestAdapter;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String ROOT_URL = "http://192.168.0.103:8080/";
    public static List<Header> headerList;
    public static Quiz quiz= new Quiz();

    public static int user_id;


    ArrayList<String> Courses = new ArrayList<>();
    public static HashMap<String , Student> Students;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        Students = new HashMap();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    RestAdapter adapter = new RestAdapter.Builder()
            .setEndpoint(ROOT_URL)
            .build();
    API api = adapter.create(API.class);

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.get_courses) {
            Toast.makeText(getApplicationContext() , "courses" , Toast.LENGTH_SHORT).show();
            // Handle the camera action
            api.getAllCourses(new Callback<List<String>>() {
                @Override
                public void success(List<String> list, Response response) {
                    //Dismissing the loading progressbar
                    Log.d("courses", list.toString());
                    Toast.makeText(getApplicationContext(), "Paaasass", Toast.LENGTH_SHORT).show();

                    ListIterator<String> itr = list.listIterator();

                    //Storing the data in our list
                    while (itr.hasNext()) {
                        String course = itr.next();
                        if (!Courses.contains(course))
                            Courses.add(course);

                    }
                    int count = 0;
                    for (Header header : headerList) {
                        if (count > 2) {
                            Log.d("sdsd", "yak " + header.getName() + " yaka " + header.getValue());
                            if (header.getName().equals("Location")) {
                                user_id = Character.getNumericValue((char)header.getValue().charAt(header.getValue().length()-1));
                            }
                        }
                        count++;
                    }

                    Gson gson = new Gson();

                    String jsonCars = gson.toJson(Courses);

                    Intent intent = new Intent(Main2Activity.this, Courses.class);
                    intent.putExtra("list_as_string", jsonCars);

                    startActivity(intent);
                    //Calling a method to show the list

                }

                @Override
                public void failure(RetrofitError error) {
                    //you can handle the errors here
                    Toast.makeText(getApplicationContext(), "Faileddd", Toast.LENGTH_SHORT).show();
                    Log.d("failed", "fail");
                    error.printStackTrace();


                }
            });


        } else if (id == R.id.responses) {

        } else if (id == R.id.profile) {

            Intent intent = new Intent(Main2Activity.this , Profile.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
