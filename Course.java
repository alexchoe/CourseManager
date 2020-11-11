/**
 *
 * @author Alex Choe
 */

//Course object that contains course code, teacher, school year, etc.
public class Course {
    private String courseCode, teacher, schoolYear, description;
    private int courseNo, grade, semester;
    
    public Course(String courseCode, int grade, String teacher, String schoolYear, int semester, String description, int courseNo){
        this.courseCode = courseCode;
        this.grade = grade;
        this.teacher = teacher;
        this.schoolYear = schoolYear;
        this.semester = semester;
        this.description = description;
        this.courseNo = courseNo;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(int courseNo) {
        this.courseNo = courseNo;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

}
