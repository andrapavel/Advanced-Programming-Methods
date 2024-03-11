package gui;

import domain.Appointment;
import domain.Patient;
import exceptions.ValidationException;
import service.Service;
import service.State;

import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;


public class PatientsController
{

    private static final Logger LOGGER = Logger.getLogger(PatientsController.class.getName());

    private final Stack<State> history = new Stack<>();
    private final Stack<State> redoStack = new Stack<>();

    private Service service;


    public PatientsController(Service service)
    {
        this.service = service;
    }

    @FXML
    private ListView<Appointment> appointmentsListView;

    @FXML
    private ListView<Patient> patientsListView;

    @FXML
    private TextField searchAppTF;

    @FXML
    private TextField searchPatientTF;

    @FXML
    private Button buttonAddApp;

    @FXML
    private Button buttonAddPatient;

    @FXML
    private Button buttonDeleteApp;

    @FXML
    private Button buttonDeletePatient;

    @FXML
    private Button buttonUpdateApp;

    @FXML
    private Button buttonUpdatePatient;

    @FXML
    private Button redoButton;

    @FXML
    private Button undoButton;

    @FXML
    private TextField patientDentalConditionTF;

    @FXML
    private TextField patientIdTF;

    @FXML
    private TextField patientNameTF;
    @FXML
    private TextField appointmentDescriptionTF;

    @FXML
    private TextField appointmentIdTF;

    @FXML
    private TextField appointmentDateTF;


    @SuppressWarnings("unchecked")
    public void populatePatientsList()
    {
        ObservableList<Patient> patientList = FXCollections.observableArrayList(service.getAllPatients());
        patientsListView.setItems(patientList);
    }

    @SuppressWarnings("unchecked")
    public void populateAppointmentsList()
    {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList(service.getAllAppointments());
        appointmentsListView.setItems(appointmentList);
    }

    public void initialize()
    {
        populateAppointmentsList();
        populatePatientsList();
    }

    @SuppressWarnings("unchecked")
    @FXML
    void onAppointmentSearch(KeyEvent event)
    {
        String searchedText = searchAppTF.getText();
        if( searchedText.isEmpty())
        {
            populateAppointmentsList();
        }
        else
        {
            ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList(service.filterByDate(searchedText));
            appointmentsListView.setItems(filteredAppointments);
        }
    }

    @SuppressWarnings("unchecked")
    @FXML
    void onPatientSearch(KeyEvent event)
    {
        String searchedText = searchPatientTF.getText();
        if( searchedText.isEmpty())
        {
            populatePatientsList();
        }
        else
        {
            ObservableList<Patient> filteredPatients = FXCollections.observableArrayList(service.filterByName(searchedText));
            patientsListView.setItems(filteredPatients);
        }
    }

//    @SuppressWarnings("unchecked")
//    @FXML
//    void addAppointmentClicked(MouseEvent event)
//    {
//        String textID = appointmentIdTF.getText();
//        String description = appointmentDescriptionTF.getText();
//        String date = appointmentDateTF.getText();
//
//        if(textID.isEmpty() || description.isEmpty() || date.isEmpty())
//        {
//            Alert dialog = new Alert(Alert.AlertType.WARNING);
//            dialog.setTitle("Error");
//            dialog.setContentText("There should be no empty fields for appointments");
//            dialog.show();
//        }
//        else
//        {
//            try
//            {
//                int id = Integer.parseInt(textID);
//
//                Appointment app = new Appointment(id, description, date);
//                service.addAppointment(app);
//                ObservableList<Appointment> appointmentList =
//                        FXCollections.observableArrayList(service.getAllAppointments());
//                appointmentsListView.setItems(appointmentList);
//            }
//            catch (NumberFormatException e)
//            {
//                System.out.println(e.getMessage());
//                Alert dialog = new Alert(Alert.AlertType.WARNING);
//                dialog.setTitle("Error");
//                dialog.setContentText("ID field should contain a valid number");
//                dialog.show();
//            }
//            catch (ValidationException e)
//            {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    @FXML
//    void addPatientClicked(MouseEvent event)
//    {
//        String textID = patientIdTF.getText();
//        String disease = patientDentalConditionTF.getText();
//        String name = patientNameTF.getText();
//
//        if(textID.isEmpty() || disease.isEmpty() || name.isEmpty())
//        {
//            Alert dialog = new Alert(Alert.AlertType.WARNING);
//            dialog.setTitle("Error");
//            dialog.setContentText("There should be no empty fields for patient");
//            dialog.show();
//        }
//        else
//        {
//            try
//            {
//                int id = Integer.parseInt(textID);
//
//                Patient p = new Patient(id, name, disease);
//                service.addPatient(p);
//                ObservableList<Patient> patientList =
//                        FXCollections.observableArrayList(service.getAllPatients());
//                patientsListView.setItems(patientList);
//            }
//            catch (NumberFormatException e)
//            {
//                System.out.println(e.getMessage());
//                Alert dialog = new Alert(Alert.AlertType.WARNING);
//                dialog.setTitle("Error");
//                dialog.setContentText("ID field should contain a valid number");
//                dialog.show();
//            }
//            catch (ValidationException e)
//            {
//                Alert dialog = new Alert(Alert.AlertType.WARNING);
//                dialog.setTitle("Error");
//                dialog.setContentText("There should be no empty fields for patient");
//                dialog.show();
//            }
//        }
//    }

//    @FXML
//    void addAppointmentClicked(MouseEvent event)
//    {
//        try
//        {
//            // Capture the current state before modification
//            State currentState = State.captureState(service);
//
//            int id = Integer.parseInt(appointmentIdTF.getText());
//            String description = appointmentDescriptionTF.getText();
//            String date = appointmentDateTF.getText();
//
//            Appointment app = new Appointment(id, description, date);
//            service.addAppointment(app);
//
//            // Add the new state to the history stack
//            history.push(currentState);
//        }
//        catch (NumberFormatException | ValidationException e)
//        {
//            e.printStackTrace();
//        }
//    }


//    @FXML
//    void addAppointmentClicked(MouseEvent event) // Only works with undo
//    {
//        try {
//            // Capture the current state before modification
//            captureAndPushState();
//
//            int id = Integer.parseInt(appointmentIdTF.getText());
//            String description = appointmentDescriptionTF.getText();
//            String date = appointmentDateTF.getText();
//
//            Appointment app = new Appointment(id, description, date);
//            service.addAppointment(app);
//
//            // Update the UI
//            updateAppointmentsListView();
//        }
//        catch (NumberFormatException | ValidationException e)
//        {
//            e.printStackTrace();
//        }
//    }

//    @FXML
//    void addAppointmentClicked(MouseEvent event)
//    {
//        try {
//            int id = Integer.parseInt(appointmentIdTF.getText());
//            String description = appointmentDescriptionTF.getText();
//            String date = appointmentDateTF.getText();
//
//            Appointment app = new Appointment(id, description, date);
//
//            // Capture the state after modification
//            State newState = State.captureState(service);
//
//            // Add the appointment
//            service.addAppointment(app);
//
//            // Push the new state onto the history stack
//            history.push(newState);
//
//            // Clear the redo stack after a new action is performed
//            redoStack.clear();
//
//            // Update the list views
//            updatePatientsListView();
//            updateAppointmentsListView();
//        }
//        catch (NumberFormatException | ValidationException e)
//        {
//            e.printStackTrace();
//        }
//    }


    @FXML
    void addAppointmentClicked(MouseEvent event) //With undo and redo
    {
        try
        {
            int id = Integer.parseInt(appointmentIdTF.getText());
            String description = appointmentDescriptionTF.getText();
            String date = appointmentDateTF.getText();

            Appointment app = new Appointment(id, description, date);

            // Capture the current state before modification
            State currentState = State.captureState(service);

            service.addAppointment(app);

            // Push the new state onto the history stack
            history.push(currentState);

            // Clear the redo stack after a new action is performed
            redoStack.clear();

            // Update the list views
            updatePatientsListView();
            updateAppointmentsListView();
        }
        catch (NumberFormatException | ValidationException e)
        {
            LOGGER.severe("Error adding appointment: " + e.getMessage());
            showAlert("Error adding appointment. Please check the input.");
        }
    }



//    @FXML
//    void addPatientClicked(MouseEvent event) // Without either undo or redo
//    {
//        try
//        {
//            int id = Integer.parseInt(patientIdTF.getText());
//            String name = patientNameTF.getText();
//            String disease = patientDentalConditionTF.getText();
//
//            // Capture the current state before modification
//            State currentState = State.captureState(service);
//
//            Patient p = new Patient(id, name, disease);
//            service.addPatient(p);
//
//            // Add the new state to the history stack
//            history.push(currentState);
//        }
//        catch (NumberFormatException | ValidationException e)
//        {
//            e.printStackTrace();
//        }
//    }

//    @FXML
//    void addPatientClicked(MouseEvent event) // Only with undo
//    {
//        try
//        {
//            // Capture the current state before modification
//            captureAndPushState();
//
//            int id = Integer.parseInt(patientIdTF.getText());
//            String name = patientNameTF.getText();
//            String disease = patientDentalConditionTF.getText();
//
//            Patient p = new Patient(id, name, disease);
//            service.addPatient(p);
//
//            // Update the UI
//            updatePatientsListView();
//        }
//        catch (NumberFormatException | ValidationException e)
//        {
//            e.printStackTrace();
//        }
//    }

    @FXML
    void addPatientClicked(MouseEvent event)  // With both undo and redo
    {
        try
        {
            // Capture the current state before modification
            captureAndPushState();

            int id = Integer.parseInt(patientIdTF.getText());
            String name = patientNameTF.getText();
            String disease = patientDentalConditionTF.getText();

            Patient p = new Patient(id, name, disease);
            service.addPatient(p);

            // Update the UI
            updatePatientsListView();
        }
        catch (NumberFormatException | ValidationException e)
        {
            LOGGER.severe("Error adding patient: " + e.getMessage());
            showAlert("Error adding patient. Please check the input.");
        }
    }


//    @SuppressWarnings("unchecked")  // Without either undo or redo
//    @FXML
//    void deleteAppointmentClicked(MouseEvent event)
//    {
//        String textId = appointmentIdTF.getText();
//        if(textId.isEmpty()){
//            Alert dialog = new Alert(Alert.AlertType.WARNING);
//            dialog.setTitle("Error");
//            dialog.setContentText("The ID filed cannot be empty");
//            dialog.show();
//        }
//        else
//        {
//            try
//            {
//                int id = Integer.parseInt(textId);
//
//                service.deleteAppointment(id);
//                ObservableList<Appointment> appointmentList =
//                        FXCollections.observableArrayList(service.getAllAppointments());
//                appointmentsListView.setItems(appointmentList);
//
//            }
//            catch (ValidationException e)
//            {
//                throw new RuntimeException(e);
//            }
//            catch (NumberFormatException e)
//            {
//                System.out.println(e.getMessage());
//                Alert dialog = new Alert(Alert.AlertType.WARNING);
//                dialog.setTitle("Error");
//                dialog.setContentText("ID field should contain a valid number");
//                dialog.show();
//            }
//        }
//    }

//    @FXML
//    void deleteAppointmentClicked(MouseEvent event)  // With undo only
//    {
//        try
//        {
//            // Capture the current state before modification
//            State currentState = State.captureState(service);
//
//            String textId = appointmentIdTF.getText();
//            if (textId.isEmpty())
//            {
//                // Handle empty ID field
//                showAlert("The ID field cannot be empty");
//            }
//            else
//            {
//                int id = Integer.parseInt(textId);
//                service.deleteAppointment(id);
//
//                // Add the new state to the history stack
//                history.push(currentState);
//
//                // Update the appointments list view
//                updateAppointmentsListView();
//            }
//        }
//        catch (ValidationException | NumberFormatException e)
//        {
//            e.printStackTrace();
//        }
//    }

    @FXML
    void deleteAppointmentClicked(MouseEvent event)  // With both undo and redo
    {
        try
        {
            // Capture the current state before modification
            captureAndPushState();

            String textId = appointmentIdTF.getText();
            if (textId.isEmpty())
            {
                // Handle empty ID field
                showAlert("The ID field cannot be empty");
            }
            else
            {
                int id = Integer.parseInt(textId);
                service.deleteAppointment(id);

                // Update the appointments list view
                updateAppointmentsListView();
            }
        }
        catch (ValidationException | NumberFormatException e)
        {
            LOGGER.severe("Error deleting appointment: " + e.getMessage());
            showAlert("Error deleting appointment. Please check the input.");
        }
    }


//    @SuppressWarnings("unchecked")
//    @FXML
//    void deletePatientClicked(MouseEvent event)
//    {
//        String textId = patientIdTF.getText();
//        if(textId.isEmpty()){
//            Alert dialog = new Alert(Alert.AlertType.WARNING);
//            dialog.setTitle("Error");
//            dialog.setContentText("The ID filed cannot be empty");
//            dialog.show();
//        }
//        else
//        {
//            try
//            {
//                int id = Integer.parseInt(textId);
//
//                service.deletePatient(id);
//                ObservableList<Patient> patientList =
//                        FXCollections.observableArrayList(service.getAllPatients());
//                patientsListView.setItems(patientList);
//
//            }
//            catch (ValidationException e)
//            {
//                throw new RuntimeException(e);
//            }
//            catch (NumberFormatException e)
//            {
//                System.out.println(e.getMessage());
//                Alert dialog = new Alert(Alert.AlertType.WARNING);
//                dialog.setTitle("Error");
//                dialog.setContentText("ID field should contain a valid number");
//                dialog.show();
//            }
//        }
//    }

//    @FXML
//    void deletePatientClicked(MouseEvent event)  // With undo only
//    {
//        try
//        {
//            // Capture the current state before modification
//            State currentState = State.captureState(service);
//
//            String textId = patientIdTF.getText();
//            if (textId.isEmpty())
//            {
//                // Handle empty ID field
//                showAlert("The ID field cannot be empty");
//            }
//            else
//            {
//                int id = Integer.parseInt(textId);
//                service.deletePatient(id);
//
//                // Add the new state to the history stack
//                history.push(currentState);
//
//                // Update the patients list view
//                updatePatientsListView();
//            }
//        }
//        catch (ValidationException | NumberFormatException e)
//        {
//            e.printStackTrace();
//        }
//    }

    @FXML
    void deletePatientClicked(MouseEvent event)  // With both undo and redo
    {
        try
        {
            // Capture the current state before modification
            captureAndPushState();

            String textId = patientIdTF.getText();
            if (textId.isEmpty())
            {
                // Handle empty ID field
                showAlert("The ID field cannot be empty");
            }
            else
            {
                int id = Integer.parseInt(textId);
                service.deletePatient(id);

                // Update the patients list view
                updatePatientsListView();
            }
        }
        catch (ValidationException | NumberFormatException e)
        {
            LOGGER.severe("Error deleting patient: " + e.getMessage());
            showAlert("Error deleting patient. Please check the input.");
        }
    }


//    @FXML
//    void updateAppointmentClicked(MouseEvent event)  //Without either undo or redo. It does not add the appointment if it doesn't exist
//    {
//        String textID = appointmentIdTF.getText();
//        String description = appointmentDescriptionTF.getText();
//        String date = appointmentDateTF.getText();
//
//        if( description.isEmpty() || date.isEmpty()){
//            Alert dialog = new Alert(Alert.AlertType.WARNING);
//            dialog.setTitle("Error");
//            dialog.setContentText("There should be no empty fields for appointments");
//            dialog.show();
//        }
//        else
//        {
//            try
//            {
//                int id = Integer.parseInt(textID);
//
//                Appointment app = new Appointment(id, description, date);
//                service.updateAppointment(id,app);
//                ObservableList<Appointment> appointmentList =
//                        FXCollections.observableArrayList(service.getAllAppointments());
//                appointmentsListView.setItems(appointmentList);
//
//            }
//            catch (NumberFormatException e)
//            {
//                System.out.println(e.getMessage());
//                Alert dialog = new Alert(Alert.AlertType.WARNING);
//                dialog.setTitle("Error");
//                dialog.setContentText("ID field should contain a valid number");
//                dialog.show();
//            }
//            catch (ValidationException e)
//            {
//                System.out.println(e.getMessage());
//                Alert dialog = new Alert(Alert.AlertType.WARNING);
//                dialog.setTitle("Error");
//                dialog.setContentText("Id not found");
//                dialog.show();
//            }
//        }
//    }

//    @FXML
//    void updateAppointmentClicked(MouseEvent event)
//    {
//        String textID = appointmentIdTF.getText();
//        String description = appointmentDescriptionTF.getText();
//        String date = appointmentDateTF.getText();
//
//        if (textID.isEmpty() || description.isEmpty() || date.isEmpty())
//        {
//            // Handle empty fields
//            showAlert("There should be no empty fields for appointments");
//        }
//        else
//        {
//            try
//            {
//                int id = Integer.parseInt(textID);
//                Appointment updatedAppointment = new Appointment(id, description, date);
//
//                boolean updateSuccessful = service.updateAppointmentB(id, updatedAppointment);
//
//                if (updateSuccessful)
//                {
//                    updateAppointmentsListView();
//                }
//                else
//                {
//                    // Handle the case where the ID does not exist
//                    //showAlert("Error", "Appointment with ID " + id + " not found. Adding a new appointment.");
//                    service.addAppointment(updatedAppointment);
//                    updateAppointmentsListView();
//                }
//            }
//            catch (NumberFormatException | ValidationException e)
//            {
//                // Handle invalid ID
//                System.out.println(e.getMessage());
//                showAlert("ID field should contain a valid number");
//            }
//        }
//    }

//    @FXML
//    void updateAppointmentClicked(MouseEvent event)  // With undo only, but it also adds the appointment if it doesn't exist
//    {
//        try
//        {
//            // Capture the current state before modification
//            State currentState = State.captureState(service);
//
//            String textID = appointmentIdTF.getText();
//            String description = appointmentDescriptionTF.getText();
//            String date = appointmentDateTF.getText();
//
//            if (textID.isEmpty() || description.isEmpty() || date.isEmpty())
//            {
//                // Handle empty fields
//                showAlert("There should be no empty fields for appointments");
//            }
//            else
//            {
//                int id = Integer.parseInt(textID);
//                Appointment updatedAppointment = new Appointment(id, description, date);
//
//                boolean updateSuccessful = service.updateAppointmentB(id, updatedAppointment);
//
//                if (updateSuccessful)
//                {
//                    // Update the list view
//                    updateAppointmentsListView();
//                }
//                else
//                {
//                    // Handle the case where the ID does not exist
//                    service.addAppointment(updatedAppointment);
//                    // Update the list view
//                    updateAppointmentsListView();
//                }
//
//                // Add the new state to the history stack
//                history.push(currentState);
//            }
//        }
//        catch (ValidationException | NumberFormatException e)
//        {
//            e.printStackTrace();
//        }
//    }

    @FXML
    void updateAppointmentClicked(MouseEvent event)  // With both undo and redo, plus it also adds the appointment if it doesn't exist
    {
        try
        {
            // Capture the current state before modification
            captureAndPushState();

            String textID = appointmentIdTF.getText();
            String description = appointmentDescriptionTF.getText();
            String date = appointmentDateTF.getText();

            if (textID.isEmpty() || description.isEmpty() || date.isEmpty())
            {
                // Handle empty fields
                showAlert("There should be no empty fields for appointments");
            }
            else
            {
                int id = Integer.parseInt(textID);
                Appointment updatedAppointment = new Appointment(id, description, date);

                boolean updateSuccessful = service.updateAppointmentB(id, updatedAppointment);

                if (updateSuccessful)
                {
                    // Update the list view
                    updateAppointmentsListView();
                }
                else
                {
                    // Handle the case where the ID does not exist
                    service.addAppointment(updatedAppointment);
                    // Update the list view
                    updateAppointmentsListView();
                }
            }
        }
        catch (ValidationException | NumberFormatException e)
        {
            LOGGER.severe("Error updating appointment: " + e.getMessage());
            showAlert("Error updating appointment. Please check the input.");
        }
    }


    // Shows an alert
    private void showAlert(String content)
    {
        Alert dialog = new Alert(Alert.AlertType.WARNING);
        dialog.setTitle("Error");
        dialog.setContentText(content);
        dialog.show();
    }

    @SuppressWarnings("unchecked")
    // Update the appointments list view
    private void updateAppointmentsListView()
    {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList(service.getAllAppointments());
        appointmentsListView.setItems(appointmentList);
    }


//    @FXML
//    void updatePatientClicked(MouseEvent event)  // Without either undo or redo. It does not add the patient if it does not exist.
//    {
//        String textID = patientIdTF.getText();
//        String name = patientNameTF.getText();
//        String disease = patientDiseaseTF.getText();
//
//        if(textID.isEmpty() || name.isEmpty() || disease.isEmpty()){
//            Alert dialog = new Alert(Alert.AlertType.WARNING);
//            dialog.setTitle("Error");
//            dialog.setContentText("There should be no empty fields for appointments");
//            dialog.show();
//        }
//        else
//        {
//            try
//            {
//                int id = Integer.parseInt(textID);
//
//                Patient p = new Patient(id, name, disease);
//                service.updatePatient(p);
//                ObservableList<Patient> patientList =
//                        FXCollections.observableArrayList(service.getAllPatients());
//                patientsListView.setItems(patientList);
//
//            }
//            catch (NumberFormatException e)
//            {
//                System.out.println(e.getMessage());
//                Alert dialog = new Alert(Alert.AlertType.WARNING);
//                dialog.setTitle("Error");
//                dialog.setContentText("ID field should contain a valid number");
//                dialog.show();
//            }
//            catch (ValidationException e)
//            {
//                System.out.println(e.getMessage());
//                Alert dialog = new Alert(Alert.AlertType.WARNING);
//                dialog.setTitle("Error");
//                dialog.setContentText("ID not found");
//                dialog.show();
//            }
//        }
//    }

//    @FXML
//    void updatePatientClicked(MouseEvent event)  // With undo only, but it also adds the patient if it does not exist.
//    {
//        try
//        {
//            // Capture the current state before modification
//            State currentState = State.captureState(service);
//
//            String textID = patientIdTF.getText();
//            String name = patientNameTF.getText();
//            String disease = patientDentalConditionTF.getText();
//
//            if (textID.isEmpty() || name.isEmpty() || disease.isEmpty())
//            {
//                // Handle empty fields
//                showAlert("There should be no empty fields for patients");
//            }
//            else
//            {
//                int id = Integer.parseInt(textID);
//                Patient updatedPatient = new Patient(id, name, disease);
//
//                // Call the service to update or add the patient
//                boolean updateSuccessful = service.updatePatientB(updatedPatient);
//
//                if (updateSuccessful)
//                {
//                    // Update the list view
//                    updatePatientsListView();
//                }
//                else
//                {
//                    service.addPatient(updatedPatient);
//                    // Update the list view
//                    updatePatientsListView();
//                }
//
//                // Add the new state to the history stack
//                history.push(currentState);
//            }
//        }
//        catch (ValidationException | NumberFormatException e)
//        {
//            e.printStackTrace();
//        }
//    }

    @FXML
    void updatePatientClicked(MouseEvent event)  // It has both the undo and the redo implemented, and it also adds the patient if it does not exist.
    {
        try
        {
            // Capture the current state before modification
            captureAndPushState();

            String textID = patientIdTF.getText();
            String name = patientNameTF.getText();
            String disease = patientDentalConditionTF.getText();

            if (textID.isEmpty() || name.isEmpty() || disease.isEmpty())
            {
                // Handle empty fields
                showAlert("There should be no empty fields for patients");
            }
            else
            {
                int id = Integer.parseInt(textID);
                Patient updatedPatient = new Patient(id, name, disease);

                // Call the service to update or add the patient
                boolean updateSuccessful = service.updatePatientB(updatedPatient);

                if (updateSuccessful)
                {
                    // Update the list view
                    updatePatientsListView();
                }
                else
                {
                    service.addPatient(updatedPatient);
                    // Update the list view
                    updatePatientsListView();
                }
            }
        }
        catch (ValidationException | NumberFormatException e)
        {
            LOGGER.severe("Error updating patient: " + e.getMessage());
            showAlert("Error updating patient. Please check the input.");
        }
    }


//    @FXML
//    void updatePatientClicked(MouseEvent event)  // Only adds the patient if it does not exist. Does not have the undo or redo implemented.
//    {
//        String textID = patientIdTF.getText();
//        String name = patientNameTF.getText();
//        String disease = patientDentalConditionTF.getText();
//
//        if (textID.isEmpty() || name.isEmpty() || disease.isEmpty())
//        {
//            // Handle empty fields
//            showAlert("There should be no empty fields for patients");
//        }
//        else
//        {
//            try
//            {
//                int id = Integer.parseInt(textID);
//                Patient updatedPatient = new Patient(id, name, disease);
//
//                // Call the service to update or add the patient
//                boolean updateSuccessful = service.updatePatientB(updatedPatient);
//
//                if (updateSuccessful)
//                {
//                    // Update the list view
//                    updatePatientsListView();
//                }
//                else
//                {
//                    service.addPatient(updatedPatient);
//                    updatePatientsListView();
//                }
//            }
//            catch (NumberFormatException e)
//            {
//                // Handle invalid ID
//                System.out.println(e.getMessage());
//                showAlert("ID field should contain a valid number");
//            }
//            catch (ValidationException e)
//            {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    @SuppressWarnings("unchecked")
    // Update the patients list view
    private void updatePatientsListView()
    {
        ObservableList<Patient> patientList = FXCollections.observableArrayList(service.getAllPatients());
        patientsListView.setItems(patientList);
    }

//    @FXML
//    void undoClicked(MouseEvent event)
//    {
//        if (event.getButton() == MouseButton.PRIMARY)
//        {

//            undo();
//
//            // Update the list views
//            updatePatientsListView();
//            updateAppointmentsListView();
//
//            // Disable undo button if there are no more actions to undo
//            undoButton.setDisable(!canUndo());
//
//            // Enable redo button since an undo action was performed
//            redoButton.setDisable(false);
//        }
//    }

    private void captureAndPushState()
    {
        // Capture the current state and push it onto the history stack
        State currentState = State.captureState(service);
        history.push(currentState);
    }

//    private void undo()
//    {
//        if (canUndo())
//        {
//            // Pop the previous state from the history stack
//            State previousState = history.pop();
//            System.out.println("Undoing to state: " + previousState);
//            updateState(previousState);
//        }
//    }

//    private void undo()
//    {
//        if (canUndo())
//        {
//            // Pop the previous state from the history stack
//            State previousState = history.pop();
//            System.out.println("Undoing to state: " + previousState);
//
//            // Update the service with the previous state
//            service.setPatients(previousState.patients());
//            service.setAppointments(previousState.appointments());
//        }
//    }
//
//
//
    public boolean canUndo()
    {
        // Check if there are previous states in the history stack
        return !history.isEmpty();
    }

//    private void updateState(State state)
//    {
//        System.out.println("Updating state to: " + state);
//        updatePatientsListView();
//        updateAppointmentsListView();
//    }

//    @FXML
//    void redoClicked(MouseEvent event)
//    {
//        if (event.getButton() == MouseButton.PRIMARY)
//        {
//            redo();
//
//            // Update the list views
//            updatePatientsListView();
//            updateAppointmentsListView();
//
//            // Enable undo button since a redo action was performed
//            undoButton.setDisable(false);
//
//            // Disable redo button if there are no more actions to redo
//            redoButton.setDisable(!canRedo());
//        }
//    }
//
//    private void redo()
//    {
//        if (canRedo())
//        {
//
//            // Pop the state from the redo stack
//            State nextState = history.pop();
//            System.out.println("Redoing to state: " + nextState);
//
//            // Push the redone state onto the history stack
//            history.push(nextState);
//
//            updateState(nextState);
//        }
//    }
//
//    public boolean canRedo()
//    {
//        // Check if there are states in the redo stack
//        return !history.isEmpty();
//    }

//    @FXML
//    void redoClicked(MouseEvent event)
//    {
//        if (event.getButton() == MouseButton.PRIMARY)
//        {
//            redo();
//
//            // Update the list views
//            updatePatientsListView();
//            updateAppointmentsListView();
//
//            // Enable undo button since a redo action was performed
//            undoButton.setDisable(false);
//
//            // Disable redo button if there are no more actions to redo
//            redoButton.setDisable(!canRedo());
//        }
//    }
//
//    private void redo()
//    {
//        if (canRedo())
//        {
//            // Pop the next state from the redo stack
//            State nextState = redoStack.pop();
//            System.out.println("Redoing to state: " + nextState);
//
//            // Capture the current state before modification
//            State currentState = State.captureState(service);
//
//            // Update the service with the redone state
//            service.setPatients(nextState.patients());
//            service.setAppointments(nextState.appointments());
//
//            // Push the current state onto the undo stack
//            history.push(currentState);
//
//            // Push the redone state onto the redo stack
//            redoStack.push(nextState);
//        }
//    }
//
//
    public boolean canRedo()
    {
        // Check if there are next states in the redo stack
        return !redoStack.isEmpty();
    }

    @FXML
    void undoClicked(MouseEvent event)
    {
        if (event.getButton() == MouseButton.PRIMARY)
        {
            undoRedo(true);
        }
    }

    @FXML
    void redoClicked(MouseEvent event)
    {
        if (event.getButton() == MouseButton.PRIMARY)
        {
            undoRedo(false);
        }
    }

    @SuppressWarnings("unchecked")
    private void undoRedo(boolean isUndo)
    {
        Stack<State> sourceStack = isUndo ? history : redoStack;
        Stack<State> targetStack = isUndo ? redoStack : history;

        if (!sourceStack.isEmpty())
        {
            // Pop the state from the source stack
            State nextState = sourceStack.pop();
            System.out.println((isUndo ? "Undoing" : "Redoing") + " to state: " + nextState);

            // Capture the current state before modification
            State currentState = State.captureState(service);

            // Update the service with the undone/redone state
            service.setPatients(nextState.patients());
            service.setAppointments(nextState.appointments());

            // Push the current state onto the opposite stack
            targetStack.push(currentState);

            // Update the list views
            updatePatientsListView();
            updateAppointmentsListView();

            // Enable/disable undo and redo buttons based on stack contents
            undoButton.setDisable(!canUndo());
            redoButton.setDisable(!canRedo());
        }
    }
}
