export class Validator {
    public static validateGame(game: {
        title: string;
        description: string;
        visibility: string;
        cover: string;
        projectUrl: string;
        genres: {
            set: string[];
        };
        tags: {
            set: string[];
        };
        screenshots: {
            set: string[];
        };
    }) {

        this.validateString(game.title, 3, 50)
        this.validateString(game.description, 10, 500)

        if (game.visibility !== 'Public' && game.visibility !== 'Private') {
            throw new Error('Invalid visibility')
        }

        this.validateBase64Image(game.cover)
        for (const screenshot of game.screenshots.set) {
            this.validateBase64Image(screenshot)
        }

        this.validateUrl(game.projectUrl)

        return game;
    }

    public static validateUrl(url: string) {
        if (!url.startsWith('http://') && !url.startsWith('https://')) {
            throw new Error('Invalid URL')
        }

        return true;
    }

    public static validateString(str: string, min: number = 0, max: number = Infinity) {
        if (str.length < min || str.length > max) {
            throw new Error(`String length must be between ${min} and ${max}`)
        }

        // Only allow letters, numbers, and spaces
        if (!/^[a-zA-Z0-9 ]+$/.test(str)) {
            throw new Error('Invalid characters in string')
        }

        return true;
    }

    public static validateBase64Image(image: string) {
        if (!image.startsWith('data:image')) {
            throw new Error('Invalid image')
        }

        // Try to decode the base64 string
        const buffer = Buffer.from(image.split(',')[1], 'base64')

        // Check if image is larger than 1920x1080
        if (buffer.length > 1920 * 1080 * 3) {
            throw new Error('Image too large, max size is 1920x1080 pixels')
        }

        // Check if image is a valid image
        return true;
    }
}