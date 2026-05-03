package web.servlet;

import metier.UserMetier;
import dao.model.User;
import util.ValidationUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet pour l'authentification (connexion)
 */
public class LoginServlet extends HttpServlet {
    private static final UserMetier userMetier = UserMetier.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("currentUser");

        // Si déjà connecté, rediriger vers la page d'accueil
        if (user != null) {
            resp.sendRedirect("dashboard");
            return;
        }

        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Validations
        if (ValidationUtil.isEmpty(email) || ValidationUtil.isEmpty(password)) {
            req.setAttribute("error", "Veuillez remplir tous les champs");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        // Authentifier l'utilisateur
        User user = userMetier.authenticate(email, password);

        if (user != null) {
            // Créer la session
            HttpSession session = req.getSession();
            session.setAttribute("currentUser", user);
            session.setMaxInactiveInterval(30 * 60); // 30 minutes

            // Rediriger vers le tableau de bord
            resp.sendRedirect("dashboard");
        } else {
            req.setAttribute("error", "Email ou mot de passe incorrect");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
