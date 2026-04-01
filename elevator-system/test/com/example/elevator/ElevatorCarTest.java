package com.example.elevator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorCarTest {

    private ElevatorCar car;

    @BeforeEach
    void setUp()
    {
        car = new ElevatorCar(1, 500.0);
    }

    @Test
    void testInitialState()
    {
        assertEquals(1, car.getId());
        assertEquals(0, car.getCurrentFloor());
        assertEquals(ElevatorState.IDLE, car.getState());
        assertEquals(0.0, car.getCurrentWeight());
        assertEquals(500.0, car.getWeightLimit());
        assertFalse(car.isDoorOpen());
        assertTrue(car.isAvailable());
        assertTrue(car.isIdle());
    }

    @Test
    void testInvalidWeightLimit()
    {
        assertThrows(IllegalArgumentException.class, () -> new ElevatorCar(2, 0));
        assertThrows(IllegalArgumentException.class, () -> new ElevatorCar(2, -100));
    }

    @Test
    void testMoveToHigherFloor()
    {
        car.moveTo(5);
        assertEquals(5, car.getCurrentFloor());
        assertEquals(ElevatorState.IDLE, car.getState());
        assertTrue(car.isDoorOpen());
    }

    @Test
    void testMoveToLowerFloor()
    {
        car.moveTo(5);
        car.moveTo(2);
        assertEquals(2, car.getCurrentFloor());
    }

    @Test
    void testMoveToSameFloor()
    {
        car.moveTo(0);
        assertEquals(0, car.getCurrentFloor());
        assertEquals(ElevatorState.IDLE, car.getState());
    }

    @Test
    void testOpenAndCloseDoor()
    {
        car.openDoor();
        assertTrue(car.isDoorOpen());
        car.closeDoor();
        assertFalse(car.isDoorOpen());
    }

    @Test
    void testCannotCloseDoorWhenOverloaded()
    {
        car.addPassengerWeight(600);
        assertTrue(car.isOverloaded());
        assertTrue(car.isDoorOpen());
        car.closeDoor();
        assertTrue(car.isDoorOpen()); // still open
    }

    @Test
    void testCannotMoveWhenOverloaded()
    {
        car.addPassengerWeight(600);
        car.moveTo(3);
        assertEquals(0, car.getCurrentFloor()); // didn't move
    }

    @Test
    void testAddAndRemoveWeight()
    {
        car.addPassengerWeight(200);
        assertEquals(200.0, car.getCurrentWeight());
        car.removePassengerWeight(50);
        assertEquals(150.0, car.getCurrentWeight());
        car.removePassengerWeight(200);
        assertEquals(0.0, car.getCurrentWeight()); // clamped to 0
    }

    @Test
    void testMaintenanceMode()
    {
        car.setUnderMaintenance();
        assertEquals(ElevatorState.UNDER_MAINTENANCE, car.getState());
        assertFalse(car.isAvailable());

        car.addDestination(5);
        car.processNextStop();
        assertEquals(0, car.getCurrentFloor()); // should not move

        car.clearMaintenance();
        assertEquals(ElevatorState.IDLE, car.getState());
        assertTrue(car.isAvailable());
    }

    @Test
    void testCannotMoveUnderMaintenance()
    {
        car.setUnderMaintenance();
        car.moveTo(3);
        assertEquals(0, car.getCurrentFloor());
    }

    @Test
    void testTriggerAlarm()
    {
        car.addDestination(3);
        car.addDestination(5);
        car.triggerAlarm();
        assertEquals(ElevatorState.IDLE, car.getState());
        assertTrue(car.isDoorOpen());
        assertFalse(car.hasPendingStops());
    }

    @Test
    void testAddDestinationAndProcess()
    {
        car.addDestination(3);
        assertTrue(car.hasPendingStops());
        car.processNextStop();
        assertEquals(3, car.getCurrentFloor());
        assertFalse(car.hasPendingStops());
    }

    @Test
    void testDuplicateDestinationIgnored()
    {
        car.addDestination(3);
        car.addDestination(3);
        car.processNextStop();
        assertEquals(3, car.getCurrentFloor());
        assertFalse(car.hasPendingStops());
    }

    @Test
    void testProcessNextStopWithEmptyQueue()
    {
        car.processNextStop();
        assertEquals(ElevatorState.IDLE, car.getState());
        assertEquals(0, car.getCurrentFloor());
    }

    @Test
    void testToString()
    {
        String str = car.toString();
        assertTrue(str.contains("Elevator 1"));
        assertTrue(str.contains("floor=0"));
        assertTrue(str.contains("IDLE"));
    }
}
