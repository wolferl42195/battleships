package at.ac.fhcampuswien.battleship.ship;

import at.ac.fhcampuswien.battleship.Position;

import java.util.ArrayList;

public class Ship {

    private ArrayList<ShipPart> shipParts = new ArrayList<>();

    private int length;

    private int x;

    private int y;

    private Position position;

    private Direction direction;

    private int divx;

    private int divy;

    public Ship(Position position, int length, Direction direction, int diffVectorX, int diffVectorY) {
        this.position = position;
        this.length = length;
        this.divx = diffVectorX;
        this.divy = diffVectorY;
        generateShip(position, length, direction);
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getDivx()
    {
        return divx;
    }

    public int getDivy()
    {
        return divy;
    }

    public int getLength()
    {
        return length;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public ArrayList<ShipPart> getShipParts()
    {
        return shipParts;
    }

    private void generateShip(Position position, int length, Direction directions) {
        int x = position.getX();
        int y = position.getY();
        for (int i = 0; i < length; i++) {
            shipParts.add(new ShipPart(new Position(x,y)));
            switch (directions) {
                case UP:
                    y--;
                    break;

                case RIGHT:
                    x++;
                    break;

                case LEFT:
                    x--;
                    break;

                case DOWN:
                    y++;
                    break;
            }
        }
    }

    public boolean attack(Position attackPosition) {
        for (ShipPart shippart : this.shipParts) {
               return shippart.getPosition().equals(attackPosition);
        }
        return false;
    }

    public boolean checkIfDestroyed() {
        for (ShipPart shippart : this.shipParts) {
            if (!shippart.isDamaged()) {
                return false;
            }
        }
        return true;
    }
}
