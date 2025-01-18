class Spaceship {
    constructor(x = window.innerWidth / 2, y = window.innerHeight - 50) {
        this.x = x;
        this.y = y;
        this.size = createVector(0.04 * window.innerWidth, 0.02 * window.innerHeight);
        this.speed = 5;
        this.direction = 0;
        this.image = spaceshipImages[0];
        this.life = 3;
    }

    // Draw the spaceship
    draw() {
        this.move();
        image(this.image, this.x, this.y, this.size.x, this.size.y);
    }

    // Move the spaceship
    move() {
        if (keyIsDown(LEFT_ARROW) && this.x > 0) {
            this.x -= this.speed;
        }
        if (keyIsDown(RIGHT_ARROW) && this.x < window.innerWidth - this.size.x) {
            this.x += this.speed;
        }
    }

    // Animation of the spaceship
    animate() {
        const currentIndex = spaceshipImages.indexOf(this.image);
        const nextIndex = (currentIndex + 1) % spaceshipImages.length;
        this.image = spaceshipImages[nextIndex];
    }

    // Shoot a bullet
    shoot() {
        readyToShoot = false;
        bulletsArray.push(new Bullet(laserImages, "spaceship", this.x, this.y - this.size.y / 2));
    }

    // On hit
    hit() {
        this.life--;
        if (this.life === 0) {
            spaceshipArray.splice(spaceshipArray.indexOf(this), 1);
            gameOver();
        }
    }
}
