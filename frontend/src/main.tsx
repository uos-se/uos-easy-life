import "./index.css";

import { StrictMode, useEffect, useState } from "react";
import { createRoot } from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { OpenAPI } from "./api";
import { Login } from "./pages/login/Login";
import { Main } from "./pages/main/Main";
import { useUserStore } from "./store/userStore";

OpenAPI.BASE = location.origin;

const router = createBrowserRouter([
  {
    path: "/",
    element: <Main />,
  },
  {
    path: "/login",
    element: <Login />,
  },
]);

function Root() {
  const { initialize, isInitialized } = useUserStore();
  const [dots, setDots] = useState(1);

  useEffect(() => {
    initialize();
    const interval = setInterval(() => {
      setDots((dots) => (dots === 3 ? 1 : dots + 1));
    }, 500);
    return () => clearInterval(interval);
  }, [initialize]);

  if (!isInitialized) {
    return (
      <div className="w-100 h-dvh flex justify-center items-center">
        <h1 className="text-2xl text-gray-500">Loading{".".repeat(dots)}</h1>
      </div>
    );
  }

  return <RouterProvider router={router} />;
}

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <Root />
  </StrictMode>
);
