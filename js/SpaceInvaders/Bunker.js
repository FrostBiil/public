class Bunker {
    constructor(x = window.innerWidth / 4, y = window.innerHeight - 150) {
        this.x = x;
        this.y = y;
        this.size = createVector(0.055 * window.innerWidth, 0.04 * window.innerHeight);
        this.life = 3;
        this.image = bunkerImages[0];
    }

    // Draw 
    draw() {
        image(this.image, this.x, this.y, this.size.x, this.size.y);
    }

    // On hit
    hit() {
        this.life--;
        console.log(this.life);
        if (this.life === 0) this.destroy();
    }

    destroy() {
        bunkerArray.splice(bunkerArray.indexOf(this), 1); // Remove the bunker from the array
    }
}
