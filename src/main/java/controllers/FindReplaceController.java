package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FindReplaceController implements Initializable {

    @FXML
    public Button findPrevButton;

    @FXML
    public Button findNextButton;

    @FXML
    public Button cancelButton;

    @FXML
    public Button replaceButton;

    @FXML
    public Button replaceAllButton;

    @FXML
    public TextField findField;

    @FXML
    public TextField replaceField;

    @FXML
    public CheckBox isMatchCase;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        findField.textProperty().addListener((observable, oldValue, newValue) -> {
            findNextButton.setDisable(newValue.equals(""));
            findPrevButton.setDisable(newValue.equals(""));
            replaceAllButton.setDisable(newValue.equals(""));
            replaceButton.setDisable(newValue.equals(""));
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

    public Button getReplaceButton() {
        return replaceButton;
    }

    public Button getReplaceAllButton() {
        return replaceAllButton;
    }

    public TextField getFindField() {
        return findField;
    }

    public TextField getReplaceField() {
        return replaceField;
    }

    public CheckBox getIsMatchCase() {
        return isMatchCase;
    }
}
