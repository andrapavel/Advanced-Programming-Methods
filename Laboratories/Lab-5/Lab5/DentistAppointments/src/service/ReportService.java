
package service;

import domain.Appointment;
import domain.Patient;
import repository.IRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.Spliterator;


public class ReportService
{
    private static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy";

    private final IRepository<Patient, Integer> patientRepository;
    private final IRepository<Appointment, Integer> appointmentRepository;

    public ReportService(IRepository<Patient, Integer> patientRepository, IRepository<Appointment, Integer> appointmentRepository)
    {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public void displayReports()
    {
        System.out.println("---------- Reports ----------");
        printPatientsWithDentalCondition("Cavities");
        printPatientsWithInitial('C');
        printAppointmentsAfterDate("12/01/2024");
        printAppointmentDescriptionById(2000);
        printAppointmentsFromDateWithDescription("27/03/2024");
        printPatientsSortedAlphabetically();
        System.out.println("-----------------------------");
    }

    public List<Patient> getPatientsWithDisease(String dentalCondition)
    {
        return toList(toStream(patientRepository.getAll())
                .filter(patient -> patient.getDentalCondition().equalsIgnoreCase(dentalCondition)));
    }

    public List<Patient> getPatientsWithInitial(char initial)
    {
        return toList(toStream(patientRepository.getAll())
                .filter(patient -> Character.toUpperCase(patient.getName().charAt(0)) == Character.toUpperCase(initial)));
    }

    public List<Appointment> getAppointmentsAfterDate(String targetDate)
    {
        return toList(toStream(appointmentRepository.getAll())
                .filter(appointment -> compareDates(appointment.getDate(), targetDate) > 0));
    }

    public Optional<String> getAppointmentDescriptionById(int appointmentID)
    {
        return toStream(appointmentRepository.getAll())
                .filter(appointment -> appointment.getID() == appointmentID)
                .map(Appointment::getDescription)
                .findFirst();
    }

    public List<String> getAppointmentsFromDateWithDescription(String targetDate)
    {
        return toList(toStream(appointmentRepository.getAll())
                .filter(appointment -> appointment.getDate().equals(targetDate))
                .map(appointment -> String.format("ID: %d, Description: %s", appointment.getID(), appointment.getDescription())));
    }

    public List<Patient> getPatientsSortedAlphabetically()
    {
        return toList(toStream(patientRepository.getAll())
                .sorted(Comparator.comparing(Patient::getName)));
    }

    private int compareDates(String date1, String date2)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
        LocalDate localDate1 = LocalDate.parse(date1, formatter);
        LocalDate localDate2 = LocalDate.parse(date2, formatter);
        return localDate1.compareTo(localDate2);
    }

    private <T> List<T> toList(Stream<T> stream)
    {
        return stream.collect(Collectors.toList());
    }

    private <T> Stream<T> toStream(Iterable<T> iterable)
    {
        Spliterator<T> spliterator = iterable.spliterator();
        return StreamSupport.stream(spliterator, false)
                .onClose(() ->
                {
                    try
                    {
                        ((AutoCloseable) iterable).close();
                    }
                    catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                });
    }

    private void printPatientsWithDentalCondition(String dentalCondition)
    {
        System.out.println("Patients with dental condition '" + dentalCondition + "':");
        toStream(getPatientsWithDisease(dentalCondition)).forEach(patient -> System.out.println(patient.toString()));
        System.out.println();
    }

    private void printPatientsWithInitial(char initial)
    {
        System.out.println("Patients with name starting with '" + initial + "':");
        toStream(getPatientsWithInitial(initial)).forEach(patient -> System.out.println(patient.toString()));
        System.out.println();
    }

    private void printAppointmentsAfterDate(String targetDate)
    {
        System.out.println("Appointments after date '" + targetDate + "':");
        toStream(getAppointmentsAfterDate(targetDate)).forEach(appointment -> System.out.println(appointment.toString()));
        System.out.println();
    }

    private void printAppointmentDescriptionById(int appointmentID)
    {
        System.out.println("Description of appointment with ID '" + appointmentID + "':");
        getAppointmentDescriptionById(appointmentID)
                .ifPresent(System.out::println);
        System.out.println();
    }

    private void printAppointmentsFromDateWithDescription(String targetDate)
    {
        List<String> appointmentsWithDescription = getAppointmentsFromDateWithDescription(targetDate);
        System.out.println("Appointments from date '" + targetDate + "' and their description:");
        toStream(appointmentsWithDescription).forEach(System.out::println);
        System.out.println();
    }

    private void printPatientsSortedAlphabetically()
    {
        System.out.println("Patients sorted alphabetically by name:");
        toStream(getPatientsSortedAlphabetically()).forEach(patient -> System.out.println(patient.toString()));
        System.out.println();
    }
}
