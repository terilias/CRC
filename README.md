**CRC algorithm, Java Implementation**

Σε αυτό το repository γίνεται υλοποίηση του αλγορίθμου ανίχνευσης σφαλμάτων CRC (Cyclic
Redundancy Check- Κυκλικός Έλεγχος Πλεονασμού) ο οποίος επινοήθηκε και δημοσιεύθηκε από
τον Γουέσλεϊ Πέτερσον (W. Wesley Peterson) το 1961. Είναι ένας από τους πιο συνηθισμένους ,
και πιο ισχυρούς κώδικες ανίχνευσης σφαλμάτων και περιγράφεται ως ακολούθως:
Δοθέντος ενός μπλοκ δεδομένων των k bits , ο μεταδότης δημιουργεί μια ακολουθία των n<k bits
(Frame Check Sequence, FCS) ,τέτοια ώστε η συνολική ακολουθία των k+n bits που προκύπτει να
διαιρείται ακριβώς με κάποιον προκαθορισμένο αριθμό P των n+1 bits. Όταν η ακολουθία των k +n
bits φθάσει στον αποδέκτη, τότε η ορθότητά της ελέγχεται διαιρώντας την με τον προκαθορισμένο
αριθμό P. Αν από τη διαίρεση αυτή δεν προκύψει υπόλοιπο, τότε το πακέτο γίνεται αποδεκτό. Αν
προκύψει υπόλοιπο, τότε συνάγεται ότι το πακέτο έχει αλλοιωθεί και ζητείται η επαναμετάδοσή
του.		
                                                                                        
Για την περιγραφή του κώδικα καθώς και για πραδείγματα εκτέλεσης παρακαλώ ανατρέξτε στο αρχείο "Περιγραφή.pdf".
