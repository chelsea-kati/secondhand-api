import React, { useEffect, useState } from 'react';
import axios from 'axios';

const SupprimerAnnoncesPage = () => {
  const [annonces, setAnnonces] = useState([]);
  const [selectedIds, setSelectedIds] = useState([]);

  const token = localStorage.getItem('token');

  useEffect(() => {
    axios.get('http://localhost:8080/api/annonces', {
      headers: { Authorization: `Bearer ${token}` }
    })
    .then(res => setAnnonces(res.data))
    .catch(err => console.error('Erreur chargement annonces', err));
  }, []);

  const handleSelect = (id) => {
    setSelectedIds(prev =>
      prev.includes(id) ? prev.filter(x => x !== id) : [...prev, id]
    );
  };

  const handleDeleteSelected = () => {
    selectedIds.forEach(id => {
      axios.delete(`http://localhost:8080/api/annonces/${id}`, {
        headers: { Authorization: `Bearer ${token}` }
      }).catch(err => console.error(`Erreur suppression annonce ${id}`, err));
    });

    setAnnonces(prev => prev.filter(a => !selectedIds.includes(a.id)));
    setSelectedIds([]);
  };

 return (
  <div className="supprimer-annonces-container">
    <h2>🗑️ Supprimer des annonces</h2>
    {annonces.length === 0 && <p className="no-annonce">Aucune annonce à afficher.</p>}
    {annonces.map(annonce => (
      <div key={annonce.id} className="annonce-item">
        <input
          type="checkbox"
          checked={selectedIds.includes(annonce.id)}
          onChange={() => handleSelect(annonce.id)}
        />
        <span>#{annonce.id} — {annonce.titre}</span>
      </div>
    ))}
    {selectedIds.length > 0 && (
      <button onClick={handleDeleteSelected} className="delete-button">
        ❌ Supprimer la sélection ({selectedIds.length})
      </button>
    )}
  </div>
);

};

export default SupprimerAnnoncesPage;
