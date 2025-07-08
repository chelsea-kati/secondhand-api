import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './SupprimerAnnoncesPage.css';
import AdminNavbar from './AdminNavbar';  // ← Import du Navbar

const SupprimerAnnoncesPage = () => {
  // États pour gérer les données et l'interface
  const [annonces, setAnnonces] = useState([]);
  const [selectedIds, setSelectedIds] = useState([]);
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(true);

  const token = localStorage.getItem('token');

  useEffect(() => {
    setLoading(true);
    axios.get('http://localhost:8089/api/annonces', {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(response => {
        if (Array.isArray(response.data)) {
          setAnnonces(response.data);
        } else if (response.data && typeof response.data === 'object') {
          const possibleArrayProps = ['annonces', 'data', 'items', 'results'];
          for (const prop of possibleArrayProps) {
            if (Array.isArray(response.data[prop])) {
              setAnnonces(response.data[prop]);
              break;
            }
          }
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
  }, []);

  const handleSelect = (id) => {
    setSelectedIds(prev =>
      prev.includes(id) ? prev.filter(x => x !== id) : [...prev, id]
    );
  };

  const handleDeleteSelected = async () => {
    try {
      await Promise.all(
        selectedIds.map(id =>
          axios.delete(`http://localhost:8089/api/annonces/${id}`, {
            headers: { Authorization: `Bearer ${token}` }
          })
        )
      );
      setAnnonces(prev => prev.filter(a => !selectedIds.includes(a.id)));
      setSelectedIds([]);
      setMessage('✅ Annonce(s) supprimée(s) avec succès');
      setTimeout(() => setMessage(''), 3000);
    } catch (err) {
      console.error('Erreur suppression annonce(s)', err);
    }
  };

  return (
    <>
      <AdminNavbar />  {/* ← Ajout Navbar ici */}
      <div className="supprimer-annonces-container">
        <h2>🗑️ Supprimer des annonces</h2>
        {message && <div className="message-success">{message}</div>}
        {loading && <p className="no-annonce">Chargement des annonces...</p>}
        {!loading && (!annonces || annonces.length === 0) && (
          <p className="no-annonce">Aucune annonce à afficher.</p>
        )}
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
        {selectedIds.length > 0 && (
          <button
            onClick={handleDeleteSelected}
            className="delete-button"
          >
            ❌ Supprimer la sélection ({selectedIds.length})
          </button>
        )}
      </div>
    </>
  );
};

export default SupprimerAnnoncesPage;
