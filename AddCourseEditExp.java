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
public class AddCourseEditExp {
    public static void display(Stage window, int courseNo, Expectation expectation){
        //grid layout containing labels and textfields
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
        
        Label editExpectation = new Label("Edit Expectation");
        editExpectation.setStyle("-fx-font-size: 15pt; -fx-font-weight: bold;");
        AnchorPane.setTopAnchor (editExpectation, 14.0);
        AnchorPane.setLeftAnchor(editExpectation, 10.0);
        
        //main menu
        //Set textfield values to what they were previously
        Label expectationLabel = new Label("Expectation:");
        GridPane.setConstraints(expectationLabel, 0, 1);
        TextField expectationInput = new TextField(expectation.getExpectation());
        GridPane.setConstraints(expectationInput, 1, 1);
        expectationInput.setPrefWidth(400);
        
        Label descriptionLabel = new Label("Description:");
        GridPane.setConstraints(descriptionLabel, 0, 2);
        TextArea descriptionInput = new TextArea(expectation.getDescription());
        GridPane.setConstraints(descriptionInput, 1, 2);
        descriptionInput.setPrefSize(400, 130);
      
        
        //Bottom menu
        HBox bottomMenu = new HBox(15);
        bottomMenu.setPadding(new Insets(10, 10, 10, 10));
        bottomMenu.setAlignment(Pos.CENTER_RIGHT);
        
        //cancel button opens the AddCourse page at tab 2
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> {
            AddCourse.display(window, 2, courseNo);
        });
        
        //Updates the row in the database with new information from user inputs
        Button save = new Button("Save");
        save.setOnAction(e -> {
            if(expectationInput.getText().equals("") || descriptionInput.getText().equals("")){
                Alert.display("Error", "All fields must be completed");
            }
            else{
                SqliteDB db = new SqliteDB();
                db.executeQuery("UPDATE CourseExpectation SET expectation = '" + expectationInput.getText() + 
                        "', description = '" + descriptionInput.getText() + "' WHERE courseExpNo = " + expectation.getCourseExpNo());
                db.closeConnection();
                AddCourse.display(window, 2, courseNo);
            }
        });
        
        //adds nodes to layout
        grid.getChildren().addAll(expectationLabel, expectationInput, descriptionLabel, descriptionInput);
        
        AnchorPane topMenu = new AnchorPane();
        topMenu.getChildren().addAll(editExpectation, home);
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
