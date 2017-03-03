package me.alvarezkevin.dice;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Kevin on 3/2/2017.
 */

public class DiceGame {

    private static final String LOG_TAG = DiceGame.class.getSimpleName();
    
    private View view;
    private Context context;
    private ImageView mDiceIV;
    private TextView mPlayer1TV;
    private TextView mPlayer2TV;
    private TextView currentRollScoreTV;
    private TextView mGameOverTV;
    private TextView mCurrentPlayerTurnTV;
    private Button mHoldButton;
    private Button mResetButton;
    private int[] diceImagesArray = {R.drawable.dot_1, R.drawable.dot_2, R.drawable.dot_3, R.drawable.dot_4, R.drawable.dot_5, R.drawable.dot_6};
    private int mPlayer1Score = 00;
    private int mPlayer2Score = 0;
    private int mCurrentRollScore = 0;
    private int newDiceNum;
    private int mCurrentPlayerTurn = 1;

    public DiceGame(View view, Context context, ImageView mDiceIV, TextView mPlayer1TV, TextView mPlayer2TV, TextView currenttRollScoreTV,
                    Button holdButton, TextView mGameOverTv, TextView mCurrentPlayerTurnTV, Button mResetButton) {
        this.view = view;
        this.context = context;
        this.mDiceIV = mDiceIV;
        this.mPlayer1TV = mPlayer1TV;
        this.mPlayer2TV = mPlayer2TV;
        this.currentRollScoreTV = currenttRollScoreTV;
        this.mHoldButton = holdButton;
        this.mGameOverTV = mGameOverTv;
        this.mCurrentPlayerTurnTV = mCurrentPlayerTurnTV;
        this.mResetButton = mResetButton;

    }

    private void rollDice() {
        mCurrentPlayerTurnTV.setText("Current Player " + getPlayerTurn() + "'s turn");
        mDiceIV.setClickable(true);
        mDiceIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDiceNum = new Random().nextInt(5 + 1) + 1;
                Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);

                mDiceIV.startAnimation(shake);
                mDiceIV.setImageResource(diceImagesArray[newDiceNum - 1]);
                if (newDiceNum == 1) {
                    Log.v(LOG_TAG, "SWITCHING TURNS");

                    mCurrentRollScore = 0;
                    currentRollScoreTV.setText("Current Roll Score: " + mCurrentRollScore);
                    mCurrentPlayerTurn += 1;

                    mCurrentPlayerTurnTV.setText("Current Player " + getPlayerTurn() + "'s turn");

                    Log.v(LOG_TAG, "Switched to player " + getPlayerTurn());

                    mDiceIV.setClickable(false);
                    mDiceIV.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDiceIV.setClickable(true);
                        }
                    }, 400);
                    return;
                }
                mCurrentRollScore += newDiceNum;
                currentRollScoreTV.setText("Current roll score: " + mCurrentRollScore);
            }
        });
    }

    private boolean SwitchTurn() {
        final boolean[] returnValue = {false};
        mHoldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnValue[0] = true;
                SwitchingProcess();
            }
        });

        return returnValue[0];
    }

    private void SwitchingProcess() {
        Log.v(LOG_TAG, "SWITCHING TURNS");
        if (getPlayerTurn() == 1) {
            mPlayer1Score += mCurrentRollScore;
            mPlayer1TV.setText("Player 1: " + mPlayer1Score);
        } else if (getPlayerTurn() == 2) {
            mPlayer2Score += mCurrentRollScore;
            mPlayer2TV.setText("Player 2: " + mPlayer2Score);
        }

        mCurrentRollScore = 0;
        currentRollScoreTV.setText("Current roll score: " + mCurrentRollScore);

        if (gameOver()) {
            whenGameOver();
            return;
        }
        mCurrentPlayerTurn += 1;

        mCurrentPlayerTurnTV.setText("Current Player " + getPlayerTurn() + "'s turn");
        Log.v(LOG_TAG, "Switched to player " + getPlayerTurn());

        mDiceIV.setClickable(false);
        mDiceIV.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDiceIV.setClickable(true);
            }
        }, 350);
    }

    private int getPlayerTurn() {
        if (mCurrentPlayerTurn % 2 == 0) {
            return 2;
        } else {
            return 1;
        }
    }


    private boolean gameOver() {
        if (mPlayer1Score >= 100 || mPlayer2Score >= 100) {
            Log.v(LOG_TAG, "Player " + getPlayerTurn() + " won");
            return true;
        } else {
            return false;
        }
    }

    private void whenGameOver() {
        mDiceIV.setClickable(false);
        mHoldButton.setClickable(false);
        mGameOverTV.setText(context.getResources().getString(R.string.game_over) + "\nPLAYER " + getPlayerTurn() + " WON!");
        mResetButton.setVisibility(View.VISIBLE);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
                mResetButton.setVisibility(View.GONE);
            }
        });

    }

    private void resetGame() {
        mCurrentPlayerTurn = 1;
        mPlayer2Score = 0;
        mPlayer1Score = 0;
        newDiceNum = 0;

        mPlayer1TV.setText("Player 1: " + mPlayer1Score);
        mPlayer2TV.setText("Player 2: " + mPlayer2Score);
        mGameOverTV.setText("");

        mDiceIV.setImageResource(diceImagesArray[0]);
        mDiceIV.setClickable(true);
        mHoldButton.setClickable(true);
        MainGame();
    }

    public void MainGame() {
        if (!SwitchTurn()) {
            rollDice();
        }
    }
}
