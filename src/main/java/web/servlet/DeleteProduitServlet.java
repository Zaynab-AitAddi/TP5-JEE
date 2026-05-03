package web.servlet;

import metier.ProduitMetier;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet pour supprimer un produit
 */
public class DeleteProduitServlet extends HttpServlet {
    private static final ProduitMetier produitMetier = ProduitMetier.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Forcer l'encodage UTF-8
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        try {
            String idStr = req.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {
                resp.sendRedirect("dashboard?error=ID produit manquant");
                return;
            }

            Long id = Long.parseLong(idStr);

            if (produitMetier.deleteProduit(id)) {
                resp.sendRedirect("dashboard?success=Produit supprime avec succes");
            } else {
                resp.sendRedirect("dashboard?error=Erreur lors de la suppression");
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect("dashboard?error=ID produit invalide");
        }
    }
}