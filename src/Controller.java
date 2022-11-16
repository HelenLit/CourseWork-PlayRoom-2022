import Child.AgeGroup;
import Child.Child;
import Email.EmailSender;
import Toy.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.Main;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller{

    @FXML
    private TextField deleteField;
    @FXML
    private TextField priceField;
    @FXML
    private Label actualPrice;
    @FXML
    private Label initialPrice;
    @FXML
    private Label deleteLabel;
    @FXML
    public ListView<String> listOfToys;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private AnchorPane tViewAnchor;
    @FXML
    private AnchorPane sortAnchor;
    @FXML
    private AnchorPane priceAnchor;
    @FXML
    private CheckBox toddlerBox;
    @FXML
    private CheckBox middlechildBox;
    @FXML
    private CheckBox teenagerBox;

    private static Stage stage;
    private static Scene scene;
    private Parent root;

    public void changeScene(String name, javafx.event.ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(name));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void updateList(List<Toy> toy){
        listOfToys.getItems().addAll(new Toy(1,"1",1,AgeGroup.getAgeGroupByOrd(1),ToySize.BIG).toString());
        for (Toy value : toy) {
            listOfToys.getItems().addAll(value.toString());
        }
    }

    public void updatePrice(){
        actualPrice.setText("Actual price:   " + Main.adm.getPlayRoom().getToyList().getActualMoney());
        initialPrice.setText("Initial money: " + Main.adm.getPlayRoom().getToyList().getInitialMoney());
    }

    public void windowAddAge(javafx.event.ActionEvent e) throws IOException {
        changeScene("/resources/ageGroups.fxml",e);
    }
    public void addAgeGroups(javafx.event.ActionEvent e) throws IOException {

        if(toddlerBox.isSelected()){
            Main.adm.getPlayRoom().addAgeGroup(AgeGroup.getAgeGroupByOrd(1));
        }
        if(middlechildBox.isSelected()){
            Main.adm.getPlayRoom().addAgeGroup(AgeGroup.getAgeGroupByOrd(2));
        }
        if(teenagerBox.isSelected()){
            Main.adm.getPlayRoom().addAgeGroup(AgeGroup.getAgeGroupByOrd(3));
        }

        changeScene("/resources/Main.fxml",e);

    }

    public void windowDeleteToy(javafx.event.ActionEvent e) throws IOException {
        changeScene("/resources/deleteToy.fxml",e);
    }

    public void deleteToy(javafx.event.ActionEvent e) throws IOException {
        int ID;
        try {
            ID = Integer.parseInt(deleteField.getText());
            if(ID > Main.adm.getToyList().getInitialMoney()) {  //Size method
                deleteLabel.setText("Ви ввели неправильні дані, введіть їх ще раз");
                return;
            }
        }catch(NumberFormatException number){
            deleteLabel.setText("Ви ввели неправильні дані, введіть їх ще раз");
            return;
        }
        Main.adm.getToyList().deleteToy(ID);
        deleteField.clear();
        deleteLabel.setText("Введіть ID іграшки, яку хочете видалити :");
        changeScene("/resources/Main.fxml",e);
        updatePrice();
    }

    public void windowRegisterChild(javafx.event.ActionEvent e) throws IOException {
        changeScene("/resources/Register.fxml",e);
    }

    public void windowAddToy(javafx.event.ActionEvent e) throws IOException {
        changeScene("/resources/NewToy.fxml",e);
    }

    public void windowSetPrice(javafx.event.ActionEvent e) throws IOException {
        mainAnchor.setDisable(true);
        mainAnchor.setVisible(false);

        priceAnchor.setDisable(false);
        priceAnchor.setVisible(true);
    }

    public void setPrice(javafx.event.ActionEvent e) throws IOException {
        int price = 0;

        try{
            price = Integer.parseInt(priceField.getText());
        }catch(NumberFormatException number){
            price = 350;
        }
        Main.adm.getPlayRoom().getToyList().setInitialMoney(price);
        priceField.clear();

        returnToMain(priceAnchor);

        updatePrice();
    }

    public void createList(javafx.event.ActionEvent e){
        Main.adm.getPlayRoom().getToyList().CreateToyMap(Main.adm.getToyList().allToysByAgeGroup(AgeGroup.TODDLER)
                , Main.adm.getToyList().allToysByAgeGroup(AgeGroup.MIDDLECHILD)
                , Main.adm.getToyList().allToysByAgeGroup(AgeGroup.TEENAGER));
        List<Toy> list = Main.adm.getToyList().allToysByAgeGroup(AgeGroup.TODDLER);
        list.addAll(Main.adm.getToyList().allToysByAgeGroup(AgeGroup.MIDDLECHILD));
        list.addAll(Main.adm.getToyList().allToysByAgeGroup(AgeGroup.TEENAGER));

        for(Toy t : Main.adm.getPlayRoom().getToyList().listFromMap(list)){
            listOfToys.getItems().addAll(t.toString());
        }

        updatePrice();
    }

    public void startRoom(javafx.event.ActionEvent e){
        Main.adm.getPlayRoom().startGroup();
        EmailSender.send("Playroom event","Playroom is opened");
    }

    public void endRoom(javafx.event.ActionEvent e){
        Main.adm.getPlayRoom().freeRoom();
        EmailSender.send("Playroom event","Playroom is closed");
    }

    public void childrenList(javafx.event.ActionEvent e){
        listOfToys.getItems().clear();

        List<Child> child = Main.adm.getPlayRoom().ChildrenList();
        for (Child value : child) {
            listOfToys.getItems().addAll(value.toString());
        }
    }

    public void windowSort(javafx.event.ActionEvent e) throws IOException {
        mainAnchor.setDisable(true);
        mainAnchor.setVisible(false);

        sortAnchor.setDisable(false);
        sortAnchor.setVisible(true);
    }

    public void windowShow(javafx.event.ActionEvent e) throws IOException {
        mainAnchor.setDisable(true);
        mainAnchor.setVisible(false);

        tViewAnchor.setDisable(false);
        tViewAnchor.setVisible(true);
    }

    public void returnToMain(AnchorPane secondAnchor) throws IOException {
        secondAnchor.setVisible(false);
        secondAnchor.setDisable(true);

        mainAnchor.setVisible(true);
        mainAnchor.setDisable(false);
    }

    public void sortByAge(javafx.event.ActionEvent e) throws IOException {
        listOfToys.getItems().clear();
        updateList(Main.adm.getPlayRoom().getToyList().sortToysByAgeGroup());
        updatePrice();
        returnToMain(sortAnchor);
    }

    public void sortByAmount(javafx.event.ActionEvent e) throws IOException {
        listOfToys.getItems().clear();
        updateList(Main.adm.getPlayRoom().getToyList().sortToysByAmount());
        updatePrice();
        returnToMain(sortAnchor);
    }

    public void sortByPrice(javafx.event.ActionEvent e) throws IOException {
        listOfToys.getItems().clear();
        updateList(Main.adm.getPlayRoom().getToyList().sortToysByPrice());
        updatePrice();
        returnToMain(sortAnchor);
    }

    public void sortReturn(javafx.event.ActionEvent e) throws IOException {
        returnToMain(sortAnchor);
    }

    public void allToys(javafx.event.ActionEvent e) throws IOException {
        listOfToys.getItems().clear();
        updateList(Main.adm.getToyList().allToysByAgeGroup(AgeGroup.TODDLER));
        updateList(Main.adm.getToyList().allToysByAgeGroup(AgeGroup.MIDDLECHILD));
        updateList(Main.adm.getToyList().allToysByAgeGroup(AgeGroup.TEENAGER));
        updatePrice();
        returnToMain(tViewAnchor);
    }

    public void toysInRoom(javafx.event.ActionEvent e) throws IOException {
        listOfToys.getItems().clear();
        updateList(Main.adm.getPlayRoom().getToyList().toysInRoom());
        updatePrice();
        returnToMain(tViewAnchor);
    }

    public void tviewReturn(javafx.event.ActionEvent e) throws IOException{
        returnToMain(tViewAnchor);
    }
}
