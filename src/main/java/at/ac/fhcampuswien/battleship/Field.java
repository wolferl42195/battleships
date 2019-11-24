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

    private boolean isAreaFree(Position position, int length, Direction dir) {
        int x = position.getX();
        int y = position.getY();
        for (int i = 0; i < length; i++) {
            /*Hier, nimmt es die Koordinaten und prüft ob es innerhalb vom Spielfeld liegt. Wenn nicht, returned er false und
            isAreaFree liefert in der setShip Methode false zurück (was dann passiert, steht in der setShip Methode)*/
            if (x < 0 || x > 9 || y < 0 || y > 9) {
                return false;
            }
            if (!this.isFree(new Position(x,y))) {
                return false;
            }

            /*Wenn beide if-Bedienungen true zurück liefern, erhöhen wir entweder die x oder y Koordinate
            abhängig von der Richtung. Wenn das Schiff nach oben zeigt, müssen wir y-- machen, um den nächsten 40
            Pixelblock (== 1 ShipPart) zu überprüfen, ob da ein Schiff gesetzt werden darf. Das machen wir alles so
            lang, wie die Länge von dem Schiff, das wir setzen wollen. (For-Schleife)*/
            switch (dir) {
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

    /*Es zählt wie viele Schiffe es in der Länge schon gibt, in der wir gerade anlegen wollen. Nimmt hier aber noch
    keine Rücksicht darauf, ob es schon 4 in der Länge 2 z.B schon gibt. Das passiert erst in der setShip Methode bzw
    . isFleetComplete. */
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
        return ((this.shipCount(2) == 4 &&
                this.shipCount(3) == 3 &&
                this.shipCount(4) == 2 &&
                this.shipCount(5) == 1));
    }

    public boolean setShip(int x, int y, int length, Direction dire, int diffvectorx, int diffvectory) {
        switch (length) {
            case 2:
                if (this.shipCount(length) >= 4) {
                    return false;
                }
                break;
            case 3:
                if (this.shipCount(length) >= 3) {
                    return false;
                }
                break;
            case 4:
                if (this.shipCount(length) >= 2) {
                    return false;
                }
                break;
            case 5:
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

        if (isAreaFree(new Position(x, y), length, dire)) {
            Position position = new Position(x, y);
            this.fleet.add(new Ship(position, length, dire, diffvectorx, diffvectory));
            return true;
        }
        return false;
    }

    /*Es überprüft für jedes Schiff der Flotte (ArrayList mit Schiffen) ob die x,y Koordinaten zutreffen. Wenn ja,
    dann werden die Koordinaten weitergegeben und die attack Methode in der Klasse Ship überprüft das gleiche für
    jeden ShipPart.*/
    public boolean attack(int x, int y) {
        Position position = new Position(x,y);
        for (Ship warship : this.fleet) {
            if (warship.attack(position)) {
                return true;
            }
        }
        return false;

    }
/*Checkt für jeden ShipPart jedes Schiffes im fleet ArrayList, ob es destroyed ist. Wenn x und y auf ein ganzes
Schiff zutreffen und checkIfDestroyed (Ship-Klasse) true liefert, returned es das zerstörte Schiff, ansonsten null.*/
    public Ship isDestroyed(int x, int y) {
        for (Ship warship : this.fleet){
            for (ShipPart part : warship.getShipParts()) {
                if (part.getX() == x && part.getY() == y && warship.checkIfDestroyed()) {
                    return warship;
                }
            }
        }
        return null;
    }

    /*Es geht jedes Schiff durch und schaut ob es zerstört ist.*/
    public boolean gameOver() {
        for (Ship ship : this.fleet) {
            if (!ship.checkIfDestroyed()) {
                return false;
            }
        }
        return true;
    }

    /*Verwendung: reset Methode in der Main. Wenn reset aufgerufen wird, wird removeAll aktiviert, bedeutet, dass wir
    eine neue ArrayList fleet erstellen (die alte wird gelöscht quasi).*/
    public void removeAll() {
        this.fleet = new ArrayList<>(0);
    }


}
