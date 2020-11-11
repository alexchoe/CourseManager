import javafx.scene.control.CheckBox;


/**
 *
 * @author Alex Choe
 */

//Expectation object that stores the expectation, description, 
//and the checkbox that will appear in the table
public class Expectation {
    private int courseExpNo, courseNo;
    private String expectation, description;
    private CheckBox select;
    
    public Expectation(int courseNo, String expectation, String description, int courseExpNo){
        this.courseExpNo = courseExpNo;
        this.courseNo = courseNo;
        this.expectation = expectation;
        this.description = description;
        this.select = new CheckBox();
    }

    public int getCourseExpNo() {
        return courseExpNo;
    }

    public void setCourseExpNo(int courseExpNo) {
        this.courseExpNo = courseExpNo;
    }

    public int getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(int courseNo) {
        this.courseNo = courseNo;
    }

    public String getExpectation() {
        return expectation;
    }

    public void setExpectation(String expectation) {
        this.expectation = expectation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }
    
    
}
