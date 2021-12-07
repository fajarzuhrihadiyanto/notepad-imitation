package main.java.models.module;

public enum Theme {
    LIGHT("Light",  "text-area-light"),
    DARK("Dark",  "text-area-dark");

    private final String label;
    private final String className;

    Theme(String label, String className) {
        this.label = label;
        this.className = className;
    }

    public String getLabel() {
        return label;
    }

    public String getClassName() {
        return className;
    }
}
