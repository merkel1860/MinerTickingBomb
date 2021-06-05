package com.example.minertickingbomb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.LinearLayoutCompat.LayoutParams;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayoutCompat parentContainer;
    Stack<Miner> miners = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentContainer = findViewById(R.id.parent_linearLayout);
        settingUpButtonRows(parentContainer.getContext());
    }

    private void settingUpButtonRows(Context context) {
        List<LinearLayoutCompat> playGround = makePlayground(context, 5,4);
        for(LinearLayoutCompat linearLayoutRow : playGround){
            parentContainer.addView(linearLayoutRow);
        }

    }

    private List<LinearLayoutCompat> makePlayground(Context context, int rows, int colomns) {
        List<LinearLayoutCompat> temporaryRows = new ArrayList<>();
        LayoutParams layoutParamsRow = makeLayoutParams(4,5,
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
        LayoutParams buttonLayoutParams = makeLayoutParams(1,2,2,
                0,0);
        for (int i=0; i<columns;i++){
            Miner button = new Miner(context);
            button.setText("Hello");
            button.setOnClickListener(this);
            miners.push(button);
            linearLayoutCompatRow.addView(button,buttonLayoutParams);
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

    @Override
    public void onClick(View v) {

    }
}