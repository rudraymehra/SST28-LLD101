package com.example.snakeladder;


public class Player {
    private final String name;
    private int position;

    public Player(String name)
    {
        this.name = name;
        this.position = 0;   // off the board, hasn't started yet
    }

    public String getName()
    { return name; }
    public int getPosition()
    { return position; }

    public void moveTo(int cell)
    {
        this.position = cell;
    }

    @Override
    public String toString()
    {
        return name + "(cell=" + position + ")";
    }
}
