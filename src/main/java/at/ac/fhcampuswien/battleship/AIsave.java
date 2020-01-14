package at.ac.fhcampuswien.battleship;

import at.ac.fhcampuswien.battleship.ship.Direction;

public class AIsave {

    private Position position;

    private Direction direction;

    private boolean water;

    public AIsave(Position position, boolean water) {
        this.position = position;
        this.water = water;
        direction = null;
    }

    public AIsave(Position position, Direction direction, boolean water) {
        this.position = position;
        this.direction = direction;
        this.water = water;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isWater() {
        return water;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setWater(boolean water) {
        this.water = water;
    }

}
