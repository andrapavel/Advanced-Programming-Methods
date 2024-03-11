package main;

import domain.Doctor;
import domain.Patient;
import gui.DoctorsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.*;
import service.Service;
import ui.UI;

import java.util.ArrayList;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        IRepository<Doctor, Integer> docRepo = new DoctorsRepositoryTextFile("doctors.txt");
        Service service = new Service(docRepo);
        DoctorsController controller = new DoctorsController(service);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/DoctorsGUI.fxml"));
        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}