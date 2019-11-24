package at.ac.fhcampuswien.battleship;

import at.ac.fhcampuswien.battleship.ship.Direction;
import at.ac.fhcampuswien.battleship.ship.Ship;
import at.ac.fhcampuswien.battleship.ship.ShipPart;

import java.util.ArrayList;

public class Field {

    private ArrayList<Ship> fleet = new ArrayList<>();

    public ArrayList<Ship> getFleet() {
        return fleet;
    }

    private boolean isFree(Position position) {
        for (Ship warship : this.fleet) {
            for (ShipPart part : warship.getShipParts()) {
                if (part.getPosition().equals(position)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isAreaFree(Position position, int length, Direction direction) {
        int x = position.getX();
        int y = position.getY();
        for (int i = 0; i < length; i++) {
            if (x < BattleShipConstants.MIN_FIELD_INDEX || x > BattleShipConstants.MAX_FIELD_INDEX ||
                    y < BattleShipConstants.MIN_FIELD_INDEX || y > BattleShipConstants.MAX_FIELD_INDEX) {
                return false;
            }
            if (!this.isFree(new Position(x,y))) {
                return false;
            }

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
        return true;
    }


    private int shipCount(int length) {
        int count = 0;
        for (Ship ship : this.fleet) {
            if (ship.getLength() == length) {
                count++;
            }
        }
        return count;
    }

    public boolean isFleetComplete() {
        return ((this.shipCount(BattleShipConstants.SHIP_LENGTH_2) == BattleShipConstants.AMOUNT_SHIPS_SIZE_2 &&
                this.shipCount(BattleShipConstants.SHIP_LENGTH_3) == BattleShipConstants.AMOUNT_SHIPS_SIZE_3 &&
                this.shipCount(BattleShipConstants.SHIP_LENGTH_4) == BattleShipConstants.AMOUNT_SHIPS_SIZE_4 &&
                this.shipCount(BattleShipConstants.SHIP_LENGTH_5) == BattleShipConstants.AMOUNT_SHIPS_SIZE_5));
    }

    public boolean setShip(Position position, int length, Direction direction, int diffvectorx, int diffvectory) {
        switch (length) {
            case BattleShipConstants.SHIP_LENGTH_2:
                if (this.shipCount(length) >= 4) {
                    return false;
                }
                break;
            case BattleShipConstants.SHIP_LENGTH_3:
                if (this.shipCount(length) >= 3) {
                    return false;
                }
                break;
            case BattleShipConstants.SHIP_LENGTH_4:
                if (this.shipCount(length) >= 2) {
                    return false;
                }
                break;
            case BattleShipConstants.SHIP_LENGTH_5:
                if (this.shipCount(length) >= 1) {
                    return false;
                }
                break;
            default:
                return false;
        }

        /*Switch hat nirgends false zurück geliefert, wir landen hier. wir überprüfen mit der isAreaFree Methode, ob
        wir am gewünschten Ort setzen dürfen. Wie?(steht oben beschrieben). Falls true, adden wir ein Objekt der
        Klasse Ship (also ein Schiff) zu unserer ArrayList fleet mittels dem Konstruktor der Klasse Ship. Wieso
        diffvectorx und y? Das steht in der main bei der Methode saveShips dabei.*/

        if (isAreaFree(position, length, direction)) {
            this.fleet.add(new Ship(position, length, direction, diffvectorx, diffvectory));
            return true;
        }
        return false;
    }

    /*Es überprüft für jedes Schiff der Flotte (ArrayList mit Schiffen) ob die x,y Koordinaten zutreffen. Wenn ja,
    dann werden die Koordinaten weitergegeben und die attack Methode in der Klasse Ship überprüft das gleiche für
    jeden ShipPart.*/
    public boolean attack(Position position) {
        for (Ship warship : this.fleet) {
            if (warship.attack(position)) {
                return true;
            }
        }
        return false;
    }


/*Checkt für jeden ShipPart jedes Schiffes im fleet ArrayList, ob es destroyed ist. Wenn x und y auf ein ganzes
Schiff zutreffen und checkIfDestroyed (Ship-Klasse) true liefert, returned es das zerstörte Schiff, ansonsten null.*/
    public Ship isDestroyed(Position position) {
        for (Ship ship : this.fleet){
            for (ShipPart part : ship.getShipParts()) {
                if (part.getPosition().equals(position) && ship.checkIfDestroyed()) {
                    return ship;
                }
            }
        }
        return null;
    }

    /**
     * Checks if all ships are destroyed and the game is over
     *
     * @return true if all ships are destroyed
     */
    public boolean checkGameOver() {
        for (Ship ship : this.fleet) {
            if (!ship.checkIfDestroyed()) {
                return false;
            }
        }
        return true;
    }

    /**
     *  The whole fleet will be deleted
     *
     */
    public void removeAll() {
        this.fleet = new ArrayList<>(0);
    }
}
