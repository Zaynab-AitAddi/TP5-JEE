# TP5 – Application de Gestion de Produits (MVC2 – Architecture Avancée)

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![JSP/Servlet](https://img.shields.io/badge/JSP%2FServlet-007396?style=for-the-badge)
![Bootstrap](https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)

> **Module :** Java EE – Développement Web Entreprise  
> **Étudiante :** Zaynab Ait Addi | **Encadrant :** Prof. Mohamed CHERRADI  
> **ENSAH – TDIA2 S4 | Année 2025-2026**

---

## 📋 Description

Évolution du TP4 vers une **architecture MVC2 renforcée** avec un filtre d'authentification amélioré gérant à la fois l'authentification et le **contrôle d'accès basé sur les rôles (RBAC)**. Les vues JSP sont déplacées dans `WEB-INF/views/` pour les protéger d'un accès direct, et l'encodage UTF-8 est centralisé via un filtre dédié.

---

## 🎯 Objectifs

- Renforcer la séparation MVC en protégeant les vues JSP dans `WEB-INF/views/`
- Centraliser l'authentification **et** le contrôle de rôle dans un seul filtre
- Séparer les filtres : `AuthenticationFilter` (auth + RBAC) et `CharacterEncodingFilter` (encodage)
- Afficher les permissions dans la vue selon le rôle de l'utilisateur connecté
- Améliorer la robustesse avec validation (`ValidationUtil`) et gestion d'erreurs

---

## 🏗️ Architecture MVC2

```
TP5/
├── pom.xml
└── src/main/java/
    ├── dao/
    │   ├── interfaces/
    │   │   ├── IProduitDAO.java
    │   │   └── IUserDAO.java
    │   ├── impl/
    │   │   ├── ProduitDAOImpl.java
    │   │   └── UserDAOImpl.java
    │   └── model/
    │       ├── Produit.java
    │       ├── User.java
    │       └── Role.java              # ADMIN / MANAGER / USER
    ├── metier/
    │   ├── ProduitMetier.java         # Singleton
    │   └── UserMetier.java            # Singleton
    ├── util/
    │   ├── PasswordUtil.java          # SHA-256
    │   └── ValidationUtil.java        # email, prix, isEmpty
    └── web/
        ├── filter/
        │   ├── AuthenticationFilter.java      # Auth + RBAC combinés
        │   └── CharacterEncodingFilter.java   # UTF-8 global
        └── servlet/
            ├── LoginServlet.java
            ├── SignupServlet.java
            ├── LogoutServlet.java
            ├── DashboardServlet.java
            ├── AddProduitServlet.java
            ├── EditProduitServlet.java
            ├── UpdateProduitServlet.java
            └── DeleteProduitServlet.java
```

**Vues protégées dans WEB-INF :**
```
src/main/webapp/
├── WEB-INF/
│   ├── web.xml
│   └── views/
│       ├── login.jsp
│       ├── signup.jsp
│       ├── dashboard.jsp
│       ├── addProduit.jsp
│       ├── editProduit.jsp
│       └── error/
│           ├── 403.jsp
│           ├── 404.jsp
│           └── 500.jsp
└── index.jsp
```

---

## 🔐 AuthenticationFilter – Logique Avancée

Le filtre `AuthenticationFilter` combine deux responsabilités :

```
Requête entrante
    │
    ├─ URL publique ? (/login, /signup, /css/*, /js/*) → laisser passer
    │
    ├─ Utilisateur non connecté ? → redirect /login
    │
    └─ Ressource protégée avec rôle insuffisant ? → forward /WEB-INF/views/error/403.jsp
           │
           └─ Sinon → chain.doFilter() → Servlet cible
```

**URLs publiques** (sans authentification) :
- `/login`, `/signup`, ressources statiques (`/css/**`, `/js/**`, `/images/**`)

**Ressources ADMIN/MANAGER uniquement** :
- `/addProduit`, `/editProduit`, `/updateProduit`, `/deleteProduit`

---

## 🔄 Flux d'Exécution – DashboardServlet

```java
HttpSession session = req.getSession();
User currentUser = (User) session.getAttribute("currentUser");

// Recherche ou liste complète
List<Produit> produits = (search != null)
    ? produitMetier.searchProduits(search)
    : produitMetier.getAllProduits();

// Permissions envoyées à la vue
req.setAttribute("canEdit",   currentUser.getRole() == Role.ADMIN || Role.MANAGER);
req.setAttribute("canDelete", currentUser.getRole() == Role.ADMIN || Role.MANAGER);
req.setAttribute("canAdd",    currentUser.getRole() == Role.ADMIN || Role.MANAGER);

req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
```

---

## 🌐 URLs de l'Application

| URL | Rôle requis | Description |
|-----|-------------|-------------|
| `/login` | Public | Connexion |
| `/signup` | Public | Inscription |
| `/logout` | Authentifié | Déconnexion |
| `/dashboard` | Tout rôle | Liste produits + recherche |
| `/addProduit` | ADMIN, MANAGER | Formulaire ajout |
| `/editProduit?id=X` | ADMIN, MANAGER | Formulaire modification |
| `/updateProduit` | ADMIN, MANAGER | Traitement modification (POST) |
| `/deleteProduit?id=X` | ADMIN, MANAGER | Suppression |

---

## 🚀 Installation et Exécution

### Prérequis

| Outil | Version |
|-------|---------|
| Java JDK | 11 |
| Apache Maven | 3.8.x |
| Apache Tomcat | 9.x |

### Déploiement

```powershell
# Configuration environnement
$env:JAVA_HOME     = "C:\Program Files\Java\jdk-11"
$env:Path         += ";C:\Program Files\Java\jdk-11\bin"
$env:Path         += ";C:\apache-maven-3.9.x\bin"
$env:CATALINA_HOME = "C:\Tomcat"

# Build et déploiement
cd C:\...\TP5
mvn clean package
Copy-Item "target\TP4-MVC2.war" "C:\Tomcat\webapps\" -Force
C:\Tomcat\bin\startup.bat

# Accès
Start-Process "http://localhost:8080/TP4-MVC2/login"
```

---

## 🔑 Différences avec TP4

| Aspect | TP4 (MVC1) | TP5 (MVC2) |
|--------|-----------|-----------|
| Vues JSP | Accessibles directement | Protégées dans `WEB-INF/views/` |
| Filtre auth | Auth seulement | Auth + RBAC dans même filtre |
| Encodage UTF-8 | Dans chaque Servlet | Centralisé dans `CharacterEncodingFilter` |
| Gestion d'erreurs | Basique | Pages 403/404/500 dédiées |
| Recherche produits | Non | Oui (paramètre `?search=`) |

---

## ⚠️ Notes Importantes

- Données stockées en **mémoire** (volatiles)
- Mots de passe hachés **SHA-256**
- Les JSP dans `WEB-INF/` ne sont **pas accessibles directement** par URL (sécurité)
- Pour la production : remplacer le stockage mémoire par une base de données

---

*TP5 – Java EE | ENSAH | TDIA2 S4 | © 2026 Zaynab AIT ADDI*
