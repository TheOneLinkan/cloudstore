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

            <div className="d-flex justify-content-between align-items-center mb-4">

                <h2 className="mb-0">
                    Products
                </h2>

                <span className="badge bg-dark">
                {products.length} products
            </span>

            </div>

            <div className="row">

                {products.map(product => (

                    <div
                        className="col-md-4 mb-4"
                        key={product.id}
                    >

                        <div className="card h-100 shadow-sm">

                            <img
                                src={product.image}
                                className="card-img-top p-3"
                                style={{
                                    height: "250px",
                                    objectFit: "contain"
                                }}
                                alt={product.title}
                            />

                            <div className="card-body d-flex flex-column">

                                <h5 className="card-title">
                                    {product.title}
                                </h5>

                                <p className="card-text fw-bold">
                                    ${product.price}
                                </p>

                                <button
                                    className="btn btn-primary mt-auto"
                                    onClick={() => createOrder(product.id)}
                                >
                                    Create Order
                                </button>

                            </div>

                        </div>

                    </div>
                ))}

            </div>

        </div>
    );
}

export default Products;