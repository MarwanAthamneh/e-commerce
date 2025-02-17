import React, { useState } from 'react';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import { Link, useNavigate } from 'react-router-dom';
import './Style/Login.css';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async () => {
        try {
            const response = await axios.post('http://localhost:9090/api/auth/login', {
                email,
                password,
            });
    
            if (response.status === 200 && response.data.accessToken) {
                const token = response.data.accessToken;
                localStorage.setItem("jwtToken", token);
                
                const decoded = jwtDecode(token);
                console.log('Token contents:', decoded);
                
                alert('Login successful!');
                navigate('/');
            }
        } catch (err) {
            console.error('Login error:', err);
            setError('Invalid email or password!');
        }
    };

    return (
        <div className="login-container">
            <h2>Login</h2>
            <div className="form-group">
                <input
                    type="text"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="form-input"
                />
            </div>
            <div className="form-group">
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="form-input"
                />
            </div>
            {error && <div className="error-message">{error}</div>}
            <button onClick={handleLogin} className="login-button">
                Login
            </button>
            <Link to="/register" className="register-link">
                <button className="register-button">
                    Register
                </button>
            </Link>
        </div>
    );
};

export default Login;

