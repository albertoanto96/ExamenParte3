import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Created by Alberto on 18/11/2016.
 */
public class Servlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String str;
        user us=null;
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        try {
            Singleton.getInstance().addUser(name, password);
            str = "Usuario creado: " + name;
            response.setContentType("application/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(str);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
            catch (InvocationTargetException e1) {
                e1.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        if (us != null) {
                str = "Usuario existente";
                response.setContentType("application/html");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(str);
            }

   }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String str;
        user sol;
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        try {
            sol = Singleton.getInstance().getUser(name);
            if (sol.getPassword().equals(password)) {
                str = "Acceso autorizado: " + name;
            } else {
                str = "Error al autenticarse";
            }
            response.setContentType("application/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(str);

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
