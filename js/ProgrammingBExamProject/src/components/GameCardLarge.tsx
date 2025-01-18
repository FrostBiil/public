// Importering af React fra react til at definere komponenter
import { Paper, Flex, Box, Title, Image, Text } from "@mantine/core";

// Importering af useHover fra Mantine hooks til at definere hover effekt
import { useHover } from "@mantine/hooks";

// Importering af Game fra prisma/client til at definere spil
import { Game } from "@prisma/client";

// Definering af GameCardLarge komponenten
export function GameCardLarge(props: { game: Game }) {
    // Definering af ref og hovered med useHover
    const { ref, hovered } = useHover()

    // Returnering af GameCardLarge komponenten
    return <Paper ref={ref} shadow="md" style={{ overflow: "clip" }} withBorder radius="md" >
        <Flex direction={"column"} >
            <Image
                style={{
                    aspectRatio: 16 / 9,
                    transition: "transform 200ms",
                    transform: hovered ? "scale(1.05)" : "scale(1)",
                }}
                src={props.game.cover}
                alt={props.game.title}
            />
            <Box p={"sm"}>
                <Title order={3}>
                    {props.game.title}
                </Title>
                <Text size={"sm"} >
                    {props.game.description}
                </Text>
            </Box>
        </Flex>
    </Paper>
}