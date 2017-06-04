package com.opencharge.opencharge.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.opencharge.opencharge.R;
import com.opencharge.opencharge.presentation.fragments.CreatePublicPointsFragment;
import com.opencharge.opencharge.presentation.fragments.CreateServiceFragment;
import com.opencharge.opencharge.presentation.fragments.MapsFragment;
import com.opencharge.opencharge.presentation.fragments.UserInfoFragment;
import com.opencharge.opencharge.presentation.locators.GoogleApiLocator;


public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new MapsFragment()).commit();

        RelativeLayout datePickerButton = (RelativeLayout) findViewById(R.id.date_picker_button);
        datePickerButton.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_profile) {
            fm.beginTransaction().replace(R.id.content_frame, new UserInfoFragment()).commit();
        } else if (id == R.id.nav_maps) {
            fm.beginTransaction().replace(R.id.content_frame, new MapsFragment()).commit();
        } else if (id == R.id.nav_newpoint) {
            fm.beginTransaction().replace(R.id.content_frame, new CreatePublicPointsFragment()).commit();
        } else if (id == R.id.nav_newservice) {
            fm.beginTransaction().replace(R.id.content_frame, new CreateServiceFragment()).commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_logout) {
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        //TODO: s'ha de mirar perque no es poden borrar les credencials de Google!!!
//        GoogleApiClient mGoogleApiClient = GoogleApiLocator.getInstance(null).getGoogleApiClient();
//        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
//                new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(@NonNull Status status) {
//                        Intent intent = new Intent(NavigationActivity.this, SignInActivity.class);
//                        startActivity(intent);
//                    }
//                }
//        );
        Intent intent = new Intent(NavigationActivity.this, SignInActivity.class);
        startActivity(intent);
    }

}
