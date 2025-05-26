// src/components/AdminNavbar.js
import React from 'react';
import { Link } from 'react-router-dom';
import './AdminNavbar.css';

const AdminNavbar = () => {
  return (
    <nav className="admin-navbar">
      <ul>
        <li><Link to="/admin">🏠 Accueil Admin</Link></li>
        <li><Link to="/annonces-en-attente">✅ Annonces à approuver</Link></li>
        <li><Link to="/supprimer-commentaire">🗑️ Supprimer commentaires</Link></li>
        <li><Link to="/supprimer-annonces">🗑️ Supprimer annonces</Link></li>
        <li><Link to="/UtilisateursList">👥 Utilisateurs</Link></li>
        <li><Link to="/ToutesAnnoncesPage">📢 Toutes les annonces</Link></li>
        <li><Link to="/CommentairesListPage">💬 Tous les commentaires</Link></li>
      </ul>
    </nav>
  );
};

export default AdminNavbar;
