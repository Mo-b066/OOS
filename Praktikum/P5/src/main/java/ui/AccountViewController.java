package ui;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import bank.IncomingTransfer;
import bank.OutgoingTransfer;
import bank.Payment;
import bank.PrivateBank;
import bank.Transaction;
import bank.Transfer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Controller-Klasse für die Kontoansicht
 * Verwaltet die Anzeige und Interaktion mit Kontotransaktionen
 */
public class AccountViewController {
    // FXML-Annotierte Felder für UI-Komponenten
    // Label für den Kontonamen
    @FXML private Label accountNameLabel;
    // Label für den Kontostand
    @FXML private Label balanceLabel;
    // ListView zur Anzeige der Transaktionen
    @FXML private ListView<Transaction> transactionListView;
    
    // Instanzvariablen
    private PrivateBank bank;           // Referenz zur Bank
    private String accountName;         // Name des aktuellen Kontos
    private ObservableList<Transaction> transactions;  // Liste der Transaktionen

    /**
     * Initialisierungsmethode, wird automatisch beim Laden der FXML aufgerufen
     */
    @FXML
    private void initialize() {
        // Setzt eine benutzerdefinierte Zellenfabrik für die Transaktionsliste
        transactionListView.setCellFactory(listView -> new ListCell<Transaction>() {
            @Override
            protected void updateItem(Transaction transaction, boolean empty) {
                super.updateItem(transaction, empty);
                
                if (empty || transaction == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Erstellt das Layout für jede Transaktion in der Liste
                    GridPane grid = new GridPane();
                    grid.setHgap(15);
                    grid.setVgap(8);
                    grid.setPadding(new Insets(5, 0, 5, 0));

                    // Add transaction details with better formatting
                    Label dateLabel = new Label(transaction.getDate());
                    dateLabel.getStyleClass().add("transaction-date");
                    
                    Label typeLabel = new Label(transaction instanceof Payment ? "Payment" : "Transfer");
                    typeLabel.getStyleClass().add("transaction-type");
                    
                    Label amountLabel = new Label(String.format("%.2f€", transaction.calculate()));
                    amountLabel.getStyleClass().add(transaction.calculate() >= 0 ? "amount-positive" : "amount-negative");
                    
                    Label descLabel = new Label(transaction.getDescription());
                    descLabel.getStyleClass().add("transaction-description");
                    
                    grid.add(dateLabel, 0, 0);
                    grid.add(typeLabel, 1, 0);
                    grid.add(amountLabel, 2, 0);
                    grid.add(descLabel, 0, 1, 3, 1);

                    setGraphic(grid);
                    setText(null);
                }
            }
        });
    }

    /**
     * Richtet das Konto mit den übergebenen Parametern ein
     * @param bank Die Bankinstanz
     * @param accountName Der Kontoname
     */
    public void setupAccount(PrivateBank bank, String accountName) {
        this.bank = bank;
        this.accountName = accountName;
        accountNameLabel.setText("Account: " + accountName);
        
        // Initialisiert das Konto und lädt die Transaktionen
        refreshTransactions();
    }

    /**
     * Aktualisiert die Transaktionsliste und den Kontostand
     */
    private void refreshTransactions() {
        try {
            // Clear existing items
            if (transactions == null) {
                transactions = FXCollections.observableArrayList();
                transactionListView.setItems(transactions);
            }
            
            transactions.clear();
            transactions.addAll(bank.getTransactions(accountName));
            updateBalance();
            
            // Debug output
            System.out.println("Loaded " + transactions.size() + " transactions for " + accountName);
        } catch (Exception e) {
            showError("Error", "Failed to load transactions: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Aktualisiert den angezeigten Kontostand
     */
    private void updateBalance() {
        try {
            double balance = bank.getAccountBalance(accountName);
            balanceLabel.setText(String.format("%.2f€", balance));
        } catch (Exception e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Handler für den Zurück-Button
     */
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainpage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) accountNameLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            showError("Error", "Could not switch to main view: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Öffnet Dialog zur Erstellung einer neuen Zahlung
     */
    @FXML
    private void handleNewPayment() {
        Dialog<Payment> dialog = new Dialog<>();
        dialog.setTitle("New Payment");
        dialog.setHeaderText("Create new payment");

        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);
        // Create a grid layout for the dialog
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        DatePicker datePicker = new DatePicker();
        TextField descriptionField = new TextField();
        TextField amountField = new TextField();
        CheckBox isOutgoingCheckbox = new CheckBox("Outgoing Payment");

        grid.add(new Label("Date:"), 0, 0);
        grid.add(datePicker, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("Amount:"), 0, 2);
        grid.add(amountField, 1, 2);
        grid.add(isOutgoingCheckbox, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                try {
                    String date = datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String description = descriptionField.getText();
                    double amount = Double.parseDouble(amountField.getText());
                    double incomingInterest = isOutgoingCheckbox.isSelected() ? 0.1 : 0.05;
                    double outgoingInterest = isOutgoingCheckbox.isSelected() ? 0.05 : 0.1;
                    
                    return new Payment(date, description, amount, incomingInterest, outgoingInterest);
                } catch (Exception e) {
                    showError("Invalid Input", "Please check your input values");
                    return null;
                }
            }
            return null;
        });

        Optional<Payment> result = dialog.showAndWait();
        result.ifPresent(payment -> {
            try {
                bank.addTransaction(accountName, payment);
                refreshTransactions();
            } catch (Exception e) {
                showError("Error", e.getMessage());
            }
        });
    }

    /**
     * Öffnet Dialog zur Erstellung einer neuen Überweisung
     */
    @FXML
    private void handleNewTransfer() {
        Dialog<Transfer> dialog = new Dialog<>();
        dialog.setTitle("New Transfer");
        dialog.setHeaderText("Create new transfer");

        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);
        // Create a grid layout for the dialog
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        DatePicker datePicker = new DatePicker();
        TextField descriptionField = new TextField();
        TextField amountField = new TextField();
        TextField senderField = new TextField();
        TextField recipientField = new TextField();

        // Pre-fill the current account name in sender field
        senderField.setText(accountName);
        
        grid.add(new Label("Date:"), 0, 0);
        grid.add(datePicker, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("Amount:"), 0, 2);
        grid.add(amountField, 1, 2);
        grid.add(new Label("Sender:"), 0, 3);
        grid.add(senderField, 1, 3);
        grid.add(new Label("Recipient:"), 0, 4);
        grid.add(recipientField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                try {
                    String date = datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String description = descriptionField.getText();
                    double amount = Double.parseDouble(amountField.getText());
                    String sender = senderField.getText();
                    String recipient = recipientField.getText();

                    // Determine if this is an incoming or outgoing transfer based on the current account
                    if (accountName.equals(sender)) {
                        return new OutgoingTransfer(date, description, amount, sender, recipient);
                    } else {
                        return new IncomingTransfer(date, description, amount, sender, recipient);
                    }
                } catch (Exception e) {
                    showError("Invalid Input", "Please check your input values");
                    return null;
                }
            }
            return null;
        });

        Optional<Transfer> result = dialog.showAndWait();
        result.ifPresent(transfer -> {
            try {
                bank.addTransaction(accountName, transfer);
                refreshTransactions();
            } catch (Exception e) {
                showError("Error", e.getMessage());
            }
        });
    }

    /**
     * Handler zum Löschen einer ausgewählten Transaktion
     */
    @FXML
    private void handleDeleteTransaction() {
        Transaction selectedTransaction = transactionListView.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Transaction");
            alert.setHeaderText("Delete Transaction");
            alert.setContentText("Are you sure you want to delete this transaction?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    bank.removeTransaction(accountName, selectedTransaction);
                    refreshTransactions();
                } catch (Exception e) {
                    showError("Error", e.getMessage());
                }
            }
        }
    }

    /**
     * Handler zum Sortieren der Transaktionen in aufsteigender Reihenfolge
     */
    @FXML
    private void handleSortAscending() {
        try {
            transactions.setAll(bank.getTransactionsSorted(accountName, true));
        } catch (Exception e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Handler zum Sortieren der Transaktionen in absteigender Reihenfolge
     */
    @FXML
    private void handleSortDescending() {
        try {
            transactions.setAll(bank.getTransactionsSorted(accountName, false));
        } catch (Exception e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Handler zum Anzeigen der positiven Transaktionen
     */
    @FXML
    private void handleShowPositive() {
        try {
            transactions.setAll(bank.getTransactionsByType(accountName, true));
        } catch (Exception e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Handler zum Anzeigen der negativen Transaktionen
     */
    @FXML
    private void handleShowNegative() {
        try {
            transactions.setAll(bank.getTransactionsByType(accountName, false));
        } catch (Exception e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Handler zum Sortieren der Transaktionen nach Betrag in aufsteigender Reihenfolge
     */
    @FXML
    private void handleSortByAmountAsc() {
        try {
            transactions.sort((t1, t2) -> Double.compare(t1.calculate(), t2.calculate()));
        } catch (Exception e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Handler zum Sortieren der Transaktionen nach Betrag in absteigender Reihenfolge
     */
    @FXML
    private void handleSortByAmountDesc() {
        try {
            transactions.sort((t1, t2) -> Double.compare(t2.calculate(), t1.calculate()));
        } catch (Exception e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Handler zum Sortieren der Transaktionen nach Datum in aufsteigender Reihenfolge
     */
    @FXML
    private void handleSortByDateAsc() {
        try {
            transactions.sort((t1, t2) -> t1.getDate().compareTo(t2.getDate()));
        } catch (Exception e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Handler zum Sortieren der Transaktionen nach Datum in absteigender Reihenfolge
     */
    @FXML
    private void handleSortByDateDesc() {
        try {
            transactions.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));
        } catch (Exception e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Handler zum Anzeigen aller Transaktionen
     */
    @FXML
    private void handleShowAll() {
        try {
            refreshTransactions();
        } catch (Exception e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Handler zum Anzeigen aller Zahlungen
     */
    @FXML
    private void handleShowPayments() {
        try {
            List<Transaction> filtered = transactions.filtered(t -> t instanceof Payment);
            transactions.setAll(filtered);
        } catch (Exception e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Handler zum Anzeigen aller Überweisungen
     */
    @FXML
    private void handleShowTransfers() {
        try {
            List<Transaction> filtered = transactions.filtered(t -> t instanceof Transfer);
            transactions.setAll(filtered);
        } catch (Exception e) {
            showError("Error", e.getMessage());
        }
    }

    /**
     * Zeigt eine Fehlermeldung an
     * @param title Titel des Fehlerdialogs
     * @param message Fehlermeldung
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}