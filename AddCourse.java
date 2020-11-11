import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class AddCourse {
    
    //Tables that store the expectations and students in course
    static TableView<Expectation> eTable;
    static TableView<CourseStudent> sTable;
    
    public static void display(Stage window, int tab, int courseNumber){
        
        //Tab 1 layout
        GridPane grid = new GridPane();
        HBox bottomMenu1 = new HBox(15);
        BorderPane tabScene1 = new BorderPane();
        tabScene1.setCenter(grid);
        tabScene1.setBottom(bottomMenu1);
        tabScene1.setPadding(new Insets(10, 10, 10, 10));
        
        //Tab 2 layout
        AnchorPane topMenu2 = new AnchorPane();
        Label expList = new Label("Expectation List");
        AnchorPane.setBottomAnchor(expList, 7.0);
        AnchorPane.setLeftAnchor(expList, 0.0);
        
        HBox buttons2 = new HBox(15);
        HBox bottomMenu2 = new HBox(15);
        BorderPane tabScene2 = new BorderPane();
        tabScene2.setTop(topMenu2);
        tabScene2.setBottom(bottomMenu2);
        tabScene2.setPadding(new Insets(0, 10, 10, 10));
        
        //Tab 3 layout
        AnchorPane topMenu3 = new AnchorPane();
        Label studentList = new Label("Student List");
        AnchorPane.setBottomAnchor(studentList, 7.0);
        AnchorPane.setLeftAnchor(studentList, 0.0);
        
        HBox buttons3 = new HBox(15);
        HBox bottomMenu3 = new HBox(15);
        BorderPane tabScene3 = new BorderPane();
        tabScene3.setTop(topMenu3);
        tabScene3.setBottom(bottomMenu3);
        tabScene3.setPadding(new Insets(0, 10, 10, 10));
        
        //Tab layout
        TabPane tabPane = new TabPane();
        tabPane.setPadding(new Insets(10, 0, 0, 0));
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        Tab tab1 = new Tab("Courses Info", tabScene1);
        Tab tab2 = new Tab("Expectations", tabScene2);
        Tab tab3 = new Tab("Students", tabScene3);
        tabPane.getTabs().addAll(tab1, tab2, tab3);

        
        //setting the tab to the number given in the parameter
        if(tab == 1){
            tabPane.getSelectionModel().select(0);
        }
        else if(tab == 2){
            tabPane.getSelectionModel().select(1);
        }
        else if(tab == 3){
            tabPane.getSelectionModel().select(2);
        }
        
        
        //Top menu
        //changes title to Edit Course if courseNumber given in the parameter isn't 0
        Label addCourse = new Label("Add Course");
        if(courseNumber != 0){
            addCourse.setText("Edit Course");
        }
        addCourse.setStyle("-fx-font-size: 15pt; -fx-font-weight: bold;");
        AnchorPane.setTopAnchor(addCourse, 14.0);
        AnchorPane.setLeftAnchor(addCourse, 10.0);
        
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
        
        //Tab 1 Bottom menu
        Button cancel1 = new Button("Cancel");
        cancel1.setOnAction(e -> HomeScreen.display(window));
        
        //Tab 1 content
        //Adding all the labels and textfields/dropdown boxes
        Label courseLabel = new Label("Course:");
        GridPane.setConstraints(courseLabel, 0, 0);
        TextField courseInput = new TextField();
        GridPane.setConstraints(courseInput, 1, 0);
        courseInput.setPrefWidth(400);
        
        Label gradeLabel = new Label("Grade:");
        GridPane.setConstraints(gradeLabel, 0, 1);
        ChoiceBox<Integer> gradeInput = new ChoiceBox<>();
        GridPane.setConstraints(gradeInput, 1, 1);
        gradeInput.getItems().addAll(9, 10, 11, 12);
        gradeInput.setPrefWidth(400);
        
        Label teacherLabel = new Label("Teacher:");
        GridPane.setConstraints(teacherLabel, 0, 2);
        TextField teacherInput = new TextField();
        GridPane.setConstraints(teacherInput, 1, 2);
        teacherInput.setPrefWidth(400);
        
        Label yearLabel = new Label("School Year (YYYY-YYYY):");
        GridPane.setConstraints(yearLabel, 0, 3);
        TextField yearInput = new TextField();
        GridPane.setConstraints(yearInput, 1, 3);
        yearInput.setPrefWidth(400);
        
        Label semesterLabel = new Label("Semester:");
        GridPane.setConstraints(semesterLabel, 0, 4);
        ChoiceBox<Integer> semesterInput = new ChoiceBox<>();
        GridPane.setConstraints(semesterInput, 1, 4);
        semesterInput.getItems().addAll(1, 2, 3, 4);
        semesterInput.setPrefWidth(400);
        
        Label descLabel = new Label("Course Description:");
        GridPane.setConstraints(descLabel, 0, 5);
        TextArea descInput = new TextArea();
        GridPane.setConstraints(descInput, 1, 5);
        descInput.setPrefSize(400, 130);
        
        //If editing course, it fills up the textfields and dropdown with previously inputted information
        if(courseNumber != 0){
            SqliteDB db = new SqliteDB();
            
            courseInput.setText(db.getCourse(courseNumber).getCourseCode());
            gradeInput.setValue(db.getCourse(courseNumber).getGrade());
            teacherInput.setText(db.getCourse(courseNumber).getTeacher());
            yearInput.setText(db.getCourse(courseNumber).getSchoolYear());
            semesterInput.setValue(db.getCourse(courseNumber).getSemester());
            descInput.setText(db.getCourse(courseNumber).getDescription());
            
            db.closeConnection();
        }
        
        //When adding course, it saves the information into database, 
        //When editing course, it updates the information in the database
        Button next1 = new Button("Save");
        next1.setOnAction(e -> {
            if(courseNumber == 0){
                if(isFieldEmpty(courseInput, gradeInput, teacherInput, yearInput, semesterInput, descInput)){
                    Alert.display("Error", "Course information must be completed");
                }
                else{
                    SqliteDB db = new SqliteDB();
                    db.executeQuery("INSERT INTO AllCourses (courseCode, grade, teacher, schoolYear, semester, description) VALUES ('" +
                            courseInput.getText() + "', " + gradeInput.getValue() + ", '" + teacherInput.getText() + "', '" +
                            yearInput.getText() + "', " + semesterInput.getValue() + ", '" + descInput.getText() + "')");
                    int temp = db.makeTempCourse();
                    db.closeConnection();
                    AddCourse.display(window, 1, temp);
                    Alert.display("Save", "Information saved.");
                }
            }
            else{
                if(isFieldEmpty(courseInput, gradeInput, teacherInput, yearInput, semesterInput, descInput)){
                    Alert.display("Error", "Course information must be completed");
                }
                else{
                    SqliteDB db = new SqliteDB();
                    db.executeQuery("UPDATE AllCourses SET courseCode = '" + courseInput.getText() + "', grade = " + gradeInput.getValue() +
                            ", teacher = '" + teacherInput.getText() + "', schoolYear = '" + yearInput.getText() + "', semester = " + 
                            semesterInput.getValue() + ", description = '" + descInput.getText() + "' WHERE CourseNo = " + courseNumber);
                    db.closeConnection();
                    Alert.display("Save", "Information saved.");
                }
            }
            
        });
        
        //When adding course, the second and third tabs are disabled at first.
        //User needs to enter the course info first and save, which enables other tabs
        if(courseNumber == 0){
            tab2.setDisable(true);
            tab3.setDisable(true);
        }
        else{
            tab2.setDisable(false);
            tab3.setDisable(false);
        }
        
        //Tab 2 content
        Button cancel2 = new Button("Cancel");
        cancel2.setOnAction(e -> HomeScreen.display(window));
        Button next2 = new Button("Save");
        
        //The save button on all three tabs saves the info in the first tab.
        //And also gives a alert message saying information saved.
        //This is in all three tabs
        next2.setOnAction(e -> {
            if(isFieldEmpty(courseInput, gradeInput, teacherInput, yearInput, semesterInput, descInput)){
                    Alert.display("Error", "Course information must be completed");
            }
            else{
                SqliteDB db = new SqliteDB();
                db.executeQuery("UPDATE AllCourses SET courseCode = '" + courseInput.getText() + "', grade = " + gradeInput.getValue() +
                        ", teacher = '" + teacherInput.getText() + "', schoolYear = '" + yearInput.getText() + "', semester = " + 
                        semesterInput.getValue() + ", description = '" + descInput.getText() + "' WHERE CourseNo = " + courseNumber);
                db.closeConnection();
                Alert.display("Save", "Information saved.");
            }
        });
        
        //Edit and Delete button checks if a row is selected, then deletes or edits the expectation
        Button editExp = new Button("Edit");
        editExp.setOnAction(e -> {
            if(eTable.getSelectionModel().getSelectedIndex() >= 0){
                AddCourseEditExp.display(window, courseNumber, eTable.getSelectionModel().getSelectedItem());
            }
        });
        
        Button deleteExp = new Button("Delete");
        deleteExp.setOnAction(e ->{
            if(eTable.getSelectionModel().getSelectedIndex() >= 0){
                boolean answer = Confirmation.display("Delete Expectation", "Are you sure you want to delete an expectation?");
                if(answer){
                    deleteExpectationClicked();
                }
            }
        });
        
        //Opens the AddCourseAddExp page
        Button addExp = new Button("Add");
        addExp.setOnAction(e -> AddCourseAddExp.display(window, courseNumber));
        
        //Tab 2 table
        TableColumn<Expectation, String> expectationColumn = new TableColumn<>("Expectation");
        expectationColumn.setMinWidth(100);
        expectationColumn.setCellValueFactory(new PropertyValueFactory("expectation"));
        expectationColumn.setSortable(false);
        
        TableColumn<Expectation, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setMinWidth(600);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory("description"));
        descriptionColumn.setSortable(false);
        
        //adds columns to table, and calls a method to add values to the table
        eTable = new TableView<>();
        eTable.setItems(getExpectation(courseNumber));
        eTable.getColumns().addAll(expectationColumn, descriptionColumn);
        tabScene2.setCenter(eTable);
        eTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        //Tab 3 content
        Button cancel3 = new Button("Cancel");
        cancel3.setOnAction(e -> HomeScreen.display(window));
        
        //saves the info on the first page.
        Button save = new Button("Save");
        save.setOnAction(e -> {
            if(isFieldEmpty(courseInput, gradeInput, teacherInput, yearInput, semesterInput, descInput)){
                    Alert.display("Error", "Course information must be completed");
            }
            else{
                SqliteDB db = new SqliteDB();
                db.executeQuery("UPDATE AllCourses SET courseCode = '" + courseInput.getText() + "', grade = " + gradeInput.getValue() +
                        ", teacher = '" + teacherInput.getText() + "', schoolYear = '" + yearInput.getText() + "', semester = " + 
                        semesterInput.getValue() + ", description = '" + descInput.getText() + "' WHERE CourseNo = " + courseNumber);
                db.closeConnection();
                Alert.display("Save", "Information saved.");
            }
        });
        
        Button editStudent = new Button("Edit");
        editStudent.setOnAction(e -> {
            if(sTable.getSelectionModel().getSelectedIndex() >= 0){
                AddCourseEditStudent.display(window, courseNumber, sTable.getSelectionModel().getSelectedItem());
            }
        });
        
        Button deleteStudent = new Button("Delete");
        deleteStudent.setOnAction(e -> {
            if(sTable.getSelectionModel().getSelectedIndex() >= 0){
                boolean answer = Confirmation.display("Delete Student", "Are you sure you want to delete a student?");
                if(answer){
                    deleteStudentClicked();
                }
            }
        });
        
        Button addStudent = new Button("Add");
        addStudent.setOnAction(e -> AddCourseAddStudent.display(window, courseNumber));
        
        //Tab 3 table
        TableColumn<CourseStudent, String> firstColumn = new TableColumn<>("First Name");
        firstColumn.setMinWidth(100);
        firstColumn.setCellValueFactory(new PropertyValueFactory("firstName"));
        firstColumn.setSortable(false);
        
        TableColumn<CourseStudent, String> lastColumn = new TableColumn<>("Last Name");
        lastColumn.setMinWidth(100);
        lastColumn.setCellValueFactory(new PropertyValueFactory("lastName"));
        lastColumn.setSortable(false);
        
        TableColumn<CourseStudent, String> idColumn = new TableColumn<>("Student ID");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory("id"));
        idColumn.setSortable(false);
        
        TableColumn<CourseStudent, Integer> gradeColumn = new TableColumn<>("Grade");
        gradeColumn.setMinWidth(70);
        gradeColumn.setCellValueFactory(new PropertyValueFactory("grade"));
        gradeColumn.setSortable(false);
        
        TableColumn<CourseStudent, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setMinWidth(200);
        emailColumn.setCellValueFactory(new PropertyValueFactory("email"));
        emailColumn.setSortable(false);
        
        //adds columns to table and calls method to add values to the table
        sTable = new TableView<>();
        sTable.setItems(getStudent(courseNumber));
        sTable.getColumns().addAll(firstColumn, lastColumn, idColumn, gradeColumn, emailColumn);
        tabScene3.setCenter(sTable);
        sTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        //top menu layout (home button and add course layout)
        AnchorPane topMenu = new AnchorPane();
        topMenu.getChildren().addAll(addCourse, home);
        
        
        //Tab 1 adding nodes to layout
        bottomMenu1.setPadding(new Insets(10, 10, 10, 10));
        bottomMenu1.setAlignment(Pos.CENTER_RIGHT);
        bottomMenu1.getChildren().addAll(cancel1, next1);
        
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.getChildren().addAll(courseLabel, courseInput, gradeLabel, gradeInput, teacherLabel, teacherInput,
                                  yearLabel, yearInput, semesterLabel, semesterInput, descLabel, descInput);
        
        //Tab 2 adding nodes to layout
        bottomMenu2.setPadding(new Insets (10, 10, 10, 10));
        bottomMenu2.setAlignment(Pos.CENTER_RIGHT);
        bottomMenu2.getChildren().addAll(cancel2, next2);
        
        buttons2.setPadding(new Insets(0, 0, 7, 0));
        buttons2.setAlignment(Pos.CENTER_RIGHT);
        buttons2.getChildren().addAll(editExp, deleteExp, addExp);
        AnchorPane.setTopAnchor(buttons2, 7.0);
        AnchorPane.setRightAnchor(buttons2, 0.0);
        
        topMenu2.getChildren().addAll(expList, buttons2);
        
        //Tab 3 adding nodes to layout
        bottomMenu3.setPadding(new Insets (10, 10, 10, 10));
        bottomMenu3.setAlignment(Pos.CENTER_RIGHT);
        bottomMenu3.getChildren().addAll(cancel3, save);
        
        buttons3.setPadding(new Insets(0, 0, 7, 0));
        buttons3.setAlignment(Pos.CENTER_RIGHT);
        buttons3.getChildren().addAll(editStudent, deleteStudent, addStudent);
        AnchorPane.setTopAnchor(buttons3, 7.0);
        AnchorPane.setRightAnchor(buttons3, 0.0);
        
        topMenu3.getChildren().addAll(studentList, buttons3);
        
        BorderPane layout = new BorderPane();
        layout.setTop(topMenu);
        layout.setCenter(tabPane);
        
        //adds scene to the stage and applies the css file
        Scene scene = new Scene(layout, 800, 600);
        scene.getStylesheets().add("theme.css");
        window.setScene(scene);
        window.show();
        
    }
    
    //Show expectations in table
    public static ObservableList<Expectation> getExpectation(int courseNo){
        //creates list of expectation objects
        ObservableList<Expectation> expectations = FXCollections.observableArrayList();
        SqliteDB db = new SqliteDB();
        //fills up the list with expectation objects made from values in database
        db.showExpectations(expectations, courseNo);
        db.closeConnection();
        return expectations;
    }      
   
    //delete expectation button clicked and confirmed
    public static void deleteExpectationClicked(){
        //deletes the row from the table in GUI
        ObservableList<Expectation> expectationClicked, allExpectations;
        allExpectations = eTable.getItems();
        
        int courseExpNo = eTable.getSelectionModel().getSelectedItem().getCourseExpNo();
        
        expectationClicked = eTable.getSelectionModel().getSelectedItems();
        expectationClicked.forEach(allExpectations::remove);
        
        //Deletes the row from the expectation table in database
        SqliteDB db = new SqliteDB();
        db.executeQuery("DELETE FROM CourseExpectation WHERE courseExpNo = " + courseExpNo);
        db.closeConnection();
    }
    
    //Show students in table
    public static ObservableList<CourseStudent> getStudent(int courseNo){
        //creates list of CourseStudent objects
        ObservableList<CourseStudent> students = FXCollections.observableArrayList();
        SqliteDB db = new SqliteDB();
        //fills up the list with courseStudent objects made from values in database
        db.showStudents(students, courseNo);
        db.closeConnection();
        return students;
    }
    
    //delete student button clicked and confirmed
    public static void deleteStudentClicked(){
        //deletes the row from the table in GUI
        ObservableList<CourseStudent> studentClicked, allStudent;
        allStudent = sTable.getItems();
        
        int studentNo = sTable.getSelectionModel().getSelectedItem().getStudentNo();
        
        studentClicked = sTable.getSelectionModel().getSelectedItems();
        studentClicked.forEach(allStudent::remove);
        
        //Deletes row from the student table in database
        SqliteDB db = new SqliteDB();
        db.executeQuery("DELETE FROM CourseStudent WHERE studentNo = " + studentNo);
        db.closeConnection();
    }
    
    //checks if all the course information is filled out
    public static boolean isFieldEmpty(TextField course, ChoiceBox<Integer> grade, TextField teacher, TextField year, ChoiceBox<Integer> semester, TextArea description){
        return (course.getText().equals("") || grade.getValue() == null || teacher.getText().equals("") || year.getText().equals("") || semester.getValue() == null || description.getText().equals(""));
    }
}
