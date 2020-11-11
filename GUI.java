import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Alex Choe
 */
public class GUI extends Application{
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage window) throws Exception{
        //names the title of the window
        window.setTitle("Course Management System");
        
        //opens the Login page
        Login.display(window);
    }
    
}
