package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import model.dto.Item;
import service.ItemService;
import service.impl.ItemServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PosViewController implements Initializable {

    ItemService itemService = new ItemServiceImpl();

    @FXML
    private JFXButton btnAllItems;

    @FXML
    private JFXButton btnCancelOrder;

    @FXML
    private JFXButton btnCheckout;

    @FXML
    private JFXButton btnClearSort;

    @FXML
    private JFXButton btnCustomers;

    @FXML
    private TableColumn<?, ?> colDescount;

    @FXML
    private TableColumn<?, ?> colItem;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private ComboBox<?> comboBrand;

    @FXML
    private ComboBox<?> comboCategory;

    @FXML
    private ComboBox<?> comboType;

    @FXML
    private FlowPane flowProducts;

    @FXML
    private ImageView imgUser;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDescount;

    @FXML
    private Label lblNetToal;

    @FXML
    private Label lblRole;

    @FXML
    private Label lblSortOrder;

    @FXML
    private Label lblSubTotal;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblUserName;

    @FXML
    private JFXListView<?> lstSuggestions;

    @FXML
    private ScrollPane scrollProducts;

    @FXML
    private TableView<?> tblSummery;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnAllItemsonAction(ActionEvent event) {
        loadItemCards();
    }

    @FXML
    void btnCancelOrderonAction(ActionEvent event) {

    }

    @FXML
    void btnCheckoutonAction(ActionEvent event) {

    }

    @FXML
    void btnClearSortonAction(ActionEvent event) {

    }

    @FXML
    void btnCustomersonAction(ActionEvent event) {

    }

    @FXML
    void comboBrandonAction(ActionEvent event) {

    }

    @FXML
    void comboCategoryonAction(ActionEvent event) {

    }

    @FXML
    void comboTypeonAction(ActionEvent event) {

    }

    @FXML
    void txtSearchonAction(ActionEvent event) {

    }

    @FXML
    void txtSearchonKeyReleased(KeyEvent event) {

    }

    private void loadItemCards(){
        flowProducts.getChildren().clear();
        ObservableList<Item> allItems = itemService.getAllItems();

        for (Item item : allItems){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/pos_item_card.fxml"));
                Parent card = loader.load();

                PosItemCardController cardController = loader.getController();
                cardController.setCardData(item);

                flowProducts.getChildren().add(card);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
