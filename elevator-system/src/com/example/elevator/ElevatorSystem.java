package com.example.elevator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ElevatorSystem {
    private final List<Floor> floors;
    private final List<ElevatorCar> cars;
    private DispatchStrategy strategy;

    public ElevatorSystem(int numberOfFloors, int numberOfCars, double weightLimitPerCar, DispatchStrategy strategy)
    {
        if (numberOfFloors < 2)
    {
            throw new IllegalArgumentException("need at least 2 floors");
        }
        if (numberOfCars < 1)
    {
            throw new IllegalArgumentException("need at least 1 elevator car");
        }

        this.floors = new ArrayList<>();
        for (int i = 0; i < numberOfFloors; i++)
    {
            boolean isGround = (i == 0);
            boolean isTop = (i == numberOfFloors - 1);
            floors.add(new Floor(i, isGround, isTop));
        }

        this.cars = new ArrayList<>();
        for (int i = 1; i <= numberOfCars; i++)
    {
            cars.add(new ElevatorCar(i, weightLimitPerCar));
        }

        this.strategy = strategy;
    }

    public void setStrategy(DispatchStrategy strategy)
    {
        this.strategy = strategy;
    }

    public void addFloor()
    {
        int oldTopIndex = floors.size() - 1;
        Floor oldTop = floors.get(oldTopIndex);
        floors.set(oldTopIndex, new Floor(oldTop.getFloorNumber(), oldTopIndex == 0, false));

        int newFloorNumber = floors.size();
        floors.add(new Floor(newFloorNumber, false, true));
        System.out.println("  Floor " + newFloorNumber + " added. Total floors: " + floors.size());
    }

    public ElevatorCar requestElevator(int fromFloor, Direction direction)
    {
        if (fromFloor < 0 || fromFloor >= floors.size())
    {
            System.out.println("  Invalid floor: " + fromFloor);
            return null;
        }

        Floor floor = floors.get(fromFloor);
        if (!floor.canRequest(direction))
    {
            System.out.println("  Floor " + fromFloor + " does not have a " + direction + " button.");
            return null;
        }

        Request request = new Request(fromFloor, direction);
        ElevatorCar selected = strategy.selectElevator(request, cars);

        if (selected == null)
    {
            System.out.println("  No elevator available for " + request + ".");
            return null;
        }

        System.out.println("  Dispatching Elevator " + selected.getId() + " for " + request + ".");
        selected.moveTo(fromFloor);
        return selected;
    }

    public void pressFloorButton(ElevatorCar car, int targetFloor)
    {
        if (targetFloor < 0 || targetFloor >= floors.size())
    {
            System.out.println("  Invalid target floor: " + targetFloor);
            return;
        }
        car.addDestination(targetFloor);
        car.processNextStop();
    }

    public void triggerEmergency()
    {
        System.out.println("  ** EMERGENCY: All elevators stopping. **");
        for (ElevatorCar car : cars)
    {
            if (car.isAvailable())
    {
                car.triggerAlarm();
            }
        }
    }

    public void setCarMaintenance(int carId)
    {
        for (ElevatorCar car : cars)
    {
            if (car.getId() == carId)
    {
                car.setUnderMaintenance();
                return;
            }
        }
        System.out.println("  Elevator " + carId + " not found.");
    }

    public void clearCarMaintenance(int carId)
    {
        for (ElevatorCar car : cars)
    {
            if (car.getId() == carId)
    {
                car.clearMaintenance();
                return;
            }
        }
        System.out.println("  Elevator " + carId + " not found.");
    }

    public void status()
    {
        System.out.println("  --- System Status ---");
        System.out.println("  Floors: " + floors.size());
        for (ElevatorCar car : cars)
    {
            System.out.println("  " + car);
        }
        System.out.println("  ---------------------");
    }

    public List<Floor> getFloors()
    {
        return Collections.unmodifiableList(floors);
    }

    public List<ElevatorCar> getCars()
    {
        return Collections.unmodifiableList(cars);
    }
}
