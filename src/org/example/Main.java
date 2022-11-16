package org.example;

import DAO.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main extends Application {

    public static Logger logger;

    public static AdmDAO adm;

    static public void setup() {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("src/mylogging.properties"));
            logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            logger.setUseParentHandlers(false);
            logger.addHandler(new FileHandler());
        } catch (IOException e) {
            System.err.println("Помилка з конфігурацією логгера.");
        }
    }
    public static void main(String[] args) throws Exception {
        setup();
        logger.log(Level.ALL,"Підключення");
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactoryType.SQLSERVER);
        assert factory != null;
        factory.setProperties("Course_Work_Play_Room","Student2022","2022");
        adm = factory.getAdmDAO();
        adm.createPlayRoom();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/Main.fxml"));
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
