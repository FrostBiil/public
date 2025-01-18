// Ekstraherer API_BASEURL og NODE_ENV fra miljøvariabler og sætter standardværdier
export const {
  // Sætter API_BASEURL til miljøvariabelens værdi eller '/api' som standard,
  // anvendes til at definere basis URL for API-anmodninger.
  REACT_APP_API_BASEURL: API_BASEURL = '/api',
  
  // Bestemmer applikationens driftsmiljø, som standard 'production'.
  NODE_ENV = 'production',
} = process.env

// Definerer IS_PRODUCTION som en boolsk værdi, der tjekker om miljøet er 'production'.
export const IS_PRODUCTION = NODE_ENV === 'production'
