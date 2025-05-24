import java.util.*;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePrice;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePrice) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePrice = basePrice;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePrice * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }
}

public class rental_car{
    private static List<Car> cars = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Rental> rentals = new ArrayList<>();

    public static void main(String[] args) {
        // Add sample cars
        cars.add(new Car("CAR001", "Toyota", "Camry", 50));
        cars.add(new Car("CAR002", "Honda", "Civic", 45));
        cars.add(new Car("CAR003", "Ford", "Focus", 40));

        menu();
    }

    public static void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n****** Car Rental System ******");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine();

            if (input.equals("1")) {
                rentCarMenu(scanner);
            } else if (input.equals("2")) {
                returnCarMenu(scanner);
            } else if (input.equals("3")) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }

    public static void rentCarMenu(Scanner scanner) {
        System.out.println("== Rent a Car ==");

        System.out.print("Enter your name: ");
        String customerName = scanner.nextLine();

        System.out.println("Available Cars:");
        for (Car car : cars) {
            if (car.isAvailable()) {
                System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
            }
        }

        System.out.print("Enter the car ID you want to rent: ");
        String carId = scanner.nextLine();

        System.out.print("Enter number of rental days: ");
        int days;
        try {
            days = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return;
        }

        Car selectedCar = null;
        for (Car car : cars) {
            if (car.getCarId().equalsIgnoreCase(carId) && car.isAvailable()) {
                selectedCar = car;
                break;
            }
        }

        if (selectedCar == null) {
            System.out.println("Invalid car ID or car not available.");
            return;
        }

        String customerId = "CUS" + (customers.size() + 1);
        Customer customer = new Customer(customerId, customerName);
        customers.add(customer);

        double totalPrice = selectedCar.calculatePrice(days);

        System.out.println("Rental Summary:");
        System.out.println("Customer ID: " + customer.getCustomerId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
        System.out.println("Days: " + days);
        System.out.printf("Total Price: $%.2f\n", totalPrice);

        System.out.print("Confirm rental (Y/N)? ");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("Y")) {
            selectedCar.rent();
            rentals.add(new Rental(selectedCar, customer, days));
            System.out.println("Car rented successfully!");
        } else {
            System.out.println("Rental canceled.");
        }
    }

    public static void returnCarMenu(Scanner scanner) {
        System.out.println("== Return a Car ==");

        System.out.print("Enter car ID to return: ");
        String carId = scanner.nextLine();

        Car carToReturn = null;
        for (Car car : cars) {
            if (car.getCarId().equalsIgnoreCase(carId) && !car.isAvailable()) {
                carToReturn = car;
                break;
            }
        }

        if (carToReturn == null) {
            System.out.println("Invalid car ID or car is not currently rented.");
            return;
        }

        Rental rentalRecord = null;
        for (Rental rental : rentals) {
            if (rental.getCar().equals(carToReturn)) {
                rentalRecord = rental;
                break;
            }
        }

        if (rentalRecord != null) {
            carToReturn.returnCar();
            System.out.println("Car returned successfully by " + rentalRecord.getCustomer().getName());
        } else {
            System.out.println("No rental record found for this car.");
        }
    }
}
