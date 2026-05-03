package web.servlet;

import metier.ProduitMetier;
import dao.model.Produit;
import dao.model.Role;
import util.ValidationUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet pour ajouter un produit
 */
public class AddProduitServlet extends HttpServlet {
    private static final ProduitMetier produitMetier = ProduitMetier.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Forcer l'encodage UTF-8
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        
        req.getRequestDispatcher("/WEB-INF/views/addProduit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Forcer l'encodage UTF-8
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        
        String nom = req.getParameter("nom");
        String description = req.getParameter("description");
        String prixStr = req.getParameter("prix");
        String categorie = req.getParameter("categorie");

        // Validations
        if (ValidationUtil.isEmpty(nom) || ValidationUtil.isEmpty(description) ||
                ValidationUtil.isEmpty(prixStr) || ValidationUtil.isEmpty(categorie)) {
            req.setAttribute("error", "Veuillez remplir tous les champs");
            req.getRequestDispatcher("/WEB-INF/views/addProduit.jsp").forward(req, resp);
            return;
        }

        // Valider le prix
        if (!ValidationUtil.isValidPrice(prixStr)) {
            req.setAttribute("error", "Le prix doit etre un nombre positif");
            req.getRequestDispatcher("/WEB-INF/views/addProduit.jsp").forward(req, resp);
            return;
        }

        try {
            Double prix = Double.parseDouble(prixStr);
            Produit produit = new Produit(nom, description, prix, categorie);

            if (produitMetier.addProduit(produit)) {
                resp.sendRedirect("dashboard?success=Produit ajoute avec succes");
            } else {
                req.setAttribute("error", "Erreur lors de l'ajout du produit");
                req.getRequestDispatcher("/WEB-INF/views/addProduit.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Format de prix invalide");
            req.getRequestDispatcher("/WEB-INF/views/addProduit.jsp").forward(req, resp);
        }
    }
}