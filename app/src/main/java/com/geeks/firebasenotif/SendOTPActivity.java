package com.geeks.firebasenotif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.geeks.firebasenotif.databinding.ActivitySendOtpBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SendOTPActivity extends AppCompatActivity {
    protected ActivitySendOtpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySendOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnGetOtp.setOnClickListener(view -> {
            if (binding.etInputMobile.getText().toString().trim().isEmpty()){
                Toast.makeText(this, "Please Enter Mobile Number", Toast.LENGTH_LONG).show();
            }else {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.btnGetOtp.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+996" + binding.etInputMobile.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        SendOTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                binding.progressBar.setVisibility(View.GONE);
                                binding.btnGetOtp.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                binding.progressBar.setVisibility(View.GONE);
                                binding.btnGetOtp.setVisibility(View.VISIBLE);
                                Toast.makeText(SendOTPActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                binding.progressBar.setVisibility(View.GONE);
                                binding.btnGetOtp.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(getApplicationContext(), VerifyOTPActivity.class);
                                intent.putExtra("mobile", binding.etInputMobile.getText().toString());
                                intent.putExtra("verificationId", verificationId);
                                startActivity(intent);
                            }
                        }
                );
            }
        });
    }
}