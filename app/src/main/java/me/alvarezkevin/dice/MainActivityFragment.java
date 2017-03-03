package me.alvarezkevin.dice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ImageView mDiceIV = (ImageView) view.findViewById(R.id.dice);
        TextView mPlayer1TV = (TextView) view.findViewById(R.id.player_1_text_view);
        TextView mPlayer2TV = (TextView) view.findViewById(R.id.player_2_text_view);
        TextView mCurrentRollTV = (TextView) view.findViewById(R.id.current_turn_score_textview);
        Button mHoldButton = (Button) view.findViewById(R.id.finish_turn_button);
        TextView mGameOver = (TextView) view.findViewById(R.id.game_over_textview);
        TextView mCurrentPlayerTurn = (TextView) view.findViewById(R.id.current_player_turn_textview);
        Button mResetGameButton = (Button)view.findViewById(R.id.reset_game_button);

        DiceGame diceGame = new DiceGame(view, getActivity(), mDiceIV, mPlayer1TV, mPlayer2TV, mCurrentRollTV, mHoldButton, mGameOver, mCurrentPlayerTurn,mResetGameButton);
        diceGame.MainGame();
        return view;
    }
}


