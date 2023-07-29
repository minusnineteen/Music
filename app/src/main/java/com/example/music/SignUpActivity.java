package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    Button tv_HaveAccount;
    Button buttonRegister;
    EditText edt_userName;
    EditText edt_pass2;
    EditText edt_pass1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        tv_HaveAccount = findViewById(R.id.tv_HaveAccount);
        buttonRegister = findViewById(R.id.txt_Register);
        edt_userName = findViewById(R.id.edt_userName);
        edt_pass2 = findViewById(R.id.edt_pass2);
        edt_pass1 = findViewById(R.id.edt_pass1);

        tv_HaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username1 = edt_userName.getText().toString();
                String password1 = edt_pass1.getText().toString();
                String confirmPassword = edt_pass2.getText().toString();

                if (password1.equals(confirmPassword)) {
                    // mật khẩu trùng khớp
                    //  đăng ký
                    DatabaseHelper databaseHelper = new DatabaseHelper(SignUpActivity.this);
                    long newRowId = databaseHelper.addUser(username1, password1);

                    if (newRowId != -1) {
                        Toast.makeText(SignUpActivity.this, "Register successful", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(SignUpActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //  không trùng khớp
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}