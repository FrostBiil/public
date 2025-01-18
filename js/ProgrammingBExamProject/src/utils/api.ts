// Importere Game og GameOwner fra Prisma for at strukturere spilinformation korrekt.
import { Game, GameOwner } from "@prisma/client";

// Importerer API_BASEURL og IS_PRODUCTION fra config.ts som bruges til at bestemme API-endepunkter
import { API_BASEURL, IS_PRODUCTION } from "./config";

// Definere klassen Api, som indeholder metoder til at håndtere netværksanmodninger
export class Api {
  // Hjælpemetode til at generere den fulde API endpoint sti baseret på driftsmiljø
  private static getEndpoint(endpoint: string) {
    if (IS_PRODUCTION) {
      /** Produktions-API endpoint findes på samme server */
      return API_BASEURL + endpoint;
    }
    /** Udviklingsendpoint, når to webservere ikke kan køre på samme port */
    return "http://localhost:3000/api" + endpoint;
  }

  // Generisk fetch metode til at håndtere netværksanmodninger
  private static fetch(endpoint: string, method: string = "GET", body?: any) {
    return window.fetch(this.getEndpoint(endpoint), {
      method: method,
      body: typeof body === "string" ? body : JSON.stringify(body),
      headers: {
        "Content-Type": "application/json",
      },
      // Sender cookies afhængig af om koden kører i produktion eller udvikling
      credentials: IS_PRODUCTION ? "same-origin" : "include",
    });
  }

  // Udfører login ved at omdirigere brugeren til autorisationssiden
  public static login(redirectSuccess?: string) {
    const currentUrl = window.location.href;
    const redirect = encodeURIComponent(redirectSuccess || currentUrl);
    window.location.href = this.getEndpoint(`/auth/github?redirect=${redirect}`);
  }

  // Udfører logud ved at kalde backendens logout endpoint
  public static logout() {
    Api.fetch("/auth/logout");
  }

  // Henter den aktuelle bruger fra API'et
  public static async me(): Promise<User | null> {
    const res = await this.fetch("/auth/me");
    if (res.status === 200) {
      return (await res.json()).data as User;
    } else {
      return null;
    }
  }

  // Publicerer et nyt spil til serveren
  public static publishGame(gameData: {
    projectUrl: string;
    title: string;
    description: string;
    visibility: string;
    tags: string[];
    genres: string[];
    cover: string;
    screenshots: string[];
  }) {
    return this.fetch("/games", "POST", gameData);
  }

  // Sletter et spil fra serveren ved spil-ID
  public static deleteGame(id: string) {
    this.fetch(`/games/${id}`, "DELETE");
  }

  // Sletter en bruger
  public static deleteUser() {
    this.fetch(`/users`, "DELETE");
  }

  // Henter alle spil fra serveren baseret på søgekriterier
  public static async getGames(search?: string, genres?: string[], tags?: string[]): Promise<Game[]> {
    const url = new URLSearchParams();
    if (search) url.append("search", search);
    if (genres) url.append("genres", genres.join(","));
    if (tags) url.append("tags", tags.join(","));

    return new Promise((resolve, reject) => {
      this.fetch(`/games?${url.toString()}`)
        .then((res) => {
          if (res.status === 200) {
            return res.json();
          } else {
            reject(res.statusText);
          }
        })
        .then((data) => {
          resolve(data as Game[]);
        });
    });
  }

  // Henter et specifikt spil ved ID
  public static async getGame(id: string): Promise<Game | null> {
    const res = await this.fetch(`/games/${id}`);
    if (res.status === 200) {
      return (await res.json()) as Game;
    } else {
      return null;
    }
  }

  // Tilføjer et spil til en brugers bibliotek
  public static async addGameToUser(gameId: string) {
    return (await this.fetch(`/users/games/${gameId}`, "POST")).json();
  }

  // Fjerner et spil fra en brugers bibliotek
  public static async removeGameFromUser(gameId: string) {
    return await this.fetch(`/users/games/${gameId}`, "DELETE");
  }

  // Henter alle repositories for den aktuelle bruger
  public static async getRepositories(): Promise<string[]> {
    const res = await this.fetch("/users/repositories");
    if (res.status === 200) {
      return (await res.json());
    } else {
      return [];
    }
  }

  // Henter alle spil tilhørende en bruger
  public static async getUserGames(): Promise<GameOwner[]> {
    const res = await this.fetch(`/users/games`);
    if (res.status === 200) {
      return (await res.json());
    } else {
      return [];
    }
  }
}