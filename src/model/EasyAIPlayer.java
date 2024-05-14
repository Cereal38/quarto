package src.model;

public class EasyAIPlayer implements Player{

    @Override
    public void selectPawn(QuartoModel quartoModel){
        quartoModel.selectPawnHuman(0);
    }

    @Override
    public void playShot(QuartoModel quartoModel) {
        quartoModel.playShotHuman(0, 0);
        System.out.println("Shot played by AI at (0, 0).");
    }
}
