package utcc.som.cken.tae.healthrecord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    //Explicit
    private TextView dateTextView, sleepTextView, typeExerciseTextView,
    timeExerciseTextView, drinWaterTextView, weightTextView;

    private ImageView breakfastImageView, lunchImageView, dinnerImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //bindWidget
        bindWidget();

        //Show TextView
        showTextView();


    } // Main Method

    private void showTextView() {

        String strDate = getIntent().getStringExtra("Date");
        dateTextView.setText(strDate);

        String strSleep = getIntent().getStringExtra("Sleep");
        sleepTextView.setText("Sleep = " + strSleep + " ชั่วโมง");

        String strTypeExercise = getIntent().getStringExtra("TypeExercise");
        typeExerciseTextView.setText("TypeExercise = " + strTypeExercise);

        String strTimeExercise = getIntent().getStringExtra("TimeExercise");
        timeExerciseTextView.setText("TimeExercise = " + strTimeExercise + " ชั่วโมง");

        String strDrinkWater = getIntent().getStringExtra("DrinkWater");
        drinWaterTextView.setText("DrinkWater = " + strDrinkWater + " แก้ว" );

        String strWeight = getIntent().getStringExtra("Weight");
        weightTextView.setText("Weight = " + strWeight + " kg");

    }//showTextView


    private void bindWidget() {
        dateTextView = (TextView) findViewById(R.id.txtDetailDate);
        sleepTextView = (TextView) findViewById(R.id.txtDetailSleep);
        typeExerciseTextView = (TextView) findViewById(R.id.txtTypeExercise);
        timeExerciseTextView = (TextView) findViewById(R.id.txtTimeExercise);
        drinWaterTextView = (TextView) findViewById(R.id.txtDrinkWater);
        weightTextView = (TextView) findViewById(R.id.txtWeight);
        breakfastImageView = (ImageView) findViewById(R.id.imvBreakfast);
        lunchImageView = (ImageView) findViewById(R.id.imvLunch);
        dinnerImageView = (ImageView) findViewById(R.id.imvDinner);


    }

} //Main Class
