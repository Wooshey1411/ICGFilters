package ru.nsu.icg.lab2.gui.view;

import java.io.File;

public interface View {
    void hide();

    void destroy();

    void resize(int width, int height);

    File showOpenDialog();

    File showSaveDialog();

    void showError(String errorMessage);

    void showHelp();

    void showAbout();
}
