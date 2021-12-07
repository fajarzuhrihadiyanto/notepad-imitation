package main.java.models;

import javafx.beans.property.*;
import javafx.scene.text.Font;
import main.java.models.module.Theme;

public class SettingsModel {

    private StringProperty fontFamily;

    private StringProperty fontName;

    private DoubleProperty fontSize;

    private DoubleProperty zoom;

    private ObjectProperty<Theme> theme;

    public SettingsModel() {
        fontFamily = new SimpleStringProperty(Font.getDefault().getFamily());
        fontName = new SimpleStringProperty(Font.getDefault().getName());
        fontSize = new SimpleDoubleProperty(14);
        zoom = new SimpleDoubleProperty(1);
        theme = new SimpleObjectProperty<>(Theme.LIGHT);
    }

    public String getFontFamily() {
        return fontFamily.get();
    }

    public StringProperty fontFamilyProperty() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily.set(fontFamily);
    }

    public String getFontName() {
        return fontName.get();
    }

    public StringProperty fontNameProperty() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName.set(fontName);
    }

    public double getFontSize() {
        return fontSize.get();
    }

    public DoubleProperty fontSizeProperty() {
        return fontSize;
    }

    public void setFontSize(double fontSize) {
        this.fontSize.set(fontSize);
    }

    public double getZoom() {
        return zoom.get();
    }

    public DoubleProperty zoomProperty() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom.set(zoom);
    }

    public Theme getTheme() {
        return theme.get();
    }

    public ObjectProperty<Theme> themeProperty() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme.set(theme);
    }
}
