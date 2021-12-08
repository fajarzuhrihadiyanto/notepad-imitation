package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FindController implements Initializable {

    @FXML
    public Button findPrevButton;

    @FXML
    public Button findNextButton;

    @FXML
    public Button cancelButton;

    @FXML
    public TextField findField;

    @FXML
    public CheckBox isMatchCase;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        findField.textProperty().addListener((observable, oldValue, newValue) -> {
            findNextButton.setDisable(newValue.equals(""));
            findPrevButton.setDisable(newValue.equals(""));
        });
    }

    public Button getFindPrevButton() {
        return findPrevButton;
    }

    public Button getFindNextButton() {
        return findNextButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public TextField getFindField() {
        return findField;
    }

    public CheckBox getIsMatchCase() {
        return isMatchCase;
    }
}
