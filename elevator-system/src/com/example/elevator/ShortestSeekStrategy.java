package com.example.elevator;

import java.util.List;

public class ShortestSeekStrategy implements DispatchStrategy {

    @Override
    public ElevatorCar selectElevator(Request request, List<ElevatorCar> cars)
    {
        ElevatorCar best = null;
        int minDistance = Integer.MAX_VALUE;

        for (ElevatorCar car : cars)
    {
            if (!car.isAvailable() || car.isOverloaded())
    {
                continue;
            }
            int distance = Math.abs(car.getCurrentFloor() - request.getFloor());
            if (distance < minDistance)
    {
                minDistance = distance;
                best = car;
            } else if (distance == minDistance && best != null && car.isIdle() && !best.isIdle())
    {
                best = car;
            }
        }
        return best;
    }
}
