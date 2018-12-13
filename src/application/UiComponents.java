package application;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UiComponents {
	
	public UiComponents() {
		createScene();
	}
	
	public GridPane createScene() {
		GridPane grid = new GridPane();

		final MenuBar menu = new MenuBar();

		Menu filterFoodBtn = new Menu("Filter Food");
		Menu loadFoodBtn = new Menu("Load Food");
		Menu addFoodBtn = new Menu("Add Food");
		menu.getMenus().addAll(filterFoodBtn, loadFoodBtn, addFoodBtn);


		Label label = new Label("\u2193 Click foods to add them to a meal \u2193");
		label.setTextFill(Color.RED);
		label.setPadding(new Insets(5,5,5,5));  
		GridPane.setHalignment(label, HPos.CENTER);
		label.setStyle("-fx-font-weight: bold");

		String[] columns = {"Food Name", "Meal?"};



		TableView<FoodItem> table = new TableView<FoodItem>();
		TableColumn<FoodItem, String> mealColumn = new TableColumn<FoodItem, String>("Meal");
		mealColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		final ObservableList<FoodItem> mealList =FXCollections.observableArrayList(
				new FoodItem("0", "Pizza"));
		table.setItems(mealList);
		table.getColumns().add(mealColumn);

		ScrollPane sp = new ScrollPane();
		sp.setContent(table);

		Label labelL = new Label("Foods (3 items)");
		//GridPane.setHalignment(labelL, HPos.CENTER);
		labelL.setPadding(new Insets(2,2,2,2)); 
		labelL.setStyle("-fx-background-color: Gainsboro;-fx-border-color: black;");


		TableView<FoodItem> tableL = new TableView<FoodItem>();
		TableColumn<FoodItem, String> foodColumn = new TableColumn<FoodItem, String>("Food");
		foodColumn.setSortType(TableColumn.SortType.DESCENDING);

		 			 
		ScrollPane spL = new ScrollPane();
		
		FoodData initialFoodData = new FoodData();
		initialFoodData.loadFoodItems("foodItems.csv");

		final ObservableList<FoodItem> foodList = FXCollections.observableArrayList();
		for (FoodItem foodItem : initialFoodData.getAllFoodItems()) {
			foodList.add(foodItem);
		}
		
		foodColumn.setCellValueFactory( new PropertyValueFactory<>("name"));		
		tableL.setItems(foodList);
		tableL.getColumns().add(foodColumn);
		spL.setContent(tableL);

		Label title = new Label("Plan Your Meal!");
		title.setPadding(new Insets(5,5,5,5)); 
		title.setStyle("-fx-font: 30px Tahoma;");
		title.setUnderline(true);
		title.setAlignment(Pos.TOP_LEFT);
		title.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);


		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(50);

		Button analyzeMealButton = new Button("Analyze Meal");
		analyzeMealButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		grid.getColumnConstraints().addAll(column1, column2);
		grid.setHgap(10); 
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));



		grid.add(title, 0, 0);
		grid.add(menu, 1, 0);
		grid.add(labelL, 0, 1);
		grid.add(label, 1, 1);
		grid.add(sp, 1, 2);
		grid.add(spL, 0, 2);
		grid.add(analyzeMealButton, 1, 3);
		return grid;
	}
	
	public Button filterFoodButton() {
		
	}
	
	public void analyzeMealButton() {
		
	}

	public Menu loadFoodButton(Stage stage) {
		Menu menu = new Menu("Load Food"); 
		
		final FileChooser fChoose = new FileChooser();
		menu.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File f = fChoose.showOpenDialog(stage);
			}
		});
	}

	public void addFoodButton() {
	
	}
}
