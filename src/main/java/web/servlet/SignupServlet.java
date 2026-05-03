package web.servlet;

import metier.UserMetier;
import dao.model.User;
import util.PasswordUtil;
import util.ValidationUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet pour l'enregistrement (inscription)
 */
public class SignupServlet extends HttpServlet {
    private static final UserMetier userMetier = UserMetier.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String nom = req.getParameter("nom");
        String prenom = req.getParameter("prenom");

        // Validations
        if (ValidationUtil.isEmpty(email) || ValidationUtil.isEmpty(password) ||
                ValidationUtil.isEmpty(confirmPassword) || ValidationUtil.isEmpty(nom) ||
                ValidationUtil.isEmpty(prenom)) {
            req.setAttribute("error", "Veuillez remplir tous les champs");
            req.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(req, resp);
            return;
        }

        // Vérifier que les mots de passe correspondent
        if (!password.equals(confirmPassword)) {
            req.setAttribute("error", "Les mots de passe ne correspondent pas");
            req.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(req, resp);
            return;
        }

        // Vérifier la force du mot de passe
        if (!PasswordUtil.isStrongPassword(password)) {
            req.setAttribute("error", "Le mot de passe doit contenir au moins 8 caractères, " +
                    "1 majuscule, 1 minuscule et 1 chiffre");
            req.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(req, resp);
            return;
        }

        // Vérifier l'email
        if (!ValidationUtil.isValidEmail(email)) {
            req.setAttribute("error", "Veuillez entrer une adresse email valide");
            req.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(req, resp);
            return;
        }

    // Dans le doPost, remplacer les messages :
    if (userMetier.signUp(email, password, nom, prenom)) {
        req.setAttribute("success", "Inscription reussie ! Veuillez vous connecter.");
        req.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(req, resp);
    } else {
        if (userMetier.getUserByEmail(email) != null) {
            req.setAttribute("error", "Cet email est deja utilise");
        } else {
            req.setAttribute("error", "Erreur lors de l'inscription. Veuillez reessayer.");
        }
        req.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(req, resp);
    }
    }
}
