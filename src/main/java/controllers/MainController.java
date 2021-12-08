package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.models.Model;
import main.java.models.module.Theme;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
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

    @FXML
    public Label zoomLabel;

    private Model model;

    private Stage findStage;

    private FXMLLoader findLoader;

    private Stage findReplaceStage;

    private FXMLLoader findReplaceLoader;

    private Stage fontStage;

    private FXMLLoader fontLoader;

    private Stage aboutStage;

    private FXMLLoader aboutLoader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new Model();

        modelPropertyHandler();
        updateUI();

        initFindController();
        initFindReplaceController();
        initFontController();
        initAboutController();

        fileMenuHandler();
        editMenuHandler();
        formatMenuHandler();
        viewMenuHandler();
        helpMenuHandler();
    }

    private void modelPropertyHandler() {
        model.getStateModel().isSavedProperty().addListener((observable, oldValue, newValue) -> {
            Stage stage = (Stage) parent.getScene().getWindow();
            if (newValue) {
                if (stage.getTitle().startsWith("*")) stage.setTitle(stage.getTitle().substring(1));
            } else {
                stage.setTitle("*" + stage.getTitle());
            }
        });

        model.getStateModel().currentFileProperty().addListener((observable, oldValue, newValue) -> {
            Stage stage = (Stage) parent.getScene().getWindow();
            if (newValue != null) stage.setTitle(newValue.getName() + " - Notepad");
            else stage.setTitle("Untitled - Notepad");
        });

        model.getSettingsModel().fontNameProperty().addListener((observable, oldValue, newValue) -> {
            double fontSize = model.getSettingsModel().getFontSize();
            double zoom = model.getSettingsModel().getZoom();

            textArea.setFont(new Font(newValue, fontSize * zoom));
        });

        model.getSettingsModel().zoomProperty().addListener((observable, oldValue, newValue) -> {
            String fontName = model.getSettingsModel().getFontName();
            double fontSize = model.getSettingsModel().getFontSize();

            textArea.setFont(new Font(fontName, fontSize * newValue.doubleValue()));
            zoomLabel.setText((int)(newValue.doubleValue() * 100) + "%");
        });

        model.getSettingsModel().fontSizeProperty().addListener((observable, oldValue, newValue) -> {
            String fontName = model.getSettingsModel().getFontName();
            double zoom = model.getSettingsModel().getZoom();

            textArea.setFont(new Font(fontName, newValue.doubleValue() * zoom));
        });

        model.getSettingsModel().themeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getClassName().equals("text-area-dark")) {
                textArea.getStyleClass().add("text-area-dark");
                textArea.getStyleClass().remove("text-area-light");
            } else {
                textArea.getStyleClass().add("text-area-light");
                textArea.getStyleClass().remove("text-area-dark");
            }
        });
    }

    private void initFindController() {
        try {
            findLoader = new FXMLLoader(getClass().getResource("/view/findView.fxml"));
            findLoader.load();
            FindController findController = findLoader.getController();

            findController.getFindPrevButton().setOnMouseClicked(event -> {
                boolean isMatchCase = findController.getIsMatchCase().isSelected();

                String word = findController.getFindField().getText();
                if (!isMatchCase) word = word.toLowerCase();

                int anchor = textArea.getAnchor();

                String area = textArea.getText(0, anchor);
                if (!isMatchCase) area = area.toLowerCase();

                int foundIndex = area.lastIndexOf(word);
                int caret = foundIndex + word.length();

                if (foundIndex != -1) textArea.selectRange(foundIndex, caret);
            });

            findController.getFindNextButton().setOnMouseClicked(event -> {
                boolean isMatchCase = findController.getIsMatchCase().isSelected();

                String word = findController.getFindField().getText();
                if (!isMatchCase) word = word.toLowerCase();

                int anchor = textArea.getCaretPosition();

                String area = textArea.getText();
                if (!isMatchCase) area = area.toLowerCase();

                int foundIndex = area.indexOf(word, anchor);
                int caret = foundIndex + word.length();

                if (foundIndex != -1) textArea.selectRange(foundIndex, caret);
            });

            findController.getCancelButton().setOnMouseClicked(event -> findStage.close());
        } catch (Exception ignored) {

        }
    }

    private void initFindReplaceController() {
        try {
            findReplaceLoader = new FXMLLoader(getClass().getResource("/view/findReplaceView.fxml"));
            findReplaceLoader.load();
            FindReplaceController findReplaceController = findReplaceLoader.getController();

            findReplaceController.getFindPrevButton().setOnMouseClicked(event -> {
                boolean isMatchCase = findReplaceController.getIsMatchCase().isSelected();

                String word = findReplaceController.getFindField().getText();
                if (!isMatchCase) word = word.toLowerCase();

                int anchor = textArea.getAnchor();

                String area = textArea.getText(0, anchor);
                if (!isMatchCase) area = area.toLowerCase();

                int foundIndex = area.lastIndexOf(word);
                int caret = foundIndex + word.length();

                if (foundIndex != -1) textArea.selectRange(foundIndex, caret);
            });

            findReplaceController.getFindNextButton().setOnMouseClicked(event -> {
                boolean isMatchCase = findReplaceController.getIsMatchCase().isSelected();

                String word = findReplaceController.getFindField().getText();
                if (!isMatchCase) word = word.toLowerCase();

                int anchor = textArea.getCaretPosition();

                String area = textArea.getText();
                if (!isMatchCase) area = area.toLowerCase();

                int foundIndex = area.indexOf(word, anchor);
                int caret = foundIndex + word.length();

                if (foundIndex != -1) textArea.selectRange(foundIndex, caret);
            });

            findReplaceController.getReplaceButton().setOnMouseClicked(event -> {
                String replacement = findReplaceController.getReplaceField().getText();

                boolean isMatchCase = findReplaceController.getIsMatchCase().isSelected();

                String word = findReplaceController.getFindField().getText();
                if (!isMatchCase) word = word.toLowerCase();

                String selected = textArea.getSelectedText();
                if (!isMatchCase) selected = selected.toLowerCase();

                if (selected.equals(word)) {
                    textArea.replaceSelection(replacement);
                }

                int anchor = textArea.getCaretPosition();

                String area = textArea.getText();
                if (!isMatchCase) area = area.toLowerCase();

                int foundIndex = area.indexOf(word, anchor);
                int caret = foundIndex + word.length();

                if (foundIndex != -1) textArea.selectRange(foundIndex, caret);
            });

            findReplaceController.getReplaceAllButton().setOnMouseClicked(event -> {
                boolean isMatchCase = findReplaceController.getIsMatchCase().isSelected();

                String replacement = findReplaceController.getReplaceField().getText();

                String word = findReplaceController.getFindField().getText();
                if (!isMatchCase) word = "(?i)" + word.toLowerCase();

                textArea.setText(textArea.getText().replaceAll(word, replacement));
            });

            findReplaceController.getCancelButton().setOnMouseClicked(event -> findReplaceStage.close());
        } catch (Exception ignored) {

        }
    }

    private void initFontController() {
        try {
            fontLoader = new FXMLLoader(getClass().getResource("/view/fontView.fxml"));
            fontLoader.load();
            FontController fontController = fontLoader.getController();
            fontController.getOkBtn().setOnMouseClicked(event -> {
                String fontFamily = fontController.getFontFamilyChooser().getSelectionModel().getSelectedItem();
                String fontName = fontController.getFontStyleChooser().getSelectionModel().getSelectedItem();
                int fontSize = fontController.getFontSizeChooser().getSelectionModel().getSelectedItem();
                Theme theme = (Theme) fontController.getTheme().selectedToggleProperty().get().getUserData();

                model.getSettingsModel().setFontFamily(fontFamily);
                model.getSettingsModel().setFontName(fontName);
                model.getSettingsModel().setFontSize(fontSize);
                model.getSettingsModel().setTheme(theme);

                fontStage.close();
            });
            fontController.getCancelBtn().setOnMouseClicked(event -> {
                String fontFamily = model.getSettingsModel().getFontFamily();
                String fontName = model.getSettingsModel().getFontName();
                double fontSize = model.getSettingsModel().getFontSize();
                Theme theme = model.getSettingsModel().getTheme();

                fontController.getFontFamilyChooser().getSelectionModel().select(fontFamily);
                fontController.getFontSizeChooser().getSelectionModel().select(Integer.valueOf((int) fontSize));
                fontController.getFontStyleChooser().getSelectionModel().select(fontName);
                Toggle toggle = fontController.getTheme().getToggles()
                        .stream()
                        .filter(tgl -> tgl.getUserData().equals(theme))
                        .findFirst()
                        .orElse(null);
                fontController.getTheme().selectToggle(toggle);
                fontStage.close();
            });

        } catch (Exception ignored) {

        }
    }

    private void initAboutController() {
        try {
            aboutLoader = new FXMLLoader(getClass().getResource("/view/aboutView.fxml"));
            aboutLoader.load();
        } catch (Exception ignored) {

        }
    }

    private void alertSaveFile() {
        File currentFile = model.getStateModel().getCurrentFile();
        String filepath = currentFile != null ? currentFile.getAbsolutePath() : "Untitled";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save File ?");
        alert.setContentText("Do you want to save changes to " + filepath + " ?");

        ButtonType buttonTypeSave = new ButtonType("Save", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Don't Save", ButtonBar.ButtonData.NO);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeSave, buttonTypeNo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get().equals(buttonTypeSave)) {
            try {
                if (currentFile == null) currentFile = saveNewFile();

                writeToFile(currentFile);

                model.getStateModel().setCurrentFile(null);
                textArea.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (result.get().equals(buttonTypeNo)) {
            model.getStateModel().setCurrentFile(null);
            textArea.setText("");
        }
    }

    private File saveNewFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File currentFile = fileChooser.showSaveDialog(menuItemSave.getParentPopup().getOwnerWindow());
        model.getStateModel().setCurrentFile(currentFile);
        return currentFile;
    }

    private void writeToFile(File file) throws IOException {
        FileWriter myWriter = new FileWriter(file);
        myWriter.write(textArea.getText());
        myWriter.close();
        model.getStateModel().setIsSaved(true);
    }

    private void fileMenuHandler() {
        menuItemNew.setOnAction(event -> {
            if (!model.getStateModel().isIsSaved()) {
                alertSaveFile();
            } else {
                model.getStateModel().setCurrentFile(null);
                textArea.setText("");
            }
        });

        menuItemNewWindow.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/mainView.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Untitled - Notepad");
                stage.setScene(new Scene(root, 900, 600));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        menuItemOpen.setOnAction(event -> {
            if (!model.getStateModel().isIsSaved()) alertSaveFile();

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File", "*.txt"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(menuItemOpen.getParentPopup().getOwnerWindow());

            try {
                if (file != null) {
                    model.getStateModel().setCurrentFile(file);
                    BufferedReader reader = new BufferedReader(new FileReader(file));

                    String line;
                    StringBuilder totalFile = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        totalFile.append(line);
                        totalFile.append("\n");
                    }
                    textArea.setText(totalFile.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        menuItemSave.setOnAction(event -> {
            try {
                File currentFile = model.getStateModel().getCurrentFile();
                if (currentFile == null) currentFile = saveNewFile();

                writeToFile(currentFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        menuItemSaveAs.setOnAction(event -> {
            try {
                File currentFile = saveNewFile();
                writeToFile(currentFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        menuItemExit.setOnAction(event -> {
            Stage stage = (Stage) parent.getScene().getWindow();
            stage.close();
        });
    }

    private void editMenuHandler() {
        menuItemCut.setOnAction(event -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(textArea.getSelectedText());
            clipboard.setContent(clipboardContent);

            textArea.replaceSelection("");
        });

        menuItemCopy.setOnAction(event -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(textArea.getSelectedText());
            clipboard.setContent(clipboardContent);
        });

        menuItemPaste.setOnAction(event -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            String stringClipboard = clipboard.getContent(DataFormat.PLAIN_TEXT).toString();
            textArea.replaceSelection(stringClipboard);
        });

        menuItemDelete.setOnAction(event -> textArea.replaceSelection(""));

        menuItemFind.setOnAction(event -> {
            try {
                if (findStage == null) {
                    findStage = new Stage();
                    findStage.setTitle("Find");
                    findStage.setScene(new Scene(findLoader.getRoot()));
                }

                if (!findStage.isShowing()) {
                    findStage.show();
                } else {
                    findStage.requestFocus();
                }

                if (findReplaceStage != null) findReplaceStage.close();
                if (fontStage != null) fontStage.close();
                if (aboutStage != null) aboutStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        menuItemFindReplace.setOnAction(event -> {
            try {
                if (findReplaceStage == null) {
                    findReplaceStage = new Stage();
                    findReplaceStage.setTitle("Find and Replace");
                    findReplaceStage.setScene(new Scene(findReplaceLoader.getRoot()));
                }

                if (!findReplaceStage.isShowing()) {
                    findReplaceStage.show();
                } else {
                    findReplaceStage.requestFocus();
                }

                if (findStage != null) findStage.close();
                if (fontStage != null) fontStage.close();
                if (aboutStage != null) aboutStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        menuItemSelectAll.setOnAction(event -> textArea.selectAll());

        menuItemTimeDate.setOnAction(event -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            textArea.replaceSelection(formatter.format(new Date()));
        });
    }

    private void formatMenuHandler() {
        menuItemWordWrap.selectedProperty().addListener((observable, oldValue, newValue) -> {
            textArea.setWrapText(newValue);
        });

        menuItemFont.setOnAction(event -> {
            try {
                if (fontStage == null) {
                    fontStage = new Stage();
                    fontStage.setTitle("Font");
                    fontStage.setScene(new Scene(fontLoader.getRoot()));
                }

                if (!fontStage.isShowing()) {
                    fontStage.show();
                } else {
                    fontStage.requestFocus();
                }

                if (findStage != null) findStage.close();
                if (findReplaceStage != null) findReplaceStage.close();
                if (aboutStage != null) aboutStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void viewMenuHandler() {
        menuItemStatusBar.selectedProperty().addListener((observable, oldValue, newValue) -> {
            statusBar.setVisible(newValue);
            statusBar.setMaxHeight(newValue ? statusBar.getPrefHeight() : 0);
        });

        menuItemZoomIn.setOnAction(event -> {
            double currentZoom = model.getSettingsModel().getZoom();
            double nextZoom = currentZoom + 0.1;
            model.getSettingsModel().setZoom(Math.min(2, nextZoom));
        });

        menuItemZoomOut.setOnAction(event -> {
            double currentZoom = model.getSettingsModel().getZoom();
            double nextZoom = currentZoom - 0.1;
            model.getSettingsModel().setZoom(Math.max(nextZoom, 0.5));
        });

        menuItemRestoreZoom.setOnAction(event -> {
            model.getSettingsModel().setZoom(1);
        });
    }

    private void helpMenuHandler() {
        menuItemAbout.setOnAction(event -> {
            try {
                if (aboutStage == null) {
                    aboutStage = new Stage();
                    aboutStage.setTitle("About");
                    aboutStage.setScene(new Scene(aboutLoader.getRoot()));
                }

                if (!aboutStage.isShowing()) {
                    aboutStage.show();
                } else {
                    aboutStage.requestFocus();
                }

                if (findStage != null) findStage.close();
                if (findReplaceStage != null) findReplaceStage.close();
                if (fontStage != null) fontStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void updateUI() {
        // Setting default font
        Font defaultFont = new Font(model.getSettingsModel().getFontName(),
                                    model.getSettingsModel().getFontSize());
        textArea.setFont(defaultFont);
        textArea.setWrapText(true);
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                File currentFile = model.getStateModel().getCurrentFile();
                if (currentFile != null) {
                    BufferedReader reader = new BufferedReader(new FileReader(currentFile));

                    String line;
                    StringBuilder fileText = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        fileText.append(line);
                        fileText.append("\n");
                    }

                    model.getStateModel().setIsSaved(newValue.equals(fileText.toString()));
                } else {
                    model.getStateModel().setIsSaved(newValue.equals(""));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Setting default settings in check menu item
        menuItemWordWrap.setSelected(true);
        menuItemStatusBar.setSelected(true);
    }

}