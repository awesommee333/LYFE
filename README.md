Lyfe-by Andrew Krapivin

Lyfe is a modification of Conway's Game of Life.
The classic Conway's Game of Life is a zero person game is a grid consisting of cells, each of which can have two state: dead or alive.
The game is run based on generations. At each generation, cells are marked dead or alive according to these rules(adjacent cells are those which are in the eight cell square around a cell):
1. If a living cell has exactly 2 or 3 adjacent cells, it stays alive. Otherwise, it is marked dead.
2. If a dead cell has exactly 3 adjacent cells, it is marked alive.
These processes happen simultaneously and can lead to some pretty cool structures.

Lyfe runs on very similar rules, but you can modify the number of cells to keep a cell alive or to bring a dead cell to life.
Lyfe also has "walls," which are dead cells that cannot come alive. Walls are colored red. They can be toggled on or off.
Color is added to Lyfe grids by the number of living cells next to a cell BEFORE reproduction/generations are concluded.

General features of lyfe:
While placing or destroying cells or walls, you only get one color of living cells and one color for dead cells. Walls stay red.
You can take a screenshot of just the board. It also takes a black and white scaled screenshot to allow you to load it in the program.
You can load a board from an image, either from url or a file.
You can change the colors.
You can toggle whether colors are enabled(if disabled, then you just get one color for cells).
You can either toggle one generation or toggle automatic generations.

Controls:
Left click to kill/resurrect a cell.
Right click to place a wall. Left click to remove a wall.
'+' to zoom in
'-' to zoom out
Arrow keys to move the grid left, right, up down.
Enter to toggle one generation.
'a' to toggle automatic generations.
'h' to take a screenshot.
