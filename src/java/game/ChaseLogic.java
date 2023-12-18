package game;

import java.util.*;

public class ChaseLogic {

    public static List<Position> findPath(GameObject[][] map, Position start, Position finish) {
        int size = map.length;
        boolean[][] visited = new boolean[size][size];
        Position[][] parent = new Position[size][size];

        Queue<Position> queue = new LinkedList<>();
        queue.add(start);
        visited[start.getY()][start.getX()] = true;

        while (!queue.isEmpty()) {
            Position current = queue.poll();

            if (current.isEquals(finish)) {
                return reconstructPath(parent, current);
            }

            for (Position neighbor : getNeighbors(map, current)) {
                int x = neighbor.getX();
                int y = neighbor.getY();
                if (isValidPosition(x, y, size, map) && !visited[y][x]) {
                    queue.add(neighbor);
                    visited[y][x] = true;
                    parent[y][x] = current;
                }
            }
        }
        return null;
    }

    private static List<Position> getNeighbors(GameObject[][] map, Position position) {
        List<Position> neighbors = new ArrayList<>();
        int size = map.length;
        int x = position.getX();
        int y = position.getY();

        if (isValidPosition(x - 1, y, size, map)) neighbors.add(new Position(x - 1, y));
        if (isValidPosition(x + 1, y, size, map)) neighbors.add(new Position(x + 1, y));
        if (isValidPosition(x, y - 1, size, map)) neighbors.add(new Position(x, y - 1));
        if (isValidPosition(x, y + 1, size, map)) neighbors.add(new Position(x, y + 1));

        return neighbors;
    }

    private static boolean isValidPosition(int x, int y, int size, GameObject[][] map) {
        return x >= 0 && x < size && y >= 0 && y < size && map[y][x].getType() != Game.objType.WALL && map[y][x].getType() != Game.objType.ENEMY && map[y][x].getType() != Game.objType.TARGET;
    }

    private static List<Position> reconstructPath(Position[][] parent, Position current) {
        List<Position> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = parent[current.getY()][current.getX()];
        }
        Collections.reverse(path);
        path.remove(0);
        return path;
    }

    public static Position findFirstStep(GameObject[][] map, Position start, Position finish) {
        List<Position> path = findPath(map, start, finish);
        Position firstStep = null;
        if (path != null) {
            firstStep = path.get(0);
        }
        return firstStep;
    }

}
