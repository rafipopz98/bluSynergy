import React, { useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";

const Message = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const orderDetails = location.state?.orderDetails;

  useEffect(() => {
    const timer = setTimeout(() => {
      navigate("/");
    }, 8000);

    return () => clearTimeout(timer);
  }, [navigate]);

  if (!orderDetails) {
    return (
      <div className="message-container">
        <div className="order-confirmation">No order details available.</div>
      </div>
    );
  }

  return (
    <div className="message-container">
      <div className="order-confirmation">
        <h2>Order Confirmation</h2>
        <p>Your order has been placed successfully!</p>
        <div>
          <strong>Name:</strong> {orderDetails.username}
        </div>
        <div>
          <strong>Email:</strong> {orderDetails.email}
        </div>
        <div className="order-details">
          <strong>Ordered Items:</strong>
          <ul>
            {orderDetails.orderedItems.map((item, index) => (
              <li key={index}>
                {item.productName} - Quantity: {item.quantity} - Total Amount: $
                {item.totalAmount.toFixed(2)}
              </li>
            ))}
            <li>Total Amount to be paid ${orderDetails.total.toFixed(2)}</li>
          </ul>
        </div>
      </div>
    </div>
  );
};

export default Message;
