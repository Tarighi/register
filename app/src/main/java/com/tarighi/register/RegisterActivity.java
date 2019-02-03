package com.tarighi.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Hawk.init(RegisterActivity.this).build();

        if (Hawk.contains(Constant.Name)) {
            ((EditText) findViewById(R.id.edtName)).setText(Hawk.get(Constant.Name).toString());
            ((EditText) findViewById(R.id.edtFamliy)).setText(Hawk.get(Constant.Famliy).toString());
            ((EditText) findViewById(R.id.edtAge)).setText(Hawk.get(Constant.Age).toString());
            ((EditText) findViewById(R.id.edtEmail)).setText(Hawk.get(Constant.Email).toString());
            ((EditText) findViewById(R.id.edtMobile)).setText(Hawk.get(Constant.Mobile).toString());
        }
    }

    public void registerMe(View v) {

        String Name = ((EditText) findViewById(R.id.edtName)).getText().toString();
        String Famliy = ((EditText) findViewById(R.id.edtFamliy)).getText().toString();
        String Age = ((EditText) findViewById(R.id.edtAge)).getText().toString();
        String Email = ((EditText) findViewById(R.id.edtEmail)).getText().toString();
        String Mobile = ((EditText) findViewById(R.id.edtMobile)).getText().toString();

        if (Name.length() > 0) {
            Hawk.put(Constant.Name, Name);
            Hawk.put(Constant.Famliy, Famliy);
            Hawk.put(Constant.Age, Age);
            Hawk.put(Constant.Email, Email);
            Hawk.put(Constant.Mobile, Mobile);

        } else {
            Hawk.delete(Constant.Name);
            Hawk.delete(Constant.Famliy);
            Hawk.delete(Constant.Age);
            Hawk.delete(Constant.Email);
            Hawk.delete(Constant.Mobile);
        }
        setResult(Activity.RESULT_OK);
        finish();

    }
}
