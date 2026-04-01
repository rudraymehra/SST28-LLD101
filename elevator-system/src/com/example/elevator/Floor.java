package com.example.elevator;

public class Floor {
    private final int floorNumber;
    private final FloorPanel panel;

    public Floor(int floorNumber, boolean isGround, boolean isTop)
    {
        this.floorNumber = floorNumber;
        boolean hasUp = !isTop;
        boolean hasDown = !isGround;
        this.panel = new FloorPanel(hasUp, hasDown);
    }

    public int getFloorNumber()
    {
        return floorNumber;
    }

    public FloorPanel getPanel()
    {
        return panel;
    }

    public boolean canRequest(Direction direction)
    {
        if (direction == Direction.UP)
    {
            return panel.hasUpButton();
        }
        return panel.hasDownButton();
    }

    @Override
    public String toString()
    {
        return "Floor " + floorNumber;
    }
}
