import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
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
public class ManageCourse {
    //table containing tasks and student.
    static TableView<TaskTable> tTable;
    static TableView<CourseStudent> sTable;
    
    public static void display(Stage window, Course course, int tab){
        
        //Main layout
        BorderPane homeLayout = new BorderPane();
        
        //Tab 1 layout
        AnchorPane tab1Top = new AnchorPane();
        Label taskList = new Label("Task List");
        AnchorPane.setBottomAnchor(taskList, 7.0);
        AnchorPane.setLeftAnchor(taskList, 0.0);
        
        HBox buttons1 = new HBox(15);
        buttons1.setPadding(new Insets(0, 0, 7, 0));
        buttons1.setAlignment(Pos.CENTER_RIGHT);
        BorderPane layout1 = new BorderPane();
        layout1.setPadding(new Insets(0, 10, 10, 10));
        layout1.setTop(tab1Top);
        
        //Tab 2 layout
        AnchorPane tab2Top = new AnchorPane();
        Label studentList = new Label("Student List");
        AnchorPane.setBottomAnchor(studentList, 7.0);
        AnchorPane.setLeftAnchor(studentList, 0.0);
        
        HBox buttons2 = new HBox(15);
        buttons2.setPadding(new Insets(0, 0, 7, 0));
        buttons2.setAlignment(Pos.CENTER_RIGHT);
        BorderPane layout2 = new BorderPane();
        layout2.setPadding(new Insets(0, 10, 10, 10));
        layout2.setTop(tab2Top);
        
        //Tabs
        TabPane tabPane = new TabPane();
        tabPane.setPadding(new Insets(10, 0, 0, 0));
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        Tab tab1 = new Tab("Tasks", layout1);
        Tab tab2 = new Tab("Evidence Records", layout2);
        tabPane.getTabs().addAll(tab1, tab2);
        
        //sets which tab is shown based on what's given in the parameter
        if(tab == 1){
            tabPane.getSelectionModel().select(0);
        }
        else if(tab == 2){
            tabPane.getSelectionModel().select(1);
        }
        
        //Top menu
        Label manageCourse = new Label(course.getCourseCode());
        manageCourse.setStyle("-fx-font-size: 15pt; -fx-font-weight: bold;");
        AnchorPane.setTopAnchor(manageCourse, 14.0);
        AnchorPane.setLeftAnchor(manageCourse, 10.0);
        
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
        
        AnchorPane topMenu = new AnchorPane();
        topMenu.getChildren().addAll(manageCourse, home);
        
        //Tab
        homeLayout.setCenter(tabPane);
        homeLayout.setTop(topMenu);
        
        //Tab 1 content
        
        //Assign mark button
        Button assignMark = new Button("Assign Mark");
        assignMark.setStyle("-fx-background-color: #007fcc; -fx-pref-width: 110px;");
        assignMark.setOnAction(e -> {
            //checks if row is selected
            if(tTable.getSelectionModel().getSelectedIndex() >= 0){
                //opens the assign mark page
                AssignMark.display(window, course, tTable.getSelectionModel().getSelectedItem());
            }
        });
        
        //Edit task button
        Button editTask = new Button("Edit");
        editTask.setOnAction(e -> {
            //Checks if row is selected
            if(tTable.getSelectionModel().getSelectedIndex() >= 0){
                //opens edit task page
                EditTask.display(window, course, tTable.getSelectionModel().getSelectedItem());
            }
        });
        
        //Delete task button
        Button deleteTask = new Button("Delete");
        deleteTask.setOnAction(e ->{
            //check if row is selected
            if(tTable.getSelectionModel().getSelectedIndex() >= 0){
                //shows confirmation box asking if they want to delete a task
                boolean answer = Confirmation.display("Delete Task", "Are you sure you want to delete a task?");
                if(answer){
                    //calls method to delete task from table and database
                    deleteTaskClicked(course);
                }
            }
        });
        
        //Add button opens add task page
        Button addTask = new Button("Add");
        addTask.setOnAction(e -> AddTask.display(window, course));
        
        buttons1.getChildren().addAll(assignMark, editTask, deleteTask, addTask);
        AnchorPane.setTopAnchor(buttons1, 7.0);
        AnchorPane.setRightAnchor(buttons1, 0.0);
        
        tab1Top.getChildren().addAll(taskList, buttons1);
        
        //Tab 1 table
        TableColumn<TaskTable, String> taskColumn = new TableColumn<>("Task");
        taskColumn.setMinWidth(90);
        taskColumn.setCellValueFactory(new PropertyValueFactory("task"));
        taskColumn.setSortable(false);
        
        TableColumn<TaskTable, String> taskIDColumn = new TableColumn<>("Task ID");
        taskIDColumn.setMinWidth(90);
        taskIDColumn.setCellValueFactory(new PropertyValueFactory("taskID"));
        taskIDColumn.setSortable(false);
        
        TableColumn<TaskTable, String> expectationColumn = new TableColumn<>("Expectation");
        expectationColumn.setMinWidth(500);
        expectationColumn.setCellValueFactory(new PropertyValueFactory("expectation"));
        expectationColumn.setSortable(false);
        
        TableColumn<TaskTable, String> percentColumn = new TableColumn<>("Weight");
        percentColumn.setMinWidth(100);
        percentColumn.setCellValueFactory(new PropertyValueFactory("percent"));
        percentColumn.setSortable(false);
        
        //adds columns to table, and adds rows
        tTable = new TableView<>();
        tTable.setItems(getTaskTable(course.getCourseNo()));
        tTable.getColumns().addAll(taskColumn, taskIDColumn, expectationColumn, percentColumn);
        layout1.setCenter(tTable);
        tTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        //Tab 2 top section
        //evidence record button
        Button evidence = new Button("Evidence records");
        evidence.setStyle("-fx-background-color: #007fcc; -fx-pref-width: 140px;");
        evidence.setOnAction(e -> {
            //checks if row is selected
            if(sTable.getSelectionModel().getSelectedIndex() >= 0){
                //opens evidence record page
                EvidenceRecord.display(window, course, sTable.getSelectionModel().getSelectedItem());
            }
        });
        
        buttons2.getChildren().addAll(evidence);
        AnchorPane.setTopAnchor(buttons2, 7.0);
        AnchorPane.setRightAnchor(buttons2, 0.0);
        
        tab2Top.getChildren().addAll(studentList, buttons2);
        
        //Tab 2 student list table
        TableColumn<CourseStudent, String> firstColumn = new TableColumn<>("First Name");
        firstColumn.setMinWidth(100);
        firstColumn.setCellValueFactory(new PropertyValueFactory("firstName"));
        firstColumn.setSortable(false);
        
        TableColumn<CourseStudent, String> lastColumn = new TableColumn<>("Last Name");
        lastColumn.setMinWidth(100);
        lastColumn.setCellValueFactory(new PropertyValueFactory("lastName"));
        lastColumn.setSortable(false);
        
        TableColumn<CourseStudent, String> IDColumn = new TableColumn<>("Student ID");
        IDColumn.setMinWidth(100);
        IDColumn.setCellValueFactory(new PropertyValueFactory("id"));
        IDColumn.setSortable(false);
        
        TableColumn<CourseStudent, Integer> gradeColumn = new TableColumn<>("Grade");
        gradeColumn.setMinWidth(70);
        gradeColumn.setCellValueFactory(new PropertyValueFactory("grade"));
        gradeColumn.setSortable(false);
        
        TableColumn<CourseStudent, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setMinWidth(200);
        emailColumn.setCellValueFactory(new PropertyValueFactory("email"));
        emailColumn.setSortable(false);
        
        //adds columns to table and rows
        sTable = new TableView<>();
        sTable.setItems(getStudent(course));
        sTable.getColumns().addAll(firstColumn, lastColumn, IDColumn, gradeColumn, emailColumn);
        layout2.setCenter(sTable);
        sTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        Scene scene = new Scene(homeLayout, 800, 600);
        scene.getStylesheets().add("theme.css");
        window.setScene(scene);
        window.show();
    }
    
    //shows tasks in a table
    public static ObservableList<TaskTable> getTaskTable(int courseNo){
        //creates list of task objects
        ObservableList<TaskTable> tasks = FXCollections.observableArrayList();
        SqliteDB db = new SqliteDB();
        //fills that list with task objects made from database 
        db.showTaskTable(tasks, courseNo);
        db.closeConnection();
        return tasks;
    }
    
    //delete task button clicked and confirmed
    public static void deleteTaskClicked(Course course){
        //removes task from table in GUI
        ObservableList<TaskTable> taskClicked, allTasks;
        allTasks = tTable.getItems();
        
        String taskName = tTable.getSelectionModel().getSelectedItem().getTask();
        
        taskClicked = tTable.getSelectionModel().getSelectedItems();
        taskClicked.forEach(allTasks::remove);
        
        //removes task from table in database
        SqliteDB db = new SqliteDB();
        db.executeQuery("DELETE FROM CourseTask WHERE courseNo = " + course.getCourseNo() + " AND taskName = '"
                + taskName + "'");
        db.closeConnection();
    }
    
    //Show students in table
    public static ObservableList<CourseStudent> getStudent(Course course){
        //creates list of course student objects
        ObservableList<CourseStudent> students = FXCollections.observableArrayList();
        SqliteDB db = new SqliteDB();
        //fills that list with course student objects made from database
        db.showStudents(students, course.getCourseNo());
        db.closeConnection();
        return students;
    }
}
