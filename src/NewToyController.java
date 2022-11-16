import Child.AgeGroup;
import Toy.Toy;
import Toy.ToySize;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewToyController implements Initializable{

    @FXML
    private TextField toyNameField;
    @FXML
    private TextField priceField;
    @FXML
    private ChoiceBox<String> ageGroup;
    @FXML
    private ChoiceBox<String> sizeBox;

    private String[] types = {"TODDLER", "MIDDLECHILD", "TEENAGER"};

    private String[] sizes = {"TINY", "SMALL","MEDIUM","BIG"};

    private String type = "";

    private String size = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ageGroup.getItems().addAll(types);
        ageGroup.setOnAction(this::getAgeGroup);

        sizeBox.getItems().addAll(sizes);
        sizeBox.setOnAction(this::getSize);
    }

    public void getAgeGroup(javafx.event.ActionEvent e){
        type = ageGroup.getValue();
    }

    public void getSize(javafx.event.ActionEvent e){
        size = sizeBox.getValue();
    }

    public void addNewToy(javafx.event.ActionEvent e) throws IOException {
        System.out.println("Додавання нової іграшки у базу даних\nВведіть\n\tназву,\n\tціну,\n\tвікову групу(1-TODDLER, 2-MIDDLECHILD, 3-TEENAGER)\n\tта розмір (1-TINY, 2-SMALL, 3 -> MEDIUM, 4 -> BIG)");
        String name = toyNameField.getText();
        if(name.equals(""))
            name = "Teddy Bear";

        int price = 0;

        try{
            price = Integer.parseInt(priceField.getText());
        }catch (NumberFormatException number){
            price = 350;
        }

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

        int toySize = 0;

        if(size.equals("")){
            toySize = 1;
        }else{
            for(int i = 0; i < sizes.length;i++){
                if(sizes[i].equals(size)){
                    toySize = i + 1;
                    break;
                }
            }
        }
        toyNameField.clear();
        priceField.clear();
        AgeGroup age = AgeGroup.getAgeGroupByOrd(id);
        ToySize size = ToySize.getSizeByOrd(toySize);
        Main.adm.getToyList().addToy(Toy.createToy(0,name,price,age,size));
        new Controller().changeScene("/resources/Main.fxml",e);
    }
}
