
package ui;

import exceptions.ValidationException;
import service.Service;
import domain.Appointment;
import domain.Patient;

import java.util.Scanner;

public class UI
{
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";

    private final Service service;
    private final Scanner scanner = new Scanner(System.in);

    public UI(Service service)
    {
        this.service = service;
    }

    private void clearScreen()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private int getIntInput(String prompt)
    {
        while (true)
        {
            try
            {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            }
            catch (NumberFormatException ex)
            {
                System.out.println(ANSI_RED + "Invalid input. Please enter a valid integer." + ANSI_RESET);
            }
        }
    }

    private String getStringInput(String prompt)
    {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private void printMenuHeader(String menuName)
    {
        System.out.println("-----" + menuName + "-----");
    }

    private void printMenuFooter()
    {
        System.out.println(" ");
    }


    // Patient-related methods
    @SuppressWarnings("unchecked")
    private void listPatients()
    {
        Iterable<Patient> patients = service.getAllPatients();
        printMenuHeader("PATIENT");
        patients.forEach(System.out::println);
        printMenuFooter();
    }

    @SuppressWarnings("unchecked")
    private void addPatient()
    {
        try
        {
            printMenuHeader("ADD PATIENT");
            int id = getIntInput("Patient's id: ");
            String name = getStringInput("Patient's name: ");
            String disease = getStringInput("Patient's dental condition: ");
            service.addPatient(new Patient(id, name, disease));
            System.out.println("Patient added successfully.");
        }
        catch (ValidationException e)
        {
            System.out.println(ANSI_RED + "Error: " + e.getMessage() + ANSI_RESET);
        }
        printMenuFooter();
    }

    @SuppressWarnings("unchecked")
    public void deletePatient()
    {
        printMenuHeader("DELETE PATIENT");
        int patientID = getIntInput("Patient ID: ");
        try
        {
            service.deletePatient(patientID);
            System.out.println("Patient with ID " + patientID + " deleted successfully.");
        }
        catch (ValidationException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void updatePatient()
    {
        printMenuHeader("UPDATE PATIENT");
        int patientID = getIntInput("Patient ID: ");
        try
        {
            Patient existingPatient = service.getPatientByID(patientID);
            if (existingPatient == null)
            {
                System.out.println("Patient with ID " + patientID + " does not exist.");
                return;
            }

            System.out.println("Existing Patient Data:\n" + existingPatient);

            System.out.println("Enter new data for the patient:");
            String newName = getStringInput("New Patient's name: ");
            String newDisease = getStringInput("New Patient's dental condition: ");

            Patient updatedPatient = new Patient(patientID, newName, newDisease);
            service.updatePatient(updatedPatient);
            System.out.println("Patient with ID " + patientID + " updated successfully.");
        }
        catch (ValidationException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // Appointment-related methods
    @SuppressWarnings("unchecked")
    public void listAppointments()
    {
        Iterable<Appointment> appointments = service.getAllAppointments();
        printMenuHeader("APPOINTMENT");
        appointments.forEach(System.out::println);
        printMenuFooter();
    }

    @SuppressWarnings("unchecked")
    public void addAppointment()
    {
        try
        {
            printMenuHeader("ADD APPOINTMENT");
            int id = getIntInput("Appointment's id: ");
            String description = getStringInput("Appointment's description: ");
            String date = getStringInput("Appointment's date (dd/MM/yyyy): ");
            service.addAppointment(new Appointment(id,description, date));
            System.out.println("Appointment added successfully.");
        }
        catch (ValidationException e)
        {
            System.out.println(ANSI_RED + "Error: " + e.getMessage() + ANSI_RESET);
        }
        printMenuFooter();
    }

    @SuppressWarnings("unchecked")
    public void deleteAppointment()
    {
        printMenuHeader("DELETE APPOINTMENT");
        int appointmentID = getIntInput("Appointment ID: ");
        try
        {
            service.deleteAppointment(appointmentID);
            System.out.println("Appointment with ID " + appointmentID + " deleted successfully.");
        }
        catch (ValidationException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        printMenuFooter();
    }

    @SuppressWarnings("unchecked")
    public void updateAppointment()
    {
        printMenuHeader("UPDATE APPOINTMENT");
        int appointmentID = getIntInput("Appointment ID: ");
        try
        {
            Appointment existingAppointment = service.getAppointmentByID(appointmentID);
            if (existingAppointment == null)
            {
                System.out.println("Appointment with ID " + appointmentID + " does not exist.");
                return;
            }

            System.out.println("Existing Appointment Data:\n" + existingAppointment);

            System.out.println("Enter new data for the appointment:");
            String newDescription = getStringInput("New Appointment's description: ");
            String newDate = getStringInput("New Appointment's date: ");

            Appointment updatedAppointment = new Appointment(appointmentID, newDescription, newDate);
            service.updateAppointment(appointmentID, updatedAppointment);
            System.out.println("Appointment with ID " + appointmentID + " updated successfully.");
        }
        catch (ValidationException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        printMenuFooter();
    }

    // Main menu
    private void printMainMenu()
    {
        System.out.println();
        System.out.println("1 - Print the menu.");
        System.out.println(" ");
        printMenuHeader("MAIN MENU");
        System.out.println("2 - Patient Operations");
        System.out.println("3 - Appointment Operations");
        System.out.println(" ");
        System.out.println("0 - Exit");
    }


    public void run()
    {
        clearScreen();
        printMainMenu();

        while (true)
        {
            int choice = getIntInput("Please input your option: ");

            switch (choice)
            {
                case 0:
                    return;
                case 1:
                    clearScreen();
                    printMainMenu();
                    break;
                case 2:
                    runPatientMenu();
                    break;
                case 3:
                    runAppointmentMenu();
                    break;
                default:
                    System.out.println(ANSI_RED + "Invalid option. " + ANSI_RESET);
                    break;
            }
        }
    }

    // Additional methods for appointment-related menu and operations

    private void runPatientMenu()
    {
        clearScreen();
        printMenuHeader("PATIENT MENU");
        System.out.println("2 - List all patients.");
        System.out.println("3 - Add patient.");
        System.out.println("4 - Delete patient.");
        System.out.println("5 - Update patient.");
        System.out.println(" ");
        System.out.println("0 - Back to Main Menu");

        while (true)
        {
            int choice = getIntInput("Please input your option: ");

            switch (choice)
            {
                case 0:
                    return;
                case 2:
                    listPatients();
                    break;
                case 3:
                    addPatient();
                    break;
                case 4:
                    deletePatient();
                    break;
                case 5:
                    updatePatient();
                    break;
                default:
                    System.out.println(ANSI_RED + "Invalid option. " + ANSI_RESET);
                    break;
            }
        }
    }

    private void runAppointmentMenu()
    {
        clearScreen();
        printMenuHeader("APPOINTMENT MENU");
        System.out.println("2 - List all appointments.");
        System.out.println("3 - Add appointment.");
        System.out.println("4 - Delete appointment.");
        System.out.println("5 - Update appointment.");
        System.out.println(" ");
        System.out.println("0 - Back to Main Menu");

        while (true)
        {
            int choice = getIntInput("Please input your option: ");

            switch (choice)
            {
                case 0:
                    return;
                case 2:
                    listAppointments();
                    break;
                case 3:
                    addAppointment();
                    break;
                case 4:
                    deleteAppointment();
                    break;
                case 5:
                    updateAppointment();
                    break;
                default:
                    System.out.println(ANSI_RED + "Invalid option. " + ANSI_RESET);
                    break;
            }
        }
    }
}
