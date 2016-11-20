import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public abstract class Dao {

    PreparedStatement prst;
    StringBuffer command= new StringBuffer();
    Field[] fields;
    Method m;
    private Connection getConnection() throws ClassNotFoundException, SQLException
    {
        Connection connect=null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connect= DriverManager.getConnection("jdbc:mysql://localhost/DSA?useLegacyDatetimeCode=false&serverTimezone=America/New_York","root","root");
        return  connect;

    }

    public List<Pokemon> selectPokemons() throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<Pokemon> pokemons=new ArrayList<Pokemon>();
        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("SELECT pokemon FROM ").append(this.getClass().getSimpleName()+" WHERE ");

        fields= this.getClass().getFields();
        for(Field f :fields){
            if(f.getName().toString().equals("name")){
                command.append(f.getName().toString()+"=?;");
            }
        }
        String query=command.toString();
        prst= con.prepareStatement(query);
        this.setName(prst);
        ResultSet rs= prst.executeQuery();
        ResultSetMetaData rsmd= rs.getMetaData();
        rs.next();
        for(int i=1;i<rsmd.getColumnCount()+1;i++){
            try {
                if (rsmd.getColumnTypeName(i).equals("JSON")) {

                    Gson gson = new Gson();
                    pokemons = gson.fromJson(rs.getString(1), List.class);

                }
                if(i==rsmd.getColumnCount()){
                    rs.next();
                    i=0;
                }
            }
            catch (Exception e){

            }

        }
        return pokemons;
    }


    public  void setName(PreparedStatement prst) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {
        int i = 1;
        for (Field f : fields) {

            m = this.getClass().getMethod(getMethod((f.getName())), null);
            Object ret = m.invoke(this, null);

            if (ret instanceof String) {

                if (f.getName().equals("name")) {
                    String name = ret.toString();
                    prst.setString(i, name);
                }
            }
            i++;
        }
    }
    public  void setId(PreparedStatement prst) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {
        int i=1;
        for(Field f:fields){

            m=this.getClass().getMethod(getMethod((f.getName())),null);
            Object ret=m.invoke(this,null);

            if(ret instanceof Integer){

                if(f.getName().equals("id")) {
                    String id = ret.toString();
                    int id2 = Integer.parseInt(id);
                    prst.setInt(i, id2);
                    System.out.println("res:" + id);
                }
            }
            i++;

        }
    }
    public void addParams(PreparedStatement prst) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {
        int i=1;
        for(Field f:fields){

            m=this.getClass().getMethod(getMethod((f.getName())),null);
            Object ret=m.invoke(this,null);

            if(ret instanceof String){
                if((f.getName().equals("name"))||(f.getName().equals(("password")))) {
                    String id = ret.toString();
                    prst.setString(i, id);
                }
            }
            if (ret instanceof List) {
                prst.setObject(i, "null");
            }
            i++;

        }
    }

    public void setParams(PreparedStatement prst) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException {
        for (Field f : fields) {

            m = this.getClass().getMethod(getMethod((f.getName())), null);
            Object ret = m.invoke(this, null);

            if(ret instanceof String){
                if(f.getName().equals("name")) {
                    String id = ret.toString();
                    prst.setString(2, id);
                }
            }
            if (ret instanceof List) {
                List<Pokemon> pok = (List<Pokemon>) ret;
                String json = new Gson().toJson(pok);
                prst.setObject(1, json);
            }
        }

    }
    private  String getMethod(String m){

        m =m.substring(0, 1).toUpperCase() +m.substring(1);
        return "get"+m;

    }

    public void insert() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, SQLException, ClassNotFoundException, NoSuchFieldException {

        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("INSERT INTO ").append(this.getClass().getSimpleName()+" (");
        fields= this.getClass().getFields();
        for(Field f : fields)
        {
            command.append(f.getName() + ",");

        }

        command.replace(command.length()-1,command.length(),") VALUES (");
        for(Field f : fields)
        {
            command.append("?,");

        }
        command.replace(command.length()-1,command.length(),");");

        String query=command.toString();
        prst= con.prepareStatement(query);
        this.addParams(prst);
        prst.execute();

    }

    public  void update() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException, ClassNotFoundException {

        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("UPDATE ").append(this.getClass().getSimpleName()+" SET ");

        System.out.println(this.getClass().getSimpleName());

        fields= this.getClass().getFields();
        for(Field f : fields)
        {
            if(f.getName().toString().equals("name")) {
            command.append(f.getName() + "=?, ");
        }
            if(f.getName().toString().equals("password")) {
            command.append(f.getName() + "=? ");
        }

        }
        command.replace(command.length()-2,command.length()," WHERE ");
        for(Field f :fields){
            if(f.getName().toString().equals("name")){
                command.append(f.getName().toString()+"=? AND");
            }
            if(f.getName().toString().equals("password")){
                command.append(f.getName().toString()+"=?;");
            }
        }

        String query=command.toString();
        prst= con.prepareStatement(query);
        this.addParams(prst);
        prst.execute();
    }
    public  void delete() throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {


        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("DELETE FROM ").append(this.getClass().getSimpleName()+" WHERE ");

        System.out.println(this.getClass().getSimpleName());

        fields= this.getClass().getFields();

        for(Field f :fields){
            if(f.getName().toString().equals("id")){
                command.append(f.getName().toString()+"=?;");
            }
        }
        System.out.println(command.toString());
        String query=command.toString();
        prst= con.prepareStatement(query);
        this.setId(prst);
        prst.execute();

    }

    public user select() throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        StringBuffer tablas=new StringBuffer();
        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("SELECT * FROM ").append(this.getClass().getSimpleName()+" WHERE ");

        fields= this.getClass().getFields();
        for(Field f :fields){
            if(f.getName().toString().equals("name")){
                command.append(f.getName().toString()+"=?;");
            }
        }
        String query=command.toString();
        prst= con.prepareStatement(query);
        this.setName(prst);
        ResultSet rs= prst.executeQuery();
        rs.beforeFirst();
        ResultSetMetaData rsmd= rs.getMetaData();
        rs.next();
        for(int i=1;i!=0;i++){
            try {

                if (rsmd.getColumnTypeName(i).equals("INT")) {
                    tablas.append(rs.getInt(i)+",");
                }
                if (rsmd.getColumnTypeName(i).equals("VARCHAR")) {
                    tablas.append(rs.getString(i)+",");
                }
                if(i==rsmd.getColumnCount()){
                    rs.next();
                    i=-1;
                }
            }
            catch (Exception e){

            }

        }
        tablas.append("-");
        String[] parts= tablas.toString().split("-");
        String[] parts2=parts[0].split(",");
        user nuevousuario= new user(parts2[1],parts2[2]);
        return nuevousuario;
    }

    public  void updatePokemon() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, SQLException, ClassNotFoundException {

        Connection con= this.getConnection();
        command= new StringBuffer();
        command.append("UPDATE ").append(this.getClass().getSimpleName()+" SET ");

        System.out.println(this.getClass().getSimpleName());

        fields= this.getClass().getFields();
        for(Field f : fields)
        {
            if(f.getName().toString().equals("pokemon")) {
                command.append(f.getName() + "=? ");
            }


        }
        command.replace(command.length()-1,command.length()," WHERE ");
        for(Field f :fields){

            if(f.getName().toString().equals("name")){
                command.append(f.getName().toString()+"=?;");
            }
        }

        String query=command.toString();
        prst= con.prepareStatement(query);
        this.setParams(prst);
        prst.execute();
    }


}
