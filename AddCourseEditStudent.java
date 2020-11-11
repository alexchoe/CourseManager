import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
//import java.util.regex.Matcher; 
import java.util.regex.Pattern; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 *
 * @author Alex Choe
 */
public class AddCourseEditStudent {
    public static void display(Stage window, int courseNo, CourseStudent student){
        
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
        
        Label editStudent = new Label("Edit Student");
        editStudent.setStyle("-fx-font-size: 15pt; -fx-font-weight: bold;");
        AnchorPane.setTopAnchor (editStudent, 14.0);
        AnchorPane.setLeftAnchor(editStudent, 10.0);
        
        //main menu
        //Sets textfield/dropdown values to what they were previously
        Label firstNameLabel = new Label("First Name:");
        GridPane.setConstraints(firstNameLabel, 0, 1);
        TextField firstNameInput = new TextField(student.getFirstName());
        GridPane.setConstraints(firstNameInput, 1, 1);
        firstNameInput.setPrefWidth(400);
        
        Label lastNameLabel = new Label("Last Name:");
        GridPane.setConstraints(lastNameLabel, 0, 2);
        TextField lastNameInput = new TextField(student.getLastName());
        GridPane.setConstraints(lastNameInput, 1, 2);
        lastNameInput.setPrefWidth(400);
        
        Label IDLabel = new Label("Student ID:");
        GridPane.setConstraints(IDLabel, 0, 3);
        TextField IDInput = new TextField(student.getId());
        GridPane.setConstraints(IDInput, 1, 3);
        IDInput.setPrefWidth(400);
        
        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 4);
        TextField emailInput = new TextField(student.getEmail());
        GridPane.setConstraints(emailInput, 1, 4);
        emailInput.setPrefWidth(400);
        
        Label gradeLabel = new Label("Grade:");
        GridPane.setConstraints(gradeLabel, 0, 5);
        ChoiceBox<Integer> gradeInput = new ChoiceBox<>();
        GridPane.setConstraints(gradeInput, 1, 5);
        gradeInput.getItems().addAll(9, 10, 11, 12);
        gradeInput.setPrefWidth(400);
        gradeInput.setValue(student.getGrade());
        
        //Bottom menu
        HBox bottomMenu = new HBox(15);
        bottomMenu.setPadding(new Insets(10, 10, 10, 10));
        bottomMenu.setAlignment(Pos.CENTER_RIGHT);
        
        //goes back to AddCourse page at tab 3
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> {
            AddCourse.display(window, 3, courseNo);
        });
        
        //Updates row in database with new info user entered
        Button save = new Button("Save");
        save.setOnAction(e -> {
            if(firstNameInput.getText().equals("") || lastNameInput.getText().equals("") || IDInput.getText().equals("") || emailInput.getText().equals("") || gradeInput.getValue() == null){
                Alert.display("Error", "All fields must be completed");
            }
            else{
                if(!isValid(emailInput.getText())){
                    Alert.display("Error", "Not a valid email address");
                }
                else{
                    SqliteDB db = new SqliteDB();
                    db.executeQuery("UPDATE CourseStudent SET studentID = '" + IDInput.getText() + "', firstName = '" + 
                            firstNameInput.getText() + "', lastName = '" + lastNameInput.getText() + "', email = '" + 
                            emailInput.getText() + "', grade = " + gradeInput.getValue() + " WHERE studentNo = " +
                            student.getStudentNo());
                    db.closeConnection();
                    AddCourse.display(window, 3, courseNo);
                }
            }
        });
        
        //adds nodes to layouts
        grid.getChildren().addAll(firstNameLabel, firstNameInput, lastNameLabel, lastNameInput, 
                                  IDLabel, IDInput, emailLabel, emailInput, gradeLabel, gradeInput);
        
        AnchorPane topMenu = new AnchorPane();
        topMenu.getChildren().addAll(editStudent, home);
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
    
    //checks if email entered is valid
    public static boolean isValid(String email){ 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null){ 
            return false; 
        }
        return pat.matcher(email).matches(); 
    }
}
