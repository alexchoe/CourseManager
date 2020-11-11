import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;

/**
 *
 * @author Alex Choe
 */
public class Login {
    public static void display(Stage window){
        //adding image
        final ImageView selectedImage = new ImageView();   
        Image image1 = new Image("EOM_Logo.png", 50, 80, false, false);
        selectedImage.setImage(image1);

        //main layout
        BorderPane layout = new BorderPane();
        
        //Top section with logo and name
        HBox top = new HBox(20);
        top.setStyle("-fx-background-color: #2e393f");
        top.setAlignment(Pos.CENTER);
        top.setPadding(new Insets(10, 10, 10, 10));
        
        Label name = new Label("Course Management System");
        name.setStyle("-fx-text-fill: white; -fx-font-size: 30pt;");
        //adding image and label
        top.getChildren().addAll(selectedImage, name);
        layout.setTop(top);
        
        //username, password text field
        GridPane grid = new GridPane();
        grid.setId("LoginBackground");
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);
        
        
        TextField userInput = new TextField();
        GridPane.setHalignment(userInput, HPos.CENTER);
        userInput.setPromptText("username");
        userInput.setMinWidth(215);
        GridPane.setConstraints(userInput, 0, 1);
        
        //when program starts, the username textfield is clicked on by default
        //this removes the focus from the texfield
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        userInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue && firstTime.get()){
                grid.requestFocus();
                firstTime.setValue(false);
            }
        });
        
        
        PasswordField passInput = new PasswordField();
        GridPane.setHalignment(passInput, HPos.CENTER);
        passInput.setMinWidth(215);
        passInput.setPromptText("password");
        GridPane.setConstraints(passInput, 0, 2);
        
        //This empty label will have the text, incorrect username or password
        //when user enters wrong password or username
        Label info = new Label("Incorrect username or password");
        GridPane.setHalignment(info, HPos.CENTER);
        info.setStyle("-fx-text-fill: transparent; -fx-font-size: 11pt;");
        GridPane.setConstraints(info, 0, 0);
        
        Button login = new Button("Log in");
        GridPane.setHalignment(login, HPos.CENTER);
        login.setStyle("-fx-pref-width: 215px");
        login.setDefaultButton(true);
        login.setOnAction(e -> {
            //if username and password is correct, it continues to the homescreen page
            if(userInput.getText().equals("alex.choe") && passInput.getText().equals("1234")){
                HomeScreen.display(window);
            }
            //if wrong, it sets the empty label to the message, and empties the password field
            else{
                info.setStyle("-fx-text-fill: red; -fx-font-size: 11pt;");
                passInput.setText("");
            }
        });
        GridPane.setConstraints(login, 0, 3);
        
        grid.getChildren().addAll(info, userInput, passInput, login);
        layout.setCenter(grid);
        
        //this code makes the window maximized when opened. If user changes the size by 
        //manually dragging the corner, it will maintain that size when the scene changes
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        window.setX(bounds.getMinX());
        window.setY(bounds.getMinY());
        window.setWidth(bounds.getWidth());
        window.setHeight(bounds.getHeight());
        
        Scene scene = new Scene(layout, 800, 600);
        scene.getStylesheets().add("theme.css");
        window.setScene(scene);
        window.show();
        
    }
}
