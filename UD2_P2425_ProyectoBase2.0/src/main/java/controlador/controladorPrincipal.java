/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.DAOFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.dao.DepartamentoDAO;
import modelo.dao.HistoricoDAO;
import modelo.dao.EmpleadoDAO;
import modelo.dao.OperativaDAO;
import modelo.vo.Departamento;
import vista.Principal;

/**
 *
 * @author Acceso a datos
 */
public class controladorPrincipal {

    public static DAOFactory mySQLFactory;
    //declara los objetos DAO

    public static DepartamentoDAO depDAO;
    public static EmpleadoDAO empDAO;
    public static HistoricoDAO histDAO;
    
   public static OperativaDAO opDAO;

    static DefaultComboBoxModel modelocombo = new DefaultComboBoxModel();

    public static Principal ventana = new Principal();

    public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);

        ventana.getCmbDepartamento().setModel(modelocombo);
    }

    public static void iniciaFactory() {
        mySQLFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        //inicializa los objetos DAO
        depDAO = mySQLFactory.getDepartamentoDAO();

        empDAO = mySQLFactory.getEmpleadoDAO();
        
        histDAO=mySQLFactory.getHistoricoDAO();
        
        opDAO=mySQLFactory.getOperativaDAO();
    }

    public static void cerrarFactory() {
        try {
            mySQLFactory.shutdown();
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void listardepartamentos() {
        // en el controlador es donde va la logica

        try {
            Connection conn;
            conn = mySQLFactory.getConnection();
            depDAO.listarlosdatos(conn, ventana.getTxtAreaDep(), ventana.getLbltotaldep());

            mySQLFactory.releaseConnection(conn);

        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void listarempleados() {
        // en el controlador es donde va la logica

        try {
            Connection conn;
            conn = mySQLFactory.getConnection();
            empDAO.listarlosdatos(conn, ventana.getTxtAreaEmp());

            mySQLFactory.releaseConnection(conn);

        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void listardepartamentopornum() {
        if (ventana.getTxtnumdep().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "faltan datos");
            return;
        }
        try {
            //siempre conectar y desconectar
            Connection conn;
            conn = mySQLFactory.getConnection();

            //pasar solo numero int Integer.parseInt(ventana.getTxtnumdep().getText().trim()
            depDAO.listarlosdatospornumdep(conn, Integer.parseInt(ventana.getTxtnumdep().getText().trim()), ventana.getTxtAreaDep(), ventana.getLbltotaldep());

            mySQLFactory.releaseConnection(conn);
        } catch (NumberFormatException ex1) {

            //salta este primero antes del de insertar
            JOptionPane.showMessageDialog(null, "entrada de datos incorrecta");
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void listarempleadopornumdep() {
        if (ventana.getTxtnumdep().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "faltan datos");
            return;
        }
        try {
            //siempre conectar y desconectar
            Connection conn;
            conn = mySQLFactory.getConnection();

            //existe departamento?
            //  Departamento d=depDAO.buscardepartamento(ventana.getTxtnumdep());
            empDAO.listarlosdatospornumdep(conn, Integer.parseInt(ventana.getTxtnumdep().getText().trim()), ventana.getTxtAreaEmp(), ventana.getLbltotalemp());

            mySQLFactory.releaseConnection(conn);
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

   

    public static void listardepartamentoscombobox() {

        try {
            Connection conn;
            conn = mySQLFactory.getConnection();

            depDAO.listarlosdatoscombobox(conn, ventana.getLbltotaldep(), modelocombo);

            mySQLFactory.releaseConnection(conn);
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void listarempleadopornumdepcombobox() {

        try {
            Connection conn;
            conn = mySQLFactory.getConnection();
            //combobox
            
            if(ventana.getCmbDepartamento().getSelectedItem()!=null){
            
            empDAO.listarlosdatospornumdep(conn, ventana.getCmbDepartamento().getItemAt(ventana.getCmbDepartamento().getSelectedIndex()).getDept_no(), ventana.getTxtAreaEmp(), ventana.getLbltotalemp());
            }
            mySQLFactory.releaseConnection(conn);
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    private static void limpiardatos() {
        ventana.getTxtnumdep2().setText("");
        ventana.getTxtnombredep().setText("");
        ventana.getTxtlocdep().setText("");

    }

    

   

    public static void modificarcomprobando() {

        Connection conn = null;

       Savepoint pc = null;
        
        String tipo="Modificar";

        try {
            conn = mySQLFactory.getConnection();

              
              //si no reseta el contador
            conn.setAutoCommit(false);

            //se hace si o si
            opDAO.contarOperativa(conn, tipo);

            pc = conn.setSavepoint();
            
            if (ventana.getTxtnumdep2().getText().isEmpty() || ventana.getTxtnombredep().getText().isEmpty() || ventana.getTxtlocdep().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "no existe el departamento");
            return;
        }
            //insertamos directamente
            // depDAO.insertar(conn, Integer.valueOf(ventana.getTxtnumdep2().getText()),ventana.getTxtnombredep().getText(),ventana.getTxtlocdep().getText());

            Departamento d = depDAO.buscardepartamento(conn, Integer.valueOf(ventana.getTxtnumdep2().getText()));

            if (d == null) {

                JOptionPane.showMessageDialog(null, "el departamento NO existe.");
                return;

            } else {
                JOptionPane.showMessageDialog(null, depDAO.modificar(conn,
                        d.getDept_no(),
                        ventana.getTxtnombredep().getText(), ventana.getTxtlocdep().getText()) + " registros afectados");

            }
            

        } catch (SQLException ex) {
            try {
                conn.rollback(pc);

            } catch (SQLException ex1) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (NumberFormatException ex1) {
            try {
                conn.rollback(pc);
            } catch (SQLException ex) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Error en formato de datos al modificar");
            limpiardatos();

        } catch (Exception ex2) {
            try {
                conn.rollback(pc);
            } catch (SQLException ex) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

        } finally {   
            
           try {
                conn.commit();
                conn.setAutoCommit(true);
                
                
            } catch (SQLException ex) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            mySQLFactory.releaseConnection(conn);
        }

    }

    public static void borrarCascada() {

        Connection conn = null;
        
        //comprobar dep         
        
           Savepoint pc = null;
        
        String tipo="Borrar";

        try {
            conn = mySQLFactory.getConnection();

              
              //si no reseta el contador
            conn.setAutoCommit(false);

            //se hace si o si
            opDAO.contarOperativa(conn, tipo);

            pc = conn.setSavepoint();
            
            if (ventana.getTxtnumdep2().getText().isEmpty() || ventana.getTxtnombredep().getText().isEmpty() || ventana.getTxtlocdep().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "no existe el departamento");
            return;
        }
            
            if ((empDAO.contarEmp(conn, Integer.valueOf(ventana.getTxtnumdep2().getText()))) != 0) {

                JOptionPane.showMessageDialog(null, empDAO.borrarEmp(conn, Integer.valueOf(ventana.getTxtnumdep2().getText())) + " registros afectados (empleados)");

            }
            JOptionPane.showMessageDialog(null, depDAO.borrar(conn, Integer.valueOf(ventana.getTxtnumdep2().getText())) + " registros afectados (departamentos)");

            
            limpiardatos();

        } catch (SQLException ex) {
            try {

                switch (ex.getErrorCode()) {
                    case 1451 ->
                        JOptionPane.showMessageDialog(null, " El departamento tiene empleados y no ha podido borarase");
                    default -> {

                    }

                }
                conn.rollback(pc);

            } catch (SQLException ex1) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (NumberFormatException ex1) {
            try {
                conn.rollback(pc);
            } catch (SQLException ex) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Error en formato de datos al borrar");
            limpiardatos();

        } catch (Exception ex2) {
            try {
                conn.rollback(pc);
            } catch (SQLException ex) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

       } finally {   
            
           try {
                conn.commit();
                conn.setAutoCommit(true);
                
                
            } catch (SQLException ex) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            mySQLFactory.releaseConnection(conn);
        }

    }

    public static void borrarRestringido() {
        
        

        Connection conn = null;
        
        
          Savepoint pc = null;
        
        String tipo="Borrar";
        
      

        try {
            conn = mySQLFactory.getConnection();
            
              //si no reseta el contador
           // conn.setAutoCommit(false);

            //se hace si o si
            opDAO.contarOperativa(conn, tipo);

            pc = conn.setSavepoint();
            
              if (ventana.getTxtnumdep2().getText().isEmpty() || ventana.getTxtnombredep().getText().isEmpty() || ventana.getTxtlocdep().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "no existe el departamento");
            return;
        }
            
            

            // if ((empDAO.contarEmp(conn, Integer.valueOf(ventana.getTxtnumdep2().getText()))) == 0) {
            JOptionPane.showMessageDialog(null, depDAO.borrar(conn, Integer.valueOf(ventana.getTxtnumdep2().getText())) + " registros afectados");

            

            // } else {
            //    JOptionPane.showMessageDialog(null, "El Departamento tiene empleados y no se puede borrar");
            // }
            limpiardatos();

        } catch (SQLException ex) {
            try {

                switch (ex.getErrorCode()) {
                    case 1451 ->
                        JOptionPane.showMessageDialog(null, " El departamento tiene empleados y no ha podido borarase");
                    default -> {

                    }

                }
                conn.rollback(pc);
            } catch (SQLException ex1) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (NumberFormatException ex1) {
            try {
                conn.rollback(pc);
            } catch (SQLException ex) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Error en formato de datos al borrar");
            limpiardatos();

        } catch (Exception ex2) {
            try {
                conn.rollback(pc);
            } catch (SQLException ex) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

        } finally {   
            
           try {
                conn.commit();
           //     conn.setAutoCommit(true);
                
                
            } catch (SQLException ex) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            mySQLFactory.releaseConnection(conn);
        }

    }

    public static void insertarcomprobandoSavepoint() {

        //cambiar depdao a operativadao
        
        Connection conn = null;

        Savepoint pc = null;
        
        String tipo="Insertar";

        

        try {
            conn = mySQLFactory.getConnection();
            //si no reseta el contador
            conn.setAutoCommit(false);

            //se hace si o si
            opDAO.contarOperativa(conn, tipo);

            pc = conn.setSavepoint();
            
             if (ventana.getTxtnumdep2().getText().isEmpty() || ventana.getTxtnombredep().getText().isEmpty() || ventana.getTxtlocdep().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "faltan datos");
            return;
        }
            
           

            Departamento d = depDAO.buscardepartamento(conn, Integer.valueOf(ventana.getTxtnumdep2().getText()));

            if (d != null) {

                JOptionPane.showMessageDialog(null, "el departamento ya existe.");
                return;

            } else {
                JOptionPane.showMessageDialog(null, depDAO.insertar(conn,
                        Integer.valueOf(ventana.getTxtnumdep2().getText()),
                        ventana.getTxtnombredep().getText(), ventana.getTxtlocdep().getText()) + " registros afectados");

            }
            

        } catch (SQLException ex) {
            try {
                conn.rollback(pc);

            } catch (SQLException ex1) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (NumberFormatException ex1) {
            try {
                conn.rollback(pc);
            } catch (SQLException ex) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Error en formato de datos al insertar");
            limpiardatos();

        } catch (Exception ex2) {
            try {
                conn.rollback(pc);
            } catch (SQLException ex) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

        } finally {
            try {
                conn.commit();
                conn.setAutoCommit(true);
                
                
            } catch (SQLException ex) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            mySQLFactory.releaseConnection(conn);
        }
    }

    public static void guardarHistorico() {
        
           Connection conn = null;

        if (ventana.getTxtnumdep2().getText().isEmpty() || ventana.getTxtnombredep().getText().isEmpty() || ventana.getTxtlocdep().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "faltan datos");
            return;
        }

        try {
            conn = mySQLFactory.getConnection();
           // insertamos directamente
             histDAO.insertarHistorico(conn, Integer.valueOf(ventana.getTxtnumdep2().getText()));

            

            conn.commit();

        } catch (SQLException ex) {
            try {
                switch (ex.getErrorCode()) {
                    case 1062 ->
                        JOptionPane.showMessageDialog(null, "El departamento ya existe");
                    default -> {

                    }

                }
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (NumberFormatException ex1) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Error en formato de datos");
            limpiardatos();

        } catch (Exception ex2) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

        } finally {
            mySQLFactory.releaseConnection(conn);
        }
        
        
  
    }

}
