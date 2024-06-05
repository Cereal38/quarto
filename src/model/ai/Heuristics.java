package src.model.ai;

/**
 * The Heuristics class defines the evaluation parameters for the AI in the Quarto game.
 * It includes values for different states and scenarios that the AI uses to calculate the optimal move.
 */
public class Heuristics {
    private int winStateValue;
    private int sameCharValue;
    private int lineOfThreeValue;
    private int commonCharValue;
    private int riskValue;

    /**
     * Constructs a Heuristics object with default values.
     */
    public Heuristics() {
        this.winStateValue = 1000;
        this.sameCharValue = 10;
        this.lineOfThreeValue = 20;
        this.commonCharValue = 10;
        this.riskValue = 1;
    }

    /**
     * Returns the value assigned to a win state.
     *
     * @return the win state value.
     */
    public int getWinStateValue() {
        return winStateValue;
    }

    /**
     * Returns the value assigned to having the same characteristic among pawns.
     *
     * @return the same characteristic value.
     */
    public int getSameCharValue() {
        return sameCharValue;
    }

    /**
     * Returns the value assigned to having a line of three pawns with the same characteristic.
     *
     * @return the line of three value.
     */
    public int getLineOfThreeValue() {
        return lineOfThreeValue;
    }

    /**
     * Returns the value assigned to having common characteristics among pawns.
     *
     * @return the common characteristic value.
     */
    public int getCommonCharValue() {
        return commonCharValue;
    }

    /**
     * Returns the value assigned to risk in the evaluation.
     *
     * @return the risk value.
     */
    public int getRiskValue() {
        return riskValue;
    }

    /**
     * Sets the value assigned to a win state.
     *
     * @param winStateValue the new win state value.
     */
    public void setWinStateValue(int winStateValue) {
        this.winStateValue = winStateValue;
    }

    /**
     * Sets the value assigned to having the same characteristic among pawns.
     *
     * @param sameCharValue the new same characteristic value.
     */
    public void setSameCharValue(int sameCharValue) {
        this.sameCharValue = sameCharValue;
    }

    /**
     * Sets the value assigned to having a line of three pawns with the same characteristic.
     *
     * @param lineOfThreeValue the new line of three value.
     */
    public void setLineOfThreeValue(int lineOfThreeValue) {
        this.lineOfThreeValue = lineOfThreeValue;
    }

    /**
     * Sets the value assigned to having common characteristics among pawns.
     *
     * @param commonCharValue the new common characteristic value.
     */
    public void setCommonCharValue(int commonCharValue) {
        this.commonCharValue = commonCharValue;
    }

    /**
     * Sets the value assigned to risk in the evaluation.
     *
     * @param riskValue the new risk value.
     */
    public void setRiskValue(int riskValue) {
        this.riskValue = riskValue;
    }
}
