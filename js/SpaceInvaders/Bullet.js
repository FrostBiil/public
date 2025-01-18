class Bullet {
    constructor(image, shooter, x = window.innerWidth / 2, y = window.innerHeight - 50, speed = 0.02 * window.innerHeight) {
        this.x = x;
        this.y = y;
        this.image = image[0];
        this.shooter = shooter;
        this.size = createVector(0.002 * window.innerWidth, 0.008 * window.innerHeight);
        this.speed = speed;
    }

    // Draw the bullet
    draw() {
        this.move();

        if (this.shooter == "spaceship") {
            this.hitAlien();
        }
        else if (this.shooter == "alien") {
            this.hitSpaceship();
        }

        this.hitBunker();

        noStroke();
        fill(255);
        rect(this.x, this.y, this.size.x, this.size.y);
    }

    // Move the bullet
    move() {
        this.y -= this.speed;

        if (this.y < 0) {
            bulletsArray.splice(bulletsArray.indexOf(this), 1);
            readyToShoot = true;
        }
    }

    // Check if the bullet hit an alien
    hitAlien() {
        for (let i = 0; i < alienArray.length; i++) {
            if (dist(this.x, this.y, alienArray[i].x, alienArray[i].y) < alienArray[i].size.y / 2) {
                alienArray[i].destroy()
                bulletsArray.splice(bulletsArray.indexOf(this), 1);
                readyToShoot = true;
                addPoints(100);
            }
        }
    }

    // Check if the bullet hit the spaceship
    hitSpaceship() {
        for (let i = 0; i < spaceshipArray.length; i++) {
            if (dist(this.x, this.y, spaceshipArray[i].x, spaceshipArray[i].y) < spaceshipArray[i].size.y / 2) {
                bulletsArray.splice(bulletsArray.indexOf(this), 1);
                spaceshipArray[i].hit();
            }
        }
    }

    // Check if the bullet hit a bunker
    hitBunker() {
        for (let i = 0; i < bunkerArray.length; i++) {
            if (dist(this.x, this.y, bunkerArray[i].x, bunkerArray[i].y) < bunkerArray[i].size.y) {
                bulletsArray.splice(bulletsArray.indexOf(this), 1);
                bunkerArray[i].hit();

                if (this.shooter == "spaceship") {
                    readyToShoot = true;
                }
            }
        }
    }
}
