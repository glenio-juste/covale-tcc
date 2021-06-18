package br.com.covale.login;


import br.com.covale.dao.UsuarioDao;
import br.com.covale.modelo.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletValidaLogin extends HttpServlet {

    private static final long serialVersionUID = 7633293501883840556L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(); 
        Usuario user = null;
        String login_form = request.getParameter("username"); 
        String senha_form = request.getParameter("password"); 
        try {
            UsuarioDao dao = new UsuarioDao(); 
            user = dao.getUsuario(login_form, senha_form);
        } catch (Exception e) {
        }
        
        if (user == null) {
            session.invalidate();
            
            request.getRequestDispatcher("erro.jsp").forward(request, response);
            request.getRequestDispatcher("login.jsp").forward(request, response);
                       
        } else {            
            session.setAttribute("user", user);
            request.getRequestDispatcher("menu.xhtml").forward(request, response);
        }
    }
}
