#include "raylib.h"

int main()
{
    // Window dimensions
    int width{800};
    int height{450};

    // circle properties
    int circle_x{200};
    int circle_y{200};
    int circle_radius{25};
    int circle_speed{5};
    // circle edges
    int l_circle_x{circle_x - circle_radius};
    int r_circle_x{circle_x + circle_radius};
    int u_circle_y{circle_y - circle_radius};
    int b_circle_y{circle_y + circle_radius};

    // axe properties
    int axe_x{300};
    int axe_y{50};
    int axe_length{50};
    int axe_direction{10};
    // axe edges:
    int l_axe_x{axe_x};
    int r_axe_x{axe_x + axe_length};
    int u_axe_y{axe_y};
    int b_axe_y{axe_y + axe_length};

    bool collison_with_axe = 
                    (b_axe_y >= u_circle_y) &&
                    (u_axe_y <= b_circle_y) &&
                    (r_axe_x >= l_circle_x) &&
                    (l_axe_x <= r_circle_x);

    // game
    int game_fps{60};

    InitWindow(width, height, "Axe Game");

    SetTargetFPS(game_fps);
    while (WindowShouldClose() == false)
    {
        BeginDrawing();
        ClearBackground(WHITE);

        // game logic begins

        if (collison_with_axe)
        {
            DrawText("Game over!", width / 2, height / 2, 64, RED);
        }
        else
        {
            // update edges
            l_circle_x = circle_x - circle_radius;
            r_circle_x = circle_x + circle_radius;
            u_circle_y = circle_y - circle_radius;
            b_circle_y = circle_y + circle_radius;
            l_axe_x = axe_x;
            r_axe_x = axe_x + axe_length;
            u_axe_y = axe_y;
            b_axe_y = axe_y + axe_length;

            // update collision with axe
            collison_with_axe = 
                    (b_axe_y >= u_circle_y) &&
                    (u_axe_y <= b_circle_y) &&
                    (r_axe_x >= l_circle_x) &&
                    (l_axe_x <= r_circle_x);

            // draw figures
            DrawCircle(circle_x, circle_y, circle_radius, BLUE);
            DrawRectangle(axe_x, axe_y, axe_length, axe_length, RED);

            // move the axe

            axe_y += axe_direction;
            if (axe_y > height - axe_length || axe_y < 0)
            {
                axe_direction = -axe_direction;
            }

            // key inpus
            if ((IsKeyDown(KEY_D) || IsKeyDown(KEY_RIGHT)) && circle_x < width - circle_radius)
            {
                circle_x += circle_speed;
            }

            if ((IsKeyDown(KEY_A) || IsKeyDown(KEY_LEFT)) && circle_x > 0 + circle_radius)
            {
                circle_x -= circle_speed;
            }

            if ((IsKeyDown(KEY_W) || IsKeyDown(KEY_UP)) && circle_y > 0 + circle_radius)
            {
                circle_y -= circle_speed;
            }

            if ((IsKeyDown(KEY_S) || IsKeyDown(KEY_DOWN)) && circle_y < height - circle_radius)
            {
                circle_y += circle_speed;
            }
        }

        // game logic ends

        EndDrawing();
    }
}