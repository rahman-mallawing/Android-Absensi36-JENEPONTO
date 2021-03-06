package com.si.uinam.absensi36restfull;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.si.uinam.absensi36restfull.models.UserModel;
import com.si.uinam.absensi36restfull.services.App;

public class LoginActivity extends AppCompatActivity {

    private static final int RESULT_CODE = 200;
    public static final String EXTRA_NAME = "EXTRA-NAME";
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    private static final String TAG = "LoginActivity";
    private LinearLayout llKejati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        edtUsername = findViewById(R.id.edt_username);
        edtPassword= findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        llKejati = findViewById(R.id.ll_kejati);
        ((GradientDrawable) llKejati.getBackground()).setColor(Color.LTGRAY);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }


    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed("Pastikan anda mengisi user-password");
            return;
        }

        btnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.CustomProgress);


        //progressDialog.setContentView(R.layout.item_progress_identity);
        progressDialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        final App app;
        app = App.getAppInstance(this,null);
        Call<UserModel> call = app.getLoginService().getUser(username, password);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Log.d("RESPONSE", response.message());
                if(response.isSuccessful()){
                    onLoginSuccess(app, response.body());
                    Log.d("TOKEN", response.body().getApiToken());

                }else{
                    Log.d("RESPONSE", response.message());
                    onLoginFailed(response.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("ERROR-RES", t.getMessage());
                progressDialog.dismiss();
                onLoginFailed(t.getMessage());
            }
        });


        // TODO: Implement your own authentication logic here.


    }


    public boolean validate() {
        boolean valid = true;

        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        if (username.isEmpty()) {
            edtUsername.setError("enter a valid username");
            valid = false;
        } else {
            edtUsername.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            edtPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            edtPassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(App app, UserModel user) {
        app.getSession().saveUser(this, user);
        Log.d("LOG-USER","SAVE USER: LOGIN ACTIVITY");
        btnLogin.setEnabled(true);
        Intent intentResult = new Intent();
        intentResult.putExtra(LoginActivity.EXTRA_NAME, user.getNamaOperator());
        setResult(LoginActivity.RESULT_CODE, intentResult);
        finish();
    }

    public void onLoginFailed(String err) {
        Toast.makeText(getBaseContext(), err, Toast.LENGTH_LONG).show();

        btnLogin.setEnabled(true);
    }


}
