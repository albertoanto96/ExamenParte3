import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Singleton extends Dao implements Interf {

    private static Singleton instance;

    final static Logger logger = Logger.getLogger(Singleton.class);

    public Singleton() {

        logger.info("se crea hashmap usuarios");

    }

    public static Singleton getInstance() {
        if (instance == null) instance = new Singleton();
        return instance;
    }


    public void addUser(String nombre, String password) throws SQLException, NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        user user = new user(nombre, password);
        user.insert();
        logger.info("se añade usuario: " + nombre);
    }

    public user getUser(String nombre) throws ClassNotFoundException, SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        user u = new user(nombre);
        return u.select();
    }

    public void addPokemon(user u, Pokemon p) throws ClassNotFoundException, SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        List<Pokemon> pokemons = u.selectPokemons();
        if(pokemons==null)
        {
            pokemons= new ArrayList<Pokemon>();
        }
        pokemons.add(p);
        u.actualizar(pokemons);
        u.updatePokemon();

    }

    public void updateUser(String name, String password) throws ClassNotFoundException, SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        user u = new user(name, password);
        u.update();
        logger.info("nombre del usuario: " + name + " y contraseña: " + password);
    }

    public String infoUsuario(String nombre) throws ClassNotFoundException, SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        user u = new user(nombre);
        int i = u.select().numeroPokemon();
        String sol = "tiene " + i + " pokemon";
        logger.info(sol);
        return sol;

    }

    public List<Pokemon> returnPokemon(user u) throws ClassNotFoundException, SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        logger.info("Se devuelve la lista de pokemon del usuario: " + u.getName());
        return u.selectPokemons();
    }

    public ArrayList<user> listaUsuarios() {
        ArrayList<user> us = new ArrayList<user>();
        if (us.size() > 0) {
            Collections.sort(us, new Comparator<user>() {
                public int compare(final user object1, final user object2) {
                    return object1.getName().compareTo(object2.getName());
                }
            });
        }
        return us;
    }
}



