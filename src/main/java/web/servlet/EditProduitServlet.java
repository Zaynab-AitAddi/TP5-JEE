package web.servlet;

import metier.ProduitMetier;
import dao.model.Produit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet pour éditer un produit
 */
public class EditProduitServlet extends HttpServlet {
    private static final ProduitMetier produitMetier = ProduitMetier.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String idStr = req.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {
                resp.sendRedirect("dashboard?error=ID produit manquant");
                return;
            }

            Long id = Long.parseLong(idStr);
            Produit produit = produitMetier.getProduitById(id);

            if (produit == null) {
                resp.sendRedirect("dashboard?error=Produit non trouvé");
                return;
            }

            req.setAttribute("produit", produit);
            req.getRequestDispatcher("/WEB-INF/views/editProduit.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect("dashboard?error=ID produit invalide");
        }
    }
}
