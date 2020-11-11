import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
public class AddCourseAddExp {
    public static void display(Stage window, int courseNo){
        //grid layout that will contain labels and textfields
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(15);
        
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
        
        Label addExpectation = new Label("Add Expectation");
        addExpectation.setStyle("-fx-font-size: 15pt; -fx-font-weight: bold;");
        AnchorPane.setTopAnchor (addExpectation, 14.0);
        AnchorPane.setLeftAnchor(addExpectation, 10.0);
        
        //main menu - makes the labels and texfields for expectation and description
        Label expectationLabel = new Label("Expectation:");
        GridPane.setConstraints(expectationLabel, 0, 1);
        TextField expectationInput = new TextField();
        GridPane.setConstraints(expectationInput, 1, 1);
        expectationInput.setPrefWidth(400);
        
        Label descriptionLabel = new Label("Description:");
        GridPane.setConstraints(descriptionLabel, 0, 2);
        TextArea descriptionInput = new TextArea();
        GridPane.setConstraints(descriptionInput, 1, 2);
        descriptionInput.setPrefSize(400, 130);
      
        
        HBox bottomMenu = new HBox(15);
        bottomMenu.setPadding(new Insets(10, 10, 10, 10));
        bottomMenu.setAlignment(Pos.CENTER_RIGHT);
        
        //Bottom menu
        //Cancel button opens the AddCourse page, starting at tab 2
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> {
            AddCourse.display(window, 2, courseNo);
        });
        
        //It inserts into the database the values entered
        Button save = new Button("Save");
        save.setOnAction(e -> {
            if(expectationInput.getText().equals("") || descriptionInput.getText().equals("")){
                Alert.display("Error", "All fields must be completed");
            }
            else{
                SqliteDB db = new SqliteDB();
                db.executeQuery("INSERT INTO CourseExpectation (courseNo, expectation, description) VALUES (" + 
                        courseNo + ", '" + expectationInput.getText() + "', '" + descriptionInput.getText() + "')");
                db.closeConnection();
                AddCourse.display(window, 2, courseNo);
            }
        });
        
        //adding nodes to layout
        grid.getChildren().addAll(expectationLabel, expectationInput, descriptionLabel, descriptionInput);
       
        AnchorPane topMenu = new AnchorPane();
        topMenu.getChildren().addAll(addExpectation, home);
        bottomMenu.getChildren().addAll(cancel, save);
        
        BorderPane layout = new BorderPane();
        layout.setLeft(grid);
        layout.setTop(topMenu);
        layout.setBottom(bottomMenu);
        
        Scene scene = new Scene(layout, 800, 600);
        scene.getStylesheets().add("theme.css");
        window.setScene(scene);
        window.show();
    }
}
