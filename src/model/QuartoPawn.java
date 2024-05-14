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
        return (getRound() != 0);
    }

    public boolean isWhite() {
        return (getWhite() != 0);
    }

    public boolean isLittle() {
        return (getLittle() != 0);
    }

    public boolean isHollow() {
        return (getHollow() != 0);
    }

    public int getRound() {
        return (pawnBinary & round);
    }

    public int getWhite() {
        return (pawnBinary & white);
    }

    public int getLittle() {
        return (pawnBinary & little);
    }

    public int getHollow() {
        return (pawnBinary & hollow);
    }

    public byte getPawn() {
        return pawnBinary;
    }
}