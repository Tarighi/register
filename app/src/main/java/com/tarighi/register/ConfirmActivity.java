package com.tarighi.register;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        ((TextView)findViewById(R.id.txtName)).setText(getIntent().getStringExtra("Name"));
        ((TextView)findViewById(R.id.txtFamliy)).setText(getIntent().getStringExtra("Famliy"));
        ((TextView)findViewById(R.id.txtAge)).setText(getIntent().getStringExtra("Age"));
        ((TextView)findViewById(R.id.txtEmail)).setText(getIntent().getStringExtra("Email"));
        ((TextView)findViewById(R.id.txtMobile)).setText(getIntent().getStringExtra("Mobile"));
    }
    public void Submit(View v) {

        Toast.makeText(this, getString(R.string.successfulConfirm), Toast.LENGTH_LONG).show();

    }

}
