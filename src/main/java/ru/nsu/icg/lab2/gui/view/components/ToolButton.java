package ru.nsu.icg.lab2.gui.view.components;

import javax.swing.*;
import java.awt.event.ActionListener;

public record ToolButton(ActionListener actionListener, ImageIcon icon, String tip) {
}
