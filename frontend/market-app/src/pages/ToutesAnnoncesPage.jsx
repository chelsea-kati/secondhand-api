import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './ToutesAnnoncesPage.css';

const ToutesAnnoncesPage = () => {
  const [annonces, setAnnonces] = useState([]);
  
  useEffect(() => {
    fetchAnnonces();
  }, []);
  
  const fetchAnnonces = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8080/api/annonces", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setAnnonces(response.data);
    } catch (error) {
      console.error("Erreur lors du chargement des annonces", error);
    }
  };
  
  const supprimerAnnonce = async (id) => {
    if (!window.confirm("Supprimer cette annonce ?")) return;
    try {
      const token = localStorage.getItem("token");
      await axios.delete(`http://localhost:8080/api/annonces/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      fetchAnnonces();
    } catch (error) {
      console.error("Erreur lors de la suppression", error);
    }
  };
//   const handleDelete = async (id) => {
//   // Convertir l'id en nombre si ce n'est pas déjà le cas
//   const numericId = Number(id);
//   console.log("Type de l'ID après conversion:", typeof numericId);
  
//   try {
//     const token = localStorage.getItem("token");
//     await axios.delete(`http://localhost:8080/api/annonces/${numericId}`, {
//       headers: {
//         Authorization: `Bearer ${token}`
//       }
//     });
//     fetchAnnonces();
//   } catch (error) {
//     console.error("Erreur lors de la suppression", error);
//   }
// };
  
  return (
    /* Ajout de la classe admin-annonces-container à l'élément div parent */
    <div className="admin-annonces-container">
      <h2>Toutes les annonces</h2>
      {annonces.length === 0 ? (
        <p>Aucune annonce trouvée.</p>
      ) : (
        <ul>
          {annonces.map((annonce) => (
            <li key={annonce.id}>
              <strong>{annonce.titre}</strong> - {annonce.description} ({annonce.approuvee ? "✅ Approuvée" : "⏳ En attente"})
              <div>
                <button onClick={() => supprimerAnnonce(annonce.id)}>🗑️ Supprimer</button>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default ToutesAnnoncesPage;