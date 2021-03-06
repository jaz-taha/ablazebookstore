/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ablazebookstore.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import edu.ablazebookstore.services.BookCrud;
import edu.ablazebookstore.models.Book;
import java.io.IOException;
import java.net.URL;
import java.sql.JDBCType;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author JARR
 */
public class BookController implements Initializable {
    
    @FXML
    private JFXTextField tftitle;
    @FXML
    private JFXTextField txauthor;
    @FXML
    private JFXTextField txisbn;
    @FXML
    private JFXTextField txpublisher;
    @FXML
    private JFXTextField txprice;
    
    @FXML
    private JFXButton btnadd;
    @FXML
    private JFXDatePicker txreleasedate;
    @FXML
    private JFXTextField txcover;
    private boolean editmode=false;
    private int  id;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private boolean validatefields() {
        
        if (tftitle.getText().isEmpty()
                || txauthor.getText().isEmpty()
                || txisbn.getText().isEmpty()
                || txpublisher.getText().isEmpty()
                || txcover.getText().isEmpty()
                || txprice.getText().isEmpty()
                || txreleasedate.getEditor().getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText(null);
            alert.setContentText("make sure no fields are empty");
            alert.showAndWait();
            return false;
            
        }
        return true;        
    }

    @FXML
    private void addbook(ActionEvent event) {
        String title, author, publisher, isbn, cover;
        float price;
        
        Date releasedate;
        if (validatefields()) {
            title = tftitle.getText();
            author = txauthor.getText();
            isbn = txisbn.getText();
            publisher = txpublisher.getText();
            cover = txcover.getText();
            
            price = Float.parseFloat(txprice.getText());
            releasedate = Date.valueOf(txreleasedate.getValue());
            BookCrud pc = new BookCrud();
            Book p1 = new Book(price, title, author, isbn, publisher, releasedate, cover);
            if(editmode)
            {
               editbook();
            }
            else{
                            pc.addBook(p1);

            }
        }
            
    }

    public void inflateUi(Book book) {
         id = book.getId();
        tftitle.setText(book.getTitle());
            txauthor.setText(book.getAuthor());
            txisbn.setText(book.getIsbn());
            txpublisher.setText(book.getPublisher());
            txcover.setText(book.getCover());
            txprice.setText(String.valueOf(book.getPrice()));
            txreleasedate.setValue(book.getReleasedate().toLocalDate());
            editmode=true;
    }
    private void editbook()
    {           
            Book book = new Book(Float.parseFloat(txprice.getText()), tftitle.getText(), txauthor.getText(), txisbn.getText(), txpublisher.getText(), Date.valueOf(txreleasedate.getValue()), txcover.getText());
            book.setId(id);
            BookCrud pc = new BookCrud();
           if(pc.updateBook(book)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Book Updated");
            alert.setHeaderText(null);
            alert.setContentText("The selected book has been updated");
            alert.showAndWait(); 
           }
    }

    @FXML
    private void loadBook(ActionEvent event) {
        if (txisbn.getText().isEmpty())
        {
              Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText(null);
            alert.setContentText("ISBN FIELD IS EMPTY");
            alert.showAndWait();
            return;
        }
        
       
    }
    
}
