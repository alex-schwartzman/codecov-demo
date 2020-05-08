import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Grab the pellets as fast as you can!
 **/
class Player {

    public static final int ALIEN_LOCATION = -202;


    static class Pac extends Coord {
        public int id;
        public String type;
        public int abilityCoolDown;

        public Pac(int x, int y, int id, String typeId, int abilityCooldown) {
            super(x, y);
            this.id = id;
            this.type = typeId;
            this.abilityCoolDown = abilityCooldown;
        }

        public boolean beats(Pac other) {
            if (this.type.equals("ROCK") && other.type.equals("SCISSORS")) {
                return true;
            } else if (this.type.equals("SCISSORS") && other.type.equals("PAPER")) {
                return true;
            } else if (this.type.equals("PAPER") && other.type.equals("ROCK")) {
                return true;
            }
            return false;
        }
    }


    static class PacBrain {
        public enum Directions {UP, DOWN, LEFT, RIGHT}

        public Directions direction;
        public Coord position;
        public Pac currentState;

        public void initDirection() {
            direction = Directions.UP;
        }

    }

    static class Coord implements Comparable<Coord> {
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
        public int compareTo(Coord o) {
            if (o.x > this.x) {
                return 1;
            }
            if (o.y > this.y) {
                return 1;
            }
            return -1;
        }
    }

    static String[] wallMap;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int width = in.nextInt(); // size of the grid
        int height = in.nextInt(); // top left corner is (x=0, y=0)
        if (in.hasNextLine()) {
            in.nextLine();
        }

        HashSet<Coord> allUnrevealedCorners = new HashSet<>();

        wallMap = new String[height];
        for (int i = 0; i < height; i++) {
            wallMap[i] = in.nextLine(); // one line of the grid: space " " is floor, pound "#" is wall
            for (int j = 0; j < wallMap[i].length(); j++) {
                if (wallMap[i].charAt(j) == ' ') {
                    allUnrevealedCorners.add(new Coord(j, i));
                }
            }
        }
        Map<Integer, PacBrain> pacBrains = new TreeMap<>();

        // game loop
        while (true) {
            LinkedList<Pac> myPacs = new LinkedList<>();
            HashSet<Pac> alienPacs = new HashSet<>();
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
                final Pac pac = new Pac(x, y, pacId, typeId, abilityCooldown);
                if (mine) {
                    myPacs.add(pac);
                } else {
                    alienPacs.add(pac);
                }
            }
            int visiblePelletCount = in.nextInt(); // all pellets in sight
            Set<Coord> allExpensivePellets = new TreeSet<>();
            LinkedList<Coord> allCheapPellets = new LinkedList<>();

            int[][] pelletsMap = new int[height][width];

            for (int i = 0; i < visiblePelletCount; i++) {
                int x = in.nextInt();
                int y = in.nextInt();
                int value = in.nextInt(); // amount of points this pellet is worth
                pelletsMap[y][x] = value;

                final Coord c = new Coord(x, y);
                if (value > 1) {
                    allExpensivePellets.add(c);
                } else {
                    allCheapPellets.add(c);
                }
            }

            for (Coord c : alienPacs) {
                pelletsMap[c.y][c.x] = ALIEN_LOCATION;
            }

            for (Coord c : myPacs) {
                allUnrevealedCorners.remove(new Coord(c.x, c.y));
            }

            for (Pac p : myPacs) {
                if (!pacBrains.containsKey(p.id)) {
                    pacBrains.put(p.id, new PacBrain());
                    pacBrains.get(p.id).initDirection(wallMap);
                }
                pacBrains.get(p.id).currentState = p;
            }

            LinkedList<String> allInstructions = new LinkedList<>();

            for (Pac p : myPacs) {
                //collision avoid mode
//                {
//                    if (isThereAlienPac()) {
//
//                    }
//                    Coord closestAlienPac = findClosestPellet(alienPacs, p);
//                    if (closestAlienPac != null && directlyVisible(closestAlienPac, p)) {
//                        if (((Pac) closestAlienPac).beats(p) && distance(p, closestAlienPac) < 9 && p.abilityCoolDown > 0) {
//                            //dangerous situation - run away
//
//                        }
//                    }
//
//                }
                //chase mode
                {
                    Coord closestAlienPac = findClosestPellet(alienPacs, p);
                    if (closestAlienPac != null && directlyVisible(closestAlienPac, p)) {
                        if (p.beats((Pac) closestAlienPac) && distance(p, closestAlienPac) < 16) {
                            if (p.abilityCoolDown == 0) {
                                allInstructions.add("SPEED " + p.id + " CH" + ((Pac) closestAlienPac).id);
                            } else {
                                allInstructions.add("MOVE " + p.id + " " + closestAlienPac.x + " " + closestAlienPac.y + " CH" + ((Pac) closestAlienPac).id);
                            }
                            continue;
                        }
                        if (!p.beats((Pac) closestAlienPac) && distance(p, closestAlienPac) < 9) {
                            if (p.abilityCoolDown == 0) {
                                allInstructions.add("SWITCH " + p.id + " " + toBeat(((Pac) closestAlienPac).type));
                                continue;
                            }
                        }
                    }
                }
                //collectExpensive
                {
                    Coord c = findClosestPellet(allExpensivePellets, p);
                    if (c != null) {
                        if (p.abilityCoolDown == 0) {
                            allInstructions.add("SPEED " + p.id + " EXP+");
                        } else {
                            allInstructions.add("MOVE " + p.id + " " + c.x + " " + c.y + " EXP");
                        }
                        continue;
                    }
                }
                //wander
                {
                    Coord c = findClosestPellet(allUnrevealedCorners, p);
                    allInstructions.add("MOVE " + p.id + " " + c.x + " " + c.y + " R");
                }
            }

            System.out.println(String.join(" | ", allInstructions));
        }
    }

    private static String toBeat(String type) {
        if (type.equals("ROCK")) {
            return "PAPER";
        }
        if (type.equals("PAPER")) {
            return "SCISSORS";
        }
        return "ROCK";
    }


    private static boolean directlyVisible(Coord one, Coord another) {
        if (one.x == another.x) {
            //check there is no wall along
            int maxY = Math.max(one.y, another.y);
            int minY = Math.min(one.y, another.y);
            for (int i = minY; i < maxY; i++) {
                if (isWall(one.x, i)) {
                    return false;
                }
            }
            return true;
        }
        if (one.y == another.y) {
            //check there is no wall along
            int maxX = Math.max(one.x, another.x);
            int minX = Math.min(one.x, another.x);
            for (int i = minX; i < maxX; i++) {
                if (isWall(i, one.y)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static boolean isWall(int x, int y) {
        return wallMap[y].charAt(x) != ' ';
    }

    static Coord findClosestPellet(Collection<? extends Coord> allPellets, Coord reference) {
        Coord result = null;
        double distance = Double.MAX_VALUE;
        for (Coord c : allPellets) {
            double currentDistance = distance(reference, c);
            if (currentDistance < distance) {
                distance = currentDistance;
                result = c;
            }
        }
        return result;
    }

    private static double distance(Coord reference, Coord c) {
        return Math.pow(c.x - reference.x, 2) + Math.pow(c.y - reference.y, 2);
    }
}