import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
/**
 *
 * @author Alex Choe
 */
public class EvidenceRecord{
    //table containing evidence record marks
    static TableView<EvidenceRecordMark1> eTable;
     
    public static void display(Stage window, Course c, CourseStudent s){
        //layout
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(0, 10, 10, 10));
        
        AnchorPane top = new AnchorPane();
        top.setPadding(new Insets(0, 0, 10, 0));
        BorderPane center = new BorderPane();
        
        HBox bottom = new HBox();
        bottom.setPadding(new Insets(10, 10, 10, 10));
        bottom.setAlignment(Pos.CENTER_RIGHT);
        layout.setTop(top);
        layout.setCenter(center);
        layout.setBottom(bottom);
        
        HBox topMenu = new HBox();
        topMenu.setPadding(new Insets(10, 10, 10, 10));
        topMenu.setAlignment(Pos.CENTER_RIGHT);
        BorderPane mainLayout = new BorderPane();
        
        //Bottom section (back button)
        Button back = new Button("Back");
        //when back button is clicked, it opens the ManageCourse page at tab 2
        back.setOnAction(e -> ManageCourse.display(window, c, 2));
        bottom.getChildren().addAll(back);
        
        Button print = new Button("Save as PDF");
        print.setStyle("-fx-pref-width: 110px");
        
        //when save as PDF button is clicked
        print.setOnAction(e -> {
            //hides the save as pdf and back buttons before taking snapshot
            print.setVisible(false);
            back.setVisible(false);
            
            //takes a snap shot of the mainLayout, which is the entire page
            SnapshotParameters param = new SnapshotParameters();
            param.setDepthBuffer(true);
            WritableImage nodeshot = mainLayout.snapshot(param, null);
            
            //saves the snapshot into a file
            File file = new File("chart.png");

            try {
                ImageIO.write(SwingFXUtils.fromFXImage(nodeshot, null), "png", file);
            } catch (IOException e1) {
                System.out.println("Error: " + e1.getMessage());
            }

            //creates a pdf with letter height and width in landscape
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage(new PDRectangle(PDRectangle.LETTER.getHeight(), PDRectangle.LETTER.getWidth()));
            PDImageXObject pdimage;
            PDPageContentStream content;
            
            //inserts the snapshot file into the pdf
            try{
                pdimage = PDImageXObject.createFromFile("chart.png", doc);
                content = new PDPageContentStream(doc, page);
                content.drawImage(pdimage, 0, 0, PDRectangle.LETTER.getHeight(), PDRectangle.LETTER.getWidth());
                content.close();
                doc.addPage(page);
                //names the pdf
                doc.save(c.getCourseCode() + "-" + c.getCourseNo() + "_" + s.getFirstName() + s.getLastName() + ".pdf");
                doc.close();
                file.delete();
            } catch(IOException ex){
                Logger.getLogger(EvidenceRecord.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //shows the save as pdf and back buttons again
            print.setVisible(true);
            back.setVisible(true);
            
            //opens the pdf in the default pdf viewer
            try{
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(new File(System.getProperty("user.dir") + "/" + c.getCourseCode() + "-" + c.getCourseNo() + "_" + s.getFirstName() + s.getLastName() + ".pdf"));
                }
            } catch(Exception e1){
                System.out.println("Error: " + e1.getMessage());
            }
        });
             
        topMenu.getChildren().addAll(print);
        
        mainLayout.setCenter(layout);
        mainLayout.setTop(topMenu);
        
        //top labels (course, teacher and student info)
        Label courseInfo = new Label();
        courseInfo.setText(c.getCourseCode() + " - " + c.getSchoolYear() + " (" + c.getTeacher() + ")");
        AnchorPane.setTopAnchor(courseInfo, 10.0);
        AnchorPane.setLeftAnchor(courseInfo, 10.0);
        
        Label studentInfo = new Label();
        studentInfo.setText(s.getLastName() + ", " + s.getFirstName() + " (" + s.getId() + ")");
        AnchorPane.setTopAnchor(studentInfo, 10.0);
        AnchorPane.setRightAnchor(studentInfo, 10.0);
        
        top.getChildren().addAll(courseInfo, studentInfo);
        
       
        //Evidence Record table
        TableColumn<EvidenceRecordMark1, String> expectationColumn = new TableColumn<>("");
        expectationColumn.setMinWidth(50);
        expectationColumn.setCellValueFactory(new PropertyValueFactory("expectation"));
        expectationColumn.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m1Column = new TableColumn<>("INC");
        m1Column.setMinWidth(50);
        m1Column.setCellValueFactory(new PropertyValueFactory("m1"));
        m1Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m2Column = new TableColumn<>("R-");
        m2Column.setMinWidth(50);
        m2Column.setCellValueFactory(new PropertyValueFactory("m2"));
        m2Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m3Column = new TableColumn<>("R");
        m3Column.setMinWidth(50);
        m3Column.setCellValueFactory(new PropertyValueFactory("m3"));
        m3Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m4Column = new TableColumn<>("R+");
        m4Column.setMinWidth(50);
        m4Column.setCellValueFactory(new PropertyValueFactory("m4"));
        m4Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m5Column = new TableColumn<>("1-");
        m5Column.setMinWidth(50);
        m5Column.setCellValueFactory(new PropertyValueFactory("m5"));
        m5Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m6Column = new TableColumn<>("1");
        m6Column.setMinWidth(50);
        m6Column.setCellValueFactory(new PropertyValueFactory("m6"));
        m6Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m7Column = new TableColumn<>("1+");
        m7Column.setMinWidth(50);
        m7Column.setCellValueFactory(new PropertyValueFactory("m7"));
        m7Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m8Column = new TableColumn<>("2-");
        m8Column.setMinWidth(50);
        m8Column.setCellValueFactory(new PropertyValueFactory("m8"));
        m8Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m9Column = new TableColumn<>("2");
        m9Column.setMinWidth(50);
        m9Column.setCellValueFactory(new PropertyValueFactory("m9"));
        m9Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m10Column = new TableColumn<>("2+");
        m10Column.setMinWidth(50);
        m10Column.setCellValueFactory(new PropertyValueFactory("m10"));
        m10Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m11Column = new TableColumn<>("3-");
        m11Column.setMinWidth(50);
        m11Column.setCellValueFactory(new PropertyValueFactory("m11"));
        m11Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m12Column = new TableColumn<>("3");
        m12Column.setMinWidth(50);
        m12Column.setCellValueFactory(new PropertyValueFactory("m12"));
        m12Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m13Column = new TableColumn<>("3+");
        m13Column.setMinWidth(50);
        m13Column.setCellValueFactory(new PropertyValueFactory("m13"));
        m13Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m14Column = new TableColumn<>("3+/4-");
        m14Column.setMinWidth(50);
        m14Column.setCellValueFactory(new PropertyValueFactory("m14"));
        m14Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m15Column = new TableColumn<>("4-");
        m15Column.setMinWidth(50);
        m15Column.setCellValueFactory(new PropertyValueFactory("m15"));
        m15Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m16Column = new TableColumn<>("4-/4");
        m16Column.setMinWidth(50);
        m16Column.setCellValueFactory(new PropertyValueFactory("m16"));
        m16Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m17Column = new TableColumn<>("4");
        m17Column.setMinWidth(50);
        m17Column.setCellValueFactory(new PropertyValueFactory("m17"));
        m17Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m18Column = new TableColumn<>("4/4+");
        m18Column.setMinWidth(50);
        m18Column.setCellValueFactory(new PropertyValueFactory("m18"));
        m18Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m19Column = new TableColumn<>("4+");
        m19Column.setMinWidth(50);
        m19Column.setCellValueFactory(new PropertyValueFactory("m19"));
        m19Column.setSortable(false);
        
        TableColumn<EvidenceRecordMark1, String> m20Column = new TableColumn<>("4++");
        m20Column.setMinWidth(50);
        m20Column.setCellValueFactory(new PropertyValueFactory("m20"));
        m20Column.setSortable(false);
        
        //adds columns to table and adds rows to table
        eTable = new TableView<>();
        eTable.setItems(getEvidenceRecord(c, s));
        eTable.getColumns().addAll(expectationColumn, m1Column, m2Column, m3Column, m4Column, m5Column, m6Column, m7Column, m8Column, m9Column, m10Column, m11Column, m12Column, m13Column, m14Column, m15Column, m16Column, m17Column, m18Column, m19Column, m20Column);
        center.setCenter(eTable);
        eTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        
        //bottom section that calculates average
        HBox bottomSection = new HBox();
        bottomSection.setPadding(new Insets(10, 10, 10, 10));
        bottomSection.setAlignment(Pos.CENTER_RIGHT);
        //calls getAverage method to get weighted average
        Label average = new Label("Overall Average: " + String.valueOf(getAverage(s.getStudentNo())) + "%");
        
        bottomSection.getChildren().addAll(average);
        center.setBottom(bottomSection);
        
        Scene scene = new Scene(mainLayout, 800, 600);
        scene.getStylesheets().add("theme.css");
        window.setScene(scene);
        window.show();
    }
    
    //Show Evidence record as a table
    public static ObservableList<EvidenceRecordMark1> getEvidenceRecord(Course course, CourseStudent student){
        //creates list of EvidenceRecordMark objects
        ObservableList<EvidenceRecordMark1> records = FXCollections.observableArrayList();
        SqliteDB db = new SqliteDB();
        //fills that list with EvidenceReocrdMark objects made from database
        db.showEvidenceRecord1(records, course.getCourseNo(), student.getStudentNo());
        db.closeConnection();
        return records;
    }
    
    //calculates average from the evidence record
    public static double getAverage(int studentNumber){
        SqliteDB db = new SqliteDB();
        //calls method in SqliteDB class that calculates weighted average
        double average = db.getAverage(studentNumber);
        db.closeConnection();
        return average;
    }

}
