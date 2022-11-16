import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.Main;

import Child.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class registerController implements Initializable {
    @FXML
    private ChoiceBox<String> ageGroup;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField parentsCredentialsField;

    private String[] types = {"TODDLER", "MIDDLECHILD", "TEENAGER"};

    private String type = "";


    public void getAgeGroup(javafx.event.ActionEvent e){
        type = ageGroup.getValue();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ageGroup.getItems().addAll(types);
        ageGroup.setOnAction(this::getAgeGroup);
    }

    public void registerChild(javafx.event.ActionEvent e) throws IOException {
        System.out.println("Реєстрація нової дитини\nВведіть\n\tім'я,\n\tпрізвище,\n\tвікову групу(1-TODDLER, 2-MIDDLECHILD, 3-TEENAGER),\n\tконтакт до батьків");
        String firstName = firstNameField.getText();
        if(firstName.equals(""))
            firstName = "Богдан";

        String lastName = lastNameField.getText();
        if(lastName.equals(""))
            lastName = "Пономаренко";

        int id = 0;

        if(type.equals("")){
            id = 1;
        }else{
            for(int i = 0; i < types.length;i++){
                if(types[i].equals(type)){
                    id = i + 1;
                    break;
                }
            }
        }

        String credentials = parentsCredentialsField.getText();
        if(credentials.equals("") )
            credentials = "+48072612399";

        firstNameField.clear();
        lastNameField.clear();
        parentsCredentialsField.clear();

        Main.adm.getPlayRoom().registerChild(new Child(firstName,lastName, AgeGroup.getAgeGroupByOrd(id),credentials));
        new Controller().changeScene("/resources/Main.fxml",e);
    }
}
