package com.example.project_java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.project_java.BD.*;
import com.example.project_java.BD.ProduitDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
public class ProduitController implements Initializable {
    @FXML
    private Button No;

    @FXML
    private Button Up;

    @FXML
    private TableColumn<Produit, String> col_description;

    @FXML
    private TableColumn<Produit, Integer> col_id;

    @FXML
    private TableColumn<Produit, String> col_name;

    @FXML
    private TableColumn<Produit, Double> col_pv;

    @FXML
    private Button ok;

    @FXML
    private TableView<Produit> table;

    @FXML
    private TextArea txt_description;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_pv;
    ObservableList<Produit> listP;

    @FXML
    void onDeleteButtonClick() {
        try {
            ProduitDAO produitDAO = new ProduitDAO();
            int idValue = Integer.parseInt(txt_id.getText());
            Double pvValue = Double.parseDouble(txt_pv.getText());
            Produit produit = new Produit(idValue, txt_nom.getText(), pvValue,txt_description.getText());


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this record?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                produitDAO.delete(produit);
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
            ProduitDAO produitDAO = new ProduitDAO();
            Double pvValue = Double.parseDouble(txt_pv.getText());

            String nomValue = txt_nom.getText();
            if (nomValue.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Nom Error");
                alert.setContentText("Please enter a valid nom.");

                alert.showAndWait();
                return;
            }

            String descriptionValue = txt_description.getText();

            Produit produit = new Produit(0, nomValue, pvValue, descriptionValue);

            produitDAO.save(produit);

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
            ProduitDAO produitDAO = new ProduitDAO();
            int idValue = Integer.parseInt(txt_id.getText());
            Double pvValue = Double.parseDouble(txt_pv.getText());
            Produit produit = new Produit(idValue, txt_nom.getText(), pvValue,txt_description.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to update this record?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                produitDAO.update(produit);
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
        col_id.setCellValueFactory(new PropertyValueFactory<Produit,Integer>("id_produit"));
        col_name.setCellValueFactory(new PropertyValueFactory<Produit,String>("nom"));

        col_pv.setCellValueFactory(new PropertyValueFactory<Produit,Double>("pv"));
        col_description.setCellValueFactory(new PropertyValueFactory<Produit,String>("description"));



        table.setItems(this.getDataProduit());
    }

    public static ObservableList<Produit> getDataProduit(){

        ProduitDAO produitDAO = null;

        ObservableList<Produit> listfx = FXCollections.observableArrayList();

        try {
            produitDAO = new ProduitDAO();
            for (Produit ettemp : produitDAO.getAll())
                listfx.add(ettemp);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listfx ;
    }
    int index =-1;
    @FXML
    public void getSelected(javafx.scene.input.MouseEvent mouseEvent) {
        index = table.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        Produit selectedProduit = table.getSelectionModel().getSelectedItem();
        txt_id.setText(String.valueOf(selectedProduit.getId_produit()));
        txt_nom.setText(selectedProduit.getNom());
        txt_pv.setText(String.valueOf(selectedProduit.getPv()));
        txt_description.setText(selectedProduit.getDescription());

        Up.setDisable(false);
        No.setDisable(false);
    }
    @FXML
    private void clearFields() {
        txt_id.setText("");
        txt_nom.setText("");
        txt_pv.setText("");
        txt_description.setText("");

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
            ProduitDAO produitDAO = new ProduitDAO();

            if (idText.isEmpty()) {
                UpdateTable();
            } else {
                Long id = Long.parseLong(idText);
                Produit produit;
                produit = produitDAO.getOne(id);

                if (produit == null) {
                    UpdateTable();
                } else {
                    table.getItems().clear();
                    table.getItems().add(produit);
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

