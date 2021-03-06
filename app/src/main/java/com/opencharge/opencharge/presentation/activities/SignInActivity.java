package com.opencharge.opencharge.presentation.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v4.util.Pair;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.Entities.UserPointSummary;
import com.opencharge.opencharge.domain.use_cases.SetCurrentUserUseCase;
import com.opencharge.opencharge.domain.use_cases.UsersCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.UsersListUseCase;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.opencharge.opencharge.R;
import com.opencharge.opencharge.presentation.locators.GoogleApiLocator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 03/05/2017.
 */

public class SignInActivity extends AppCompatActivity  {

    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton signInButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mAuth = FirebaseAuth.getInstance();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this,new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(SignInActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        GoogleApiLocator.getInstance(mGoogleApiClient);
        
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    setCurrentUser(user);
                } else {
                    Log.d("SignInActivity", "onAuthStateChanged:signed_out");
                }
            }
        };


        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isNetworkAvailable()){
                    signIn();
                }else {
                    Toast.makeText(SignInActivity.this, "Oops! No tens connexió a internet!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.e("Sign in result: ", "" + result.isSuccess());
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
                Toast.makeText(SignInActivity.this, "Sign In Error", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {
        Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("SignInActivity", "signInWithGoogleCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("SignInActivity", "ERROR: signInWithCredential", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            //Get Current User Data
                            Log.w("SignInActivity", "SUCCESS: credential = " + account);
                            UsersListUseCase usersListUseCase = UseCasesLocator.getInstance().getUsersListUseCase(new UsersListUseCase.Callback() {
                                @Override
                                public void onUsersRetrieved(User[] users) {
                                    String email = account.getEmail();
                                    boolean notInFirebase = true;
                                    for (User user : users) {
                                        if (notInFirebase && user.getEmail().equals(email)) {
                                            notInFirebase = false;
                                        }
                                    }
                                    if (notInFirebase) {
                                        String name = account.getDisplayName();
                                        //TODO: la següent linea pot produir un NullPointerException, s'haura de tractar
                                        String photoUri = account.getPhotoUrl().toString();
                                        List<UserPointSummary> puntsCreats = new ArrayList<>();

                                        UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
                                        UsersCreateUseCase getCreateUsersUseCase = useCasesLocator.getUsersCreateUseCase(new UsersCreateUseCase.Callback() {
                                            @Override
                                            public void onUserCreated(String id) {
                                                Log.d("SignInActivity", "onUserCreatedCallback");
                                                goToApp();
                                            }

                                        });

                                        getCreateUsersUseCase.setUserParameters(name, photoUri, email, puntsCreats);
                                        getCreateUsersUseCase.execute();
                                    }
                                    else
                                        goToApp();
                                }

                            });
                            usersListUseCase.execute();

                        }
                    }
                });
    }

    private void goToApp() {
        Intent intent = new Intent(SignInActivity.this, NavigationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void setCurrentUser(FirebaseUser user) {
        Log.d("SignInActivity", "onAuthStateChanged:signed_in:" + user.getEmail());
        SetCurrentUserUseCase setCurrentUserUseCase = UseCasesLocator.getInstance().getSetCurrentUserUseCase(this);
        setCurrentUserUseCase.setUserEmail(user.getEmail());
        setCurrentUserUseCase.execute();
    }

    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

}

