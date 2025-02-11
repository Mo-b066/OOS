package bank;

// Die klasse Payment soll Ein- und Auszahlungen repräsentieren
public class Payment {

    // Hier werden die Klassenatribute angelegt
    private String date="";              // Zeitpunkt einer Ein- oder Auszahlung
    private double amount=0;             // Geldmenge einer Ein- oder Auszahlung
    private String description="";       // Beschreibung des Vorgangs
    private double incomingInterest =0;  // Zinsen bei Einzahlung
    private double outgoingInterest =0;  // Zinsen bei Auszahlung

    // Dieser Konstruktor initialisiert die Attribute date, amount und description
   public Payment(String date, double amount, String description) {
      this.date = date;
      this.description=description;
      this.amount=amount;

    }
    //Dieser Konstruktor verwendet den ersten Konstruktor, um date, amount und description zu initialisieren, und fügt zusätzliche Attribute hinzu, nämlich incomingInterest und outgoingInterest
    public Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest){

        this(date,amount,description);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
   }
    //Copy-Konstruktor erstellt eine Kopie eines bestehenden Payment-Objekts
    public Payment(Payment payment) {
        this.date = payment.date;
        this.description = payment.description;
        this.amount = payment.amount;
        this.incomingInterest = payment.incomingInterest;
        this.outgoingInterest = payment.outgoingInterest;
    }

    // Getter und Setter für Attribute

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public double getIncomingInterest() {return incomingInterest;}
    public void setIncomingInterest(double incomingInterest) {
        if (incomingInterest >= 0 && incomingInterest <= 1) {
            this.incomingInterest = incomingInterest;
        } else {
            System.out.println("Eine fehlerhafte Eingabe: Prozent Wert muss Zwischen 0 und 1 sein (Incomingintrest)");
            this.incomingInterest= 0.0;
        }
    }

    public double getOutgoingInterest() {return outgoingInterest;}
    public void setOutgoingInterest(double outgoingInterest) {
        if(outgoingInterest >= 0 && outgoingInterest <= 1)
        {
            this.outgoingInterest = outgoingInterest;
        }
        else{
            System.out.println("Eine fehlerhafte Eingabe: Prozent Wert muss Zwischen 0 und 1 sein (Outgoingintrest)");
            this.outgoingInterest= 0.0;}
    }

    // Gibt den Inhalt aller Klassenattribute auf der Konsole aus
    public void printObject(){

        System.out.println("Description: " + description);
        System.out.println("Date: "+date);
        System.out.println("Amount: " + amount);
        if(incomingInterest!=0){
            System.out.println("Incoming Interest: " + incomingInterest);
        }
        if(outgoingInterest!=0){
            System.out.println("Outgoing Interest: " + outgoingInterest);
        }
   }

    }



// getter setter für alle atributte
