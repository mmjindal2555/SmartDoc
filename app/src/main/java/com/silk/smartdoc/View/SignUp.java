package com.silk.smartdoc.View;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    Button verifyButton;
    Button signup;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    FirebaseAuth mAuth;

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
        verifyButton = (Button)findViewById(R.id.verifyEmailButton);
        signup = (Button)findViewById(R.id.signUpButton);

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"font/Satisfy-Regular.ttf");
        sdlogo.setTypeface(myCustomFont);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));

        mAuth = FirebaseAuth.getInstance();

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
                dobET.setBackground(getDrawable(R.drawable.normal_edit_txt));
            }
        });

        nameET.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                nameET.setBackground(getDrawable(R.drawable.normal_edit_txt));
                return false;
            }
        });
        emailEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                emailEt.setBackground(getDrawable(R.drawable.normal_edit_txt));
                return false;
            }
        });
        passwordET.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                passwordET.setBackground(getDrawable(R.drawable.normal_edit_txt));
                return false;
            }
        });
        cnfpassET.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                cnfpassET.setBackground(getDrawable(R.drawable.normal_edit_txt));
                return false;
            }
        });



        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean nameIsValid = true,
                        dobIsValid = true,
                        passwordIsValid = true,
                        cnfPassIsValid = true;
                final String name = nameET.getText().toString();
                if(name.equals("")){
                    nameET.setBackground(getDrawable(R.drawable.error_edit_text));
                    nameET.setHintTextColor(getResources().getColor(R.color.error_on_blue));
                    nameET.setHint("Name is required");
                    nameIsValid = false;
                }
                Date dob=new Date();
                if(dobET.getText().toString().equals("")){
                    dobET.setBackground(getDrawable(R.drawable.error_edit_text));
                    dobET.setHintTextColor(getResources().getColor(R.color.error_on_blue));
                    dobET.setHint("Date of Birth is required");
                    dobIsValid = false;
                }
                else{
                    dob = new Date(dobET.getText().toString());
                }
                Date checkDate = new Date(2010-1900,1,1);
                if(!dob.before(checkDate)){
                    dobIsValid = false;
                    dobET.setBackground(getDrawable(R.drawable.error_edit_text));
                    dobET.setText("");
                    dobET.setHintTextColor(getResources().getColor(R.color.error_on_blue));
                    dobET.setHint("Date of Birth is not valid");
                }
                final String email = emailEt.getText().toString();
                final String password = passwordET.getText().toString();
                String cnfpassword = cnfpassET.getText().toString();
                final String regno = registrationET.getText().toString();
                final String sex ;
                boolean sexIsValid = true;
                if(rmale.isChecked())
                    sex="Male";
                else if(rfemale.isChecked())
                    sex="Female";
                else
                    sex="Others";
                final Boolean isDoctor;
                if(docSwitch.isChecked())
                    isDoctor = true;
                else
                    isDoctor = false;
                boolean isDocValid = true;
                Boolean isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
                if(email.equals("") || (!isValidEmail))
                {
                    emailEt.setBackground(getDrawable(R.drawable.error_edit_text));
                    emailEt.setHintTextColor(getResources().getColor(R.color.error_on_blue));
                    emailEt.setText("");
                    emailEt.setHint("Enter Your Email-id in correct format ");
                    isValidEmail = false;
                }
                if(isDoctor && regno.equals("")){
                    registrationET.setBackground(getDrawable(R.drawable.error_edit_text));
                    registrationET.setHintTextColor(getResources().getColor(R.color.error_on_blue));
                    registrationET.setHint("Registration Number Required");
                    isDocValid = false;
                }
                if(password.equals("")){
                    passwordET.setBackground(getDrawable(R.drawable.error_edit_text));
                    passwordET.setHintTextColor(getResources().getColor(R.color.error_on_blue));
                    passwordET.setHint("Enter Your Password");
                    passwordIsValid = false;
                }
                else if(cnfpassword.equals("") || !password.equals(cnfpassword)){
                    cnfpassET.setBackground(getDrawable(R.drawable.error_edit_text));
                    cnfpassET.setHintTextColor(getResources().getColor(R.color.error_on_blue));
                    cnfpassET.setText("");
                    cnfpassET.setHint("Passwords don't match");
                    cnfPassIsValid = false;
                }
                if(nameIsValid && dobIsValid && isValidEmail && passwordIsValid && cnfPassIsValid
                        && sexIsValid && isDocValid){

                    final Date finalDob = dob;

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        if (user != null) {
                                            user.sendEmailVerification()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(SignUp.this,"Please Verify by going " +
                                                                                "through your email",
                                                                        Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });
                                        }
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                                        String id = ref.push().getKey();
                                        Person person = new Person(name, email, password, finalDob,
                                                sex, email, isDoctor, regno,null,null,id);
                                        ref.child(id).setValue(person);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SignUp.this, task.getException().getMessage()+"",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

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
