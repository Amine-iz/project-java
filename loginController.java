package com.example.project_java;

import com.example.project_java.BD.Login;
import com.example.project_java.BD.LoginDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class loginController {
    @FXML
    private Button btn_login;

    @FXML
    private Label lbl_wronglogin;

    @FXML
    private PasswordField txt_password;

    @FXML
    private TextField txt_username;

    @FXML
    public void userLogin(ActionEvent event) throws IOException, SQLException {
        checkLogin();
    }
    HelloApplication m = new HelloApplication();
    private void checkLogin() throws IOException, SQLException {

        if(txt_username.getText().isEmpty() || txt_password.getText().isEmpty()) {
            lbl_wronglogin.setText("Please enter your data.");
            return;
        }
        LoginDAO loginDAO = new LoginDAO();
        Login login = loginDAO.getLoginByUsernameAndPassword(txt_username.getText(), txt_password.getText());

        if(login != null) {
            lbl_wronglogin.setText("Success!");
            m.changeScene("dashboard.fxml");
        } else {
            lbl_wronglogin.setText("Wrong username or password!");
        }
    }
}
