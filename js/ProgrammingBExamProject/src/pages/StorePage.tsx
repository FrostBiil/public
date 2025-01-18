// Importering af hooks fra react
import { useContext, useEffect, useState } from "react";

// Importering af nødvendige komponenter fra Matine Carousel
import { Carousel } from "@mantine/carousel";

// Importering af nødvendige komponenter fra Mantine Core
import {
  Image,
  Paper,
  Text,
  Title,
  Button,
  useMantineTheme,
  Divider,
  Box,
  Flex,
  Pill,
  SimpleGrid,
  Group,
  Input,
  MultiSelect,
  TagsInput,
  Center,
  Loader,
} from "@mantine/core";

// Importering af Game fra prisma/client til at definere spil
import { Game } from "@prisma/client";

// Importering af Api til at hente spil fra databasen
import { Api } from "../utils/api";

// Importering af AuthContext fra AuthProvider til at tjekke om brugeren er logget ind
import { AuthContext } from "../contexts/AuthProvider";

// Importering af GameCardLarge komponenten fra components mappen
import { GameCardLarge } from "../components/GameCardLarge";

// Importering af nødvendige hooks fra Mantine
import { useDebouncedState } from "@mantine/hooks";

// Definering af StorePage komponenten
export function StorePage() {
  // Definering af games og setGames med useState
  const [games, setGames] = useState<Game[]>([]);
  
  // Definering af theme fra useMantineTheme
  const theme = useMantineTheme();
  
  // Definering af bruger fra AuthContext
  const { user } = useContext(AuthContext);

  // Definering af søg og setSearch med useDebouncedState
  const [search, setSearch] = useDebouncedState("", 200);

  // Definering af genres og setGenres med useState
  const [genres, setGenres] = useState<string[] | undefined>(undefined);
  
  // Definering af tags og setTags med useDebouncedState
  const [tags, setTags] = useDebouncedState<string[]>([], 200);

  // Definering af loading og setLoading med useState
  const [loading, setLoading] = useState(true);

  // useEffect hook til at hente spil fra databasen
  useEffect(() => {
    Api.getGames(
      search.length > 0 ? search : undefined,
      genres && genres?.length > 0 ? genres : undefined,
      tags.length > 0 ? tags : undefined
    ).then((games) => {
      console.log(games);
      setGames(games);
      setLoading(false);
    });
  }, [search, genres, tags]);

  // Funktion til at tilføje spil til bruger
  const addGameButton = (item: Game) => {
    return user ? (
      <SimpleGrid mt={"md"} cols={2} w={"100%"}>
        <Button
          size="sm"
          variant="outline"
          onClick={() => {
            window.location.href = `/spil/${item.id}`;
          }}
        >
          Se detaljer
        </Button>
        <Button
          size="sm"
          onClick={() => {
            Api.addGameToUser(item.id);

            setGames(games.filter((game) => game.id !== item.id));
          }}
        >
          Tilføj spil
        </Button>
      </SimpleGrid>
    ) : (
      <></>
    );
  };

  // Definering af featuredGames med de første 5 spil
  const featuredGames = games.slice(0, 5).map((item) => {
    return (
      <Carousel.Slide key={item.id}>
        <GameCardLarge game={item} />
      </Carousel.Slide>
    );
  });

  // Definering af gridElements med spil
  const gridElements = games.map((item) => (
    <Paper
      key={item.id}
      shadow="md"
      withBorder
      radius="lg"
      p="lg"
    >
      <Flex direction={"column"} h={"100%"}>
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
          {item.genres.join(", ")}
        </Text>
        <Text fz="sm" mt="xs">
          {item.description}
        </Text>
        {item.tags.length > 0 ? (
          <>
            <Divider mt="md" labelPosition="left" label="Tags" />
            <Flex wrap={"wrap"} gap="md" pt={"md"}>
              {item.tags.map((tag) => (
                <Pill key={tag} color="blue" style={{ marginRight: 5 }}>
                  {tag}
                </Pill>
              ))}
            </Flex>
            <Divider mt="xs" />
          </>
        ) : null}

        <Box style={{ flexGrow: 1 }} />

        <Group mt="xs">{user && <>{addGameButton(item)}</>}</Group>
      </Flex>
    </Paper>
  ));

  // Hvis loading er true, vises en loader
  if (loading)
    return (
      <Center pt={"xl"}>
        <Loader />
      </Center>
    );

  // Returnering af komponenten
  return (
    <>
      <Box w="100%" bg={theme.colors[theme.primaryColor][1]} py="lg">
        <Carousel
          withIndicators
          slideSize={"32%"}
          slideGap={"xl"}
          p="md"
          align="start"
          loop
          slidesToScroll={3}
          style={{ overflow: "visible" }}
        >
          {featuredGames}
        </Carousel>
      </Box>
      <Box
        bg={theme.colors[theme.primaryColor][0]}
        p={"md"}
        style={{
          position: "sticky",
          top: 0,
        }}
      >
        <Title order={4}>Filtrer</Title>
        <Flex gap={"md"} direction={"row"}>
          <Input
            placeholder="Søg efter spil"
            style={{
              flexGrow: 3,
            }}
            onChange={(e) => {
              setSearch(e.currentTarget.value);
            }}
          />
          <MultiSelect
            style={{
              flexGrow: 1,
            }}
            data={[
              "Action",
              "Adventure",
              "Casual",
              "Indie",
              "MassivelyMultiplayer",
              "Racing",
              "RPG",
              "Simulation",
              "Sports",
              "Strategy",
            ]}
            placeholder="Genres"
            onChange={(value) => {
              setGenres(value);
            }}
          />
          <TagsInput
            style={{
              flexGrow: 1,
            }}
            placeholder="Tags"
            value={tags}
            onChange={(value) => {
              setTags(value);
            }}
          />
        </Flex>
      </Box>
      <Box>
        <SimpleGrid p="md" cols={4}>
          {gridElements}
        </SimpleGrid>
      </Box>
    </>
  );
}
