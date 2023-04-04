package com.example.project_java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class DashboardController {
    HelloApplication m = new HelloApplication();

    @FXML
    private Button btn_commande;

    @FXML
    private Button btn_liv;

    @FXML
    private Button btn_prod;

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
}
