package game;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import game.exeptions.IllegalParametersException;

import java.util.LinkedList;

public class GameMap {
    private static GameObject[][] map;
    private static int size;
    private int enemiesCount;
    private int wallsCount;
    private static ColoredPrinter printer;

    private static Gamer gamer;
    private static LinkedList<Enemy> enemies;
    private GameObject target;

    public GameMap(int sizeMap, int enemiesCount, int wallsCount)  {
        this.printer = new ColoredPrinter();
        size = sizeMap;
        map = new GameObject[size][size];
        this.enemiesCount = enemiesCount;
        this.wallsCount = wallsCount;
        enemies = new LinkedList<>();
        try{
            setAllObjects(0);
        } catch (IllegalParametersException ex) {
            Exiter.exitWithMsg(ex.getMessage());
        }
    }

    private void setAllObjects(int depth) throws IllegalParametersException {
        if (depth >2000 ){
            throw new IllegalParametersException("Reduce the number of enemies and obstacles or increase the field size");
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = new GameObject(ScanConfigFile.getEmptyColor(), ScanConfigFile.getEmptyChar(), Game.objType.EMPTY, new Position(j, i));
            }
        }
        gamer = null;
        enemies.clear();
        target = null;
        for (int i = 0; i < wallsCount; i++) {
            setObject(Game.objType.WALL, depth);
        }
        for (int i = 0; i < enemiesCount; i++) {
            setObject(Game.objType.ENEMY, depth);
        }
            setObject(Game.objType.GAMER, depth);
            setObject(Game.objType.TARGET, depth);
    }

    private void setObject(Game.objType type, int depth) throws IllegalParametersException{
        if (depth >2000 ){
            throw new IllegalParametersException("Reduce the number of enemies and obstacles or increase the field size");
        }
        GameObject object = null;
        Position pos = Position.getRandomPosition(size, map);
        switch (type) {
            case WALL:
                object = new GameObject(ScanConfigFile.getWallColor(), ScanConfigFile.getWallChar(), type, pos);
                break;
            case ENEMY:
                object = new Enemy(ScanConfigFile.getEnemyColor(), ScanConfigFile.getEnemyChar(), type, pos);
                enemies.add((Enemy) object);
                break;
            case GAMER:
                pos = findGamerPos(pos, depth);
                if (gamer == null) {
                    object = new Gamer(ScanConfigFile.getPlayerColor(), ScanConfigFile.getPlayerChar(), type, pos);
                    gamer = (Gamer) object;
                }
                break;
            case TARGET:
                if (target == null) {
                    if (ChaseLogic.findPath(map, gamer.getPosition(), pos) == null) {
                        depth++;
                        setObject(Game.objType.TARGET, depth);
                    } else {
                        object = new GameObject(ScanConfigFile.getGoalColor(), ScanConfigFile.getGoalChar(), type, pos);
                        target = object;
                    }
                }
                break;
        }
        if (object != null) {
            map[pos.getY()][pos.getX()] = object;
        }
    }

    private Position findGamerPos(Position gamerPos,  int depth) throws IllegalParametersException {
        if (depth >2000 ){
            throw new IllegalParametersException("Reduce the number of enemies and obstacles or increase the field size");
        }
        for (int i = 0; i < size * size - wallsCount - enemiesCount && checkEnemiesAround(gamerPos); i++) {
            gamerPos = Position.getRandomPosition(size, map);
        }

        if (checkEnemiesAround(gamerPos)) {
            depth++;
            setAllObjects(depth);
        }
        return gamerPos;
    }


    private boolean checkEnemiesAround(Position pos) {
        LinkedList<Position> listPos = pos.getListNears();
        for (Position nearPos : listPos
        ) {
            if (GameMap.getObjAt(nearPos).getType() == Game.objType.ENEMY) {
                return true;
            }
        }
        return false;
    }

    public static void draw() {
        GameObject currObj;
        System.out.print("\u001b[2J");
        System.out.flush();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                currObj = map[i][j];
                printer.print(currObj.getSimb(), Ansi.Attribute.NONE,
                        Ansi.FColor.NONE, currObj.getColor());
            }
            System.out.println();
        }
    }

    public static Gamer getGamer() {
        return gamer;
    }

    public static LinkedList<Enemy> getEnemies() {
        return enemies;
    }

    public static void switchPositions(Position pos1, Position pos2) {
        GameObject tmp = map[pos1.getY()][pos1.getX()];
        map[pos1.getY()][pos1.getX()] = map[pos2.getY()][pos2.getX()];
        map[pos1.getY()][pos1.getX()].setPos(pos1);
        map[pos2.getY()][pos2.getX()] = tmp;
        map[pos2.getY()][pos2.getX()].setPos(pos2);
    }

    public static GameObject getObjAt(Position pos) {
        return map[pos.getY()][pos.getX()];
    }

    public static int getSize() {
        return size;
    }

    public static GameObject[][] getMap() {
        return map;
    }
}
