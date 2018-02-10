package zdalyapp.mayah;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

import zdalyapp.mayah.global.Constants;
import zdalyapp.mayah.global.Utils;
import zdalyapp.mayah.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    ImageView imgMainLogo, imgBotLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constants.GraphColorList = new ArrayList<>();
        ShowView();
        ShowLoginScreen();
    }

    private void ShowView() {
        imgMainLogo = (ImageView) findViewById(R.id.imageView2);
        imgBotLogo = (ImageView) findViewById(R.id.imageView3);
        imgMainLogo.setImageBitmap(Utils.GetImageFromAssets(this, "logo_medium.png"));
        imgBotLogo.setImageBitmap(Utils.GetImageFromAssets(this, "zdaly_logo.png"));
    }

    private void ShowLoginScreen() {
        new CountDownTimer(2000, 1000){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}
