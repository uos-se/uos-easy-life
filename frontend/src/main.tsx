import "./index.css";

import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { Login } from "./pages/login/Login";
import { Main } from "./pages/main/Main";
import { SessionProvider } from "./context/SessionProvider";
import { OpenAPI } from "./api";

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

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <SessionProvider>
      <RouterProvider router={router} />
    </SessionProvider>
  </StrictMode>
);
