package src.model;

public class Heuristics {
    int winStateValue;
    int sameCharValue;
    int lineOfThreeValue;
    int commonCharValue;
    int riskValue;

    Heuristics (){
        this.winStateValue = 1000;
        this.sameCharValue = 10;
        this.lineOfThreeValue = 20;
        this.commonCharValue = 10;
        this.riskValue = 1;
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
    int getRiskValue(){
        return riskValue;       }
    void setWinStateValue(int winStateValue){
        this.winStateValue = winStateValue;
    }

    public void setCommonCharValue(int commonCharValue) {
        this.commonCharValue = commonCharValue;
    }

    public void setSameCharValue(int sameCharValue){
        this.sameCharValue = sameCharValue;
    }
    
    public void setRiskValue(int riskValue){
        this.riskValue = riskValue;
    }
    public void setLineOfThreeValue( int lineOfThreeValue){
        this.lineOfThreeValue = lineOfThreeValue;
    }}
