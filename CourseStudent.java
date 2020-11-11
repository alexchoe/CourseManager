/**
 *
 * @author Alex Choe
 */

//Student object that contains student info and course Number that they are in
public class CourseStudent {
    private String id, firstName, lastName, email;
    private int studentNo, grade, courseNo;
    
    public CourseStudent(String id, String firstName, String lastName, String email, int grade, int studentNo, int courseNo){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.grade = grade;
        this.studentNo = studentNo;
        this.courseNo = courseNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(int studentNo) {
        this.studentNo = studentNo;
    }

    public int getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(int courseNo) {
        this.courseNo = courseNo;
    }
    
}
