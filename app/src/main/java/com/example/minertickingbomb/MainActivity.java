package com.example.minertickingbomb;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.LinearLayoutCompat.LayoutParams;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayoutCompat parentContainer;
    private Stack<Miner> miners = new Stack<>();
    private Button startButton, restartButton;
    private Button cancelButton;
    private TextView playerScoreTextView;
    private TextView playerNameTextView;
    private TextView playerScoreLEDTextView;
    private Player aPlayer;

    /**
     *
     */
    private final int STARTBUTTONID = (int) UUID.randomUUID().getMostSignificantBits();
    private final int CANCELBUTTONID = (int) UUID.randomUUID().getMostSignificantBits();

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentContainer = findViewById(R.id.parent_linearLayout);
        aPlayer = new Player("Muller", "Dessalinnes");

        settingUpButtonRows(parentContainer.getContext());
    }

    private void settingUpButtonRows(Context context) {
        // Building Panel Score
        LinearLayoutCompat panelLayout = makePanelScore(context);
        parentContainer.addView(panelLayout);

        // Building Playgroud
        List<LinearLayoutCompat> playGround = makePlayground(context, 6, 4);
        for (LinearLayoutCompat linearLayoutRow : playGround) {
            parentContainer.addView(linearLayoutRow);
        }

        // Define Start and Cancel button viewed as system button.
        // and then place them into rout view.
        LinearLayoutCompat systemLayout = makeSystemLinearLayoutCompat(context);
        parentContainer.addView(systemLayout);


    }

    private LinearLayoutCompat makeSystemLinearLayoutCompat(Context context) {
        startButton = new Button(this);
        startButton.setText("Start");
        startButton.setOnClickListener(this);
        startButton.setId(STARTBUTTONID);

        cancelButton = new Button(this);
        cancelButton.setText("Restart");
        cancelButton.setOnClickListener(this);
        cancelButton.setId(CANCELBUTTONID);

        LayoutParams systemButtonParams = makeLayoutParams(50, 2,
                2, 0, 0);

        LinearLayoutCompat systemLayout = new LinearLayoutCompat(context);
        systemLayout.setLayoutParams(makeLayoutParams(0, 5, 5,
                5, 5));
        systemLayout.addView(startButton, systemButtonParams);
        systemLayout.addView(cancelButton, systemButtonParams);
        return systemLayout;
    }

    private List<LinearLayoutCompat> makePlayground(Context context, int rows, int colomns) {
        List<LinearLayoutCompat> temporaryRows = new ArrayList<>();
        LayoutParams layoutParamsRow = makeLayoutParams(0, 5,
                5, 0, 0);
        layoutParamsRow.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        for (int i = 0; i < rows; i++) {
            LinearLayoutCompat linearLayoutCompatRow = new LinearLayoutCompat(context);
            linearLayoutCompatRow.setLayoutParams(layoutParamsRow);
            makeRowOfButtons(context, linearLayoutCompatRow, colomns);
            temporaryRows.add(linearLayoutCompatRow);
        }
        return temporaryRows;
    }

    // Making Row of Buttons
    private void makeRowOfButtons(Context context, LinearLayoutCompat linearLayoutCompatRow, int columns) {
        LayoutParams buttonLayoutParams = makeLayoutParams(25, 2, 2,
                0, 0);
        for (int i = 0; i < columns; i++) {
            Miner mine = new Miner(context);
            mine.setText("Hello");
            mine.setValue(pseudoRandomGenerator());
            mine.setId(mine.createViewID());
            // TODO checking Miner id consistency
            //System.out.println("Identifier : "+mine.getIdentifierMiner()+"/ ID_MINE :"+mine.getValue());
            mine.setOnClickListener(this);
            miners.push(mine);
            linearLayoutCompatRow.addView(mine, buttonLayoutParams);
        }
    }


    // Create a LayoutParams
    private LayoutParams makeLayoutParams(int weight, int rightMargin, int leftMargin,
                                          int topMarging, int bottomMarging) {
        LayoutParams buttonLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.weight = weight;
        buttonLayoutParams.rightMargin = rightMargin;
        buttonLayoutParams.leftMargin = leftMargin;
        buttonLayoutParams.topMargin = topMarging;
        buttonLayoutParams.bottomMargin = bottomMarging;
        return buttonLayoutParams;
    }

    private void gatheringScreenSize() {
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        Toast.makeText(this, height + "/" + width, Toast.LENGTH_SHORT).show();
        System.out.println("Height : " + height + " width :" + width);
    }

    // Generator of value which might be either a positive of negative value.
    private int pseudoRandomGenerator() {
        int seed = (int) (Math.random() * 1000);
        return seed < (Math.random() * 1000) ? ~seed : seed;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {

        if (v.getId() == STARTBUTTONID) {
            Toast.makeText(this, "You got: " + STARTBUTTONID, Toast.LENGTH_SHORT).show();

        } else if (v.getId() == CANCELBUTTONID) {
            Toast.makeText(this, "You got it wrong, so let start over", Toast.LENGTH_SHORT).show();
            updatePlayerScores(0);
            reactivatePlayGround();
        } else {
            Miner localMine = ((Miner) findViewById(v.getId()));
            updatePlayerScores(aPlayer.getScore() + localMine.getValue());
            localMine.setEnabled(false);
            miners.removeElement(localMine);
            if(miners.isEmpty() && aPlayer.getScore() < 5000){
                Toast.makeText(this,"Game Over",Toast.LENGTH_LONG ).show();
            }else if(aPlayer.getScore() >= 5000){
                Toast.makeText(this, "Wow You got it, Winner", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void reactivatePlayGround() {
        parentContainer.removeAllViews();
        settingUpButtonRows(parentContainer.getContext());
    }

    private void updatePlayerScores(int score) {
        aPlayer.setScore(score);
        CharSequence updateScore = "Score : " + aPlayer.getScore();
        playerScoreTextView.setText(updateScore);

    }

    // Panel Score along with Player name
    public LinearLayoutCompat makePanelScore(Context context) {
        playerNameTextView = new TextView(context);
        playerScoreTextView = new TextView(context);
        playerScoreLEDTextView = new TextView(context);

        LinearLayoutCompat panelScoreLinearLayout = new LinearLayoutCompat(context);
        panelScoreLinearLayout.setLayoutParams(makeLayoutParams(0, 15,
                15, 15, 0));
        CharSequence player = aPlayer.makeFullName();
        playerNameTextView.setText(player);
        LayoutParams playerParams = makeLayoutParams(50, 0,
                0, 0, 0);
        panelScoreLinearLayout.addView(playerNameTextView, playerParams);

        CharSequence score = String.valueOf("Score : " + aPlayer.getScore());
        playerScoreTextView.setText(score);
        LayoutParams scoreParams = makeLayoutParams(50, 0,
                0, 0, 0);

        panelScoreLinearLayout.addView(playerScoreTextView, scoreParams);

        return panelScoreLinearLayout;

    }
}