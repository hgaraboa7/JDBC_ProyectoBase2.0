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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import modelo.vo.Departamento;

/**
 *
 * @author hector.garaboacasas
 */
public class DepartamentoDAO {

    public void listarlosdatos(Connection conn, JTextArea ta, JLabel lbltotaldep) throws SQLException {
        ta.setText("");
        String consulta = "select * from Departamentos";
        //usar throws
        Statement sentencia = conn.createStatement();

        ResultSet rs = sentencia.executeQuery(consulta);

        while (rs.next()) {

            ta.append(rs.getInt(1) + " " + rs.getString("dnombre") + " " + rs.getString(3) + "\n");

        }
        consulta = "select count(*) from Departamentos";
        rs = sentencia.executeQuery(consulta);

        if (rs.next()) {
            lbltotaldep.setText("total departamentos: " + rs.getInt(1) + "");
        }

    }

    public void listarlosdatospornumdep(Connection conn, int numdep, JTextArea txtArea, JLabel lbltotaldep) throws SQLException {

        //pepared se usa cuando hay parametros
        txtArea.setText("");
        String consulta = "select * from Departamentos where dept_no=?";
        PreparedStatement sentencia = conn.prepareStatement(consulta);
        sentencia.setInt(1, numdep);

        //cuando es create se pone la sentenmcia en el execute
        //pero cuando es prepared, ya esta la consulta en la creacion del prepared
        //entonces el RS es solo executequery() por estar ya prperadada
        ResultSet rs = sentencia.executeQuery();

        if (rs.next()) {
            txtArea.append(rs.getInt(1) + " " + rs.getString("dnombre") + " " + rs.getString(3) + "\n");
        } else {
            txtArea.append("No hay departamentos");
        }
        consulta = "select count(*) from Departamentos";
        rs = sentencia.executeQuery(consulta);

        if (rs.next()) {
            lbltotaldep.setText("total departamentos: " + rs.getInt(1) + "");
        }

    }

    

    public void listarlosdatoscombobox(Connection conn, JLabel lbltotaldep, DefaultComboBoxModel modelocombo) throws SQLException {

        modelocombo.removeAllElements();

        lbltotaldep.setText("");
        String consulta = "select * from Departamentos";

        Statement sentencia = conn.createStatement();

        ResultSet rs = sentencia.executeQuery(consulta);

        while (rs.next()) {

            // ta.append(rs.getInt(1)+" "+rs.getString("dnombre")+" "+rs.getString(3)+"\n");
            modelocombo.addElement(new Departamento(rs.getInt(1), rs.getString(2), rs.getString(3)));

        }
        consulta = "select count(*) from Departamentos";
        rs = sentencia.executeQuery(consulta);

        if (rs.next()) {
            lbltotaldep.setText("total departamentos: " + rs.getInt(1) + "");
        }

    }

    public int insertar(Connection conn, Integer numdep, String nombre, String loc) throws SQLException {

        

        String consulta = "INSERT INTO departamentos (dept_no, dnombre, loc) VALUES (?, ?, ?);";

        PreparedStatement sentencia = conn.prepareStatement(consulta);

        sentencia.setInt(1, numdep);
        sentencia.setString(2, nombre);
        sentencia.setString(3, loc);

        

        return sentencia.executeUpdate();

    }

    public int borrar(Connection conn, Integer numdep) throws SQLException {
      //  int registrosAfectados;

        String consulta = "DELETE FROM departamentos WHERE dept_no = ?";

        PreparedStatement sentencia = conn.prepareStatement(consulta);

        sentencia.setInt(1, numdep);
       // registrosAfectados = sentencia.executeUpdate();

        return sentencia.executeUpdate();
    }

    public Departamento buscardepartamento(Connection conn, Integer numdep) throws SQLException {

        Departamento d = null;

        String consulta = "select * from Departamentos where dept_no=?";
        PreparedStatement sentencia = conn.prepareStatement(consulta);
        sentencia.setInt(1, numdep);

        ResultSet rs = sentencia.executeQuery();

        if (rs.next()) {
            d = new Departamento(numdep, rs.getString(2), rs.getString(3));
        }
        return d;
    }

    public int modificar(Connection conn, Integer numdep, String nombre, String loc) throws SQLException {

        

        String consulta = "UPDATE departamentos SET dnombre = ?, loc = ? WHERE dept_no = ?";

        PreparedStatement sentencia = conn.prepareStatement(consulta);

        sentencia.setString(1, nombre);
        sentencia.setString(2, loc);
        sentencia.setInt(3, numdep);

        return sentencia.executeUpdate();

        

    }

     public int recuperarHistorico(Connection conn, Departamento d) throws SQLException {
        
        
        String consulta="insert into departamentos VALUES (?, ?, ?)";
      
        PreparedStatement sentencia = conn.prepareStatement(consulta);

        sentencia.setInt(1, d.getDept_no());
        sentencia.setString(2, d.getNombre());
        sentencia.setString(3, d.getLoc());

        return sentencia.executeUpdate();
        
        
        
        
        
     }

    

    

    

   
      

}
