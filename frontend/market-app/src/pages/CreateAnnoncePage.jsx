import React, { useState } from 'react';

const CreateAnnoncePage = () => {
  const [formData, setFormData] = useState({
    titre: '',
    description: '',
    prix: '',
    categorie: '',
    localisation: ''
  });

  const handleChange = (e) => {
    setFormData(prev => ({
      ...prev,
      [e.target.name]: e.target.value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token'); // 🔐 Token stocké après login
    try {
      const response = await fetch('http://localhost:8080/api/annonces', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`
        },
        body: JSON.stringify({
          ...formData,
          utilisateur: { id: 1 } // Remplace par l’ID réel si besoin
        })
      });

      if (!response.ok) throw new Error('Échec de la création de l\'annonce');
      alert('Annonce créée avec succès !');
      setFormData({
        titre: '',
        description: '',
        prix: '',
        categorie: '',
        localisation: ''
      });
    } catch (err) {
      alert('Erreur : ' + err.message);
    }
  };

  return (
    <div>
      <h2>Créer une annonce</h2>
      <form onSubmit={handleSubmit}>
        <input type="text" name="titre" placeholder="Titre" value={formData.titre} onChange={handleChange} required />
        <br />
        <textarea name="description" placeholder="Description" value={formData.description} onChange={handleChange} required />
        <br />
        <input type="number" name="prix" placeholder="Prix" value={formData.prix} onChange={handleChange} required />
        <br />
        <input type="text" name="categorie" placeholder="Catégorie" value={formData.categorie} onChange={handleChange} required />
        <br />
        <input type="text" name="localisation" placeholder="Localisation" value={formData.localisation} onChange={handleChange} required />
        <br />
        <button type="submit">Publier</button>
      </form>
    </div>
  );
};

export default CreateAnnoncePage;
