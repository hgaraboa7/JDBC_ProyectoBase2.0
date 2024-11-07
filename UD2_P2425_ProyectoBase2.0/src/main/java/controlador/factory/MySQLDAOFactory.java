package controlador.factory;

import java.sql.Connection;
import java.sql.SQLException;
import controlador.pool.BasicConnectionPool;
import modelo.dao.DepartamentoDAO;
import modelo.dao.EmpleadoDAO;
import modelo.dao.HistoricoDAO;
import modelo.dao.OperativaDAO;

public class MySQLDAOFactory extends DAOFactory {

    final static String user = "root";
    final static String password = "root";
    final static String BD = "ejemplo"; //Indica aqui la BD 
    final static String IP = "192.168.56.101"; //Indica aqui la IP 
    final static String url = "jdbc:mysql://" + IP + ":3306/" + BD;

    //192.168.56.101
    static BasicConnectionPool bcp;

    public MySQLDAOFactory() {

        try {
            bcp = BasicConnectionPool.create(url, user, password);
        } catch (SQLException e) {
            // TODO Auto-generated catch blocks
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return bcp.getConnection();
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        return bcp.releaseConnection(connection);
    }

    @Override
    public int getSize() {
        return bcp.getSize();
    }
    //add getUser, getURL....

    @Override
    public void shutdown() throws SQLException {
        bcp.shutdown();
    }
    //implementamos los métodos abstractos

    @Override
    public DepartamentoDAO getDepartamentoDAO() {

        return new DepartamentoDAO();
    }

    @Override
    public EmpleadoDAO getEmpleadoDAO() {

        return new EmpleadoDAO();
    }

    @Override
    public HistoricoDAO getHistoricoDAO() {
        return new HistoricoDAO();
    }

    @Override
    public OperativaDAO getOperativaDAO() {
        return new OperativaDAO();
    }

}
