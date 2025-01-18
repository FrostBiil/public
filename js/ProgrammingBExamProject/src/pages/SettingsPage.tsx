// Importering af 
import { useContext } from "react";

// Importering af nødvendige komponenter fra Mantine Core
import {
  useMantineTheme,
  Group,
  ColorSwatch,
  Paper,
  Container,
  Flex,
  Tooltip,
  Button,
  Divider,
} from "@mantine/core";

// Importering af Api til at slette brugeren
import { Api } from "../utils/api";

// Importering af AuthContext fra AuthProvider til at tjekke om brugeren er logget ind
import { AuthContext } from "../contexts/AuthProvider";

// Definering af SettingsPage komponenten
export function SettingsPage() {
  // Definering af theme fra useMantineTheme
  const theme = useMantineTheme();

  // Definering af bruger fra AuthContext
  const { user } = useContext(AuthContext);

  // Returnering af SettingsPage komponenten
  return (
    <>
      <Container mt={"xl"}>
        <Paper withBorder shadow={"md"} p={"md"}>
          <h3>Indstilinger</h3>

          <Flex direction={"column"}>
            <h4>Tema</h4>
            <Group>
              {Object.keys(theme.colors).map((color) => (
                <Tooltip key={color} label={color}>
                  <ColorSwatch
                    color={theme.colors[color][theme.primaryShade as number]}
                    onClick={() => {
                      window.localStorage.setItem(
                        "mantine-color-scheme",
                        color
                      );
                      // eslint-disable-next-line no-restricted-globals
                      location.reload();
                    }}
                  />
                </Tooltip>
              ))}
            </Group>

            <Divider mt={"xl"} />

            {user && (
              <Button
                mt={"xl"}
                fullWidth
                color={"red"}
                variant="outline"
                onClick={() => {
                  if (
                    // eslint-disable-next-line no-restricted-globals
                    !confirm(
                      "Er du sikker på at du vil slette din konto og alle de spil du har udgivet? Dette kan ikke fortrydes ⚠️"
                    )
                  )
                    return;

                  console.warn("Sletter konto");

                  Api.deleteUser();
                }}
              >
                Slet konto
              </Button>
            )}
          </Flex>
        </Paper>
      </Container>
    </>
  );
}
