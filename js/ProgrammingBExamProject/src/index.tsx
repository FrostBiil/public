// Importerer React og ReactDOM til opbygning og rendering af React-komponenttræet
import React from "react";
import ReactDOM from "react-dom/client";

// Importerer hovedkomponenten App
import App from "./app";

// Importerer MantineProvider og createTheme fra @mantine/core til temastyring
import { MantineProvider, createTheme } from "@mantine/core";

// Definerer et tema, hvor den primære farve hentes fra lokal lagring eller sættes til 'blå',
// og anvender en primær skygge på niveau 7.
const theme = createTheme({
  primaryColor: window.localStorage.getItem("mantine-color-scheme") || "blue",
  primaryShade: 7,
});

// Opretter en rod container knyttet til 'root'-DOM-elementet, med sikkerhed for at elementet findes (non-null assertion).
const root = ReactDOM.createRoot(document.getElementById("root")!);

// Renderer React-komponenttræet i React.StrictMode for at aktivere tjek og advarsler for dets efterkommere.
root.render(
  <React.StrictMode>
    <MantineProvider theme={theme}>
      {/* Indkapsler App-komponenten med MantineProvider for at anvende det globale tema */}
      <App />
    </MantineProvider>
  </React.StrictMode>
);