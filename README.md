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

Each segment is build like this:
| Name     | Length                   | Notes                             |
| -------- | ------------------------ | --------------------------------- |
| Length   | 1 byte                   |                                   |
| Id       | 1 byte                   |                                   |
| Data     | (`Length` - 1) \* 1 byte | See the section below for details |

The data field looks like the following, depending on the ID:

### Server to Client

#### Id = 1 | Tile Update

| Name      | Length | Notes                          |
| --------- | ------ | ------------------------------ |
| row       | 1 byte |                                |
| col       | 1 byte |                                |
| new state | 1 byte | 0 = empty, 1 = Yellow, 2 = Red |

#### Id = 2 | Win State

| Name  | Length | Notes                                   |
| ----- | ------ | --------------------------------------- |
| state | 1 byte | 0 = Draw, 1 = Yellow Wins, 2 = Red Wins |


#### Id = 3 | Game Init

| Name       | Length | Notes                               |
|------------| ------ |-------------------------------------|
| ownedState | 1 byte | 1 = You are Yellow, 2 = You are Red |

### Client to Server

#### Id = 1 | Column Select

| Name | Length | Notes |
| ---- | ------ | ----- |
| col  | 1 byte |       |

Note that the client is free to send this whenever it wants, and the server is to handle this appropriately
