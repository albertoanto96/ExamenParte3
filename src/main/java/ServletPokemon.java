import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alberto on 18/11/2016.
 */
public class ServletPokemon extends HttpServlet{

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String ret = null;
        List<Pokemon> sol = new ArrayList<Pokemon>();
        String nombre = request.getParameter("nombre");
        String name=request.getParameter("name");
        user us=new user(nombre);
        try {
            us= Singleton.getInstance().getUser(nombre);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            sol= Singleton.getInstance().returnPokemon(us);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }  catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Pokemon pokemon= new Pokemon(name);

        try {
            Singleton.getInstance().addPokemon(us, pokemon);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


        response.setContentType("application/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(ret);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        List<String> ret = new ArrayList<String>();
        List<Pokemon> sol = null;
        String nombre=request.getParameter("nombre");
        user us=null;
        try {
            us=Singleton.getInstance().getUser(nombre);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            sol= Singleton.getInstance().returnPokemon(us);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }  catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Gson gsonSender = new Gson();
        String gson = gsonSender.toJson(sol);
        Gson gsonReceiver = new Gson();
        List obj = gsonReceiver.fromJson(gson, List.class);
        for(int i=0;i<obj.size();i++) {
            String pok = obj.get(i).toString();
            String[] part = pok.split("}");
            String[] poke = part[0].split("=");
            ret.add(poke[1]);
        }

        String json2 = new Gson().toJson(ret);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json2);


    }
}
