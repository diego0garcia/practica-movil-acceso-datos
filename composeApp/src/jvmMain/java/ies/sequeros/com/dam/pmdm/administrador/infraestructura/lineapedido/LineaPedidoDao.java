package ies.sequeros.com.dam.pmdm.administrador.infraestructura.lineapedido;


import ies.sequeros.com.dam.pmdm.administrador.modelo.LineaPedido;
import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.DataBaseConnection;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.IDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LineaPedidoDao implements IDao<LineaPedido> {
    private DataBaseConnection conn;
    private final String table_name = "LINEAPEDIDO";
    private final String selectall = "select * from " + table_name;
    private final String selectbyid = "select * from " + table_name + " where id=?";
    private final String findbyname = "select * from " + table_name + " where name=?";

    private final String deletebyid = "delete from " + table_name + " where id= ?";
    private final String insert = "INSERT INTO " + table_name + " (id, product_name, product_price, id_pedido) " +
            "VALUES (?, ?, ?, ?)";
    private final String update =
            "UPDATE " + table_name + " SET product_name = ?, product_price = ?, id_pedido = ?" +
                    "WHERE id = ?";
    public LineaPedidoDao() {
    }

    public DataBaseConnection getConn() {
        return this.conn;
    }

    public void setConn(final DataBaseConnection conn) {
        this.conn = conn;
    }

    @Override
    public LineaPedido getById(final String id) {
        LineaPedido sp = null;// = new Dependiente();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(selectbyid);
            pst.setString(1, id);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sp = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(LineaPedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + selectbyid + " | Parametros: [id=" + id + "]");
            return sp;
        } catch (final SQLException ex) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sp;
    }

    public LineaPedido findByName(final String name) {
        LineaPedido sp = null;// = new Dependiente();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(findbyname);
            pst.setString(1, name);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sp = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(LineaPedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + findbyname + " | Parametros: [name=" + name + "]");

            return sp;
        } catch (final SQLException ex) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sp;
    }
    @Override
    public List<LineaPedido> getAll() {
        final ArrayList<LineaPedido> scl = new ArrayList<>();
        LineaPedido tempo;
        PreparedStatement pst = null;
        try {
            try {
                pst = conn.getConnection().prepareStatement(selectall);
            } catch (final SQLException ex) {
                Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tempo = registerToObject(rs);
                scl.add(tempo);
            }

            pst.close();
            Logger logger = Logger.getLogger(LineaPedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + selectall+ " | Parametros: ");

        } catch (final SQLException ex) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return scl;
    }
    //Pedido

    @Override
    public void update(final LineaPedido item) {

        try {
            final PreparedStatement pst =
                    conn.getConnection().prepareStatement(update);
            pst.setString(4, item.getId());
            pst.setString(1, item.getProduct_name());
            pst.setFloat(2, item.getProduct_price());
            pst.setString(3,item.getId_pedido());
            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(LineaPedidoDao.class.getName());
            /*
            logger.info(() ->
                    "Ejecutando SQL: " + update +
                            " | Params: [1]=" + item.getName() +
                            ", [2]=" + item.getImagePath() +
                            ", [3]=" + item.getDescripcion() +
                            ", [4]=" + item.getEnable() +
                            ", [5]=" + item.getDate() +
                            ", [6]=" + item.getId() +
                            "]"
            );
            */
        } catch (final SQLException ex) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(final LineaPedido item) {
        try {
            final PreparedStatement pst =
                    conn.getConnection().prepareStatement(deletebyid);
            pst.setString(1, item.getId());
            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(LineaPedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + deletebyid + " | Parametros: [id=" + item.getId() + "]");

        } catch (final SQLException ex) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    @Override
    public void insert(final LineaPedido item) {
        final PreparedStatement pst;
        try {
            pst = conn.getConnection().prepareStatement(insert,
                    Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, item.getId());
            pst.setString(2, item.getProduct_name());
            pst.setFloat(3, item.getProduct_price());
            pst.setString(4,item.getId_pedido());

            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(LineaPedidoDao.class.getName());
            /*
            logger.info(() ->
                    "Ejecutando SQL: " + update +
                            " | Params: [1]=" + item.getName() +
                            ", [2]=" + item.getImagePath() +
                            ", [3]=" + item.getDescripcion() +
                            ", [4]=" + item.getEnable() +
                            ", [5]=" + item.getDate() +
                            ", [6]=" + item.getId() +
                            "]"
            );
             */
        } catch (final SQLException ex) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    //pasar de registro a objeto
    private LineaPedido registerToObject(final ResultSet r) {

        LineaPedido sc =null;
        try {
            sc=new LineaPedido(
                    r.getString("ID"),
                    r.getString("PRODUCT_NAME"),
                    r.getFloat("PRODUCT_PRICE"),
                    r.getString("ID_PEDIDO"));
            return sc;
        } catch (final SQLException ex) {
            Logger.getLogger(LineaPedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sc;
    }
}