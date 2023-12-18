package game;

import com.diogonunes.jcdp.color.api.Ansi;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ScanConfigFile {
    private static String profileProduction = "src/resources/application-production.properties";
    private static String profileDev = "src/resources/application-dev.properties";

    private static char enemyChar;
    private static char playerChar;
    private static char wallChar;
    private static char goalChar;
    private static char emptyChar;
    private static Ansi.BColor enemyColor;
    private static Ansi.BColor playerColor;
    private static Ansi.BColor goalColor;
    private static Ansi.BColor emptyColor;
    private static Ansi.BColor wallColor;

    public static char getEnemyChar() {
        return enemyChar;
    }

    public static char getPlayerChar() {
        return playerChar;
    }

    public static char getWallChar() {
        return wallChar;
    }

    public static char getGoalChar() {
        return goalChar;
    }

    public static char getEmptyChar() {
        return emptyChar;
    }

    public static Ansi.BColor getEnemyColor() {
        return enemyColor;
    }

    public static Ansi.BColor getPlayerColor() {
        return playerColor;
    }

    public static Ansi.BColor getGoalColor() {
        return goalColor;
    }

    public static Ansi.BColor getEmptyColor() {
        return emptyColor;
    }

    public static Ansi.BColor getWallColor() {
        return wallColor;
    }

    public static void scanFile(boolean isDev) {
        File file;
        if (isDev) {
            file = new File(profileDev);
        } else {
            file = new File(profileProduction);
        }
        try (FileReader fileReader = new FileReader(file)) {
            Properties properties = new Properties();

            properties.load(fileReader);

            enemyChar = properties.getProperty("enemy.char").charAt(0);
            playerChar = properties.getProperty("player.char").charAt(0);
            wallChar = properties.getProperty("wall.char").charAt(0);
            goalChar = properties.getProperty("goal.char").charAt(0);
            emptyChar = properties.getProperty("empty.char").charAt(0);

            enemyColor = Ansi.BColor.valueOf(properties.getProperty("enemy.color"));
            playerColor = Ansi.BColor.valueOf(properties.getProperty("player.color"));
            goalColor = Ansi.BColor.valueOf(properties.getProperty("goal.color"));
            emptyColor = Ansi.BColor.valueOf(properties.getProperty("empty.color"));
            wallColor = Ansi.BColor.valueOf(properties.getProperty("wall.color"));
        } catch (IllegalArgumentException | StringIndexOutOfBoundsException e) {
            Exiter.exitWithMsg("Incorrect properties file");
        } catch (NullPointerException e) {
            Exiter.exitWithMsg("Check the configuration file is correct");
        } catch (IOException e) {
            Exiter.exitWithMsg(e.getMessage());
        }
    }
}