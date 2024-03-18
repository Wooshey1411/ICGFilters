package ru.nsu.icg.lab2.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Tool {
    private String name;

    private String tip;

    @SerializedName("icon")
    private String iconPath;

    @SerializedName("controller")
    private String controllerClassPath;

    private int group;
}
