import {useEffect, useState} from "react";

function Products() {

    const [products, setProducts] = useState([]);

    useEffect(() => {

        fetch("http://localhost:8081/products")
            .then(response => response.json())
            .then(data => {

                console.log(data);

                setProducts(data);
            })
            .catch(error => {
                console.error(error);
            });

    }, []);

    const createOrder = async (productId) => {

        const token = localStorage.getItem("token");

        try {

            const response = await fetch(
                "http://localhost:8080/orders",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${token}`
                    },
                    body: JSON.stringify({
                        productId: productId,
                        quantity: 1
                    })
                }
            );

            if (!response.ok) {
                throw new Error("Failed to create order");
            }

            const data = await response.json();

            console.log("Order created:", data);

            alert(`Order created for product ${productId}`);

        } catch (error) {

            console.error(error);

            alert("Could not create order");
        }
    };

    return (
        <div>

            <h2>Products</h2>
            <p>Products loaded: {products.length}</p>
            {products.map(product => (

                <div
                    key={product.id}
                    style={{
                        border: "1px solid black",
                        padding: "10px",
                        marginBottom: "10px"
                    }}
                >
                    <h3>{product.title}</h3>

                    <p>Price: ${product.price}</p>

                    <button
                        onClick={() => createOrder(product.id)}
                    >
                        Create Order
                    </button>

                </div>
            ))}

        </div>
    );
}

export default Products;