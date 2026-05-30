import {useState} from "react";

function Login() {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [isRegistering, setIsRegistering] = useState(false);
    const [loggedInUser, setLoggedInUser] = useState(localStorage.getItem("username") || "");

    async function handleLogin(e) {
        e.preventDefault();
        try {
            const response = await fetch(`${import.meta.env.VITE_API_URL}/auth/login`, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({username, password})
            });
            if (!response.ok) throw new Error("Login failed");
            const token = await response.text();
            localStorage.setItem("token", token);
            localStorage.setItem("username", username);
            setLoggedInUser(username);
            alert("Login successful");
        } catch (error) {
            console.error(error);
            alert("Wrong username or password");
        }
    }

    async function handleRegister(e) {
        e.preventDefault();
        try {
            const response = await fetch(`${import.meta.env.VITE_API_URL}/auth/register`, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({username, password})
            });
            if (!response.ok) throw new Error("Registration failed");
            alert("Account created! You can now log in.");
            setIsRegistering(false);
        } catch (error) {
            console.error(error);
            alert("Registration failed");
        }
    }

    function handleLogout() {
        localStorage.removeItem("token");
        localStorage.removeItem("username");
        setLoggedInUser("");
    }

    if (loggedInUser) {
        return (
            <div className="card shadow-sm p-4 mb-4 d-flex flex-row justify-content-between align-items-center">
                <span>Logged in as <strong>{loggedInUser}</strong></span>
                <button className="btn btn-outline-dark" onClick={handleLogout}>
                    Logout
                </button>
            </div>
        );
    }

    return (
        <div className="card shadow-sm p-4 mb-4">
            <h2 className="mb-4">{isRegistering ? "Register" : "Login"}</h2>
            <form onSubmit={isRegistering ? handleRegister : handleLogin}>
                <div className="mb-3">
                    <input type="text" placeholder="Username" value={username}
                           onChange={(e) => setUsername(e.target.value)} className="form-control"/>
                </div>
                <div className="mb-3">
                    <input type="password" placeholder="Password" value={password}
                           onChange={(e) => setPassword(e.target.value)} className="form-control"/>
                </div>
                <button type="submit" className="btn btn-dark me-2">
                    {isRegistering ? "Register" : "Login"}
                </button>
                <button type="button" className="btn btn-outline-secondary"
                        onClick={() => setIsRegistering(!isRegistering)}>
                    {isRegistering ? "Back to Login" : "Create Account"}
                </button>
            </form>
        </div>
    );
}

export default Login;