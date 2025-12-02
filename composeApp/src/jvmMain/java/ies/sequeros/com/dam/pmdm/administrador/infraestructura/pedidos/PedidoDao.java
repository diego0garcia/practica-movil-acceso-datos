package ies.sequeros.com.dam.pmdm.administrador.infraestructura.pedidos;


import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido;
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

public class PedidoDao implements IDao<Pedido> {
    private DataBaseConnection conn;
    private final String table_name = "PEDIDO";
    private final String selectall = "select * from " + table_name;
    private final String selectbyid = "select * from " + table_name + " where id=?";
    private final String findbyname = "select * from " + table_name + " where name=?";

    private final String deletebyid = "delete from " + table_name + " where id='?'";
    private final String insert = "INSERT INTO " + table_name + " (id, name, iage_path, descripcion, enabled, date, id_dependiente) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String update =
            "UPDATE " + table_name + " SET name = ?, image_path = ?, descripcion = ?, enabled = ?, date = ?, id_dependiente = ? " +
                    "WHERE id = ?";
    public PedidoDao() {
    }

    public DataBaseConnection getConn() {
        return this.conn;
    }

    public void setConn(final DataBaseConnection conn) {
        this.conn = conn;
    }

    @Override
    public Pedido getById(final String id) {
        Pedido sp = null;// = new Dependiente();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(selectbyid);
            pst.setString(1, id);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sp = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(PedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + selectbyid + " | Parametros: [id=" + id + "]");
            return sp;
        } catch (final SQLException ex) {
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sp;
    }

    public Pedido findByName(final String name) {
        Pedido sp = null;// = new Dependiente();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(findbyname);
            pst.setString(1, name);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sp = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(PedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + findbyname + " | Parametros: [name=" + name + "]");

            return sp;
        } catch (final SQLException ex) {
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sp;
    }
    @Override
    public List<Pedido> getAll() {
        final ArrayList<Pedido> scl = new ArrayList<>();
        Pedido tempo;
        PreparedStatement pst = null;
        try {
            try {
                pst = conn.getConnection().prepareStatement(selectall);
            } catch (final SQLException ex) {
                Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tempo = registerToObject(rs);
                scl.add(tempo);
            }

            pst.close();
            Logger logger = Logger.getLogger(PedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + selectall+ " | Parametros: ");

        } catch (final SQLException ex) {
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return scl;
    }

    @Override
    public void update(final Pedido item) {

        try {
            final PreparedStatement pst =
                    conn.getConnection().prepareStatement(update);
            pst.setString(1, item.getName());
            pst.setString(2, item.getImagePath());
            pst.setString(3,item.getDescripcion());
            pst.setBoolean(4, item.getEnable());
            pst.setDate(5, item.getDate());
            pst.setDate(6, item.getDate());
            pst.setString(7, item.getId());
            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(PedidoDao.class.getName());
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
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

    }

    @Override
    public void delete(final Pedido item) {
        try {
            final PreparedStatement pst =
                    conn.getConnection().prepareStatement(deletebyid);
            pst.setString(1, item.getId());
            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(PedidoDao.class.getName());
            logger.info("Ejecutando SQL: " + deletebyid + " | Parametros: [id=" + item.getId() + "]");

        } catch (final SQLException ex) {
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    @Override
    public void insert(final Pedido item) {

        final PreparedStatement pst;
        try {
           pst = conn.getConnection().prepareStatement(insert,
                    Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, item.getId());
            pst.setString(2, item.getName());
            pst.setString(5,item.getImagePath());
            pst.setString(6,item.getDescripcion());
            pst.setBoolean(7, item.getEnable());
            pst.setDate(8, item.getDate());
            pst.setString(9, item.getId_dependiente());

            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(PedidoDao.class.getName());
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

        } catch (final SQLException ex) {
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    //pasar de registro a objeeto
    private Pedido registerToObject(final ResultSet r) {
        Pedido sc =null;
        try {
            sc=new Pedido(
                    r.getString("ID"),
                    r.getString("NAME"),
                    r.getString("IMAGE_PATH"),
                    r.getString("DESCRIPCION"),
                    r.getBoolean("ENABLED"),
                    r.getDate("DATE"),
                    r.getString("ID_DEPENDIENTE"));
            return sc;
        } catch (final SQLException ex) {
            Logger.getLogger(PedidoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sc;
    }
}
