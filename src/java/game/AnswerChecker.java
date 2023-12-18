package game;

import java.util.Scanner;

public class AnswerChecker {
    private static Scanner in = new Scanner(System.in);

    public static String checkMoveGamer() {
        String answer = in.next().trim().toLowerCase();
        if (answer.equals("9")) {
            Exiter.exitFail();
        } else if (answer.equals("a") || answer.equals("s") || answer.equals("d") || answer.equals("w")) {
            return answer;
        }
        System.err.println("Incorrect input!");
        return null;
    }

    public static void checkMoveEnemy() {
        boolean repeat = true;
        do {
            String answer = in.next().trim().toLowerCase();
            if (!answer.equals("8")) {
                System.err.println("Incorrect input!");
            } else {
                repeat = false;
            }
        } while (repeat);
    }
}
