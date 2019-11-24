package at.ac.fhcampuswien.battleship.ship;

import at.ac.fhcampuswien.battleship.Position;

public class ShipPart {

    private Position position;

    private boolean damage;

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public Position getPosition(){
        return position;
    }


    public ShipPart(Position position) {
        this.position = position;
        this.damage = false;
    }

    public boolean isDamaged() {
        return damage;
    }

    public void destroy() {
        this.damage = true;
    }

}