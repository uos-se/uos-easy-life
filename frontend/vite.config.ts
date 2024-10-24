import react from "@vitejs/plugin-react";
import path from "path";
import { defineConfig } from "vite";

// https://vitejs.dev/config/
export default defineConfig({
  server: {
    host: "0.0.0.0",
    proxy: {
      "/api": {
        target: "http://localhost:8080",
      },
    },
  },
  plugins: [react()],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
});
