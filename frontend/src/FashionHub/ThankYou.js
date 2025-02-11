import React from 'react';
import { Link } from 'react-router-dom';
import './Style/ThankYou.css'; 

const ThankYou = () => {
    return (
        <div className="thank-you-container">
            <div className="thank-you-card">
                <div className="checkmark-circle">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52">
                        <circle className="checkmark-circle-background" cx="26" cy="26" r="25" fill="none"/>
                        <path className="checkmark-icon" fill="none" d="M14.1 27.2l7.1 7.2 16.7-16.8"/>
                    </svg>
                </div>
                <h1>Thank You!</h1>
                <p>Your message has been successfully sent. 
                   We appreciate you reaching out and will get back to you soon.</p>
                <div className="thank-you-actions">
                    <Link to="/" className="btn btn-primary">Return to Home</Link>
                    <Link to="/contact" className="btn btn-secondary">Send Another Message</Link>
                </div>
            </div>
        </div>
    );
};

export default ThankYou;