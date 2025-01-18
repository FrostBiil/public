import Express, { Application, } from 'express'
import Cors from './utils/cors'
import Routes from './router'
import { IS_PRODUCTION } from './utils/config'
import session from 'express-session'
import { PrismaSessionStore } from '@quixo3/prisma-session-store'
import passport from 'passport'
import { prisma } from './utils/db'
import cookieParser from 'cookie-parser'

/**
 * Singelton-klasse til at oprette og administrere en express server
 */
export default class Server {
    private application: Application
    private port: number | string

    constructor() {
        this.port = process.env.PORT || 3000
        this.application = Express()
    }

    private plugins() {
        this.application.use(Express.urlencoded({ extended: true }))
        this.application.use(cookieParser())
        this.application.use(Express.json({
            limit: '50mb'
        }))
        this.application.use(session({
            secret: process.env.SESSION_SECRET!,
            store: new PrismaSessionStore(prisma as any, {
                checkPeriod: 2 * 60 * 1000, //ms
                dbRecordIdIsSessionId: true,
                dbRecordIdFunction: undefined,
            }),
            cookie: {
                secure: IS_PRODUCTION,
            },
        }))
        this.application.use(passport.session());
        this.application.use(Cors({
            origin: IS_PRODUCTION ? 'https://localhost:3000' : 'http://localhost:5000',
            credentials: true,
        }))
        this.application.use(Routes)
    }

    public run() {
        try {
            this.plugins()
            this.application.listen(this.port, () => {
                console.log(`Server running on port ${this.port}`)
            })
        } catch (err) {
            console.error(err)
            process.exit(1)
        }
    }
}

new Server().run()