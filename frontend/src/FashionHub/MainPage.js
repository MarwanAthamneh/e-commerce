import React from 'react'
import './Style/MainPage.css';
import { Link } from "react-router-dom";
import axios from 'axios';
export const MainPage = () => {
 

    const addToCart = async (itemName, itemId) => {
        try {
            const token = localStorage.getItem("jwtToken");
            console.log("Attempting to add item:", { itemName, itemId });
            
            if (!token) {
                alert('Please login to add items to cart');
                return;
            }
    
            // Use URLSearchParams to properly send the productId as a query parameter
            const params = new URLSearchParams();
            params.append('productId', itemId);
    
            const response = await axios({
                method: 'post',
                url: 'http://localhost:9090/api/cart/add',
                params: params,  // This will append productId as a query parameter
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
    
            console.log('Add to cart response:', response.data);
            
            if (response.data && response.data.message) {
                alert(response.data.message);
            } else {
                alert(`${itemName} has been added to your cart!`);
            }
        } catch (error) {
            console.error('Error details:', {
                message: error.message,
                response: error.response?.data,
                status: error.response?.status
            });
    
            if (error.response?.status === 401) {
                alert('Please login again to continue');
                // Optionally redirect to login page
                // window.location.href = '/login';
            } else {
                alert(error.response?.data?.message || 'Failed to add item to cart. Please try again.');
            }
        }
    };

    return (
        <div>
            <header>
                <div className="logo">FashionHub</div>
                <nav>
                <ul>
                        <li><Link to="/main">Home</Link></li>
                        <li><Link to="#collections" onClick={(e) => {
                            e.preventDefault();
                            document.getElementById('collections').scrollIntoView({ behavior: 'smooth' });
                        }}>Collections</Link></li>
                        <li><Link to="/about">About Us</Link></li>
                        <li><Link to="/contact">Contact</Link></li>
                        <li><Link to="/cart">Cart</Link></li>
                    </ul>
                </nav>
            </header>

            <section id="hero">
                <div className="hero-content">
                    <h1>Step into Style</h1>
                    <p>Discover the latest trends in fashion and redefine your wardrobe with our exclusive collections.</p>
                    <a href="#collections" className="shop-now">Shop Now</a>
                </div>
            </section>

            <section id="featured-products">
                <h2>Featured Products</h2>
                <div className="product-grid">
                    <div className="product">
                        <img src="Photos/hoodie.jpg" alt="Product 1" />
                        <h3>Stylish T-Shirt</h3>
                        <p>$25.00</p>
                        <button onClick={() => addToCart('Stylish T-Shirt',1)}>Add to Cart</button>
                    </div>
                    <div className="product">
                        <img src="Photos/dress.jpg" alt="Product 2" />
                        <h3>Elegant Dress</h3>
                        <p>$50.00</p>
                        <button onClick={() => addToCart('Elegant Dress',2)}>Add to Cart</button>
                    </div>
                    <div className="product">
                        <img src="Photos/jeans.jpg"alt="Product 3" />
                        <h3>Classic Jeans</h3>
                        <p>$40.00</p>
                        <button onClick={() => addToCart('Classic Jeans',3)}>Add to Cart</button>
                    </div>
                </div>
            </section>

            <section id="collections">
                <h2>Our Collections</h2>
                <div className="collections-grid">
                    <div className="collection">
                        <img src="Photos/men.jpg" alt="Men's Collection" />
                        <h3>Men</h3>
                    </div>
                    <div className="collection">
                        <img src="Photos/women.jpg" alt="Women's Collection" />
                        <h3>Women</h3>
                    </div>
                    <div className="collection">
                        <img src="Photos/kids.jpg" alt="Kids' Collection" />
                        <h3>Kids</h3>
                    </div>
                </div>
            </section>

            <footer>
                <div className="footer-content">
                    <p>&copy; 2024 FashionHub. All Rights Reserved.</p>
                   
                </div>
            </footer>
        </div>
    );
};


export default MainPage;
