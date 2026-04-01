package com.example.elevator;

import java.util.List;

public class FcfsStrategy implements DispatchStrategy {

    @Override
    public ElevatorCar selectElevator(Request request, List<ElevatorCar> cars)
    {
        for (ElevatorCar car : cars)
    {
            if (car.isAvailable() && car.isIdle() && !car.isOverloaded())
    {
                return car;
            }
        }
        for (ElevatorCar car : cars)
    {
            if (car.isAvailable() && !car.isOverloaded())
    {
                return car;
            }
        }
        return null;
    }
}
