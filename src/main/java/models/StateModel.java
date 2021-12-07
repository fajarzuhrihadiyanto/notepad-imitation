package main.java.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.File;

public class StateModel {

    private BooleanProperty isSaved;
    private ObjectProperty<File> currentFile;

    public StateModel() {
        this.isSaved = new SimpleBooleanProperty(true);
        this.currentFile = new SimpleObjectProperty<>(null);
    }

    public boolean isIsSaved() {
        return isSaved.get();
    }

    public void setIsSaved(boolean isSaved) {
        this.isSaved.set(isSaved);
    }

    public BooleanProperty isSavedProperty() {
        return isSaved;
    }

    public File getCurrentFile() {
        return currentFile.get();
    }

    public ObjectProperty<File> currentFileProperty() {
        return currentFile;
    }

    public void setCurrentFile(File currentFile) {
        this.currentFile.set(currentFile);
    }
}
