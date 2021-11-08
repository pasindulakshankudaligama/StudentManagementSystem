package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.custom.impl.ClassController;
import dao.custom.impl.FeeController;
import dao.custom.impl.RegisteredStudentController;
import dao.custom.ClassManage;
import dao.custom.ManageFee;
import dao.custom.RegisterStudentManage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Class;
import model.Fee;
import model.RegisterStudent;
import model.StudentDetail;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;
import view.tm.StudentDetailTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class CollectFeesFormController {

    public AnchorPane collectFeeContext;
    public JFXButton btnCancel;
    public JFXComboBox<String> cmbRegisterId;
    public JFXComboBox<String> cmbClassId;
    public JFXTextField txtFeeId;
    public JFXTextField txtAmount;
    public TableView<StudentDetailTM> tblFee;
    public TableColumn colFeeId;
    public TableColumn colRegisterId;
    public TableColumn colClassId;
    public TableColumn colAmount;
    public TableColumn colDate;
    public TableColumn colTime;
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtTotalAmount;
    public JFXTextField txtPaidAmount;
    public JFXTextField txtRemainBalance;
    public JFXButton btnClear;
    public JFXTextField txtStudentName;
    public JFXTextField txtGrade;
    public JFXButton btnAdd;
    double netPrice;

    int cartSelectedRowForRemove = -1;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern feeIdPattern = Pattern.compile("^(F)[-]?[0-9]{3}$");
    Pattern amountPattern = Pattern.compile("^[1-9][0-9]{3,4}$");

    ObservableList<StudentDetailTM> obList = FXCollections.observableArrayList();

    private final ManageFee dao = new FeeController();
    private final ClassManage dao1 = new ClassController();
    private final RegisterStudentManage dao2 = new RegisteredStudentController();

    public void initialize() throws SQLException, ClassNotFoundException {
        btnAdd.setDisable(true);
        storeValidations();

        colFeeId.setCellValueFactory(new PropertyValueFactory<>("feeId"));
        colRegisterId.setCellValueFactory(new PropertyValueFactory<>("registerId"));
        colClassId.setCellValueFactory(new PropertyValueFactory<>("classId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("collectDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("collectTime"));

        loadDateAndTime();

        try {
            loadClassIds();
            loadRegisterStudentsIds();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tblFee.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });
        cmbRegisterId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setRegStudentData(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        cmbClassId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setClassData(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void storeValidations() {
        map.put(txtFeeId, feeIdPattern);
        map.put(txtAmount, amountPattern);
    }

    public void collectOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<StudentDetail> students = new ArrayList<>();
        for (StudentDetailTM tempTm : obList
        ) {
            students.add(
                    new StudentDetail(
                            tempTm.getRegisterId(),
                            tempTm.getClassId(),
                            tempTm.getFeeId(),
                            lblDate.getText(),
                            lblTime.getText(),
                            Double.parseDouble(txtTotalAmount.getText())
                    ));
        }

        Fee f1 = new Fee(
                txtFeeId.getText(),
                Double.parseDouble(txtTotalAmount.getText()),
                students

        );

        if (dao.add(f1)) {
            new AddNotifications().sceneNotifications("Saved", "collecting fees", 0, 3);
            try {
                JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/Bill.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(design);

                String studentName = txtStudentName.getText();
                String grade = txtGrade.getText();
                double totalAmount = Double.parseDouble(txtTotalAmount.getText());
                double paidAmount = Double.parseDouble(txtPaidAmount.getText());
                double remainingBalance = Double.parseDouble(txtRemainBalance.getText());
                HashMap map2 = new HashMap();
                map2.put("Student Name",studentName);
                map2.put("Grade",grade);
                map2.put("Total Amount",totalAmount);
                map2.put("Paid Amount",paidAmount);
                map2.put("Remaining Balance",remainingBalance);

                ObservableList<StudentDetailTM> details= tblFee.getItems();

                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map2,new JRBeanArrayDataSource(details.toArray()));
                JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
                e.printStackTrace();
            }

            clear();
            tblFee.refresh();
            obList.clear();
        } else {
            new AddNotifications().sceneNotifications("Not Saved", "Try again later", 0, 4);
        }
    }

    private void loadClassIds() throws SQLException, ClassNotFoundException {
        List<String> subjectIds = dao1.getAllClassIds();
        cmbClassId.getItems().addAll(subjectIds);

    }

    private void loadRegisterStudentsIds() throws SQLException, ClassNotFoundException {
        List<String> regIds = dao2.getRegisteredStudentIds();
        cmbRegisterId.getItems().addAll(regIds);
    }

    private void setRegStudentData(String id) throws SQLException, ClassNotFoundException {
        RegisterStudent r1 = dao2.passRegisteredStudentDetails(id);
        if (r1 == null) {
        } else {
            txtStudentName.setText(r1.getStudentName());
        }
    }

    private void setClassData(String id) throws SQLException, ClassNotFoundException {
        Class c1 = dao1.passClassDetails(id);
        if (c1 == null) {
        } else {
            txtGrade.setText(c1.getGrade());
        }
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void cancelOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();

    }

    public void addToTable(ActionEvent actionEvent) {

        double amount = Double.parseDouble(txtAmount.getText());
        StudentDetailTM tm = new StudentDetailTM(
                cmbRegisterId.getValue(),
                cmbClassId.getValue(),
                txtFeeId.getText(),
                lblDate.getText(),
                lblTime.getText(),
                amount
        );
        int rowNumber = isExists(tm);
        if (rowNumber == -1) {
            obList.add(tm);
        } else {
            StudentDetailTM temp = obList.get(rowNumber);

            StudentDetailTM newTm = new StudentDetailTM(
                    temp.getRegisterId(),
                    temp.getClassId(),
                    temp.getFeeId(),
                    temp.getCollectDate(),
                    temp.getCollectTime(),
                    amount + temp.getAmount()
            );
            obList.remove(rowNumber);
            obList.add(newTm);
        }
        tblFee.setItems(obList);

        for (StudentDetailTM temp : tblFee.getItems()) {
            netPrice += temp.getAmount();
        }
        txtTotalAmount.setText(new DecimalFormat("0.00").format(netPrice));
    }

    private int isExists(StudentDetailTM tm) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getFeeId().equals(obList.get(i).getFeeId())) {
                return i;
            }
        }
        return -1;
    }

    private void clear() {
        txtGrade.clear();
        txtStudentName.clear();
        txtAmount.clear();
        txtFeeId.clear();
        txtTotalAmount.clear();
        txtRemainBalance.clear();
        txtPaidAmount.clear();
        cmbClassId.setValue("");
        cmbRegisterId.setValue("");
        cmbRegisterId.requestFocus();
        tblFee.getSelectionModel().clearSelection();

    }

    public void moveRemainingBalance(ActionEvent actionEvent) {
        int amountPaidText = Integer.parseInt(txtPaidAmount.getText());
        txtRemainBalance.setText(new DecimalFormat("0.00").format(amountPaidText - netPrice));

        txtRemainBalance.requestFocus();
    }

    public void clearOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {
            new AddNotifications().sceneNotifications("Warning", "Please Select a row", 0, 4);
        } else {
            obList.remove(cartSelectedRowForRemove);
            tblFee.refresh();
            clear();
        }

    }

    public void CancelAddFeeOnAction(ActionEvent actionEvent) {
        clear();
        txtTotalAmount.clear();
        txtPaidAmount.clear();
        txtRemainBalance.clear();
        txtStudentName.clear();
        txtGrade.clear();
        obList.clear();
    }

    public void keyEvent(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map, btnAdd);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new AddNotifications().sceneNotifications("INFORMATION", "Added to TextField", 0, 0);
            }
        }

    }
}
