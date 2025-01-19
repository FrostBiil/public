# Space Invaders

A brief description of the project.

## Requirements

- A web browser
- Visual Studio Code
- **Live Server** extension for Visual Studio Code (recommended)

## How to Run the Project

1. Clone or download the repository.
2. Open the project folder in **Visual Studio Code**.
3. Install the **Live Server** extension from the Visual Studio Code extensions marketplace if you haven't already.
4. Right-click the `index.html` file in the **Explorer** view and select **Open with Live Server**.
5. Your default web browser will open the project on a local server (e.g., `http://127.0.0.1:5500`).

### Alternative Method

If you prefer not to use the **Live Server** extension, you can use any other method to serve the project locally, such as:
- Python's built-in HTTP server: python -m http.server
- Node.js-based servers like: http-server.
- 
## Why a Live Server is Needed
This project requires a live server to function correctly due to:
- JavaScript features that depend on the HTTP or HTTPS protocol (e.g., fetching resources or working with modules).
