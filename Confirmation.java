import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

/**
 *
 * @author Alex Choe
 */
public class Confirmation {
    //store user's answer
    static boolean answer;
    
    //a small confirmation box with message and a yes or no buttons
    public static boolean display(String title, String message){
        Stage window = new Stage();
        //prevents other windows to be clicked until confirmation box is closed
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(130);
        
        //label set with the message from the parameter
        Label label = new Label(message);
        HBox messageLabel = new HBox();
        messageLabel.getChildren().addAll(label);
        messageLabel.setAlignment(Pos.CENTER);
        
        //sets the boolean to true or false based on button input
        Button yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        
        Button noButton = new Button("No");
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });
        
        //layout
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(messageLabel);
        
        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(noButton, yesButton);
        
        BorderPane layout = new BorderPane();
        layout.setTop(vbox);
        layout.setCenter(hbox);
        
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("theme.css");
        window.setScene(scene);
        window.showAndWait();
        
        return answer;
    }
}
