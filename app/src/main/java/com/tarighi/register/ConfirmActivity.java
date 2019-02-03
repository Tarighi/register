package com.tarighi.register;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

public class ConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        ((TextView)findViewById(R.id.txtName)).setText(Hawk.get(Constant.Name).toString());
        ((TextView)findViewById(R.id.txtFamliy)).setText(Hawk.get(Constant.Famliy).toString());
        ((TextView)findViewById(R.id.txtAge)).setText(Hawk.get(Constant.Age).toString());
        ((TextView)findViewById(R.id.txtEmail)).setText(Hawk.get(Constant.Email).toString());
        ((TextView)findViewById(R.id.txtMobile)).setText(Hawk.get(Constant.Mobile).toString());

        Button btnSubmit =findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
