package gui;

import domain.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import service.Service;

import java.util.ArrayList;

public class DoctorsController {
    private Service service;

    public DoctorsController(Service service) {
        this.service = service;
    }

    @FXML
    private ListView<Doctor> doctorsListView;

    @FXML
    private TextField searchTextField;


    @FXML
    private TextField nameTextField;


    @FXML TextField locationTextField;

    @FXML TextField gradeTextField;


    @FXML
    private TextField idTextField;


    void populateList()
    {
        ObservableList<Doctor> doctorsList = FXCollections.observableArrayList(service.getAll());
        doctorsListView.setItems(doctorsList);
    }

    public void initialize()
    {
        populateList();
    }

    @FXML
    void searchOnKeyTyped(KeyEvent event) {
        String searchText = searchTextField.getText();
        if (searchText.equals(""))
            populateList();
        else {
            ObservableList<Doctor> filteredDoctors = FXCollections.observableArrayList(service.filterBySpecialty(searchText));
            doctorsListView.setItems(filteredDoctors);
        }
    }



    @FXML
    void onAddButtonClicked(MouseEvent event) {

        try {
            Double grade = Double.parseDouble( gradeTextField.getText());
            Integer id =Integer.parseInt( idTextField.getText());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setTitle("ERoare!!");
            dialog.setContentText(e.getMessage());
            dialog.show();
        }

        String name = nameTextField.getText();
        String location = locationTextField.getText();

        if (name.isEmpty() || location.isEmpty()) {
            Alert dialog = new Alert(Alert.AlertType.WARNING);
            dialog.setTitle("eroare");
            dialog.setContentText("Nu gasim nimic in location sau name");
        }
    }
}
