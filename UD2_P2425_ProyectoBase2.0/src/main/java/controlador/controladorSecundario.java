/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import static controlador.controladorPrincipal.mySQLFactory;
import controlador.factory.DAOFactory;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.dao.DepartamentoDAO;
import modelo.dao.EmpleadoDAO;
import modelo.dao.HistoricoDAO;
import modelo.dao.OperativaDAO;
import modelo.vo.Departamento;
import modelo.vo.Empleado;
import vista.Empleados;
import vista.Principal;

/**
 *
 * @author hector.garaboacasas
 */
public class controladorSecundario {

    public static DAOFactory mySQLFactory;
    //declara los objetos DAO

    public static DepartamentoDAO depDAO;
    public static EmpleadoDAO empDAO;
    public static HistoricoDAO histDAO;

    public static OperativaDAO opDAO;

    static DefaultComboBoxModel modelocombo = new DefaultComboBoxModel();

    public static Empleados ventana = new Empleados();

    public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);

        ventana.getCmbDepEmpleado().setModel(modelocombo);
    }

    public static void iniciaFactory() {
        mySQLFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        //inicializa los objetos DAO
        depDAO = mySQLFactory.getDepartamentoDAO();

        empDAO = mySQLFactory.getEmpleadoDAO();

        histDAO = mySQLFactory.getHistoricoDAO();

        opDAO = mySQLFactory.getOperativaDAO();
    }

    public static void cerrarFactory() {
        try {
            mySQLFactory.shutdown();
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void MostrarEmp() {
        try {
            Connection conn;
            conn = mySQLFactory.getConnection();
            Empleado emp = empDAO.mostrarEmp(conn,
                    Integer.valueOf(ventana.getTxtEmpNo().getText())
            );

            if (emp == null) {

                JOptionPane.showMessageDialog(null, "el empleado NO existe.");
                return;

            } else {

                ventana.getTxtApellido().setText(emp.getApellido());

                ventana.getTxtDir().setText(String.valueOf(emp.getDir()));

                //java.sql.Date
                ventana.getTxtFechaAlt().setText(String.valueOf(emp.getDate()));
                ventana.getTxtOficio().setText(emp.getOficio());
                ventana.getTxtSalario().setText(String.valueOf(emp.getSalario()));

                Departamento dep = depDAO.buscardepartamento(conn, emp.getEmp_dept_no());
                if (dep == null) {
                    JOptionPane.showMessageDialog(null, "el departamento del empleado+" + ventana.getTxtEmpNo().getText() + " NO existe.");
                    return;
                } else {
                    modelocombo.setSelectedItem(dep);
                }

//                  
            }

        } catch (Exception ex) {
            Logger.getLogger(controladorSecundario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void mostrarComboDepartamento() {

        Connection conn = null;

        try {
            conn = mySQLFactory.getConnection();

            depDAO.listarlosdatoscombobox2(conn, modelocombo);

        } catch (Exception ex) {
            Logger.getLogger(controladorSecundario.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            mySQLFactory.releaseConnection(conn);
        }

    }

    public static void insertarEmpleado() {
        
        Connection conn=null;
        
        
        
        
        
        try {
            conn=mySQLFactory.getConnection();
            
            empDAO.insertar(ventana.getTxtEmpNo(),
                    ventana.getTxtApellido(),
                    ventana.getTxtDir(), 
                    ventana.getTxtOficio(), 
                    ventana.getTxtSalario(),
                    ventana.getTxtFechaAlt(),
                    ventana.getCmbDepEmpleado().getSelectedItem());
            
            
        } catch (Exception ex) {
            Logger.getLogger(controladorSecundario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
      }

}
