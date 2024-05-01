package src;

public class QuartoPawn {
    private int r, w, l, h;

    public QuartoPawn(int round, int white, int little, int hollow) {
        r = round;
        w = white;
        l = little;
        h = hollow;
    }

    public boolean isRound() {
        if (r == 0)
            return false;
        return true;
    }

    public boolean isWhite() {
        if (w == 0)
            return false;
        return true;
    }

    public boolean isLittle() {
        if (l == 0)
            return false;
        return true;
    }

    public boolean isHollow() {
        if (h == 0)
            return false;
        return true;
    }

    public int getRound() {
        return r;
    }

    public int getWhite() {
        return w;
    }

    public int getLittle() {
        return l;
    }

    public int getHollow() {
        return h;
    }
}