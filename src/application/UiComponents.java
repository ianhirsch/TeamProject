package application;

import java.io.File;
import java.nio.file.Paths;

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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UiComponents {
	
	private String filePath;
	
	public UiComponents() {
		
	}
	
	public GridPane createScene() {
		GridPane grid = new GridPane();
		
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
		grid.add(loadFoodBtn, 0, 3);
		grid.add(analyzeMealButton, 1, 3);
	}
	
	public Button filterFoodButton() {
		
	}
	
	public Button analyzeMealButton(Stage primaryStage) {
		Button analyzeMealButton = new Button("Analyze Meal");
		analyzeMealButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		analyzeMealButton.setOnAction(
		        new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		                final Stage dialog = new Stage();
		                dialog.initModality(Modality.APPLICATION_MODAL);
		                dialog.initOwner(primaryStage);
		                VBox dialogVbox = new VBox(20);
		                dialogVbox.getChildren().add(new Text("Dialog"));
		                Scene dialogScene = new Scene(dialogVbox, 300, 200);
		                dialog.setScene(dialogScene);
		                dialog.show();
		            }
		         });
		return analyzeMealButton;
	}
	
	public Label clickLabel() {
		Label label = new Label("\u2193 Click foods to add them to a meal \u2193");
		label.setTextFill(Color.RED);
		label.setPadding(new Insets(5,5,5,5));  
		GridPane.setHalignment(label, HPos.CENTER);
		label.setStyle("-fx-font-weight: bold");
		
		return label;
	}
	
	public Label planMealLabel() {
		Label title = new Label("Plan Your Meal!");
		title.setPadding(new Insets(5,5,5,5)); 
		title.setStyle("-fx-font: 30px Tahoma;");
		title.setUnderline(true);
		title.setAlignment(Pos.TOP_LEFT);
		title.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		return title;
	}

	public Button loadFoodButton(Stage stage) {
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

	public Menu addFoodMenu() {
	
	}
	
	public void foodItemList() {
		
	}
	
	public void mealList() {
		
	}
	
	public ScrollPane leftScrollPane() {
		ScrollPane spL = new ScrollPane();
		
		FoodData initialFoodData = new FoodData();
		initialFoodData.loadFoodItems(Paths.get(System.getProperty("user.dir"), "foodItems.csv").toString());

		final ObservableList<FoodItem> foodList = FXCollections.observableArrayList();
		for (FoodItem foodItem : initialFoodData.getAllFoodItems()) {
			foodList.add(foodItem);
		}
		
		foodColumn.setCellValueFactory( new PropertyValueFactory<>("name"));
		tableL.setItems(foodList);
		tableL.getColumns().add(foodColumn);
		spL.setContent(tableL);
	}
}
