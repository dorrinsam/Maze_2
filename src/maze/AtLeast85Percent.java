package maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AtLeast85Percent implements MazeGenerator {
    private final int width, height;

    AtLeast85Percent(int height, int width) {
        this.width = width;
        this.height = height;
    }

    public int[][] generate() {
        Random r = new Random();
        int[][] map = new int[height][width];
        int srcRow = 0;
        int destRow;
        for (int i = 0; i < 4; i++) {
            destRow = i == 3 ? height - 1 : r.nextInt(height);
            int h = height;
            int w = i == 3 ? width - 3 * width / 4 : width / 4;
            int[][] current = generateMap(h, w, srcRow, destRow);
            for (int j = 0; j < h; j++) {
                for (int k = 0; k < w; k++) {
                    map[j][25 * i + k] = current[j][k];
                }
            }
            srcRow = destRow;
        }
        map[0][0] = 0;
        map[height - 1][width - 1] = 0;
        return map;
    }

    private int[][] generateMap(int rowNum, int columnNum, int srcRow, int destRow) {
        int[][] map = new int[rowNum][columnNum];
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < columnNum; j++) {
                map[i][j] = 1;
            }
        }
        Random r = new Random();
        map[srcRow][0] = 0;
        map[destRow][columnNum - 1] = 0;
        final double fillPercent = 85.5;
        final int maxEmptyCells = (int) (rowNum * columnNum * (100 - fillPercent) / 100);
        int emptyCells = 2;
        int currentRow = destRow;
        int currentColumn = columnNum - 1;
        while (maxEmptyCells - emptyCells > Math.abs(currentRow - destRow) + currentColumn + 2) {
            List<Direction> possibleMoves = getPossibleMoves(map, currentRow, currentColumn, true);
            Direction move;
            if (!possibleMoves.isEmpty()) {
                move = chooseRandom(possibleMoves);
                switch (move) {
                    case up:
                        currentRow--;
                        break;
                    case down:
                        currentRow++;
                        break;
                    case left:
                        currentColumn--;
                        break;
                    case right:
                        currentColumn++;
                }
                map[currentRow][currentColumn] = 0;
                emptyCells++;
            } else {
                possibleMoves = getPossibleMoves(map, currentRow, currentColumn, false);
                move = chooseRandom(possibleMoves);
                switch (move) {
                    case up:
                        currentRow--;
                        break;
                    case down:
                        currentRow++;
                        break;
                    case left:
                        currentColumn--;
                        break;
                    case right:
                        currentColumn++;
                }
            }
        }
        while (currentRow != srcRow || currentColumn != 0) {
            if (currentRow == srcRow) {
                currentColumn--;
            } else if (currentColumn == 0) {
                if (srcRow < currentRow) {
                    currentRow--;
                } else {
                    currentRow++;
                }
            } else {
                switch (r.nextInt(2)) {
                    case 0:
                        currentColumn--;
                        break;
                    case 1:
                        if (srcRow < currentRow) {
                            currentRow--;
                        } else {
                            currentRow++;
                        }
                }
            }
            if (map[currentRow][currentColumn] == 1) {
                map[currentRow][currentColumn] = 0;
                emptyCells++;
            }
        }
        while (emptyCells < maxEmptyCells) {
            int selectedRow = r.nextInt(rowNum);
            int selectedColumn = r.nextInt(columnNum);

            if (map[selectedRow][selectedColumn] == 1) {
                map[selectedRow][selectedColumn] = 0;
                emptyCells++;
            }
        }
        return map;
    }

    private Direction chooseRandom(List<Direction> possibleMoves) {
        if (possibleMoves.size() == 1) {
            return possibleMoves.get(0);
        }
        Random r = new Random();
        return possibleMoves.get(r.nextInt(possibleMoves.size()));
    }

    private List<Direction> getPossibleMoves(int[][] map, int row, int column, boolean removeEmpty) {
        List<Direction> result = new ArrayList<>();
        if (isDownPossible(map, row, column, removeEmpty)) {
            result.add(Direction.down);
        }
        if (isUpPossible(map, row, column, removeEmpty)) {
            result.add(Direction.up);
        }
        if (isRightPossible(map, row, column, removeEmpty)) {
            result.add(Direction.right);
        }
        if (isLeftPossible(map, row, column, removeEmpty)) {
            result.add(Direction.left);
        }
        return result;
    }

    private boolean isUpPossible(int[][] map, int row, int column, boolean removeEmpty) {
        return row != 0 && (map[row - 1][column] == 1 || !removeEmpty);
    }

    private boolean isDownPossible(int[][] map, int row, int column, boolean removeEmpty) {
        return row != map.length - 1 && (map[row + 1][column] == 1 || !removeEmpty);
    }

    private boolean isLeftPossible(int[][] map, int row, int column, boolean removeEmpty) {
        return column != 0 && (map[row][column - 1] == 1 || !removeEmpty);
    }

    private boolean isRightPossible(int[][] map, int row, int column, boolean removeEmpty) {
        return column != map[0].length - 1 && (map[row][column + 1] == 1 || !removeEmpty);
    }
}
