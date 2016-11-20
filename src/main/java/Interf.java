import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alberto on 18/11/2016.
 */
public interface Interf {

        void addUser(String nombre, String password) throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException;
        user getUser(String nombre) throws ClassNotFoundException, SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException;
        void addPokemon(user u, Pokemon p) throws ClassNotFoundException, SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException;
        void updateUser(String name, String newname) throws ClassNotFoundException, SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException;
        String infoUsuario(String nombre) throws ClassNotFoundException, SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException;
        List<Pokemon> returnPokemon(user u) throws ClassNotFoundException, SQLException, InvocationTargetException, IllegalAccessException, NoSuchMethodException;
        ArrayList<user> listaUsuarios();


}

