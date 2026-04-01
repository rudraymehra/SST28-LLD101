# Elevator System

## Class Diagram

```
+---------------+          +------------------+
|  Direction    |          |  ElevatorState   |
|  (enum)       |          |  (enum)          |
|---------------|          |------------------|
|  UP           |          |  IDLE            |
|  DOWN         |          |  MOVING_UP       |
+---------------+          |  MOVING_DOWN     |
                           |  UNDER_MAINTENANCE|
                           +------------------+

+---------------+          +------------------+
|  FloorPanel   |          |  Request         |
|---------------|          |------------------|
| - hasUpButton |          | - floor: int     |
| - hasDownButton|         | - direction: Dir |
|---------------|          |------------------|
| + hasUpButton()|         | + getFloor()     |
| + hasDownButton()|       | + getDirection() |
+---------------+          +------------------+

+---------------------+
|  Floor              |
|---------------------|
| - floorNumber: int  |
| - panel: FloorPanel |
|---------------------|
| + canRequest(dir)   |
+---------------------+

+-------------------------------------+
|  ElevatorCar                        |
|-------------------------------------|
| - id: int                           |
| - weightLimit: double               |
| - currentFloor: int                 |
| - currentWeight: double             |
| - state: ElevatorState              |
| - doorOpen: boolean                 |
| - pendingFloors: Queue<Integer>     |
|-------------------------------------|
| + moveTo(floor)                     |
| + openDoor() / closeDoor()         |
| + addPassengerWeight(w)             |
| + removePassengerWeight(w)          |
| + triggerAlarm()                    |
| + setUnderMaintenance()             |
| + clearMaintenance()                |
| + addDestination(floor)             |
| + processNextStop()                 |
| + isAvailable() / isIdle()         |
| + isOverloaded()                    |
+-------------------------------------+

+-------------------------------+       +---------------------------+
|  <<interface>>                |       |  FcfsStrategy             |
|  DispatchStrategy             |<------+---------------------------|
|-------------------------------|       | + selectElevator(req,cars)|
| + selectElevator(req, cars)   |       +---------------------------+
+-------------------------------+
        ^                               +---------------------------+
        |                               |  ShortestSeekStrategy     |
        +-------------------------------+---------------------------|
                                        | + selectElevator(req,cars)|
                                        +---------------------------+

+---------------------------------------+
|  ElevatorSystem                       |
|---------------------------------------|
| - floors: List<Floor>                 |
| - cars: List<ElevatorCar>             |
| - strategy: DispatchStrategy          |
|---------------------------------------|
| + requestElevator(floor, direction)   |
| + pressFloorButton(car, targetFloor)  |
| + triggerEmergency()                  |
| + setCarMaintenance(carId)            |
| + clearCarMaintenance(carId)          |
| + addFloor()                          |
| + setStrategy(strategy)               |
| + status()                            |
+---------------------------------------+
```

## Design

- **ElevatorCar** manages its own state (floor, weight, door, pending stops). It refuses to move when overloaded or under maintenance and keeps the door open with an alarm when the weight limit is exceeded.
- **Floor** holds a **FloorPanel** that exposes only valid buttons (ground floor has no DOWN, top floor has no UP).
- **DispatchStrategy** is a pluggable interface. **FcfsStrategy** picks the first idle car. **ShortestSeekStrategy** picks the nearest car to the requesting floor, preferring idle cars on ties.
- **ElevatorSystem** ties everything together: validates floor requests against panels, dispatches using the current strategy, handles emergency alarms (all cars stop), maintenance toggling, and dynamic floor addition.

## Build and Run

```bash
cd elevator-system/src
javac com/example/elevator/*.java
java com.example.elevator.App
```
