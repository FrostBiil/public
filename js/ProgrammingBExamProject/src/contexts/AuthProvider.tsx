// Importing af react for at kunne bruge hooks
import { createContext, useEffect, useState } from "react";

// Import af api for at kunne bruge login og logout
import { Api } from "../utils/api";

// Createring af interface User, som beskriver en bruger i applikationen
interface AuthContextProps {
  user: User | null;
  loaded: boolean;
  login: () => void;
  logout: () => void;
}

// Opretter en context, som kan bruges til at gemme brugerens data
export function AuthProvider(props: React.PropsWithChildren<{}>) {
  // Opretter en state, som gemmer brugerens data
  const [user, setUser] = useState<User | null>(null);
  
  // Opretter en state, som gemmer om brugeren er loaded
  const [loaded, setLoaded] = useState(false);
  
  // Opretter en funktion, som logger brugeren ind
  const login = () => {
    Api.login();
  };

  // Opretter en funktion, som logger brugeren ud
  const logout = () => {
    Api.logout();
    setUser(null);
  };

  // Henter brugerens data fra databasen, når komponenten bliver loaded
  useEffect(() => {
    Api.me().then((user) => {
        setUser(user);
        setLoaded(true);
    });
  }, []);

  // Returnerer en provider, som giver børnene adgang til brugerens data
  return (
    <AuthContext.Provider value={{user, login, logout, loaded}}>
      {props.children}
    </AuthContext.Provider>
  );
}

// Opretter en context, som kan bruges til at gemme brugerens data
export const AuthContext = createContext({} as AuthContextProps);
