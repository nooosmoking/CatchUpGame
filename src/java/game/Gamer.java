package game;

import com.diogonunes.jcdp.color.api.Ansi;

public class Gamer extends GameObject {
    public Gamer(Ansi.BColor color, char simb, Game.objType type, Position position) {
        super(color, simb, type, position);
    }

    public boolean run(String answer) {
        int newX = this.getPosition().getX();
        int newY = this.getPosition().getY();
        switch (answer) {
            case "w":
                newY -= 1;
                break;
            case "a":
                newX -= 1;
                break;
            case "s":
                newY += 1;
                break;
            case "d":
                newX += 1;
                break;
        }
        if (newX < 0 || newX >= GameMap.getSize() || newY < 0 || newY >= GameMap.getSize())
            Exiter.exitFail();

        Position newPos = new Position(newX, newY);
        switch (GameMap.getObjAt(newPos).getType()) {
            case WALL:
                return false;
            case TARGET:
                Exiter.exitWin();
        }

        GameMap.switchPositions(this.getPosition(), newPos);
        return true;
    }
}
