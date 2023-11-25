package org.axerold;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Данный класс осуществляет тестирование с помощью модуля JUnit5. <br>
 * Тесты построены по структуре AAA - arrange, act, assert <br>
 * arrange - задаёт массив для дальнейшей обработки <br>
 * act - вызов функции modifyWeightMatrix из класса Main <br>
 * assert - проверка полученной матрицы весов <br>
 * Для своего теста достаточно скопировать первый тест и поменять значения в массивах
 */
class MainTest {

    @Test
    void wikiExample() {
        // arrange
        int[][] initMatrix = {{0, 7, 0, 5, 0, 0, 0},
                {7, 0, 8, 9, 7, 0, 0},
                {0, 8, 0, 0, 5, 0, 0},
                {5, 9, 0, 0, 15, 6, 0},
                {0, 7, 5, 15, 0, 8, 9},
                {0, 0, 0, 6, 8, 0, 11},
                {0, 0, 0, 0, 9, 11, 0}
        };


        // act
        int[][] modifiedMatrix = Main.modifyWeightMatrix(initMatrix, 3);

        // assert
        int[][] expected = {{0, 7, 0, 5, 0, 0, 0},
                {7, 0, 0, 0, 7, 0, 0},
                {0, 0, 0, 0, 5, 0, 0},
                {5, 0, 0, 0, 0, 6, 0},
                {0, 7, 5, 0, 0, 0, 9},
                {0, 0, 0, 6, 0, 0, 0},
                {0, 0, 0, 0, 9, 0, 0}
        };
        assertArrayEquals(modifiedMatrix, expected);
    }

    @Test
    void apparentExample() {
        // arrange
        int[][] initMatrix = {{0, 2, 0, 10, 7, 0},
                {2, 0, 5, 0, 6, 7},
                {0, 5, 0, 0, 0, 9},
                {10, 0, 0, 0, 8, 0},
                {7, 6, 0, 8, 0, 2},
                {0, 7, 9, 0, 2, 0},
        };


        // act
        int[][] modifiedMatrix = Main.modifyWeightMatrix(initMatrix, -1);

        // assert
        int[][] expected = {{0, 2, 0, 0, 0, 0},
                {2, 0, 5, 0, 6, 0},
                {0, 5, 0, 0, 0, 0},
                {0, 0, 0, 0, 8, 0},
                {0, 6, 0, 8, 0, 2},
                {0, 0, 0, 0, 2, 0},
        };
        assertArrayEquals(expected, modifiedMatrix);
    }

    @Test
    void habrExample() {
        // arrange
        int[][] initMatrix = {{0, 7, 8, 0, 0, 0},
                {7, 0, 11, 2, 0, 0},
                {8, 11, 0, 6, 9, 0},
                {0, 2, 6, 0, 11, 9},
                {0, 0, 9, 11, 0, 10},
                {0, 0, 0, 9, 10, 0},
        };

        // act
        int[][] modifiedMatrix = Main.modifyWeightMatrix(initMatrix, -1);

        // assert
        int[][] expected = {{0, 7, 0, 0, 0, 0},
                {7, 0, 0, 2, 0, 0},
                {0, 0, 0, 6, 9, 0},
                {0, 2, 6, 0, 0, 9},
                {0, 0, 9, 0, 0, 0},
                {0, 0, 0, 9, 0, 0},
        };
        assertArrayEquals(expected, modifiedMatrix);
    }

    @Test
    void internetExample() {
        // arrange
        int[][] initMatrix = {{0, 4, 4, 0, 0, 0},
                {4, 0, 2, 0, 0, 0},
                {4, 2, 0, 3, 2, 4},
                {0, 0, 3, 0, 0, 3},
                {0, 0, 2, 0, 0, 3},
                {0, 0, 4, 3, 3, 0},
        };

        // act
        int[][] modifiedMatrix = Main.modifyWeightMatrix(initMatrix, 2);

        // assert
        int[][] expected = {{0, 0, 4, 0, 0, 0},
                {0, 0, 2, 0, 0, 0},
                {4, 2, 0, 3, 2, 0},
                {0, 0, 3, 0, 0, 0},
                {0, 0, 2, 0, 0, 3},
                {0, 0, 0, 0, 3, 0},
        };
        assertArrayEquals(expected, modifiedMatrix);
    }

    @Test
    void forestExample() {
        // arrange
        int[][] initMatrix = {{0, 2, 0, 7, 0, 0, 0},
                {2, 0, 4, 5, 0, 0, 0},
                {0, 4, 0, 9, 0, 0, 0},
                {7, 5, 9, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 5},
                {0, 0, 0, 0, 1, 0, 6},
                {0, 0, 0, 0, 5, 6, 0}
        };


        // act
        int[][] modifiedMatrix = Main.modifyWeightMatrix(initMatrix, 3);

        // assert
        int[][] expected = {{0, 2, 0, 0, 0, 0, 0},
                {2, 0, 4, 5, 0, 0, 0},
                {0, 4, 0, 0, 0, 0, 0},
                {0, 5, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 5},
                {0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 5, 0, 0}
        };
        assertArrayEquals(expected, modifiedMatrix);
    }

    @Test
    void failExampleWrongRowAmount() {
        // arrange
        int[][] initMatrix = {{0, 4, 4, 0, 0, 0},
                {4, 0, 2, 0, 0, 0},
                {4, 2, 0, 3, 2, 4},
                {0, 0, 3, 0, 0, 3},
                {0, 0, 2, 0, 0, 3},
        };

        // act
        int[][] modifiedMatrix = Main.modifyWeightMatrix(initMatrix, 2);

        // assert
        assertNull(modifiedMatrix);
    }

    @Test
    void failExampleWrongColAmount() {
        // arrange
        int[][] initMatrix = {{0, 4, 4, 0, 0},
                {4, 0, 2, 0, 0},
                {4, 2, 0, 3, 2},
                {0, 0, 3, 0, 0},
                {0, 0, 2, 0, 0},
                {0, 0, 4, 3, 3},
        };

        // act
        int[][] modifiedMatrix = Main.modifyWeightMatrix(initMatrix, 2);

        // assert
        assertNull(modifiedMatrix);
    }

    @Test
    void failExampleOrientedGraph() {
        // arrange
        int[][] initMatrix = {{0, 4, 4, 0, 0, 0},
                {0, 0, 2, 0, 0, 0},
                {4, 2, 0, 3, 2, 4},
                {0, 0, 3, 0, 0, 3},
                {0, 0, 2, 0, 0, 3},
                {0, 0, 0, 3, 0, 0},
        };

        // act
        int[][] modifiedMatrix = Main.modifyWeightMatrix(initMatrix, 2);

        // assert
        assertNull(modifiedMatrix);
    }

    @Test
    void failExampleGraphContainsLoops() {
        // arrange
        int[][] initMatrix = {{1, 4, 4, 0, 0, 0},
                {4, 0, 2, 0, 0, 0},
                {4, 2, 5, 3, 2, 4},
                {0, 0, 3, 0, 0, 3},
                {0, 0, 2, 0, 0, 3},
                {0, 0, 4, 3, 3, 9},
        };

        // act
        int[][] modifiedMatrix = Main.modifyWeightMatrix(initMatrix, 2);

        // assert
        assertNull(modifiedMatrix);
    }
}