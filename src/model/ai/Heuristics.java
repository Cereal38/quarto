package src.model.ai;

// Class to represent heuristic values for evaluating the board in a Quarto game
public class Heuristics {
    int winStateValue;        // Heuristic value for a winning state
    int sameCharValue;        // Heuristic value for having the same characteristic
    int lineOfThreeValue;     // Heuristic value for having three pawns in a line
    int commonCharValue;      // Heuristic value for common characteristics in a line
    int riskValue;            // Heuristic value for assessing risk on the board

    // Constructor to initialize heuristic values with default values
    public Heuristics(){
        this.winStateValue = 1000;
        this.sameCharValue = 10;
        this.lineOfThreeValue = 20;
        this.commonCharValue = 10;
        this.riskValue = 1;
    }

    // Getter for winStateValue
    public int getWinStateValue(){
        return winStateValue;
    }

    // Getter for sameCharValue
    public int getSameCharValue(){
        return sameCharValue;
    }

    // Getter for lineOfThreeValue
    public int getLineOfThreeValue(){
        return lineOfThreeValue;
    }

    // Getter for commonCharValue
    public int getCommonCharValue(){
        return commonCharValue;
    }

    // Getter for riskValue
    public int getRiskValue(){
        return riskValue;
    }

    // Setter for winStateValue
    public void setWinStateValue(int winStateValue){
        this.winStateValue = winStateValue;
    }

    // Setter for commonCharValue
    public void setCommonCharValue(int commonCharValue) {
        this.commonCharValue = commonCharValue;
    }

    // Setter for sameCharValue
    public void setSameCharValue(int sameCharValue){
        this.sameCharValue = sameCharValue;
    }

    // Setter for riskValue
    public void setRiskValue(int riskValue){
        this.riskValue = riskValue;
    }

    // Setter for lineOfThreeValue
    public void setLineOfThreeValue(int lineOfThreeValue){
        this.lineOfThreeValue = lineOfThreeValue;
    }
}
