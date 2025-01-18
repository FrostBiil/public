// Constant(s)
const anim_change_rate = 20;
const aliens_per_row = 8;
const aliens_space = 30;

// Variable(s)
let aliens_dir = "left";

// Integer(s)
let anim_frame = 0;
let points = 0;

// Boolean(s)
let readyToShoot = true;

// Image(s)
const spaceshipImages = [];
const SquidImages = [];
const CrabImages = [];
const OctopusImages = [];
const bunkerImages = [];
const crabLaserImages = [];
const octopusLaserImages = [];
const squidLaserImages = [];
const laserImages = [];
const UFOImages = [];

const alienLaserImages = {
    Squid: squidLaserImages,
    Crab: crabLaserImages,
    Octopus: octopusLaserImages,
};

// Object(s)
const alienArray = [];
const bulletsArray = [];
const bunkerArray = [];
const spaceshipArray = [];
const ufoArray = [];

// Responsive canvas size
let canvas;
let canvasWidth;
let canvasHeight;

// Load images
function preload() {
    loadImages(spaceshipImages, "Spaceship", 1);
    loadImages(SquidImages, "Squid", 2);
    loadImages(CrabImages, "Crab", 2);
    loadImages(OctopusImages, "Octopus", 2);
    loadImages(bunkerImages, "Bunker", 1)
    loadImages(crabLaserImages, "CrabLaser", 4)
    loadImages(octopusLaserImages, "OctopusLaser", 4)
    loadImages(squidLaserImages, "SquidLaser", 4)
    loadImages(laserImages, "Laser", 1)
    loadImages(UFOImages, "UFO", 1)

}

// function to load images in a loop
function loadImages(array, name, count) {
    for (let i = 0; i < count; i++) {
        const img = loadImage(`Images/${name}/Img${i}.png`);
        if (img) {
            array.push(img);
        } else {
            console.error(`Failed to load image Img${i}.png`);
        }
    }
}



function setup() {
    canvasWidth = window.innerWidth;
    canvasHeight = window.innerHeight;
    canvas = createCanvas(canvasWidth, canvasHeight);
    imageMode(CENTER);

    spaceshipArray.push(new Spaceship(canvasWidth / 2, canvasHeight - 50));

    const alienTypes = [Octopus, Crab, Crab, Squid, Squid];
    const rows = 5;

    for (let i = 0; i < rows; i++) {
        for (let j = 0; j < aliens_per_row; j++) {
            const AlienType = alienTypes[i];
            alienArray.push(new AlienType(aliens_space + j * aliens_space, aliens_space + i * aliens_space));
        }
    }

    // Create 3 bunkers
    for (let i = 0; i < 3; i++) {
        bunkerArray.push(new Bunker(50 + i * (canvasWidth / 4), canvasHeight - 100));
    }
}

function windowResized() {
    canvasWidth = window.innerWidth;
    canvasHeight = window.innerHeight;
    resizeCanvas(canvasWidth, canvasHeight);

    // Adjust positions and sizes of elements based on the new canvas size
    for (let i = 0; i < spaceshipArray.length; i++) {
        spaceshipArray[i].updatePosition(canvasWidth / 2, canvasHeight - 50);
    }

    for (let i = 0; i < bunkerArray.length; i++) {
        bunkerArray[i].updatePosition(50 + i * (canvasWidth / 4), canvasHeight - 100);
    }
}

function draw() {
    background(0);
    displayPoints();
    
    if (frameCount % 30 === 0) {
        if (moveDownAllAliens()) {
            for (let i = 0; i < alienArray.length; i++) {
                alienArray[i].moveDown();
            }
        } else {
            for (let i = 0; i < alienArray.length; i++) {
                alienArray[i].move();
            }
        }
    }

    for (let i = 0; i < alienArray.length; i++) {
        alienArray[i].draw();
    }

    for (let i = 0; i < spaceshipArray.length; i++) {
        spaceshipArray[i].draw();
    }

    animHandler();

    if (keyIsDown(32) && readyToShoot) {
        for (let i = 0; i < spaceshipArray.length; i++) {
            spaceshipArray[i].shoot();
        }
    }

    if (bulletsArray) {
        for (let i = 0; i < bulletsArray.length; i++) {
            bulletsArray[i].draw();
        }
    }

    for (let i = 0; i < bunkerArray.length; i++) {
        bunkerArray[i].draw();
    }

    for (let i = 0; i < ufoArray.length; i++) {
        ufoArray[i].draw();
    }

    if (frameCount % 60 === 0) {
        alienShoot();
    }

    if (frameCount % 600 === 0) {
        console.log("ufo created");
        ufoArray.push(new UFO(0, 50, 1));
    }
}

function animHandler() {
    anim_frame++;
    if (anim_frame % anim_change_rate === 0) {
        for (let i = 0; i < alienArray.length; i++) {
            alienArray[i].animate();
        }
    }
}

function alienShoot() {
    let randomAlien = alienArray[Math.floor(Math.random() * alienArray.length)];
    const alienType = randomAlien.constructor.name;
    const laserImages = alienLaserImages[alienType];
    bulletsArray.push(new Bullet(laserImages, "alien", randomAlien.x, randomAlien.y + randomAlien.size.y / 2, -2));
}

function moveDownAllAliens() {
    for (let i = 0; i < alienArray.length; i++) {
        if ((alienArray[i].x - alienArray[i].speed <= 0 && aliens_dir == "left") || (alienArray[i].x + alienArray[i].size.x + alienArray[i].speed >= canvasWidth && aliens_dir == "right")) {
            if (aliens_dir == "left") {
                aliens_dir = "right";
                return true;
            } else {
                aliens_dir = "left";
                return true;
            }
        }
    }
    return false;
}

function gameOver() {
    for (let i = 0; i < alienArray.length; i++) {
        alienArray.splice(i, 1);
    }

    for (let i = 0; i < bulletsArray.length; i++) {
        bulletsArray.splice(i, 1);
    }

    for (let i = 0; i < bunkerArray.length; i++) {
        bunkerArray.splice(i, 1);
    }

    for (let i = 0; i < spaceshipArray.length; i++) {
        spaceshipArray.splice(i, 1);
    }

    textSize(32);
    text("Game Over", canvasWidth / 3, canvasHeight / 2 - 50);
    text("Points: " + points, canvasWidth / 3, canvasHeight / 2);

    textSize(16);
    text("Press R to restart", canvasWidth / 3, canvasHeight / 2 + 30);

    if (keyIsDown(82)) {
        setup();
    }
}

function displayPoints() {
    textSize(16);
    text("Points: " + points, canvasWidth - 80, 20);
}

function addPoints(amount) {
    points += amount;
}
