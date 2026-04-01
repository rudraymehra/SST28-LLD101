package com.example.elevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RequestTest {

    @Test
    void testRequestCreation()
    {
        Request request = new Request(5, Direction.UP);
        assertEquals(5, request.getFloor());
        assertEquals(Direction.UP, request.getDirection());
    }

    @Test
    void testToString()
    {
        Request request = new Request(3, Direction.DOWN);
        String str = request.toString();
        assertTrue(str.contains("3"));
        assertTrue(str.contains("DOWN"));
    }
}
