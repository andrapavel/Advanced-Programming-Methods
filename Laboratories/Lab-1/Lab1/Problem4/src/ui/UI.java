package ui;

import service.Service;
import utils.Identifiable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Scanner;

import static service.Service.isIDUnique;

public class UI
{
    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_RED = "\u001B[31m";

    private final Service serv;

    public UI(Service serv)
    {
        this.serv = serv;
    }

    public void listPatients()
    {
        ArrayList<Identifiable> appointments = this.serv.getAll();

        if (appointments.isEmpty())
        {
            System.out.println("The list is empty. No patients found.");
        }
        else
        {
            for (Identifiable p : appointments)
            {
                System.out.println(p);
            }
        }
    }

    public void printMenu()
    {
        System.out.println("1 - Print the menu.");
        System.out.println("2 - List all the patients.");
        System.out.println("3 - Add patient.");
        System.out.println("4 - Delete patient.");
        System.out.println("5 - Change patient.");
        System.out.println("0 - Exit");
    }

    public void run()
    {
        printMenu();

        while (true)
        {
            System.out.print("Please input your option: ");
            Scanner scan = new Scanner(System.in);

            int choice = scan.nextInt();
            scan.nextLine(); // Consume the newline character

            if (choice == 0 || choice == 1 || choice == 2 || choice == 3 || choice == 4 || choice == 5)
            {
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
                    {
                        int ID = 0;
                        boolean validID = false;
                        ArrayList<Integer> existingIDs = serv.getAllIDs();

                        while (!validID)
                        {
                            System.out.print("Enter the patient's ID: ");
                            if (scan.hasNextInt())
                            {
                                ID = scan.nextInt();
                                if (ID != 0)
                                {
                                    // Check for uniqueness
                                    boolean isUnique = isIDUnique(ID, existingIDs);

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
                                    System.out.println("ID cannot be 0. Please enter a valid ID.");
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

                        String illness = "";

                        while (illness.isEmpty())
                        {
                            System.out.print("Enter the patient's illness: ");
                            if (scan.hasNextLine())
                            {
                                illness = scan.nextLine();
                            }

                            if (illness.isEmpty())
                            {
                                System.out.println("Illness cannot be empty. Please enter a valid illness.");
                            }
                        }

                        Date dateOfAppointment = null;
                        boolean validDate = false;

                        while (!validDate)
                        {
                            System.out.print("Enter the date of appointment (dd.MM.yyyy): ");
                            String dateInput = scan.next();
                            try
                            {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                                dateOfAppointment = new Date(dateFormat.parse(dateInput).getTime());
                                validDate = true;
                            }
                            catch (java.text.ParseException e)
                            {
                                System.out.println("Invalid date format. Please enter a date in the format dd.MM.yyyy.");
                            }
                        }

                        serv.addPatient(ID, name, age, illness, dateOfAppointment);
                        break;
                    }
                    case 4:
                    {
                        int ID;
                        boolean validID = false;
                        ArrayList<Integer> existingIDs = serv.getAllIDs();

                        while (!validID)
                        {
                            System.out.print("Enter the patient's ID: ");
                            if (scan.hasNextInt())
                            {
                                ID = scan.nextInt();
                                if (ID != 0)
                                {
                                    if (existingIDs.contains(ID))
                                    {
                                        // The ID exists
                                        serv.deletePatient(ID);
                                        System.out.println("Patient with ID " + ID + " has been deleted.");
                                        validID = true;
                                    }
                                    else
                                    {
                                        // The ID doesn't exist
                                        System.out.println("Patient with ID " + ID + " was not found. Please enter a valid ID.");
                                    }
                                }
                                else
                                {
                                    // The ID is equal to 0
                                    System.out.println("The ID should not be equal to 0. Please enter a valid ID.");
                                }
                            }
                            else
                            {
                                System.out.println("Invalid ID format. Please enter a valid integer ID.");
                                scan.next(); // Consume the invalid input
                            }
                        }
                        break;
                    }
                    case 5:
                    {
                        int ID = 0;
                        boolean validID = false;
                        ArrayList<Integer> existingIDs = serv.getAllIDs();

                        while (!validID)
                        {
                            System.out.print("Enter the patient's ID: ");
                            if (scan.hasNextInt())
                            {
                                ID = scan.nextInt();
                                if (ID != 0)
                                {
                                    if (existingIDs.contains(ID))
                                    {
                                        // The ID exists
                                        validID = true;
                                    }
                                    else
                                    {
                                        // The ID doesn't exist
                                        System.out.println("Patient with ID " + ID + " was not found. Please enter a valid ID.");
                                    }
                                }
                                else
                                {
                                    // The ID is equal to 0
                                    System.out.println("The ID should not be equal to 0. Please enter a valid ID.");
                                }
                            }
                            else
                            {
                                System.out.println("Invalid ID format. Please enter a valid integer ID.");
                                scan.next(); // Consume the invalid input
                            }
                        }

                        Date newAppointmentDate = null;
                        boolean validDate = false;

                        while (!validDate)
                        {
                            System.out.print("Enter the date of appointment (dd.MM.yyyy): ");
                            String dateInput = scan.next();
                            try
                            {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                                newAppointmentDate = new Date(dateFormat.parse(dateInput).getTime());
                                validDate = true;
                            }
                            catch (java.text.ParseException e)
                            {
                                System.out.println("Invalid date format. Please enter a date in the format dd.MM.yyyy.");
                            }
                        }

                        serv.changeAppointment(ID, newAppointmentDate);
                        System.out.println("The patient's date for the appointment has been updated.");
                        break;
                    }
                    default: break;
                }
            }
            else
            {
                System.out.print(ANSI_RED + "Invalid option. " + ANSI_RESET);
            }
        }
    }

}
