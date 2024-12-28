import java.util.*;

class Appointment {
    String patientName;
    String timeSlot;
    boolean isUrgent;

    public Appointment(String patientName, String timeSlot, boolean isUrgent) {
        this.patientName = patientName;
        this.timeSlot = timeSlot;
        this.isUrgent = isUrgent;
    }

    @Override
    public String toString() {
        return "Patient: " + patientName + ", Time: " + timeSlot + (isUrgent ? " (Urgent)" : "");
    }
}

class Doctor {
    String name;
    LinkedList<Appointment> schedule;

    public Doctor(String name) {
        this.name = name;
        this.schedule = new LinkedList<>();
    }

    public void addAppointment(Appointment appointment) {
        schedule.add(appointment);
    }

    public void showSchedule() {
        System.out.println("Schedule for Dr. " + name + ":");
        for (Appointment appointment : schedule) {
            System.out.println(appointment);
        }
    }
}

class AppointmentSystem {
    List<Doctor> doctors;
    PriorityQueue<Appointment> appointmentQueue;

    public AppointmentSystem() {
        doctors = new ArrayList<>();
        appointmentQueue = new PriorityQueue<>(
                (a1, a2) -> Boolean.compare(a2.isUrgent, a1.isUrgent) // Urgent appointments first
        );
    }

    public void addDoctor(String doctorName) {
        doctors.add(new Doctor(doctorName));
    }

    public Doctor findDoctor(String doctorName) {
        for (Doctor doctor : doctors) {
            if (doctor.name.equals(doctorName)) {
                return doctor;
            }
        }
        return null;
    }

    public void bookAppointment(String doctorName, String patientName, String timeSlot, boolean isUrgent) {
        Doctor doctor = findDoctor(doctorName);
        if (doctor == null) {
            System.out.println("Doctor not found!");
            return;
        }

        Appointment appointment = new Appointment(patientName, timeSlot, isUrgent);
        appointmentQueue.add(appointment);

        while (!appointmentQueue.isEmpty()) {
            Appointment nextAppointment = appointmentQueue.poll();
            doctor.addAppointment(nextAppointment);
        }
    }

    public void showDoctorSchedule(String doctorName) {
        Doctor doctor = findDoctor(doctorName);
        if (doctor != null) {
            doctor.showSchedule();
        } else {
            System.out.println("Doctor not found!");
        }
    }
}

public class DoctorManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AppointmentSystem system = new AppointmentSystem();

        // Add doctors
        system.addDoctor("Dr. Smith");
        system.addDoctor("Dr. Johnson");

        System.out.println("Welcome to the Doctor Management System!");

        while (true) {
            System.out.println("\n1. Book an Appointment");
            System.out.println("2. View Doctor's Schedule");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over

            if (choice == 1) {
                // Book an appointment
                System.out.print("Enter Doctor's name: ");
                String doctorName = scanner.nextLine();

                System.out.print("Enter Patient's name: ");
                String patientName = scanner.nextLine();

                System.out.print("Enter Time Slot: ");
                String timeSlot = scanner.nextLine();

                System.out.print("Is the appointment urgent (true/false): ");
                boolean isUrgent = scanner.nextBoolean();
                scanner.nextLine();  // Consume newline left-over

                system.bookAppointment(doctorName, patientName, timeSlot, isUrgent);
                System.out.println("Appointment booked successfully!");
            } else if (choice == 2) {
                // Show schedule
                System.out.print("Enter Doctor's name to view their schedule: ");
                String doctorName = scanner.nextLine();

                system.showDoctorSchedule(doctorName);
            } else if (choice == 3) {
                // Exit the program
                System.out.println("Thank you for using the Doctor Management System.");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
