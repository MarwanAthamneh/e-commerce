import React from 'react';
import './Style/AboutUs.css' 
const AboutUs = () => {
  return (
    <div className="about-us">
      <div className="about-header">
        <h1>About FashionHub</h1>
        <p>
          Welcome to FashionHub, your one-stop destination for the latest trends in clothing and fashion. We bring you a curated collection of apparel that combines style, comfort, and affordability.
        </p>
      </div>

      <div className="about-content">
        <div className="about-section">
          <img src="team.jpg" alt="Our Team" className="about-image" />
          <h2>Our Mission</h2>
          <p>
            At FashionHub, our mission is to make high-quality fashion accessible to everyone. We strive to inspire confidence through style.
          </p>
        </div>

        <div className="about-section">
          <img src="sustainability.jpg" alt="Sustainability" className="about-image" />
          <h2>Our Values</h2>
          <p>
            Sustainability, inclusivity, and innovation are at the heart of everything we do. We’re committed to making a positive impact on the planet and the fashion industry.
          </p>
        </div>

        <div className="about-section">
          <img src="store.jpg" alt="Our Store" className="about-image" />
          <h2>Why Choose Us</h2>
          <p>
            From timeless classics to the latest trends, FashionHub offers a wide variety of styles for every occasion. Our customer-first approach ensures a seamless shopping experience.
          </p>
        </div>
      </div>
    </div>
  );
};

export default AboutUs;

