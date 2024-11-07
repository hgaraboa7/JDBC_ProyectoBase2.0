/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.vo.Departamento;

/**
 *
 * @author hector.garaboacasas
 */
public class HistoricoDAO {
    
     public void insertarHistorico(Connection conn, Integer numdep) throws SQLException {
        
         

        String consulta = "select * from Departamentos where dept_no=?";
        PreparedStatement sentencia = conn.prepareStatement(consulta);
        sentencia.setInt(1, numdep);

        ResultSet rs = sentencia.executeQuery();
        
         //hecho mal, haciendolo asi no es con consultas
        String sumaSalarios="select salario from empleados where dept_no=?";
        PreparedStatement sentencia3 = conn.prepareStatement(sumaSalarios);
        sentencia3.setInt(1, numdep);
        
        int salario=0;
        
        ResultSet rs2 = sentencia3.executeQuery();
     
        
        
     
        while(rs2.next()){
            
            salario= salario + rs2.getInt(1);
            
            
        }

        if (rs.next()) {
           
        
        
         String consulta2 = "INSERT INTO historico (hdept_no, hdnombre, hloc, sumsal) VALUES (?, ?, ?,?);";

        PreparedStatement sentencia2 = conn.prepareStatement(consulta2);

        
        
        
        sentencia2.setInt(1, rs.getInt(1) );
        sentencia2.setString(2, rs.getString(2) );
        sentencia2.setString(3, rs.getString(3));
        sentencia2.setInt(4, salario );
        

        sentencia2.executeUpdate();
        
         }
       
        
        
    }

    

   public Departamento buscardepartamentoHistorico(Connection conn, Integer numdep) throws SQLException {
     Departamento d = null;

        String consulta = "select * from historicos where hdept_no=?";
        PreparedStatement sentencia = conn.prepareStatement(consulta);
        sentencia.setInt(1, numdep);

        ResultSet rs = sentencia.executeQuery();

        if (rs.next()) {
            d = new Departamento(numdep, rs.getString(2), rs.getString(3));
        }
        return d;
    }

    public int borrarHistorico(Connection conn, Departamento d) throws SQLException {
        
          String consulta = "DELETE FROM historicos WHERE hdept_no = ?";

        PreparedStatement sentencia = conn.prepareStatement(consulta);

        sentencia.setInt(1, d.getDept_no());
       

        return sentencia.executeUpdate();
        
     }
   
  
    
    
    
}
