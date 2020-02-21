package com.josipmatijas.wordingrid;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    final Handler handler = new Handler();

    private ProgressBar mTimerProgressBar;
    private TextView mTimerTextView;
    private CardView mCardViewTimer;
    private MyGridLayout myGridLayout;
    private MyCardView[][] mCardViewsGrid = new MyCardView[4][4];
    private TextView[][] mTextViewsGrid = new TextView[4][4];
    private DiceData[][] mDiceData = new DiceData[4][4];
    private CountDownTimer countDownTimer;

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
        mCardViewTimer = (CardView) findViewById(R.id.cardViewTimer);

        mTimerProgressBar.setMax(105000);

        // init static data
        initDiceLetters();

        // get gridlayout child views - MyCardView-s
        myGridLayout = (MyGridLayout) findViewById(R.id.myGridLayout);
        int row = 0;
        int column = -1;
        for(int i = 0; i < myGridLayout.getChildCount(); i++) {
            View v = myGridLayout.getChildAt(i);
            if(v instanceof MyCardView) {
                column++;
                if(column > 3) {
                    column = 0;
                    row++;
                }
                mCardViewsGrid[row][column] = (MyCardView) v;

                View v2 = mCardViewsGrid[row][column].getChildAt(0);
                if(v2 != null && v2 instanceof TextView) {
                    mTextViewsGrid[row][column] = (TextView) v2;
                }
            }
        }

        // gridlayout animation (change default settings)
        myGridLayout.getLayoutTransition().setDuration(800L);
        myGridLayout.getLayoutTransition().setInterpolator(
                LayoutTransition.CHANGE_APPEARING
                        | LayoutTransition.APPEARING
                        | LayoutTransition.DISAPPEARING
                        | LayoutTransition.CHANGE_DISAPPEARING
                        | LayoutTransition.CHANGING,
                new AnticipateOvershootInterpolator(3.0f));

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                throwDices();
            }
        }, 400);
    }

    private void initDiceLetters() {
        int diceStringsIndex = -1;
        for(int row = 0; row < 4; row++) {
            for(int col = 0; col < 4; col++) {
                mDiceData[row][col] = new DiceData();
                diceStringsIndex++;
                String diceString = DiceData.DICE_STRINGS[diceStringsIndex];
                for(int letterIndex = 0; letterIndex < 6; letterIndex++) {
                    mDiceData[row][col].getLetters()[letterIndex] = String.valueOf(diceString.charAt(letterIndex));
                }
            }
        }
    }

    private void startNewGame() {
        throwDices();
    }

    private void throwDices() {
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
        mTimerProgressBar.setProgress(0);
        mTimerTextView.setText("throwing dices!!!");

        final Random rand = new Random();
        for(int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                mTextViewsGrid[row][col].setText(mDiceData[row][col].getLetters()[rand.nextInt(6)]);
            }
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                throwDices_2(rand);
            }
        }, 400);
    }

    private void throwDices_2(final Random rand) {
        Log.d(TAG, "dices reposition");

        final List<View> tempListViews = new ArrayList<>(20);
        for(int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                View v = mCardViewsGrid[row][col];
                myGridLayout.removeView(v);
                int position = rand.nextInt(tempListViews.size() + 1);
                Log.d(TAG, "position: " + position);
                tempListViews.add(position, v);
            }
        }

        for(View v : tempListViews) {
            myGridLayout.addView(v);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                throwDices_3(rand, tempListViews);
            }
        }, 400);
    }

    private void throwDices_3(Random rand, List<View> tempListViews) {
        Log.d(TAG, "rotate randomly");
        int i = -1;
        for(View v : tempListViews) {
            i++;
            v.animate()
                    .rotationBy((rand.nextInt(4) + 1) * 90f).setDuration(1500L)
                    .rotationYBy(rand.nextBoolean() ? 360f : -360f)
                    .rotationXBy(rand.nextBoolean() ? 360f : -360f)
                    .setStartDelay((long)(rand.nextFloat() * 150 + (i/4 + i%4) * 80))
                    .setInterpolator(new AnticipateOvershootInterpolator(1.2f));
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                throwDices_4();
            }
        }, 2500);
    }

    private void throwDices_4() {
        Log.d(TAG, "start countdown timer");
        mCardViewTimer.animate().setInterpolator(new AnticipateOvershootInterpolator(1.3f)).setDuration(1500L).rotationBy(360f).rotationYBy(360f);

        countDownTimer = new CountDownTimer(105000, 100) {

            public void onTick(long millisUntilFinished) {
                mTimerTextView.setText("" + (millisUntilFinished / 1000 + 1));
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
