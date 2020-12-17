// FABRICE KALVANDA
//"The Maze"
////////////////////////////////////////////////////////////////////////////////
import becker.robots.*;

class MazeBot extends RobotSE // <-- THIS IS MADE FROM THE RobotSE CLASS. CHECK THE BECKER LIBRARY FOR NEW METHODS
{
   int totalMoves = 0;
   int moveWest = 0;
   int moveEast = 0;
   int moveSouth = 0;
   int moveNorth = 0;

   //FIVE Instance Variables will be declared and initialized here 
   //one each for totalMoves, movesWest, movesEast, movesSouth, and MovesNorth
   
	public MazeBot(City theCity, int str, int ave, Direction dir, int numThings)
	{
		super(theCity, str, ave, dir, numThings);
	}
   
   public boolean isFacingNorth()
      {
         if (this.getDirection()== Direction.NORTH)
            {
               return true;
            }
            return false;
      }
   
   public boolean isFacingEast()
      {
         return this.getDirection() == Direction.EAST;
      }  
      
   public boolean isFacingWest()
      {
         return this.getDirection() == Direction.WEST;
      }
   
   public boolean isFacingSouth()
      {
         return this.getDirection() == Direction.SOUTH;
      }  
  
    // create a new method here called movesCounted() that will count
   // everything it is supposed to by adding to the instance variables before moving, 
   // and then use that instead of move() in the NavigateMaze() method below
      
      public void movesCounted()
      {
      
         if(this.isFacingNorth())
            {
               moveNorth++;
            }
         
         if(this.isFacingSouth())
            {
               moveSouth++;
            }
         
         if(this.isFacingEast())
            {
               moveEast++;
            }
         
         if(this.isFacingWest())
            {
               moveWest++;
            }     
     }

	public void printEverything()
   	{
                  
         String msg = " Summary\n" +
         "=============================================================\n" +
         "\n The total number of time the robot moved West:" + moveWest +
         "\n The total number of time the robot moved East:" + moveEast +
         "\n The total number of time the robot moved South:" + moveSouth +
         "\n The total number of time the robot moved North:" + moveNorth;
         System.out.println(msg);
   	}

	// The isAtEndSpot() method nelow is what's called a 'helper method' 
   // It exists just to make another command (in this case, NavigateMaze) easier to understand.
	// It does this by replacing some code that otherwise would be in NavigateMaze
	// with it's name, and doing that work here, instead.
	// Declaring it "private" means that only the MazeBot is allowed to call upon it.
	private boolean isAtEndSpot()
	{
		return (this.getAvenue() == 9 && this.getStreet() == 10);	
	}
	
   // THIS IS THE ONE MAIN METHOD WILL DO EVERYTHING (ALTHOUGH IT CAN USE OTHER METHODS 
   // LIKE isAtEndSpot, movesCounted(), printEverything(), ETC)
   public void navigateMaze()
	   {
		// While your robot hasn't yet reached the 'end spot', keep navigating through the Maze and doing its thing
		   while( !this.isAtEndSpot() )
   		{
         if(this.countThingsInBackpack() > 0)
         {
            this.putThing();
         }
            this.turnLeft();
            while(!frontIsClear())
             {
               
               this.turnRight();
            // HERE IS WHERE ALL THE MAGIC HAPPENS!
            // The robot will navigate the Maze until it gets to the End Spot. 	
               
             } 
               this.move();
               
         // THINGS TO CONSIDER: The robot should put a thing down if it has a thing in the packback; even if it runs out
         // of things, then robot should still be able to successfully complete the Maze	
         }
               
      }      
 	              

      // After completing Maze, print total number of spaces moved and how many times robot moved East, South, West, North.
}

//###################################################################################################
// NO NEED TO TOUCH ANYTHING FROM HERE ON DOWN, EXCEPT TO CHANGE NUMBER OF THINGS IN BACKPACK IN MAIN
// The NavigateMaze() method is already set up and called by don the robot down in main
//###################################################################################################
public class Maze extends Object
{
	private static void MakeMaze(City theCity)
	{
		for(int i = 1; i < 11; i++)
		{
			// north wall
			new Wall(theCity, 1, i, Direction.NORTH);

			// Second to north wall
			if (i <= 9)
				new Wall(theCity, 1, i, Direction.SOUTH);

			// Third to north wall
			if (i >= 4)
				new Wall(theCity, 4, i, Direction.SOUTH);

			// south wall
			if (i != 9) // (9, 10, SOUTH), is where the 'exit' is
				new Wall(theCity, 10, i, Direction.SOUTH);

			// west wall
			if( i != 1) // (1,1, WEST) is where the 'entrance' is
				new Wall(theCity, i, 1, Direction.WEST);

			// second to west-most wall
			if (i >= 3 && i < 6)
				new Wall(theCity, i, 6, Direction.WEST);

			// east wall
			new Wall(theCity, i, 10, Direction.EAST);
		}

		// cul-de-sac
		new Wall(theCity, 3, 10, Direction.WEST);
		new Wall(theCity, 3, 10, Direction.SOUTH);

		new Wall(theCity, 2, 8, Direction.WEST);
		new Wall(theCity, 2, 8, Direction.SOUTH);

		new Wall(theCity, 10, 8, Direction.NORTH);
		new Wall(theCity, 10, 9, Direction.EAST);
		new Wall(theCity, 10, 9, Direction.NORTH);
		MakeSpiral(theCity, 8, 9, 3);
		new Wall(theCity, 8, 10, Direction.SOUTH);

		MakeSpiral(theCity, 10, 5, 4);
	}

	public static void MakeSpiral(City theCity, int st, int ave, int size)
	{
		// We start out building the wall northward
		// the walls will be built on the east face of the current
		// intersection
		Direction facing = Direction.EAST;

		while( size > 0)
		{
			int spacesLeft = size;
			int aveChange = 0;
			int stChange = 0;
			switch(facing)
			{
				case EAST:
					stChange = -1;
					break;
				case NORTH:
					aveChange = -1;
					break;
				case WEST:
					stChange = 1;
					break;
				case SOUTH:
					aveChange = 1;
					break;
			}

			while(spacesLeft > 0)
			{
				new Wall(theCity, st, ave, facing);
				ave = ave + aveChange;
				st = st + stChange;
				spacesLeft--;
			}
			// back up one space
			ave = ave - aveChange;
			st = st - stChange;

			switch(facing)
			{
				case EAST:
					facing = Direction.NORTH;
					break;
				case NORTH:
					facing = Direction.WEST;
					size--;
					break;
				case WEST:
					facing = Direction.SOUTH;
					break;
				case SOUTH:
					facing = Direction.EAST;
					size--;
					break;
			}
		}
	}
   
   //###########################################################################################
   // Main Method
   //###########################################################################################
	public static void main(String[] args)
	{
		City calgary = new City(12, 12);
		MazeBot don = new MazeBot(calgary, 1, 1, Direction.EAST, 30); // <-- YOU WILL NEED TO CHANGE THIS FROM ZERO

		Maze.MakeMaze(calgary);

		don.navigateMaze(); // <-- HERE'S WHERE THE NavigateMaze() method is called. NO NEED TO TOUCH AT ALL
        
   }
   
   
} 
