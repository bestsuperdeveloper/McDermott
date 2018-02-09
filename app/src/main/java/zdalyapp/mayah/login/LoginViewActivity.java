package zdalyapp.mayah.login;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zdalyapp.mayah.R;

public class LoginViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);
        LogIn();
    }

    private void LogIn() {
        String username = getIntent().getExtras().getString("name", "");
        String password = getIntent().getExtras().getString("password", "");
        new CountDownTimer(2000, 1000){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent();
                if (true)
                {
                    intent.putExtra("MESSAGE", "OK");
                }
                else
                {
                    intent.putExtra("MESSAGE", "NO");
                }
                setResult(2, intent);
                finish();
            }
        }.start();

    }
}
