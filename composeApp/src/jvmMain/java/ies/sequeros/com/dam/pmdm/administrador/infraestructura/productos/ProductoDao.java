package ies.sequeros.com.dam.pmdm.administrador.infraestructura.productos;


import ies.sequeros.com.dam.pmdm.administrador.modelo.Pedido;
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

public class ProductoDao implements IDao<Producto> {
    private DataBaseConnection conn;
    private final String table_name = "PRODUCTO";
    private final String selectall = "select * from " + table_name;
    private final String selectbyid = "select * from " + table_name + " where id=?";
    private final String findbyname = "select * from " + table_name + " where name=?";

    private final String deletebyid = "delete from " + table_name + " where id= ?";
    private final String insert = "INSERT INTO " + table_name + " (id, id_categoria, name, image_path, descripcion, price, enabled, categoriasName) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String update =
            "UPDATE " + table_name + " SET id_categoria = ?, name = ?, image_path = ?, descripcion = ?, price = ?, enabled = ?, categoriasName = ?" +
                    "WHERE id = ?";
    public ProductoDao() {
    }

    public DataBaseConnection getConn() {
        return this.conn;
    }

    public void setConn(final DataBaseConnection conn) {
        this.conn = conn;
    }

    @Override
    public Producto getById(final String id) {
        Producto sp = null;// = new Dependiente();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(selectbyid);
            pst.setString(1, id);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sp = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(ProductoDao.class.getName());
            logger.info("Ejecutando SQL: " + selectbyid + " | Parametros: [id=" + id + "]");
            return sp;
        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sp;
    }

    public Producto findByName(final String name) {
        Producto sp = null;// = new Dependiente();
        try {
            final PreparedStatement pst = conn.getConnection().prepareStatement(findbyname);
            pst.setString(1, name);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                sp = registerToObject(rs);
            }
            pst.close();
            Logger logger = Logger.getLogger(ProductoDao.class.getName());
            logger.info("Ejecutando SQL: " + findbyname + " | Parametros: [name=" + name + "]");

            return sp;
        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sp;
    }
    @Override
    public List<Producto> getAll() {
        final ArrayList<Producto> scl = new ArrayList<>();
        Producto tempo;
        PreparedStatement pst = null;
        try {
            try {
                pst = conn.getConnection().prepareStatement(selectall);
            } catch (final SQLException ex) {
                Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tempo = registerToObject(rs);
                scl.add(tempo);
            }

            pst.close();
            Logger logger = Logger.getLogger(ProductoDao.class.getName());
            logger.info("Ejecutando SQL: " + selectall+ " | Parametros: ");

        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return scl;
    }
    //Pedido

    @Override
    public void update(final Producto item) {
        try {
            final PreparedStatement pst =
                    conn.getConnection().prepareStatement(update);
            pst.setString(8, item.getId());
            pst.setString(1, item.getCategoriaId());
            pst.setString(2, item.getName());
            pst.setString(3,item.getImagePath());
            pst.setString(4,item.getDescription());
            pst.setFloat(5,item.getPrice());
            pst.setBoolean(6, item.getEnabled());
            pst.setString(7, item.getCategoriaName());
            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(ProductoDao.class.getName());
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
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(final Producto item) {
        try {
            final PreparedStatement pst =
                    conn.getConnection().prepareStatement(deletebyid);
            pst.setString(1, item.getId());
            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(ProductoDao.class.getName());
            logger.info("Ejecutando SQL: " + deletebyid + " | Parametros: [id=" + item.getId() + "]");

        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    @Override
    public void insert(final Producto item) {
        final PreparedStatement pst;
        try {
            pst = conn.getConnection().prepareStatement(insert,
                    Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, item.getId());
            pst.setString(2, item.getCategoriaId());
            pst.setString(3, item.getName());
            pst.setString(4,item.getImagePath());
            pst.setString(5,item.getDescription());
            pst.setFloat(6, item.getPrice());
            pst.setBoolean(7, item.getEnabled());
            pst.setString(8, item.getCategoriaName());

            pst.executeUpdate();
            pst.close();
            Logger logger = Logger.getLogger(ProductoDao.class.getName());
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
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    //pasar de registro a objeto
    private Producto registerToObject(final ResultSet r) {

        Producto sc =null;
        try {
            sc=new Producto(
                    r.getString("ID"),
                    r.getString("ID_CATEGORIA"),
                    r.getString("NAME"),
                    r.getString("IMAGE_PATH"),
                    r.getString("DESCRIPCION"),
                    r.getFloat("PRICE"),
                    r.getBoolean("ENABLED"),
                    r.getString("CATEGORIASNAME"));
            return sc;
        } catch (final SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return sc;
    }
}