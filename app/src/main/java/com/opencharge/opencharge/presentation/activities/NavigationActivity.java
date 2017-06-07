package com.opencharge.opencharge.presentation.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.auth.FirebaseAuth;
import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.use_cases.GetCurrentUserUseCase;
import com.opencharge.opencharge.presentation.fragments.AboutFragment;
import com.opencharge.opencharge.presentation.fragments.CreatePublicPointsFragment;
import com.opencharge.opencharge.presentation.fragments.CreateServiceFragment;
import com.opencharge.opencharge.presentation.fragments.HelpFragment;
import com.opencharge.opencharge.presentation.fragments.MapsFragment;
import com.opencharge.opencharge.presentation.fragments.UserInfoFragment;
import com.opencharge.opencharge.presentation.locators.GoogleApiLocator;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;
import com.squareup.picasso.Picasso;


public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView profilePic;
    TextView username;

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

        View hView =  navigationView.getHeaderView(0);
        profilePic = (ImageView) hView.findViewById(R.id.navigationPic);
        username = (TextView) hView.findViewById(R.id.username);
        loadUserPhoto();
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
        final FragmentManager fm = getSupportFragmentManager();
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_profile) {
            UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
            GetCurrentUserUseCase getCurrentUserUseCase = useCasesLocator.getGetCurrentUserUseCase(this, new GetCurrentUserUseCase.Callback() {
                @Override
                public void onCurrentUserRetrieved(User currentUser) {
                    UserInfoFragment fragment = UserInfoFragment.newInstance(currentUser.getId());
                    fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
                }
            });
            getCurrentUserUseCase.execute();
        } else if (id == R.id.nav_maps) {
            fm.beginTransaction().replace(R.id.content_frame, new MapsFragment()).commit();
        } else if (id == R.id.nav_newpoint) {
            fm.beginTransaction().replace(R.id.content_frame, new CreatePublicPointsFragment()).commit();
        } else if (id == R.id.nav_help) {
            fm.beginTransaction().replace(R.id.content_frame, new HelpFragment()).addToBackStack(null).commit();
        } else if (id == R.id.nav_about) {
            fm.beginTransaction().replace(R.id.content_frame, new AboutFragment()).addToBackStack(null).commit();
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

    private void loadUserPhoto() {
        final Context context = this;
        UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
        GetCurrentUserUseCase getCurrentUserUseCase = useCasesLocator.getGetCurrentUserUseCase(context, new GetCurrentUserUseCase.Callback() {
            @Override
            public void onCurrentUserRetrieved(User currentUser) {
                Picasso.with(context).load(currentUser.getPhoto()).into(profilePic);
                username.setText(currentUser.getUsername());
            }
        });
        getCurrentUserUseCase.execute();
    }

}
