package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import main.java.models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public BorderPane parent;

    @FXML
    public TextArea textArea;

    @FXML
    public MenuItem menuItemNew;

    @FXML
    public MenuItem menuItemNewWindow;

    @FXML
    public MenuItem menuItemOpen;

    @FXML
    public MenuItem menuItemSave;

    @FXML
    public MenuItem menuItemSaveAs;

    @FXML
    public MenuItem menuItemExit;

    @FXML
    public MenuItem menuItemUndo;

    @FXML
    public MenuItem menuItemRedo;

    @FXML
    public MenuItem menuItemCut;

    @FXML
    public MenuItem menuItemCopy;

    @FXML
    public MenuItem menuItemPaste;

    @FXML
    public MenuItem menuItemDelete;

    @FXML
    public MenuItem menuItemFind;

    @FXML
    public MenuItem menuItemFindReplace;

    @FXML
    public MenuItem menuItemSelectAll;

    @FXML
    public MenuItem menuItemTimeDate;

    @FXML
    public CheckMenuItem menuItemWordWrap;

    @FXML
    public MenuItem menuItemFont;

    @FXML
    public MenuItem menuItemZoomIn;

    @FXML
    public MenuItem menuItemZoomOut;

    @FXML
    public MenuItem menuItemRestoreZoom;

    @FXML
    public CheckMenuItem menuItemStatusBar;

    @FXML
    public MenuItem menuItemAbout;

    @FXML
    public FlowPane statusBar;

    private Model model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
