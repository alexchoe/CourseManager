import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Alex Choe
 */
//A class with methods that deal with accessing the database
public class SqliteDB {
    Connection c = null;
    Statement stmt = null;
    Statement stmt2 = null;
    
    //the colors for tasks in evidence record based on weight
    final static String GRADIENT_0 ="#57bb8a";
    final static String GRADIENT_5 ="#63b682";
    final static String GRADIENT_10 ="#73b87e";
    final static String GRADIENT_15 ="#84bb7b";
    final static String GRADIENT_20 ="#94bd77";
    final static String GRADIENT_25 ="#a4c073";
    final static String GRADIENT_30 ="#b0be6e";
    final static String GRADIENT_35 ="#c4c56d";
    final static String GRADIENT_40 ="#d4c86a";
    final static String GRADIENT_45 ="#e2c965";
    final static String GRADIENT_50 ="#f5ce62";
    final static String GRADIENT_55 ="#f3c563";
    final static String GRADIENT_60 ="#e9b861";
    final static String GRADIENT_65 ="#e6ad61";
    final static String GRADIENT_70 ="#ecac67";
    final static String GRADIENT_75 ="#e9a268";
    final static String GRADIENT_80 ="#e79a69";
    final static String GRADIENT_85 ="#e5926b";
    final static String GRADIENT_90 ="#e2886c";
    final static String GRADIENT_95 ="#e0816d";
    final static String GRADIENT_100 ="#dd776e";
    
    
    SqliteDB(){
        //connects the the database
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:CMODB.db");
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    //Adds students to the list
    public void showStudents(ObservableList<CourseStudent> students, int courseNumber){
        try{
            this.stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CourseStudent WHERE courseNo = " + courseNumber);
            
            while(rs.next()){
                int studentNo = rs.getInt("studentNo");
                int courseNo = rs.getInt("courseNo");
                String id = rs.getString("studentID");
                String firstname = rs.getString("firstName");
                String lastname = rs.getString("lastName");
                String email = rs.getString("email");
                int grade = rs.getInt("grade");
                
                students.add(new CourseStudent(id, firstname, lastname, email, grade, studentNo, courseNo));
            }
        } catch(Exception e){
            System.out.println("Error"+ e.getMessage());
        }
    }
    
    //Adds courses to the list
    public void showCourses(ObservableList<Course> courses){
        try{
            this.stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM AllCourses");
            
            while(rs.next()){
                int courseNo = rs.getInt("courseNo");
                String code = rs.getString("courseCode");
                int grade = rs.getInt("grade");
                String teacher = rs.getString("teacher");
                String schoolYear = rs.getString("schoolYear");
                int semester = rs.getInt("semester");
                String description = rs.getString("description");
                
                courses.add(new Course(code, grade, teacher, schoolYear, semester, description, courseNo));
            }
        } catch(Exception e){
            System.out.println("Error"+ e.getMessage());
        }
    }
    
    //Adds expectations to the list
    public void showExpectations(ObservableList<Expectation> expectations, int courseNumber){
        try{
            this.stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CourseExpectation");
            
            while(rs.next()){
                int courseNo = rs.getInt("courseNo");
                if(courseNo == courseNumber){
                    int courseExpNo = rs.getInt("courseExpNo");
                    String expectation = rs.getString("expectation");
                    String description = rs.getString("description");
                
                    expectations.add(new Expectation(courseNo, expectation, description, courseExpNo));
                }
            }
        } catch(Exception e){
            System.out.println("Error"+ e.getMessage());
        }
    }
   
    //Adds tasks to the list
    public void showTaskTable(ObservableList<TaskTable> tasks, int courseNumber){
        try{
            this.stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT taskName, taskID, taskPercent, group_concat(CE.expectation, ', ') AS expectation FROM CourseTask CT JOIN CourseExpectation CE\n" +
                    "ON CT.courseNo = CE.courseNo AND CT.courseExpNo = CE.courseExpNo\n" +
                    "WHERE CT.courseNo = " + courseNumber + "\n" +
                    "GROUP BY taskName");
            while(rs.next()){
                String task = rs.getString("taskName");
                String taskID = rs.getString("taskID");
                int percent = rs.getInt("taskPercent");
                String expectation = rs.getString("expectation");
                
                tasks.add(new TaskTable(task, taskID, percent, expectation));
            }
            
        } catch(Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
    }
    
    //returns a ArrayList containing the expectation numbers that the task covered
    //This method is called from EditTask class 
    public ArrayList<Integer> getExpectationChosen(TaskTable task, int courseNumber){
        try{
            this.stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CourseTask WHERE courseNo = " + courseNumber + 
                    " AND taskName = '" + task.getTask() + "'");
            ArrayList<Integer> expNumber = new ArrayList<>();
            
            while(rs.next()){
                int courseExpNo = rs.getInt("courseExpNo");
                expNumber.add(courseExpNo);
            }
            return expNumber;
            
        } catch(Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
        return null;
    }
    
    //adds student name, and dropdown boxes based on how many expectations a task covers
    public void showMarks(ObservableList<Mark> marks, int courseNumber, String taskName){
        try{
            this.stmt = c.createStatement();
            this.stmt2 = c.createStatement();
            ResultSet rs2;
            ResultSet rs = stmt.executeQuery("SELECT * FROM CourseMark WHERE courseNo = " + courseNumber
                    + " AND taskName = '" + taskName + "'");
            //If the task already has marks in it (editing marks)
            if(rs.next()){
                //counts the number of students in class
                int iCounter = 0;
                rs = stmt.executeQuery("SELECT studentNo FROM CourseStudent WHERE courseNo = " + courseNumber);
                
                while(rs.next()){
                    iCounter++;
                }
                
                //counts the number of expectations in task
                int jCounter = 0;
                rs = stmt.executeQuery("SELECT DISTINCT courseTaskNo FROM (SELECT * FROM CourseMark WHERE courseNo = "+courseNumber+
                        " AND taskName = '" + taskName + "') ORDER BY courseTaskNo");
                while(rs.next()){
                    jCounter++;
                }
                String s1="",s2="",s3="",s4="",s5="",s6="",s7="",s8="",s9="",s10="",
                        s11="",s12="",s13="",s14="",s15="",s16="",s17="",s18="",s19="",s20="";
                
                rs = stmt.executeQuery("SELECT * FROM CourseMark WHERE courseNo = " + courseNumber +
                        " AND taskName = '" + taskName + "'");
                rs2 = stmt2.executeQuery("SELECT * FROM CourseStudent WHERE courseNo = " + courseNumber);
                
                //for each student, and for each expectation, a mark is collected from the database
                for(int i = 0; i < iCounter; i++){
                    rs2.next();
                    String first = rs2.getString("firstName");
                    String last = rs2.getString("lastName");  
                    String name = first + " " + last;
                    
                    for(int j = 0; j < jCounter; j++){
                        switch(j){
                            case 0:
                                if(rs.next()){
                                    s1 = rs.getString("mark");
                                }
                                else{
                                    s1 = "";
                                }
                                break;
                            case 1:
                                if(rs.next()){
                                    s2 = rs.getString("mark");
                                }
                                else{
                                    s2 = "";
                                }
                                break;
                            case 2:
                                if(rs.next()){
                                    s3 = rs.getString("mark");
                                }
                                else{
                                    s3 = "";
                                }
                                break;
                            case 3:
                                if(rs.next()){
                                    s4 = rs.getString("mark");
                                }
                                else{
                                    s4 = "";
                                }
                                break;
                            case 4:
                                if(rs.next()){
                                    s5 = rs.getString("mark");
                                }
                                else{
                                    s5 = "";
                                }
                                break;
                            case 5:
                                if(rs.next()){
                                    s6 = rs.getString("mark");
                                }
                                else{
                                    s6 = "";
                                }
                                break;
                            case 6:
                                if(rs.next()){
                                    s7 = rs.getString("mark");
                                }
                                else{
                                    s7 = "";
                                }
                                break;
                            case 7:
                                if(rs.next()){
                                    s8 = rs.getString("mark");
                                }
                                else{
                                    s8 = "";
                                }
                                break;
                            case 8:
                                if(rs.next()){
                                    s9 = rs.getString("mark");
                                }
                                else{
                                    s9 = "";
                                }
                                break;
                            case 9:
                                if(rs.next()){
                                    s10 = rs.getString("mark");
                                }
                                else{
                                    s10 = "";
                                }
                                break;
                            case 10:
                                if(rs.next()){
                                    s11 = rs.getString("mark");
                                }
                                else{
                                    s11 = "";
                                }
                                break;
                            case 11:
                                if(rs.next()){
                                    s12 = rs.getString("mark");
                                }
                                else{
                                    s12 = "";
                                }
                                break;
                            case 12:
                                if(rs.next()){
                                    s13 = rs.getString("mark");
                                }
                                else{
                                    s13 = "";
                                }
                                break;
                            case 13:
                                if(rs.next()){
                                    s14 = rs.getString("mark");
                                }
                                else{
                                    s14 = "";
                                }
                                break;
                            case 14:
                                if(rs.next()){
                                    s15 = rs.getString("mark");
                                }
                                else{
                                    s15 = "";
                                }
                                break;
                            case 15:
                                if(rs.next()){
                                    s16 = rs.getString("mark");
                                }
                                else{
                                    s16 = "";
                                }
                                break;
                            case 16:
                                if(rs.next()){
                                    s17 = rs.getString("mark");
                                }
                                else{
                                    s17 = "";
                                }
                                break;
                            case 17:
                                if(rs.next()){
                                    s18 = rs.getString("mark");
                                }
                                else{
                                    s18 = "";
                                }
                                break;
                            case 18:
                                if(rs.next()){
                                    s19 = rs.getString("mark");
                                }
                                else{
                                    s19 = "";
                                }
                                break;
                            case 19:
                                if(rs.next()){
                                    s20 = rs.getString("mark");
                                }
                                else{
                                    s20 = "";
                                }
                                break;    
                        }
                        
                    }
                    marks.add(new Mark(name,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s20));
                        
                    
                }
            }
            //If task doesn't have marks in it(first time assigning mark)
            else{
                rs = stmt.executeQuery("SELECT * FROM CourseStudent WHERE courseNo = " + courseNumber);
                //gives empty string values for the marks
                while(rs.next()){
                    String first = rs.getString("firstName");
                    String last = rs.getString("lastName");  
                    String name = first + " " + last;
                    marks.add(new Mark(name,"","","","","","","","","","","","","","","","","","","",""));

                }
            }
        } catch(Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
    }
    
    //checks if the task already has marks stored. this determines whether it's adding or editing marks
    public boolean doesTaskHaveMarks(int courseNumber, String taskName){
        try{
            this.stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CourseMark WHERE courseNo = " + courseNumber
                    + " AND taskName = '" + taskName + "'");
            return rs.next();
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
    
    //returns a arrayList containing the expectations covered by a task
    public ArrayList<String> getNumberOfExp(int courseNumber, TaskTable task){
        try{
            this.stmt = c.createStatement();
            this.stmt2 = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CourseTask WHERE courseNo = " + courseNumber +
                    " AND taskName = '" + task.getTask() + "'");
            ResultSet rs2;
            ArrayList<String> list = new ArrayList<>();
            while(rs.next()){
                int exp = rs.getInt("courseExpNo");
                rs2 = stmt2.executeQuery("SELECT * FROM CourseExpectation WHERE courseExpNo =" + exp);
                String expName = rs2.getString("expectation");
                list.add(expName);
            }
            return list;
        } catch(Exception e){
            System.out.println("Error"+ e.getMessage());
        }
        return null;
    }
    
    
    
    //saves the values in the assign mark page into the database
    public void addMark(Course course, TaskTable task, TableView<Mark> table, ArrayList<String> list){
        try{
            this.stmt = c.createStatement();         
            ResultSet rs;
            int rowNum = 0;
            
            //goes through each row in the table
            for(Mark m: table.getItems()){
                
                //goes through each expectation in the list, which is the same thing as
                //the number of combo boxes in each row
                for(int i = 1; i <= list.size(); i++){
                    
                    //gets the courseTask number and student number from the database
                    rs = stmt.executeQuery("SELECT * FROM CourseTask WHERE courseNo = " + course.getCourseNo() + 
                            " AND taskName = '" + task.getTask() +  "' LIMIT 1 OFFSET " + (i-1));
                    int courseTaskNo = rs.getInt("courseTaskNo");
                    
                    rs = stmt.executeQuery("SELECT * FROM CourseStudent WHERE courseNo = " + course.getCourseNo() +
                            " LIMIT 1 OFFSET " + rowNum);
                    int studentNo = rs.getInt("studentNo");
                    
                    //uses a switch statement because number of columns can be different
                    switch(i){
                        case 1:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM1().getValue() + "')");
                            break;
                        case 2:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM2().getValue() + "')");
                            
                            break;
                        case 3:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM3().getValue() + "')");
                            break;        
                        case 4:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM4().getValue() + "')");
                            break;
                        case 5:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM5().getValue() + "')");
                            break;
                        case 6:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM6().getValue() + "')");
                            break;
                        case 7:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM7().getValue() + "')");
                            break;
                        case 8:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM8().getValue() + "')");
                            break;
                        case 9:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM9().getValue() + "')");
                            break;
                        case 10:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM10().getValue() + "')");
                            break;
                        case 11:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM11().getValue() + "')");
                            break;
                        case 12:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM12().getValue() + "')");
                            break;
                        case 13:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM13().getValue() + "')");
                            break;
                        case 14:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM14().getValue() + "')");
                            break;
                        case 15:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM15().getValue() + "')");
                            break;
                        case 16:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM16().getValue() + "')");
                            break;
                        case 17:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM17().getValue() + "')");
                            break;
                        case 18:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM18().getValue() + "')");
                            break;
                        case 19:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM19().getValue() + "')");
                            break;
                        case 20:
                            stmt.executeUpdate("INSERT INTO CourseMark (courseNo, courseTaskNo, taskName, studentNo, mark) VALUES (" + 
                                    course.getCourseNo() + ", " + courseTaskNo + ", '" + task.getTask() + "', " + studentNo + ", '" + 
                                    m.getM20().getValue() + "')");
                            break;
                        
                    }
                    
                }
                rowNum++;
            }
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    //method that checks that all the combo boxes in the table has the correct levels
    public boolean areMarksCorrect(TableView<Mark> table, ArrayList<String> list){
        //goes through each row of the table
        for(Mark m: table.getItems()){

            //goes through each combo box in the row
            for(int i = 1; i <= list.size(); i++){
                switch(i){
                    case 1:
                        if(!markValuesCorrect(m.getM1())){
                            return false;
                        }
                        break;
                    case 2:
                        if(!markValuesCorrect(m.getM2())){
                            return false;
                        }
                        break;
                    case 3:
                        if(!markValuesCorrect(m.getM3())){
                            return false;
                        }
                        break;        
                    case 4:
                        if(!markValuesCorrect(m.getM4())){
                            return false;
                        }
                        break;
                    case 5:
                        if(!markValuesCorrect(m.getM5())){
                            return false;
                        }
                        break;
                    case 6:
                        if(!markValuesCorrect(m.getM6())){
                            return false;
                        }
                        break;
                    case 7:
                        if(!markValuesCorrect(m.getM7())){
                            return false;
                        }
                        break;
                    case 8:
                        if(!markValuesCorrect(m.getM8())){
                            return false;
                        }
                        break;
                    case 9:
                        if(!markValuesCorrect(m.getM9())){
                            return false;
                        }
                        break;
                    case 10:
                        if(!markValuesCorrect(m.getM10())){
                            return false;
                        }
                        break;
                    case 11:
                        if(!markValuesCorrect(m.getM11())){
                            return false;
                        }
                        break;
                    case 12:
                        if(!markValuesCorrect(m.getM12())){
                            return false;
                        }
                        break;
                    case 13:
                        if(!markValuesCorrect(m.getM13())){
                            return false;
                        }
                        break;
                    case 14:
                        if(!markValuesCorrect(m.getM14())){
                            return false;
                        }
                        break;
                    case 15:
                        if(!markValuesCorrect(m.getM15())){
                            return false;
                        }
                        break;
                    case 16:
                        if(!markValuesCorrect(m.getM16())){
                            return false;
                        }
                        break;
                    case 17:
                        if(!markValuesCorrect(m.getM17())){
                            return false;
                        }
                        break;
                    case 18:
                        if(!markValuesCorrect(m.getM18())){
                            return false;
                        }
                        break;
                    case 19:
                        if(!markValuesCorrect(m.getM19())){
                            return false;
                        }
                        break;
                    case 20:
                        if(!markValuesCorrect(m.getM20())){
                            return false;
                        }
                        break;

                }
            }
        }
        return true;
    }
    
    //returns true if the value in the combo box is one of the following levels, false if not
    public boolean markValuesCorrect(ComboBox<String> m){
        if(m.getValue().equals("I")){
            return true;
        }
        if(m.getValue().equals("R-")){
            return true;
        }
        if(m.getValue().equals("R")){
            return true;
        }
        if(m.getValue().equals("R+")){
            return true;
        }
        if(m.getValue().equals("1-")){
            return true;
        }
        if(m.getValue().equals("1")){
            return true;
        }
        if(m.getValue().equals("1+")){
            return true;
        }
        if(m.getValue().equals("2-")){
            return true;
        }
        if(m.getValue().equals("2")){
            return true;
        }
        if(m.getValue().equals("2+")){
            return true;
        }
        if(m.getValue().equals("3-")){
            return true;
        }
        if(m.getValue().equals("3")){
            return true;
        }
        if(m.getValue().equals("3+")){
            return true;
        }
        if(m.getValue().equals("3+/4-")){
            return true;
        }
        if(m.getValue().equals("4-")){
            return true;
        }
        if(m.getValue().equals("4-/4")){
            return true;
        }
        if(m.getValue().equals("4")){
            return true;
        }
        if(m.getValue().equals("4/4+")){
            return true;
        }
        if(m.getValue().equals("4+")){
            return true;
        }
        if(m.getValue().equals("4++")){
            return true;
        }
        if(m.getValue().equals("")){
            return true;
        }
        return false;
    }
    
    
    //adds each row of evidence record as a observableList which will be used to add rows to the table
    public void showEvidenceRecord1(ObservableList<EvidenceRecordMark1> record, int courseNumber, int studentNumber){
        try{
            this.stmt = c.createStatement();
            //this SQLite query makes a table that looks exactly like an evidence record
            ResultSet rs = stmt.executeQuery("SELECT CE.expectation,\n" +
                    "       group_concat((CASE WHEN mark = 'I'  AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) MarkI,\n" +
                    "       group_concat((CASE WHEN mark = 'R-' AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) MarkRM,\n" +
                    "       group_concat((CASE WHEN mark = 'R'  AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) MarkR, \n" +
                    "       group_concat((CASE WHEN mark = 'R+' AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) MarkRP,\n" +
                    "       group_concat((CASE WHEN mark = '1-' AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) Mark1M,\n" +
                    "       group_concat((CASE WHEN mark = '1'  AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) Mark1,\n" +
                    "       group_concat((CASE WHEN mark = '1+' AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) Mark1P, \n" +
                    "       group_concat((CASE WHEN mark = '2-' AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) Mark2M,\n" +
                    "       group_concat((CASE WHEN mark = '2'  AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) Mark2,\n" +
                    "       group_concat((CASE WHEN mark = '2+' AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) Mark2P, \n" +
                    "       group_concat((CASE WHEN mark = '3-' AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) Mark3M,\n" +
                    "       group_concat((CASE WHEN mark = '3'  AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) Mark3,\n" +
                    "       group_concat((CASE WHEN mark = '3+' AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) Mark3P, \n" +
                    "       group_concat((CASE WHEN mark = '3+/4-' AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) Mark3P4M,\n" +
                    "       group_concat((CASE WHEN mark = '4-' AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) Mark4M,\n" +
                    "       group_concat((CASE WHEN mark = '4-/4' AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) Mark4M4,\n" +
                    "       group_concat((CASE WHEN mark = '4' AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) Mark4,\n" +
                    "       group_concat((CASE WHEN mark = '4/4+' AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) Mark44P,       \n" +
                    "       group_concat((CASE WHEN mark = '4+' AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) Mark4P,\n" +
                    "       group_concat((CASE WHEN mark = '4++' AND CM.studentNo = " + studentNumber + " THEN ct.taskID END), char(13) || char(10)) Mark4PP                                \n" +
                    "FROM CourseTask CT JOIN CourseMark CM ON CT.courseNo = CM.courseNo AND CT.courseTaskNo = CM.courseTaskNo\n" +
                    "LEFT JOIN CourseExpectation CE ON CE.courseNo = CT.courseNo AND CE.courseExpNo = CT.courseExpNo\n" +
                    "WHERE CT.courseNo = " + courseNumber + "\n" +
                    "GROUP BY CE.expectation;");
            
            while(rs.next()){
                String expectation = rs.getString("expectation");
                String m;
                TextFlow t1=null,t2=null,t3=null,t4=null,t5=null,t6=null,t7=null,t8=null,t9=null,t10=null,t11=null,t12=null,t13=null,t14=null,t15=null,t16=null,t17=null,t18=null,t19=null,t20=null; 
                
                //for each mark, the text and color is set
                if(rs.getString("MarkI") != null){
                    m = rs.getString("MarkI");
                    t1 = new TextFlow();
                    addText(m, t1, courseNumber);
                }
                if(rs.getString("MarkRM") != null){
                    m = rs.getString("MarkRM");
                    t2 = new TextFlow();
                    addText(m, t2, courseNumber);
                }
                if(rs.getString("MarkR") != null){
                    m = rs.getString("MarkR");
                    t3 = new TextFlow();
                    addText(m, t3, courseNumber);
                }
                if(rs.getString("MarkRP") != null){
                    m = rs.getString("MarkRP");
                    t4 = new TextFlow();
                    addText(m, t4, courseNumber);
                }
                if(rs.getString("Mark1M") != null){
                    m = rs.getString("Mark1M");
                    t5 = new TextFlow();
                    addText(m, t5, courseNumber);
                }
                if(rs.getString("Mark1") != null){
                    m = rs.getString("Mark1");
                    t6 = new TextFlow();
                    addText(m, t6, courseNumber);
                }
                if(rs.getString("Mark1P") != null){
                    m = rs.getString("Mark1P");
                    t7 = new TextFlow();
                    addText(m, t7, courseNumber);
                }
                if(rs.getString("Mark2M") != null){
                    m = rs.getString("Mark2M");
                    t8 = new TextFlow();
                    addText(m, t8, courseNumber);
                }
                if(rs.getString("Mark2") != null){
                    m = rs.getString("Mark2");
                    t9 = new TextFlow();
                    addText(m, t9, courseNumber);
                }
                if(rs.getString("Mark2P") != null){
                    m = rs.getString("Mark2P");
                    t10 = new TextFlow();
                    addText(m, t10, courseNumber);
                }
                if(rs.getString("Mark3M") != null){
                    m = rs.getString("Mark3M");
                    t11 = new TextFlow();
                    addText(m, t11, courseNumber);
                }
                if(rs.getString("Mark3") != null){
                    m = rs.getString("Mark3");
                    t12 = new TextFlow();
                    addText(m, t12, courseNumber);
                }
                if(rs.getString("Mark3P") != null){
                    m = rs.getString("Mark3P");
                    t13 = new TextFlow();
                    addText(m, t13, courseNumber);
                }
                if(rs.getString("Mark3P4M") != null){
                    m = rs.getString("Mark3P4M");
                    t14 = new TextFlow();
                    addText(m, t14, courseNumber);
                }
                if(rs.getString("Mark4M") != null){
                    m = rs.getString("Mark4M");
                    t15 = new TextFlow();
                    addText(m, t15, courseNumber);
                }
                if(rs.getString("Mark4M4") != null){
                    m = rs.getString("Mark4M4");
                    t16 = new TextFlow();
                    addText(m, t16, courseNumber);
                }
                if(rs.getString("Mark4") != null){
                    m = rs.getString("Mark4");
                    t17 = new TextFlow();
                    addText(m, t17, courseNumber);
                }
                if(rs.getString("Mark44P") != null){
                    m = rs.getString("Mark44P");
                    t18 = new TextFlow();
                    addText(m, t18, courseNumber);
                }
                if(rs.getString("Mark4P") != null){
                    m = rs.getString("Mark4P");
                    t19 = new TextFlow();
                    addText(m, t19, courseNumber);
                }
                if(rs.getString("Mark4PP") != null){
                    m = rs.getString("Mark4PP");
                    t20 = new TextFlow();
                    addText(m, t20, courseNumber);
                }
                
                record.add(new EvidenceRecordMark1(courseNumber, expectation, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20));
            }    
            
        } catch(Exception e){
            System.out.println("Evidence Error: " + e.getMessage());
        }
    }
    
    
    //the method returns the weight of the task given in the parameter
    public int getTaskPercent(String taskID, int courseNumber){
        try{
            this.stmt2 = c.createStatement();
            ResultSet rs = stmt2.executeQuery("SELECT * FROM CourseTask WHERE courseNo = " + courseNumber
                    + " AND taskID = '" + taskID + "'");
            int taskPercent = rs.getInt("taskPercent");
            return taskPercent;
        } catch (Exception e){
            System.out.println("Color Error: " + e.getMessage());
        }
        return -1;
    }
    
    //this returns the number of marks in the string from the evidence record
    //if the string is "T1", it returns 1, if it is "T1, T2, T3", it returns 3
    public int getNumOfTasks(String str){
        int counter = 0;
        for(int i=0; i<str.length(); i++){
            if(str.charAt(i) == 10 || str.charAt(i) == 13){
                counter++;
            }
        }
        return (counter/2) + 1;
    }
    
    //assigns color to each mark in the textFlow
    public void assignColor(int numOfTasks, String str, TextFlow flow, int courseNo){
        int strIndex = 0;
        for(int i=0; i<str.length(); i++){
            if(str.charAt(i) == 10 || str.charAt(i) == 13){
                Text text = new Text(str.substring(strIndex, i+1));
                
                color(text, courseNo, false, flow);
                flow.getChildren().add(text);
                strIndex = i+2;
                i++;
                
            }
        }
        Text text = new Text(str.substring(strIndex));
        color(text, courseNo, true, flow);
        flow.getChildren().add(text);
        flow.setPrefHeight(flow.getHeight()/2);
    }
    
    //sets text and assigns color to the textflow
    public void addText(String str, TextFlow flow, int courseNo){
        int num = getNumOfTasks(str);
        assignColor(num, str, flow, courseNo);
    }
    
    //sets color of text based on the weight
    public void color(Text text, int courseNumber, boolean last, TextFlow flow){
        int weight;
        if(last){
            weight = getTaskPercent(text.getText().substring(0, text.getText().length()), courseNumber);
        }
        else{
            weight = getTaskPercent(text.getText().substring(0, text.getText().length()-1), courseNumber);
        }
        switch(weight){
            case 0:
                text.setFill(Color.web(GRADIENT_0));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                text.applyCss();
                break;
            case 5:
                text.setFill(Color.web(GRADIENT_5));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 10:
                text.setFill(Color.web(GRADIENT_10));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 15:
                text.setFill(Color.web(GRADIENT_15));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 20:
                text.setFill(Color.web(GRADIENT_20));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 25:
                text.setFill(Color.web(GRADIENT_25));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 30:
                text.setFill(Color.web(GRADIENT_30));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 35:
                text.setFill(Color.web(GRADIENT_35));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 40:
                text.setFill(Color.web(GRADIENT_40));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 45:
                text.setFill(Color.web(GRADIENT_45));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 50:
                text.setFill(Color.web(GRADIENT_50));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 55:
                text.setFill(Color.web(GRADIENT_55));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 60:
                text.setFill(Color.web(GRADIENT_60));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 65:
                text.setFill(Color.web(GRADIENT_65));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 70:
                text.setFill(Color.web(GRADIENT_70));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 75:
                text.setFill(Color.web(GRADIENT_75));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 80:
                text.setFill(Color.web(GRADIENT_80));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 85:
                text.setFill(Color.web(GRADIENT_85));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 90:
                text.setFill(Color.web(GRADIENT_90));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 95:
                text.setFill(Color.web(GRADIENT_95));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
            case 100:
                text.setFill(Color.web(GRADIENT_100));
                text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
                break;
        }
    }
    
    //gets the weighted average of all the marks in the evidence record
    public double getAverage(int studentNumber){
        try {
            this.stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SUM((CASE WHEN mark = 'I'  THEN 0 ELSE 0 END)* CT.taskPercent+\n" +
                    "(CASE WHEN mark = 'R-' THEN 12.5 ELSE 0 END)* CT.taskPercent+\n" +
                    "(CASE WHEN mark = 'R'  THEN 25 ELSE 0 END)* CT.taskPercent+\n" +
                    "(CASE WHEN mark = 'R+' THEN 37.5 ELSE 0 END)* CT.taskPercent+\n" +
                    "(CASE WHEN mark = '1-' THEN 51.7 ELSE 0 END)* CT.taskPercent+\n" +
                    "(CASE WHEN mark = '1'  THEN 55 ELSE 0 END)* CT.taskPercent+\n" +
                    "(CASE WHEN mark = '1+' THEN 58.3 ELSE 0 END)* CT.taskPercent+\n" +
                    "(CASE WHEN mark = '2-' THEN 61.7 ELSE 0 END)* CT.taskPercent+\n" +
                    "(CASE WHEN mark = '2'  THEN 65 ELSE 0 END)* CT.taskPercent+\n" +
                    "(CASE WHEN mark = '2+' THEN 68.3 ELSE 0 END)* CT.taskPercent+\n" +
                    "(CASE WHEN mark = '3-' THEN 71.7 ELSE 0 END)* CT.taskPercent+\n" +
                    "(CASE WHEN mark = '3'  THEN 75 ELSE 0 END)* CT.taskPercent+\n" +
                    "(CASE WHEN mark = '3+' THEN 78.3 ELSE 0 END)* CT.taskPercent+\n" +
                    "(CASE WHEN mark = '3+/4-' THEN 80 ELSE 0 END)* CT.taskPercent+ \n" +
                    "(CASE WHEN mark = '4-' THEN 83.3 ELSE 0 END)* CT.taskPercent+\n" +
                    "(CASE WHEN mark = '4-/4' THEN 86.7 ELSE 0 END)* CT.taskPercent+\n" +
                    "(CASE WHEN mark = '4' THEN 90 ELSE 0 END)* CT.taskPercent+\n" +
                    "(CASE WHEN mark = '4/4+' THEN 93.3 ELSE 0 END)* CT.taskPercent+\n" +
                    "(CASE WHEN mark = '4+' THEN 96.7 ELSE 0 END)* CT.taskPercent+\n" +
                    "(CASE WHEN mark = '4++' THEN 100 ELSE 0 END)* CT.taskPercent)/\n" +
                    "(SELECT SUM(CT1.taskPercent) FROM CourseMark CM1 JOIN CourseTask CT1 ON CM1.courseTaskNo = CT1.courseTaskNo\n" +
                    "AND CM1.courseNo = CT1.courseNo WHERE CM1.mark != '' AND CM1.studentNo = "+ studentNumber +")\n" +
                    "\n" +
                    "AS finalAverage\n" +
                    "FROM CourseMark CM JOIN CourseTask CT ON CM.courseTaskNo = CT.courseTaskNo AND CM.courseNo = CT.courseNo\n" +
                    "WHERE mark != '' AND studentNo = "+ studentNumber);
            
            double average = rs.getDouble("finalAverage");
            return round(average, 2);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }
    
    //deletes mark from the database
    public void deleteMark(Course course, TaskTable task){
        try{
            this.stmt = c.createStatement();
            stmt.executeUpdate("DELETE FROM CourseMark WHERE courseNo = " + course.getCourseNo() +
                    " AND taskName = '" + task.getTask() + "'");
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    //returns whether a task with the name already exists in that course
    public boolean taskNameExists(Course course, String taskName){
        try{
            this.stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CourseTask WHERE courseNo = " + course.getCourseNo()
                    + " AND taskName = '" + taskName + "'");
            return rs.next();
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return false;  
    }
    
    //returns whether a task with the ID already exists in that course
    public boolean taskIDExists(Course course, String taskID){
        try{
            this.stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CourseTask WHERE courseNo = " + course.getCourseNo()
                    + " AND taskID = '" + taskID + "'");
            return rs.next();
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return false;  
    }
    
    //returns the course number of the most recently created course
    public int makeTempCourse(){
        try{
            this.stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM AllCourses ORDER BY courseNo DESC LIMIT 1;");
            int courseNo = rs.getInt("courseNo");
            return courseNo;
        } catch(Exception e){
            System.out.println("Error"+ e.getMessage());
        }
        return -1;
    }
    
    //closes the connection to the database
    public void closeConnection(){
        try{
            c.close();
        } catch(Exception e){
            System.out.println("error"+ e.getMessage());
        }
    }
    
    //executes query given in the parameter, used for update, delete, and insert queries
    public void executeQuery(String query){
        try{
            this.stmt = c.createStatement();
            c.createStatement().execute("PRAGMA foreign_keys = ON");
            ResultSet rs = stmt.executeQuery(query);
        } catch(Exception e){
            //System.out.println("Error: "+ e.getMessage());
        }
    }
    
    //returns the course with the course number given
    public Course getCourse(int courseNumber){
        try{
            this.stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM AllCourses WHERE courseNo = " + courseNumber);
            int courseNo = rs.getInt("courseNo");
            String code = rs.getString("courseCode");
            int grade = rs.getInt("grade");
            String teacher = rs.getString("teacher");
            String schoolYear = rs.getString("schoolYear");
            int semester = rs.getInt("semester");
            String description = rs.getString("description");
                
            return new Course(code, grade, teacher, schoolYear, semester, description, courseNo);
        } catch(Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
        return null;
    }
    
    //rounds the double value to the decimal places given
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
}

