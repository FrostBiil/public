import passport from "passport";
import Router from "../Router";
import { Strategy as GithubStrategy } from "passport-github2";
import { prisma } from "../../utils/db";
import { User } from "@prisma/client";


class AuthRoutes extends Router {
    public baseRoute = "/auth";

    constructor() {
        super();

        passport.use(
            new GithubStrategy({
                clientID: process.env.GITHUB_CLIENT_ID!,
                clientSecret: process.env.GITHUB_CLIENT_SECRET!,
                scope: ["user:email", "read:user"],
                callbackURL: "http://localhost:3000/api/auth/github/callback"
            }, function (accessToken: any, refreshToken: any, profile: any, done: (err: any, user: any) => void) {

                const obj: Omit<User, "joinedAt"> = {
                    accessToken,
                    id: profile.id,
                    displayName: profile.displayName || profile._json.name || profile.username,
                    username: profile.username,
                    photo: profile.photos[0].value,
                    email: profile.emails[0].value,
                    company: profile._json.company,
                    location: profile._json.location,
                };

                prisma.user.upsert({
                    where: {
                        id: profile.id
                    },
                    create: obj,
                    update: obj
                }).then(() => {
                    return done(null, profile);
                }).catch((err) => {
                    return done(err, null);
                })
            })
        )

        passport.serializeUser((user: any, done) => {
            done(null, user.id);
        })

        passport.deserializeUser((id: any, done) => {
            prisma.user.findUnique({
                where: { id }
            }).then((user) => {
                done(null, user)
            }).catch((err) => {
                done(err, null)
            })
        })
    }

    protected routes(): void {

        this.router.get("/me", AuthRoutes.protect, (req, res) => {
            /**
             * Returner info om brugeren, hvis brugeren er logget ind
             */
            return res.status(200).json({
                status: 200,
                data: req.user
            })
        })

        this.router.get("/logout", (req, res) => {
            req.session?.destroy((msg) => {
                if (msg)
                    res.redirect("/error?m=" + msg)

                res.redirect("/")
            })
        })

        this.router.get("/github", (req, res) => {
            // Gem hvor brugeren skal redirectes til efter login
            const redirect = req.query.redirect as string;
            res.cookie("redirect", redirect)
            passport.authenticate("github")(req, res);
        });

        this.router.get("/github/callback", passport.authenticate("github"), (req, res) => {
            // Redirect til hvor brugeren var før login, eller til / hvis der ikke er nogen redirect
            const redirect = req.cookies.redirect || "/";
            res.clearCookie("redirect");
            res.redirect(redirect);
        });
    }

    public static protect = (req: any, res: any, next: any) => {
        if (req.user && req.user.id) {
            return next();
        }

        res.status(401).json({
            status: 401,
            message: "Not logged in"
        })
    }

    public protect = (req: any, res: any, next: any) => {
        AuthRoutes.protect(req, res, next);
    }
}

export default new AuthRoutes()