package com.company;
import java.util.Arrays;
import java.util.Random;
/**
 * @author Ηλίας Τερζής , ΑΕΜ: 3170 , terzisilias@csd.auth.gr
 * @version 1.0
 * Αυτή η κλάση περιλαμβάνει όλες εκείνες τις λειτουργίες που είναι απαραίτητες για τον αλγόριθμο CRC.
 */
public class Message {
    private int plithos,k,ber;//plithos: αριθμος των μηνυμάτων που θα παραχθούν, k: το size σε bits για κάθε μήνυμα, ber:ο αριθμός - εκθέτης για το ΒΕR
    private long[] p;//ο αριθμός p που χρησιμοποιείται για το crc.Είναι long γιατί δοκίμασα με int και δεν έτρεχε για μεγάλες ακολουθίες bit
    private int incorr;//ο αριθμός των μηνυμάτων που  έχουν υποστεί σφάλμα καθώς περάσανε από το ενθόρυβο κανάλι
    private int crcincorrect;//ο αριθμός των μηνυμάτων που ανίχνευσε ως λανθασμενα ο  crc
    private int[][] messages;//o δύο διαστάσεων πίνακας των μηνυμάτων.Σε κάθε γραμμή υπάρχει και ένα μήνυμα.
    private Random rd = new Random();//η γεννήτρια ψευδοτυχαίων αριθμών που χρησιμοποιείται όπου είναι απαραίτητο

    /**
     * Ο κατασκευαστής της κλάσης Message δημιουργεί n μηνύματα των k bits έκαστο.
     * @param n ο αριθμός των μηνυμάτων
     * @param k το μέγεθος του κάθε μηνύματος (αριθμός ψηφίων)
     * @param ber ο αριθμός-εκθέτης για τον υπολογισμό του BER
     * @param pn ο αριθμός p -εδώ μετατρέπεται σε πίνακα int[]
     */
    public Message(int n,int k,int ber,long pn){
        this.plithos=n;
        this.k=k;
        this.ber=ber;
        incorr=0;
        crcincorrect=0;
        messages=new int[n][k];
//        messages[0]=new int[] {1,0,1,0,0,0,1,1,0,1}; αυτό υπάρχει εδώ γιατί χρησιμοποιείθηκε μαζί με Ρ=110101 από το παράδειγμα του βιβλίου για testing του αλγόριθμου

        for(int i=0;i<n;i++){
            for(int j=0;j<k;j++){
                if(rd.nextBoolean()==true){
                    messages[i][j]=1;
                }
                else{
                    messages[i][j]=0;
                }
            }
        }
        p=getDigits(pn);
    }

    /**
     *
     * Υλοποιεί τον πολλαπλασιασμό 2^n*Μ , δηλ. τοποθετεί n μηδενικά στα δεξιά του κάθε message χρησιμοποιώντσας μια χειροκίνητη πράξη τύπου realloc.
     */
    public void addZerosToMess(){
        int n=p.length;
        int length=k+n-1;
        for(int w=0;w<plithos;w++){
            int [] temp=new int[length];
            for(int i=0;i<k;i++){
                temp[i]=messages[w][i];
            }
            for(int j=messages[0].length;j<length;j++){
                temp[j]=0;
            }
            messages[w]=temp;
        }

    }

    /**
     * Χρησιμοποιεί επαναληπτικά για κάθε μήνυμα την μέθοδο crc για να δημιουργήσει το τελικό μήνυμα που θα μεταδοθεί(μαζί με το fcs).
     * Το αποτέλεσμα εκτέλεσης αυτής της μεθόδου είναι ότι ο πίανκας messages[][] πλέον έχει τα μηνύματα μαζί με τις ακολουθίες ελέγχου , άρα τις ακολουθίες bit που θα μεταδοθούν
     */
    public void applyCRCforMessages(){
        for(int i=0;i<plithos;i++){
                int[]t = Arrays.copyOf(messages[i],messages[i].length);
                crc(t);//στέλνω ένα αντίγραφο του πίνακα για να το αλλάξω κατά τη διαίρεση xor και από αυτό θα πάρω τα τελευταία n-k ψηφία
                for(int j=0;j<p.length-1;j++){
                    messages[i][k+j]=t[k+j];//για να παρω το fcs και να το βαλω στο μήνυμα
                }
        }
    }

    /**
     * Αλλοιώνει πιθανοτικά με πιθανότητα 10^ber τα μηνύματα και υπολογίζει πόσα μηνύματα  αλλοιώθηκαν.
     */
    public void errorGenerator(){
        int bound=(int) Math.pow(10,Math.abs(ber));//υπολογίζεται το όριο για τον τυχαίο αριθμό που ουσιαστικά υλοποιεί την αλλοίωση με το ber
        //χρησιμοποιείται η abs γιατί ο χρήστης το πιο πιθανό είναι να δώσει αρνητικό αριθμό αφού για το  ber η βάση θα είναι το 10 και ο εκθέτης ένας αρνητικός αριθμός
        bound--;//επειδή η random επιστρέφει τυχαίο από το 0
        for(int i=0;i<plithos;i++){
            int[] copy=Arrays.copyOf(messages[i],messages[i].length);
            for(int j=0;j<messages[i].length;j++){
                if(rd.nextInt(bound)==1){
                    //θα συμβεί σφάλμα
                    if(messages[i][j]==0) messages[i][j]=1;
                    if(messages[i][j]==1) messages[i][j]=0;
                }
            }
            if(!(Arrays.equals(copy,messages[i]))) incorr++;//αφού  έγινε κάποια αλλοίωση αυξάνουμε τον μετρητή των εσφαλμένων μηνυμάτων
        }

    }

    /**
     * Μία απλή μέθοδος για testing.
     * Εμφανίζει τον πίνακα των μηνυμάτων.
     */
    public void showMessages(){
        for(int i=0;i<plithos;i++){
            System.out.println(" A message: ");
            for(int j=0;j<messages[0].length;j++){
                System.out.print(" "+messages[i][j]);
            }
            System.out.println(" ");
        }
    }

    /**
     * Απλή μέθοδος εμφάνισης του πίνακα με τα ψηφία του αριθμού P για testing.
     */
    public void showP(){
        System.out.println("Given P has the follow digits :");
        for(int i=0;i<p.length;i++){
            System.out.print(" "+p[i]);
        }
        System.out.println(" ");
    }
    /**
     * Δέχεται έναν αριθμό κι επιστρέφει τον αριθμό των ψηφίων του.
     * @param x ο αριθμός το πλήθος του οποίου τα ψηφία υπολογίζει
     * @return ο αριθμός των ψηφίων του
     */
    private int calcNumOfDigits(long x){
        int n=0;
        while(x!=0){
            n++;
            x=x/10;
        }
        return n;
    }

    /**
     * Υπολογισμός των ψηφίων ενός ακέραιου αριθμού.
     * @param x ο αριθμός του οποίου τα ψηφία τα θέλουμε σε πίνακα
     * @return ο πίνακας ακεράιων που σε κάθε θέση του έχει και ένα ψηφίο του αριθμού: [0...n-1] όπου n  το πλήθος των ψηφίων του αριθμού
     */
    private long[] getDigits(long x){
        int n=calcNumOfDigits(x);
        long[] d=new long[n];
        int i=0;
        n--;
        while(x>0){
            d[n-i]=x%10;
            x=x/10;
            i++;
        }
        return d;
    }

    /**
     * Υλοποεί τον αλγόριθμο CRC.Μετά την εκτέλεση αυτής της μεθόδου , ο πίνακας περιέχει το αποτέλεσμα
     * της διαίρεσης  με τον αριθμό Ρ (των n bits).
     * Από αυτόν , εμείς θα πάρουμε τα τελευταία n-1 bits και θα τα βάλουμε ως ακολουθία FCS στο μήνυμα.
     * @param t Το μήνυμα για το οποίο υπολογίζει την ακολουθία FCS
     *
     */
    public void crc(int[] t){

        for(int i=0;i<k;i++){
            if (t[i]==0)
                continue;
            xor(t,p,i);
        }
    }

    /**
     * Υλοποιεί την modulo-2 αριθμητική η οποία ουσιαστικά είναι η λογική πράξη XOR.
     * @param t ο πίνακας του μηνύματος
     * @param divisor ο αριθμός p
     * @param pos η θέση από την οποία πρέπει να ξεκινήσει να κάνει XOR-συγκρίσεις.
     */
    private void xor(int[] t, long[] divisor,int pos){
        for(int i=0; i<divisor.length; i++){
            t[i+pos] = t[i+pos]==divisor[i]?0:1;
        }
    }

    /**
     * Χρησιμοποείται για να υπολογίσει τον αριθμό των μηνυνμάτων τα οποία ο αλγόριθμος CRC αναγνώρισε ως εσφαλμένα .
     * Γι'αυτό το σκοπό , επαναληπτικά για κάθε μήνυμα , εφαρμόζει τον crc.
     * Γι'αυτά κανονικά το σύστημα παραλήπτη θα ζητήσει επαναποστολή.
     * Η  μέθοδος αυτή καλείται μετά την κλήση της μεθόδου errorGenerator όπου αναπαρίσταται η μετάδοση μέσα από το ενθόρυβο κανάλι.
     */
    public void crcCheck(){
        for(int i=0;i<plithos;i++){
            int[] t=Arrays.copyOf(messages[i],messages[i].length);
            crc(t);
            //ο πίνακας t πλέον έχει το αποτέλεσμα της διαίρεσης .Αν δεν έχει υπόλοιπο η διαίρεση-άρα όλα τα bit είναι 0- σημαίνει ότι δεν ανιχνεύθηκε αλλοιωμένο μήνυμα.
            for(int w=0;w<(t.length);w++){
                if(t[w]!=0){
                    //αν ένα bit δεν είναι 0 σημαίνει ότι η ακολουθία που λάβαμε είναι ένα αλλοιωμένο μήνυμα.
                    crcincorrect++;//αυξάνεται ο μετρητής των αλλοιωμένων.
                    break;
                }
            }
        }
    }

    /**
     * Ο getter για το πλήθος των μηνυμάτων.
     * @return την τιμή του πεδίου plithos της κλάσης αυτής.
     */
    public int getPlithos() {
        return plithos;
    }
    /**
     * Ο getter για το πλήθος των εσφαλμένων μηνυμάτων.
     * @return την τιμή του πεδίου incorr της κλάσης αυτής.
     */
    public int getIncorr() {
        return incorr;
    }
    /**
     * Ο getter για το πλήθος των μηνυμάτων που αναγνώρισε ως αλλοιβμένα ο αλγόριθμος.
     * @return την τιμή του πεδίου crcincorrect της κλάσης αυτής.
     */
    public int getCrcincorrect() {
        return crcincorrect;
    }
}
