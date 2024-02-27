package com.geeks.firebasenotif;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.geeks.firebasenotif.databinding.ActivityVerifyOtpBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity {
    private ActivityVerifyOtpBinding binding;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.textMobile.setText(String.format(
                "+996-%s", getIntent().getStringExtra("mobile")
        ));

        setupOTPInputs();

        verificationId = getIntent().getStringExtra("verificationId");

        binding.btnVerify.setOnClickListener(view -> {
            if (binding.inputCode1.getText().toString().trim().isEmpty()
                    || binding.inputCode2.getText().toString().trim().isEmpty()
                    || binding.inputCode3.getText().toString().trim().isEmpty()
                    || binding.inputCode4.getText().toString().trim().isEmpty()
                    || binding.inputCode5.getText().toString().trim().isEmpty()
                    || binding.inputCode6.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Please Enter valid Code", Toast.LENGTH_SHORT).show();
            }

            String code =
                    binding.inputCode1.getText().toString() +
                            binding.inputCode2.getText().toString() +
                            binding.inputCode3.getText().toString() +
                            binding.inputCode4.getText().toString() +
                            binding.inputCode5.getText().toString() +
                            binding.inputCode6.getText().toString();

            if (verificationId != null){
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.btnVerify.setVisibility(View.INVISIBLE);

                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                        verificationId,
                        code
                );
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                        .addOnCompleteListener(task -> {
                            binding.progressBar.setVisibility(View.GONE);
                            binding.btnVerify.setVisibility(View.VISIBLE);
                            if (task.isSuccessful()){
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else {
                                Toast.makeText(this, "The verification code is invalid", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        binding.textResendOTP.setOnClickListener(view -> {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+996" + getIntent().getStringExtra("mobile"),
                    60,
                    TimeUnit.SECONDS,
                    VerifyOTPActivity.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {}
                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.makeText(VerifyOTPActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            verificationId = newVerificationId;
                            Toast.makeText(VerifyOTPActivity.this, "The code is sent again!", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        });
    }

    private void setupOTPInputs() {
        binding.inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    binding.inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        binding.inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    binding.inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        binding.inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    binding.inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        binding.inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    binding.inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        binding.inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    binding.inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}