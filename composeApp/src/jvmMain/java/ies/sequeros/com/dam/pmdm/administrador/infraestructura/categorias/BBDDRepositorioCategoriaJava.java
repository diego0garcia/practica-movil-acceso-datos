package ies.sequeros.com.dam.pmdm.administrador.infraestructura.categorias;

import ies.sequeros.com.dam.pmdm.administrador.infraestructura.dependientes.DependienteDao;
import ies.sequeros.com.dam.pmdm.administrador.modelo.Categoria;
import ies.sequeros.com.dam.pmdm.administrador.modelo.Dependiente;
import ies.sequeros.com.dam.pmdm.commons.infraestructura.DataBaseConnection;

import java.sql.SQLException;
import java.util.List;

public  class BBDDRepositorioCategoriaJava {
    private final DataBaseConnection db;
    private CategoriasDao dao;
    public BBDDRepositorioCategoriaJava(String path) throws Exception {
        super();
        this.db = new DataBaseConnection();
        this.db.setConfig_path(path);
        this.db.open();
        dao= new CategoriasDao();
        dao.setConn(this.db);

    }
    public void add(Categoria item){
        this.dao.insert(item);
    }
    public boolean remove(Categoria item){
        this.dao.delete(item);
        return true;
    }
    public boolean remove(String id){
        var item=this.dao.getById(id);
        if(item!=null){
            this.remove(item);
            return true;
        }
        return false;
    }
    public boolean  update(Categoria item){
        this.dao.update(item);
        return true;
    }
    public List<Categoria> getAll() {
        return this.dao.getAll();
    }
    public Categoria findByName(String name){

        return null;
    }
    public Categoria  getById(String id){
        return this.dao.getById(id);

    }

    public List<Categoria> findByIds(List<String> ids){
        return null;
    }
    public void close(){
        try {
            this.db.close();
        //no hace caso de esta excepción
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
