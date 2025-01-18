// Importerer AppShell fra Mantine til layout og styling
import { AppShell } from "@mantine/core";

// Importerer Router og routing-komponenter fra react-router-dom til styring af navigation
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

// Importerer Mantine's globale CSS-filer for styling af kernekomponenter og specifikke moduler
import "@mantine/core/styles.css";
import "@mantine/carousel/styles.css";
import "@mantine/dropzone/styles.css";
import "@mantine/notifications/styles.css";

// Importerer specifikke sider og komponenter
import { ErrorPage } from "./pages/ErrorPage";
import { AuthProvider } from "./contexts/AuthProvider";
import { Navbar } from "./components/Navbar";
import { SettingsPage } from "./pages/SettingsPage";
import { StorePage } from "./pages/StorePage";
import { LibraryPage } from "./pages/LibraryPage";
import { UploadPage } from "./pages/UploadPage";
import { GamePage } from "./pages/GamePage";

// Definerer hovedkomponenten App, som anvender AuthProvider for autentifikation
// og Router for at definere ruterne i applikationen.
export default function App() {
  return (
    <AuthProvider>
      <Router>
        <AppShell
          navbar={{
            width: 100, // Bredden af navbar
            breakpoint: 0, // Breakpoint for responsivt design
          }}
        >
          <AppShell.Navbar>
            <Navbar /> {/* Indsætter Navbar komponenten*/}
          </AppShell.Navbar>
          <AppShell.Main>
            <Routes>
              {/* Definerer ruter for applikationen, hvor hver rute er knyttet til en specifik side */}
              <Route path="/" element={<StorePage />} />
              <Route path="/butik" element={<StorePage />} />
              <Route path="/bibliotek" element={<LibraryPage />} />
              <Route path="/upload" element={<UploadPage />} />
              <Route path="/indstillinger" element={<SettingsPage />} />
              <Route path="/spil/:id" element={<GamePage />} />
              <Route path="*" element={<ErrorPage />} /> {/* Fallback rute hvis ingen andre matcher */}
            </Routes>
          </AppShell.Main>
        </AppShell>
      </Router>
    </AuthProvider>
  );
}
