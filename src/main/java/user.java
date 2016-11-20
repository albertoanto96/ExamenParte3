import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alberto on 18/11/2016.
 */
public class user extends Dao {
    public String name;
    public String password;
    public List<Pokemon> pokemon;
    public user(String name, String password){
        this.password=password;
        this.name=name;
        pokemon =new ArrayList<Pokemon>();
    }
    public user(String name){
        this.name=name;
    }
    public void actualizar (List<Pokemon> pok){this.pokemon=pok;}
    public List<Pokemon> getPokemon(){return this.pokemon;}
    public String getName(){return this.name;}
    public String getPassword(){return this.password;}
    public void setName(String name){this.name=name;}
    public int numeroPokemon(){return pokemon.size();}


}
