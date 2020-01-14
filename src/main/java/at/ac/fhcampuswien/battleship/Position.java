package at.ac.fhcampuswien.battleship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Position {

    private int x;

    private int y;

    public boolean equals(Position position) {
        return position.getX() == getX() && position.getY() == getY();
    }
}
