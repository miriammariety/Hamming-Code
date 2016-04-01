/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hammingcode;

import java.util.Scanner;

/**
 *
 * @author ty
 */
public class HammingCode {

    int bitCount;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        HammingCode program = new HammingCode();
        Scanner in = new Scanner(System.in);
        boolean runProgram = true;
        while (runProgram) {
            System.out.println("Choose: 1. Create Hamming Code\t 2.Correct Hamming Code");
            System.out.print("Choice: ");
            int choice = in.nextInt();
            while (invalidChoice(choice)) {
                System.out.println("Invalid choice! Input again: ");
                choice = in.nextInt();
            }
            if (choice == 1) {
                program.create();
            } else if (choice == 2) {
                program.correct();
            }
            in.nextLine();
            System.out.println("Run again? y/n");
            String answer = in.nextLine();
            answer = answer.toLowerCase();
            runProgram = answer.equals("y");
        }

    }

    public void create() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input data: ");
        String input = in.nextLine();
        while (invalidData(input)) {
            System.out.print("Invalid input! Input again: ");
            input = in.nextLine();
        }

        System.out.println("Select Parity: 1. Odd\t 2.Even");
        System.out.print("Choice: ");
        int parity = in.nextInt();
        while (invalidChoice(parity)) {
            System.out.print("Invalid Parity! Input again: ");
            parity = in.nextInt();
        }

        createCodeword(input, parity);
    }

    public void correct() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input Codeword: ");
        String input = in.nextLine();

        //Ask for correct codeword
        while (invalidData(input)) {
            System.out.print("Invalid input! Input again: ");
            input = in.nextLine();
        }

        //Ask for correct parity
        System.out.println("Select Parity: 1. Odd\t 2.Even");
        System.out.print("Choice: ");
        int parity = in.nextInt();
        while (invalidChoice(parity)) {
            System.out.print("Invalid Parity! Input again: ");
            parity = in.nextInt();
        }

        //Correct codeword
        int bitError = checkBitError(input, parity);
        if (bitError != 0) {
            System.out.println("Codeword (Corrected): ");
            StringBuilder sb = new StringBuilder(input);
            if (input.charAt(bitError - 1) == '0') {
                sb.setCharAt((bitError - 1), '1');
            } else {
                sb.setCharAt((bitError - 1), '0');
            }
            System.out.println(sb);
        }
    }

    private void createCodeword(String input, int parity) {
        String temp = insertParity(input);
        String codeword = replaceParity(temp, parity);
        System.out.println("CODEWORD: " + codeword);
    }

    private String insertParity(String input) {
        bitCount = (int) (Math.log(input.length()) / Math.log(2)) + 1;
        int counter = 0;
        StringBuilder builder = new StringBuilder(input);
        while (counter < bitCount) {
            builder.insert((int) Math.pow(2, counter++) - 1, 'X');
        }
        return builder.toString();
    }

    private String replaceParity(String temp, int parity) {
        while (temp.contains("X")) {
            int start = temp.indexOf('X');
            String bit = calculateCheckbit(temp, start, parity);
            temp = temp.replaceFirst("X", bit);
        }
        return temp;
    }

    private String calculateCheckbit(String temp, int start, int parity) {
        int skip = start + 1;
        boolean use = true;
        char[] bits = new char[temp.length()];
        int j = 0;
        
        //Loops for skips
        for (int i = start, counter = 1; i < temp.length(); i++, counter++) {
            if (use) {
                bits[j++] = temp.charAt(i);
            }
            if (counter % skip == 0) {
                use = !use; //Set to opposite
            }
        }
        int counter = 0;
        for (char bit : bits) {
            if (Character.isDigit(bit)) {
                if (bit == '1') {
                    counter++;
                }
            }
        }

        return setParity(parity, counter);
    }

    private int checkBitError(String input, int parity) {
        bitCount = (int) (Math.log(input.length()) / Math.log(2)) + 1;
        int position = 0;
        for (int i = 0; i < bitCount; i++) {
            int rIndex = (int) Math.pow(2, i) - 1;
            if (calculateR(input, rIndex, parity).equals("1")) {
                position += rIndex + 1;
            }
        }

        if (position == 0) {
            System.out.println("No errors");
        } else {
            System.out.println("Error at position " + position);
        }

        return position;
    }

    private String calculateR(String input, int rIndex, int parity) {
        int skip = rIndex + 1;
        boolean isPart = true;
        int bitCounter = 0;
        for (int i = rIndex, ctr = 1; i < input.length(); i++, ctr++) {
            if (isPart) {

                if (input.charAt(i) == '1') {
                    bitCounter++;
                }
            }

            if (ctr % skip == 0) {
                isPart = !isPart;
            }
        }

        return setParity(parity, bitCounter);
    }

    private static boolean invalidData(String input) {
//        if (input.length() < 8 || input.length() > 24) {
//            return true;
//        }
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) != '1' && input.charAt(i) != '0') {
                return true;
            }
        }

        return false;
    }

    private static boolean invalidChoice(int choice) {
        if (choice > 2 || choice < 1) {
            return true;
        }
        return false;
    }

    private String setParity(int parity, int counter) {
        int p = 0;
        switch (parity) {
            case 2:
                p = 0;
                break;
            case 1:
                p = 1;
                break;
        }

        if (counter % 2 == p) {
            return "0";
        } else {
            return "1";
        }
    }
}
