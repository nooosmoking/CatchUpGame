package game;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Exiter {
    public static void exitWithMsg(String msg) {
        System.err.println(msg);
        AnswerChecker.closeScanner();
        System.exit(-1);
        AnswerChecker.closeScanner();
    }

    public static void exitWin() {
        File file = checkFile("win.bmp");
        try {
            printImg(file);
        } catch (Exception ex) {
            System.out.println("You win!");
        }
        AnswerChecker.closeScanner();
        System.exit(0);
    }

    public static void exitFail() {
        File file = checkFile("lose.bmp");
        try {
            printImg(file);
        } catch (Exception ex) {
            System.out.println("You lose!");
        }
        AnswerChecker.closeScanner();
        System.exit(0);
    }

    public static void printImg(File file) throws Exception {
        System.out.println();
        BufferedImage image = ImageIO.read(file);
        ColoredPrinter printer = new ColoredPrinter();

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int color = image.getRGB(j, i);
                if (color == -1) {
                    printer.print("  ", Ansi.Attribute.NONE,
                            Ansi.FColor.NONE, Ansi.BColor.YELLOW);
                } else {
                    printer.print("  ", Ansi.Attribute.NONE,
                            Ansi.FColor.NONE, Ansi.BColor.MAGENTA);
                }

            }
            System.out.print("\n");
        }
        image.flush();
    }

    public static File checkFile(String name) {
        try {
            File file = new File("src/resources/" + name);
            if (!file.exists()) {
                return null;
            } else {
                return file;
            }
        } catch (Exception ex) {
            exitWithMsg(ex.getMessage());
        }
        return null;
    }

}
