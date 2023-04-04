package com.example.project_java;

import com.example.project_java.BD.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;


public class CommandeController implements Initializable {
    @FXML
    private Button No;
    @FXML
    private TableView<Commande> Table;
    @FXML
    private Button Up;
    @FXML
    private TableColumn<Commande, Double> col_total;
    @FXML
    private TableColumn<Commande, String> col_date;
    @FXML
    private TableColumn<Commande, Integer> col_id;
    @FXML
    private TableColumn<Commande, String> col_satus;
    @FXML
    private TableColumn<Commande, Integer> col_id_livreur;
    @FXML
    private Button ok;
    @FXML
    private TextField txt_date;
    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_id_livreur;
    @FXML
    private TextField txt_status;
    @FXML
    private ComboBox<String> box_status;
    @FXML
    private TextField txt_total;
    @FXML
    void onDeleteButtonClick() {
        try {
            CommandeDAO commandeDAO = new CommandeDAO();
            int idValue = Integer.parseInt(txt_id.getText());
            Double TotalVal = Double.parseDouble(txt_total.getText());
            int id_livreurValue = Integer.parseInt(txt_id_livreur.getText());
            Commande commande = new Commande(idValue, txt_date.getText(),txt_status.getText(),TotalVal,id_livreurValue);


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this record?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                commandeDAO.delete(commande);
                UpdateTable();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onSaveButtonClick() {
        try {
            CommandeDAO commandeDAO = new CommandeDAO();

            Double totalVal = Double.parseDouble(txt_total.getText());

            String idLivreurText = txt_id_livreur.getText().trim();
            if (!idLivreurText.matches("^[0-9]+$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("ID Livreur Format Error");
                alert.setContentText("Please enter a valid ID Livreur with numbers only.");
                alert.showAndWait();
                return;
            }
            int idLivreurValue = Integer.parseInt(idLivreurText);
            LivreurDAO livreurDAO = new LivreurDAO();
            if (!livreurDAO.exists(idLivreurValue)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("ID Livreur Error");
                alert.setContentText("ID Livreur does not exist");
                alert.showAndWait();
                return;
            }

            String dateValue = txt_date.getText();
            if (!dateValue.matches("^(\\d{2}[\\/\\-]){2}\\d{4}$") || dateValue.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Date Format Error");
                alert.setContentText("Please enter a valid date in the format dd/MM/yyyy or dd-MM-yyyy.");

                alert.showAndWait();
                return;
            }
            Commande commande = new Commande(0, dateValue, txt_status.getText(), totalVal, idLivreurValue);
            commandeDAO.save(commande);
            UpdateTable();
            clearFields();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Input Format Error");
            alert.setContentText("Please enter a valid input.");

            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onUpdateButtonClick() {
        try {
            CommandeDAO commandeDAO = new CommandeDAO();
            int idValue = Integer.parseInt(txt_id.getText());
            Double TotalVal = Double.parseDouble(txt_total.getText());
            int id_livreurValue = Integer.parseInt(txt_id_livreur.getText());
            Commande commande = new Commande(idValue, txt_date.getText(),txt_status.getText(),TotalVal,id_livreurValue);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to update this record?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                commandeDAO.update(commande);
                UpdateTable();
                clearFields();
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpdateTable();
    }
    public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<Commande,Integer>("id"));
        col_date.setCellValueFactory(new PropertyValueFactory<Commande,String>("date"));

        col_satus.setCellValueFactory(new PropertyValueFactory<Commande,String>("status"));
        col_total.setCellValueFactory(new PropertyValueFactory<Commande,Double>("total"));
        col_id_livreur.setCellValueFactory(new PropertyValueFactory<Commande,Integer>("id_livreur"));
        Table.setItems(this.getDataCommande());
    }

    public static ObservableList<Commande> getDataCommande(){

        CommandeDAO commandeDAO = null;
        ObservableList<Commande> listfx = FXCollections.observableArrayList();
        try {
            commandeDAO = new CommandeDAO();
            for (Commande ettemp : commandeDAO.getAll())
                listfx.add(ettemp);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listfx ;
    }
    int index =-1;

    @FXML
    public void getSelected(MouseEvent event) {
        index = Table.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        Commande selectedCommande = Table.getSelectionModel().getSelectedItem();
        txt_id.setText(String.valueOf(selectedCommande.getId()));
        txt_date.setText(String.valueOf(selectedCommande.getDate()));
        txt_id_livreur.setText(String.valueOf(selectedCommande.getId_livreur()));
        txt_status.setText(selectedCommande.getStatus());
        txt_total.setText(String.valueOf(selectedCommande.getTotal()));

    }
    @FXML
    private void clearFields() {
        txt_id.setText("");
        txt_date.setText("");
        txt_status.setText("");
        txt_total.setText("");
        txt_id_livreur.setText("");

    }
    HelloApplication m = new HelloApplication();
    @FXML
    public void BackMenu() throws IOException {
        m.changeScene("dashboard.fxml");
    }
    @FXML
    public void getGestionCommande() throws IOException {
        m.changeScene("commande.fxml");
    }

    @FXML
    public void getGestionLIvreur() throws IOException {
        m.changeScene("hello-view.fxml");

    }

    @FXML
    public void getGestionProduit() throws IOException {
        m.changeScene("produits.fxml");

    }
    @FXML
    public void BackLogin()  throws IOException {
        m.changeScene("login.fxml");
    }


    @FXML TextField txt_num;

    @FXML
    public void searchLivreurById() {
        try {
            String idText = txt_num.getText().trim();
            CommandeDAO commandeDAO = new CommandeDAO();

            if (idText.isEmpty()) {
                UpdateTable();
            } else {
                Long id = Long.parseLong(idText);
                Commande commande;
                commande = commandeDAO.getOne(id);

                if (commande == null) {
                    UpdateTable();
                } else {
                    Table.getItems().clear();
                    Table.getItems().add(commande);
                }
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("ID Livreur Format Error");
            alert.setContentText("Please enter a valid ID Livreur");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
