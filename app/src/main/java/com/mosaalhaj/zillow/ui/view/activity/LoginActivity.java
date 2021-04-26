package com.mosaalhaj.zillow.ui.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mosaalhaj.zillow.R;
import com.mosaalhaj.zillow.databinding.ActivityLoginBinding;
import com.mosaalhaj.zillow.model.Response;
import com.mosaalhaj.zillow.response.LoginResponse;
import com.mosaalhaj.zillow.ui.viewmodel.LoginViewModel;

import static com.mosaalhaj.zillow.item.Constants.ACCESS_TOKEN;
import static com.mosaalhaj.zillow.item.Constants.REFRESH_TOKEN;
import static com.mosaalhaj.zillow.item.Constants.REMEMBER_ME;
import static com.mosaalhaj.zillow.item.Constants.SHARED_PREFERENCE_FILE;
import static com.mosaalhaj.zillow.item.Constants.USER_EMAIL;
import static com.mosaalhaj.zillow.item.Constants.USER_ID;
import static com.mosaalhaj.zillow.item.Constants.USER_PASSWORD;
import static com.mosaalhaj.zillow.item.Settings.isEmailValid;

public class LoginActivity extends AppCompatActivity {

    public final static int REGISTER_REQUEST_CODE = 122;
    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;
    private boolean emailValid = false, passwordValid = false;
    private SharedPreferences preferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        preferences = getSharedPreferences(SHARED_PREFERENCE_FILE,MODE_PRIVATE);

        Observer<Response<LoginResponse>> observer = response -> {

            if (response.isSucceeded() && response.getData() != null) {

                try{

                    storeUserData(response.getData());
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                startActivity(intent);
                finish();

            } else
                Toast.makeText(getBaseContext(), response.getMessage(), Toast.LENGTH_SHORT).show();

            enableLoginButton();
        };

        viewModel.liveData.observe(this, observer);

        binding.loginBtLogin.setOnClickListener(lis -> {

            disableLoginButton();

            String email = binding.loginEtEmail.getText().toString();
            String pass = binding.loginEtPassword.getText().toString();

            if (!email.isEmpty() && !pass.isEmpty()) {

                viewModel.login(email, pass);

            } else
                Toast.makeText(getBaseContext(), "Please Check Email and Password is not Empty", Toast.LENGTH_SHORT).show();


        });

        binding.loginTvRegister.setOnClickListener(lis -> {
            Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
            startActivityForResult(intent, REGISTER_REQUEST_CODE);
        });

        binding.loginEtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence email, int start, int before, int count) {
                if (!isEmailValid(email.toString())) {
                    emailValid = false;
                    binding.loginEtEmail.setError("Email not Vaild");
                } else
                    emailValid = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                buttonConfirm();
            }
        });

        binding.loginEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence pass, int start, int before, int count) {
                if (pass.length() < 7 ) {
                    passwordValid = false;
                    binding.loginEtPassword.setError("Make Sure Password Contain numbers & characters");
                } else
                    passwordValid = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                buttonConfirm();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REGISTER_REQUEST_CODE && resultCode == RESULT_OK)
            if (data != null) {

                binding.loginEtEmail.setText(data.getStringExtra(USER_EMAIL));
                binding.loginEtPassword.setText(data.getStringExtra(USER_PASSWORD));
                enableLoginButton();
            }


    }

    private void buttonConfirm() {
        if (emailValid && passwordValid) {
            enableLoginButton();
        } else if (binding.loginBtLogin.isEnabled()) {
            disableLoginButton();
        }
    }

    private void disableLoginButton() {
        binding.loginBtLogin.setEnabled(false);
        binding.loginBtLogin.setBackgroundResource(R.drawable.app_button_background_disabled);
    }

    private void enableLoginButton() {
        binding.loginBtLogin.setEnabled(true);
        binding.loginBtLogin.setBackgroundResource(R.drawable.app_button_background);
    }

    @SuppressLint({"CommitPrefEdits", "ApplySharedPref"})
    private void storeUserData (LoginResponse loginResponse){
         SharedPreferences.Editor editor = preferences.edit();

         editor.putString(USER_ID,loginResponse.getUserId());
         editor.putString(REFRESH_TOKEN,loginResponse.getRefreshToken());
         editor.putString(ACCESS_TOKEN,loginResponse.getTokenResponse().getToken());
         if (binding.loginCbRememberMe.isChecked()){

             String email = binding.loginEtEmail.getText().toString();
             String pass = binding.loginEtPassword.getText().toString();

             editor.putString(USER_EMAIL,email);
             editor.putString(USER_PASSWORD,pass);
             editor.putBoolean(REMEMBER_ME,true);
         } else
             editor.putBoolean(REMEMBER_ME,false);

         editor.commit();
    }



}