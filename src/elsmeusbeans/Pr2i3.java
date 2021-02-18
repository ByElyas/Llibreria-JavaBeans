/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elsmeusbeans;

//import java.beans.;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.util.Properties;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author eliesfatsini
 */
public class Pr2i3 implements Serializable, VetoableChangeListener {
    
    
    private static ResultSet rst;

    public static final String PROP_RST = "rst";

    /**
     * Get the value of rst
     *
     * @return the value of rst
     */
    public ResultSet getRst() {
        return rst;
    }

    /**
     * Set the value of rst
     *
     * @param rst new value of rst
     * @throws java.beans.PropertyVetoException
     */
    public void setRst(ResultSet rst) throws PropertyVetoException {
        ResultSet oldRst = this.rst;
        vetoableChangeSupport.fireVetoableChange(PROP_RST, oldRst, rst);
        Pr2i3.rst = rst;
        propertyChangeSupport.firePropertyChange(PROP_RST, oldRst, rst);
    }

    
    private static Connection conn = null;

    public static Connection getConn() {
        return conn;
    }




    private String propsDB;

    public static final String PROP_PROPSDB = "testDB";

    private String query_db;

        private String update_db;

    public static final String PROP_UPDATE_DB = "update_db";

    /**
     * Get the value of update_db
     *
     * @return the value of update_db
     */
    public String getUpdate_db() {
        return update_db;
    }

    /**
     * Set the value of update_db
     *
     * @param update_db new value of update_db
     * @throws java.beans.PropertyVetoException
     */
    public void setUpdate_db(String update_db) throws PropertyVetoException {
        String oldUpdate_db = this.update_db;
        vetoableChangeSupport.fireVetoableChange(PROP_UPDATE_DB, oldUpdate_db, update_db);
        this.update_db = update_db;
        propertyChangeSupport.firePropertyChange(PROP_UPDATE_DB, oldUpdate_db, update_db);
    }

    
    public static final String PROP_QUERY_DB = "query_db";
    
    // METODES ESPECIFICS DEL MEU PROGRAMA
    //metode generic per a insertar dades --- tambè serveix per a conductor
//    public static <T> void insertar(T a, Collection<T> col) {
//        col.add(a);
//    }
    

    /**
     * Get the value of query_db
     *
     * @return the value of query_db
     */
    public String getQuery_db() {
        return query_db;
    }

    /**
     * Set the value of query_db
     *
     * @param query_db new value of query_db
     */
    public void setQuery_db(String query_db) throws PropertyVetoException {
        String oldQuery_db = this.query_db;
        vetoableChangeSupport.fireVetoableChange(PROP_QUERY_DB, oldQuery_db, query_db);
        this.query_db = query_db;
    }

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public String getPropsDB() {
        return propsDB;
    }

    public void setPropsDB(String propsDB) throws PropertyVetoException {
        String oldPropsDB = this.propsDB;
        vetoableChangeSupport.fireVetoableChange(PROP_PROPSDB, oldPropsDB, propsDB);
        this.propsDB = propsDB;

    }

    private transient final VetoableChangeSupport vetoableChangeSupport = new VetoableChangeSupport(this);

    public void addVetoableChangeListener(VetoableChangeListener listener) {
        vetoableChangeSupport.addVetoableChangeListener(listener);
    }

    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        vetoableChangeSupport.removeVetoableChangeListener(listener);
    }

    public Pr2i3() {
        this.addVetoableChangeListener(this);
    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        switch (evt.getPropertyName()) {
            default:
                System.out.println("Propietat restringida no tractada!!");
                JOptionPane.showMessageDialog(null, evt);

            case Pr2i3.PROP_PROPSDB:
                Properties properties = new Properties();
                try {
                    properties.load(new FileInputStream((String) evt.getNewValue()));

                    String url = (String) properties.get("url");
                    String user = (String) properties.get("user");
                    String pass = (String) properties.get("passwordUser");
//                    System.out.println("per lo menos he entrat al try");
                    conn = DriverManager.getConnection(url, user, pass);
                    System.out.println("Conectat en exit");
                    JOptionPane.showMessageDialog(null, "Connexió establerta!!");

                } catch (IOException | SQLException ex) {
                    throw new PropertyVetoException("", evt);
                } 
                break;

            case Pr2i3.PROP_QUERY_DB:
                try {
                    Statement sta = conn.createStatement();               
                    rst = sta.executeQuery((String) evt.getNewValue());
                } catch (SQLException ex) {
                    throw new PropertyVetoException("", evt);
                } 
                break;
            
            case Pr2i3.PROP_UPDATE_DB:
                try {
                    Statement sta = conn.createStatement();    
//                    System.out.println((String) evt.getNewValue());
                    sta.executeUpdate((String) evt.getNewValue());
                } catch (SQLException ex) {
                    throw new PropertyVetoException("", evt);
                } 
                break;

        }
    }
}
