import java.util.*;

/**
 * Grab the pellets as fast as you can!
 **/
class Player {

    public static final int PELLET_SCORE_MULTIPLIER = 10;
    public static final int EMPTY_FLOOR_SCORE = 0;
    public static final int UNVISITED_FLOOR_SCORE = 1;
    public static final int MAX_AFFORDABLE_RECURSION_LIMIT = 30;

    public enum Direction {UP, DOWN, LEFT, RIGHT}

    static final Player.Direction[] directionSelectionOrder = {Player.Direction.UP, Player.Direction.RIGHT, Player.Direction.DOWN, Player.Direction.LEFT};

    public static final HashSet<Coord> alreadyVisitedTiles = new HashSet<>();
    public static final HashSet<Coord> nextStepPacs = new HashSet<>();

    public static class Coord {
        public int x;
        public int y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Coord)) return false;
            Coord coord = (Coord) o;
            return x == coord.x &&
                    y == coord.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static class GameMap {
        private final int height;
        private final int width;
        private final Feature[][] map;

        private enum Feature {WALL, FLOOR}

        public GameMap(int width, int height) {
            this.width = width;
            this.height = height;
            map = new Feature[height][width];
        }

        public void addWall(int x, int y) {
            map[y][x] = Feature.WALL;
        }

        public void addFloor(int x, int y) {
            map[y][x] = Feature.FLOOR;
        }

        public Coord getCheckedNextStep(Coord c, Direction direction) {
            Coord result = new Coord(c.x, c.y);
            switch (direction) {
                case UP:
                    result.y--;
                    break;
                case DOWN:
                    result.y++;
                    break;
                case LEFT:
                    result.x--;
                    break;
                case RIGHT:
                    result.x++;
                    break;
                default:
                    //nothing
            }
            result.x = (result.x + width) % width;
            result.y = (result.y + height) % height;
            return result;
        }

        public boolean isWallThere(Coord c, Direction direction) {
            return isWallAt(getCheckedNextStep(c, direction));
        }

        public boolean isWallAt(Coord destination) {
            return map[destination.y][destination.x] == Feature.WALL;
        }

        private Player.Direction oppositeDirection(Player.Direction direction) {
            switch (direction) {
                case UP:
                    return Player.Direction.DOWN;
                case DOWN:
                    return Player.Direction.UP;
                case LEFT:
                    return Player.Direction.RIGHT;
                case RIGHT:
                default:
                    return Player.Direction.LEFT;
            }
        }

        private Player.Direction clockwiseDirection(Player.Direction direction) {
            switch (direction) {
                case UP:
                    return Player.Direction.RIGHT;
                case DOWN:
                    return Player.Direction.LEFT;
                case LEFT:
                    return Player.Direction.UP;
                case RIGHT:
                default:
                    return Player.Direction.DOWN;
            }
        }

        private Direction reconsiderDirection(Coord position, Direction direction) {
            for (Player.Direction d : directionSelectionOrder) {
                if (d == oppositeDirection(direction)) {
                    //we don't want to reverse just like that
                    continue;
                }
                if (!isWallThere(position, d)) {
                    return d;
                }
            }

            //seems like reverse is the only option
            return oppositeDirection(direction);
        }
    }

    public static class MoveCommand {
        private static String commandString = "MOVE";
        private final Coord position;
        private final int id;
        private final String label;

        public MoveCommand(int id, Coord position, String label) {
            this.id = id;
            this.position = position;
            this.label = label;
        }

        @Override
        public String toString() {
            return commandString + " " + id + " " + position.x + " " + position.y + " " + label;
        }
    }

    public static class SpeedCommand {
        private static String commandString = "SPEED";
        private final int id;

        public SpeedCommand(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return commandString + " " + id;
        }
    }

    public static class DirectionScore {
        public Coord target;
        public int score;

        public DirectionScore(Coord target, int score) {
            this.target = target;
            this.score = score;
        }
    }

    public static class Pac {
        public Direction direction;
        public Coord position;
        private int recursionLimit = MAX_AFFORDABLE_RECURSION_LIMIT / 2;

        public Pac(Coord position, String typeId) {
            this.position = position;
            this.type = typeId;
        }

        public Pac() {
        }

        //returns the score for the direction
        public DirectionScore traverseRecursively(Coord position, GameMap map, HashMap<Coord, Integer> visiblePellets, int localRecursionLimit, HashMap<Coord, Integer> alreadyRecursed, HashSet<Coord> visiblePacs) {
            if (map.isWallAt(position)
                    || localRecursionLimit == 0
                    || (alreadyRecursed.containsKey(position) && alreadyRecursed.get(position) > localRecursionLimit)
                    || visiblePacs.contains(position)) {
                return new DirectionScore(position, 0);
            } else {
                int scoreForCurrentPosition = visiblePellets.containsKey(position) ? visiblePellets.get(position) * PELLET_SCORE_MULTIPLIER : EMPTY_FLOOR_SCORE;
                if (!alreadyVisitedTiles.contains(position)) {
                    scoreForCurrentPosition += UNVISITED_FLOOR_SCORE;
                }
                alreadyRecursed.put(position, localRecursionLimit);

                DirectionScore maxDirectionScore = new DirectionScore(position, 0);
                for (Direction d : directionSelectionOrder) {
                    DirectionScore tmpScore = traverseRecursively(map.getCheckedNextStep(position, d), map, visiblePellets, localRecursionLimit - 1, alreadyRecursed, visiblePacs);
                    if (tmpScore.score > maxDirectionScore.score) {
                        maxDirectionScore = tmpScore;
                    }
                }
                maxDirectionScore.score += scoreForCurrentPosition;
                return maxDirectionScore;
            }
        }

        public Object nextMove(int id, GameMap map, HashMap<Coord, Integer> visiblePellets, HashSet<Coord> visiblePacs) {
            if (abilityCoolDown == 0) {
                return new SpeedCommand(id);
            }

            recursionLimit = Math.min(recursionLimit - 1, MAX_AFFORDABLE_RECURSION_LIMIT - 1);
            DirectionScore maxDirectionScore = new DirectionScore(map.getCheckedNextStep(position, direction), 0);

            while (maxDirectionScore.score == 0 && recursionLimit < MAX_AFFORDABLE_RECURSION_LIMIT) {
                for (Direction d : directionSelectionOrder) {
                    DirectionScore tmpScore = traverseRecursively(map.getCheckedNextStep(position, d), map, visiblePellets, recursionLimit, new HashMap<>(), visiblePacs);
                    if (tmpScore.score > maxDirectionScore.score) {
                        maxDirectionScore = tmpScore;
                        direction = d;
                    }
                }
                if (maxDirectionScore.score == 0 || recursionLimit < 8) {
                    //sad, did not find anything - increase the recursion limit then.
                    recursionLimit *= 2;
                }
            }

            Coord myNextStep = map.getCheckedNextStep(position, direction);

            if (maxDirectionScore.score > 0 && !nextStepPacs.contains(myNextStep)) {
                return new MoveCommand(id, maxDirectionScore.target, "R" + recursionLimit);
            }

            if (map.isWallAt(myNextStep)) {
                direction = map.reconsiderDirection(position, direction);
            } else {
                attemptTurnClockwise(map);
            }

            move(map);
            return new MoveCommand(id, position, "N" + recursionLimit);
        }

        private void attemptTurnClockwise(GameMap map) {
            final Player.Direction newDirection = map.clockwiseDirection(direction);
            if (!map.isWallThere(position, newDirection)) {
                direction = newDirection;
            }
        }

        private void move(GameMap map) {
            position = map.getCheckedNextStep(position, direction);
        }

        public void positionAndDirect(Coord position, GameMap map) {
            this.position = position;
            this.direction = map.reconsiderDirection(position, Direction.UP);
        }

        public String type;
        public int speedTurnsLeft;
        public int abilityCoolDown;
    }

    public static class Game {
        private GameMap map;
        private HashMap<Integer, Pac> enemyPacs = new HashMap<>();
        private HashMap<Integer, Pac> myPacs = new HashMap<>();
        private HashMap<Integer, Pac> myPacsHistory = new HashMap<>();
        private HashMap<Coord, Integer> visiblePellets = new HashMap<>();
        private HashSet<Coord> visiblePacs = new HashSet<>();


        public String step() {
            LinkedList<String> pacMoves = new LinkedList<>();
            for (Map.Entry<Integer, Pac> p : myPacs.entrySet()) {
                pacMoves.add(p.getValue().nextMove(p.getKey(), map, visiblePellets, visiblePacs).toString());
            }
            return String.join(" | ", pacMoves);
        }

        public void addMap(GameMap map) {
            this.map = map;
        }

        public void updatePac(int pacId, boolean mine, int x, int y, String typeId, int speedTurnsLeft, int abilityCoolDown) {
            final Coord position = new Coord(x, y);
            visiblePacs.add(position);
            if (mine) {
                Pac pac;
                if (!myPacsHistory.containsKey(pacId)) {
                    pac = createNewPacWithProperDirection(position);
                } else {
                    pac = myPacsHistory.get(pacId);
                }
                pac.position = position;
                pac.type = typeId;
                pac.speedTurnsLeft = speedTurnsLeft;
                pac.abilityCoolDown = abilityCoolDown;
                myPacs.put(pacId, pac);
                myPacsHistory.put(pacId, pac);
                alreadyVisitedTiles.add(position);
            } else {
                enemyPacs.put(pacId, new Pac(position, typeId));
            }
        }

        private Pac createNewPacWithProperDirection(Coord position) {
            final Pac pac = new Pac();
            pac.positionAndDirect(position, map);
            return pac;
        }

        public void setVisiblePellet(int x, int y, int value) {
            if (value > 2) {
                visiblePellets.put(new Coord(x, y), value);
            }
        }

        public void initNextStep() {
            myPacs.clear();
            visiblePellets.clear();
            visiblePacs.clear();
            nextStepPacs.clear();
        }
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int width = in.nextInt(); // size of the grid
        int height = in.nextInt(); // top left corner is (x=0, y=0)
        if (in.hasNextLine()) {
            in.nextLine();
        }

        Game game = new Game();

        {
            GameMap map = new GameMap(width, height);
            for (int y = 0; y < height; y++) {
                String row = in.nextLine(); // one line of the grid: space " " is floor, pound "#" is wall
                for (int x = 0; x < row.length(); x++) {
                    if (row.charAt(x) == '#') {
                        map.addWall(x, y);
                    } else {
                        map.addFloor(x, y);
                    }
                }
            }

            game.addMap(map);
        }

        // game loop
        while (true) {
            game.initNextStep();
            int myScore = in.nextInt();
            int opponentScore = in.nextInt();
            int visiblePacCount = in.nextInt(); // all your pacs and enemy pacs in sight
            for (int i = 0; i < visiblePacCount; i++) {
                int pacId = in.nextInt(); // pac number (unique within a team)
                boolean mine = in.nextInt() != 0; // true if this pac is yours
                int x = in.nextInt(); // position in the grid
                int y = in.nextInt(); // position in the grid
                String typeId = in.next(); // unused in wood leagues
                int speedTurnsLeft = in.nextInt(); // unused in wood leagues
                int abilityCooldown = in.nextInt(); // unused in wood leagues
                game.updatePac(pacId, mine, x, y, typeId, speedTurnsLeft, abilityCooldown);
            }
            int visiblePelletCount = in.nextInt(); // all pellets in sight

            for (int i = 0; i < visiblePelletCount; i++) {
                int x = in.nextInt();
                int y = in.nextInt();
                int value = in.nextInt(); // amount of points this pellet is worth
                game.setVisiblePellet(x, y, value);
            }

            System.out.println(game.step());
        }
    }
}