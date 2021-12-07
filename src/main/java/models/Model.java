package main.java.models;

public class Model {

    private final SettingsModel settingsModel;

    private final StateModel stateModel;

    public Model() {
        settingsModel = new SettingsModel();
        stateModel = new StateModel();
    }

    public SettingsModel getSettingsModel() {
        return settingsModel;
    }

    public StateModel getStateModel() {
        return stateModel;
    }
}
