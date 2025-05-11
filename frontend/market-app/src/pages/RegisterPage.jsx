import React, { useState } from 'react';

const RegisterPage = () => {
  const [formData, setFormData] = useState({
    nom: '',
    email: '',
    password: '',
    telephone: '',
    adresse: ''
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const response = await fetch('http://localhost:8080/api/auth/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(formData)
    });

    if (response.ok) {
      alert('Inscription réussie !');
    } else {
      alert('Erreur lors de l’inscription.');
    }
  };

  return (
    <div>
      <h2>Créer un compte</h2>
      <form onSubmit={handleSubmit}>
        <input name="nom" placeholder="Nom" onChange={handleChange} />
        <input name="email" placeholder="Email" onChange={handleChange} />
        <input name="password" type="password" placeholder="Mot de passe" onChange={handleChange} />
        <input name="telephone" placeholder="Téléphone" onChange={handleChange} />
        <input name="adresse" placeholder="Adresse" onChange={handleChange} />
        <button type="submit">S’inscrire</button>
      </form>
    </div>
  );
};

export default RegisterPage;
