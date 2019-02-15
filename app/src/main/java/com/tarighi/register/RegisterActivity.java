package com.tarighi.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {


    UserInfo currentUser;
    Spinner sprCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sprCity = findViewById(R.id.sprCity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprCity.setAdapter(adapter);

        currentUser = UserController.INSTANCE.getCurrentUser();
        if (currentUser != null) {

            ((EditText) findViewById(R.id.edtName)).setText(currentUser.FirstName);
            ((EditText) findViewById(R.id.edtFamliy)).setText(currentUser.Family);
            ((EditText) findViewById(R.id.edtAge)).setText(Integer.toString(currentUser.Age));
            ((EditText) findViewById(R.id.edtEmail)).setText(currentUser.Email);
            ((EditText) findViewById(R.id.edtMobile)).setText(Long.toString(currentUser.Mobile));
            if (currentUser.AVATAR != null && currentUser.AVATAR.length() > 0) {
                Uri selectedImage = Uri.parse(currentUser.AVATAR);
                RoundedImageView imageView = (RoundedImageView) findViewById(R.id.imgAvatar);
                imageView.setImageURI(selectedImage);
                imageView.setTag(selectedImage.toString());
            }
            if (currentUser.City != null && currentUser.City.length() > 0) {

                int spinnerPosition = adapter.getPosition(currentUser.City);
                sprCity.setSelection(spinnerPosition);
            }
        }

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtName = findViewById(R.id.edtName);
                EditText edtFamliy = findViewById(R.id.edtFamliy);
                EditText edtAge = findViewById(R.id.edtAge);
                EditText edtEmail = findViewById(R.id.edtEmail);
                EditText edtMobile = findViewById(R.id.edtMobile);
                RoundedImageView imgAvatar = (RoundedImageView) findViewById(R.id.imgAvatar);
                sprCity = findViewById(R.id.sprCity);
                String FirstName = edtName.getText().toString();
                String Family = edtFamliy.getText().toString();
                if (edtAge.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "enter your age", Toast.LENGTH_SHORT).show();
                    return;
                }
                int Age = Integer.parseInt(edtAge.getText().toString());
                String Email = edtEmail.getText().toString();
                if (edtMobile.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "enter your mobile", Toast.LENGTH_SHORT).show();
                    return;
                }
                long Mobile = Long.parseLong(edtMobile.getText().toString());
                if (imgAvatar.getTag() == null) {
                    Toast.makeText(RegisterActivity.this, "select your avatar", Toast.LENGTH_SHORT).show();
                    return;
                }
                String AVATAR = imgAvatar.getTag().toString();
                String City = sprCity.getSelectedItem().toString();

                if (City.length() < 3) {
                    Toast.makeText(RegisterActivity.this, "enter your city", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Mobile > 0 && FirstName.length() > 0 && Family.length() > 0) {
                    if (currentUser == null)
                        currentUser = new UserInfo();
                    currentUser.Mobile = Mobile;
                    currentUser.FirstName = FirstName;
                    currentUser.Family = Family;
                    currentUser.Age = Age;
                    currentUser.Email = Email;
                    currentUser.AVATAR = AVATAR;
                    currentUser.City = City;

                    UserController.INSTANCE.addOrUpdateUser(currentUser);
                    UserController.INSTANCE.signIn(currentUser);
                } else {
                    UserController.INSTANCE.signOut();
                }
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(R.id.edtName)).setText("");
                ((EditText) findViewById(R.id.edtFamliy)).setText("");
                ((EditText) findViewById(R.id.edtAge)).setText("");
                ((EditText) findViewById(R.id.edtEmail)).setText("");
                ((EditText) findViewById(R.id.edtMobile)).setText("");
                ((Spinner) findViewById(R.id.sprCity)).setSelection(0);
                ((RoundedImageView) findViewById(R.id.imgAvatar)).setTag("");
                ((RoundedImageView) findViewById(R.id.imgAvatar)).setImageURI(Uri.parse(""));
            }
        });

        RoundedImageView imgAvatar = findViewById(R.id.imgAvatar);
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pix.start(RegisterActivity.this, Constant.ImageSelectorRequestCode);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == Constant.ImageSelectorRequestCode) {
            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);

            RoundedImageView imageView = (RoundedImageView) findViewById(R.id.imgAvatar);
            Uri selectedImage = Uri.parse(returnValue.get(0));
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
