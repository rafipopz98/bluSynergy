import React, { useState, useEffect } from "react";
import axios from "axios";
import "./App.css";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from "react-router-dom";

function Product() {
  const [products, setProducts] = useState([]);
  const [cartItems, setCartItems] = useState([]);
  const [totalAmount, setTotalAmount] = useState(0);
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [quantities, setQuantities] = useState({});

  const navigate = useNavigate();

  // fetch all products

  const fetchProducts = () => {
    axios
      .get("http://localhost:8081/product/all")
      .then((response) => {
        setProducts(response.data);

        const initialQuantities = {};
        response.data.forEach((product) => {
          initialQuantities[product.id] = "Enter your Quantity";
        });
        setQuantities(initialQuantities);
      })
      .catch((error) => {
        console.error("Error fetching products:", error);
      });
  };

  useEffect(() => {
    fetchProducts();
  }, [cartItems]);

  // add to =cart
  const handleAddToCart = (productId, productName, productPrice) => {
    // validation to check user quantity is number and and not null
    const quantityToAdd = parseInt(quantities[productId], 10);
    if (isNaN(quantityToAdd) || quantityToAdd <= 0) {
      toast.error("Please enter a valid quantity.");
      return;
    }
    // to check whether the user given quantity is more than present quantity
    const product = products.find((p) => p.id === productId);
    if (quantityToAdd > product.quantity) {
      toast.error(`Cannot add more than ${product.quantity} available.`);
      return;
    }

    axios
      .post("http://localhost:8081/cart/add", {
        productId: productId,
        quantity: quantities[productId],
      })
      .then(() => {
        fetchCartItems();
        toast.success(`${productName} added to cart successfully!`);
      })
      .catch((error) => {
        console.error("Error adding to cart:", error);
        toast.error("Failed to add to cart. Please try again.");
      });
  };

  // all cart products
  const fetchCartItems = () => {
    axios
      .get("http://localhost:8081/cart/all")
      .then((response) => {
        setCartItems(response.data);
        let total = 0;
        response.data.forEach((cart) => {
          cart.cartItems.forEach((item) => {
            total += item.totalAmount;
          });
        });
        setTotalAmount(total);
      })
      .catch((error) => {
        console.error("Error fetching cart items:", error);
      });
  };

  useEffect(() => {
    fetchCartItems();
  }, []);

  // placing the order

  const placeOrder = () => {
    // validation to check name and email are not null and with spaces
    if (!name.trim()) {
      toast.error("Name is required.");
      return;
    } else if (!email.trim()) {
      toast.error("Email is required.");
      return;
    } else if (cartItems.length <= 0) {
      toast.error("Cart is  empty.");
      return;
    }

    const cartIds = cartItems.map((cart) => cart.cartId);
    axios
      .post("http://localhost:8081/order/place", {
        name: name,
        email: email,
        cartIds: cartIds,
      })
      .then((response) => {
        console.log("Order placed successfully:", response.data);
        toast.success("Order placed successfully!");

        let orderedItems = [];
        cartItems.forEach((cart) => {
          cart.cartItems.forEach((item) => {
            orderedItems.push({
              productName: item.productName,
              quantity: item.quantity,
              totalAmount: item.totalAmount,
            });
          });
        });

        let total = 0;
        cartItems.forEach((cart) => {
          cart.cartItems.forEach((item) => {
            total += item.totalAmount;
          });
        });

        const orderDetails = {
          username: name,
          email: email,
          orderedItems: orderedItems,
          total,
        };

        navigate("/pay", { state: { orderDetails } });

        setCartItems([]);
        setTotalAmount(0);
        setName("");
        setEmail("");
      })
      .catch((error) => {
        console.error("Error placing order:", error);
        toast.error("Failed to place order. Please try again.");
      });
  };

  // to update quantity
  const updateQuantity = (productId, newQuantity) => {
    setQuantities((prevQuantities) => ({
      ...prevQuantities,
      [productId]: newQuantity,
    }));
  };

  return (
    <div className="Wrapper">
      <ToastContainer />
      <div className="products">
        <h1>
          Enter a quantity and click the "Add" button to put this in your
          shopping cart
        </h1>
        {products.map((product) => (
          <div key={product.id} className="singleItem">
            <h1>{product.name}</h1>
            <p>${product.price}</p>
            <p style={{ color: "red" }}>{product.quantity} left </p>
            <input
              placeholder="Enter quantity"
              type="number"
              value={quantities[product.id] || ""}
              onChange={(e) => updateQuantity(product.id, e.target.value)}
            />
            <button
              onClick={() =>
                handleAddToCart(product.id, product.name, product.price)
              }
            >
              ADD
            </button>
          </div>
        ))}
      </div>
      <div className="cart">
        <h1>Your shopping cart</h1>
        <table>
          <thead>
            <tr>
              <th>Product</th>
              <th>Quantity</th>
              <th>Amount</th>
            </tr>
          </thead>
          <tbody>
            {cartItems.length === 0 ? (
              <tr>
                <td colSpan="3">Cart is empty</td>
              </tr>
            ) : (
              cartItems.map((cart) =>
                cart.cartItems.map((item, index) => (
                  <tr key={index}>
                    <td>{item.productName}</td>
                    <td>{item.quantity}</td>
                    <td>${item.totalAmount.toFixed(2)}</td>
                  </tr>
                ))
              )
            )}
            <tr>
              <td colSpan="2">Total</td>
              <td>${totalAmount.toFixed(2)}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div className="details">
        <h1>Enter your name and email to place the order</h1>
        <div className="inputs">
          <input
            type="text"
            placeholder="Enter name"
            value={name}
            required
            onChange={(e) => setName(e.target.value)}
          />
          <input
            type="text"
            placeholder="Enter email"
            value={email}
            required
            onChange={(e) => setEmail(e.target.value)}
          />
          <button type="submit" onClick={placeOrder}>
            Place Order
          </button>
        </div>
      </div>
    </div>
  );
}

export default Product;
