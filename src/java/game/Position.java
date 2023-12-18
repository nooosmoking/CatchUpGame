package game;

import java.util.LinkedList;
import java.util.Random;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isNear(Position other) {
        boolean near = false;
        if (other.x == this.x && (Math.abs(this.y - other.y) <= 1)) {
            near = true;
        } else if (other.y == this.y && (Math.abs(this.x - other.x) <= 1)) {
            near = true;
        }
        return near;
    }

    public LinkedList<Position> getListNears() {
        LinkedList<Position> nearPositions = new LinkedList<>();
        if (x > 0) {
            nearPositions.add(new Position(x - 1, y));
        }
        if (x < GameMap.getSize() - 1) {
            nearPositions.add(new Position(x + 1, y));
        }
        if (y > 0) {
            nearPositions.add(new Position(x, y - 1));
        }
        if (y < GameMap.getSize() - 1) {
            nearPositions.add(new Position(x, y + 1));
        }
        return nearPositions;

    }

    public boolean isEquals(Position other) {
        return this.x == other.getX() && this.y == other.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public static Position getRandomPosition(int size, GameObject[][] map) {
        int x = new Random().nextInt(size);
        int y = new Random().nextInt(size);

        while ((map[y][x].getType() != Game.objType.EMPTY)) {
            x = new Random().nextInt(size);
            y = new Random().nextInt(size);
        }
        return new Position(x, y);
    }
}
