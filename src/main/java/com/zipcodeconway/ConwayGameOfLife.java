package com.zipcodeconway;

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
        int size = 5;
        ConwayGameOfLife sim = new ConwayGameOfLife(size);
        sim.worldSize = size;
        sim.world = sim.createRandomStart(size);
        int[][] endingWorld = sim.simulate(10);

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

        for(int c=0; c<maxGenerations; c++){
            for(int i=0; i<worldSize; i++){
                for(int j=0; j<worldSize ; j++){
                    world[i][j] = isAlive(i,j,world);
                    System.out.print(world[i][j]+" ");
                }
                System.out.print("\n");
            }
            System.out.println("-------");
            sw.display(world,c+1);
            sw.sleep(40);
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
        String[] dir= {"UP", "UPRIGHT", "RIGHT", "DOWNRIGHT", "DOWN", "DOWNLEFT", "LEFT", "LEFTUP"};
        int[] check = goodNeighbors(row,col, world[0].length);
        int thisOne = world[row][col];
        int sum = 0;
        for(int i=0; i<check.length; i++){
            sum += get(dir[check[i]],row,col,world);
        }
        if(thisOne == 1) {
            if (sum < 2)
                return 0;
            else if (sum > 3)
                return 0;
            else if (sum == 2 || sum == 3)
                return 1;
        }else{
            if(sum == 3){
                return 1;
            }
        }
        return 0;
    }

    public int[] goodNeighbors(int row, int col, int dim){
        //String[] dir= {
        // "UP",
        // "UPRIGHT", "RIGHT",
        // "DOWNRIGHT", "DOWN",
        // "DOWNLEFT", "LEFT",
        // "LEFTUP"};

        if(row==0 && col ==0){
            return new int[] {2,3,4};
        }else if (row==dim-1 && col ==0){
            return new int[] {0,1,2};
        }else if (col==dim-1 && row == 0){
            return new int[] {4,5,6};
        }else if (col==dim-1 && row == dim-1){
            return new int[] {6,7,0};
        }else if(row == 0){
            return new int[] {2,3,4,5,6};
        }else if(col== dim-1){
            return new int[] {4,5,6,7,0};
        }else if(col == 0){
            return new int[] {0,1,2,3,4};
        }else if(row == dim-1){
            return new int[] {0,1,2,6,7};
        }else{
            return new int[] {0,1,2,3,4,5,6,7};
        }
    }


    public int get(String s, int row, int col, int[][] mat){
        if(s.equals("UP"))
            return mat[row-1][col];
        else if(s.equals("DOWN"))
            return mat[row+1][col];
        else if(s.equals("RIGHT"))
            return mat[row][col+1];
        else if(s.equals("LEFT"))
            return mat[row][col-1];
        else if(s.equals("UPLEFT"))
            return mat[row-1][col-1];
        else if(s.equals("UPRIGHT"))
            return mat[row-1][col+1];
        else if(s.equals("DOWNLEFT"))
            return mat[row+1][col-1];
        else if(s.equals("DOWNRIGHT"))
            return mat[row+1][col+1];
        return 0;
    }

}


