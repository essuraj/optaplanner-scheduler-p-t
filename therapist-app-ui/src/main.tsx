import { createRoot } from "react-dom/client";
import App from "./App.tsx";
import "./index.css";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
const queryClient = new QueryClient();
import { PrismaneProvider } from "@prismane/core";

createRoot(document.getElementById("root")!).render(
  <PrismaneProvider>
    <QueryClientProvider client={queryClient}>
      <App />
    </QueryClientProvider>
  </PrismaneProvider>
);
