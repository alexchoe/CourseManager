import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Alex Choe
 */
public class HomeScreen {
    //table containing courses
    static TableView<Course> cTable;
    
    public static void display(Stage window){
        //top section
        AnchorPane top = new AnchorPane();
        Label courseList = new Label("Course List");
        AnchorPane.setBottomAnchor(courseList, 7.0);
        AnchorPane.setLeftAnchor(courseList, 0.0);
        
        HBox buttons = new HBox(15);
        buttons.setPadding(new Insets(0, 0, 7, 0));
        buttons.setAlignment(Pos.CENTER_RIGHT);
        
        //Manage course button
        Button manageCourse = new Button("Task/Mark");
        manageCourse.setStyle("-fx-background-color: #007fcc; -fx-pref-width: 100px;");
        manageCourse.setOnAction(e -> {
            //checks if a row in the table is selected
            if(cTable.getSelectionModel().getSelectedIndex() >=0){
                //opens manage Course page at tab 1
                ManageCourse.display(window, cTable.getSelectionModel().getSelectedItem(), 1);
            }
        });
        
        //Edit course button
        Button editCourse = new Button("Edit");
        editCourse.setOnAction(e -> {
            //checks if a row in the table is selected
            if(cTable.getSelectionModel().getSelectedIndex() >=0){
                //add course and edit course both use the same class, but if courseNumber given in parameter
                //is 0, it treats it as add course, and if it's not 0, it treats it as edit course
                int num = cTable.getSelectionModel().getSelectedItem().getCourseNo();
                AddCourse.display(window, 1, num);
            }
        });
        
        //Delete course button
        Button deleteCourse = new Button("Delete");
        deleteCourse.setOnAction(e -> {
            //checks if a row in the table is selected
            if(cTable.getSelectionModel().getSelectedIndex() >= 0){
                //opens a confirmation box asking if they want to delete a course
                boolean answer = Confirmation.display("Delete Course", "Are you sure you want to delete a course?");
                if(answer){
                    //method to delete course from table in GUI and from database
                    deleteCourseClicked();
                }
            }  
        });
        
        //Add button
        Button addCourse = new Button("Add");
        addCourse.setOnAction(e -> {
            //since 0 is passed as a course Number, it knows user clicked add course
            AddCourse.display(window, 1, 0);
        });
        
        //Course Table 
        TableColumn<Course, String> codeColumn = new TableColumn<>("Course Code");
        codeColumn.setMinWidth(90);
        codeColumn.setCellValueFactory(new PropertyValueFactory("courseCode"));
        codeColumn.setSortable(false);
        
        TableColumn<Course, Integer> courseGradeColumn = new TableColumn<>("Grade");
        courseGradeColumn.setMinWidth(70);
        courseGradeColumn.setCellValueFactory(new PropertyValueFactory("grade"));
        courseGradeColumn.setSortable(false);
        
        TableColumn<Course, String> teacherColumn = new TableColumn<>("Teacher");
        teacherColumn.setMinWidth(110);
        teacherColumn.setCellValueFactory(new PropertyValueFactory("teacher"));
        teacherColumn.setSortable(false);
        
        TableColumn<Course, String> yearColumn = new TableColumn<>("School Year");
        yearColumn.setMinWidth(90);
        yearColumn.setCellValueFactory(new PropertyValueFactory("schoolYear"));
        yearColumn.setSortable(false);
        
        TableColumn<Course, String> semesterColumn = new TableColumn<>("Semester");
        semesterColumn.setMinWidth(70);
        semesterColumn.setCellValueFactory(new PropertyValueFactory("semester"));
        semesterColumn.setSortable(false);
        
        TableColumn<Course, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setMinWidth(300);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory("description"));
        descriptionColumn.setSortable(false);
        
        //Adds columns and rows to table
        cTable = new TableView<>();
        cTable.setItems(getCourse());
        cTable.getColumns().addAll(codeColumn, courseGradeColumn, teacherColumn, yearColumn, semesterColumn, descriptionColumn);
        cTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Layout
        AnchorPane topMenu = new AnchorPane();
        BorderPane homeLayout = new BorderPane();
        BorderPane layout1 = new BorderPane();
        BorderPane.setMargin(layout1, new Insets(0, 10, 10, 10));
        
        //Layout
        buttons.getChildren().addAll(manageCourse, editCourse, deleteCourse, addCourse);
        AnchorPane.setTopAnchor(buttons, 10.0);
        AnchorPane.setRightAnchor(buttons, 0.0);
        top.getChildren().addAll(courseList, buttons);
        
        //Label
        Label homeLabel = new Label("Manage Course");
        homeLabel.setStyle("-fx-font-size: 15pt; -fx-font-weight: bold;");
        AnchorPane.setTopAnchor(homeLabel, 14.0);
        AnchorPane.setLeftAnchor(homeLabel, 10.0);
        
        //adds image to home button
        Image img = new Image("Home.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(25);
        view.setPreserveRatio(true);
        
        Button home = new Button();
        home.setGraphic(view);
        home.setStyle("-fx-max-height: 25; -fx-max-width: 25; -fx-min-height: 25; -fx-min-width: 25;");
        home.setOnAction(e -> HomeScreen.display(window));
        AnchorPane.setTopAnchor(home, 12.0);
        AnchorPane.setRightAnchor(home, 12.0);
        
        //BorderPane.setMargin(topMenu, new Insets(0, 10, 0, 10));
        
        //Layout
        topMenu.getChildren().addAll(homeLabel, home);
        homeLayout.setTop(topMenu);
        homeLayout.setCenter(layout1);
        layout1.setTop(top);
        layout1.setCenter(cTable);
        
        Scene homeScene = new Scene(homeLayout, 800, 600);
        homeScene.getStylesheets().add("theme.css");
        window.setScene(homeScene);
        window.show();
        
        
    }
      
    
    //Show courses in table
    public static ObservableList<Course> getCourse(){
        //creates list of course objects
        ObservableList<Course> courses = FXCollections.observableArrayList();
        SqliteDB db = new SqliteDB();
        //fills that list with course objects made from database
        db.showCourses(courses);
        db.closeConnection();
        return courses;
    }
    
    
    //delete course button clicked and confirmed
    public static void deleteCourseClicked(){
        //removes course from table in GUI
        ObservableList<Course> courseClicked, allCourses;
        allCourses = cTable.getItems();
        
        int CourseNo = cTable.getSelectionModel().getSelectedItem().getCourseNo();
        
        courseClicked = cTable.getSelectionModel().getSelectedItems();
        courseClicked.forEach(allCourses::remove);
        
        //removes course from course table in database
        SqliteDB db = new SqliteDB();
        db.executeQuery("DELETE FROM AllCourses WHERE courseNo = " + CourseNo);
        db.closeConnection();
    }
}
