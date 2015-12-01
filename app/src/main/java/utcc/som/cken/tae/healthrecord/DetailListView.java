package utcc.som.cken.tae.healthrecord;

import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DetailListView extends AppCompatActivity {

    //Explicit
    private String TAG ="Health";
    private UserTABLE objUserTABLE;
    private RecordTABLE objRecordTABLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list_view);

        objUserTABLE = new UserTABLE(this);
        objRecordTABLE = new RecordTABLE(this);

        //Sysnchornize mySQL
        synchornizeMySQL();


    } // Main Method

    private void synchornizeMySQL() {

        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase
                ("Health.db", MODE_PRIVATE, null);
        objSqLiteDatabase.delete("userTABLE", null, null);
        objSqLiteDatabase.delete("recordTABLE", null, null);


        //Setup Policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);


        // Sync ข้อมูลจาก Table โดยค่อยๆทำทีละ Table
        int intTimes = 0;    // จำนวนครั้ง
        while (intTimes <= 1) {


            // Constant
            InputStream objInputStream = null;  // โหลดไปใช้ไป
            String strJSON = null;  // จะเปลี่ยน Input Stream ให้เป็น String
            String strUrlUser = "http://swiftcodingthai.com/tae/get_data_user_tae.php";   // URL ของไฟล์ JSON ตาราง User
            String strUrlRecord = "http://swiftcodingthai.com/tae/get_data_record_tae.php";
            HttpPost objHttpPost = null;   // ประกาศตัวแปรไว้

            // ข้อที่ 1. Create InputStream   ทำให้มันโหลดแบบ Streaming ให้ได้ก่อน
            try {   // สิ่งที่เสี่ยงต่อการ Error ใส่ในนี้

                HttpClient objHttpClient = new DefaultHttpClient();
                switch (intTimes) {
                    case 0:
                        objHttpPost = new HttpPost(strUrlUser);
                        break;
                    default:
                        objHttpPost = new HttpPost(strUrlRecord);
                        break;
                }

                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();


            } catch (Exception e) { // ถ้า Error จะเข้ามาในนี้

                Log.d(TAG, "InputStream ==>" + e.toString());

            }


            // ข้อที่ 2. Create strJSON     เปลี่ยนสิ่งที่เรา Streaming มาให้เป็น String
            try {

                BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
                StringBuilder objStringBuilder = new StringBuilder();   // ตัวที่ทำหน้าที่รวม
                String strLine = null;  // ตัวแปรที่รับตัวที่ถูกตัดมา

                while ((strLine = objBufferedReader.readLine())!= null ) {  // ถ้า strLine ว่างเปล่า ก็ออกจาก Loop

                    objStringBuilder.append(strLine);   // มีหน้าที่คอยผูก String ไปเรื่อย ๆ


                }   // While Loop
                objInputStream.close();                 // ถ้าหมด ก็ไม่ต้องโหลดต่อ
                strJSON = objStringBuilder.toString();


            } catch (Exception e) {

                Log.d(TAG, "strJSON ==> "+e.toString());

            }



            // ข้้อที่ 3. Update SQLite     เอา strJSON ที่ได้มา มาใส่ใน SQLite
            try {

                final JSONArray objJsonArray = new JSONArray(strJSON);

                for (int i = 0; i < objJsonArray.length(); i++) {

                    JSONObject object = objJsonArray.getJSONObject(i);  // เอา i มาแทนค่าตำแหน่งของ Array

                    switch (intTimes) {

                        // สำหรับ UserTABLE
                        // ได้ String 8 ตัวสำหรับใส่ใน DB แล้ว
                        case 0:
                            String strUser = object.getString("User");  // User เป็น Key ใน JSON
                            String strPassword = object.getString("Password");
                            String strName = object.getString("Name");
                            String strAge = object.getString("Age");
                            String strSex = object.getString("Sex");
                            String strWeight = object.getString("Weight");
                            String strHeight = object.getString("Height");
                            String strEmail = object.getString("Email");

                            objUserTABLE.addNewUser(strUser, strPassword, strName, strAge, strSex, strWeight, strHeight,strEmail);
                            break;
                        default:
                            String strSleep = object.getString("Sleep");
                            String strBreakfast = object.getString("Breakfast");
                            String strLunch = object.getString("Lunch");
                            String strDinner = object.getString("Dinner");
                            String strTypeExercise = object.getString("TypeExercise");
                            String strTimeExercise = object.getString("TimeExercise");
                            String strDrinkWater = object.getString("DrinkWater");
                            String strWeightRecord = object.getString("Weight");
                            String strNameUser = object.getString("NameUser");

                            objRecordTABLE.addNewRecord(strSleep, strBreakfast, strLunch, strDinner, strTypeExercise, strTimeExercise, strDrinkWater, strWeightRecord, strNameUser);
                            break;

                    }

                }   // วิ่งวนตามจำนวน แถวใน JSON

            } catch (Exception e) {

                Log.d(TAG, "Update Error ==> "+e.toString());

            }

            intTimes += 1;  // บวกทีละ 1

        }   // while



    } // synchornize
} //Main Class
