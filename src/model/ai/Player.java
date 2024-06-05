package src.model.ai;

import src.model.game.QuartoModel;

/**
 * An interface representing a player in the Quarto game.
 */

public interface Player {

    /**
     * Selects a pawn to play in the Quarto game.
     *
     * @param quartoModel The QuartoModel representing the current state of the game.
     */
    void selectPawn(QuartoModel quartoModel);

    /**
     * Plays a shot in the Quarto game.
     *
     * @param quartoModel The QuartoModel representing the current state of the game.
     */
    void playShot(QuartoModel quartoModel);
}
