package fxProj;

import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML TableView<CountryTableData> TableV;
    @FXML TableColumn<CountryTableData, String> IdC;
    @FXML TableColumn<CountryTableData, String> NameC;
    @FXML TableColumn<CountryTableData, String> ConC;

    @FXML JFXButton ReloadB;
    @FXML JFXButton InsertB;
    @FXML JFXButton UpdateB;
    @FXML JFXButton DeleteB;

    @FXML TextField TextId;
    @FXML TextField TextName;
    @FXML TextField TextCont;


    private SessionFactory factory = null;
    private Session session= null;

    List<CountryTableData> list = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
             factory = new Configuration().configure().buildSessionFactory();
        }catch (Exception e){
        System.out.println("Fail" + e);
        }

        IdC.setCellValueFactory(new PropertyValueFactory<CountryTableData, String>("Id"));
        NameC.setCellValueFactory(new PropertyValueFactory<CountryTableData, String>("Name"));
        ConC.setCellValueFactory(new PropertyValueFactory<CountryTableData,String>("Continent"));

        IdC.setCellFactory(TextFieldTableCell.<CountryTableData>forTableColumn());
        NameC.setCellFactory(TextFieldTableCell.<CountryTableData>forTableColumn());
        ConC.setCellFactory(TextFieldTableCell.<CountryTableData>forTableColumn());

        TableV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CountryTableData>() {
            @Override
            public void changed(ObservableValue<? extends CountryTableData> observable, CountryTableData oldValue, CountryTableData newValue) {
                CountryTableData cdata = TableV.getSelectionModel().getSelectedItem();
                if (cdata!=null){
                    TextId.setText(cdata.getId());
                    TextName.setText(cdata.getName());
                    TextCont.setText(cdata.getContinent());}
            }
        });


        reloadMethod();

    }

    public void reloadA(ActionEvent event){
        list.clear();
        reloadMethod();
    }



    public void insertA(ActionEvent event){

        if (!session.isConnected()){
            session = factory.openSession();}
        Transaction tx = session.beginTransaction();
        int idget = Integer.parseInt(TextId.getText());
        CountryHibernate c = new CountryHibernate(idget,TextName.getText(),TextCont.getText());
        session.save(c);
        tx.commit();
        list.clear();
        reloadMethod();
    }

    public void updateA(ActionEvent event){

        if (!session.isConnected()){
            session = factory.openSession();}
        Transaction tx = session.beginTransaction();
        int idi = Integer.parseInt(TextId.getText());
        CountryHibernate c = (CountryHibernate)session.get(CountryHibernate.class,idi);
        c.setName(TextName.getText());
        c.setContinent(TextCont.getText());
        session.update(c);
        tx.commit();
        list.clear();
        reloadMethod();
    }

    public void deleteA(ActionEvent event){
        if (!session.isOpen()){
            session = factory.openSession();}
        Transaction tx = session.beginTransaction();
        int idi = Integer.parseInt(TextId.getText());
        CountryHibernate c = (CountryHibernate)session.get(CountryHibernate.class,idi);
        session.delete(c);
        tx.commit();
        list.clear();
        reloadMethod();
    }



    private void reloadMethod() {

            session = factory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            List countries = session.createQuery("FROM CountryHibernate").list();
            for (Iterator iterator = countries.iterator(); iterator.hasNext();){
                CountryHibernate data = (CountryHibernate) iterator.next();
                int idInt = data.getID();
                String nameS = data.getName();
                String countryS = data.getContinent();
                String idS = String.valueOf(idInt);

                System.out.println(" "+ idInt + " " + nameS+ " " + countryS);
                list.add(new CountryTableData(idS,nameS,countryS));
            }
            tx.commit();


        }catch (Exception e){
            System.out.println( " Fail1 " + e );
        }
        try {


            ObservableList<CountryTableData> oList = FXCollections.observableList(list);
            TableV.setItems(oList);
        }catch (Exception e ){
            System.out.println( " Fail2 " + e );
        }
    }

}
