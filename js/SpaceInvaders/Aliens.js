class Alien {
    constructor(x = 0, y = 0) {
        this.x = x;
        this.y = y;
        this.size = createVector(0.04 * window.innerWidth, 0.02 * window.innerHeight);
        this.speed = 10;
        this.verticalSpeed = 0.04 * window.innerHeight;
    }

    // Draw the alien
    draw() {
        image(this.image, this.x, this.y, this.size.x, this.size.y);
    }

    // Move the alien from side to side and down
    move(speed = this.speed) {
        this.speed = speed;

        // Move from left to right
        if (aliens_dir == "right") {
            this.x += speed;
        }
        // Move from right to left
        else if (aliens_dir == "left") {
            this.x -= speed;
        }
    }

    // Move the alien down
    moveDown() {
        this.y += this.verticalSpeed;
    }

    destroy() {
        // Remove the alien from the array
        alienArray.splice(alienArray.indexOf(this), 1);
    }
}

class Squid extends Alien {
    constructor(x = 0, y = 0) {
        super(x, y);
        this.image = SquidImages[0];
    }

    // Animation of the alien
    animate() {
        const currentIndex = SquidImages.indexOf(this.image);
        const nextIndex = (currentIndex + 1) % SquidImages.length;
        this.image = SquidImages[nextIndex];
    }
}

class Crab extends Alien {
    constructor(x = 0, y = 0) {
        super(x, y);
        this.image = CrabImages[0];
    }

    // Animation of the alien
    animate() {
        const currentIndex = CrabImages.indexOf(this.image);
        const nextIndex = (currentIndex + 1) % CrabImages.length;
        this.image = CrabImages[nextIndex];
    }
}

class Octopus extends Alien {
    constructor(x = 0, y = 0) {
        super(x, y);
        this.image = OctopusImages[0];
    }

    // Animation of the alien
    animate() {
        const currentIndex = OctopusImages.indexOf(this.image);
        const nextIndex = (currentIndex + 1) % OctopusImages.length;
        this.image = OctopusImages[nextIndex];
    }
}

