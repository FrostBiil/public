import { Genre, User } from '@prisma/client'
import { prisma } from '../../utils/db'
import Router from '../Router'
import AuthRoutes from './auth'

const GENRES = [
    "Action",
    "Adventure",
    "Casual",
    "Indie",
    "MassivelyMultiplayer",
    "Racing",
    "RPG",
    "Simulation",
    "Sports",
    "Strategy"
]

class GameRoute extends Router {
    public baseRoute = '/games'

    public routes() {

        this.router.get("/", (req, res) => {

            const user = req.user;

            const search: string = req.query.search as string || ""
            const genres: Genre[] = (req.query.genres as string || "").split(",").filter(Boolean) as Genre[]
            const tags: string[] = (req.query.tags as string || "").split(",").filter(Boolean) as string[]

            const skip = isNaN(parseInt(req.query.skip as string)) ? 0 : parseInt(req.query.skip as string)
            const take = isNaN(parseInt(req.query.take as string)) ? 10 : parseInt(req.query.take as string)

            prisma.game.findMany({
                skip,
                take,
                orderBy: {
                    owners: {
                        _count: "desc"
                    },
                },

                where: {
                    genres: genres.length > 0 ? {
                        hasSome: genres
                    } : undefined,

                    tags: tags.length > 0 ? {
                        hasSome: tags
                    } : undefined,

                    OR: search.length > 0 ? [
                        {
                            description: {
                                contains: search as string,
                                mode: "insensitive"
                            }
                        },
                        {
                            title: {
                                contains: search as string,
                                mode: "insensitive"
                            }
                        }
                    ] : undefined,

                    NOT: user ? {
                        owners: {
                            some: {
                                userId: (user as User).id
                            }
                        }
                    } : undefined,

                    visibility: "Public"
                }
            }).then((games) => {
                res.json(games)
            }).catch((err) => {
                res.status(500).json(err)
            })
        })

        this.router.post("/", AuthRoutes.protect, (req, res) => {
            const {
                projectUrl,
                title,
                description,
                visibility,
                genres,
                tags,
                cover,
                screenshots
            } = req.body

            console.log(req.body)

            // Validate inputs
            if (typeof projectUrl !== "string") {
                // TODO: Validate project URL

                return res.status(400).json({
                    message: "Project URL must be valid"
                })
            }

            if (typeof title !== "string" || title.length < 3 || title.length > 100) {
                return res.status(400).json({
                    message: "Title must be a string with at least 3 characters and at most 100 characters"
                })
            }

            if (typeof description !== "string" || description.length < 10 || description.length > 1000) {
                return res.status(400).json({
                    message: "Description must be a string with at least 10 characters and at most 1000 characters"
                })
            }

            if (visibility !== "Public" && visibility !== "Private") {
                return res.status(400).json({
                    message: "Visibility must be either 'Public' or 'Private'"
                })
            }

            if (!Array.isArray(genres) || genres.some((genre) => typeof genre !== "string" || !GENRES.includes(genre))) {
                return res.status(400).json({
                    message: "Genres must be an array of strings, where each string is a valid genre",
                    validGenres: GENRES
                })
            }

            if (!Array.isArray(tags) || tags.some((tag) => typeof tag !== "string" || tag.length < 2 || tag.length > 20)) {
                return res.status(400).json({
                    message: "Tags must be an array of strings, where each string is at least 3 characters and at most 20 characters"
                })
            }

            // Cover must either be a valid base64 string
            if (typeof cover !== "string" || (cover.length < 10 && !cover.startsWith("data:image/"))) {
                return res.status(400).json({
                    message: "Cover must be a valid a base64 string"
                })
            }

            // Screenshots must be an array of valid base64 strings
            if (!Array.isArray(screenshots) || screenshots.some((screenshot) => typeof screenshot !== "string" || (screenshot.length < 10 && !screenshot.startsWith("data:image/")))) {
                return res.status(400).json({
                    message: "Screenshots must be an array of valid base64 strings"
                })
            }

            prisma.game.create({
                data: {
                    title,
                    description,
                    visibility,
                    cover,
                    projectUrl,
                    genres: {
                        set: genres
                    },
                    tags: {
                        set: tags
                    },
                    screenshots: {
                        set: screenshots
                    },
                    publisher: {
                        connect: {
                            id: (req.user as User).id
                        }
                    },
                }
            }).then((game) => {
                res.json(game)
            }).catch((err) => {
                res.status(500).json(err)
            })
        })

        this.router.get("/:id", (req, res) => {
            const id = req.params.id
            const user = req.user as User;

            prisma.game.findUnique({
                where: {
                    id,

                    OR: [
                        {
                            visibility: "Public"
                        },
                        {
                            publisherId: user?.id,
                        }
                    ]
                }
            }).then((game) => {
                if (game) {
                    res.json(game)
                } else {
                    res.status(404).json({
                        message: "Game not found"
                    })
                }
            }).catch((err) => {
                res.status(500).json(err)
            })
        })
    }
}

export default new GameRoute()