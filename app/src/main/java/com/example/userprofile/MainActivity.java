package com.example.userprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    EditText etAddress;
    EditText etContry;
    EditText etHobby;
    EditText etMail;
    EditText etPhone;
    RadioGroup rg;
    Button btnDob;
    Button SignIn;
    Spinner spSchool, spCollege;
    SeekBar seekSchool, seekCollege;
    TextView tvPercentage;
    TextView tvCGPA;
    String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    String MobilePattern = "[0-9]{10}";
    String[] spsch = {"SSC","HSC"};
    String[] spclg = {"B.Com","BCA"};
    String selSchool,selCollege,gender;
    int percentage=0, CGPA=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etContry = findViewById(R.id.etContry);
        etHobby = findViewById(R.id.etHobby);
        etMail = findViewById(R.id.etMail);
        etPhone = findViewById(R.id.etPhone);
        rg = findViewById(R.id.rgGender);
        btnDob = findViewById(R.id.btnDOB);
        spSchool = findViewById(R.id.spSchool);
        spCollege = findViewById(R.id.spCollege);
        seekSchool = findViewById(R.id.seekSchool);
        seekCollege = findViewById(R.id.seekCollege);
        SignIn = findViewById(R.id.btnSignIn);
        tvPercentage = findViewById(R.id.tvPercentage);
        tvCGPA = findViewById(R.id.tvCGPA);


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = radioGroup.findViewById(i);
                gender = rb.getText().toString();

            }
        });

        Calendar myCalendar= Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                String myFormat="MM/dd/yy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                btnDob.setText(dateFormat.format(myCalendar.getTime()));
            }
        };
        btnDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this,
                        date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,spsch);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,spclg);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSchool.setAdapter(adapter1);
        spCollege.setAdapter(adapter2);
        spSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selSchool = spsch[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selSchool = "";
            }
        });
        spCollege.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selCollege = spclg[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selCollege = "";
            }
        });

        seekSchool.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvPercentage.setText(Integer.toString(i)+"%");
                percentage = i;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekCollege.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvCGPA.setText("CGPA "+Integer.toString(i));
                CGPA = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEmpty() || rg.getCheckedRadioButtonId()==-1 || selSchool.isEmpty() || selCollege.isEmpty() || percentage==0 || CGPA==0 || btnDob.getText().toString().contains("DOB") ){
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
                else if(!etPhone.getText().toString().matches(MobilePattern) || !etMail.getText().toString().matches(emailPattern)) {
                    etPhone.setError("Not valid input");
                    etMail.setError("Not valid input");
                }
                else {
                    Toast.makeText(MainActivity.this, "signing Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.English:
                changeLanguage(MainActivity.this,"en");
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                break;
            case R.id.Hindi:
                changeLanguage(MainActivity.this,"hi");
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeLanguage(MainActivity mainActivity, String lang) {
        Locale local = new Locale(lang);
        Resources resources = mainActivity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(local);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

    }


    private boolean checkEmpty() {
        Boolean valid = false;
        if ( TextUtils.isEmpty(etName.getText().toString()) ) {
            etName.setError("First name cannot be empty");
            valid = true;
        }
        if ( TextUtils.isEmpty(etAddress.getText().toString()) ) {
            etAddress.setError("last name cannot be empty");
            valid = true;
        }
        if ( TextUtils.isEmpty(etContry.getText().toString()) ) {
            etContry.setError("email cannot be empty");
            valid = true;
        }
        if ( TextUtils.isEmpty(etHobby.getText().toString()) ) {
            etHobby.setError("address cannot be empty");
            valid = true;
        }
        if ( TextUtils.isEmpty(etMail.getText().toString()) ) {
            etMail.setError("mobile cannot be empty");
            valid = true;
        }
        if ( TextUtils.isEmpty(etPhone.getText().toString()) ) {
            etPhone.setError("mobile cannot be empty");
            valid = true;
        }
        return valid;
    }
}