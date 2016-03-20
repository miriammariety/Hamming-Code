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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner in = new Scanner(System.in);
        System.out.println("Input:");
        String input = in.nextLine();
        while(invalidData(input)){
            System.out.print("Invalid input! Input again: ");
            input = in.nextLine();
        }
        
        System.out.println("Select Parity: 1. Odd\t 2.Even");
        int parity = in.nextInt();
        while(invalidParity(parity)){
            System.out.print("Invalid Parity! Input again: ");
            parity = in.nextInt();
        }
        
    }

    private static boolean invalidData(String input) {
        if(input.length()< 8 || input.length()>24)
            return true;
        for(int i=0; i<input.length(); i++){
            if(input.charAt(i)!='1' && input.charAt(i)!='0')
                return true;
        }
        
        return false;
    }

    private static boolean invalidParity(int parity) {
        if(parity > 2 || parity < 1)
            return true;
        return false;
    }
    
}
