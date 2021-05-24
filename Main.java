package com.company;

/**
 * Τμήμα Πληροφορικής Α.Π.Θ.
 * Ακαδημαϊκό  έτος 2018-2019
 * Προαιρετική εργασία για το μάθημα Ψηφιακές Επικοινωνίες (4ο εξάμηνο) : Υλοποίηση Αλγορίθμου ανίχνευσης σφαλμάτων CRC.
 * @author Ηλίας Τερζής , ΑΕΜ: 3170 , terzisilias@csd.auth.gr
 * @version 1.0
 * Αυτή η κλάση χρησιμοποείται για την εκκίνηση του προγράμματος.
 */
public class Main {

    public static void main(String[] args) {
	Cli cli=new Cli();
	Message mess=new Message(cli.askForNumOfMess(),cli.askForSizeOfMess(),cli.askForBER(),cli.askForP());
	//τα ακόλουθα σχόλια είναι για εμφανίσεις που χρησιμοποιούνται για testing
//    mess.showMessages();
//    mess.showP();
//    System.out.println("adding zeros...");
    mess.addZerosToMess();
//    mess.showMessages();
    mess.applyCRCforMessages();
//    System.out.println("after crc ... ");
//    mess.showMessages();
    mess.errorGenerator();
    mess.crcCheck();
    cli.showCorr(mess.getPlithos(),mess.getIncorr(), mess.getCrcincorrect());
    }
}
