package com.example.elevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FloorTest {

    @Test
    void testGroundFloorHasOnlyUpButton()
    {
        Floor ground = new Floor(0, true, false);
        assertTrue(ground.canRequest(Direction.UP));
        assertFalse(ground.canRequest(Direction.DOWN));
    }

    @Test
    void testTopFloorHasOnlyDownButton()
    {
        Floor top = new Floor(9, false, true);
        assertFalse(top.canRequest(Direction.UP));
        assertTrue(top.canRequest(Direction.DOWN));
    }

    @Test
    void testMiddleFloorHasBothButtons()
    {
        Floor mid = new Floor(5, false, false);
        assertTrue(mid.canRequest(Direction.UP));
        assertTrue(mid.canRequest(Direction.DOWN));
    }

    @Test
    void testFloorNumber()
    {
        Floor floor = new Floor(7, false, false);
        assertEquals(7, floor.getFloorNumber());
    }

    @Test
    void testToString()
    {
        Floor floor = new Floor(3, false, false);
        assertEquals("Floor 3", floor.toString());
    }
}
