import React, { useState } from 'react';
import axios from 'axios';
import './LoginPage.css';

function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/api/auth/login', {
        email,
        password,
      });

      const token = response.data.token;
      localStorage.setItem('token', token);
      setMessage('Connexion réussie');
      // 🔁 Rediriger ou faire autre chose si nécessaire
    } catch (error) {
      console.error(error);
      setMessage('Échec de la connexion. Vérifiez vos identifiants.');
    }
  };

  return (
    <div style={{ padding: '2rem' }}>
      <h2>Connexion</h2>
      <form onSubmit={handleLogin}>
        <div>
          <label>Email : </label>
          <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
        </div>
        <div>
          <label>Mot de passe : </label>
          <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
        </div>
        <button type="submit">Se connecter</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
}

export default LoginPage;
