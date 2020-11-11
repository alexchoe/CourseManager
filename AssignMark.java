import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class AssignMark {
    //table containing marks
    static TableView<Mark> mTable;
    
    public static void display(Stage window, Course course, TaskTable task){
        //Stores all expectations the task covers in that course in an arraylist of strings
        SqliteDB db = new SqliteDB();
        ArrayList<String> list = db.getNumberOfExp(course.getCourseNo(), task);
        db.closeConnection();

        //Main layout
        BorderPane layout = new BorderPane();
        
        //Top menu
        AnchorPane topMenu = new AnchorPane();
        //label is set to the course code
        Label courseName = new Label(course.getCourseCode());
        courseName.setStyle("-fx-font-size: 15pt; -fx-font-weight: bold;");
        AnchorPane.setTopAnchor(courseName, 14.0);
        AnchorPane.setLeftAnchor(courseName, 10.0);
        
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
        topMenu.getChildren().addAll(courseName, home);
        
        //Bottom menu
        HBox bottomMenu = new HBox(15);
        bottomMenu.setAlignment(Pos.CENTER_RIGHT);
        bottomMenu.setPadding(new Insets(10, 10, 10, 10));
        
        //cancel button opens manageCourse page at tab 1
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> ManageCourse.display(window, course,1));
        
        //Save button
        Button save = new Button("Save");
        save.setOnAction(e -> {
            SqliteDB db2 = new SqliteDB();
            //checks if the values in the dropdown are one of the options
            //since the user can also type the values
            if(!db2.areMarksCorrect(mTable, list)){
                Alert.display("Error", "Mark entered must be one of the options");
            }
            else{
                //If marks were already given previously, it deletes those marks from database first
                if(db2.doesTaskHaveMarks(course.getCourseNo(), task.getTask())){
                    db2.deleteMark(course, task);
                }
                //Adds the marks into database
                db2.addMark(course, task, mTable, list);

                db2.closeConnection();
                ManageCourse.display(window, course,1);
            }
            db2.closeConnection();
        });
        
        //adds cancel and save button to layout
        bottomMenu.getChildren().addAll(cancel, save);
        
        //Center content layout
        BorderPane main = new BorderPane();
        main.setPadding(new Insets(10, 10, 10, 10));
        
        //Label for table
        Label taskName = new Label(task.getTask());
        BorderPane.setMargin(taskName, new Insets(7, 0, 7, 0));
        
        //Table
        //student name column
        TableColumn<Mark, String> studentColumn = new TableColumn<>("Student");
        studentColumn.setMinWidth(190);
        studentColumn.setCellValueFactory(new PropertyValueFactory("name"));
        studentColumn.setSortable(false);
        
        //adds student name column to table
        mTable = new TableView<>();
        mTable.getColumns().add(studentColumn);
        mTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        //20 null comboboxes(dropdown) assuming that number of expectations a task covers
        //won't exceed 20
        TableColumn<Mark, ComboBox> c1 = null, c2 = null, c3 = null, c4 = null, c5 = null, c6 = null, c7 = null,
                c8 = null, c9 = null, c10 = null, c11 = null, c12 = null, c13 = null, c14 = null, c15 = null, 
                c16 = null, c17 = null, c18 = null, c19 = null, c20 = null;
        
        
        //for each value in the ArrayList containing expectations
        for(int i=0 ; i<list.size(); i++){
            //switch statement because each column needs a different name to refer to
            switch(i){
                //for each expectation, it will create a column with the expectation name
                //and add the column to the table
                case 0:
                    c1 = new TableColumn(""+ list.get(i));
                    c1.setMinWidth(90);
                    c1.setCellValueFactory(new PropertyValueFactory("m1"));
                    c1.setSortable(false);
                    mTable.getColumns().addAll(c1);
                    break;
                case 1:
                    c2 = new TableColumn("" + list.get(i));
                    c2.setMinWidth(90);
                    c2.setCellValueFactory(new PropertyValueFactory("m2"));
                    c2.setSortable(false);
                    mTable.getColumns().addAll(c2);
                    break;
                case 2:
                    c3 = new TableColumn("" + list.get(i));
                    c3.setMinWidth(90);
                    c3.setCellValueFactory(new PropertyValueFactory("m3"));
                    c3.setSortable(false);
                    mTable.getColumns().addAll(c3);
                    break;
                case 3:
                    c4 = new TableColumn("" + list.get(i));
                    c4.setMinWidth(90);
                    c4.setCellValueFactory(new PropertyValueFactory("m4"));
                    c4.setSortable(false);
                    mTable.getColumns().addAll(c4);
                    break;

                case 4:
                    c5 = new TableColumn("" + list.get(i));
                    c5.setMinWidth(90);
                    c5.setCellValueFactory(new PropertyValueFactory("m5"));
                    c5.setSortable(false);
                    mTable.getColumns().addAll(c5);
                    break;

                case 5:
                    c6 = new TableColumn("" + list.get(i));
                    c6.setMinWidth(90);
                    c6.setCellValueFactory(new PropertyValueFactory("m6"));
                    c6.setSortable(false);
                    mTable.getColumns().addAll(c6);
                    break;

                case 6:
                    c7 = new TableColumn("" + list.get(i));
                    c7.setMinWidth(90);
                    c7.setCellValueFactory(new PropertyValueFactory("m7"));
                    c7.setSortable(false);
                    mTable.getColumns().addAll(c7);
                    break;

                case 7:
                    c8 = new TableColumn("" + list.get(i));
                    c8.setMinWidth(90);
                    c8.setCellValueFactory(new PropertyValueFactory("m8"));
                    c8.setSortable(false);
                    mTable.getColumns().addAll(c8);
                    break;

                case 8:
                    c9 = new TableColumn("" + list.get(i));
                    c9.setMinWidth(90);
                    c9.setCellValueFactory(new PropertyValueFactory("m9"));
                    c9.setSortable(false);
                    mTable.getColumns().addAll(c9);
                    break;

                case 9:
                    c10 = new TableColumn("" + list.get(i));
                    c10.setMinWidth(90);
                    c10.setCellValueFactory(new PropertyValueFactory("m10"));
                    c10.setSortable(false);
                    mTable.getColumns().addAll(c10);
                    break;

                case 10:
                    c11 = new TableColumn("" + list.get(i));
                    c11.setMinWidth(90);
                    c11.setCellValueFactory(new PropertyValueFactory("m11"));
                    c11.setSortable(false);
                    mTable.getColumns().addAll(c11);
                    break;

                case 11:
                    c12 = new TableColumn("" + list.get(i));
                    c12.setMinWidth(90);
                    c12.setCellValueFactory(new PropertyValueFactory("m12"));
                    c12.setSortable(false);
                    mTable.getColumns().addAll(c12);
                    break;

                case 12:
                    c13 = new TableColumn("" + list.get(i));
                    c13.setMinWidth(90);
                    c13.setCellValueFactory(new PropertyValueFactory("m13"));
                    c13.setSortable(false);
                    mTable.getColumns().addAll(c13);
                    break;

                case 13:
                    c14 = new TableColumn("" + list.get(i));
                    c14.setMinWidth(90);
                    c14.setCellValueFactory(new PropertyValueFactory("m14"));
                    c14.setSortable(false);
                    mTable.getColumns().addAll(c14);
                    break;

                case 14:
                    c15 = new TableColumn("" + list.get(i));
                    c15.setMinWidth(90);
                    c15.setCellValueFactory(new PropertyValueFactory("m15"));
                    c15.setSortable(false);
                    mTable.getColumns().addAll(c15);
                    break;

                case 15:
                    c16 = new TableColumn("" + list.get(i));
                    c16.setMinWidth(90);
                    c16.setCellValueFactory(new PropertyValueFactory("m16"));
                    c16.setSortable(false);
                    mTable.getColumns().addAll(c16);
                    break;

                case 16:
                    c17 = new TableColumn("" + list.get(i));
                    c17.setMinWidth(90);
                    c17.setCellValueFactory(new PropertyValueFactory("m17"));
                    c17.setSortable(false);
                    mTable.getColumns().addAll(c17);
                    break;

                case 17:
                    c18 = new TableColumn("" + list.get(i));
                    c18.setMinWidth(90);
                    c18.setCellValueFactory(new PropertyValueFactory("m18"));
                    c18.setSortable(false);
                    mTable.getColumns().addAll(c18);
                    break;

                case 18:
                    c19 = new TableColumn("" + list.get(i));
                    c19.setMinWidth(90);
                    c19.setCellValueFactory(new PropertyValueFactory("m19"));
                    c19.setSortable(false);
                    mTable.getColumns().addAll(c19);
                    break;
                case 19:
                    c20 = new TableColumn("" + list.get(i));
                    c20.setMinWidth(90);
                    c20.setCellValueFactory(new PropertyValueFactory("m20"));
                    c20.setSortable(false);
                    mTable.getColumns().addAll(c20);
                    break;
                        
            } 
            
        }
        //sets rows in the table
        mTable.setItems(getMarks(course.getCourseNo(), task));
        
        
        
        //Main layout
        layout.setTop(topMenu);
        layout.setBottom(bottomMenu);
        layout.setCenter(main);
        
        main.setTop(taskName);
        main.setCenter(mTable);
        
        Scene scene = new Scene(layout, 800, 600);
        scene.getStylesheets().add("theme.css");
        window.setScene(scene);
        window.show();
        
    }
    
    //gets values onto the table
    public static ObservableList<Mark> getMarks(int courseNo, TaskTable task){
        //creates list of Mark objects
        ObservableList<Mark> marks = FXCollections.observableArrayList();
        SqliteDB db = new SqliteDB();
        //fills that list with Mark objects made from database
        db.showMarks(marks, courseNo, task.getTask());
        db.closeConnection();
        return marks;
    }
    
}
