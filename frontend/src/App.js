import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Register from './FashionHub/Register';
import Login from './FashionHub/Login';
import MainPage from './FashionHub/MainPage';
import AboutUs from './FashionHub/AboutUs';
import Cart from './FashionHub/Cart';
import Contact from './FashionHub/Contact';
function App() {

  return (
   
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path='/main' element={<MainPage/> }/>
        <Route path='/about' element={<AboutUs/>}/>
        <Route path='/cart' element={<Cart/>}/>
        <Route path="/contact" element={<Contact />} />

      </Routes>
    </Router>
  );
}

export default App;