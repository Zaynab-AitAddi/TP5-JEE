package web.servlet;

import metier.ProduitMetier;
import dao.model.Produit;
import dao.model.User;
import dao.model.Role;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet pour la page d'accueil et la liste des produits
 * Les permissions varient selon le rôle
 */
public class DashboardServlet extends HttpServlet {
    private static final ProduitMetier produitMetier = ProduitMetier.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        
        String search = req.getParameter("search");
        List<Produit> produits;

        if (search != null && !search.trim().isEmpty()) {
            produits = produitMetier.searchProduits(search);
        } else {
            produits = produitMetier.getAllProduits();
        }

        req.setAttribute("produits", produits);
        req.setAttribute("search", search);
        req.setAttribute("currentUser", currentUser);
        
        // Envoyer les permissions à la JSP
        req.setAttribute("canEdit", currentUser.getRole() == Role.ADMIN || 
                                    currentUser.getRole() == Role.MANAGER);
        req.setAttribute("canDelete", currentUser.getRole() == Role.ADMIN || 
                                      currentUser.getRole() == Role.MANAGER);
        req.setAttribute("canAdd", currentUser.getRole() == Role.ADMIN || 
                                   currentUser.getRole() == Role.MANAGER);
        
        req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
    }
}