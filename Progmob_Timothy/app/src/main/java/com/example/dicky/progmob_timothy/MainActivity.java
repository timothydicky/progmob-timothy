package com.example.dicky.progmob_timothy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isLoggedIn() == true){
            Intent intent = new Intent(getApplicationContext(), RandomPostActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            FacebookSdk.sdkInitialize(getApplicationContext());
            callbackManager = CallbackManager.Factory.create();

            loginButton = (LoginButton)findViewById(R.id.login_button);

            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Intent intent = new Intent(getApplicationContext(), RandomPostActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(MainActivity.this,"Login dibatalkan.",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(MainActivity.this,"Login gagal.",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    public boolean isLoggedIn() {
        accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}