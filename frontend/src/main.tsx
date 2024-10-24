import "./index.css";

import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { SessionContextProvider } from "./context/SessionContextProvider";
import { Login } from "./pages/login/Login";
import { Main } from "./pages/main/Main";

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
    <SessionContextProvider>
      <RouterProvider router={router} />
    </SessionContextProvider>
  </StrictMode>
);
