package ui;

import domain.Appointment;
import domain.Patient;
import exceptions.ValidationException;
import service.Service;

import java.io.IOException;
import java.util.Scanner;

import static service.Service.getRepositoryType;

public class UI
{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    private Service serv;

    private final Scanner scanner = new Scanner(System.in);

    public UI(Service service) throws IOException
    {
        this.serv = service;
        String repoType = getRepositoryType("D://Faculty of Mathematics and Computer Science//2023-2024//First Semester//Advanced Programming Methods//Labs//Lab5//DentistAppointments//src//settings.properties");
    }

    // PATIENT
    public void listPatients()
    {
        Iterable patients = serv.getAllPatients();

        if (!patients.iterator().hasNext())
        {
            System.out.println("There are no patients.");
            return;
        }

        for (Object p : patients)
        {
            System.out.println(p.toString());
        }
    }

    public void addPatient() // PATIENT
    {
        try
        {
            System.out.println("Please enter the following data:\n");
            System.out.println("Patient's id: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Patient's name: ");
            String name = scanner.nextLine();
            System.out.println("Patient's dental condition: ");
            String disease = scanner.next();
            serv.addPatient(new Patient(id, name, disease));
        }
        catch (ValidationException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deletePatient() // DELETE PATIENT
    {
        try
        {
            System.out.print("Enter the ID of the patient to delete: ");
            int patientID = scanner.nextInt();
            serv.deletePatient(patientID);
            System.out.println("Patient with ID " + patientID + " deleted successfully.");
        }
        catch (ValidationException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updatePatient() // UPDATE PATIENT
    {
        try
        {
            System.out.println("Please enter the ID of the patient to update:");
            int idUp = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Please enter the following data for the updated patient:");
            System.out.println("New Patient's name: ");
            String newName = scanner.nextLine();
            System.out.println("New Patient's dental condition: ");
            String newDisease = scanner.nextLine();
            Patient updatedPatient = new Patient(idUp, newName, newDisease);
            serv.updatePatient(updatedPatient);
        }
        catch (ValidationException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // APPOINTMENT

    public void listAppointments()
    {
        Iterable appointments = serv.getAllAppointments();

        if (!appointments.iterator().hasNext())
        {
            System.out.println("There are no appointments.");
            return;
        }

        for (Object a : appointments)
        {
            System.out.println(a.toString());
        }
    }

    public void addAppointment() // ADD APPOINTMENT
    {
        try
        {
            System.out.println("Please enter the following data:\n");
            System.out.println("Appointment's ID: ");
            int idA = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Appointment's description: ");
            String description = scanner.nextLine();
            System.out.println("Appointment's date: ");
            String date = scanner.next();
            serv.addAppointment(new Appointment(idA, description, date));
        }
        catch (ValidationException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteAppointment() // DELETE APPOINTMENT
    {
        try
        {
            System.out.print("Enter the ID of the appointment to cancel: ");
            int appointmentID = scanner.nextInt();
            serv.deleteAppointment(appointmentID);
            System.out.println("Appointment with ID " + appointmentID + " deleted successfully.");
        }
        catch (ValidationException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateAppointment() // UPDATE APPOINTMENT
    {
        try {
            System.out.println("Please enter de ID of the appointment to update:\n");
            int idApp = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Please enter the following data:\n");
            System.out.println("New Appointment's description: ");
            String newDescription = scanner.nextLine();
            System.out.println("New Appointment's date: ");
            String newDate = scanner.next();
            Appointment updatedAppointment = new Appointment(idApp, newDescription, newDate);
            serv.updateAppointment(idApp, updatedAppointment);
        }
        catch (ValidationException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void printMenu()
    {
        System.out.println("1 - Print the menu.");
        System.out.println(" ");
        System.out.println("-----PATIENT MENU-----");
        System.out.println("2 - List all the patients.");
        System.out.println("3 - Add patient.");
        System.out.println("4 - Delete patient.");
        System.out.println("5 - Update patient.");
        System.out.println(" ");
        System.out.println("-----APPOINTMENT MENU-----");
        System.out.println("6 - List all the appointments");
        System.out.println("7 - Add appointment.");
        System.out.println("8 - Delete appointment.");
        System.out.println("9 - Update appointment.");
        System.out.println(" ");
        System.out.println("0 - Exit");
    }

    public void run()
    {
        printMenu();

        while (true)
        {
            System.out.print("Please input your option: ");
            int choice;

            if (scanner.hasNextInt())
            {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
            }
            else
            {
                System.out.println(ANSI_RED + "Invalid option. " + ANSI_RESET);
                scanner.nextLine(); // Consume the invalid input
                continue;
            }

            switch (choice)
            {
                case 0:
                    return;
                case 1:
                    printMenu();
                    break;
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
                case 6:
                    listAppointments();
                    break;
                case 7:
                    addAppointment();
                    break;
                case 8:
                    deleteAppointment();
                    break;
                case 9:
                    updateAppointment();
                    break;

                default:
                    System.out.println(ANSI_RED + "Invalid option. " + ANSI_RESET);
                    break;
            }
        }
    }
}
