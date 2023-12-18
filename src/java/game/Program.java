package game;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import game.exeptions.IllegalParametersException;

public class Program {

    public static void main(String[] args) {
        Args jArgs = new Args();
        JCommander JCommander = new JCommander(jArgs);
        try {
            try {
                JCommander.parse(args);
                ScanConfigFile.scanFile(jArgs.isDevProfile());
                checkArgs(jArgs);
            } catch (ParameterException ex) {
                throw new IllegalParametersException(ex.getMessage());
            }
        } catch (IllegalParametersException ex) {
            Exiter.exitWithMsg(ex.getMessage());
        }
        Game game = new Game(jArgs);
        game.run();
    }

    public static void checkArgs(Args jArgs) throws IllegalParametersException {
        if (jArgs.getSize() <= 2 || jArgs.getEnemiesCount() <= 0 || jArgs.getWallsCount() < 0) {
            throw new IllegalParametersException("Too small count in args!");
        }

        if (jArgs.getWallsCount() + jArgs.getEnemiesCount() > (jArgs.getSize() * jArgs.getSize()) - 3) {
            throw new IllegalParametersException("Reduce the count of enemies and obstacles or increase the field size");
        }

        if (!jArgs.isValidProfile()) {
            throw new IllegalParametersException("Invalid profile mode");
        }
    }
}