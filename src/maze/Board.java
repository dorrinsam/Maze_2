package maze;

class Board {
    private int[][] boardState;

    Board(MazeGenerator generator) {
        boardState = generator.generate();
    }

    int[][] getBoardState() {
        return boardState;
    }
}
