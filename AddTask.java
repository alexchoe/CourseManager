import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
/**
 *
 * @author Alex Choe
 */
public class AddTask {
    //table containing expectations
    static TableView<Expectation> eTable;
    
    public static void display(Stage window, Course course){
        //grid layout containing labels and textfields
        GridPane input = new GridPane();
        input.setVgap(10);
        input.setHgap(10);
        input.setPadding(new Insets(10, 10, 10, 10));
        BorderPane main = new BorderPane();
        main.setPadding(new Insets(10, 10, 10, 10));
        main.setTop(input);
        
        //Top section
        //adds image to home button
        Image img = new Image("Home.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(25);
        view.setPreserveRatio(true);
        
        Button home = new Button();
        home.setGraphic(view);
        home.setStyle("-fx-max-height: 25; -fx-max-width: 25; -fx-min-height: 25; -fx-min-width: 25;");
        home.setOnAction(e -> HomeScreen.display(window));
        AnchorPane.setTopAnchor(home, 13.0);
        AnchorPane.setRightAnchor(home, 13.0);
        
        Label addTask = new Label("Add Task");
        addTask.setStyle("-fx-font-size: 15pt; -fx-font-weight: bold;");
        AnchorPane.setTopAnchor (addTask, 14.0);
        AnchorPane.setLeftAnchor(addTask, 10.0);
        
        //main menu - labels and textfields/dropdown
        Label taskNameLabel = new Label("Task:");
        GridPane.setConstraints(taskNameLabel, 0, 0);
        TextField taskNameInput = new TextField();
        taskNameInput.setPrefWidth(400);
        GridPane.setConstraints(taskNameInput, 1, 0);
        
        Label taskIDLabel = new Label("Task ID:");
        GridPane.setConstraints(taskIDLabel, 0, 1);
        TextField taskIDInput = new TextField();
        taskIDInput.setPrefWidth(400);
        GridPane.setConstraints(taskIDInput, 1, 1);
        
        Label percentLabel = new Label("Weight:");
        GridPane.setConstraints(percentLabel, 0, 2);
        ChoiceBox<Integer> percentInput = new ChoiceBox<>();
        percentInput.getItems().addAll(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100);
        percentInput.setPrefWidth(400);
        percentInput.setValue(0);
        GridPane.setConstraints(percentInput, 1, 2);
        
        //table showing expectations in that course
        TableColumn<Expectation, String> expectationColumn = new TableColumn<>("Expectation");
        expectationColumn.setMinWidth(90);
        expectationColumn.setCellValueFactory(new PropertyValueFactory("expectation"));
        expectationColumn.setSortable(false);
        
        TableColumn<Expectation, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setMinWidth(400);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory("description"));
        descriptionColumn.setSortable(false);
        
        TableColumn<Expectation, String> checkColumn = new TableColumn<>("Select");
        checkColumn.setMinWidth(80);
        checkColumn.setCellValueFactory(new PropertyValueFactory("select"));
        checkColumn.setSortable(false);
        
        //adds columns to course and sets values into table
        eTable = new TableView<>();
        eTable.setItems(getExpectation(course.getCourseNo()));
        eTable.getColumns().addAll(expectationColumn, descriptionColumn, checkColumn);
        main.setCenter(eTable);
        eTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        //Bottom menu
        HBox bottomMenu = new HBox(15);
        bottomMenu.setPadding(new Insets(10, 10, 10, 10));
        bottomMenu.setAlignment(Pos.CENTER_RIGHT);
        
        //cancel button opens manageCourse page at tab 1
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> {
            ManageCourse.display(window, course, 1);
        });
        
        //Save button
        Button save = new Button("Save");
        save.setOnAction(e -> {
            if(taskNameInput.getText().equals("") || taskIDInput.getText().equals("") || percentInput.getValue() == null){
                Alert.display("Error", "All fields must be completed");
            }
            else if(!isExpSelected()){
                Alert.display("Error", "At least one expectation must be selected");
            } 
            else{
                SqliteDB db = new SqliteDB();
                //Checks if the task name or ID is already being used in that course
                if(db.taskNameExists(course, taskNameInput.getText()) || db.taskIDExists(course, taskIDInput.getText())){
                    Alert.display("Error", "Task name or ID is already being used in this course");
                }
                else{
                    //for each row in expectation table, if that row checkbox is selected,
                    //it adds that expectation and info from textfields into database
                    for(Expectation exp: eTable.getItems()){
                        if(exp.getSelect().isSelected()){
                            db.executeQuery("INSERT INTO CourseTask (courseNo, courseExpNo, taskName, taskID, taskPercent) VALUES (" + 
                                    course.getCourseNo() + ", " + exp.getCourseExpNo() + ", '" + taskNameInput.getText() + 
                                    "', '" + taskIDInput.getText()+ "', " + percentInput.getValue() + ")");

                        }
                    }
                    db.closeConnection();
                    ManageCourse.display(window, course, 1);
                }
                db.closeConnection();
            }
        });
        
        //adds nodes to layouts
        input.getChildren().addAll(taskNameLabel, taskNameInput, taskIDLabel, taskIDInput, percentLabel, percentInput);
        
        AnchorPane topMenu = new AnchorPane();
        topMenu.getChildren().addAll(addTask, home);
        bottomMenu.getChildren().addAll(cancel, save);
        
        BorderPane layout = new BorderPane();
        layout.setCenter(main);
        layout.setTop(topMenu);
        layout.setBottom(bottomMenu);
        
        Scene scene = new Scene(layout, 800, 600);
        scene.getStylesheets().add("theme.css");
        window.setScene(scene);
        window.show();
        
    }
    
    //Shows expectations in a table
    public static ObservableList<Expectation> getExpectation(int courseNo){
        //creates list of Expectation objects
        ObservableList<Expectation> expectations = FXCollections.observableArrayList();
        SqliteDB db = new SqliteDB();
        //fills that list with expectation objects made from database
        db.showExpectations(expectations, courseNo);
        db.closeConnection();
        return expectations;
    }  
    
    //checks if at least 1 expectation is selected
    public static boolean isExpSelected(){
        int counter = 0;
        for(Expectation e: eTable.getItems()){
            if(e.getSelect().isSelected()){
                counter++;
            }
        }
        return (counter > 0);
    }
}
