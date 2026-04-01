package com.example.elevator;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StrategyTest {

    @Test
    void testShortestSeekPicksClosest()
    {
        ElevatorCar car1 = new ElevatorCar(1, 500);
        ElevatorCar car2 = new ElevatorCar(2, 500);

        car1.moveTo(2);
        car2.moveTo(8);

        ShortestSeekStrategy strategy = new ShortestSeekStrategy();
        Request request = new Request(3, Direction.UP);

        ElevatorCar selected = strategy.selectElevator(request, Arrays.asList(car1, car2));
        assertEquals(1, selected.getId()); // car1 is closer
    }

    @Test
    void testShortestSeekPrefersIdleOnTie()
    {
        ElevatorCar car1 = new ElevatorCar(1, 500);
        ElevatorCar car2 = new ElevatorCar(2, 500);

        // Both at floor 0, both idle and same distance — first one wins
        ShortestSeekStrategy strategy = new ShortestSeekStrategy();
        Request request = new Request(0, Direction.UP);

        ElevatorCar selected = strategy.selectElevator(request, Arrays.asList(car1, car2));
        assertEquals(1, selected.getId());
    }

    @Test
    void testShortestSeekSkipsUnavailable()
    {
        ElevatorCar car1 = new ElevatorCar(1, 500);
        ElevatorCar car2 = new ElevatorCar(2, 500);

        car1.setUnderMaintenance();

        ShortestSeekStrategy strategy = new ShortestSeekStrategy();
        Request request = new Request(0, Direction.UP);

        ElevatorCar selected = strategy.selectElevator(request, Arrays.asList(car1, car2));
        assertEquals(2, selected.getId());
    }

    @Test
    void testShortestSeekSkipsOverloaded()
    {
        ElevatorCar car1 = new ElevatorCar(1, 500);
        ElevatorCar car2 = new ElevatorCar(2, 500);

        car1.addPassengerWeight(600); // overloaded

        ShortestSeekStrategy strategy = new ShortestSeekStrategy();
        Request request = new Request(0, Direction.UP);

        ElevatorCar selected = strategy.selectElevator(request, Arrays.asList(car1, car2));
        assertEquals(2, selected.getId());
    }

    @Test
    void testShortestSeekReturnsNullWhenNoneAvailable()
    {
        ElevatorCar car1 = new ElevatorCar(1, 500);
        car1.setUnderMaintenance();

        ShortestSeekStrategy strategy = new ShortestSeekStrategy();
        Request request = new Request(0, Direction.UP);

        assertNull(strategy.selectElevator(request, List.of(car1)));
    }

    @Test
    void testFcfsPrefersIdleFirst()
    {
        ElevatorCar car1 = new ElevatorCar(1, 500);
        ElevatorCar car2 = new ElevatorCar(2, 500);

        // Move car1 so it's in MOVING state — but moveTo sets back to IDLE.
        // addDestination doesn't change state, so both are IDLE. First idle wins.
        FcfsStrategy strategy = new FcfsStrategy();
        Request request = new Request(0, Direction.UP);

        ElevatorCar selected = strategy.selectElevator(request, Arrays.asList(car1, car2));
        assertEquals(1, selected.getId()); // first idle car
    }

    @Test
    void testFcfsFallsBackToNonIdle()
    {
        ElevatorCar car1 = new ElevatorCar(1, 500);
        car1.addDestination(5); // not idle but available

        FcfsStrategy strategy = new FcfsStrategy();
        Request request = new Request(0, Direction.UP);

        ElevatorCar selected = strategy.selectElevator(request, List.of(car1));
        assertEquals(1, selected.getId());
    }

    @Test
    void testFcfsReturnsNullWhenNoneAvailable()
    {
        ElevatorCar car1 = new ElevatorCar(1, 500);
        car1.setUnderMaintenance();

        FcfsStrategy strategy = new FcfsStrategy();
        Request request = new Request(0, Direction.UP);

        assertNull(strategy.selectElevator(request, List.of(car1)));
    }
}
