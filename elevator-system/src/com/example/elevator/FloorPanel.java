package com.example.elevator;

public class FloorPanel {
    private final boolean hasUpButton;
    private final boolean hasDownButton;

    public FloorPanel(boolean hasUpButton, boolean hasDownButton)
    {
        this.hasUpButton = hasUpButton;
        this.hasDownButton = hasDownButton;
    }

    public boolean hasUpButton()
    {
        return hasUpButton;
    }

    public boolean hasDownButton()
    {
        return hasDownButton;
    }
}
