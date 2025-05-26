import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './CommentairesListPage.css';

const CommentairesListPage = () => {
  const [commentaires, setCommentaires] = useState([]);

  useEffect(() => {
    fetchCommentaires();
  }, []);

  const fetchCommentaires = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/commentaires");
      setCommentaires(response.data);
    } catch (error) {
      console.error("Erreur lors du chargement des commentaires :", error);
    }
  };

  return (
    <div className="commentaires-container">
      <h2>Commentaires des utilisateurs</h2>
      {commentaires.length === 0 ? (
        <p>Aucun commentaire disponible.</p>
      ) : (
        <ul>
          {commentaires.map((commentaire) => (
            <li key={commentaire.id}>
              <p><strong>{commentaire.utilisateur?.nom || "Anonyme"}</strong> :</p>
              <p>{commentaire.texte}</p>
              <p>Sur l’annonce #{commentaire.annonce?.id}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default CommentairesListPage;
