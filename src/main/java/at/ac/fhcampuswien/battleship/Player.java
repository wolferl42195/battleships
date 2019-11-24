package at.ac.fhcampuswien.battleship;

import at.ac.fhcampuswien.battleship.ship.Direction;

import java.util.ArrayList;
import java.util.Random;

public class Player {

    Field area = new Field();

    private ArrayList<Position> attackPositions = new ArrayList<>();

    private ArrayList<AIsave> AIsave = null;

    private boolean isHuman;

    Player(boolean isHuman)
    {
        this.isHuman = isHuman;
    }

    public void saveAttack(int x, int y) {
        this.attackPositions.add(new Position(x, y));
    }

    public boolean attackPossible(Position position) {
        for (Position a : this.attackPositions) {
            if (a.equals(position)) {
                return false;
            }
        }
        return true;
    }

    public void reset(){
        this.attackPositions = new ArrayList<>();
    }

    public void setHuman(boolean human) {
        isHuman = human;
    }


    public boolean AISet() {
        if (!isHuman) {
            return false;
        } else {
            this.AISetting(2);
            this.AISetting(2);
            this.AISetting(2);
            this.AISetting(2);
            this.AISetting(3);
            this.AISetting(3);
            this.AISetting(3);
            this.AISetting(4);
            this.AISetting(4);
            this.AISetting(5);
            return true;
        }
    }

    private void AISetting(int length) {
        int x, y;
        Direction direction;
        Random random = new Random();
        do
        {
            x = random.nextInt((9 - 0) + 1) + 0;
            y = random.nextInt((9 - 0) + 1) + 0;
            direction = Direction.RIGHT;
            switch (random.nextInt((3 - 0) + 1) + 0)
            {
                case 0:
                    direction = Direction.RIGHT;
                    break;
                case 1:
                    direction = Direction.UP;
                    break;
                case 2:
                    direction = Direction.LEFT;
                    break;
                case 3:
                    direction = Direction.DOWN;
                    break;
            }
        } while (this.area.setShip(new Position(x, y), length, direction, 0, 0));
    }

    public boolean simpleAIAttack(Player enemy) {
        int x, y;
        Random random = new Random();
        do
        {
            x = random.nextInt((9 - 0) + 1) + 0;
            y = random.nextInt((9 - 0) + 1) + 0;
        } while (this.attackPossible(new Position(x, y)));
        this.saveAttack(x, y);
        return enemy.area.attack(new Position(x, y));
    }

    public boolean complexAIAttack(Player enemy) {
        Random random = new Random();
        boolean result;
        int x, y;
        Direction direction;
        if (this.AIsave == null) {
            do
            {
                x = random.nextInt((9 - 0) + 1) + 0;
                y = random.nextInt((9 - 0) + 1) + 0;
            } while (this.attackPossible(new Position(x, y)));
            this.saveAttack(x, y);
            result = enemy.area.attack(new Position(x, y));
            if (enemy.area.isDestroyed(new Position(x, y)) != null)
            {
                return true;
            } else if (!result)
            {
                return false;
            } else
            {
                AIsave = new ArrayList<>();
                AIsave.add(new AIsave(new Position(x, y), false));
            }
        } else if (AIsave.get(0).getDirection() == null)
        {
            direction = Direction.DOWN;
            x = AIsave.get(0).getX();
            y = AIsave.get(0).getY();
            do
            {
                switch (random.nextInt((3 - 0) + 1) + 0)
                {
                    case 0:
                        direction = Direction.RIGHT;
                        x++;
                        break;
                    case 1:
                        direction = Direction.UP;
                        y--;
                        break;
                    case 2:
                        direction = Direction.LEFT;
                        x--;
                        break;
                    case 3:
                        direction = Direction.DOWN;
                        y++;
                        break;
                }
            } while (this.attackPossible(new Position(x, y)));
            result = enemy.area.attack(new Position(x, y));
            this.saveAttack(x, y);
            if (result)
            {
                AIsave.add(new AIsave(new Position(x, y), direction, true));
                return true;
            } else
            {
                return false;
            }
        } else
        {
            x = AIsave.get(0).getX();
            y = AIsave.get(0).getY();
            switch (AIsave.get(0).getDirection())
            {
                case RIGHT:
                    x += (int) AIsave.size();
                    break;
                case LEFT:
                    x -= (int) AIsave.size();
                    break;
                case DOWN:
                    y += (int) AIsave.size();
                    break;
                case UP:
                    y -= (int) AIsave.size();
                    break;
            }
            if (this.attackPossible(new Position(x, y)))
            {
                this.saveAttack(x, y);
                result = enemy.area.attack(new Position(x, y));
                if (result)
                {
                    AIsave.add(new AIsave(new Position(x, y), AIsave.get(0).getDirection(), false));
                    return result;
                } else
                {
                    switch (AIsave.get(0).getDirection()) {
                        case RIGHT:
                            x--;
                            break;
                        case LEFT:
                            x++;
                            break;
                        case DOWN:
                            y--;
                            break;
                        case UP:
                            y++;
                            break;
                    }
                    if (this.attackPossible(new Position(x, y))) {
                        AIsave a = AIsave.get(0);
                        direction = Direction.LEFT;
                        switch (AIsave.get(0).getDirection()) {
                            case RIGHT:
                                direction = Direction.LEFT;
                                break;
                            case LEFT:
                                direction = Direction.RIGHT;
                                break;
                            case DOWN:
                                direction = Direction.UP;
                                break;
                            case UP:
                                direction = Direction.DOWN;
                                break;
                        }
                        a.setDirection(direction);
                        AIsave = new ArrayList<>();
                        AIsave.add(a);
                        AIsave.add(new AIsave(new Position(x, y), direction, false));
                        return true;
                    } else
                    {
                        AIsave = null;
                        return false;
                    }
                }
            }
        }
        return false;
    }
}
