# Connect Four

A simple multiplayer implementation of the popular board game.

The project is split into two parts:
- **Server**: The server side part
- **Client**: The client consisting of an AWT user interface

The server retrieves two connections by two different clients and then starts the game.

## Game Rules
- 2 Players (Yellow & Red)
- Yellow has the first move
- First player to connect four chips either horizontally, vertically or diagonally wins the game
- If neither player can achieve this until the board is full, the game ends in a tie

## Protocol
| Command | Name    | Arguments   | Description          |
|---------|---------|-------------|----------------------|
| `MSG`   | Message | StringArray | Just sends a message |
| `RES`   | Result  | char        | Game result          | 
| `MOV`   | Move    | int, int    | Move on both sides   |
