package src.model;

public class Heuristics {
    int winStateValue;
    int sameCharValue;
    int lineOfThreeValue;
    int commonCharValue;

    Heuristics (){
        this.winStateValue = 1000;
        this.sameCharValue = 10;
        this.lineOfThreeValue = 20;
        this.commonCharValue = 10;
    }
    int getWinStateValue(){
        return winStateValue;
    }
    int getSameCharValue(){
        return sameCharValue;
    }

    int getLineOfThreeValue(){
        return lineOfThreeValue;
    }
    int getCommonCharValue(){
        return commonCharValue;
    }

    void setWinStateValue(int winStateValue){
        this.winStateValue = winStateValue;
    }

    public void setCommonCharValue(int commonCharValue) {
        this.commonCharValue = commonCharValue;
    }

    public void setSameCharValue(int sameCharValue){
        this.sameCharValue = sameCharValue;
    }
    
    public void setLineOfThreeValue( int lineOfThreeValue){
        this.lineOfThreeValue = lineOfThreeValue;
    }
}
