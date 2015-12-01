package utcc.som.cken.tae.healthrecord;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText userEditText, passwordEditText, nameEditText,
            emailEditText, weightEditText, heightEditText; // ใช้ในการกรอก
    private RadioGroup sexRadioGroup;
    private Spinner ageSpinner;

    private String userString, passwordString, nameString,
            emailString, weightString, heightString, sexString = "Male", ageString;

    private UserTABLE objUserTABLE;
    private MyDialog objMyDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        objUserTABLE = new UserTABLE(this);
        objMyDialog = new MyDialog();

        // Bind Widget

        bindWidget();

        //Create Spinner
        createSpinner();

        //Create Radio Group
        createRadioGroup();

    } // OnCreate

    private void createRadioGroup() {
        sexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radMale:
                        sexString = "Male";
                        break;
                    case R.id.radFemale:
                        sexString = "Female";
                        break;
                    default:
                        sexString = "Male";
                        break;
                }

            }
        });

    }

    private void createSpinner() {

        final String[] strAge = {"0 - 5", "5 - 10", "10 - 15", "15 - 20",
                "20 - 25", "25 - 30", "30 - 35", "35 - 40",
                "40 - 45", "45 - 50", "50 - 55", "55 - 60" ,
                "60 - 65" , "65 - 70" , "70 - 75" ,"75 - 80"};
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strAge);
        ageSpinner.setAdapter(ageAdapter);

        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ageString = strAge[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ageString = strAge[0];

            }
        });


    } // createSpinner

    private void bindWidget() {

        userEditText = (EditText) findViewById(R.id.edtUserSign);
        passwordEditText = (EditText)findViewById(R.id.edtPassSign);
        nameEditText = (EditText) findViewById(R.id.edtNameSign);
        emailEditText = (EditText) findViewById(R.id.edtEmailSign);
        weightEditText = (EditText) findViewById(R.id.edtWeight);
        heightEditText = (EditText) findViewById(R.id.edtHeight);
        ageSpinner = (Spinner) findViewById(R.id.spnAge);
        sexRadioGroup = (RadioGroup) findViewById(R.id.ragSex);


    }

    public void clickSave(View view){

        //Get Value From Edit text
        userString = userEditText.getText().toString().trim(); //trim ตัดช่องว่างข้างหน้าหลังอัตโนมัติ
        passwordString = passwordEditText.getText().toString().trim();
        nameString = nameEditText.getText().toString().trim();
        emailString = emailEditText.getText().toString().trim();
        weightString = weightEditText.getText().toString().trim();
        heightString = heightEditText.getText().toString().trim();

        //Check Zero
        if (userString.equals("") || passwordString.equals("") || nameString.equals("") || emailString.equals("") || weightString.equals("") || heightString.equals("") ) {

            //Have Space
            MyDialog objMyDialog = new MyDialog();
            objMyDialog.errorDialog(SignUpActivity.this, "มีช่องว่าง", "กรุณากรอกให้ครบ");

        } else {

            //Check User
            checkUser();


        }





    } // clickSave

    private void checkUser() {

        try {

            String[] strResult = objUserTABLE.searchUser(userString);
            //ค้นหาเจอ
            objMyDialog.errorDialog(SignUpActivity.this, "เปลี่ยน User", "ฐานข้อมูลมี User " + strResult[1] + " นี้อยู่แล้ว");

        } catch (Exception e) {

            //Confirm Value
            confirmvalue();

        }


    }

    private void confirmvalue() {

        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.icon_question);
        objBuilder.setTitle("Confirm Value");
        objBuilder.setMessage("User = " + userString
                + "\n" + "Password = " + passwordString
                + "\n" + "Name = " + nameString
                + "\n" + "Email = " + emailString
                + "\n" + "Sex = " + sexString
                + "\n" + "Age = " + ageString
                + "\n" + "Weight = " + weightString
                + "\n" + "Height = " + heightString);
        objBuilder.setCancelable(false); // Undo ไม่ได้
        objBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Update to MySQL
                updateToMySQL();
                dialogInterface.dismiss();
            }
        });
        objBuilder.show();

    }

    private void updateToMySQL() {

        //Setup policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);


        //Update Value
        try {

            ArrayList<NameValuePair> objNameValuePairs = new ArrayList<NameValuePair>();
            objNameValuePairs.add(new BasicNameValuePair("isAdd", "true"));
            objNameValuePairs.add(new BasicNameValuePair("User", userString));
            objNameValuePairs.add(new BasicNameValuePair("Password", passwordString));
            objNameValuePairs.add(new BasicNameValuePair("Name", nameString));
            objNameValuePairs.add(new BasicNameValuePair("Age", ageString));
            objNameValuePairs.add(new BasicNameValuePair("Sex", sexString));
            objNameValuePairs.add(new BasicNameValuePair("Weight", weightString));
            objNameValuePairs.add(new BasicNameValuePair("Height", heightString));
            objNameValuePairs.add(new BasicNameValuePair("Email", emailString));

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/tae/add_data_user_tae.php");
            objHttpPost.setEntity(new UrlEncodedFormEntity(objNameValuePairs, "UTF-8"));
            objHttpClient.execute(objHttpPost);

            Toast.makeText(SignUpActivity.this, "Update New Value Successful", Toast.LENGTH_LONG);

            finish();

        } catch (Exception e) {
            Toast.makeText(SignUpActivity.this, "Cannot Update To MySQL", Toast.LENGTH_LONG).show();

        }



    } // UpdateToMySQL


} //Main Class
