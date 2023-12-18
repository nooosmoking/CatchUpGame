package game;

public class Game {
    private GameMap map;
    private boolean profileDev;

    protected enum objType {
        EMPTY,
        ENEMY,
        WALL,
        TARGET,
        GAMER
    }

    public Game(Args jArgs) {
        map = new GameMap(jArgs.getSize(), jArgs.getEnemiesCount(), jArgs.getWallsCount());
        profileDev = jArgs.isDevProfile(jArgs.getProfile());
    }

    public void run() {
        String answer = null;
        boolean fail = false;
        while (true) {
            GameMap.draw();
            answer = AnswerChecker.checkMoveGamer();
            if (answer == null) {
                continue;
            }
            if (!GameMap.getGamer().run(answer)) {
                continue;
            }
            if (profileDev) {
                handleDevProfile();
            } else {
                handleProductionProfile();
            }
        }
    }

    private void handleProductionProfile() {
        for (Enemy enemy : GameMap.getEnemies()) {
            enemy.kill();
        }
    }

    private void handleDevProfile() {
        GameMap.draw();
        for (Enemy enemy : GameMap.getEnemies()) {
            System.out.println("Enter 8 for enemy movement");
            AnswerChecker.checkMoveEnemy();
            enemy.kill();
            GameMap.draw();
        }
        System.out.println("Enemy movements are over, use AWSD to move around");
    }
}


