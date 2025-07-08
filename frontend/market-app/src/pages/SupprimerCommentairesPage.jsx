import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './SupprimerCommentairesPage.css';
import AdminNavbar from './AdminNavbar'; // ✅ Import du Navbar

const SupprimerCommentairesPage = () => {
  const [commentaires, setCommentaires] = useState([]);
  const [selectedIds, setSelectedIds] = useState([]);

  const token = localStorage.getItem('token');

  useEffect(() => {
    axios.get('http://localhost:8089/api/commentaires', {
      headers: { Authorization: `Bearer ${token}` }
    })
    .then(res => setCommentaires(res.data))
    .catch(err => console.error('Erreur chargement commentaires', err));
  }, []);

  const handleSelect = (id) => {
    setSelectedIds(prev =>
      prev.includes(id) ? prev.filter(x => x !== id) : [...prev, id]
    );
  };

  const handleDeleteSelected = () => {
    selectedIds.forEach(id => {
      axios.delete(`http://localhost:8089/api/commentaires/${id}`, {
        headers: { Authorization: `Bearer ${token}` }
      }).catch(err => console.error('Erreur suppression commentaire', err));
    });

    setCommentaires(prev => prev.filter(c => !selectedIds.includes(c.id)));
    setSelectedIds([]);
  };

  return (
    <>
      <AdminNavbar /> {/* ✅ Ajout du Navbar ici */}
      <div className="container">
        <h2>🗑️ Supprimer des commentaires</h2>
        {commentaires.length === 0 && <p>Aucun commentaire à afficher.</p>}
        {commentaires.map(commentaire => (
          <div key={commentaire.id} className="commentaire-item">
            <input
              type="checkbox"
              checked={selectedIds.includes(commentaire.id)}
              onChange={() => handleSelect(commentaire.id)}
            />
            <span> #{commentaire.id} — {commentaire.contenu?.substring(0, 50)}...</span>
          </div>
        ))}
        {selectedIds.length > 0 && (
          <button onClick={handleDeleteSelected} className="delete-button">
            ❌ Supprimer la sélection ({selectedIds.length})
          </button>
        )}
      </div>
    </>
  );
};

export default SupprimerCommentairesPage;
