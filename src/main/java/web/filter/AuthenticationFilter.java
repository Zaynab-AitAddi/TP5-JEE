package web.filter;

import dao.model.User;
import dao.model.Role;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filtre pour l'authentification et le contrôle d'accès basé sur les rôles
 */
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // Forcer l'encodage UTF-8
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = requestURI.substring(contextPath.length());
        
        HttpSession session = req.getSession(false);

        // 1. URLs publiques (accessibles sans authentification)
        if (isPublicURL(path)) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Vérifier si l'utilisateur est authentifié
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("currentUser");
        }

        if (user == null) {
            // Non authentifié → rediriger vers login
            resp.sendRedirect(contextPath + "/login");
            return;
        }

        // 3. Vérifier les permissions selon le rôle
        if (!hasPermission(user.getRole(), path)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, 
                "Accès refusé. Vous n'avez pas les permissions nécessaires.");
            return;
        }

        // 4. Tout est OK
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    /**
     * Vérifie si l'URL est accessible au public
     */
    private boolean isPublicURL(String path) {
        // Permettre les ressources statiques
        if (path.endsWith(".css") || path.endsWith(".js") || 
            path.endsWith(".png") || path.endsWith(".jpg") || 
            path.endsWith(".jpeg") || path.endsWith(".gif") ||
            path.endsWith(".ico") || path.endsWith(".woff") || 
            path.endsWith(".woff2") || path.endsWith(".ttf")) {
            return true;
        }
        
        // URLs publiques
        String[] publicUrls = {"/login", "/signup"};
        for (String publicURL : publicUrls) {
            if (path.equals(publicURL) || path.startsWith(publicURL)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si l'utilisateur a la permission d'accéder à l'URL
     */
    private boolean hasPermission(Role role, String path) {
        // ADMIN a accès à tout
        if (role == Role.ADMIN) {
            return true;
        }
        
        // URLs réservées à ADMIN et MANAGER (pas USER)
        String[] managerAdminUrls = {"/addProduit", "/editProduit", "/updateProduit", "/deleteProduit"};
        for (String url : managerAdminUrls) {
            if (path.contains(url)) {
                return role == Role.MANAGER || role == Role.ADMIN;
            }
        }
        
        // Toutes les autres URLs (dashboard, search) sont accessibles à tous les authentifiés
        return true;
    }
}