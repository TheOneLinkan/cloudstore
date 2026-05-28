import {useState} from "react";

function Login() {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    async function handleLogin(e) {

        e.preventDefault();

        try {

            const response = await fetch(`${import.meta.env.VITE_API_URL}/auth/login`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    username,
                    password
                })
            });

            if (!response.ok) {
                throw new Error("Login failed");
            }

            const token = await response.text();

            localStorage.setItem("token", token);

            alert("Login successful");

        } catch (error) {

            console.error(error);
            alert("Wrong username or password");
        }
    }

    return (
        <div className="card shadow-sm p-4 mb-4">

            <h2 className="mb-4">
                Login
            </h2>

            <form onSubmit={handleLogin}>

                <div className="mb-3">
                    <input
                        type="text"
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        className="form-control"
                    />
                </div>

                <div className="mb-3">
                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="form-control"
                    />
                </div>

                <button
                    type="submit"
                    className="btn btn-dark"
                >
                    Login
                </button>

            </form>

        </div>
    );
}

export default Login;