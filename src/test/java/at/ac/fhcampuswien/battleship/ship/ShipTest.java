package at.ac.fhcampuswien.battleship.ship;

import at.ac.fhcampuswien.battleship.Position;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Test
    void testAttack() {
        Position position = new Position(0, 0);
        Ship ship = new Ship();

        ArrayList<ShipPart> shipPartList = ship.generateShip(position, 5, Direction.RIGHT);
        assertEquals(shipPartList.size(), 5);
    }
}