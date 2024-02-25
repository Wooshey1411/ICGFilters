package ru.nsu.icg.lab2.model.context;

public interface ContextListener {
    // TODO: добавить осмысленные методы: onContextActionChange(), onContextFilterChange(), ...
    // TODO: после добавление дополнительных методов разделить интерфейс на несколько по принципу ISP
    void onContextActionChange(Context context, ContextAction action);
}
