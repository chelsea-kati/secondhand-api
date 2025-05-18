import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './ToutesAnnoncesPage.css;'

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

  return (
    <div className="admin-annonces-container">
      <h2>Toutes les annonces</h2>
      {annonces.length === 0 ? (
        <p>Aucune annonce trouvée.</p>
      ) : (
        <ul>
          {annonces.map((annonce) => (
            <li key={annonce.id} style={{ marginBottom: '1rem', borderBottom: '1px solid #ccc' }}>
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
