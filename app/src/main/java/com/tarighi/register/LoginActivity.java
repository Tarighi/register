package com.tarighi.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.tarighi.register.Helpers.MyBroadcastReceiver;

import java.util.List;


public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Hawk.init(LoginActivity.this).build();

        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.intent.action.INPUT_METHOD_CHANGED");
        MyBroadcastReceiver receiver=new MyBroadcastReceiver();
        registerReceiver(receiver,intentFilter);

        SetView();

        Button btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                EditText edtMobile = findViewById(R.id.edtMobile);
                String mobile=edtMobile.getText().toString();
                try {
                    UserInfo currentUser = (UserInfo) spinner.getSelectedItem();
                    if (currentUser == null) {
                        Toast.makeText(LoginActivity.this, getString(R.string.no_any_user), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mobile.length()==0) {
                        Toast.makeText(LoginActivity.this, getString(R.string.login_empty_mobile), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (currentUser.Mobile == Long.parseLong(mobile)) {
                        Toast.makeText(LoginActivity.this, getString(R.string.login_welcome_msg), Toast.LENGTH_SHORT).show();

                        UserController.INSTANCE.signIn(currentUser);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.login_wrong_mobile), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {

                    Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnSignUP = findViewById(R.id.btnSignUp);
        btnSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, Constant.RegisterRequestCode);
            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();
        UserController.INSTANCE.signOut();
        SetView();
    }

    private void SetView() {

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<UserInfo> adapter = new ArrayAdapter<UserInfo>(this, android.R.layout.simple_spinner_item, UserController.INSTANCE.getUsers());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.RegisterRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                SetView();
                if (UserController.INSTANCE.getCurrentUser()!=null) {
                    Toast.makeText(this, getString(R.string.successfulConfirm), Toast.LENGTH_LONG).show();
                }
            }
        }

    }
}
