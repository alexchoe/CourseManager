import javafx.scene.control.ComboBox;

/**
 *
 * @author Alex Choe
 */
//Mark object that stores first and last name of the student, and up to 20 marks
public class Mark {
    private String name;
    private ComboBox<String> m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16, m17, m18, m19, m20;
    
    
    public Mark(String name,String v1,String v2,String v3,String v4,String v5,String v6,String v7,String v8,
            String v9,String v10,String v11,String v12,String v13,String v14,String v15,String v16,
            String v17,String v18,String v19,String v20){
        
        this.name = name;
        
        this.m1 = new ComboBox<>(); m1.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m1.setEditable(true);
        m1.setValue(v1);
        
        this.m2 = new ComboBox<>(); m2.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m2.setEditable(true);
        m2.setValue(v2);
        
        this.m3 = new ComboBox<>(); m3.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m3.setEditable(true);
        m3.setValue(v3);
        
        this.m4 = new ComboBox<>(); m4.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m4.setEditable(true);
        m4.setValue(v4);
        
        this.m5 = new ComboBox<>(); m5.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m5.setEditable(true);
        m5.setValue(v5);
        
        this.m6 = new ComboBox<>(); m6.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m6.setEditable(true);
        m6.setValue(v6);
        
        this.m7 = new ComboBox<>(); m7.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m7.setEditable(true);
        m7.setValue(v7);
        
        this.m8 = new ComboBox<>(); m8.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m8.setEditable(true);
        m8.setValue(v8);
        
        this.m9 = new ComboBox<>(); m9.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m9.setEditable(true);
        m9.setValue(v9);
        
        this.m10 = new ComboBox<>(); m10.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m10.setEditable(true);
        m10.setValue(v10);
        
        this.m11 = new ComboBox<>(); m11.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m11.setEditable(true);
        m11.setValue(v11);
        
        this.m12 = new ComboBox<>(); m12.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m12.setEditable(true);
        m12.setValue(v12);
        
        this.m13 = new ComboBox<>(); m13.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m13.setEditable(true);
        m13.setValue(v13);
        
        this.m14 = new ComboBox<>(); m14.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m14.setEditable(true);
        m14.setValue(v14);
        
        this.m15 = new ComboBox<>(); m15.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m15.setEditable(true);
        m15.setValue(v15);
        
        this.m16 = new ComboBox<>(); m16.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m16.setEditable(true);
        m16.setValue(v16);
        
        this.m17 = new ComboBox<>(); m17.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m17.setEditable(true);
        m17.setValue(v17);
        
        this.m18 = new ComboBox<>(); m18.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3","3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m18.setEditable(true);
        m18.setValue(v18);
        
        this.m19 = new ComboBox<>(); m19.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m19.setEditable(true);
        m19.setValue(v19);
        
        this.m20 = new ComboBox<>(); m20.getItems().addAll("4++", "4+", "4/4+", "4", "4-/4", "4-", "3+/4-", "3+", "3", "3-", "2+", "2", "2-", "1+", "1", "1-", "R+", "R", "R-", "I");
        m20.setEditable(true);
        m20.setValue(v20);
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ComboBox<String> getM1() {
        return m1;
    }

    public void setM1(ComboBox<String> m1) {
        this.m1 = m1;
    }

    public ComboBox<String> getM2() {
        return m2;
    }

    public void setM2(ComboBox<String> m2) {
        this.m2 = m2;
    }

    public ComboBox<String> getM3() {
        return m3;
    }

    public void setM3(ComboBox<String> m3) {
        this.m3 = m3;
    }

    public ComboBox<String> getM4() {
        return m4;
    }

    public void setM4(ComboBox<String> m4) {
        this.m4 = m4;
    }

    public ComboBox<String> getM5() {
        return m5;
    }

    public void setM5(ComboBox<String> m5) {
        this.m5 = m5;
    }

    public ComboBox<String> getM6() {
        return m6;
    }

    public void setM6(ComboBox<String> m6) {
        this.m6 = m6;
    }

    public ComboBox<String> getM7() {
        return m7;
    }

    public void setM7(ComboBox<String> m7) {
        this.m7 = m7;
    }

    public ComboBox<String> getM8() {
        return m8;
    }

    public void setM8(ComboBox<String> m8) {
        this.m8 = m8;
    }

    public ComboBox<String> getM9() {
        return m9;
    }

    public void setM9(ComboBox<String> m9) {
        this.m9 = m9;
    }

    public ComboBox<String> getM10() {
        return m10;
    }

    public void setM10(ComboBox<String> m10) {
        this.m10 = m10;
    }

    public ComboBox<String> getM11() {
        return m11;
    }

    public void setM11(ComboBox<String> m11) {
        this.m11 = m11;
    }

    public ComboBox<String> getM12() {
        return m12;
    }

    public void setM12(ComboBox<String> m12) {
        this.m12 = m12;
    }

    public ComboBox<String> getM13() {
        return m13;
    }

    public void setM13(ComboBox<String> m13) {
        this.m13 = m13;
    }

    public ComboBox<String> getM14() {
        return m14;
    }

    public void setM14(ComboBox<String> m14) {
        this.m14 = m14;
    }

    public ComboBox<String> getM15() {
        return m15;
    }

    public void setM15(ComboBox<String> m15) {
        this.m15 = m15;
    }

    public ComboBox<String> getM16() {
        return m16;
    }

    public void setM16(ComboBox<String> m16) {
        this.m16 = m16;
    }

    public ComboBox<String> getM17() {
        return m17;
    }

    public void setM17(ComboBox<String> m17) {
        this.m17 = m17;
    }

    public ComboBox<String> getM18() {
        return m18;
    }

    public void setM18(ComboBox<String> m18) {
        this.m18 = m18;
    }

    public ComboBox<String> getM19() {
        return m19;
    }

    public void setM19(ComboBox<String> m19) {
        this.m19 = m19;
    }

    public ComboBox<String> getM20() {
        return m20;
    }

    public void setM20(ComboBox<String> m20) {
        this.m20 = m20;
    }

}
