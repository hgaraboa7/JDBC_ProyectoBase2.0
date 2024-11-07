/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author hector.garaboacasas
 */
public class OperativaDAO {
    
    public void contarOperativa(Connection conn, String tipo) throws SQLException {
        



    String consulta = "SELECT cantidad FROM Operativas WHERE Tipo LIKE ?";
    String consulta2 = "UPDATE operativas SET Cantidad = ? WHERE Tipo LIKE ?";

    try (PreparedStatement sentencia = conn.prepareStatement(consulta);
         PreparedStatement sentencia2 = conn.prepareStatement(consulta2)) {

        
        //al introducir un dato incorrecto como nºdep peta y lo reinicia a 0 o valor anterior de transaccion completada
        // si no se pone setautomit false
        sentencia.setString(1, tipo);
        ResultSet rs = sentencia.executeQuery();

        if (rs.next()) {
            sentencia2.setInt(1, (rs.getInt("cantidad") + 1)); 
        } else {
            sentencia2.setInt(1, rs.getInt("cantidad") );
        }

        
        sentencia2.setString(2, tipo);
        sentencia2.executeUpdate();
    }
        
   }
    
}
