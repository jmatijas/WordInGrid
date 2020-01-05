package com.josipmatijas.wordingrid;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressBar mTimerProgressBar;
    private TextView mTimerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_fab_play_arrow);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Start a new game?", Snackbar.LENGTH_LONG)
                        .setAction("Start", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startNewGame();
                            }
                        }).show();
            }
        });

        // timer textview and progressbar
        mTimerProgressBar = (ProgressBar) findViewById(R.id.progressBar_timer);
        mTimerTextView = (TextView) findViewById(R.id.textView_timer);

        mTimerProgressBar.setMax(105000);
    }

    private void startNewGame() {
        new CountDownTimer(105000, 100) {

            public void onTick(long millisUntilFinished) {
                mTimerTextView.setText("" + millisUntilFinished / 1000);
                Log.d(TAG, "millisUntilFinished: " + ((int)millisUntilFinished));
                mTimerProgressBar.setProgress((int)millisUntilFinished);
            }

            public void onFinish() {
                mTimerTextView.setText("time's up!");
            }

        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
