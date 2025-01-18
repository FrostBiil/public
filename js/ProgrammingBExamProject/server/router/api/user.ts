import Router from "../Router"
import { prisma } from "../../utils/db";
import AuthRoutes from "./auth";
import { User } from "@prisma/client";

class UserRouter extends Router {
    public baseRoute = '/users'

    public routes() {

        this.router.delete("/", AuthRoutes.protect, async (req, res) => {
            // Slet brugeren og alle dens spil og ejerskaber

            const userId = (req.user as User).id

            console.log("Deleting user", userId)

            await prisma.user.deleteMany({
                where: {
                    id: userId
                }
            })

            res.json({ message: "User deleted" })
        });

        this.router.get("/games", AuthRoutes.protect, async (req, res) => {

            const userId = (req.user as User).id

            res.json(
                await prisma.gameOwner.findMany({
                    where: {
                        userId
                    },
                    include: {
                        game: true,
                    }
                })
            )

        })

        this.router.post("/games/:gameId", AuthRoutes.protect, async (req, res) => {

            const userId = (req.user as User).id
            const gameId = req.params.gameId

            console.log({
                userId,
                gameId
            })

            const user = await prisma.user.findUnique({
                where: { id: userId }
            })

            if (!user) {
                return res.status(404).json({ error: "User not found" })
            }

            const game = await prisma.game.findUnique({
                where: { id: gameId }
            })

            if (!game) {
                return res.status(404).json({ error: "Game not found" })
            }

            const ownsGame = await prisma.gameOwner.findFirst({
                where: {
                    gameId,
                    userId
                }
            })

            if (ownsGame) {
                return res.status(400).json({ error: "Du ejer allerede dette spil" })
            }

            res.json(
                await prisma.gameOwner.create({
                    data: {
                        gameId,
                        userId
                    }
                })
            )
        });

        this.router.delete("/games/:gameId", AuthRoutes.protect, async (req, res) => {

            const userId = (req.user as User).id
            const gameId = req.params.gameId

            const user = await prisma.user.findUnique({
                where: { id: userId }
            })

            if (!user) {
                return res.status(404).json({ error: "User not found" })
            }

            const game = await prisma.game.findUnique({
                where: { id: gameId }
            })

            if (!game) {
                return res.status(404).json({ error: "Game not found" })
            }

            const ownsGame = await prisma.gameOwner.findFirst({
                where: {
                    gameId,
                    userId
                }
            })

            if (!ownsGame) {
                return res.status(400).json({ error: "Du ejer ikke dette spil" })
            }

            res.json(
                await prisma.gameOwner.delete({
                    where: {
                        gameId_userId: {
                            gameId,
                            userId
                        }
                    }
                })
            )

        })

        this.router.get("/repositories", AuthRoutes.protect, async (req, res) => {
            // Use the gitbug api to get tg

            fetch(`https://api.github.com/users/${(req.user as User).username}/repos`, {
                headers: {
                    Authorization: `Bearer ${(req.user as User).accessToken}`
                }
            }).then(async (data) => {
                const repos = await data.json() as { html_url: string }[]
                res.json(repos.map((repo: any) => repo.html_url))
            })
        });
    }
}

export default new UserRouter()