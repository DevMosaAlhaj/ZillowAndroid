package com.mosaalhaj.zillow.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.mosaalhaj.zillow.R;
import com.mosaalhaj.zillow.databinding.ActivityRegisterBinding;
import com.mosaalhaj.zillow.model.User;
import com.mosaalhaj.zillow.viewmodel.RegisterViewModel;

import static com.mosaalhaj.zillow.item.Constants.USER_EMAIL;
import static com.mosaalhaj.zillow.item.Constants.USER_PASSWORD;
import static com.mosaalhaj.zillow.item.Settings.isEmailValid;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel viewModel;
    private ActivityRegisterBinding binding;
    private boolean firstNameValid, lastNameValid, emailValid, phoneValid, passwordValid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =
                DataBindingUtil.setContentView(this, R.layout.activity_register);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.setLifecycleOwner(this);
        initBool();

        viewModel.liveData.observe(this, response -> {
            if (!response.getData().isEmpty()) {
                String email = binding.registerEtEmail.getText().toString();
                String pass = binding.registerEtPassword.getText().toString();
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                intent.putExtra(USER_EMAIL, email);
                intent.putExtra(USER_PASSWORD, pass);
                setResult(RESULT_OK, intent);
                finish();
            } else
                enableRegisterButton();

            Toast.makeText(getBaseContext(), response.getMessage(), Toast.LENGTH_SHORT).show();

        });

        binding.registerBtRegister.setOnClickListener(lis -> {
            disableRegisterButton();

            String firstName = binding.registerEtFirstName.getText().toString();
            String lastName = binding.registerEtLastName.getText().toString();
            String email = binding.registerEtEmail.getText().toString();
            String phone = binding.registerEtPhone.getText().toString();
            String password = binding.registerEtPassword.getText().toString();

            User user = new User();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhoneNumber(phone);
            user.setPassword(password);

            viewModel.register(user);
        });

        binding.registerEtFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence firstName, int start, int before, int count) {
                if (firstName.length() < 4) {
                    firstNameValid = false;
                    binding.registerEtFirstName.setError("First Name is too Short");
                } else
                    firstNameValid = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                buttonConfirm();
            }
        });

        binding.registerEtLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence lastName, int start, int before, int count) {
                if (lastName.length() < 5) {
                    lastNameValid = false;
                    binding.registerEtLastName.setError("Last Name is too Short");
                } else
                    lastNameValid = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                buttonConfirm();
            }
        });

        binding.registerEtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence email, int start, int before, int count) {
                if (!isEmailValid(email.toString())) {
                    emailValid = false;
                    binding.registerEtEmail.setError("Email not Valid");
                } else
                    emailValid = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                buttonConfirm();
            }
        });

        binding.registerEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence phone, int start, int before, int count) {
                if (phone.length() != 10) {
                    phoneValid = false;
                    binding.registerEtPhone.setError("Phone Number must be 10 Numbers");
                } else
                    phoneValid = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                buttonConfirm();
            }
        });

        binding.registerEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence pass, int start, int before, int count) {
                if (pass.length() < 8 && !pass.toString().matches("(.*[0-9].*)")) {
                    passwordValid = false;
                    binding.registerEtPassword.setError("Make Sure Password Contain numbers & characters");
                } else
                    passwordValid = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                buttonConfirm();
            }
        });
    }

    private void initBool() {
        firstNameValid = false;
        lastNameValid = false;
        emailValid = false;
        passwordValid = false;
        phoneValid = false;
    }

    private void buttonConfirm() {
        if (emailValid && passwordValid && firstNameValid && lastNameValid && phoneValid) {
            enableRegisterButton();
        } else if (binding.registerBtRegister.isEnabled()) {
            disableRegisterButton();
        }
    }

    private void disableRegisterButton() {
        binding.registerBtRegister.setEnabled(false);
        binding.registerBtRegister.setBackgroundResource(R.drawable.app_button_background_disabled);
    }

    private void enableRegisterButton() {
        binding.registerBtRegister.setEnabled(true);
        binding.registerBtRegister.setBackgroundResource(R.drawable.app_button_background);
    }

}