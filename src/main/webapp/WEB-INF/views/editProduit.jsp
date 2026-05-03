<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifier un Produit - Gestion des Produits</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            font-family: 'Poppins', 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
        }

        /* Navbar Styles */
        .navbar {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            padding: 1rem 0;
        }

        .navbar-brand {
            font-weight: 800;
            font-size: 1.6rem;
            color: white !important;
            letter-spacing: -0.5px;
            transition: all 0.3s ease;
        }

        .navbar-brand:hover {
            transform: scale(1.05);
        }

        .nav-link {
            color: rgba(255, 255, 255, 0.85) !important;
            font-weight: 500;
            transition: all 0.3s ease;
            padding: 0.5rem 1rem !important;
        }

        .nav-link:hover {
            color: white !important;
            transform: translateY(-2px);
        }

        .user-badge {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 0.5rem 1rem;
            border-radius: 30px;
            font-size: 0.85rem;
            font-weight: 600;
            background: rgba(255, 255, 255, 0.2);
            color: white;
            backdrop-filter: blur(10px);
            transition: all 0.3s ease;
        }

        .user-badge:hover {
            background: rgba(255, 255, 255, 0.3);
            transform: translateY(-2px);
        }

        /* Page Header */
        .page-header {
            background: linear-gradient(135deg, rgba(255,255,255,0.95) 0%, rgba(255,255,255,0.85) 100%);
            padding: 3rem 0;
            margin-bottom: 2rem;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
            border-bottom: 1px solid rgba(102, 126, 234, 0.1);
        }

        .page-header h1 {
            font-size: 2.5rem;
            font-weight: 800;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            background-clip: text;
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            margin-bottom: 0.5rem;
        }

        .page-header p {
            color: #666;
            font-size: 1.1rem;
        }

        /* Form Container */
        .form-container {
            max-width: 900px;
            margin: 0 auto;
            padding: 0 1rem 3rem;
        }

        /* Back Button */
        .back-link {
            display: inline-flex;
            align-items: center;
            gap: 10px;
            background: white;
            padding: 0.8rem 1.5rem;
            border-radius: 50px;
            color: #667eea;
            text-decoration: none;
            font-weight: 600;
            margin-bottom: 2rem;
            transition: all 0.3s ease;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
        }

        .back-link:hover {
            transform: translateX(-5px);
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.2);
            color: #764ba2;
        }

        /* Product Info Card */
        .product-info-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 20px;
            padding: 1.5rem;
            margin-bottom: 2rem;
            color: white;
            box-shadow: 0 10px 30px rgba(102, 126, 234, 0.3);
        }

        .product-info-title {
            font-size: 0.9rem;
            opacity: 0.9;
            margin-bottom: 0.5rem;
            letter-spacing: 1px;
        }

        .product-info-value {
            font-size: 1.8rem;
            font-weight: 800;
        }

        /* Form Card */
        .form-card {
            background: white;
            border-radius: 25px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.08);
            overflow: hidden;
            transition: all 0.3s ease;
        }

        .form-card:hover {
            box-shadow: 0 25px 50px rgba(102, 126, 234, 0.15);
        }

        .form-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 1.5rem 2rem;
            color: white;
        }

        .form-header h3 {
            margin: 0;
            font-weight: 700;
            font-size: 1.3rem;
        }

        .form-body {
            padding: 2rem;
        }

        /* Form Controls */
        .form-group {
            margin-bottom: 1.8rem;
        }

        .form-label {
            font-weight: 700;
            color: #333;
            margin-bottom: 0.6rem;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .form-label i {
            color: #667eea;
            width: 20px;
        }

        .required-field::after {
            content: '*';
            color: #dc3545;
            margin-left: 4px;
        }

        .form-control, .form-select {
            border: 2px solid #e9ecef;
            border-radius: 12px;
            padding: 0.85rem 1.2rem;
            font-size: 1rem;
            transition: all 0.3s ease;
        }

        .form-control:focus, .form-select:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.15);
            outline: none;
        }

        textarea.form-control {
            resize: vertical;
            min-height: 120px;
        }

        /* Action Buttons */
        .form-actions {
            display: flex;
            gap: 1rem;
            margin-top: 2rem;
            padding-top: 1.5rem;
            border-top: 2px solid #f0f0f0;
        }

        .btn-update {
            background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
            border: none;
            padding: 0.9rem 2rem;
            font-weight: 700;
            border-radius: 12px;
            transition: all 0.3s ease;
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }

        .btn-update:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 25px rgba(40, 167, 69, 0.3);
        }

        .btn-cancel {
            background: #6c757d;
            border: none;
            padding: 0.9rem 2rem;
            font-weight: 700;
            border-radius: 12px;
            transition: all 0.3s ease;
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }

        .btn-cancel:hover {
            background: #5a6268;
            transform: translateY(-3px);
        }

        /* Alert Styles */
        .alert-custom {
            border-radius: 15px;
            border: none;
            padding: 1rem 1.5rem;
            margin-bottom: 2rem;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
        }

        /* Footer */
        .footer {
            background: linear-gradient(135deg, #2d3748 0%, #1a202c 100%);
            color: white;
            text-align: center;
            padding: 2rem;
            margin-top: 3rem;
        }

        /* Animations */
        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .form-card {
            animation: fadeInUp 0.5s ease;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .page-header h1 {
                font-size: 1.8rem;
            }
            
            .form-body {
                padding: 1.5rem;
            }
            
            .form-actions {
                flex-direction: column;
            }
            
            .product-info-value {
                font-size: 1.3rem;
            }
        }
    </style>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark sticky-top">
        <div class="container-fluid px-4">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard">
                <i class="fas fa-box"></i> Gestion Produits
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto align-items-center">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/dashboard">
                            <i class="fas fa-home"></i> Accueil
                        </a>
                    </li>
                    <li class="nav-item">
                        <div class="user-badge">
                            <i class="fas fa-user-circle"></i>
                            ${sessionScope.currentUser.prenom} ${sessionScope.currentUser.nom}
                        </div>
                    </li>
                    <li class="nav-item ms-2">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">
                            <i class="fas fa-sign-out-alt"></i> Déconnexion
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Page Header -->
    <div class="page-header">
        <div class="container-fluid">
            <h1><i class="fas fa-edit"></i> Modifier un Produit</h1>
            <p>Mettez à jour les informations du produit dans le système</p>
        </div>
    </div>

    <!-- Main Content -->
    <div class="form-container">
        <a href="${pageContext.request.contextPath}/dashboard" class="back-link">
            <i class="fas fa-arrow-left"></i> Retour à la liste des produits
        </a>

        <!-- Alert Messages -->
        <c:if test="${not empty param.error}">
            <div class="alert alert-danger alert-custom alert-dismissible fade show" role="alert">
                <i class="fas fa-exclamation-triangle"></i>
                <strong>Erreur!</strong> ${param.error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <c:if test="${not empty param.success}">
            <div class="alert alert-success alert-custom alert-dismissible fade show" role="alert">
                <i class="fas fa-check-circle"></i>
                <strong>Succès!</strong> ${param.success}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <c:choose>
            <c:when test="${not empty produit}">
                <!-- Product Info Card -->
                <div class="product-info-card">
                    <div class="row align-items-center">
                        <div class="col-auto">
                            <i class="fas fa-tag" style="font-size: 2.5rem; opacity: 0.9;"></i>
                        </div>
                        <div class="col">
                            <div class="product-info-title">PRODUIT #${produit.idProduit}</div>
                            <div class="product-info-value">${produit.nom}</div>
                        </div>
                        <div class="col-auto">
                            <span class="badge bg-white text-dark px-3 py-2 rounded-pill">
                                <i class="fas fa-calendar"></i> Modification en cours
                            </span>
                        </div>
                    </div>
                </div>

                <!-- Edit Form -->
                <div class="form-card">
                    <div class="form-header">
                        <h3><i class="fas fa-pencil-alt"></i> Formulaire de modification</h3>
                    </div>
                    
                    <div class="form-body">
                        <form method="POST" action="${pageContext.request.contextPath}/updateProduit" id="editForm">
                            <input type="hidden" name="id" value="${produit.idProduit}">

                            <div class="form-group">
                                <label class="form-label required-field">
                                    <i class="fas fa-heading"></i> Nom du produit
                                </label>
                                <input type="text" 
                                       id="nom" 
                                       name="nom" 
                                       required 
                                       value="${produit.nom}"
                                       class="form-control"
                                       placeholder="Ex: iPhone 15 Pro, Laptop Dell XPS, ...">
                                <small class="text-muted">Le nom doit être unique et descriptif</small>
                            </div>

                            <div class="form-group">
                                <label class="form-label required-field">
                                    <i class="fas fa-align-left"></i> Description
                                </label>
                                <textarea id="description" 
                                          name="description" 
                                          required 
                                          rows="5" 
                                          class="form-control"
                                          placeholder="Décrivez le produit en détail...">${produit.description}</textarea>
                                <small class="text-muted">Description complète du produit (matériaux, dimensions, fonctionnalités...)</small>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="form-label required-field">
                                            <i class="fas fa-euro-sign"></i> Prix (€)
                                        </label>
                                        <input type="number" 
                                               id="prix" 
                                               name="prix" 
                                               required 
                                               value="${produit.prix}" 
                                               step="0.01" 
                                               min="0"
                                               class="form-control"
                                               placeholder="0.00">
                                        <small class="text-muted">Prix en euros (TTC)</small>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="form-label required-field">
                                            <i class="fas fa-folder"></i> Catégorie
                                        </label>
                                        <select id="categorie" 
                                                name="categorie" 
                                                required 
                                                class="form-select">
                                            <option value="">-- Sélectionner une catégorie --</option>
                                            <option value="Électronique" ${produit.categorie == 'Électronique' ? 'selected' : ''}>
                                                📱 Électronique
                                            </option>
                                            <option value="Accessoires" ${produit.categorie == 'Accessoires' ? 'selected' : ''}>
                                                🎧 Accessoires
                                            </option>
                                            <option value="Logiciels" ${produit.categorie == 'Logiciels' ? 'selected' : ''}>
                                                💻 Logiciels
                                            </option>
                                            <option value="Livres" ${produit.categorie == 'Livres' ? 'selected' : ''}>
                                                📚 Livres
                                            </option>
                                            <option value="Vêtements" ${produit.categorie == 'Vêtements' ? 'selected' : ''}>
                                                👕 Vêtements
                                            </option>
                                            <option value="Maison" ${produit.categorie == 'Maison' ? 'selected' : ''}>
                                                🏠 Maison
                                            </option>
                                            <option value="Autres" ${produit.categorie == 'Autres' ? 'selected' : ''}>
                                                📦 Autres
                                            </option>
                                        </select>
                                        <small class="text-muted">Catégorie pour organiser les produits</small>
                                    </div>
                                </div>
                            </div>

                            <!-- Preview Section (Optional) -->
                            <div class="mt-4 p-3 bg-light rounded" style="background: #f8f9fa !important;">
                                <h6 class="mb-2"><i class="fas fa-eye"></i> Aperçu :</h6>
                                <div class="row">
                                    <div class="col-md-6">
                                        <strong>Nom:</strong> <span id="previewNom">${produit.nom}</span>
                                    </div>
                                    <div class="col-md-6">
                                        <strong>Prix:</strong> <span id="previewPrix">${produit.prix}</span> €
                                    </div>
                                </div>
                            </div>

                            <div class="form-actions">
                                <button type="submit" class="btn btn-update">
                                    <i class="fas fa-save"></i> Mettre à jour
                                </button>
                                <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-cancel">
                                    <i class="fas fa-times"></i> Annuler
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-danger alert-custom" role="alert">
                    <i class="fas fa-exclamation-circle"></i>
                    <strong>Produit non trouvé!</strong> Le produit que vous cherchez à modifier n'existe pas dans la base de données.
                </div>
                <div class="text-center">
                    <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary btn-lg">
                        <i class="fas fa-arrow-left"></i> Retour à la liste
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Footer -->
    <div class="footer">
        <p>&copy; Zaynab AITADDI -TP5- JEE MVC2</p>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Live preview for form inputs
        document.getElementById('nom')?.addEventListener('input', function() {
            document.getElementById('previewNom').textContent = this.value || '---';
        });
        
        document.getElementById('prix')?.addEventListener('input', function() {
            document.getElementById('previewPrix').textContent = this.value || '0';
        });
        
        // Form validation before submit
        document.getElementById('editForm')?.addEventListener('submit', function(e) {
            const nom = document.getElementById('nom').value.trim();
            const prix = document.getElementById('prix').value;
            
            if (nom.length < 2) {
                e.preventDefault();
                alert('Le nom du produit doit contenir au moins 2 caractères');
                return false;
            }
            
            if (parseFloat(prix) <= 0) {
                e.preventDefault();
                alert('Le prix doit être supérieur à 0');
                return false;
            }
            
            return true;
        });
    </script>
</body>
</html>