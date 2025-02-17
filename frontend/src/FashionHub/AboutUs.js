import React from 'react';
import './Style/AboutUs.css';
import { Link } from "react-router-dom";

const AboutUs = () => {
  const heroStyle = {
    backgroundImage: `url("Photos/BackGround.jpg")`,
    backgroundSize: 'cover',
    backgroundPosition: 'center'
  };

  return (
    <div className="about-us-page">
      <section className="hero-section" style={heroStyle}>
        <div className="hero-overlay"></div>
        <div className="hero-content">
          <span className="about-tag">ABOUT</span>
          <h1>
            Bridging the physical and
            <br />
            digital fashion worlds
          </h1>
          <p>
            We enable consumers to visualize and interact with fashion, creating digital experiences
            of the future.
          </p>
          <button className="lets-talk-btn"><Link to="/contact">LET'S TALK →</Link></button>
          

        </div>
      </section>

      <section className="sustainability-section">
        <div className="sustainability-container">
          <div className="sustainability-image">
            <img 
              src="Photos/aboutus.jpg" 
              alt="Sustainability in Fashion" 
            />
          </div>
          <div className="sustainability-content">
            <h2>Sustainability</h2>
            <p>
              Global product returns are equivalent to emissions from ~850,000 cars!
              The majority of apparel returns are due to wrong fit and size. By not
              actively addressing size and fit, together with the offer of free returns,
              retailers have inadvertently created an unsustainable shopping behavior.
              Our virtual fitting solution has been proven to reduce returns by up to 50%,
              helping to reduce the industry's carbon footprint.
            </p>
            <p>
              Furthermore, digital fashion can help to reduce the overproduction of
              clothing, saving 3300 liters of water, emitting 97% less carbon emissions.
              By enabling consumers to wear these items, this impact can be realized.
            </p>
          </div>
        </div>
      </section>
    </div>
  );
};

export default AboutUs;