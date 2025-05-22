//import React, { Component } from 'react';
//import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
//import CreateAnnoncePage from './pages/CreateAnnoncePage';
import AnnoncesEnAttentePage from './pages/AnnoncesEnAttentePage';
import AdminDashboardPage from'./pages/AdminDashboardPage ';
import SupprimerAnnoncesPage from'./pages/SupprimerAnnoncesPage';
import SupprimerCommentairesPage from'./pages/SupprimerCommentairesPage';
import ListeUtilisateursPage from'./pages/ListeUtilisateursPage';
import ToutesAnnoncesPage from'./pages/ToutesAnnoncesPage';
import CommentairesListPage from './pages/CommentairesListPage';


// const AppRoutes = () => {
//   return (
//     <Router>
//       <Routes>
//         <Route path="/" element={<HomePage />} />
//         <Route path="/login" element={<LoginPage/>} />
//       </Routes>
//     </Router>
//   );
// };
//export default AppRoutes;
const routes = [
    { path: '/', Component: HomePage },
    { path: '/login', Component: LoginPage },
    {path: '/register', Component: RegisterPage},
    { path: '/admin', Component: AdminDashboardPage },
    //{ path: '/AddAnnonce', Component: CreateAnnoncePage },
    { path: '/annonces-en-attente', Component: AnnoncesEnAttentePage },
    { path: '/supprimer-annonces', Component: SupprimerAnnoncesPage },
    { path: '/supprimer-commentaire', Component: SupprimerCommentairesPage },
    { path: '/UtilisateursList', Component: ListeUtilisateursPage },
    { path: '/ToutesAnnoncesPage', Component: ToutesAnnoncesPage },
    { path: '/CommentairesListPage', Component: CommentairesListPage },


  ];
  
  export default routes;

