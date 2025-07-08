import React, { useEffect, useState } from 'react';
import axios from 'axios';
import AdminNavbar from './AdminNavbar'; // Assure-toi que le chemin est correct
import './ListeUtilisateursPage.css';

const ListeUtilisateursPage = () => {
  const [utilisateurs, setUtilisateurs] = useState([]);
  const token = localStorage.getItem('token');

  useEffect(() => {
    fetchUtilisateurs();
  }, []);

  const fetchUtilisateurs = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8089/api/utilisateurs/role/utilisateur", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setUtilisateurs(response.data);
    } catch (error) {
      console.error("Erreur lors du chargement des utilisateurs", error);
    }
  };

  const supprimerUtilisateur = async (id) => {
    if (!window.confirm("Supprimer cet utilisateur ?")) return;

    try {
      const token = localStorage.getItem("token");
      await axios.delete(`http://localhost:8080/api/utilisateurs/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      fetchUtilisateurs();
    } catch (error) {
      console.error("Erreur lors de la suppression", error);
    }
  };

  const activerUtilisateur = (id) => {
    axios.patch(`http://localhost:8080/api/utilisateurs/${id}/activer`, {}, {
      headers: { Authorization: `Bearer ${token}` },
    })
    .then(() => {
      setUtilisateurs(prev =>
        prev.map(u => u.id === id ? { ...u, actif: true } : u)
      );
    })
    .catch(err => console.error('Erreur activation', err));
  };

  const desactiverUtilisateur = (id) => {
    axios.patch(`http://localhost:8080/api/utilisateurs/${id}/desactiver`, {}, {
      headers: { Authorization: `Bearer ${token}` },
    })
    .then(() => {
      setUtilisateurs(prev =>
        prev.map(u => u.id === id ? { ...u, actif: false } : u)
      );
    })
    .catch(err => console.error('Erreur désactivation', err));
  };

  return (
    <div>
      <AdminNavbar />
      <div className="admin-users-container">
        <h2>Liste des utilisateurs</h2>
        {utilisateurs.length === 0 ? (
          <p>Aucun utilisateur trouvé.</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Nom</th>
                <th>Email</th>
                <th>Téléphone</th>
                <th>Adresse</th>
                <th>Rôle</th>
                <th>Statut</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {utilisateurs.map((u) => (
                <tr key={u.id}>
                  <td>{u.nom}</td>
                  <td>{u.email}</td>
                  <td>{u.telephone}</td>
                  <td>{u.adresse}</td>
                  <td>{u.role}</td>
                  <td>{u.actif ? "✅ Actif" : "🚫 Inactif"}</td>
                  <td>
                    <button onClick={() => supprimerUtilisateur(u.id)}>🗑️ Supprimer</button>
                    {u.actif ? (
                      <button onClick={() => desactiverUtilisateur(u.id)} style={{ marginLeft: '5px', backgroundColor: '#aaa344', color: '#111' }}>
                        🚫 Désactiver
                      </button>
                    ) : (
                      <button onClick={() => activerUtilisateur(u.id)} style={{ marginLeft: '5px', backgroundColor: '#cef73b', color: '#111' }}>
                        ✅ Activer
                      </button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
};

export default ListeUtilisateursPage;
