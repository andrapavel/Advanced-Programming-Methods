package ui;

import service.Service;
import domain.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class UI
{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    private final Service serv;
    private final Scanner scan;

    public UI(Service serv)
    {
        this.serv = serv;
        this.scan = new Scanner(System.in);
    }

    // PATIENT
    public void listPatients()
    {
        ArrayList<Patient> patients = this.serv.getAllPatients();

        if (patients.isEmpty())
        {
            System.out.println("The list is empty. No patient found.");
        }
        else
        {
            for (Patient p : patients)
            {
                System.out.println(p);
            }
        }
    }

    public void addPatient() // PATIENT
    {
        int ID = 0;
        boolean validID = false;

        while (!validID)
        {
            System.out.print("Enter the patient's ID: ");
            if (scan.hasNextInt())
            {
                ID = scan.nextInt();
                if (ID != 0)
                {
                    boolean isUnique = serv.checkIDUniqueness(ID);
                    if (isUnique)
                    {
                        validID = true;
                        scan.nextLine();
                    }
                    else
                    {
                        System.out.println("ID is not unique. Please enter a different ID.");
                        scan.nextLine();
                    }
                }
                else
                {
                    System.out.println("ID cannot be 0. Please enter a valid integer ID.");
                    scan.nextLine();
                }
            }
            else
            {
                System.out.println("Invalid ID format. Please enter a valid integer ID.");
                scan.nextLine(); // Consume the invalid input
            }
        }

        String name = "";

        while (name.isEmpty())
        {
            System.out.print("Enter the patient's name: ");
            if (scan.hasNextLine())
            {
                name = scan.nextLine(); // Read the entire line
            }

            if (name.isEmpty())
            {
                System.out.println("Name cannot be empty. Please enter a valid name.");
            }
        }

        int age = 0;
        boolean validAge = false;

        while (!validAge)
        {
            System.out.print("Enter the patient's age: ");
            if (scan.hasNextInt())
            {
                age = scan.nextInt();
                validAge = true;
                scan.nextLine();
            }
            else
            {
                System.out.println("Invalid age. Please enter a valid integer age.");
                scan.nextLine(); // Consume the invalid input
            }
        }

        String dentalCondition = "";

        while (dentalCondition.isEmpty())
        {
            System.out.print("Enter the patient's dental condition: ");
            if (scan.hasNextLine())
            {
                dentalCondition = scan.nextLine();
            }

            if (dentalCondition.isEmpty())
            {
                System.out.println("Dental condition cannot be empty. Please enter a valid dental condition.");
            }
        }

        serv.addPatient(ID, name, age, dentalCondition);
    }

    public void deletePatient() // DELETE PATIENT
    {
        int ID;

        System.out.print("Enter the patient's ID: ");
        if (scan.hasNextInt())
        {
            ID = scan.nextInt();

            // Check if the patient with the given ID exists
            if (serv.patientExistence(ID))
            {
                serv.deletePatient(ID);
                System.out.println("Patient with ID " + ID + " has been deleted.");
            }
            else
            {
                System.out.println("Patient with ID " + ID + " was not found. Provide a valid ID.");
            }
        }
        else
        {
            System.out.println("Invalid ID format. Please enter a valid integer ID.");
            scan.next(); // Consume the invalid input
        }
    }

    public void updatePatient() // UPDATE PATIENT
    {
        System.out.print("Enter the patient's ID: ");
        if (scan.hasNextInt())
        {
            int patientID = scan.nextInt();
            scan.nextLine(); // Consume the newline character

            System.out.print("Enter the new name: ");
            String newName = scan.nextLine();

            System.out.print("Enter the new dental condition: ");
            String newDentalCondition = scan.nextLine();

            serv.updatePatient(patientID, newName, newDentalCondition);
        }
        else
        {
            System.out.println("Invalid ID format. Please enter a valid integer ID.");
            scan.next(); // Consume the invalid input
        }
    }

    public void filterPatientsByName()
    {

    }

    // APPOINTMENT

    public void listAppointments()
    {
        ArrayList<Appointment> appointments = this.serv.getAllAppointments();

        if (appointments.isEmpty())
        {
            System.out.println("The list is empty. No appointment found.");
        }
        else
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            for (Appointment a : appointments)
            {
                Date appointmentDateTime = a.getDateTime();
                String formattedDateTime = dateFormat.format(appointmentDateTime);
                System.out.println("Appointment ID: " + a.getID() + ", DateTime: " + formattedDateTime + ", Patient ID: " + a.getPatientID());
            }
        }
    }


    public void addAppointment() // ADD APPOINTMENT
    {
        int appointmentID = 0;
        int patientID = 0;

        boolean validInput = false;

        while (!validInput)
        {
            System.out.print("Enter the patient's ID: ");
            if (scan.hasNextInt())
            {
                patientID = scan.nextInt();
                scan.nextLine(); // Consume the newline character

                // Check if the patient exists
                if (!serv.patientExistence(patientID))
                {
                    System.out.println("Patient with ID " + patientID + " does not exist.");
                }
                else
                {
                    validInput = true; // Patient ID is valid, exit the loop
                }
            }
            else
            {
                System.out.println("Invalid patient ID format. Please enter a valid integer ID.");
                scan.next(); // Consume the invalid input
            }
        }

        validInput = false; // Reset the flag for input validation

        while (!validInput)
        {
            System.out.print("Enter the appointment ID: ");
            if (scan.hasNextInt())
            {
                appointmentID = scan.nextInt();
                scan.nextLine(); // Consume the newline character

                if (appointmentID == 0)
                {
                    System.out.println("Appointment ID cannot be 0. Please enter a valid integer ID.");
                }
                else if (serv.appointmentExistence(appointmentID))
                {
                    System.out.println("Appointment with ID " + appointmentID + " already exists.");
                }
                else
                {
                    validInput = true; // Appointment ID is valid, exit the loop
                }
            }
            else
            {
                System.out.println("Invalid appointment ID format. Please enter a valid integer ID.");
                scan.next(); // Consume the invalid input
            }
        }

        validInput = false; // Reset the flag for input validation

        while (!validInput)
        {
            System.out.print("Enter the appointment date and time (dd.MM.yyyy HH:mm): ");
            String dateTimeInput = scan.nextLine();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            try
            {
                java.util.Date utilDate = dateFormat.parse(dateTimeInput);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                // Check if an appointment already exists for the same patient at the same date and time
                if (serv.appForPatientExists(patientID, sqlDate))
                {
                    System.out.println("An appointment for the same patient at the same date and time already exists.");
                }
                else
                {
                    validInput = true; // Input is valid, exit the loop
                    serv.addAppointment(appointmentID, sqlDate, patientID);
                    System.out.println("Appointment added successfully.");
                }
            }
            catch (ParseException e)
            {
                System.out.println("Invalid date and time format. Please enter in the format dd.MM.yyyy HH:mm.");
            }
        }
    }

    public void deleteAppointment() // DELETE APPOINTMENT
    {
        System.out.print("Enter the appointment's ID: ");

        int ID;

        if (scan.hasNextInt())
        {
            ID = scan.nextInt();
            scan.nextLine(); // Consume the newline character

            if (serv.appointmentExistence(ID))
            {
                serv.deleteAppointment(ID);
                System.out.println("Appointment with ID " + ID + " has been deleted.");
            }
            else
            {
                System.out.println("Appointment with ID " + ID + " does not exist.");
            }
        }
        else
        {
            System.out.println("Invalid ID format. Please enter a valid integer ID.");
            scan.next(); // Consume the invalid input
        }
    }

    public void updateAppointment() // UPDATE APPOINTMENT
    {
        int patientID;
        int appointmentID = 0;

        boolean validPatientID = false;

        while (!validPatientID)
        {
            System.out.print("Enter the patient's ID: ");
            if (scan.hasNextInt())
            {
                patientID = scan.nextInt();
                scan.nextLine(); // Consume the newline character

                // Check if the patient exists
                if (serv.patientExistence(patientID))
                {
                    validPatientID = true; // Patient ID is valid, exit the loop
                }
                else
                {
                    System.out.println("Patient with ID " + patientID + " does not exist.");
                }
            }
            else
            {
                System.out.println("Invalid patient ID format. Please enter a valid integer ID.");
                scan.next(); // Consume the invalid input
            }
        }

        boolean validAppointmentID = false;

        while (!validAppointmentID)
        {
            System.out.print("Enter the appointment's ID: ");
            if (scan.hasNextInt())
            {
                appointmentID = scan.nextInt();
                scan.nextLine(); // Consume the newline character

                // Check if the appointment exists
                if (serv.appointmentExistence(appointmentID))
                {
                    validAppointmentID = true; // Appointment ID is valid, exit the loop
                }
                else
                {
                    System.out.println("Appointment with ID " + appointmentID + " does not exist.");
                }
            }
            else
            {
                System.out.println("Invalid appointment ID format. Please enter a valid integer ID.");
                scan.next(); // Consume the invalid input
            }
        }

        // Prompt for the new date and time
        System.out.print("Enter the new date and time (dd.MM.yyyy HH:mm): ");
        String dateTimeInput = scan.nextLine();

        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            Date dateTime = new Date(dateFormat.parse(dateTimeInput).getTime());
            serv.updateAppointment(appointmentID, dateTime);
            System.out.println("Appointment with ID " + appointmentID + " has been updated.");
        }
        catch (ParseException e)
        {
            System.out.println("Invalid date and time format. Please enter in the format dd.MM.yyyy HH:mm.");
        }
    }

    public void filterAppointmentsByMonth()
    {

    }

    // PATIENT'S APPOINTMENTS
    public void listPatientAppointments()
    {
        System.out.print("Enter the patient's ID: ");
        int patientID = scan.nextInt();
        scan.nextLine(); // Consume the newline character

        // Call the service method to get the list of patient's appointments
        List<Appointment> patientAppointments = serv.getPatientAppointments(patientID);

        if (patientAppointments.isEmpty())
        {
            System.out.println("No appointments found for the patient with ID " + patientID);
        }
        else
        {
            System.out.println("Appointments for the patient with ID " + patientID + ":");
            for (Appointment appointment : patientAppointments) {
                System.out.println(appointment);
            }
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
        System.out.println("6 - Filter patients by name.");
        System.out.println(" ");
        System.out.println("-----APPOINTMENT MENU-----");
        System.out.println("7 - List all the appointments");
        System.out.println("8 - Add appointment.");
        System.out.println("9 - Delete appointment.");
        System.out.println("10 - Update appointment.");
        System.out.println("11 - Filter appointments by month.");
        System.out.println(" ");
        System.out.println("-----PATIENT'S APPOINTMENTS-----");
        System.out.println("12 - List all the appointments of a patient.");
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

            if (scan.hasNextInt())
            {
                choice = scan.nextInt();
                scan.nextLine(); // Consume the newline character
            }
            else
            {
                System.out.println(ANSI_RED + "Invalid option. " + ANSI_RESET);
                scan.nextLine(); // Consume the invalid input
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
                    filterPatientsByName();
                    break;
                case 7:
                    listAppointments();
                    break;
                case 8:
                    addAppointment();
                    break;
                case 9:
                    deleteAppointment();
                    break;
                case 10:
                    updateAppointment();
                    break;
                case 11:
                    filterAppointmentsByMonth();
                    break;
                case 12:
                    listPatientAppointments();
                    break;


                default:
                    System.out.println(ANSI_RED + "Invalid option. " + ANSI_RESET);
                    break;
            }
        }
    }
}
