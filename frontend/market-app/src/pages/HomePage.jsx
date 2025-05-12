import React from 'react';
import { useNavigate } from 'react-router-dom';
import backgroundImage from '../assets/background.jpg'; // Image bien importée

const HomePage = () => {
  const navigate = useNavigate();

  const handleLogin = () => {
    navigate('/login');
  };

  const handleRegister = () => {
    navigate('/register');
  };

  return (
    <div
      style={{
        backgroundImage: `url(${backgroundImage})`, // ✅ Utilise bien la variable importée
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        height: '100vh',
        width: '100%',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        color: '#fff',
        textShadow: '1px 1px 2px #000',
      }}
    >
      <h1 style={{ fontSize: '3rem', marginBottom: '2rem' }}>
        Bienvenue sur SecondHand Market
      </h1>
      <div>
        <button
          onClick={handleLogin}
          style={{
            padding: '1rem 2rem',
            margin: '0 1rem',
            fontSize: '1rem',
            backgroundColor: '#111',
            border: 'none',
            borderRadius: '5px',
            color: '#fff',
            cursor: 'pointer',
          }}
        >
          Se connecter
        </button>
        <button
          onClick={handleRegister}
          style={{
            padding: '1rem 2rem',
            margin: '0 1rem',
            fontSize: '1rem',
            backgroundColor: '#aaa344',
            border: 'none',
            borderRadius: '5px',
            color: '#fff',
            cursor: 'pointer',
          }}
        >
          S'enregistrer
        </button>
      </div>
    </div>
  );
};

export default HomePage;
