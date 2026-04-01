package com.example.elevator;

import java.util.List;

public interface DispatchStrategy {
    
    ElevatorCar selectElevator(Request request, List<ElevatorCar> cars);
}
