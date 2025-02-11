package bank;

//soll im Kontext von Überweisungen verwendet werden
public class Transfer {

    // Hier werden die Klassenatribute angelegt
    private String date="";               // Zeitpunkt einer Ein- oder Auszahlung
    private String description="";        // Beschreibung des Vorgangs
    private double amount=0;              // Geldmenge einer Ein- oder Auszahlung
    private String sender ="";            // Akteur der die Geldmenge überwiesen hat
    private String recipient="";          // Akteur der die Geldmenge überwiesen bekommen hat

    // Dieser Konstruktor initialisiert die Attribute date, amount und description
   public Transfer(String date, double amount, String description) {
        this.date = date;
        this.description=description;
        this.amount=amount;

    }
    //Dieser Konstruktor verwendet den ersten Konstruktor, um date, amount und description zu initialisieren, und fügt zusätzliche Attribute hinzu, nämlich sender und recipient
    public Transfer(String date, double amount,String description,  String sender, String recipient) {
        this(date, amount, description);
        this.sender = sender;
        this.recipient = recipient;
    }
    //Copy-Konstruktor erstellt eine Kopie eines bestehenden Payment-Objekts
    public Transfer(Transfer transfer) {
        this.date = transfer.date;
        this.description = transfer.description;
        this.amount = transfer.amount;
        this.sender = transfer.sender;
        this.recipient = transfer.recipient;
    }

    // Getter und Setter für Attribute

    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public String getSender() {return sender;}
    public void setSender(String sender) {this.sender = sender;}

    public String getRecipient() {return recipient;}
    public void setRecipient(String recipient) {this.recipient = recipient;}

    // Gibt dne Inhalt aller Klassenattribute auf der Konsole aus
    public void printObject(){
        System.out.println("Description: " + description);
        System.out.println("Date: "+date);
        System.out.println("Amount: " + amount);
       if(sender.length() != 0){
           System.out.println("Sender: " + sender);
       }
       if(recipient.length() != 0){
           System.out.println("Recipient: " + recipient);
       }
    }


}
