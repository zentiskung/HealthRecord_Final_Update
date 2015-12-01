package utcc.som.cken.tae.healthrecord;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jibble.simpleftp.SimpleFTP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecordActivity extends AppCompatActivity {

    //Explicit
    private TextView showTimeTextView;
    private String idUserString, currentTimeString, sleepString = null, breakfastname = null, lunchname = null, dinnername = null,
            breakfastString = null, lunchString = null, dinnerString = null, timeExerciseString = null, typeExerciseString = null, drinkWaterString = null, weightString = null;
    private Spinner sleepSpinner, exerciseSpinner, drinkWaterSpinner;
    private EditText breakfastEditText, lunchEditText, dinnerEditText, timeExerciseEditText, typeExerciseEditText, weightEditText;
    private String strNameUser;
    // ดึงรูป
    public static final int PICK_IMAGE = 1, PICK_IMAGE2 = 2, PICK_IMAGE3 = 3;

    //Bitmap imagedata1,imagedata2,imagedata3;
    //TextView text,text2,text3;   ใช้แสดงpath
    ImageView imageView1, imageView2, imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);


        //bindWidget();
        bindWidget();

        //show Time
        showTime();

        //Create Sleep Spinner
        createSleepSpinner();

        //Create execise Spinner
        createExerciseSpinner();

        createDrinkWaterSpinner();

        showUser();

        imageView1 = (ImageView) findViewById(R.id.imageView10);
        imageView2 = (ImageView) findViewById(R.id.imageView11);
        imageView3 = (ImageView) findViewById(R.id.imageView12);

        /*text = (TextView)findViewById(R.id.textView10);
        text2 = (TextView)findViewById(R.id.textView10);
        text3 = (TextView)findViewById(R.id.textView10);   ใช้แสดง path */


        Button buttonIntent = (Button) findViewById(R.id.button10);
        Button buttonIntent1 = (Button) findViewById(R.id.button11);
        Button buttonIntent2 = (Button) findViewById(R.id.button12);


        buttonIntent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent
                        , "Select Picture"), PICK_IMAGE);
            }
        });
        buttonIntent1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("image/*");
                startActivityForResult(Intent.createChooser(intent1
                        , "Select Picture"), PICK_IMAGE2);
            }
        });
        buttonIntent2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
                intent2.setType("image/*");
                startActivityForResult(Intent.createChooser(intent2
                        , "Select Picture"), PICK_IMAGE3);

            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode
            , Intent returndata) {
        String imagepath1, imagepath2, imagepath3;
        Uri uri1, uri2, uri3;


        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {

            Uri imageUri = returndata.getData();
            String msg = "URI: " + imageUri + "\n";

            String imagePath = findPath(imageUri);
            msg += "Path: " + imagePath;
            breakfastString = imagePath;
            breakfastname = breakfastString.substring(breakfastString.lastIndexOf("/") + 1);
            uri1 = imageUri;
            //Intent imgp1 = new Intent(imagePath1);
            //imgp1.putExtra("breakfast",imagePath1);


            //text.setText(msg);  //แสดง path
            try {
                Bitmap imagedata1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));//Media.getBitmap(this.getContentResolver(), imageUri);
                imageView1.setImageBitmap(imagedata1);

            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }


                   /* Bitmap imageData1 = BitmapFactory.decodeFile(imagePath);
                    imageView1.setImageBitmap(imageData1); */

        } else if (requestCode == PICK_IMAGE2 && resultCode == RESULT_OK) {
            Uri imageUri = returndata.getData();
            String msg = "URI: " + imageUri + "\n";

            String imagePath = findPath(imageUri);
            msg += "Path: " + imagePath;
            lunchString = imagePath;
            lunchname = lunchString.substring(lunchString.lastIndexOf("/") + 1);


            //text.secText(msg);  แสดง path
            try {
                Bitmap imagedata2 = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri)); //Media.getBitmap(this.getContentResolver(), imageUri);//
                imageView2.setImageBitmap(imagedata2);

            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
            // Bitmap imageData2 = BitmapFactory.decodeFile(imagePath);
            // imageView2.setImageBitmap(imageData2);
        } else if (requestCode == PICK_IMAGE3 && resultCode == RESULT_OK) {
            Uri imageUri = returndata.getData();
            String msg = "URI: " + imageUri + "\n";

            String imagePath = findPath(imageUri);
            msg += "Path: " + imagePath;
            imagepath3 = imagePath;
            uri3 = imageUri;
            dinnerString = imagePath;
            dinnername = dinnerString.substring(dinnerString.lastIndexOf("/") + 1);

            //text.secText(msg);  แสดง path
            try {
                Bitmap imagedata3 = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));  //Media.getBitmap(this.getContentResolver(), imageUri);//
                imageView3.setImageBitmap(imagedata3);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Bitmap imageData3 = BitmapFactory.decodeFile(imagePath);
            // imageView3.setImageBitmap(imageData3);
        }

    }


    /*{
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Uri uri = returndata.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                imageView1.setImageBitmap(bitmap);
                imageView2.setImageBitmap(bitmap2);
                imageView3.setImageBitmap(bitmap3);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } */


    //onCreate

    private String findPath(Uri uri) {
        String imagePath;

        String[] columns = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, columns, null, null, null);

        if (cursor != null) { // case gallery
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            imagePath = cursor.getString(columnIndex);
        } else { // case another app
            imagePath = uri.getPath();

        }
        return imagePath;
    }

    private void showUser() {
        strNameUser = getIntent().getStringExtra("NameUser");
        TextView userTextView = (TextView) findViewById(R.id.textView18);
        userTextView.setText(strNameUser);

    }


    private void createDrinkWaterSpinner() {

        final String[] strDrinkWater = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

        ArrayAdapter<String> drinkwaterAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strDrinkWater);
        drinkWaterSpinner.setAdapter(drinkwaterAdapter);

        drinkWaterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                drinkWaterString = strDrinkWater[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                drinkWaterString = strDrinkWater[0];
            }
        });
    }

    private void createExerciseSpinner() {

        final String[] strExercise = new String[5];
        strExercise[0] = "Run";
        strExercise[1] = "Bicycle";
        strExercise[2] = "Swim";
        strExercise[3] = "Yoga";
        strExercise[4] = "Other";


        ArrayAdapter<String> exerciseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strExercise);
        exerciseSpinner.setAdapter(exerciseAdapter);

        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeExerciseString = strExercise[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                typeExerciseString = strExercise[0];

            }
        });

    }

    public void clickSaveRecord(View view) {
        // Intent img1 = getIntent();
        // breakfastString = img1.getStringExtra("breakfast");
        // breakfastString = breakfastEditText.getText().toString().trim(); //String imagePath = findPath(uri1.onActivityResult);
        // lunchString = lunchEditText.getText().toString().trim();
        // dinnerString = dinnerEditText.getText().toString().trim();
        timeExerciseString = timeExerciseEditText.getText().toString().trim();
        weightString = weightEditText.getText().toString().trim();


        confirmValue();


    }

    private void createSleepSpinner() {

        final String[] strSleep = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

        ArrayAdapter<String> sleepAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strSleep);
        sleepSpinner.setAdapter(sleepAdapter);
        sleepSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sleepString = strSleep[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                sleepString = strSleep[0];

            }
        });

    }


    private void showTime() {

        DateFormat objDateFormat = new SimpleDateFormat("yyyy/MM/dd HH/mm/ss");
        Date currentDate = new Date();
        currentTimeString = objDateFormat.format(currentDate);
        showTimeTextView.setText(currentTimeString);

    }

    private void bindWidget() {
        showTimeTextView = (TextView) findViewById(R.id.txtShowTime);
        sleepSpinner = (Spinner) findViewById(R.id.spinner);
        /*breakfastEditText = (EditText) findViewById(R.id.editText);
        lunchEditText = (EditText) findViewById(R.id.editText2);
        dinnerEditText = (EditText) findViewById(R.id.editText3);*/
        //breakfastImageView = (ImageView) findViewById(R.id.imageView10);
        exerciseSpinner = (Spinner) findViewById(R.id.spinner3);
        timeExerciseEditText = (EditText) findViewById(R.id.editText4);
        drinkWaterSpinner = (Spinner) findViewById(R.id.spinner4);
        weightEditText = (EditText) findViewById(R.id.editText5);

    }


    private void confirmValue() {


        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.icon_question);
        objBuilder.setTitle("Confirm Value");
        objBuilder.setMessage("Sleep = " + sleepString
                + "\n" + "Breakfast = " + breakfastname
                + "\n" + "Lunch = " + lunchname
                + "\n" + "Dinner = " + dinnername
                + "\n" + "TypeExercise = " + typeExerciseString
                + "\n" + "TimeExercise = " + timeExerciseString
                + "\n" + "DrinkWater = " + drinkWaterString
                + "\n" + "Weight = " + weightString);
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
                uploadpic();
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
            //objNameValuePairs.add(new BasicNameValuePair("idUser", strIDUser));
            objNameValuePairs.add(new BasicNameValuePair("Sleep", sleepString));
            objNameValuePairs.add(new BasicNameValuePair("Breakfast", "http://swiftcodingthai.com/tae/" + breakfastname));
            objNameValuePairs.add(new BasicNameValuePair("Lunch", "http://swiftcodingthai.com/tae/" + lunchname));
            objNameValuePairs.add(new BasicNameValuePair("Dinner", "http://swiftcodingthai.com/tae/" + dinnername));
            objNameValuePairs.add(new BasicNameValuePair("TypeExercise", typeExerciseString));
            objNameValuePairs.add(new BasicNameValuePair("TimeExercise", timeExerciseString));
            objNameValuePairs.add(new BasicNameValuePair("DrinkWater", drinkWaterString));
            objNameValuePairs.add(new BasicNameValuePair("Weight", weightString));
            objNameValuePairs.add(new BasicNameValuePair("NameUser", strNameUser));

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/tae/add_data_record_tae.php");
            objHttpPost.setEntity(new UrlEncodedFormEntity(objNameValuePairs, "UTF-8"));
            objHttpClient.execute(objHttpPost);

            Toast.makeText(RecordActivity.this, "Update New Value Successful", Toast.LENGTH_LONG);

            finish();

        } catch (Exception e) {
            Toast.makeText(RecordActivity.this, "Cannot Update To MySQL", Toast.LENGTH_LONG).show();

        }


    } // UpdateToMySQL

    public void uploadpic() {

        try {
            SimpleFTP ftp = new SimpleFTP();

            // Connect to an FTP server on port 21.
            ftp.connect("ftp.swiftcodingthai.com", 21, "tae@swiftcodingthai.com", "Abc12345");

            // Set binary mode.
            ftp.bin();

            // Change to a new working directory on the FTP server.
            ftp.cwd("web");

            // Upload some files.
            /*ftp.stor(new File("webcam.jpg"));
            ftp.stor(new File("comicbot-latest.png"));*/

            // You can also upload from an InputStream, e.g.
            ftp.stor(new File(breakfastString));
            ftp.stor(new File(lunchString));
            ftp.stor(new File(dinnerString));

            // Quit from the FTP server.
            ftp.disconnect();
        } catch (IOException e) {
            // Jibble.
        }

        /// test ดึงรูป


    }
}

// Main Class
