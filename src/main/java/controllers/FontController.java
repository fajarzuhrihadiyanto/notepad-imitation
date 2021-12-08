package main.java.controllers;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.java.models.Model;
import main.java.models.module.Theme;

import java.net.URL;
import java.util.ResourceBundle;

public class FontController implements Initializable {

    @FXML
    public ListView<String> fontFamilyChooser;

    @FXML
    public ListView<String> fontStyleChooser;

    @FXML
    public ListView<Integer> fontSizeChooser;

    @FXML
    public RadioButton lightModeBtn;

    @FXML
    public RadioButton darkModeBtn;

    @FXML
    public AnchorPane area;

    @FXML
    public Label sampleText;

    @FXML
    public Button okBtn;

    @FXML
    public Button cancelBtn;

    private ToggleGroup theme;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCombobox();
        initRadioButton();
        initValue();
    }

    private void initCombobox() {
        fontFamilyChooser.getItems().addAll(Font.getFamilies());
        fontFamilyChooser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            fontStyleChooser.setItems(new ObservableListWrapper<>(Font.getFontNames(newValue)));
            fontStyleChooser.getSelectionModel().select(0);
        });

        fontSizeChooser.getItems().addAll(8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72);
        fontSizeChooser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            sampleText.setFont(new Font(fontStyleChooser.getSelectionModel().getSelectedItem(), newValue));
        });

        fontStyleChooser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            sampleText.setFont(new Font(newValue, fontSizeChooser.getSelectionModel().getSelectedItem().doubleValue()));
        });
    }

    private void initValue() {
        fontFamilyChooser.getSelectionModel().select(Font.getDefault().getFamily());
        fontSizeChooser.getSelectionModel().select(new Integer(14));
    }

    private void initRadioButton() {
        theme = new ToggleGroup();
        theme.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            Theme theme = (Theme) newValue.getUserData();
            sampleText.getStyleClass().setAll(theme.getClassName());
        });

        lightModeBtn.setToggleGroup(theme);
        lightModeBtn.setUserData(Theme.LIGHT);
        lightModeBtn.setSelected(true);
        darkModeBtn.setToggleGroup(theme);
        darkModeBtn.setUserData(Theme.DARK);
    }

    public ListView<String> getFontFamilyChooser() {
        return fontFamilyChooser;
    }

    public ListView<String> getFontStyleChooser() {
        return fontStyleChooser;
    }

    public ListView<Integer> getFontSizeChooser() {
        return fontSizeChooser;
    }

    public RadioButton getLightModeBtn() {
        return lightModeBtn;
    }

    public RadioButton getDarkModeBtn() {
        return darkModeBtn;
    }

    public Label getSampleText() {
        return sampleText;
    }

    public Button getOkBtn() {
        return okBtn;
    }

    public Button getCancelBtn() {
        return cancelBtn;
    }

    public ToggleGroup getTheme() {
        return theme;
    }
}
