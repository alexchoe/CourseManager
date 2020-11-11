/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex Choe
 */
public class TaskTable {
    private String task, taskID, expectation;
    private int percent;
    
    public TaskTable(String task, String taskID, int percent, String expectation){
        this.task = task;
        this.taskID = taskID;
        this.percent = percent;
        this.expectation = expectation;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getExpectation() {
        return expectation;
    }

    public void setExpectation(String expectation) {
        this.expectation = expectation;
    }
    
}
