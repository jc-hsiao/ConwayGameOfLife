package com.zipcodeconway;

import java.util.Arrays;

public class ConwayGameOfLife {
    public int worldSize;
    public int[][] world;
    public SimpleWindow sw;

    public ConwayGameOfLife(Integer dimension) {
        sw = new SimpleWindow(dimension);
    }

    public ConwayGameOfLife(Integer dimension, int[][] startmatrix) {
        sw = new SimpleWindow(dimension);
        this.world = startmatrix;
    }

    public static void main(String[] args) {
        int size = 10;
        ConwayGameOfLife sim = new ConwayGameOfLife(size);
        sim.worldSize = size;
        sim.world = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        int[][] endingWorld = sim.simulate(100);

    }

    // Contains the logic for the starting scenario.
    // Which cells are alive or dead in generation 0.
    // allocates and returns the starting matrix of size 'dimension'
    private int[][] createRandomStart(Integer dimension) {
        int[][] m = new int[dimension][dimension];
        for(int i=0; i<dimension ; i++){
            for(int j=0; j<dimension ; j++){
                m[i][j] = (int) (Math.random()*2);
                //System.out.print(m[i][j]+" ");
            }
            //System.out.print("\n");
        }
        return m;
    }

    public int[][] simulate(Integer maxGenerations) {
        int[][] cur = new int[worldSize][];
        for(int i = 0; i < worldSize; i++)
            cur[i] = world[i].clone();

        for(int c=1; c<maxGenerations; c++){
            for(int i=0; i<worldSize; i++){
                for(int j=0; j<worldSize ; j++){
                    cur[i][j] = isAlive(i,j,world);
                }
            }

            sw.display(cur,c+1);
            sw.sleep(20);
            for(int i=0; i<worldSize; i++){
                world[i] = cur[i].clone();
                //for(int j=0; j<worldSize ; j++){
                    //System.out.print(world[i][j]+" ");
                //}
                //System.out.print("\n");
            }
            //System.out.println("-------");


        }
        return world;
    }

    // copy the values of 'next' matrix to 'current' matrix,
    // and then zero out the contents of 'next' matrix
    public void copyAndZeroOut(int [][] next, int[][] current) {
        for(int i=0; i<next[0].length; i++){
            for(int j=0; j<next[0].length ; j++){
                current[i][j] = next[i][j];
                next[i][j]=0;
                //System.out.print(m[i][j]+" ");
            }
            //System.out.print("\n");
        }
    }

    // Calculate if an individual cell should be alive in the next generation.
    // Based on the game logic:
	/*
		Any live cell with fewer than two live neighbours dies, as if by needs caused by underpopulation.
		Any live cell with more than three live neighbours dies, as if by overcrowding.
		Any live cell with two or three live neighbours lives, unchanged, to the next generation.
		Any dead cell with exactly three live neighbours cells will come to life.
	*/
    private int isAlive(int row, int col, int[][] world) {

        int numOfLiveNeighbor = getNeighbor(row,col,world);

        if(world[row][col] == 0) {
            if (numOfLiveNeighbor == 3)
                return 1;
            else
                return 0;
        }

        else if(world[row][col] == 1) {
            if (numOfLiveNeighbor == 2 || numOfLiveNeighbor == 3)
                return 1;
            else
                return 0;
        }
        return  -1;

    }

    public int getNeighbor(int row, int col, int[][] world) {
        int sum = 0;
        //calculates number of alive neighbor
        for (int i = row-1; i <= row+1; i++) {
            for(int j = col-1; j <= col+1; j++) {
                if (    !(i == row && j == col) &&
                        (i >= 0 && i < world[0].length) &&
                        (j >= 0 && j < world[0].length)) {
                    if(world[i][j] == 1)
                        sum++;
                }
            }
        }
        return sum;
    }
}


