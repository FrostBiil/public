// Importering af nødvendige komponenter fra Mantine
import {
  Image,
  Title,
  Text,
  Button,
  Divider,
  Grid,
  Group,
  Flex,
  Pill,
  Center,
  Loader,
} from "@mantine/core";

// Importering af nødvendige typer fra Prisma
import { Game, GameOwner } from "@prisma/client";

// Importering af nødvendige komponenter og hooks React
import { useContext, useEffect, useState } from "react";

// Importering af api til at hente data fra backend
import { Api } from "../utils/api";

// Importering af AuthContext fra AuthProvider
import { AuthContext } from "../contexts/AuthProvider";

// Definering af LibraryPage komponenten
export function LibraryPage() {
  // Definering af state til at gemme spil
  const [ownerships, setOwnerships] = useState<GameOwner[]>([]);
  
  // Henter bruger og om bruger er loaded fra AuthContext
  const { user, loaded } = useContext(AuthContext);

  // Definering af state til at gemme om data er loaded
  const [loading, setLoading] = useState(true);

  // Henter spil fra backend og gemmer dem i state
  useEffect(() => {
    Api.getUserGames().then((ownerships) => {
      console.log(ownerships);
      setOwnerships(ownerships.map((ownership) => ownership));
      setLoading(false);
    });
  }, []);

  // Hvis bruger ikke er loaded og bruger er loaded, så logges bruger ind
  useEffect(() => {
    console.log(user);
    if (!user && loaded) {
      Api.login();
    }
  }, [user, loaded]);

  // Definering af goToGamePageButton funktionen
  const goToGamePageButton = (item: Game) => {
    return (
      <Group pt="xs">
        <Button size="sm" onClick={() => window.open("/spil/" + item.id)}>
          Se spil
        </Button>
      </Group>
    );
  };

  // Definering af games funktionen
  const games = (span?: number, sortedByDate?: boolean) =>
    ownerships
      .sort((a, b) =>
        sortedByDate
          ? new Date(b.date).getTime() - new Date(a.date).getTime()
          : (a as any as { game: Game }).game.title.localeCompare(
              (b as any as { game: Game }).game.title
            )
      )
      .map((o) => (o as any as { game: Game }).game)
      .map((item) => (
        <Grid.Col key={item.id} span={span ?? 2} p="lg">
          <Image
            style={{ aspectRatio: 16 / 9 }}
            radius="md"
            src={item.cover}
            alt={item.title}
          />
          <Title order={3} pt="md">
            {item.title}
          </Title>
          <Text c="gray" pb="md">
            {(item.genres ?? []).join(", ")}
          </Text>
          <Text fz="sm" pt="xs">
            {item.description}
          </Text>
          {item.tags && item.tags.length > 0 ? (
            <>
              <Divider pt="md" labelPosition="left" label="Tags" />
              <Flex wrap={"wrap"} gap="md" pt={"md"}>
                {item.tags.map((tag) => (
                  <Pill key={tag} color="blue" style={{ marginRight: 5 }}>
                    {tag}
                  </Pill>
                ))}
              </Flex>
              <Divider pt="xs" />
            </>
          ) : (
            <></>
          )}
          {goToGamePageButton(item)}
        </Grid.Col>
      ));

  // Hvis data er loading, så vises loader
  if (loading)
    return (
      <Center pt={"xl"}>
        <Loader />
      </Center>
    );

  // Returnerer siden hvis der ikke loades
  return (
    <>
        <Divider px="md" labelPosition="left" label={"Senest spil"} />
        <Grid columns={3} p="md">
          {games(1, true).slice(0, 3)}
        </Grid>

        <Divider
          px="md"
          labelPosition="left"
          label={"Spil " + ownerships.length}
        />
        <Grid columns={5} p="md">
          {games(1)}
        </Grid>
    </>
  );
}
