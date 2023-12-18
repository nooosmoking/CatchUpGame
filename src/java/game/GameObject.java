package game;

import com.diogonunes.jcdp.color.api.Ansi;

public class GameObject {
    private Ansi.BColor color;
    private char simb;
    private Position position;
    private Game.objType type;

    public GameObject(Ansi.BColor color, char simb, Game.objType type, Position position) {
        this.color = color;
        this.simb = simb;
        this.position = position;
        this.type = type;
    }

    public Ansi.BColor getColor() {
        return color;
    }

    public char getSimb() {
        return simb;
    }

    public Position getPosition() {
        return position;
    }

    public Game.objType getType() {
        return type;
    }

    public void setPos(Position pos) {
        this.position = pos;
    }
}
