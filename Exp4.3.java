Experiment 4.3: Ticket Booking System

This program simulates a ticket booking system where multiple users (threads) try to book seats at the same time. The key challenges addressed are:

1) Avoiding Double Booking → Using synchronized methods to ensure no two users book the same seat.
2) Prioritizing VIP Customers → Using thread priorities so VIP users' bookings are processed before regular users.

📌 Core Concepts Used
️1 Synchronized Booking Method
The method bookSeat() is marked as synchronized, ensuring that only one thread can access it at a time.
This prevents race conditions, where two threads might try to book the same seat simultaneously.
  
️2 Thread Priorities for VIP Customers
Threads representing VIP users are assigned Thread.MAX_PRIORITY so they execute first.
Regular users have Thread.NORM_PRIORITY or Thread.MIN_PRIORITY, making them process later.

3 Handling Multiple Users
Each user trying to book a seat is represented by a thread.
Users can select a seat, and if it’s already booked, they receive an error message.


Step-by-Step Execution
1 Initialize the TicketBookingSystem → Allows booking of N seats.
2 Create Multiple Booking Threads → Each user (VIP or Regular) is assigned a thread.
3 Start All Threads → Threads compete for booking, with VIPs processed first.
4 Ensure No Double Booking → synchronized method prevents duplicate seat allocation.
5 Threads Finish Execution & Display Booking Status.


🔹 Why Use Synchronization?
Without synchronized, two threads might book the same seat simultaneously, causing double booking issues. Using synchronized, only one thread at a time can modify the seat booking data.

🔹 Why Use Thread Priorities?
Setting higher priority for VIP users ensures their bookings are processed first, simulating real-world priority-based bookings.

Test Cases

Test Case 1: No Seats Available Initially
Input:
System starts with 5 seats.
No users attempt to book.
Expected Output:
No bookings yet.

Test Case 2: Successful Booking
Input:
Anish (VIP) books Seat 1.
Bobby (Regular) books Seat 2.
Charlie (VIP) books Seat 3.
Expected Output:
Anish (VIP) booked seat 1
Bobby (Regular) booked seat 2
Charlie (VIP) booked seat 3

Test Case 3: Thread Priorities (VIP First)
Input:
Bobby (Regular) books Seat 4 (low priority).
Anish (VIP) books Seat 4 (high priority).
Expected Output:
Anish (VIP) booked seat 4
Bobby (Regular): Seat 4 is already booked!

Test Case 4: Preventing Double Booking
Input:
Anish (VIP) books Seat 1.
Bobby (Regular) tries to book Seat 1 again.
Expected Output:
Anish (VIP) booked seat 1
Bobby (Regular): Seat 1 is already booked!

Test Case 5: Booking After All Seats Are Taken
Input:
All 5 seats are booked.
A new user (Regular) tries to book Seat 3.
Expected Output:
Error: Seat 3 is already booked!

Test Case 6: Invalid Seat Selection
Input:
User tries to book Seat 0 (out of range).
User tries to book Seat 6 (beyond available seats).
Expected Output:
Invalid seat number!

Test Case 7: Simultaneous Bookings (Concurrency Test)
Input:
10 users try booking at the same time for 5 seats.
Expected Output:
5 users successfully book seats.
5 users receive error messages for already booked seats.


  CODE:
  import java.util.Scanner;

class TicketBookingSystem {
    private boolean[] seats;

    public TicketBookingSystem(int totalSeats) {
        seats = new boolean[totalSeats];
    }

    // Synchronized method to prevent double booking
    public synchronized String bookSeat(int seatNumber, String user, boolean isVIP) {
        if (seatNumber < 1 || seatNumber > seats.length) {
            return "Invalid seat number!";
        }
        if (seats[seatNumber - 1]) {
            return user + ": Seat " + seatNumber + " is already booked!";
        }
        
        seats[seatNumber - 1] = true;
        return user + " (" + (isVIP ? "VIP" : "Regular") + ") booked seat " + seatNumber;
    }

    // Display available seats
    public synchronized void displaySeats() {
        System.out.println("\nCurrent Seat Status:");
        for (int i = 0; i < seats.length; i++) {
            System.out.println("Seat " + (i + 1) + ": " + (seats[i] ? "Booked" : "Available"));
        }
    }
}

// Thread class for users booking seats
class BookingThread extends Thread {
    private TicketBookingSystem system;
    private int seatNumber;
    private String user;
    private boolean isVIP;

    public BookingThread(TicketBookingSystem system, int seatNumber, String user, boolean isVIP) {
        this.system = system;
        this.seatNumber = seatNumber;
        this.user = user;
        this.isVIP = isVIP;
        setPriority(isVIP ? Thread.MAX_PRIORITY : Thread.NORM_PRIORITY); // VIP gets high priority
    }

    @Override
    public void run() {
        System.out.println(system.bookSeat(seatNumber, user, isVIP));
    }
}

// Main class with switch-case menu
public class TicketBookingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of seats available: ");
        int totalSeats = scanner.nextInt();

        TicketBookingSystem system = new TicketBookingSystem(totalSeats);

        while (true) {
            System.out.println("\n===== Ticket Booking System =====");
            System.out.println("1. Book a Seat");
            System.out.println("2. Display Seat Status");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1: // Book a seat
                    System.out.print("Enter your name: ");
                    String user = scanner.next();
                    System.out.print("Enter seat number to book: ");
                    int seatNumber = scanner.nextInt();
                    System.out.print("Are you a VIP? (true/false): ");
                    boolean isVIP = scanner.nextBoolean();
                    
                    BookingThread booking = new BookingThread(system, seatNumber, user, isVIP);
                    booking.start();
                    
                    try {
                        booking.join(); // Ensure booking completes before moving on
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;

                case 2: // Display seats
                    system.displaySeats();
                    break;

                case 3: // Exit
                    System.out.println("Exiting Ticket Booking System. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        }
    }
}



