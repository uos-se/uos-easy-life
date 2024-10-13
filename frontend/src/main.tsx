import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { Login } from "./pages/login/Login";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { Main } from "./pages/main/Main";
import { SessionContextProvider } from "./context/SessionContextProvider";

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
