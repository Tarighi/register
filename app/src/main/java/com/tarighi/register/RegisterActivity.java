package com.tarighi.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

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
            Uri selectedImage=Uri.parse(Hawk.get(Constant.AVATAR).toString());
            ImageView imageView = (ImageView) findViewById(R.id.imgAvatar);
            imageView.setImageURI(selectedImage);
            imageView.setTag(selectedImage.toString());
        }


    }

    public void RegisterMe(View v) {

        String Name = ((EditText) findViewById(R.id.edtName)).getText().toString();
        String Famliy = ((EditText) findViewById(R.id.edtFamliy)).getText().toString();
        String Age = ((EditText) findViewById(R.id.edtAge)).getText().toString();
        String Email = ((EditText) findViewById(R.id.edtEmail)).getText().toString();
        String Mobile = ((EditText) findViewById(R.id.edtMobile)).getText().toString();
        String AVATAR = ((ImageView) findViewById(R.id.imgAvatar)).getTag().toString();

        if (Name.length() > 0) {
            Hawk.put(Constant.Name, Name);
            Hawk.put(Constant.Famliy, Famliy);
            Hawk.put(Constant.Age, Age);
            Hawk.put(Constant.Email, Email);
            Hawk.put(Constant.Mobile, Mobile);
            Hawk.put(Constant.AVATAR, AVATAR);

        } else {
            Hawk.delete(Constant.Name);
            Hawk.delete(Constant.Famliy);
            Hawk.delete(Constant.Age);
            Hawk.delete(Constant.Email);
            Hawk.delete(Constant.Mobile);
            Hawk.delete(Constant.AVATAR);
        }
        setResult(Activity.RESULT_OK);
        finish();

    }

    protected void SelectPhoto(View v) {
        Pix.start(RegisterActivity.this, Constant.ImageSelectorRequestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == Constant.ImageSelectorRequestCode) {
            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);

            ImageView imageView = (ImageView) findViewById(R.id.imgAvatar);
            Uri selectedImage=Uri.parse(returnValue.get(0));
            imageView.setImageURI(selectedImage);
            imageView.setTag(selectedImage.toString());


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(RegisterActivity.this, Constant.ImageSelectorRequestCode);
                } else {
                    Toast.makeText(RegisterActivity.this, "Approve permissions to open ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
