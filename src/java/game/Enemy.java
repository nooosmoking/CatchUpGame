package game;

import com.diogonunes.jcdp.color.api.Ansi;

public class Enemy extends GameObject {
    public Enemy(Ansi.BColor color, char simb, Game.objType type, Position position) {
        super(color, simb, type, position);
    }

    public void kill() {
        Position enemyPos = this.getPosition();
        Position gamerPos = GameMap.getGamer().getPosition();
        if (enemyPos.isNear(gamerPos)) {
            GameMap.draw();
            Exiter.exitFail();
        }
        Position newPos = ChaseLogic.findFirstStep(GameMap.getMap(), enemyPos, gamerPos);

        if (newPos != null) {
            GameMap.switchPositions(this.getPosition(), newPos);
        }
        if (this.getPosition().isNear(gamerPos)) {
            GameMap.draw();
            Exiter.exitFail();
        }
    }
}
