package zdalyapp.mayah.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.Browser;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import zdalyapp.mayah.McDomatsApp;
import zdalyapp.mayah.R;
import zdalyapp.mayah.dashboard.DashboardActivity;
import zdalyapp.mayah.global.Constants;
import zdalyapp.mayah.global.Utils;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Dialog loginDialog;
    CheckBox chkTerms;
    boolean termsChecked;
    String userName, passWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ShowView();
    }

    private void ShowView() {
        ImageView imgBotLogo = (ImageView)findViewById(R.id.imageView5);
        imgBotLogo.setImageBitmap(Utils.GetImageFromAssets(this, "zdaly_logo.png"));
        ImageView imgMainLogo = (ImageView)findViewById(R.id.imageView4);
        imgMainLogo.setImageBitmap(Utils.GetImageFromAssets(this, "logo.png"));

        String udata = getString(R.string.terms_usage);
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        ((TextView)findViewById(R.id.textView3)).setText(content);
        edtPassword = (EditText) findViewById(R.id.editText2);
        edtUsername = (EditText) findViewById(R.id.editText);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox) ;
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                termsChecked = b;
            }
        });
        userName = Utils.GetStringFromPreference(Constants.STR_EMAIL, this);
        passWord = Utils.GetStringFromPreference(Constants.STR_PASS, this);
        if (!userName.equals("") && !passWord.equals(""))
        {
            edtUsername.setText(userName);
            edtPassword.setText(passWord);
            LogIn();
        }
        findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(getString(R.string.web_url));

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
                try {
                    startActivity(intent);
                }
                catch (Exception e){};
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (termsChecked == true) {
                    userName = edtUsername.getText().toString();
                    passWord = edtPassword.getText().toString();
                    LogIn();
                }
                else
                    showAlertTerms();

            }
        });
    }

    private void showAlertTerms() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_terms_title);
        builder.setMessage(R.string.alert_terms_message);
        builder.setPositiveButton(R.string.string_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void showDialog()
    {
        loginDialog = new Dialog(this);
        loginDialog.setContentView(R.layout.activity_login_view);
        ImageView imageView = loginDialog.findViewById(R.id.imageView);
        loginDialog.show();
    }
    private void hideDialog()
    {
        if (loginDialog != null)
            loginDialog.dismiss();
    }
    private void LogIn() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if (!validate()) return;
        String url = Constants.API_URL + Constants.LOGIN_URL + "?email=" + userName + "&password=" + passWord;
        showDialog();

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        try {
                            String state = response.getString("state");
                            String token = response.getString("token");
                            String result = response.getString("result");
                            if (state.equals("success"))
                            {
                                Utils.SetStringFromPreference(Constants.TOKEN, token, LoginActivity.this);
                                Utils.SetStringFromPreference(Constants.USERID, result, LoginActivity.this);
                                Utils.SetStringFromPreference(Constants.STR_EMAIL, userName, LoginActivity.this);
                                Utils.SetStringFromPreference(Constants.STR_PASS, passWord, LoginActivity.this);
                                hideDialog();
                                ShowDashBoard();
                            }
                            else
                            {
                                hideDialog();
                                ShowAlertLoginDialog();
                            }
                        } catch (JSONException e) {


                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getLocalizedMessage());
                        hideDialog();
                        ShowAlertLoginDialog();
                    }
                }
        );
// Add the request to the RequestQueue.
        McDomatsApp.getInstance().addToRequestQueue(loginRequest, "loginRequest");
//        LayoutInflater inflater = (LayoutInflater) this
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View layout = inflater.inflate(R.layout.activity_login_view,
//                (ViewGroup) findViewById(R.id.popup_element));
//        PopupWindow pwindo = new PopupWindow(layout, 300, 370, true);
//        pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);


//        Intent intent = new Intent(LoginActivity.this,LoginViewActivity.class);
//        intent.putExtra("name", edtUsername.getText().toString());
//        intent.putExtra("password", edtPassword.getText().toString());
//        startActivityForResult(intent, 2);

    }

    private void ShowAlertLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_terms_title);
        builder.setMessage(R.string.alert_login_message);
        builder.setPositiveButton(R.string.string_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private boolean validate() {
        if (userName.equals(""))
        {
            edtUsername.setError("Email is empty!");
            return false;
        }
        if (passWord.equals(""))
        {
            edtPassword.setError("Password is empty!");
            return false;
        }
        if (!Utils.ValidEmail(userName))
        {
            edtUsername.setError("Please input a validate email!");
            return false;
        }
        return true;
    }

    private void ShowDashBoard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode == 2)
        {
            String message = data.getStringExtra("MESSAGE");
            if (message.equals("OK"))
            {
                Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, "NO", Toast.LENGTH_LONG).show();
            }

        }
    }
}
