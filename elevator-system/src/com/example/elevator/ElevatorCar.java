package com.example.elevator;

import java.util.LinkedList;
import java.util.Queue;

public class ElevatorCar {
    private final int id;
    private final double weightLimit;
    private int currentFloor;
    private double currentWeight;
    private ElevatorState state;
    private boolean doorOpen;
    private final Queue<Integer> pendingFloors;

    public ElevatorCar(int id, double weightLimit)
    {
        if (weightLimit <= 0)
    {
            throw new IllegalArgumentException("weight limit must be positive");
        }
        this.id = id;
        this.weightLimit = weightLimit;
        this.currentFloor = 0;
        this.currentWeight = 0;
        this.state = ElevatorState.IDLE;
        this.doorOpen = false;
        this.pendingFloors = new LinkedList<>();
    }

    public int getId()
    {
        return id;
    }

    public int getCurrentFloor()
    {
        return currentFloor;
    }

    public ElevatorState getState()
    {
        return state;
    }

    public double getWeightLimit()
    {
        return weightLimit;
    }

    public double getCurrentWeight()
    {
        return currentWeight;
    }

    public boolean isDoorOpen()
    {
        return doorOpen;
    }

    public boolean isAvailable()
    {
        return state != ElevatorState.UNDER_MAINTENANCE;
    }

    public boolean isIdle()
    {
        return state == ElevatorState.IDLE;
    }

    public void setUnderMaintenance()
    {
        this.state = ElevatorState.UNDER_MAINTENANCE;
        this.pendingFloors.clear();
        System.out.println("  Elevator " + id + " is now under maintenance.");
    }

    public void clearMaintenance()
    {
        if (state == ElevatorState.UNDER_MAINTENANCE)
    {
            this.state = ElevatorState.IDLE;
            System.out.println("  Elevator " + id + " maintenance complete. Back to IDLE.");
        }
    }

    public void addPassengerWeight(double weight)
    {
        this.currentWeight += weight;
        if (currentWeight > weightLimit)
    {
            System.out.println("  ** Elevator " + id + ": WEIGHT LIMIT EXCEEDED. Doors staying open. Please reduce load. **");
            this.doorOpen = true;
        }
    }

    public void removePassengerWeight(double weight)
    {
        this.currentWeight = Math.max(0, this.currentWeight - weight);
    }

    public boolean isOverloaded()
    {
        return currentWeight > weightLimit;
    }

    public void openDoor()
    {
        this.doorOpen = true;
        System.out.println("  Elevator " + id + ": doors opened at floor " + currentFloor + ".");
    }

    public void closeDoor()
    {
        if (isOverloaded())
    {
            System.out.println("  Elevator " + id + ": cannot close doors. Weight limit exceeded.");
            return;
        }
        this.doorOpen = false;
        System.out.println("  Elevator " + id + ": doors closed.");
    }

    public void triggerAlarm()
    {
        System.out.println("  ** Elevator " + id + ": ALARM TRIGGERED. Emergency stop at floor " + currentFloor + ". **");
        this.state = ElevatorState.IDLE;
        this.pendingFloors.clear();
        this.doorOpen = true;
    }

    public void addDestination(int floor)
    {
        if (state == ElevatorState.UNDER_MAINTENANCE)
    {
            System.out.println("  Elevator " + id + " is under maintenance. Cannot accept requests.");
            return;
        }
        if (!pendingFloors.contains(floor))
    {
            pendingFloors.add(floor);
        }
    }

    public void processNextStop()
    {
        if (state == ElevatorState.UNDER_MAINTENANCE)
    {
            return;
        }
        if (isOverloaded())
    {
            System.out.println("  Elevator " + id + ": overloaded. Resolve weight before moving.");
            return;
        }
        if (pendingFloors.isEmpty())
    {
            state = ElevatorState.IDLE;
            return;
        }

        int targetFloor = pendingFloors.poll();
        moveTo(targetFloor);
    }

    public void moveTo(int targetFloor)
    {
        if (state == ElevatorState.UNDER_MAINTENANCE)
    {
            System.out.println("  Elevator " + id + " is under maintenance. Cannot move.");
            return;
        }
        if (isOverloaded())
    {
            System.out.println("  Elevator " + id + ": overloaded. Cannot move.");
            return;
        }
        if (doorOpen)
    {
            closeDoor();
            if (doorOpen)
    {
                return;
            }
        }

        if (targetFloor > currentFloor)
    {
            state = ElevatorState.MOVING_UP;
        } else if (targetFloor < currentFloor)
    {
            state = ElevatorState.MOVING_DOWN;
        }

        System.out.println("  Elevator " + id + ": " + state + " from floor " + currentFloor + " to floor " + targetFloor + ".");
        currentFloor = targetFloor;
        state = ElevatorState.IDLE;
        openDoor();
    }

    public boolean hasPendingStops()
    {
        return !pendingFloors.isEmpty();
    }

    @Override
    public String toString()
    {
        return "Elevator " + id + " [floor=" + currentFloor + ", state=" + state
                + ", weight=" + currentWeight + "/" + weightLimit + "kg]";
    }
}
