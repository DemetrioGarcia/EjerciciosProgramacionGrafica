package org.example.demo;

import com.almasb.fxgl.net.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloApplication extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 350, 400);
        stage.setTitle("Mantenimiento de Estudiantes");
        stage.setScene(scene);
        stage.show();
    }

    private static Parent LoadFXML(String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml+".fxml"));
        return fxmlLoader.load();
    }

    static void setRoot(String fxml) throws IOException{
        scene.setRoot(LoadFXML(fxml));
    }

    public static void main(String[] args) {
        launch();
    }

}