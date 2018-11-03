package lammer.florian.hemaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class QuizOverview extends AppCompatActivity {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] SLIDER = {R.drawable.slider_1, R.drawable.slider_2, R.drawable.slider_3};
    private ArrayList<Integer> SLIDERArray = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_overview);
        init();

        //Buttons
        Button b_allgemeineGrundlagen = (Button) findViewById(R.id.b_GrundlagenAllgemein);
        Button b_Huten = (Button) findViewById(R.id.b_GrundlagenAllgemein);
        Button b_spezilleGrundlagen = (Button) findViewById(R.id.b_GrundlagenSpeziell);
        Button b_fortgeschritteneTechnik = (Button) findViewById(R.id.b_TechnikFortgeschritten);

        //Quiz Button
        b_allgemeineGrundlagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), quiz_SB_allgemGrundlagen.class);
                finish();
                startActivity(intent);
            }
        });

        b_Huten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), quiz_SB_allgemGrundlagen.class);
                finish();
                startActivity(intent);
            }
        });


    }


    //Slider
    private void init() {
        for(int i = 0; i < SLIDER.length; i++){
            SLIDERArray.add(SLIDER[i]);
        }

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyAdapter(QuizOverview.this, SLIDERArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        //Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == SLIDER.length){
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);

    }

}
