// Importering af nødvendige komponenter fra react
import { useContext, useEffect, useState } from "react";

// Importering af nødvendige komponenter fra Mantine
import {
  Autocomplete,
  Button,
  Checkbox,
  Container,
  Grid,
  Group,
  MultiSelect,
  Paper,
  Radio,
  TagsInput,
  TextInput,
  useMantineTheme,
} from "@mantine/core";

// Importering af useForm fra @mantine/form
import { useForm } from "@mantine/form";

// Importering af Dropzone til at uploade billeder
import { Dropzone, IMAGE_MIME_TYPE } from "@mantine/dropzone";

// Importering af ImageConverter til at konvertere billeder til base64
import { ImageConverter } from "../utils/imageConverter";

// Importering af Api til at uploade spil
import { Api } from "../utils/api";

// Importering af AuthContext til at tjekke om brugeren er logget ind
import { AuthContext } from "../contexts/AuthProvider";

// Definering af FormValues interface
interface FormValues {
  projectUrl: string;
  title: string;
  description: string;
  tags: string[];
  genres: string[];
  visibility: string;
  termsOfService: boolean[];
}

// Definering af Genres array
const Genres = [
  "Action",
  "Adventure",
  "Casual",
  "Indie",
  "Massively Multiplayer",
  "Racing",
  "RPG",
  "Simulation",
  "Sports",
  "Strategy",
];

// Definering af UploadPage komponenten
export function UploadPage() {
  // Definering af state til at gemme frontbillede
  const [cover, setCoverImage] = useState<string | undefined>(undefined);
  
  // Henter bruger og om bruger er loaded fra AuthContext
  const { user, loaded } = useContext(AuthContext);

  // Definering af state til at gemme skærmbilleder
  const [screenshots, setScreenshots] = useState<string[]>([]);

  // Definering af state til at gemme projekter
  const [projects, setProjects] = useState<string[]>([]);

  // Definering af theme fra useMantineTheme
  const theme = useMantineTheme();

  // Definering af form fra useForm
  const form = useForm<FormValues>({
    initialValues: {
      projectUrl: "",
      title: "",
      description: "",
      tags: [],
      genres: [],
      visibility: "",
      termsOfService: [],
    },

    // Validering af form
    validate: {
      // Validering af projectUrl tjekker om værdien er en URL
      projectUrl: (value) =>
        /^(https?:\/\/)?(www\.)?[-a-zA-Z0-9@:%._~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_~#?&//=]*)$/.test(
          value
        )
          ? null
          : "Ugyldig URL",
      
      // Validering af title tjekker om værdien er tom
      title: (value) => (value.trim() ? null : "Titel er påkrævet"),
      
      // Validering af description tjekker om værdien er tom
      description: (value) => (value.trim() ? null : "Beskrivelse er påkrævet"),
      
      // Validering af tags tjekker om værdien er tom
      genres: (value) =>
        value.length > 0 ? null : "Mindst 1 genre er påkrævet",
      
      // Validering af visibility tjekker om værdien er "Private" eller "Public"
      visibility: (value) =>
        ["Private", "Public"].includes(value)
          ? null
          : "Vælg venligst visibilitet",
    },
  });

  // Definering af formItemsText array
  const formItemsText = [
    {
      withAsterisk: true,
      label: "Titel",
      placeholder: "Mit fede spil",
      id: "title",
      ...form.getInputProps("title"),
    },
    {
      withAsterisk: true,
      label: "Kort beskrivelse",
      placeholder: "Dette spil er fedt, fordi...",
      id: "description",
      ...form.getInputProps("description"),
    },
  ];

  // useEffect hook der køres når komponenten mountes
  useEffect(() => {

    // Hvis bruger ikke er loaded, så returneres der
    if (!loaded) return;

    // Hvis bruger ikke er logget ind, så logges bruger ind
    if (!user) {
      Api.login();
    }

    // Henter projekter fra backend hvis bruger er logget ind
    if (user) {
      Api.getRepositories().then((projects) => {
        setProjects(projects);
      });
    }
  }, [user, loaded]);

  // Returnerer UploadPage komponenten
  return (
    <Container py={"xl"}>
      <Paper shadow="md" p="xl">
        <h2>Upload et nyt projekt</h2>
        <form
          onSubmit={form.onSubmit((values) => {
            if (!cover) {
              alert("Vælg venligst et frontbillede");
              return;
            }
            if (screenshots.length === 0) {
              alert("Vælg venligst mindst et skærmbillede");
              return;
            }

            Api.publishGame({ ...values, cover, screenshots }).finally(() => {window.location.reload()});
          })}
        >
          <Grid>
            <Grid.Col span={7}>

              <Autocomplete pt="md"
                data={projects}
                label="Github URL"
                withAsterisk
                placeholder="https://github.com/user/repo"
                {...form.getInputProps("projectUrl")}
              />

              {formItemsText.map((props) => (
                <TextInput
                  pt="md"
              {...props}
              onChange={(v) => form.setFieldValue(props.id, v.target.value)}
                />
              ))}

              <MultiSelect
                pt="md"
                withAsterisk
                label="Dit spil's genre"
                placeholder="Vælg mindst 1 genre"
                data={Genres}
                onChange={(v) => form.setFieldValue("genres", v)}
                error={form.errors.genres}
              />

              <TagsInput
                label="Tags"
                placeholder="Skriv tag(s)"
                pt="md"
                onChange={(v) => form.setFieldValue("tags", v)}
              />

              <Radio.Group
                name="visibility"
                label="Visibilitet"
                withAsterisk
                pt="md"
                {...form.getInputProps("visibility")}
              >
                <Group mt="xs">
                  <Radio value="Private" label="Privat" />
                  <Radio value="Public" label="Offenlig" />
                </Group>
              </Radio.Group>

              <Checkbox.Group pt="md" withAsterisk label="Vilkår og betingelser">
                <Checkbox
                  pt="md"
                  required
                  value="agree"
                  label="Jeg accepterer at sælge min sjæl til djævelen for at dette spil kan udgives."
                  {...form.getInputProps("termsOfService", { type: "checkbox" })}
                />
              </Checkbox.Group>
            </Grid.Col>
            <Grid.Col span={5}>
              <h5>Frontbillede</h5>
              <p>Opload et billede der repræsentere dit spil</p>
              <Dropzone
                onDrop={(files) =>
                  ImageConverter.convertImageToBase64(files[0]).then((encoded) =>
                    setCoverImage(encoded)
                  )
                }
                accept={IMAGE_MIME_TYPE}
                style={{
                  height: 150,
                  borderColor: "lightgrey",
                  borderStyle: "dotted",
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "center",
                }}
              >
                {cover ? (
                  <img alt="Frontbillede"
                    src={cover}
                    style={{
                      width: 150,
                      height: 150,
                      objectFit: "cover",
                      borderRadius: theme.radius.sm,
                    }}
                  />
                ) : (
                  <Button>Vælg frontbillede</Button>
                )}
              </Dropzone>

              <h5 style={{ marginTop: 20 }}>Skærmbilleder</h5>
              <p>Opload skærmbilleder fra spillet. Anbefalet 3-5.</p>
              <Dropzone
                onDrop={(files) =>
                  Promise.all(
                    files.map((file) => ImageConverter.convertImageToBase64(file))
                  ).then(setScreenshots)
                }
                accept={IMAGE_MIME_TYPE}
                multiple
                style={{
                  height: 150,
                  borderColor: "lightgrey",
                  borderStyle: "dotted",
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "center",
                }}
              >
                {screenshots.length !== undefined ? (
                  <Group>
                    {" "}
                    {screenshots.map((screenshot) => (
                      <img alt="Skærmbilleder"
                        src={screenshot}
                        style={{
                          width: 50,
                          height: 50,
                          objectFit: "cover",
                          borderRadius: theme.radius.sm,
                        }}
                      />
                    ))}
                  </Group>
                ) : (
                  <Button>Vælg skærmbilleder</Button>
                )}
              </Dropzone>
            </Grid.Col>
          </Grid>
          {/*eslint-disable-next-line no-restricted-globals*/}
          <Button fullWidth mt={"lg"} type="submit">Udgiv</Button>
        </form>
      </Paper>
    </Container>
  );
}