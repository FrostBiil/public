// Importering af Mantine-komponenter til opbygning af brugergrænsefladen
import { Container, Title, Text, Button, SimpleGrid } from "@mantine/core";

// Funktion til at navigere brugeren tilbage til hjemmesiden
function goToHome() {
  window.location.href = "/";
}

// Komponent for en fejlside
export function ErrorPage() {
  return (
    <Container pt="80px" pb="80px">
      <SimpleGrid spacing={{ base: 40, sm: 80 }} cols={{ base: 1, sm: 2 }}>
        <div>
          <Title fw="900" fs="34px" mb="md" ff="arial">
            Der er noget galt...
          </Title>
          <Text c="dimmed" size="lg">
            Siden du forsøger at tilgå eksistere ikke. Du kan have skrevet den
            forkerte webadresse, eller siden er blevet flyttet til en anden URL.
            Hvis dette er en fejl, kontakt support.
          </Text>
          <Button onClick={goToHome} variant="outline" size="md" mt="xl">
            Gå tilbage til forsiden
          </Button>
        </div>
      </SimpleGrid>
    </Container>
  );
}