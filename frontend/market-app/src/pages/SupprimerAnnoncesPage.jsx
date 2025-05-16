import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './SupprimerAnnoncesPage.css'; // 


const SupprimerAnnoncesPage = () => {
  // États pour gérer les données et l'interface
  const [annonces, setAnnonces] = useState([]); // Stocke la liste des annonces
  const [selectedIds, setSelectedIds] = useState([]); // Stocke les IDs des annonces sélectionnées
  const [message, setMessage] = useState(''); // Pour afficher les messages de confirmation
  const [loading, setLoading] = useState(true); // État pour gérer le chargement
  
  // Récupération du token d'authentification stocké dans localStorage
  const token = localStorage.getItem('token');

  // Effet qui s'exécute au chargement du composant pour récupérer les annonces
  useEffect(() => {
    // Appel API pour récupérer les annonces
    setLoading(true);
    axios.get('http://localhost:8080/api/annonces', {
      headers: { Authorization: `Bearer ${token}` } // Envoi du token pour l'authentification
    })
      .then(response => {
        // Vérification que response.data est bien un tableau
        // Si c'est un objet avec une propriété contenant les annonces, ajustez en conséquence
        if (Array.isArray(response.data)) {
          setAnnonces(response.data);
        } else if (response.data && typeof response.data === 'object') {
          // Si les données sont un objet, cherchons une propriété qui pourrait contenir le tableau
          // Par exemple, si l'API renvoie { annonces: [...] } ou { data: [...] }
          const possibleArrayProps = ['annonces', 'data', 'items', 'results'];
          for (const prop of possibleArrayProps) {
            if (Array.isArray(response.data[prop])) {
              setAnnonces(response.data[prop]);
              break;
            }
          }
          
          // Si on n'a pas trouvé de tableau, initialiser avec un tableau vide
          if (!Array.isArray(annonces) || annonces.length === 0) {
            console.warn('Format de données inattendu:', response.data);
            setAnnonces([]);
          }
        } else {
          console.error('Format de données non supporté:', response.data);
          setAnnonces([]);
        }
        setLoading(false);
      })
      .catch(err => {
        console.error('Erreur chargement annonces', err);
        setAnnonces([]);
        setLoading(false);
      });
  }, []); // Tableau de dépendances vide = exécution unique au montage du composant

  // Fonction pour sélectionner/désélectionner une annonce
  const handleSelect = (id) => {
    setSelectedIds(prev =>
      // Si l'ID est déjà sélectionné, on le retire, sinon on l'ajoute
      prev.includes(id) ? prev.filter(x => x !== id) : [...prev, id]
    );
  };

  // Fonction pour supprimer les annonces sélectionnées
  const handleDeleteSelected = async () => {
    try {
      // Utilisation de Promise.all pour attendre que toutes les suppressions soient terminées
      await Promise.all(
        selectedIds.map(id =>
          // Envoi d'une requête DELETE pour chaque annonce sélectionnée
          axios.delete(`http://localhost:8080/api/annonces/${id}`, {
            headers: { Authorization: `Bearer ${token}` }
          })
        )
      );

      // Mise à jour de l'état local pour refléter les suppressions
      setAnnonces(prev => prev.filter(a => !selectedIds.includes(a.id)));
      setSelectedIds([]); // Réinitialisation des sélections
      setMessage('✅ Annonce(s) supprimée(s) avec succès'); // Message de confirmation

      // Disparition du message après 3 secondes
      setTimeout(() => setMessage(''), 3000);
    } catch (err) {
      console.error('Erreur suppression annonce(s)', err);
    }
  };

  return (
    <div className="supprimer-annonces-container">
      <h2>🗑️ Supprimer des annonces</h2>
      
      {/* Affichage du message de confirmation s'il existe */}
      {message && (
        <div className="message-success">{message}</div>
      )}

      {/* Message si chargement en cours */}
      {loading && (
        <p className="no-annonce">Chargement des annonces...</p>
      )}

      {/* Message si aucune annonce n'est disponible */}
      {!loading && (!annonces || annonces.length === 0) && (
        <p className="no-annonce">Aucune annonce à afficher.</p>
      )}
      
      {/* Liste des annonces */}
      <div>
        {Array.isArray(annonces) && annonces.map(annonce => (
          <div key={annonce.id} className="annonce-item">
            <input
              type="checkbox"
              checked={selectedIds.includes(annonce.id)}
              onChange={() => handleSelect(annonce.id)}
            />
            <span>#{annonce.id} — {annonce.titre}</span>
          </div>
        ))}
      </div>
      
      {/* Bouton de suppression qui n'apparaît que si des annonces sont sélectionnées */}
      {selectedIds.length > 0 && (
        <button 
          onClick={handleDeleteSelected} 
          className="delete-button"
        >
          ❌ Supprimer la sélection ({selectedIds.length})
        </button>
      )}
    </div>
  );
};

export default SupprimerAnnoncesPage;

//export default SupprimerAnnoncesPage;


