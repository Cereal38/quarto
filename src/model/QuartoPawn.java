package src.model;

public class QuartoPawn {
    private int pawnBinary;
    private final int hollow = 0b0001;
    private final int little = 0b0010;
    private final int white = 0b0100;
    private final int round = 0b1000;


    public QuartoPawn(int pawn) {
        pawnBinary = pawn;
    }

    public boolean isRound() {
        return ((pawnBinary & round) == 1);
    }

    public boolean isWhite() {
        return (((pawnBinary & white) >> 1) == 1);
    }

    public boolean isLittle() {
        return (((pawnBinary & little) >> 2) == 1);
    }

    public boolean isHollow() {
        return (((pawnBinary & hollow) >> 3) == 0);
    }

    public int getRound() {
        return ((pawnBinary & round));
    }

    public int getWhite() {
        return ((pawnBinary & white) >> 1);
    }

    public int getLittle() {
        return ((pawnBinary & little) >> 2);
    }

    public int getHollow() {
        return ((pawnBinary & hollow) >> 3);
    }
}