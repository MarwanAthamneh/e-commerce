import React, { useEffect, useState, useCallback } from 'react';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import './Style/Cart.css'     

const Cart = () => {
    const [cartItems, setCartItems] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const getUserFromToken = () => {
        const token = localStorage.getItem('jwtToken');
        if (!token) return null;
        return jwtDecode(token);
    };

    const fetchCartItems = useCallback(async () => {
        try {
            const token = localStorage.getItem('jwtToken');
            if (!token) {
                setError('Please login to view your cart');
                setLoading(false);
                return;
            }

            const decodedToken = getUserFromToken();
            if (!decodedToken?.userId) {
                setError('Invalid session. Please login again.');
                setLoading(false);
                return;
            }

            const response = await axios.get(
                `http://localhost:9090/api/cart/${decodedToken.userId}`,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                }
            );

            console.log('Cart response:', response.data);
            setCartItems(response.data);
            setLoading(false);
        } catch (err) {
            console.error('Error fetching cart:', err);
            setError('Failed to fetch cart items');
            setLoading(false);
        }
    }, []);

    const removeFromCart = useCallback(async (cartItemId) => {
        try {
            const token = localStorage.getItem('jwtToken');
            if (!token) {
                setError('Please login to remove items');
                return;
            }

            const cartId = cartItems[0]?.cartId;
            if (!cartId) {
                console.error('No cartId found');
                setError('Unable to remove item: Cart ID not found');
                return;
            }

            console.log('Removing item:', { cartId, cartItemId });
            
            const response = await axios.delete(
                `http://localhost:9090/api/cart/${cartId}/items/${cartItemId}`,
                {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                }
            );

            console.log('Remove response:', response);

            if (response.status === 200) {
                await fetchCartItems(); // Refresh cart after removal
            }
        } catch (err) {
            console.error('Error removing item:', err);
            setError('Failed to remove item');
        }
    }, [cartItems, fetchCartItems]);

    useEffect(() => {
        fetchCartItems();
    }, [fetchCartItems]);

    if (loading) {
        return <div className="cart-loading">Loading your cart...</div>;
    }

    return (
        <div className="cart-container">
            <h2 className="cart-title">Your Shopping Cart</h2>
            
            {error && <div className="cart-error">{error}</div>}
            
            {!loading && !error && cartItems.length === 0 && (
                <div className="cart-empty">Your cart is empty</div>
            )}
            
            {!loading && !error && cartItems.length > 0 && (
                <div className="cart-items">
                    {cartItems.map((cartItem) => (
                        <div key={cartItem.id} className="cart-item">
                            <div className="item-image">
                                <img 
                                    src={`/Photos/${cartItem.item?.imageUrl}`} 
                                    alt={cartItem.item?.name}
                                    className="cart-item-image"
                                />
                            </div>
                            <div className="item-details">
                                <h3>{cartItem.item?.name || 'Unknown Item'}</h3>
                                <p className="item-price">
                                    ${cartItem.item?.price.toFixed(2) || '0.00'}
                                </p>
                                <p className="item-quantity">
                                    Quantity: {cartItem.quantity || 0}
                                </p>
                            </div>
                            <button 
                                className="remove-button"
                                onClick={() => removeFromCart(cartItem.id)}
                            >
                                Remove
                            </button>
                        </div>
                    ))}
                    
                    <div className="cart-summary">
                        <h3>
                            Total: ${cartItems.reduce((sum, item) => 
                                sum + ((item.item?.price || 0) * (item.quantity || 0)), 0).toFixed(2)}
                        </h3>
                        <button className="checkout-button">
                            Proceed to Checkout
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Cart;

