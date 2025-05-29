package org.example.demo;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class HelloController {

    @FXML
    private TextField NiaTextField;
    @FXML
    private TextField NombreTextField;
    @FXML
    private DatePicker FechaTextField;
    @FXML
    private TableColumn<Estudiante, Integer> niaTableColumn;
    @FXML
    private TableColumn<Estudiante, String> nombreTableColumn;
    @FXML
    private TableColumn<Estudiante, Date> fechaTableColumn;
    @FXML
    private TableView<Estudiante> tablaEstudiante;

    private final Connection bd = conexion();

    @FXML
    private Button boton1;
    @FXML
    private Button boton2;

    @FXML
    public void initialize() {

        boton2.setDisable(true);

        niaTableColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getNia()).asObject());
        nombreTableColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));

        fechaTableColumn.setCellValueFactory(data ->
                new ReadOnlyObjectWrapper<>(data.getValue().getFechaNacimiento())
        );

        actualizarlista(bd);


    }

    public void guardarEstudiante() throws IOException {

        String nombre = NombreTextField.getText();
        Integer nia = Integer.valueOf(NiaTextField.getText());
        Date fecha = Date.valueOf(FechaTextField.getValue());

        Estudiante estudiante = new Estudiante(nia, nombre, fecha);

        insertar(bd, estudiante);

        NombreTextField.clear();
        NiaTextField.clear();

    }

    public static Connection conexion() {

        Connection conexion;
        String host = "jdbc:mariadb://localhost:3307/";
        String user = "root";
        String psw = "";
        String bd = "estudiantes";
        System.out.println("Conectando...");

        try {
            conexion = DriverManager.getConnection(host + bd, user, psw);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return conexion;
    }

    public static void desconectar(Connection conexion) {
        System.out.println("Desconectando...");

        try {
            conexion.close();
            System.out.println("Conexión finalizada.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void insertar(Connection conexion, Estudiante estudiante) {
        String query = "INSERT INTO estudiantes (nia, nombre, fechaNacimiento) VALUES " + estudiante.toString() + ";";

        Statement stmt;

        try {
            stmt = conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        actualizarlista(bd);
    }

    public ObservableList<Estudiante> actualizarlista(Connection conexion) {
        ObservableList<Estudiante> listaEstudiante = FXCollections.observableArrayList();

        String query = "SELECT * FROM estudiantes";

        Statement stmt;
        ResultSet respuesta;

        try {
            stmt = conexion.createStatement();
            respuesta = stmt.executeQuery(query);

            while (respuesta.next()) {
                listaEstudiante.add(new Estudiante(respuesta.getInt("nia"),
                        respuesta.getString("nombre"),
                        respuesta.getDate("fechaNacimiento")));
            }

            System.out.println(listaEstudiante);
            tablaEstudiante.setItems(listaEstudiante);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return listaEstudiante;
    }

    public void borrar(Connection conexion, Estudiante estudiante) {

        String query = "DELETE FROM estudiantes WHERE nia=" + estudiante.getNia();

        Statement stmt;

        try {
            stmt = bd.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void eliminarEstudiante() throws Exception {

        Estudiante seleccionado = tablaEstudiante.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            borrar(bd, seleccionado);
        } else {
            System.out.println("No hay ninguna fila seleccionada.");
        }
        actualizarlista(bd);
    }

    public void actualizarEstudiante() throws Exception {
        boton2.setDisable(false);
        boton1.setDisable(true);
        Estudiante seleccionado = tablaEstudiante.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            NiaTextField.setText(String.valueOf(seleccionado.getNia()));
            NombreTextField.setText(seleccionado.getNombre());
            FechaTextField.setValue(seleccionado.getFechaNacimiento().toLocalDate());
        } else {
            System.out.println("No hay ninguna fila seleccionada.");
        }
    }

    public void guardar() {
        boton1.setDisable(false);
        boton2.setDisable(true);

        String nombre = NombreTextField.getText();
        Integer nia = Integer.valueOf(NiaTextField.getText());
        Date fecha = Date.valueOf(FechaTextField.getValue());

        String query = "UPDATE estudiantes SET nombre = '"+nombre+"', FechaNacimiento = '"+fecha+"' WHERE nia = "+nia;

        Statement stmt;

        try {
            stmt = bd.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        actualizarlista(bd);
        NombreTextField.clear();
        NiaTextField.clear();
    }


}