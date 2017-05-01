package com.silk.smartdoc.View;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.silk.smartdoc.Model.Person;
import com.silk.smartdoc.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SignUp extends AppCompatActivity {

    TextView sdlogo;
    EditText nameET;
    EditText dobET;
    RadioButton rmale;
    RadioButton rfemale;
    RadioButton rothers;
    EditText emailEt;
    EditText passwordET;
    EditText cnfpassET;
    EditText registrationET;
    Switch docSwitch;
    Button signup;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sdlogo = (TextView)findViewById(R.id.sdLogo);
        nameET = (EditText)findViewById(R.id.nameEditText);
        dobET = (EditText)findViewById(R.id.dobEditText);
        rmale = (RadioButton)findViewById(R.id.maleRadioButton);
        rfemale = (RadioButton)findViewById(R.id.femaleRadioButton);
        rothers = (RadioButton)findViewById(R.id.othersRadioButton);
        emailEt = (EditText)findViewById(R.id.usernameEditText);
        passwordET = (EditText)findViewById(R.id.passwordEditText);
        cnfpassET = (EditText)findViewById(R.id.confirmPwEditText);
        registrationET = (EditText)findViewById(R.id.regNoEditText);
        docSwitch = (Switch)findViewById(R.id.isDocSwitch);
        signup = (Button)findViewById(R.id.signUpButton);

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"font/Satisfy-Regular.ttf");
        sdlogo.setTypeface(myCustomFont);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));

        rmale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rmale.isChecked()==true){
                    rfemale.setChecked(false);
                    rothers.setChecked(false);
                }
            }
        });
        rfemale.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rfemale.isChecked()==true){
                    rmale.setChecked(false);
                    rothers.setChecked(false);
                }
            }
        });
        rothers.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rothers.isChecked()==true){
                    rmale.setChecked(false);
                    rfemale.setChecked(false);
                }
            }
        });
        docSwitch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(docSwitch.isChecked()==true){
                    registrationET.setEnabled(true);
                    registrationET.setText(" ");
                }
                else {
                    registrationET.setEnabled(false);
                    registrationET.setText("0");
                }
            }
        });

        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dobET.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SignUp.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        nameET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(nameET.getBackground()==getDrawable(R.drawable.error_edit_text)){
                    nameET.setBackground(getDrawable(R.drawable.normal_edit_txt));
                    nameET.setTextColor(Color.WHITE);
                    nameET.setText("");
                }
            }
        });
        dobET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(dobET.getBackground()==getDrawable(R.drawable.error_edit_text)){
                    dobET.setBackground(getDrawable(R.drawable.normal_edit_txt));
                    dobET.setTextColor(Color.WHITE);
                    dobET.setText("");
                }
            }
        });
        emailEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(emailEt.getBackground()==getDrawable(R.drawable.error_edit_text)){
                    emailEt.setBackground(getDrawable(R.drawable.normal_edit_txt));
                    emailEt.setTextColor(Color.WHITE);
                    emailEt.setText("");
                }
            }
        });
        passwordET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(passwordET.getBackground()==getDrawable(R.drawable.error_edit_text)){
                    passwordET.setBackground(getDrawable(R.drawable.normal_edit_txt));
                    passwordET.setTextColor(Color.WHITE);
                    passwordET.setText("");
                }
            }
        });
        cnfpassET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(cnfpassET.getBackground()==getDrawable(R.drawable.error_edit_text)){
                    cnfpassET.setBackground(getDrawable(R.drawable.normal_edit_txt));
                    cnfpassET.setTextColor(Color.WHITE);
                    cnfpassET.setText("");
                }
            }
        });



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                Date dob = new Date(dobET.getText().toString());
                String email = emailEt.getText().toString();
                String password = passwordET.getText().toString();
                String cnfpassword = cnfpassET.getText().toString();
                String regno = registrationET.getText().toString();
                String sex ;
                if(rmale.isChecked()==true)
                    sex="Male";
                else if(rmale.isChecked()==true)
                    sex="Female";
                else
                    sex="Others";
                Boolean isDoctor;
                if(docSwitch.isChecked()==true)
                    isDoctor = true;
                else
                    isDoctor = false;

                if(name.equals("")){
                    nameET.setBackground(getDrawable(R.drawable.error_edit_text));
                    nameET.setTextColor(Color.RED);
                    nameET.setText("Enter Your Name");
                }
                if(dob.equals("")){
                    dobET.setBackground(getDrawable(R.drawable.error_edit_text));
                    dobET.setTextColor(Color.RED);
                    dobET.setText("Enter Your Date of Birth");
                }
                if(email.equals("")){
                    emailEt.setBackground(getDrawable(R.drawable.error_edit_text));
                    emailEt.setTextColor(Color.RED);
                    emailEt.setText("Enter Your Email-id");
                }
                Boolean isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
                if (isValidEmail == false)
                {
                    emailEt.setBackground(getDrawable(R.drawable.error_edit_text));
                    emailEt.setTextColor(Color.RED);
                    emailEt.setText("Enter Your Email-id in correct format ");
                }
                if(password.equals("")){
                    passwordET.setBackground(getDrawable(R.drawable.error_edit_text));
                    passwordET.setTextColor(Color.RED);
                    passwordET.setText("Enter Your Password");
                }
                if(cnfpassword.equals("")){
                    cnfpassET.setBackground(getDrawable(R.drawable.error_edit_text));
                    cnfpassET.setTextColor(Color.RED);
                    cnfpassET.setText("Enter Your Password Again");
                }

                if(password.equals(cnfpassword)){

                    Person person = new Person(name, email, password, dob, sex, email, isDoctor, regno);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                    ref.child(email).setValue(person);
                }
                else
                {
                    cnfpassET.setBackground(getDrawable(R.drawable.error_edit_text));
                    cnfpassET.setTextColor(Color.RED);
                    cnfpassET.setText("Passwords do not match ");
                    cnfpassET.setInputType(InputType.TYPE_CLASS_TEXT);
                }



            }
        });


    }
    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dobET.setText(sdf.format(myCalendar.getTime()));
    }
}
