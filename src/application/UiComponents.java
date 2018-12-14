package application;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UiComponents {

	private String filePath;
	private GridPane grid;
	public ObservableList<FoodItem> foodList = FXCollections.observableArrayList();
	public ObservableList<FoodItem> mealList = FXCollections.observableArrayList();
	private ObservableValue<String> foodCount;

	public UiComponents(Stage stage) {
		this.grid = new GridPane();
		createScene(stage);
	}

	public void createScene(Stage stage) {

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(20);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(40);
		ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(40);
		
		
		Button addFoodBtn = new Button("Add Food");	
		
		addFoodBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		grid.getColumnConstraints().addAll(column1, column2, column3);
		grid.setHgap(10); 
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));

		grid.add(planMealLabel(), 1, 0);
		grid.add(filterFoodButton(stage), 2, 4);
		grid.add(addFoodBtn, 1, 4);
		grid.add(clickLabel(), 2, 1);
		grid.add(foodItemList(), 1, 2);
		grid.add(mealList(), 2, 2);
	    grid.add(foodCountLabel(), 1, 1);
		grid.add(loadFoodButton(stage), 1, 3);
		grid.add(analyzeMealButton(stage), 2, 3);
		grid.add(radioButton(stage), 0, 2);
		
	}
		public VBox radioButton(Stage primaryStage ) {
		    ToggleGroup group = new ToggleGroup();
	        RadioButton calories = new RadioButton("Calories");
	        RadioButton fat = new RadioButton("Fat");
	        RadioButton carbohydrate = new RadioButton("Carbohydrate");
	        RadioButton fiber = new RadioButton("fiber");
	        RadioButton protein = new RadioButton("protein");
	        
	        calories.setToggleGroup(group);
	        fat.setToggleGroup(group);
	        carbohydrate.setToggleGroup(group);
	        fiber.setToggleGroup(group);
	        protein.setToggleGroup(group);
		    
		    VBox vb = new VBox();
		    vb.setPadding(new Insets(40, 0, 0, 0));
		    vb.setSpacing(20);

		    vb.getChildren().addAll(calories, fat, carbohydrate, fiber, protein);
		    
		    return vb;
		}
		
		
		
		
	

    

	public Button filterFoodButton(Stage primaryStage) {
	    Button filterFoodBtn = new Button("Filter Food");
	    filterFoodBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	    filterFoodBtn.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                   
                    final Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(primaryStage);
                    VBox dialogVbox = new VBox(20);
                    Button enter = new Button("enter value");
                    dialogVbox.getChildren().addAll(new Text("Number of Foods in Meal: " ), enter);
                    Scene dialogScene = new Scene(dialogVbox, 300, 200);
                    dialog.setScene(dialogScene);
                    dialog.show();
                }
             });
	    return filterFoodBtn;
	
	}
	
	public GridPane getGrid() {
		return grid;
	}

	private Label foodCountLabel() {
		Label labelL = new Label("There are " + foodList.size() + " food items");
		//labelL.textProperty().bind(foodCount);;
		labelL.setPadding(new Insets(2,2,2,2)); 
		labelL.setStyle("-fx-background-color: Gainsboro;-fx-border-color: black;");
		return labelL;
	}

	private Button analyzeMealButton(Stage primaryStage) {
		Button analyzeMealButton = new Button("Analyze Meal");
		analyzeMealButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		analyzeMealButton.setOnAction(
		        new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	double totalCalories = 0;
		            	double totalFat = 0;
		            	double totalFiber = 0;
		            	double totalProtein = 0;
		            	double totalCarbohydrates = 0;
		
		            	for(int i = 0; i < mealList.size(); i++) {
		            		totalCalories = totalCalories + mealList.get(i).getNutrientValue("calories");
		            		totalFat = totalFat + mealList.get(i).getNutrientValue("fat");
		            		totalFiber = totalFiber + mealList.get(i).getNutrientValue("fiber");
		            		totalCarbohydrates = totalCarbohydrates + mealList.get(i).getNutrientValue("carbohydrate");
		            		totalProtein = totalProtein + mealList.get(i).getNutrientValue("protein");
		            	}
		                final Stage dialog = new Stage();
		                dialog.initModality(Modality.APPLICATION_MODAL);
		                dialog.initOwner(primaryStage);
		                VBox dialogVbox = new VBox(20);
		                dialogVbox.getChildren().add(new Text("Number of Foods in Meal: " + mealList.size() + 
		                		"\n" + "Total Calories: " + totalCalories + "\n" + "Total Fat: " + totalFat +  "\n" 
		                		+ "Total Carbohydrates: " + totalCarbohydrates + "\n" + "Total Fiber: " + totalFiber
		                		+ "\n" + "Total Protein: " + totalProtein + "\n\n" + "END OF ANALYSIS"));
		                Scene dialogScene = new Scene(dialogVbox, 300, 200);
		                dialog.setScene(dialogScene);
		                dialog.show();
		            }
		         });
		return analyzeMealButton;
	}

	private Label clickLabel() {
		Label label = new Label("\u2193 Double-Click to add foods to a meal \u2193");
		label.setTextFill(Color.RED);
		label.setPadding(new Insets(5,5,5,5));  
		GridPane.setHalignment(label, HPos.CENTER);
		label.setStyle("-fx-font-weight: bold");

		return label;
	}

	private Label planMealLabel() {
		Label title = new Label("Plan Your Meal!");
		title.setPadding(new Insets(5,5,5,5)); 
		title.setStyle("-fx-font: 30px Tahoma;");
		title.setUnderline(true);
		title.setAlignment(Pos.TOP_LEFT);
		title.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		return title;
	}

	private Button loadFoodButton(Stage stage) {
		Button btn = new Button("Load Food"); 
		btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		final FileChooser fChoose = new FileChooser();
		fChoose.getExtensionFilters().add( new FileChooser.ExtensionFilter("CSV", "*.csv"));
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File f = fChoose.showOpenDialog(stage);
				if (f != null) {
					filePath = f.getPath();
				}
			}
		});

		return btn;
	}

//	public Button addFoodMenu() {
//		Button addFood = new Button("Add Food");
//		addFood.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(final ActionEvent e) {
//				
//			}
//				
//		});
//	}

	private TableView<FoodItem> foodItemList() {
		TableView<FoodItem> tableL = new TableView<FoodItem>();
		TableColumn<FoodItem, String> foodColumn = new TableColumn<FoodItem, String>("Food");
		foodColumn.setSortType(TableColumn.SortType.DESCENDING);
		//foodColumn.

		FoodData initialFoodData = new FoodData();
		initialFoodData.loadFoodItems(Paths.get(System.getProperty("user.dir"), "foodItems.csv").toString());
		
		//final ObservableList<FoodItem> foodList = FXCollections.observableArrayList();
		//ObservableList<FoodItem> mealList = FXCollections.observableArrayList();
		
		for (FoodItem foodItem : initialFoodData.getAllFoodItems()) {
			foodList.add(foodItem);
		}

		foodColumn.setCellValueFactory( new PropertyValueFactory<>("name"));
		tableL.setItems(foodList);
		tableL.getColumns().add(foodColumn);
		tableL.setRowFactory(tv -> {
		    TableRow<FoodItem> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (!row.isEmpty())) {
		            FoodItem selectedFood = row.getItem();
		            mealList.add(selectedFood);
		        }
		    });
		    return row;
		});
		return tableL;
	}

	private TableView<FoodItem> mealList() {
		TableView<FoodItem> table = new TableView<FoodItem>();
		TableColumn<FoodItem, String> mealColumn = new TableColumn<FoodItem, String>("Meal");
		mealColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
	    table.setItems(mealList);
		table.getColumns().add(mealColumn);
		return table;
	}

}
