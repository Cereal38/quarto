package src.model.ai;

import src.model.game.QuartoModel;

public interface Player {
    void selectPawn(QuartoModel quartoModel);

    void playShot(QuartoModel quartoModel);
}
