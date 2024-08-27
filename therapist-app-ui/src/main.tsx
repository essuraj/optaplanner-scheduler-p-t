import { createRoot } from "react-dom/client";
import App from "./App.tsx";
import "./index.css";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { GeistProvider } from "@geist-ui/core";
const queryClient = new QueryClient();

createRoot(document.getElementById("root")!).render(
  <GeistProvider>
    <QueryClientProvider client={queryClient}>
      <App />
    </QueryClientProvider>
  </GeistProvider>
);
