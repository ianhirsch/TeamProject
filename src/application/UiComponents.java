package application;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UiComponents {

	public String filePath;
	private GridPane grid;
	public ObservableList<FoodItem> foodList = FXCollections.observableArrayList();
	public ObservableList<FoodItem> mealList = FXCollections.observableArrayList();
	public ObservableList<Integer> foodCount = FXCollections.observableArrayList();
	public ObservableList<String> ruleNutrient = FXCollections.observableArrayList();
	public TextField tf;
	public String selectedRuleNutrient;
	public String selectedRuleComparator;
	public Label labelL = new Label();

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

		VBox vb = new VBox();
		vb.getChildren().addAll(creatFilterLabel(), nutrientFilter(), valueFilter(), comparatorFilter(),
				addRuleButton(), filterFoodButton(stage));
		vb.setSpacing(10);
		vb.setStyle("-fx-background-color: Gainsboro;-fx-border-color: black;");

		grid.add(planMealLabel(), 0, 0);
		grid.add(addFoodBtn, 1, 1);
		grid.add(clickLabel(), 2, 1);
		grid.add(foodItemList(), 1, 2);
		grid.add(foodCountLabel(), 1, 0);
		grid.add(mealList(), 2, 2);
		grid.add(loadFoodButton(stage), 1, 3);
		grid.add(analyzeMealButton(stage), 2, 3);
		grid.add(vb, 0, 2);
		grid.add(ruleList(), 0, 3);

	}

	public Label creatFilterLabel() {
		Label lb = new Label("Create Custom Filter");
		lb.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		lb.setAlignment(Pos.CENTER);
		lb.setStyle("-fx-border-color: black;");
		return lb;
	}

	public VBox nutrientFilter() {
		ToggleGroup group = new ToggleGroup();
		RadioButton calories = new RadioButton("Calories");
		calories.setUserData("Calories");
		RadioButton fat = new RadioButton("Fat");
		fat.setUserData("Fat");
		RadioButton carbohydrate = new RadioButton("Carbohydrate");
		carbohydrate.setUserData("Carbohydrates");
		RadioButton fiber = new RadioButton("Fiber");
		fiber.setUserData("Fiber");
		RadioButton protein = new RadioButton("Protein");
		protein.setUserData("Protein");

		calories.setToggleGroup(group);
		fat.setToggleGroup(group);
		carbohydrate.setToggleGroup(group);
		fiber.setToggleGroup(group);
		protein.setToggleGroup(group);

		VBox vb = new VBox();
		vb.setPadding(new Insets(10, 0, 0, 10));
		vb.setSpacing(10);

		vb.getChildren().addAll(calories, fat, carbohydrate, fiber, protein);

		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle,Toggle new_toggle) {
				selectedRuleNutrient = group.getSelectedToggle().getUserData().toString();
			}
		});

		return vb;
	}

	public TextField valueFilter() {
		tf = new TextField("Enter a number");
		return tf;
	}

	public VBox comparatorFilter() {
		ToggleGroup group = new ToggleGroup();
		RadioButton lessEqual = new RadioButton("<=");
		lessEqual.setUserData("<=");
		RadioButton equal = new RadioButton("=");
		equal.setUserData("=");
		RadioButton greatEqual = new RadioButton(">=");
		greatEqual.setUserData(">=");

		lessEqual.setToggleGroup(group);
		equal.setToggleGroup(group);
		greatEqual.setToggleGroup(group);

		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle,Toggle new_toggle) {
				selectedRuleComparator = group.getSelectedToggle().getUserData().toString();
			}
		});

		VBox vb = new VBox();
		vb.setPadding(new Insets(10, 0, 0, 10));
		vb.setSpacing(10);
		vb.getChildren().addAll(lessEqual, equal, greatEqual);

		return vb;
	}

	public ListView<String> ruleList() {
		ListView<String> list = new ListView<String>();
		list.setItems(ruleNutrient);
		TableColumn<String,String> column = new TableColumn<String,String>("Rule");
		//list.s
		column.setCellValueFactory(new PropertyValueFactory<>("name"));
		return list;
	}

	public Button addRuleButton() {
		Button btn = new Button("Add Rule");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				StringBuilder sb = new StringBuilder();
				sb.append(selectedRuleNutrient + " ");
				sb.append(selectedRuleComparator + " ");
				sb.append(tf.getText());
				ruleNutrient.add(sb.toString());
			}});
		return btn;
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
		Label labelL = new Label("There are " + foodCount.get(0) + " food items");
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
		title.setStyle("-fx-font: 18px Tahoma;");
		title.setUnderline(true);
		title.setAlignment(Pos.TOP_LEFT);
		title.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		return title;
	}

	private Button loadFoodButton(Stage stage) {
		Button btn = new Button("Load Food"); 
		btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		final FileChooser fChoose = new FileChooser();
		fChoose.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("CSV", "*.csv"),
				new FileChooser.ExtensionFilter("TXT", "*.txt"));
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File f = fChoose.showOpenDialog(stage);
				if (f != null) {
					filePath = f.getPath();
					FoodData fd = new FoodData();
					fd.loadFoodItems(filePath);
					foodList.remove(0, foodList.size()-1);
					for (FoodItem foodItem : fd.getAllFoodItems()) {
						foodList.add(foodItem);
					}
					foodCount.remove(0);
					int u = foodList.size();
					foodCount.add(u);
					int g = foodCount.get(0);
					labelL.setText("There are " + 53214321 + " food items");
					mealList.remove(0, mealList.size());
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
		TableColumn<FoodItem, String> foodColumn = new TableColumn<FoodItem, String>("Food (Click to Alphabetize)");
		foodColumn.setSortType(TableColumn.SortType.DESCENDING);
		//foodColumn.

		FoodData initialFoodData = new FoodData();
		initialFoodData.loadFoodItems(Paths.get(System.getProperty("user.dir"), "foodItems.csv").toString());

		for (FoodItem foodItem : initialFoodData.getAllFoodItems()) {
			foodList.add(foodItem);
		}

		foodColumn.setCellValueFactory( new PropertyValueFactory<>("name"));
		tableL.setItems(foodList);
		foodCount.add(initialFoodData.getAllFoodItems().size());
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
