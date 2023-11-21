package org.axerold;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    List<List<Integer>> wrapToArrayList(int[][] matrix) {
        List<List<Integer>> wrappedMatrix = new ArrayList<>();
        for (var row: matrix) {
            List<Integer> wrappedRow = new ArrayList<>();
            Arrays.stream(row).forEach(wrappedRow::add);
            wrappedMatrix.add(wrappedRow);
        }
        return wrappedMatrix;
    }


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
        var wrappedModifiedMatrix = wrapToArrayList(modifiedMatrix);

        // assert
        int[][] expected = {{0, 7, 0, 5, 0, 0, 0},
                {7, 0, 0, 0, 7, 0, 0},
                {0, 0, 0, 0, 5, 0, 0},
                {5, 0, 0, 0, 0, 6, 0},
                {0, 7, 5, 0, 0, 0, 9},
                {0, 0, 0, 6, 0, 0, 0},
                {0, 0, 0, 0, 9, 0, 0}
        };
        var wrappedExpected = wrapToArrayList(expected);
        assertEquals(wrappedExpected, wrappedModifiedMatrix);
    }
}