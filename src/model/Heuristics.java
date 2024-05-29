package src.model;

public class Heuristics {
    int winStateValue;
    int sameCharValue;
    int lineOfThreeValue;
    int commonCharValue;

    Heuristics (int winState, int sameCharValue, int lineOfThree, int commonCharValue){
        this.winStateValue = winState;
        this.sameCharValue = sameCharValue;
        this.lineOfThreeValue = lineOfThree;
        this.commonCharValue = commonCharValue;
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

}
