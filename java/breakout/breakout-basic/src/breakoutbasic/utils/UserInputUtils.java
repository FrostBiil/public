package breakoutbasic.utils;

import java.util.Scanner;

/**
 * Utils to get user input
 */
public class UserInputUtils {
    public static Scanner scan = new Scanner(System.in); // Scanner

    /**
     * Get a number from the user in the terminal
     *
     * @param textStatement what kind of text
     * @return a number or run it again if the number was invalid
     */
    public static int getUserInputInteger(String textStatement) {
        // Making sure the input is a number
        try {
            System.out.print(textStatement);
            return scan.nextInt();
        }
        catch (Exception e) {
            scan.next();
            return getUserInputInteger(textStatement);
        }
    }

}
