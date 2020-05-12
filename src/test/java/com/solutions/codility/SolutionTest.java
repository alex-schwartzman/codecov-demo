package com.solutions.codility;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void testSmallGameCircularRun() {
        List<String> asciiMap = Arrays.asList(
                "########",
                "#      #",
                "#.# ##.#",
                " o####  ",
                "########"
        );

        Player.Game game = initGame(asciiMap);

        assertEquals("MOVE 0 1 1 R", game.step());
    }

    @Test
    public void testShortestPath() {
        List<String> asciiMap = Arrays.asList(
                "########",
                "#      #",
                "# ##*#.#",
                "o ####  ",
                "########"
        );

        Player.Game game = initGame(asciiMap);

        assertEquals("MOVE 0 6 3 R", game.step());
    }


    private Player.Game initGame(List<String> asciiMap) {
        Player.GameMap map = new Player.GameMap(asciiMap.get(0).length(), asciiMap.size());
        Player.Game game = new Player.Game(map);

        int pacCounter = 0;
        for (int y = 0; y < asciiMap.size(); y++) {
            String row = asciiMap.get(y); // one line of the grid: space " " is floor, pound "#" is wall
            for (int x = 0; x < row.length(); x++) {
                final char c = row.charAt(x);
                switch (c) {
                    case '#':
                        map.addWall(x, y);
                        break;
                    case '.':
                        game.setVisiblePellet(x, y, 1);
                        map.addFloor(x, y);
                        break;
                    case '*':
                        game.setVisiblePellet(x, y, 10);
                        map.addFloor(x, y);
                        break;
                    case 'o':
                        game.updatePac(pacCounter, true, x, y, "ROCK", 0, 2);
                        map.addFloor(x, y);
                        break;
                    case 'c':
                        game.updatePac(pacCounter, false, x, y, "ROCK", 0, 2);
                        map.addFloor(x, y);
                        break;
                    default:
                        map.addFloor(x, y);
                }
            }
        }
        return game;
    }

}
