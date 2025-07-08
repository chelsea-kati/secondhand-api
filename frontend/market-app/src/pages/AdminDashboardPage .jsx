import React from 'react';
import { Link } from 'react-router-dom';
import './AdminDashboardPage.css';
//import AdminNavbar from './AdminNavbar';

const AdminDashboardPage = () => {
  return (
    <>
      {/* <AdminNavbar /> */}
      <div className="dashboard-container">
        <h2>👨‍💼 Tableau de bord Admin</h2>
        <p>Bienvenue dans l'espace d'administration. Choisissez une action :</p>
        <ul className="dashboard-actions">
          <li><Link to="/annonces-en-attente"> Approuver des annonces</Link></li>
          <li><Link to="/supprimer-commentaire"> Supprimer des commentaires</Link></li>
          <li><Link to="/supprimer-annonces"> Supprimer des annonces</Link></li>
          <li><Link to="/UtilisateursList">Affichage de touts les utilisateurs </Link></li>
          <li><Link to="/ToutesAnnoncesPage">Affichage de toutes les annonces(approuvées ou non)</Link></li>
          <li><Link to="/CommentairesListPage">Affichage de touts les commentaires</Link></li>
        </ul>
      </div>
    </>
  );
};

export default AdminDashboardPage;
