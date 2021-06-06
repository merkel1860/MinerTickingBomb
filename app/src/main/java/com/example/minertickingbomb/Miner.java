package com.example.minertickingbomb;

import android.content.Context;

import java.util.UUID;

public class Miner extends androidx.appcompat.widget.AppCompatButton {
    private int value;
    private UUID identifierMiner;

    public Miner(Context context) {
        super(context);
        identifierMiner = UUID.randomUUID();

    }

    public int createViewID(){
        final int viewID = (int)identifierMiner.getMostSignificantBits();
        return viewID > 0? viewID: (viewID * -1);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public UUID getIdentifierMiner() {
        return identifierMiner;
    }

    public void setIdentifierMiner(UUID identifierMiner) {
        this.identifierMiner = identifierMiner;
    }
}
