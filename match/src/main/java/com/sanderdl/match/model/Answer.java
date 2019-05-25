package com.sanderdl.match.model;

public class Answer{
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int winFrom(int other){
        if (this.type == other)
            return 0;

        if(this.type == 3 && other == 1){
            return 1;
        }else if(this.type == 1 && other == 3){
            return 2;
        }

        return this.type - other < 0 ? 1 : 2;
    }
}