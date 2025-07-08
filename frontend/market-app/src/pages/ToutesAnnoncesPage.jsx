import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './ToutesAnnoncesPage.css';

const ToutesAnnoncesPage = () => {
  const [annonces, setAnnonces] = useState([]);
  const [message, setMessage] = useState("");

  useEffect(() => {
    fetchAnnonces();
  }, []);

  const fetchAnnonces = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8089/api/annonces", {
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
    if (!window.confirm("Voulez-vous vraiment supprimer cette annonce ?")) return;
    try {
      const token = localStorage.getItem("token");
      await axios.delete(`http://localhost:8089/api/annonces/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setMessage("✅ L'annonce a été supprimée avec succès !");
      fetchAnnonces();

      // Effacer le message après 3 secondes
      setTimeout(() => setMessage(""), 3000);
    } catch (error) {
      console.error("Erreur lors de la suppression", error);
      setMessage("❌ Erreur lors de la suppression de l'annonce.");
      setTimeout(() => setMessage(""), 3000);
    }
  };

  return (
    <div className="admin-annonces-container">
      <h2>Toutes les annonces</h2>

      {message && <p className="message">{message}</p>}

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
