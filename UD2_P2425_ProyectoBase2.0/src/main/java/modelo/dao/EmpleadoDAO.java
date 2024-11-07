/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import modelo.vo.Departamento;

/**
 *
 * @author hector.garaboacasas
 */
public class EmpleadoDAO {

    public void listarlosdatos(Connection conn, JTextArea ta) throws SQLException {
        ta.setText("");
        String consulta = "select * from Empleados";
        //usar throws
        Statement sentencia = conn.createStatement();

        ResultSet rs = sentencia.executeQuery(consulta);

        while (rs.next()) {

            ta.append(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + "\n");

        }

    }

    public void listarlosdatospornumdep(Connection conn, int numdep, JTextArea txtArea, JLabel lbltotalemp) throws SQLException {

        txtArea.setText("");
        lbltotalemp.setText("");

        String consulta = "select * from empleados where dept_no=?";
        PreparedStatement sentencia = conn.prepareStatement(consulta, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        /*
        String consulta2="select count(*) from Empleados where dept_no=?";
        PreparedStatement sentencia2=conn.prepareStatement(consulta2);
         */
        sentencia.setInt(1, numdep);

        ResultSet rs = sentencia.executeQuery();

        /*
        sentencia2.setInt(1,numdep);
        
        ResultSet rs2=sentencia2.executeQuery();
         */
        if (!rs.next()) {
            txtArea.append("no hay empleados en ese departamento");
            return;
        }
        rs.beforeFirst();

        while (rs.next()) {
            txtArea.append(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + rs.getString("dept_no") + "\n");

        }

        String consulta2 = "select count(*) from empleados where dept_no=?";
        PreparedStatement sentencia2 = conn.prepareStatement(consulta2);

        sentencia2.setInt(1, numdep);

        ResultSet rs2 = sentencia2.executeQuery();

        if (rs2.next()) {
            lbltotalemp.setText("total empleados: " + rs2.getInt(1) + "");
        }

    }

    public int contarEmp(Connection conn, int numdep) throws SQLException {

        String consulta = "select count(*) from empleados where dept_no=?";
        PreparedStatement sentencia = conn.prepareStatement(consulta);

        sentencia.setInt(1, numdep);

        ResultSet rs2 = sentencia.executeQuery();

        if (rs2.next()) {
            return rs2.getInt(1);
        } else {
            return 0;
        }

    }

    public int borrarEmp(Connection conn, Integer numdep) throws SQLException {
       // int registrosAfectados;

        String consulta = "DELETE FROM empleados WHERE dept_no = ?";

        PreparedStatement sentencia = conn.prepareStatement(consulta);

        sentencia.setInt(1, numdep);
//        registrosAfectados = sentencia.executeUpdate();

        return sentencia.executeUpdate();
    }

}
