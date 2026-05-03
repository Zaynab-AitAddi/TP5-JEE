package web.servlet;

import metier.ProduitMetier;
import dao.model.Produit;
import util.ValidationUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet pour mettre à jour un produit
 */
public class UpdateProduitServlet extends HttpServlet {
    private static final ProduitMetier produitMetier = ProduitMetier.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Forcer l'encodage UTF-8
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            String idStr = req.getParameter("id");
            String nom = req.getParameter("nom");
            String description = req.getParameter("description");
            String prixStr = req.getParameter("prix");
            String categorie = req.getParameter("categorie");

            // Validations
            if (ValidationUtil.isEmpty(idStr) || ValidationUtil.isEmpty(nom) ||
                    ValidationUtil.isEmpty(description) || ValidationUtil.isEmpty(prixStr) ||
                    ValidationUtil.isEmpty(categorie)) {
                resp.sendRedirect("dashboard?error=Tous les champs sont obligatoires");
                return;
            }

            Long id = Long.parseLong(idStr);
            Produit produit = produitMetier.getProduitById(id);

            if (produit == null) {
                resp.sendRedirect("dashboard?error=Produit non trouve");
                return;
            }

            // Valider le prix
            if (!ValidationUtil.isValidPrice(prixStr)) {
                resp.sendRedirect("editProduit?id=" + id + "&error=Prix invalide");
                return;
            }

            Double prix = Double.parseDouble(prixStr);
            produit.setNom(nom);
            produit.setDescription(description);
            produit.setPrix(prix);
            produit.setCategorie(categorie);

            if (produitMetier.updateProduit(produit)) {
                resp.sendRedirect("dashboard?success=Produit modifie avec succes");
            } else {
                resp.sendRedirect("editProduit?id=" + id + "&error=Erreur lors de la modification");
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect("dashboard?error=Format invalide");
        }
    }
}