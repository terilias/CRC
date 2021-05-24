package com.company;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Ηλίας Τερζής , ΑΕΜ: 3170 , terzisilias@csd.auth.gr
 * @version 1.0
 * Αυτή η κλάση υλοποιεί την διεπαφή με τον χρήστη μέσω τερματικού (Command line interaction).
 */
public class Cli {
    private Scanner scanner=new Scanner(System.in);
    public Cli(){

    }

    /**
     * Ρωτάει τον χρήστη για τον αριθμό των μηνυμάτων που θα σταλούν.
     */
    public int askForNumOfMess(){
        int n=0;
        boolean ok=false;
        System.out.println("Number of messages will you send : ");
        do{
            try{

                n=scanner.nextInt();
                ok=true;
            }catch (java.util.InputMismatchException e){
                System.out.println("Error with input.Please give an integer. ");
            }
            scanner.nextLine();
        }while (!ok);
        return n;

    }
    /**
     * Ρωτάει τον χρήστη για τον αριθμό των bits που θα αποτελούν το κάθε μήνυμα.
     */
    public int askForSizeOfMess(){
        int n=0;
        boolean ok=false;
        System.out.println("Number of bits for every message : ");
        do{
            try{

                n=scanner.nextInt();
                ok=true;
            }catch (java.util.InputMismatchException e){
                System.out.println("Error with input.Please give an integer. ");
            }
            scanner.nextLine();
        }while (!ok);
        return n;

    }
    /**
     * Ρωτάει τον χρήστη για το P.
     */
    public long askForP(){
        long p=0;
        boolean ok=false;
        System.out.println("The P number for CRC algorithm is : ");
        do{
            try{

                p=scanner.nextLong();
                ok=true;
                long number=p;
                long d;
                while (number > 0) {//check if the given number isn't in binary
                    d=number%10;
                    if( d!=0 && d!=1 ){
                        ok=false;
                        throw new InputMismatchException() ;
                    }
                    number = number / 10;
                }

            }catch (java.util.InputMismatchException e){
                System.out.println("Error with input.Please give a binary integer. \n");
            }
            scanner.nextLine();
        }while (!ok);
        return p;

    }

    /**
     * Ρωτάει τον χρήστη για το Bit Error Rate.
     */
    public int askForBER(){
        int ber=0;
        boolean ok=false;
        System.out.println("Bit Error Rate has  base  10 , give the exponent : ");
        do{
            try{

                ber=scanner.nextInt();
                ok=true;
            }catch (java.util.InputMismatchException e){
                System.out.println("Error with input.Please give an integer. ");
            }
            scanner.nextLine();
        }while (!ok);
        return ber;

    }

    /**
     * Εμφανίζει τα αποτελέσματα της μετάδοσης : πόσα μηνύματα μεταδόθηκαν εδφαλμένα και πόσα ο CRC ανίχνευσε ως εσφαλμένα.
     * @param plithos
     * @param incorr
     * @param crcincorr
     */
    public void showCorr(int plithos , int incorr,int crcincorr){

        System.out.println("Transmission completed.");
        System.out.println("In this session "+plithos+" messages were transmitted.");
        System.out.println("CRC detected as incorrect "+crcincorr+" messages.");
        System.out.println("Ιn fact they were sent with error "+incorr+" messages");

}

}
