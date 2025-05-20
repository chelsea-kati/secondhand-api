import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './AnnoncesEnAttentePage.css';

const AnnoncesEnAttentePage = () => {
  const [annonces, setAnnonces] = useState([]);
  const[message, setMessage]= useState('');

  useEffect(() => {
    fetchAnnoncesNonApprouvees();
  }, []);

  const fetchAnnoncesNonApprouvees = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8080/api/annonces", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      // ⚠️ Ne garde que les non approuvées
      const nonApprouvees = response.data.filter(a => !a.approuvee);
      setAnnonces(nonApprouvees);
    } catch (error) {
      console.error("Erreur lors de la récupération des annonces :", error);
      
    }
  };

  const approuverAnnonce = async (id) => {
    try {
      const token = localStorage.getItem("token");
      await axios.put(`http://localhost:8080/api/annonces/${id}/approuver`, {}, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setMessage="Annonce approuvée avec succès";
      fetchAnnoncesNonApprouvees(); // Rafraîchir
    } catch (error) {
      console.error("Erreur lors de l'approbation :", error);
    }
  };

  const supprimerAnnonce = async (id) => {
    try {
      const token = localStorage.getItem("token");
      await axios.delete(`http://localhost:8080/api/annonces/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setMessage=("Annonce supprimée avec succès");
      fetchAnnoncesNonApprouvees(); // Rafraîchir
    } catch (error) {
      console.error("Erreur lors de la suppression :", error);
    }
  };

  return (
    <div className="annonces-container">
      <h2>Annonces en attente d’approbation</h2>
      {annonces.length === 0 ? (
        <p>Aucune annonce en attente.</p>
      ) : (
        <ul>
          {annonces.map((annonce) => (
            <li key={annonce.id} className="annonce-item">
              <strong>{annonce.titre}</strong> - {annonce.description}
              <div className="annonce-buttons">
                {!annonce.approuvee && (
                  <button onClick={() => approuverAnnonce(annonce.id)}>✅ Approuver</button>
                )}
                <button onClick={() => supprimerAnnonce(annonce.id)}>🗑️ Supprimer</button>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default AnnoncesEnAttentePage;
