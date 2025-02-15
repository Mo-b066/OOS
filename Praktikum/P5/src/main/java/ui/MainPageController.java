package ui;

// Importieren der benötigten Klassen
import java.io.File;
import java.util.Optional;

import bank.Payment;
import bank.PrivateBank;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

/**
 * Controller-Klasse für die Hauptseite der Banking-Anwendung
 */
public class MainPageController {
    // ListView für die Anzeige der Konten
    @FXML
    private ListView<String> accountListView;
    
    // Referenz zur Bank-Instanz
    private PrivateBank bank;
    // Observable Liste für die Konten
    private ObservableList<String> accounts;

    /**
     * Initialisierungsmethode, wird automatisch aufgerufen
     * nachdem die FXML-Datei geladen wurde
     * und alle FXML-Elemente initialisiert wurden
     *
     */
    @FXML
    private void initialize() {
        try {
            // Erstellt das Verzeichnis für Bankkonten, falls es nicht existiert
            String directory = System.getProperty("user.dir") + "/bank_accounts";
            new File(directory).mkdirs();
            
            // Initialisiert die Bank mit Standardzinssätzen
            bank = new PrivateBank("MyBank", 0.05, 0.1, directory);
            
            // Erstellt Beispielkonten, falls keine Konten existieren
            if (bank.getAccountsToTransactions().isEmpty()) {
                createSampleAccounts();
            }
            
            // Aktualisiert die Kontoliste
            refreshAccounts();

            // Fügt Doppelklick-Handler hinzu
            accountListView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    handleSelectAccount();
                }
            });
        } catch (Exception e) {
            showError("Error", "Konnte Bank nicht initialisieren: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Erstellt Beispielkonten mit Initialeinzahlungen
     */
    private void createSampleAccounts() {
        try {
            // Erstellt zwei Beispielkonten
            bank.createAccount("John Doe");
            bank.createAccount("Jane Smith");
            
            // Fügt Initialeinzahlungen hinzu
            bank.addTransaction("John Doe", 
                new Payment("2024-01-01", "Initial Deposit", 1000.0, 0.05, 0.1));
            bank.addTransaction("Jane Smith", 
                new Payment("2024-01-01", "Initial Deposit", 2000.0, 0.05, 0.1));
        } catch (Exception e) {
            System.err.println("Fehler beim Erstellen der Beispielkonten: " + e.getMessage());
        }
    }

    /**
     * Aktualisiert die Kontoliste in der UI
     */
    private void refreshAccounts() {
        try {
            // Lädt alle Konten in die Observable Liste
            accounts = FXCollections.observableArrayList(bank.getAccountsToTransactions().keySet()); //automatische UI-Aktualisierungen.

            accountListView.setItems(accounts);
        } catch (Exception e) {
            showError("Error", "Konnte Konten nicht aktualisieren: " + e.getMessage());
        }
    }

    /**
     * Handler für das Erstellen eines neuen Kontos
     */
    @FXML
    private void handleNewAccount() {
        // Erstellt einen Dialog für die Kontoeingabe
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Neues Konto");
        dialog.setHeaderText("Neues Konto erstellen");
        dialog.setContentText("Bitte Kontoname eingeben:");

        // Verarbeitet die Benutzereingabe
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(accountName -> {
            try {
                bank.createAccount(accountName);
                refreshAccounts();
            } catch (Exception e) {
                showError("Fehler beim Erstellen des Kontos", e.getMessage());
            }
        });
    }

    /**
     * Handler für die Kontoauswahl
     * Öffnet die Kontoansicht
     */
    @FXML
    private void handleSelectAccount() {
        // Lädt die Kontodetailansicht
        String selectedAccount = accountListView.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/accountview.fxml"));
                Parent root = loader.load();
                
                AccountViewController controller = loader.getController();
                controller.setupAccount(bank, selectedAccount);
                
                Scene scene = accountListView.getScene();
                scene.setRoot(root);
            } catch (Exception e) {
                showError("Error", "Konnte Kontoansicht nicht öffnen: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Handler für das Löschen eines Kontos
     */
    @FXML
    private void handleDeleteAccount() {
        String selectedAccount = accountListView.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            // Bestätigungsdialog anzeigen
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konto löschen");
            alert.setHeaderText("Konto löschen bestätigen");
            alert.setContentText("Sind Sie sicher, dass Sie dieses Konto löschen möchten: " + selectedAccount + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    bank.deleteAccount(selectedAccount);
                    refreshAccounts();
                } catch (Exception e) {
                    showError("Fehler beim Löschen des Kontos", e.getMessage());
                }
            }
        }
    }

    /**
     * Hilfsmethode zum Anzeigen von Fehlermeldungen
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}