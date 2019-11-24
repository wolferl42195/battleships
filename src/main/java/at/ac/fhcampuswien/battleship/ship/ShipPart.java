package at.ac.fhcampuswien.battleship.ship;

import at.ac.fhcampuswien.battleship.Position;

public class ShipPart {

    private Position position;

    private boolean damage;

    public ShipPart(Position position) {
        this.position = position;
        this.damage = false;
    }

    public Position getPosition(){
        return position;
    }


    public boolean isDamaged() {
        return damage;
    }

    public void destroy() {
        this.damage = true;
    }

}