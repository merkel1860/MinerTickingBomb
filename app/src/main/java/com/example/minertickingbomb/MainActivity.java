package com.example.minertickingbomb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.LinearLayoutCompat.LayoutParams;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

import static com.example.minertickingbomb.R.string.start_btn;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayoutCompat parentContainer;
    private Stack<Miner> miners = new Stack<>();
    private Button startButton, restartButton;
    private Button cancelButton ;
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
        settingUpButtonRows(parentContainer.getContext());
    }

    private void settingUpButtonRows(Context context) {
        List<LinearLayoutCompat> playGround = makePlayground(context, 6,4);
        for(LinearLayoutCompat linearLayoutRow : playGround){
            parentContainer.addView(linearLayoutRow);
        }

       /* List<LinearLayoutCompat> systemButtons = makePlayground(context,1,2);
        for(LinearLayoutCompat linearLayoutRow : systemButtons){
            parentContainer.addView(linearLayoutRow);
        }*/
        LinearLayoutCompat systemLayout = makeSystemLinearLayoutCompat(context);
        parentContainer.addView(systemLayout);


    }

    private LinearLayoutCompat makeSystemLinearLayoutCompat(Context context) {
        startButton = new Button(this);
        startButton.setText("Start");
        startButton.setOnClickListener(this);
        startButton.setId(STARTBUTTONID);

        cancelButton = new Button(this);
        cancelButton.setText("Cancel");
        cancelButton.setOnClickListener(this);
        cancelButton.setId(CANCELBUTTONID);

        LayoutParams systemButtonParams = makeLayoutParams(50,2,
                2,0,0);

        LinearLayoutCompat systemLayout = new LinearLayoutCompat(context);
        systemLayout.setLayoutParams(makeLayoutParams(0,5,5,
                5,5));
        systemLayout.addView(startButton,systemButtonParams);
        systemLayout.addView(cancelButton,systemButtonParams);
        return systemLayout;
    }

    private List<LinearLayoutCompat> makePlayground(Context context, int rows, int colomns) {
        List<LinearLayoutCompat> temporaryRows = new ArrayList<>();
        LayoutParams layoutParamsRow = makeLayoutParams(0,5,
                5,0,0);
        layoutParamsRow.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        for(int i=0; i < rows; i++){
            LinearLayoutCompat linearLayoutCompatRow = new LinearLayoutCompat(context);
            linearLayoutCompatRow.setLayoutParams(layoutParamsRow);
            makeRowOfButtons(context, linearLayoutCompatRow,colomns);
            temporaryRows.add(linearLayoutCompatRow);
        }
        return temporaryRows;
    }

    private void makeRowOfButtons(Context context, LinearLayoutCompat linearLayoutCompatRow, int columns) {
        LayoutParams buttonLayoutParams = makeLayoutParams(25,2,2,
                0,0);
        for (int i=0; i<columns;i++){
            Miner mine = new Miner(context);
            mine.setText("Hello");
            mine.setValue(pseudoRandomGenerator());
            mine.setId(((int) mine.getIdentifierMiner().getMostSignificantBits()));
            // TODO checking Miner id consistency
            //            System.out.println(button.getIdentifierMiner()+"/"+button.getId());
            mine.setOnClickListener(this);
            miners.push(mine);
            linearLayoutCompatRow.addView(mine,buttonLayoutParams);
        }
    }

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
        int width  = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        Toast.makeText(this,height+"/"+width,Toast.LENGTH_SHORT).show();
        System.out.println("Height : "+height + " width :"+width);
    }

    private int pseudoRandomGenerator(){
        return (int)(Math.random()*1000);
    }
    @Override
    public void onClick(View v) {

        if(v.getId() == STARTBUTTONID){
            Toast.makeText(this,"You got: " + STARTBUTTONID,Toast.LENGTH_SHORT).show();

        }else if(v.getId() == CANCELBUTTONID){
            Toast.makeText(this,"You got: " + CANCELBUTTONID,Toast.LENGTH_SHORT).show();
        }else {
            // TODO review received value in getValue, it eeems that there is some inconsistency
            Toast.makeText(this,"You got: " + ((Miner)findViewById(v.getId())).getValue(),Toast.LENGTH_SHORT).show();
        }
    }
}