import React, { useState } from 'react';
import './Style/Contact.css';
import { useNavigate } from 'react-router-dom'; 


const PhoneIcon = () => (
    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
        <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"></path>
    </svg>
);

const EmailIcon = () => (
    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
        <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path>
        <polyline points="22,6 12,13 2,6"></polyline>
    </svg>
);

const LocationIcon = () => (
    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
        <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path>
        <circle cx="12" cy="10" r="3"></circle>
    </svg>
);

const Contact = () => {
    const navigate = useNavigate(); // This line should now work

    const [formData, setFormData] = useState({
        name: '',
        email: '',
        subject: '',
        message: ''
    });

    const [submitStatus, setSubmitStatus] = useState({
        isLoading: false,
        success: false,
        error: false,
        message: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        setSubmitStatus({ isLoading: true, success: false, error: false, message: '' });

        try {
            const response = await fetch('http://localhost:9090/api/contact/send', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                navigate('/thank-you');
            } else {
                const errorText = await response.text();
                setSubmitStatus({
                    isLoading: false,
                    success: false,
                    error: true,
                    message: errorText || 'Failed to send message'
                });
            }
        } catch (error) {
            setSubmitStatus({
                isLoading: false,
                success: false,
                error: true,
                message: 'Network error. Please try again.'
            });
        }
    };

    return (
        <div className="contact-container">
            <div className="contact-wrapper">
                <div className="contact-info">
                    <h2>Contact Us</h2>
                    <p>We'd love to hear from you. Send us a message and we'll respond as soon as possible.</p>
                    
                    <div className="contact-details">
                        <div className="contact-detail">
                            <PhoneIcon />
                            <span>641 233 9993</span>
                        </div>
                        <div className="contact-detail">
                            <EmailIcon />
                            <span>marwan.geed@gmail.com</span>
                        </div>
                        <div className="contact-detail">
                            <LocationIcon />
                            <span>Fairfield Iowa</span>
                        </div>
                    </div>
                </div>
                
                {submitStatus.success && (
                    <div className="success-message">
                        {submitStatus.message}
                    </div>
                )}
                
                {submitStatus.error && (
                    <div className="error-message">
                        {submitStatus.message}
                    </div>
                )}
                
                <form className="contact-form" onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="name">Full Name</label>
                        <input 
                            type="text" 
                            id="name"
                            name="name"
                            value={formData.name}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    
                    <div className="form-group">
                        <label htmlFor="email">Email Address</label>
                        <input 
                            type="email" 
                            id="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    
                    <div className="form-group">
                        <label htmlFor="subject">Subject</label>
                        <input 
                            type="text" 
                            id="subject"
                            name="subject"
                            value={formData.subject}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    
                    <div className="form-group">
                        <label htmlFor="message">Your Message</label>
                        <textarea 
                            id="message"
                            name="message"
                            value={formData.message}
                            onChange={handleChange}
                            required
                        ></textarea>
                    </div>
                    
                    <button 
                        type="submit" 
                        className="submit-btn" 
                        disabled={submitStatus.isLoading}
                    >
                        {submitStatus.isLoading ? 'Sending...' : 'Send Message'}
                    </button>
                </form>
            </div>
        </div>
    );
};

export default Contact;