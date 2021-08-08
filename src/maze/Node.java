package maze;

class Node {
    private boolean wall;
    boolean visited;
    Node previous;
    int x;
    int y;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
        wall = true;
        visited = false;
        previous = null;
    }

    boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }
}