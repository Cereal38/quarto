package src.model;

public class QuartoPawn {
    private byte pawnBinary;
    private final byte hollow = 0b0001;
    private final byte little = 0b0010;
    private final byte white = 0b0100;
    private final byte round = 0b1000;


    public QuartoPawn(byte pawn) {
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

    public int getPawn() {
        return pawnBinary;
    }
}