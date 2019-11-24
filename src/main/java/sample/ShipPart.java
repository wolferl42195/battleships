package sample;

public class ShipPart {

    private int x;

    private int y;

    private boolean damage;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ShipPart(int x, int y) {
        this.x = x;
        this.y = y;
        this.damage = false;
    }

    public boolean isDamaged() {
        return damage;
    }

    public void destroy() {
        this.damage = true;
    }

}