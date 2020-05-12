//package com.solutions.codility;

import java.util.*;

//public
class Player {

    public static final int PELLET_SCORE_MULTIPLIER = 10;
    public static final int EMPTY_FLOOR_SCORE = 0;
    public static final int UNVISITED_FLOOR_SCORE = 3;
    public static final int HARD_RECURSION_LIMIT = 30;
    public static final String TYPE_ROCK = "ROCK";
    public static final String TYPE_SCISSORS = "SCISSORS";
    public static final String TYPE_PAPER = "PAPER";

    public enum Direction {UP, DOWN, LEFT, RIGHT}

    static final Player.Direction[] directionSelectionOrder = {Player.Direction.UP, Player.Direction.RIGHT, Player.Direction.DOWN, Player.Direction.LEFT};

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

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    public static class Navigator {
        public static final HashSet<Coord> alreadyVisitedTiles = new HashSet<>();
        public static final HashSet<Coord> nextStepPacs = new HashSet<>();
        public GameMap map;
        public HashMap<Coord, Pac> enemyPacs = new HashMap<>();

        public Navigator(GameMap map) {
            this.map = map;
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
                if (!map.isWallThere(position, d)) {
                    return d;
                }
            }

            //seems like reverse is the only option
            return oppositeDirection(direction);
        }

        public void addEnemyPac(Pac pac) {
            enemyPacs.put(pac.position, pac);
        }

        public void initNextStep() {
            nextStepPacs.clear();
            enemyPacs.clear();
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

    public static class NextJourney {
        public int score = 0;
        public LinkedList<Coord> stepsTrace = new LinkedList<>();

        public NextJourney() {
        }

        public void addStep(Coord position, int score) {
            stepsTrace.addFirst(position);
            this.score += score;
        }
    }

    public static class Pac {
        public Direction direction;
        public Coord position;
        public String type;
        public int speedTurnsLeft;
        public int abilityCoolDown;

        public Pac(Coord position, String typeId) {
            this.position = position;
            this.type = typeId;
        }

        public Pac() {
        }

        public NextJourney traverseRecursively(Coord position, Navigator navigator, HashMap<Coord, Integer> visiblePellets, int localRecursionLimit, HashMap<Coord, Integer> alreadyRecursed, HashSet<Coord> visiblePacs) {
            if (navigator.map.isWallAt(position)
                    || localRecursionLimit == 0
                    || (alreadyRecursed.containsKey(position) && alreadyRecursed.get(position) > localRecursionLimit)
                    || (alreadyRecursed.size() > 0 && visiblePacs.contains(position))) {
                return null;
            } else {

                int scoreForCurrentPosition = visiblePellets.containsKey(position) ? visiblePellets.get(position) * PELLET_SCORE_MULTIPLIER : EMPTY_FLOOR_SCORE;
                if (!Navigator.alreadyVisitedTiles.contains(position)) {
                    scoreForCurrentPosition += UNVISITED_FLOOR_SCORE;
                }

                alreadyRecursed.put(position, localRecursionLimit);

                NextJourney bestNextJourney = null;
                for (Direction d : directionSelectionOrder) {
                    NextJourney tmpScore = traverseRecursively(navigator.map.getCheckedNextStep(position, d), navigator, visiblePellets, localRecursionLimit - 1, alreadyRecursed, visiblePacs);
                    if (bestNextJourney == null || (tmpScore != null && tmpScore.score > bestNextJourney.score)) {
                        bestNextJourney = tmpScore;
                    }
                }

                if (bestNextJourney == null) {
                    bestNextJourney = new NextJourney();
                }

                bestNextJourney.addStep(position, scoreForCurrentPosition);
                return bestNextJourney;
            }
        }

        public NextJourney findNearestSuperPellet(Coord position, Navigator navigator, ArrayList<Coord> visiblePellets, int localRecursionLimit, HashMap<Coord, Integer> alreadyRecursed, HashSet<Coord> visiblePacs) {
            if (navigator.map.isWallAt(position)
                    || localRecursionLimit == 0
                    || (alreadyRecursed.containsKey(position) && alreadyRecursed.get(position) > localRecursionLimit)
                    || (alreadyRecursed.size() > 0 && visiblePacs.contains(position))) {
                return null;
            } else {
                if (visiblePellets.contains(position)) {
                    NextJourney bestNextJourney = new NextJourney();
                    bestNextJourney.addStep(position, 0);
                    return bestNextJourney;
                }

                alreadyRecursed.put(position, localRecursionLimit);

                NextJourney bestNextJourney = null;
                for (Direction d : directionSelectionOrder) {
                    NextJourney tmpScore = findNearestSuperPellet(navigator.map.getCheckedNextStep(position, d), navigator, visiblePellets, localRecursionLimit - 1, alreadyRecursed, visiblePacs);
                    if (bestNextJourney == null || (tmpScore != null && tmpScore.score > bestNextJourney.score)) {
                        bestNextJourney = tmpScore;
                    }
                }

                //noinspection ConstantConditions - that's quirk in linter, findNearestSuperPellet returns null when it hits the wall
                if (bestNextJourney != null) {
                    bestNextJourney.addStep(position, -1);
                }

                return bestNextJourney;
            }
        }


        public boolean beats(Pac other) {
            if (this.type.equals(TYPE_ROCK) && other.type.equals(TYPE_SCISSORS)) {
                return true;
            } else if (this.type.equals(TYPE_SCISSORS) && other.type.equals(TYPE_PAPER)) {
                return true;
            } else if (this.type.equals(TYPE_PAPER) && other.type.equals(TYPE_ROCK)) {
                return true;
            }
            return false;
        }

        private boolean isThatPacToBlockMe(Coord position, Navigator navigator) {
            Pac p = navigator.enemyPacs.get(position);
            if (p != null) {
                return p.beats(this);
            }
            return false;
        }


        public Object nextPelletHuntMove(int id, Navigator navigator, ArrayList<Coord> visibleSuperPellets, HashSet<Coord> visiblePacs) {
            if (abilityCoolDown == 0) {
                return new SpeedCommand(id);
            }

            NextJourney bestNextJourney = findNearestSuperPellet(position, navigator, visibleSuperPellets, HARD_RECURSION_LIMIT, new HashMap<>(), visiblePacs);
            if (bestNextJourney == null || bestNextJourney.stepsTrace.isEmpty()) {
                return nextWander(id, navigator, new HashMap<>(), visiblePacs);
            }

            final LinkedList<Coord> stepsTrace = bestNextJourney.stepsTrace;

            Coord myNextStep;
            if (stepsTrace.size() > 2) {
                myNextStep = bestNextJourney.stepsTrace.get(2);
            } else {
                myNextStep = bestNextJourney.stepsTrace.get(1);
            }

            dumpAllCoords("PacH" + id + " " + bestNextJourney.score + ":", bestNextJourney.stepsTrace);
            return new MoveCommand(id, myNextStep, "H");
        }

        public Object nextWander(int id, Navigator navigator, HashMap<Coord, Integer> visiblePellets, HashSet<Coord> visiblePacs) {
            if (abilityCoolDown == 0) {
                return new SpeedCommand(id);
            }

            NextJourney bestNextJourney = traverseRecursively(position, navigator, visiblePellets, HARD_RECURSION_LIMIT, new HashMap<>(), visiblePacs);

            if (bestNextJourney != null && bestNextJourney.stepsTrace.size() > 1) {
                final LinkedList<Coord> stepsTrace = bestNextJourney.stepsTrace;

                Coord myNextStep;
                if (stepsTrace.size() > 2) {
                    myNextStep = bestNextJourney.stepsTrace.get(2);
                } else {
                    myNextStep = bestNextJourney.stepsTrace.get(1);
                }

                dumpAllCoords("Pac " + id + " " + bestNextJourney.score + ":", bestNextJourney.stepsTrace);
                return new MoveCommand(id, myNextStep, "R");
            }

            if (navigator.map.isWallAt(navigator.map.getCheckedNextStep(position, direction))) {
                direction = navigator.reconsiderDirection(position, direction);
            } else {
                attemptTurnClockwise(navigator);
            }

            move(navigator.map);
            return new MoveCommand(id, position, "N");
        }

        private void dumpAllCoords(String message, Collection<Coord> stepsTrace) {
            List<String> stepsList = new LinkedList<>();
            for (Coord c : stepsTrace) {
                stepsList.add(c.toString());
            }
            System.err.println(message + String.join("->", stepsList));
        }

        private void attemptTurnClockwise(Navigator navigator) {
            final Player.Direction newDirection = navigator.clockwiseDirection(direction);
            if (!navigator.map.isWallThere(position, newDirection)) {
                direction = newDirection;
            }
        }

        private void move(GameMap map) {
            position = map.getCheckedNextStep(position, direction);
        }

        public void positionAndDirect(Coord position, Navigator navigator) {
            this.position = position;
            this.direction = navigator.reconsiderDirection(position, Direction.UP);
        }

        @Override
        public String toString() {
            return "Pac{" +
                    position +
                    ", " + type +
                    ", " + speedTurnsLeft +
                    ", " + abilityCoolDown +
                    '}';
        }
    }

    public static class Game {
        private HashMap<Integer, Pac> myPacsHistory = new HashMap<>();
        private HashMap<Integer, Pac> myPacs = new HashMap<>();
        private HashMap<Coord, Integer> visiblePellets = new HashMap<>();
        private ArrayList<Coord> visibleSuperPellets = new ArrayList<>();
        private HashSet<Coord> visiblePacs = new HashSet<>();
        private Navigator navigator;

        public Game(GameMap map) {
            navigator = new Navigator(map);
        }

        public String step() {
            dumpAllInput();

            LinkedList<String> pacMoves = new LinkedList<>();

            //switch game type - GOLD-HUNT when there are expensive pellets and WANDER when there are nothing visible
            if (!visibleSuperPellets.isEmpty()) {
                //HUNT
                for (Map.Entry<Integer, Pac> p : myPacs.entrySet()) {
                    pacMoves.add(p.getValue().nextPelletHuntMove(p.getKey(), navigator, visibleSuperPellets, visiblePacs).toString());
                }
            } else {
                //                WANDER
                for (Map.Entry<Integer, Pac> p : myPacs.entrySet()) {
                    pacMoves.add(p.getValue().nextWander(p.getKey(), navigator, visiblePellets, visiblePacs).toString());
                }
            }


            return String.join(" | ", pacMoves);
        }

        private void dumpAllInput() {
            if (!navigator.enemyPacs.isEmpty()) {
                List<String> enemyPacsList = new LinkedList<>();
                for (Pac p : navigator.enemyPacs.values()) {
                    enemyPacsList.add(p.toString());
                }
                System.err.println("Pacs:" + String.join(" | ", enemyPacsList));
            }

            if (!visiblePellets.isEmpty()) {
                List<String> visiblePelletStrings = new LinkedList<>();
                for (Map.Entry<Coord, Integer> e : visiblePellets.entrySet()) {
                    visiblePelletStrings.add(e.getKey() + "=" + e.getValue());
                }
                System.err.println("Pellets:" + String.join(" | ", visiblePelletStrings));
            }


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
                Navigator.alreadyVisitedTiles.add(position);
            } else {
                navigator.addEnemyPac(new Pac(position, typeId));
            }
        }

        private Pac createNewPacWithProperDirection(Coord position) {
            final Pac pac = new Pac();
            pac.positionAndDirect(position, navigator);
            return pac;
        }

        public void setVisiblePellet(int x, int y, int value) {
            final Coord coord = new Coord(x, y);
            visiblePellets.put(coord, value);
            if (value > 2) {
                visibleSuperPellets.add(coord);
            }
        }

        public void initNextStep() {
            myPacs.clear();
            visiblePellets.clear();
            visiblePacs.clear();
            navigator.initNextStep();
            visibleSuperPellets.clear();
        }
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int width = in.nextInt(); // size of the grid
        int height = in.nextInt(); // top left corner is (x=0, y=0)
        if (in.hasNextLine()) {
            in.nextLine();
        }

        Game game;

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

            game = new Game(map);
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
                game.setVisiblePellet(x, y, value * value);
            }

            System.out.println(game.step());
        }
    }
}