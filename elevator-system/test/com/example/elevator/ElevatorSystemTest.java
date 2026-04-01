package com.example.elevator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorSystemTest {

    private ElevatorSystem system;

    @BeforeEach
    void setUp()
    {
        system = new ElevatorSystem(10, 3, 500.0, new ShortestSeekStrategy());
    }

    @Test
    void testSystemCreation()
    {
        assertEquals(10, system.getFloors().size());
        assertEquals(3, system.getCars().size());
    }

    @Test
    void testInvalidFloorCount()
    {
        assertThrows(IllegalArgumentException.class,
                () -> new ElevatorSystem(1, 2, 500, new FcfsStrategy()));
    }

    @Test
    void testInvalidCarCount()
    {
        assertThrows(IllegalArgumentException.class,
                () -> new ElevatorSystem(5, 0, 500, new FcfsStrategy()));
    }

    @Test
    void testRequestElevatorUp()
    {
        ElevatorCar car = system.requestElevator(3, Direction.UP);
        assertNotNull(car);
        assertEquals(3, car.getCurrentFloor());
    }

    @Test
    void testRequestElevatorDown()
    {
        ElevatorCar car = system.requestElevator(5, Direction.DOWN);
        assertNotNull(car);
        assertEquals(5, car.getCurrentFloor());
    }

    @Test
    void testRequestFromInvalidFloor()
    {
        ElevatorCar car = system.requestElevator(15, Direction.UP);
        assertNull(car);
    }

    @Test
    void testRequestFromNegativeFloor()
    {
        ElevatorCar car = system.requestElevator(-1, Direction.UP);
        assertNull(car);
    }

    @Test
    void testCannotRequestDownFromGround()
    {
        ElevatorCar car = system.requestElevator(0, Direction.DOWN);
        assertNull(car);
    }

    @Test
    void testCannotRequestUpFromTopFloor()
    {
        ElevatorCar car = system.requestElevator(9, Direction.UP);
        assertNull(car);
    }

    @Test
    void testPressFloorButton()
    {
        ElevatorCar car = system.requestElevator(0, Direction.UP);
        assertNotNull(car);
        system.pressFloorButton(car, 7);
        assertEquals(7, car.getCurrentFloor());
    }

    @Test
    void testPressInvalidFloorButton()
    {
        ElevatorCar car = system.requestElevator(0, Direction.UP);
        assertNotNull(car);
        system.pressFloorButton(car, 20); // invalid, should not crash
        assertEquals(0, car.getCurrentFloor());
    }

    @Test
    void testSetAndClearMaintenance()
    {
        system.setCarMaintenance(1);
        ElevatorCar car1 = system.getCars().get(0);
        assertFalse(car1.isAvailable());

        system.clearCarMaintenance(1);
        assertTrue(car1.isAvailable());
    }

    @Test
    void testMaintenanceCarNotDispatched()
    {
        // Put all cars under maintenance except car 3
        system.setCarMaintenance(1);
        system.setCarMaintenance(2);

        ElevatorCar dispatched = system.requestElevator(3, Direction.UP);
        assertNotNull(dispatched);
        assertEquals(3, dispatched.getId()); // only car 3 is available
    }

    @Test
    void testAllCarsUnderMaintenance()
    {
        system.setCarMaintenance(1);
        system.setCarMaintenance(2);
        system.setCarMaintenance(3);

        ElevatorCar car = system.requestElevator(3, Direction.UP);
        assertNull(car);
    }

    @Test
    void testTriggerEmergency()
    {
        system.triggerEmergency();
        for (ElevatorCar car : system.getCars())
    {
            assertTrue(car.isDoorOpen());
            assertFalse(car.hasPendingStops());
        }
    }

    @Test
    void testAddFloor()
    {
        assertEquals(10, system.getFloors().size());
        system.addFloor();
        assertEquals(11, system.getFloors().size());

        // New top floor should not have UP button
        Floor newTop = system.getFloors().get(10);
        assertFalse(newTop.canRequest(Direction.UP));
        assertTrue(newTop.canRequest(Direction.DOWN));
    }

    @Test
    void testChangeStrategy()
    {
        system.setStrategy(new FcfsStrategy());
        ElevatorCar car = system.requestElevator(5, Direction.UP);
        assertNotNull(car);
    }

    @Test
    void testStatusDoesNotThrow()
    {
        assertDoesNotThrow(() -> system.status());
    }
}
