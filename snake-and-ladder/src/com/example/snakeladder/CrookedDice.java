package com.example.snakeladder;

import java.util.Random;

public class CrookedDice implements Dice{
    private static final int[] EVEN_FACES = {2, 4, 6};
    private final Random random;

    public CrookedDice()
    {
        this.random = new Random();
    }

    @Override
    public int roll()
    {
        return EVEN_FACES[random.nextInt(EVEN_FACES.length)];
    }

    @Override
    public int faces()
    { return 6; }
}
