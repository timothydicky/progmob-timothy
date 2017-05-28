package com.example.dicky.progmob_timothy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.util.Random;

public class RandomPostActivity extends AppCompatActivity {

    Button logoutButton;
    Button post;
    Button btnFacbook;
    Bitmap image;
    ProgressBar progressBar;

    private AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_post);

        if (isLoggedIn() == false){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }

        logoutButton = (Button) findViewById(R.id.logout_button);
        post = (Button) findViewById(R.id.shareButton);
        btnFacbook = (Button) findViewById(R.id.btnFacebook);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnFacbook.setVisibility(View.INVISIBLE);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                Random r = new Random();
                int number = (r.nextInt(100));

                if (number>=0 && number<=33){
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.sad);
                    posting("Sepertinya hari ini saya sedang sial");
                }
                if (number>=34 && number<=66){
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.flat);
                    posting("Hari ini nampaknya biasa saja");
                }
                if (number>=67 && number<=100){
                    image = BitmapFactory.decodeResource(getResources(), R.drawable.happy);
                    posting("Saya merasa beruntung saat ini");
                }
            }
        });
        btnFacbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile profile = Profile.getCurrentProfile();


                String uri = "facebook://facebook.com/"+profile.getId();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
    }

    private void posting(String s) {
        Toast.makeText(RandomPostActivity.this, "Tunggu sebentar, keberuntungan anda sedang diprediksi.", Toast.LENGTH_SHORT).show();
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption(s.toString())
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareApi shareApi = new ShareApi(content);
        shareApi.share(new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(RandomPostActivity.this, "Berhasil mengirim ke dinding Facebook anda.", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btnFacbook.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancel() {
                Toast.makeText(RandomPostActivity.this, "onCancel.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(RandomPostActivity.this, "onError.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean isLoggedIn() {
        accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}