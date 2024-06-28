import React from "react";
import Product from "./Product";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Message from "./Message";

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Product />} />
        <Route path="/pay" element={<Message />} />
      </Routes>
    </Router>
  );
};

export default App;
