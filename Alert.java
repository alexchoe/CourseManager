import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

/**
 *
 * @author Alex Choe
 */
public class Alert {
    //A small alert box that contains a message and a OK button
    public static void display(String title, String message){
        Stage window = new Stage();
        //prevents other window from being clicked until alert page is closed
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(130);
        
        //label with message given in the parameter
        Label label = new Label(message);
        Button button = new Button("OK");
        button.setOnAction(e -> window.close());
        
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("theme.css");
        window.setScene(scene);
        window.showAndWait();
    }
}
