package utcc.som.cken.tae.healthrecord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

        //Show Image
        showImage();


    } // Main Method

    private void showImage() {



            try {

                String strURLbreakfast = getIntent().getStringExtra("Breakfast");
                Picasso.with(DetailActivity.this).load(strURLbreakfast).resize(200, 200).into(breakfastImageView);

                String strURLlunch = getIntent().getStringExtra("Lunch");
                Picasso.with(DetailActivity.this).load(strURLlunch).resize(200, 200).into(lunchImageView);

                String strURLdinner = getIntent().getStringExtra("Dinner");
                Picasso.with(DetailActivity.this).load(strURLdinner).resize(200, 200).into(dinnerImageView);


            } catch (Exception e) {

                breakfastImageView.setImageResource(R.drawable.icon_question);
                lunchImageView.setImageResource(R.drawable.icon_question);
                dinnerImageView.setImageResource(R.drawable.icon_question);

            }


    } // showImage

    private void showTextView() {

        String strDate = getIntent().getStringExtra("Date");
        dateTextView.setText(strDate);

        String strSleep = getIntent().getStringExtra("Sleep");
        sleepTextView.setText(getString(R.string.sleep) +" = " + strSleep + " "+ getString(R.string.hour2));

        String strTypeExercise = getIntent().getStringExtra("TypeExercise");
        typeExerciseTextView.setText(getString(R.string.exercise) + " = " + strTypeExercise);

        String strTimeExercise = getIntent().getStringExtra("TimeExercise");
        timeExerciseTextView.setText(getString(R.string.time)+ " = " + strTimeExercise + " " + getString(R.string.hour2));

        String strDrinkWater = getIntent().getStringExtra("DrinkWater");
        drinWaterTextView.setText(getString(R.string.drink2) + " = " + strDrinkWater + " "+ getString(R.string.glass));

        String strWeight = getIntent().getStringExtra("Weight");
        weightTextView.setText(getString(R.string.weight2)+ " = " + strWeight + " "+getString(R.string.kilo2));

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
