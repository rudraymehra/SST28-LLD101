package com.example.elevator;

public class App {
    public static void main(String[] args)
    {
        System.out.println("=== Elevator System Demo ===");
        System.out.println();

        ElevatorSystem system = new ElevatorSystem(10, 3, 500.0, new ShortestSeekStrategy());
        system.status();

        System.out.println();
        System.out.println("1. Person at floor 0 wants to go UP to floor 5");
        ElevatorCar car1 = system.requestElevator(0, Direction.UP);
    
    if (car1 != null)
    {
            car1.addPassengerWeight(75);
            car1.closeDoor();
            system.pressFloorButton(car1, 5);
            car1.removePassengerWeight(75);
            car1.closeDoor();
        }

        System.out.println();


        System.out.println("2. Person at floor 7 wants to go DOWN to floor 2");
        ElevatorCar car2 = system.requestElevator(7, Direction.DOWN);
        if (car2 != null)
    {
            car2.addPassengerWeight(80);
            car2.closeDoor();
            system.pressFloorButton(car2, 2);
            car2.removePassengerWeight(80);
            car2.closeDoor();
        }

        System.out.println();
        System.out.println("3. Two people press buttons at the same time");
        System.out.println("   Moving elevator 3 to floor 9 first so it is nearest to floor 8");
        system.getCars().get(2).moveTo(9);
        system.getCars().get(2).closeDoor();
        System.out.println("   Person A at floor 3 wants UP, Person B at floor 8 wants DOWN");
        ElevatorCar carA = system.requestElevator(3, Direction.UP);
        ElevatorCar carB = system.requestElevator(8, Direction.DOWN);
        if (carA != null && carB != null)
    {
            System.out.println("   Elevator " + carA.getId() + " serves floor 3, Elevator " + carB.getId() + " serves floor 8.");
            carA.closeDoor();
            carB.closeDoor();
        }

        system.status();

        System.out.println();
        System.out.println("4. Weight limit exceeded scenario");
        ElevatorCar carW = system.requestElevator(0, Direction.UP);
        if (carW != null)
    {
            carW.addPassengerWeight(200);
            carW.addPassengerWeight(200);
            carW.addPassengerWeight(150);
            System.out.println("   Trying to move while overloaded...");
            system.pressFloorButton(carW, 4);
            System.out.println("   Removing excess weight...");
            carW.removePassengerWeight(150);
            carW.closeDoor();
            system.pressFloorButton(carW, 4);
            carW.removePassengerWeight(200);
            carW.removePassengerWeight(200);
            carW.closeDoor();
        }

        System.out.println();
        System.out.println("5. Maintenance mode");
        system.setCarMaintenance(2);
        System.out.println("   Requesting elevator at floor 5 (car 2 should be skipped)...");
        ElevatorCar carM = system.requestElevator(5, Direction.UP);
        if (carM != null)
    {
            System.out.println("   Got Elevator " + carM.getId() + " (not 2).");
            carM.closeDoor();
        }
        system.clearCarMaintenance(2);
        system.status();

        System.out.println();
        System.out.println("6. Top floor can only go DOWN, ground floor can only go UP");
        System.out.println("   Pressing UP at top floor (9):");
        system.requestElevator(9, Direction.UP);
        System.out.println("   Pressing DOWN at ground floor (0):");
        system.requestElevator(0, Direction.DOWN);

        System.out.println();
        System.out.println("7. Emergency alarm");
        ElevatorCar carE1 = system.requestElevator(0, Direction.UP);
        if (carE1 != null)
    {
            carE1.closeDoor();
            carE1.addDestination(6);
        }
        system.triggerEmergency();
        system.status();

        System.out.println();
        System.out.println("8. Adding a new floor dynamically");
        system.addFloor();
        System.out.println("   Requesting elevator to new top floor...");
        ElevatorCar carNew = system.requestElevator(0, Direction.UP);
        if (carNew != null)
    {
            carNew.addPassengerWeight(70);
            carNew.closeDoor();
            system.pressFloorButton(carNew, system.getFloors().size() - 1);
            carNew.removePassengerWeight(70);
            carNew.closeDoor();
        }

        System.out.println();
        System.out.println("9. Switching dispatch strategy to FCFS");
        system.setStrategy(new FcfsStrategy());
        ElevatorCar carF = system.requestElevator(3, Direction.UP);
        if (carF != null)
    {
            System.out.println("   FCFS picked Elevator " + carF.getId() + ".");
            carF.closeDoor();
        }

        System.out.println();
        system.status();
        System.out.println("=== Demo Complete ===");
    }
}
