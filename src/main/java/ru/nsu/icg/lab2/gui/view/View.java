package ru.nsu.icg.lab2.gui.view;

public interface View {
    void hide();

    void destroy();

    void resize();

    void showError(String errorMessage);

    void showHelp();

    void showAbout();
}
