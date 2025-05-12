import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './LoginPage.css';

function LoginPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');
  const navigate = useNavigate(); // 👈 pour la redirection

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/api/auth/login', {
        email,
        password,
      });

      const token = response.data.token;
      localStorage.setItem('token', token);
      setMessage('Connexion réussie ✅');
      
      // 👇 Rediriger vers le tableau de bord admin
      navigate('/admin');
    } catch (error) {
      console.error(error);
      setMessage('Échec de la connexion ❌ Vérifiez vos identifiants.');
    }
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleLogin}>
        <h2>Connexion</h2>
        <div className="input-group">
          <label>Email :</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div className="input-group">
          <label>Mot de passe :</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Se connecter</button>
        {message && <p className="message">{message}</p>}
      </form>
    </div>
  );
}

export default LoginPage;
