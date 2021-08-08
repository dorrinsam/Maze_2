package maze;

import java.util.ArrayList;
import java.util.Collections;

class DFS implements MazeGenerator {
    private Node[][] maze;
    private final int width, height;

    DFS(int height, int width) {
        this.width = width;
        this.height = height;
        maze = new Node[this.height][this.width];
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                maze[i][j] = new Node(j, i);
            }
        }
    }

    private void dfs(int x, int y, int prevX, int prevY) {
        if (!canVisit(x, y)) {
            return;
        }
        Node node = maze[y][x];
        maze[y][x].previous = maze[prevY][prevX];
        visit(node.x, node.y);

        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(Direction.right);
        directions.add(Direction.up);
        directions.add(Direction.down);
        directions.add(Direction.left);

        Collections.shuffle(directions);

        for (int i = 0; i < 4; i++) {
            Direction direction = directions.get(i);
            if (direction.equals(Direction.right)) dfs(x + 2, y, x, y);
            else if (direction.equals(Direction.up)) dfs(x, y - 2, x, y);
            else if (direction.equals(Direction.down)) dfs(x, y + 2, x, y);
            else dfs(x - 2, y, x, y);
        }
    }

    private void visit(int x, int y) {
        if (maze[y][x].previous != null) {
            int wx, wy;
            wx = (maze[y][x].previous.x + x) / 2; // wall x
            wy = (maze[y][x].previous.y + y) / 2; // wall y
            maze[wy][wx].setWall(false);
        }
        maze[y][x].visited = true;
        maze[y][x].setWall(false);
    }

    private boolean canVisit(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height && !maze[y][x].visited;
    }

    private int[][] getMaze() {
        int out[][] = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == height - 1 && j == width - 1) {
                    out[i][j] = 0;
                } else if (maze[i][j].isWall()) {
                    out[i][j] = 1;
                } else {
                    out[i][j] = 0;
                }
            }
        }
        return out;
    }

    public int[][] generate() {
        dfs(0, 0, 0, 0);
        return getMaze();
    }
}
