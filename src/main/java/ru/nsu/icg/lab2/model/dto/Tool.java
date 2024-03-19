package ru.nsu.icg.lab2.model.dto;

public record Tool(String name,
                   String tip,
                   String iconPath,
                   String controllerClassPath,
                   int group,
                   boolean isToggle,
                   boolean isHand,
                   boolean isOneToOne,
                   boolean isOnWindowSize) {
    public boolean hasGroup() {
        return group != 0;
    }
}
