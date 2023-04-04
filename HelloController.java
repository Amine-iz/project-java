package com.example.project_java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.project_java.BD.Livreur;
import com.example.project_java.BD.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
public class HelloController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextField nom;


    @FXML
    private TextField tele;


    @FXML
    private TableView<Livreur> mytable;


    @FXML
    private TableColumn<Livreur, Long> col_id;

    @FXML
    private TableColumn<Livreur, String> col_nom;

    @FXML
    private TableColumn<Livreur, String> col_tele;

    @FXML
    private Button No;

    @FXML
    void onDeleteButtonClick() {
        try {
            LivreurDAO livreurDAO = new LivreurDAO();
            long idValue = Long.parseLong(id.getText());
            Livreur liv = new Livreur(idValue, nom.getText(), tele.getText());


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this record?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                livreurDAO.delete(liv);
                UpdateTable();
                clearFields();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void onUpdateButtonClick() {
        try {
            LivreurDAO livreurDAO = new LivreurDAO();
            long idValue = Long.parseLong(id.getText());
            Livreur liv = new Livreur(idValue, nom.getText(), tele.getText());


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to update this record?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                livreurDAO.update(liv);
                UpdateTable();
                clearFields();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void onSaveButtonClick() {

        // accees a la bdd

        try {
            LivreurDAO livreurDAO = new LivreurDAO();

            Livreur liv = new Livreur(0l, nom.getText(), tele.getText());

            String teleValue = tele.getText();
            if (teleValue.isEmpty() && nom.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Input Format Error");
                alert.setContentText("Please enter a valid number.");

                alert.showAndWait();
                return;
            }

            if (!teleValue.matches("^\\+?[0-9]{3}-?[0-9]{6,12}$") || teleValue.length() != 10) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Telephone Number Format Error");
                alert.setContentText("Please enter a valid telephone number in the format 0624-123456.");

                alert.showAndWait();
                return;
            }

            livreurDAO.save(liv);

            UpdateTable();
            clearFields();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Input Format Error");
            alert.setContentText("Please enter a valid number.");

            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpdateTable();

    }

    public void UpdateTable() {
        col_id.setCellValueFactory(new PropertyValueFactory<Livreur, Long>("id_livreur"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Livreur, String>("nom"));

        col_tele.setCellValueFactory(new PropertyValueFactory<Livreur, String>("telephone"));


        mytable.setItems(this.getDataLivreurs());
    }

    public static ObservableList<Livreur> getDataLivreurs() {

        LivreurDAO livreurDAO = null;

        ObservableList<Livreur> listfx = FXCollections.observableArrayList();

        try {
            livreurDAO = new LivreurDAO();
            for (Livreur ettemp : livreurDAO.getAll())
                listfx.add(ettemp);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listfx;
    }

    int index = -1;

    @FXML
    public void getSelected(javafx.scene.input.MouseEvent mouseEvent) {
        index = mytable.getSelectionModel().getFocusedIndex();
        if (index <= -1) {
            return;
        }
        id.setText(col_id.getCellData(index).toString());
        nom.setText(col_nom.getCellData(index).toString());
        tele.setText(col_tele.getCellData(index).toString());

    }

    HelloApplication m = new HelloApplication();

    @FXML
    public void BackMenu() throws IOException {
        m.changeScene("dashboard.fxml");
    }

    @FXML
    private void clearFields() {
        id.setText("");
        nom.setText("");
        tele.setText("");

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
    public void BackLogin() throws IOException {
        m.changeScene("login.fxml");
    }

    @FXML TextField txt_nu;
    @FXML
    public void searchLivreurById() {
        try {
            String idText = txt_nu.getText().trim();
            LivreurDAO livreurDAO = new LivreurDAO();

            if (idText.isEmpty()) {
                UpdateTable();
            } else {
                Long id = Long.parseLong(idText);
                Livreur livreur = livreurDAO.getOne(id);

                if (livreur == null) {
                    UpdateTable();
                } else {
                    mytable.getItems().clear();
                    mytable.getItems().add(livreur);
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