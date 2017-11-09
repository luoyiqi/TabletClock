package com.zengtianyu.tabletclock;

import android.app.Activity;
import android.app.ApplicationErrorReport;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextClock;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("status",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final Integer[] status1 = {sharedPreferences.getInt("second", 1)};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextClock textClock = findViewById(R.id.textClock2);
        if (status1[0] %2==1){
            textClock.setFormat12Hour("HH:mm:ss");
            textClock.setFormat24Hour("HH:mm:ss");
            textClock.setTextSize(getResources().getDimension(R.dimen.fontsmall));
        }else {
            textClock.setFormat12Hour("HH:mm");
            textClock.setFormat24Hour("HH:mm");
            textClock.setTextSize(getResources().getDimension(R.dimen.fontbig));
        }

        final MaterialCalendarView materialCalendarView = findViewById(R.id.cal);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        materialCalendarView.setDateSelected(calendar,true);
        materialCalendarView.setCurrentDate(calendar);
        materialCalendarView.setWeekDayLabels(R.array.week_title);
        materialCalendarView.setEnabled(false);
        materialCalendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialCalendarView.setDateSelected(calendar,true);
            }
        });

        findViewById(R.id.textClock3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialCalendarView.clearSelection();
                materialCalendarView.setCurrentDate(calendar);
                materialCalendarView.setDateSelected(calendar,true);
            }
        });

        findViewById(R.id.textClock2).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TextClock textClock = findViewById(R.id.textClock2);
                if (status1[0] %2==1){
                    textClock.setFormat12Hour("HH:mm");
                    textClock.setFormat24Hour("HH:mm");
                    textClock.setTextSize(getResources().getDimension(R.dimen.fontbig));

                    status1[0]++;
                    editor.putInt("second",status1[0]);
                    editor.apply();
                }else {
                    textClock.setFormat12Hour("HH:mm:ss");
                    textClock.setFormat24Hour("HH:mm:ss");
                    textClock.setTextSize(getResources().getDimension(R.dimen.fontsmall));

                    status1[0]++;
                    editor.putInt("second",status1[0]);
                    editor.apply();
                }
                return true;
            }
        });


    }
}
