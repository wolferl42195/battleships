package at.ac.fhcampuswien.battleship.ship;

import at.ac.fhcampuswien.battleship.Position;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class Ship {

    static Logger logger = LogManager.getLogger("Ship");

    private ArrayList<ShipPart> shipParts = new ArrayList<>();

    private int length;

    private Position position;

    private Direction direction;

    private int diffVectorX;

    private int diffVectorY;

    protected Ship() {
    }

    ;

    public Ship(Position position, int length, Direction direction, int diffVectorX, int diffVectorY) {
        this.position = position;
        this.length = length;
        this.diffVectorX = diffVectorX;
        this.diffVectorY = diffVectorY;
        this.direction = direction;
        shipParts = generateShip(position, length, direction);
    }

    public Position getPosition() {
        return position;
    }

    public int getDiffVectorX() {
        return diffVectorX;
    }

    public int getDiffVectorY() {
        return diffVectorY;
    }

    public int getLength() {
        return length;
    }

    public Direction getDirection() {
        return direction;
    }

    public ArrayList<ShipPart> getShipParts() {
        return shipParts;
    }

    protected ArrayList<ShipPart> generateShip(Position position, int length, Direction direction) {
        logger.info("generate Ship " + position + " " + length + " " + direction);
        ArrayList<ShipPart> shipParts = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        for (int i = 0; i < length; i++) {
            shipParts.add(new ShipPart(new Position(x, y)));
            switch (direction) {
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
        return shipParts;
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
