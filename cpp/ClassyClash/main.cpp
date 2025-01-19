#include "raylib.h"
#include "raymath.h"
#include "Character.h"
#include "Prop.h"
#include "Enemy.h"
#include <string>

int main()
{
    // Constants
    const int WINDOW_WIDTH{384};
    const int WINDOW_HEIGHT{384};
    const int FPS{60};
    const float mapScale{4.f};

    // Initialize the window
    InitWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "Classy Clasher");

    // Load textures (Must be after InitWindow)
    Texture2D map = LoadTexture("nature_tileset/WorldMap.png");
    Vector2 mapPos{0.f, 0.f};

    Character knight(WINDOW_WIDTH, WINDOW_HEIGHT);

    Prop props[2]{
        Prop{Vector2{600.f, 300.f}, LoadTexture("nature_tileset/Rock.png")},
        Prop{Vector2{400.f, 500.f}, LoadTexture("nature_tileset/Log.png")},
    };

    Enemy goblin{
        Vector2{1500.f, 500.f},
        LoadTexture("characters/goblin_idle_spritesheet.png"),
        LoadTexture("characters/goblin_run_spritesheet.png")};

    Enemy slime{
        Vector2{300.f, 1200.f},
        LoadTexture("characters/slime_idle_spritesheet.png"),
        LoadTexture("characters/slime_run_spritesheet.png")};

    Enemy* enemies[]{
        &goblin,
        &slime
    };

    for (auto enemy : enemies)
    {
        enemy->setTarget(&knight);
    }

    SetTargetFPS(FPS);
    while (!WindowShouldClose())
    {
        BeginDrawing();
        ClearBackground(WHITE);

        // Game logic starts -------------------------------------------------

        mapPos = Vector2Scale(knight.getWorldPos(), -1.f);

        // Draw map
        DrawTextureEx(map, mapPos, 0.f, mapScale, WHITE);

        for (auto prop : props)
        {
            prop.Render(knight.getWorldPos());
        }

        if (!knight.getAlive())
        {
            DrawText("Game Over!", 55.f, 45.f, 40, RED);
            EndDrawing();
            continue;
        }
        else
        {
            std::string knightHealth{"Health: "};
            knightHealth.append(std::to_string(knight.getHealth()), 0, 5);
            DrawText(knightHealth.c_str(), 55.f, 45.f, 40, RED);
        }

        knight.tick(GetFrameTime());
        // Check map bounds
        if (knight.getWorldPos().x < 0.f ||
            knight.getWorldPos().y < 0.f ||
            knight.getWorldPos().x + WINDOW_WIDTH > map.width * mapScale ||
            knight.getWorldPos().y + WINDOW_HEIGHT > map.height * mapScale)
            knight.undoMovement();

        // Check prop collisions
        for (auto prop : props)
        {
            if (CheckCollisionRecs(knight.getCollisionRec(), prop.getCollisionRec(knight.getWorldPos())))
                knight.undoMovement();
        }

        // Enemy on tick
        for (auto enemy : enemies)
        {
            enemy->tick(GetFrameTime());
        }

        if (IsMouseButtonPressed(MOUSE_LEFT_BUTTON))
        {
            for (auto enemy : enemies)
            {
                if (CheckCollisionRecs(enemy->getCollisionRec(), knight.getWeaponCollisionRec()))
                {
                    enemy->setAlive(false);
                }
            }
        }

        // Game logic ends ---------------------------------------------------

        EndDrawing();
    }
    UnloadTexture(map);
    CloseWindow();
}
