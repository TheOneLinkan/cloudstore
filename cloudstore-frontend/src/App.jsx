import Login from "./Login";
import Products from "./Products";
import Orders from "./Orders";

function App() {
    return (
        <div className="bg-light min-vh-100">
            <nav className="navbar navbar-dark bg-dark shadow-sm">
                <div className="container">
                    <span className="navbar-brand mb-0 h1">CloudStore</span>
                </div>
            </nav>
            <div className="container py-4">
                <Login/>
                <hr className="my-4"/>
                <Orders/>
                <hr className="my-4"/>
                <Products/>
            </div>
        </div>
    );
}

export default App;