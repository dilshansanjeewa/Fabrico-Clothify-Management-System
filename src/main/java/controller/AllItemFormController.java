package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import model.dto.Item;
import service.ItemService;
import service.impl.ItemServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AllItemFormController implements Initializable {
    
    private ItemService itemService = new ItemServiceImpl();

    @FXML
    private JFXButton btnClear;

    @FXML
    private ComboBox<?> comboBrand;

    @FXML
    private ComboBox<?> comboCategory;

    @FXML
    private ComboBox<?> comboPrice;

    @FXML
    private FlowPane flowCardContainer;

    @FXML
    private ListView<?> lstSuggestions;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void comboBrandOnAction(ActionEvent event) {

    }

    @FXML
    void comboCategoryOnAction(ActionEvent event) {

    }

    @FXML
    void comboPriceOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    private void loadItemCards(){
        flowCardContainer.getChildren().clear();
        ObservableList<Item> allItems = itemService.getAllItems();

        for (Item item : allItems){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/item_card_view.fxml"));
                Parent itemCard = loader.load();

                ItemCardViewFormController cardController = loader.getController();
                cardController.setCardDate(item);

                flowCardContainer.getChildren().add(itemCard);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadItemCards();
    }
}
