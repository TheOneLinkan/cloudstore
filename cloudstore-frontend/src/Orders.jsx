import {useState} from "react";

function Orders() {

    const [orders, setOrders] = useState([]);
    const [loaded, setLoaded] = useState(false);

    async function fetchOrders() {
        const token = localStorage.getItem("token");
        if (!token) {
            alert("You must be logged in to view orders");
            return;
        }
        try {
            const response = await fetch(`${import.meta.env.VITE_API_URL}/orders/my`, {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            });
            if (!response.ok) throw new Error("Failed to fetch orders");
            const data = await response.json();
            setOrders(data);
            setLoaded(true);
        } catch (error) {
            console.error(error);
            alert("Could not fetch orders");
        }
    }

    return (
        <div className="card shadow-sm p-4 mb-4">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2 className="mb-0">My Orders</h2>
                <button className="btn btn-dark" onClick={fetchOrders}>
                    Load Orders
                </button>
            </div>

            {loaded && orders.length === 0 && (
                <p className="text-muted">No orders yet.</p>
            )}

            {orders.length > 0 && (
                <table className="table table-striped">
                    <thead>
                    <tr>
                        <th>Product</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                    </tr>
                    </thead>
                    <tbody>
                    {orders.map((order, index) => (
                        <tr key={index}>
                            <td>{order.productTitle}</td>
                            <td>${order.productPrice}</td>
                            <td>{order.quantity}</td>
                            <td>${order.totalPrice?.toFixed(2)}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default Orders;