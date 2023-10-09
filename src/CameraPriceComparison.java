/**
 * @author Max Day
 * Created At: 2023/10/09
 */

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CameraPriceComparison {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the number of manufacturers:");
        int numManufacturers = scanner.nextInt();
        //Define the 1D arrays
        String[] manufacturers = new String[numManufacturers];
        double[] mPrices = new double[numManufacturers];
        double[] dslrPrices = new double[numManufacturers];
        //Define the 2D arrays
        String[][] finalPrices = new String[numManufacturers][2];

        // Input prices for each manufacturer
        for (int i = 0; i < numManufacturers; i++) {
            inputPrices(scanner, manufacturers, mPrices, dslrPrices, i);
            finalPrices[i][0] = "R" + mPrices[i];
            finalPrices[i][1] = "R" + dslrPrices[i];
        }

        /**             DISPLAY         */
        // I apologise in advance for the massive one-liners there's nothing I could do to solve it
        System.out.println("---------------------------------------\nCAMERA TECHNOLOGY REPORT\n---------------------------------------\n\t\t\tMIRRORLESS\t\tDSLR");//simplified formatting of print statements
        double maxDif = 0.0;
        String maxDiffManufacturer = manufacturers[0];
        for (int i = 0; i < numManufacturers; i++) {
            System.out.println(manufacturers[i] + "\t\t" + finalPrices[i][0] + "\t\t" + finalPrices[i][1]);
            double dif = parsePrice(finalPrices[i][0]) - parsePrice(finalPrices[i][1]);
            if (maxDif < dif) {
                maxDif = dif;
                maxDiffManufacturer = manufacturers[i];
            }
        }
        System.out.println("---------------------------------------\nCAMERA TECHNOLOGY RESULTS\n---------------------------------------");
        /** I have included this to explain how we choose to print the stars since its berried all the way at the end of the one-liner. Basically we check if the current manufacturer name is the same as the max different manufacture name*/
        for (int i = 0; i < numManufacturers; i++)
            System.out.println(manufacturers[i] + "\tR" + round2dp(parsePrice(finalPrices[i][0]) - parsePrice(finalPrices[i][1])) + (manufacturers[i].equalsIgnoreCase(maxDiffManufacturer) ? " *** " : ""));
        System.out.println("\n\nCAMERA WITH THE MOST COST DIFFERENCE: " + maxDiffManufacturer + "\n---------------------------------------");
        scanner.close();
    }


    private static void inputPrices(Scanner scanner, String[] manufacturers, double[] mPrices, double[] dslrPrices, int index) {// read prices of a unit then map
        System.out.println("Please enter the manufacturer name: ");
        manufacturers[index] = scanner.next();
        mPrices[index] = readValidPrice(scanner, "mirrorless"); //Breaking it down into two units makes it easier to read but also expandable so any amounts of manufacturers can be inputted
        dslrPrices[index] = readValidPrice(scanner, "DSLR");
    }

    private static double round2dp(double input) {// silly little method to make code look "cleaner"
        return Math.round(input * 100.0) / 100.0;
    }

    private static double readValidPrice(Scanner scanner, String cameraType) { //using a while true statement here means if the user enters incorrect data, the program won't crash but will ask for a reinput
        while (true) {
            System.out.printf("Please enter the %s camera price (eg. R10500.00): ", cameraType);
            String priceInput = scanner.next();
            if (isValidDecimal(priceInput)) return parsePrice(priceInput);
            System.out.println("Invalid input. Please enter a valid price with two decimal places.");
        }
    }

    private static double parsePrice(String priceString) {
        return Double.parseDouble(priceString.substring(1)); // Remove 'R' and convert to double
    }

    public static boolean isValidDecimal(String input) { // regix to test if number is valid. TLDR: checks for num 0-9 then a decimal point then 2 numbers right after
        String decimalPattern = "^[0-9]+\\.[0-9]{2}$";
        Matcher matcher = Pattern.compile(decimalPattern).matcher(input.substring(1));
        return matcher.matches();
    }
}