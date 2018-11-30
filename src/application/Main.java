package TeamProject.src.application;

import java.util.ArrayList;

import javax.swing.JTable;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;


public class Main extends Application {	
	@Override
	public void start(Stage primaryStage) {
		try {			 
			 
			
			GridPane grid = new GridPane();
			 
			
			final MenuBar menu = new MenuBar();
			
			 Menu filterFoodBtn = new Menu("Filter Food");
			 Menu loadFoodBtn = new Menu("Load Food");
			 Menu addFoodBtn = new Menu("Add Food");
			 menu.getMenus().addAll(filterFoodBtn, loadFoodBtn, addFoodBtn);
			 
			 
			 Label label = new Label("Foods (69 items)");
			 label.setPadding(new Insets(5,5,5,5));  
			 label.setStyle("-fx-background-color: Gainsboro;-fx-border-color: black;");
			
			 
			 String[] columns = {"Food Name", "Meal?"};
			 
			 TableView<String> table = new TableView<String>();
			 TableColumn<String, String> mealColumn = new TableColumn<String, String>("Meal Name");
			 TableColumn<String, String> calorieColumn = new TableColumn<String, String>("Total Calories");
			 table.getColumns().addAll(mealColumn, calorieColumn);
			 
			 ScrollPane sp = new ScrollPane();
			 sp.setContent(table);
			 
			 Label labelL = new Label("Foods (6543 items)");
			 labelL.setPadding(new Insets(5,5,5,5)); 
			 labelL.setStyle("-fx-background-color: Gainsboro;-fx-border-color: black;");
			 
			 
			 TableView<String> tableL = new TableView<String>();
			 TableColumn<String, String> addMealColumn = new TableColumn<String, String>("Food");
			 TableColumn<String, String> foodColumn = new TableColumn<String, String>("Add To Meal");
			 tableL.getColumns().addAll(addMealColumn, foodColumn);
			 ScrollPane spL = new ScrollPane();
			 spL.setContent(tableL);
			 

			 Label title = new Label("Meal Planner");
			 title.setPadding(new Insets(5,5,5,5)); 
			 title.setStyle("-fx-background-color: Gainsboro;-fx-border-color: black;");
			 title.setAlignment(Pos.TOP_LEFT);
			 ColumnConstraints column1 = new ColumnConstraints();
			 column1.setPercentWidth(50);
			 ColumnConstraints column2 = new ColumnConstraints();
			 column2.setPercentWidth(50);
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
			 
			 
			Scene scene = new Scene(grid, 550, 550);
			primaryStage.setTitle("StackPane Layout Demo");
		    primaryStage.setScene(scene);
		    primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
