// Henter useParams hook fra react-router-dom for at kunne tilgå URL-parametre.
import { useParams } from "react-router-dom";  

// Importerer Api modulet for at kunne foretage API kald.
import { Api } from "../utils/api";  

// Importerer hooks fra React til statshåndtering og sideeffekter.
import { useEffect, useState } from "react";  

// Importerer UI komponenter fra Mantine.
import {
  Container,
  Paper,
  Image,
  Text,
  Flex,
  Box,
  Divider,
  Pill,
  useMantineTheme,
  Button,
  Group,
  Center,
  Loader,
  SimpleGrid,
  Title,
} from "@mantine/core";

// Importerer Carousel komponent for billedefremvisning.
import { Carousel } from "@mantine/carousel";

// Definition af typen 'Game' for at strukturere spilinformation korrekt.
interface Game {
  id: string;
  releaseDate: Date;
  lastUpdated: Date;
  publisherId: string;
  projectUrl: string;
  title: string;
  description: string;
  tags: string[];
  genres: string[];
  cover: string;
  screenshots: string[];
}

// Hovedkomponent for spilsiden, hvor specifikke spiloplysninger vises.
export function GamePage() {
  // Henter spil-ID fra URL'en ved hjælp af useParams hook.
  const { id } = useParams();

  // Tilstandsværdier for spiloplysninger og indlæsningsstatus.
  const [game, setGame] = useState<Game | null>(null);
  const [loading, setLoading] = useState(true);

  // Anvender tema fra Mantine for konsistent styling.
  const theme = useMantineTheme();

  // Komponent der viser en download knap for spillet.
  const downloadGameButton = (item: Game) => {
    return (
      <Group mt="xs">
        <Button
          fullWidth
          justify="center"
          size="sm"
          onClick={() => window.open(item.projectUrl + "/releases/latest")}
        >
          Download spil
        </Button>
      </Group>
    );
  };

  // Funktion til at navigere brugeren tilbage til hjemmesiden.
  function goToHome() {
    window.location.href = "/";
  }

  // Hook der henter spildata fra API ved komponentmontering eller når ID ændres.
  useEffect(() => {
    if (id === undefined) {
      return;  // Tidlig returnering hvis ID ikke er defineret.
    }
    Api.getGame(id).then((game) => {
      setGame(game);  // Gemmer spiloplysningerne i tilstand.
      setLoading(false);  // Indikere at indlæsning er færdig.
    });
  }, [id]);

  // Viser en loader mens data indlæses.
  if (loading) {
    return (
      <Center pt={"xl"}>
        <Loader />
      </Center>
    );
  }

  // Viser en fejlmeddelelse hvis spillet ikke kunne findes.
  if (game === null) {
    return (
      <Container pt="80px" pb="80px">
        <SimpleGrid spacing={{ base: 40, sm: 80 }} cols={{ base: 1, sm: 2 }}>
          <div>
            <Title fw="900" fs="34px" mb="md" ff="arial">
              Der er noget galt...
            </Title>
            <Text c="dimmed" size="lg">
              Siden du forsøger at tilgå eksisterer ikke. Du kan have skrevet den
              forkerte webadresse, eller siden er blevet flyttet til en anden
              URL. Hvis dette er en fejl, kontakt support.
            </Text>
            <Button onClick={goToHome} variant="outline" size="md" mt="xl">
              Gå tilbage til forsiden
            </Button>
          </div>
        </SimpleGrid>
      </Container>
    );
  }

  // Hovedvisning af spiloplysninger når data er tilgængelige.
    return (
    <>
      <Container mt={"xl"}>
        <Paper withBorder shadow={"md"} p={"md"}>
          <Flex h="100%">
            <Flex direction={"column"} h={"100%"} p="xs">
              <Image
                style={{ aspectRatio: 16 / 9 }}
                radius="md"
                src={game.cover}
                alt={game.title}
              />
              <Carousel withIndicators height={200} slidesToScroll={5}>
                {game.screenshots.map((screenshot) => (
                  <Carousel.Slide>
                    <Image
                      style={{ aspectRatio: 16 / 9 }}
                      src={screenshot}
                      alt={game.title}
                    />
                  </Carousel.Slide>
                ))}
              </Carousel>
            </Flex>
            <Flex direction={"column"} p="xs" gap={"sm"}>
              <Image
                w={"10vw"}
                style={{ aspectRatio: 16 / 9 }}
                radius="md"
                src={game.cover}
                alt={game.title}
              />
              <h3>{game.title}</h3>
              <Text>{game.description}</Text>
              <Divider label="Genres" labelPosition="left" />
              <Text>{game.genres.join(", ")}</Text>
              <Divider label="Tags" labelPosition="left" />
              <Flex>
                {game.tags.map((tag) => (
                  <Pill
                    key={tag}
                    radius="xl"
                    variant="filled"
                    bg={theme.colors[theme.primaryColor][1]}
                  >
                    {tag}
                  </Pill>
                ))}
              </Flex>
              <Divider />
              <Box style={{ flexGrow: 1 }} />

              {downloadGameButton(game)}
            </Flex>
          </Flex>
        </Paper>
      </Container>
    </>
  );
}
