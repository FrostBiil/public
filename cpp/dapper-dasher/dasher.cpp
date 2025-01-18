#include "raylib.h"

struct AnimData
{
    Rectangle rec;
    Vector2 pos;
    int frame;
    float updateTime;
    float runningTime;
};

bool isOnGround(AnimData data, int windowHeight)
{
    return (data.pos.y >= windowHeight - data.rec.height);
}

AnimData updateAnimData(AnimData data, float deltaTime, int maxFrame)
{
    // Update running time
    data.runningTime += deltaTime;

    if (data.runningTime >= data.updateTime)
    {
        data.runningTime = 0.0;

        // Update animation frame
        data.rec.x = data.frame * data.rec.width;
        data.frame++;

        if (data.frame > maxFrame)
        {
            data.frame = 0;
        }
    }
    return data;
}

void drawBackground(Texture2D background, float bgX)
{
    Vector2 bgPos{bgX, 0.0};
    DrawTextureEx(background, bgPos, 0.0, 2.0, WHITE);
    DrawTextureEx(background, {bgPos.x + background.width * 2, bgPos.y}, 0.0, 2.0, WHITE);
}

float updateBackgroundX(Texture2D background, float x, float speed, float deltaTime)
{
    x -= speed * deltaTime;

    if (x <= -background.width * 2)
    {
        x = 0.0;
    }
    return x;
}

int main()
{
    // Window dimensions
    const int WINDOW_DIMENSIONS[2]{512, 380}; // [0] width, [1] height

    // Game variables
    const int FPS{60};
    const int GRAVITY{1'000}; // gravity (pixel/(s*s))
    bool collision{};

    // Initialize the window
    InitWindow(WINDOW_DIMENSIONS[0], WINDOW_DIMENSIONS[1], "Dapper Dasher");

    // AnimData for nebula
    Texture2D nebula = LoadTexture("textures/12_nebula_spritesheet.png");

    const int SIZE_OF_NEBULAE = 6;
    AnimData nebulae[SIZE_OF_NEBULAE]{};

    for (int i = 0; i < SIZE_OF_NEBULAE; i++)
    {
        nebulae[i].rec = {0.0, 0.0, nebula.width / 8, nebula.height / 8};
        nebulae[i].pos = {WINDOW_DIMENSIONS[0] + i * 300, WINDOW_DIMENSIONS[1] - nebula.height / 8};
        nebulae[i].pos.y = WINDOW_DIMENSIONS[1] - nebula.height / 8;
        nebulae[i].frame = 0;
        nebulae[i].runningTime = 0.0;
        nebulae[i].updateTime = 1.0 / 16.0;
    }

    // nebula x velocity (pixels / second)
    int nebVel{-200};

    float finishLine{nebulae[SIZE_OF_NEBULAE - 1].pos.x};

    // Scarfy variables
    Texture2D scarfy = LoadTexture("textures/scarfy.png");
    AnimData scarfyData;
    scarfyData.rec.width = scarfy.width / 6;
    scarfyData.rec.height = scarfy.height;
    scarfyData.rec.x = 0;
    scarfyData.rec.y = 0;
    scarfyData.pos.x = WINDOW_DIMENSIONS[0] / 2 - scarfyData.rec.width / 2;
    scarfyData.pos.y = WINDOW_DIMENSIONS[1] - scarfyData.rec.height;
    scarfyData.frame = 0;
    scarfyData.updateTime = 1.0 / 12.0;
    scarfyData.runningTime = 0;

    // Is the rectanlge in the air?
    bool isInAir{};
    // Jump velocity (pixels/second)
    const int JUMP_VELOCITY{-600};

    int velocity{};

    Texture2D background = LoadTexture("textures/far-buildings.png");
    float bgX{};

    Texture2D midground = LoadTexture("textures/back-buildings.png");
    float mgX{};

    Texture2D foreground = LoadTexture("textures/foreground.png");
    float fgX{};

    SetTargetFPS(FPS);
    while (!WindowShouldClose())
    {
        // Delta time (time since last frame)
        const float dT{GetFrameTime()};

        // Start drawing
        BeginDrawing();
        ClearBackground(WHITE);

        // Game logic

        // Background x-positions
        bgX = updateBackgroundX(background, bgX, 20, dT);
        mgX = updateBackgroundX(midground, mgX, 40, dT);
        fgX = updateBackgroundX(foreground, fgX, 80, dT);

        // Draw the backgrounds
        drawBackground(background, bgX);
        drawBackground(midground, mgX);
        drawBackground(foreground, fgX);

        // Perform ground check
        if (isOnGround(scarfyData, WINDOW_DIMENSIONS[1]))
        {
            // Rectangle is on the ground
            velocity = 0;
            isInAir = false;
        }
        else
        {
            // Rectangle is in the air
            velocity += GRAVITY * dT;
            isInAir = true;
        }

        // Jump check
        if (IsKeyPressed(KEY_SPACE) && !isInAir)
        {
            velocity += JUMP_VELOCITY;
        }

        // Update nebula position
        for (int i = 0; i < SIZE_OF_NEBULAE; i++)
        {
            nebulae[i].pos.x += nebVel * dT;
        }

        // Update finish line
        finishLine += nebVel * dT;

        // Update scarfy position
        scarfyData.pos.y += velocity * dT;

        // Update animation frame nebula
        for (int i = 0; i < SIZE_OF_NEBULAE; i++)
        {
            nebulae[i] = updateAnimData(nebulae[i], dT, 7);
        }

        // Update animation frame scarfy
        if (!isInAir)
        {
            scarfyData = updateAnimData(scarfyData, dT, 5);
        }

        // Check for collision
        for (AnimData nebula : nebulae)
        {
            float pad{50};
            Rectangle nebRec{
                nebula.pos.x + pad,
                nebula.pos.y + pad,
                nebula.rec.width - 2 * pad,
                nebula.rec.height - 2 * pad};

            Rectangle scarfyRec{
                scarfyData.pos.x,
                scarfyData.pos.y,
                scarfyData.rec.width,
                scarfyData.rec.height};

            if (CheckCollisionRecs(nebRec, scarfyRec))
            {
                collision = true;
            }
        }

        if (collision)
        {
            DrawText("Game Over!", WINDOW_DIMENSIONS[0]/3, WINDOW_DIMENSIONS[1]/2, 20, RED);
        } else if (scarfyData.pos.x >= finishLine) {
            DrawText("You won!", WINDOW_DIMENSIONS[0]/3, WINDOW_DIMENSIONS[1]/2, 20, GREEN);
        }
        else
        {
            // Draw nebula
            for (int i = 0; i < SIZE_OF_NEBULAE; i++)
            {
                DrawTextureRec(nebula, nebulae[i].rec, nebulae[i].pos, WHITE);
            }

            // Draw scarfy
            DrawTextureRec(scarfy, scarfyData.rec, scarfyData.pos, WHITE);
        }

        EndDrawing();
    }
    UnloadTexture(scarfy);
    UnloadTexture(nebula);
    UnloadTexture(background);
    UnloadTexture(midground);
    UnloadTexture(foreground);
    CloseWindow();
}