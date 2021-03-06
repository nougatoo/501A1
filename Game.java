package sourceCode;
/**
 * main.java
 * Created By: Brandon Brien
 * 
 * A game where users can buy things in order to get more money
 * 
 * Blocks are used to purchase special upgrades
 * hopefully they can be brought up in a menu (the choices)
 * 
 * As the user starts to generate more and more money, blocks become
 * harder and harder to generate, so be careful with your spending!
 * 
 * 
 * 
 * TODO next:
 * 		- Add a pause button
 * 			general message: You will not earn money if t he game is paused!
 * 
 * 
 * 
 * 
 *  Long term TODO: 
 *     
 *   - create a stats section!
 *   
 *   - Have special blocks that pop up (with increasing frequency) that let the user click on them
 *     and they receive some kind of bonus!
 *     
 * Last edited: May 25th, 2015
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Game implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1668063172940490679L;

	private Utilities util;

	//Graphic attributes
	private JFrame frame;
	public GamePanel test;
	private WelcomeScreen welcome_screen;
	
	private JButton lemon_button, orange_button, apple_button, grape_button, blueberry_button;
	private JButton upgradeYBlocks_button, upgradeOBlocks_button, upgradeRBlocks_button, upgradePBlocks_button, upgradeBBlocks_button;
	
	public JLabel currMon_label, currRate_label, totalMon_label, lemonP_label, orangeP_label, appleP_label, grapeP_label, blueberryP_label;
	public JLabel numLemon_label, numOranges_label, numApple_label, numGrape_label, numBlueberry_label; //Number of fruit purchased
	public JLabel numYellow_label, numOrange_label, numRed_label, numPurple_label, numBlue_label; //Number of blocks generated label
	public JLabel general_message_label1, general_message_label2, general_message_label3, general_message_label4, general_message_label5;
	
	private int[] numBlock = new int[3]; //Integer rep of all blocks
	public final int MAX_GRID_NUM = 400;
	public final int NUMBER_OF_COLORS = 5;
	public final int NUMBER_OF_FRUITS = 5;
	
	//Non-Graphics Attributes
	private double current_money;
	private double money_rate;
	private double total_money_made;
	
	
	private int game_type;

	
	/* 
	 * Fruit Objects Neded
	 */
	private Fruit lemon;
	private Fruit oranges;
	private Fruit apple;
	private Fruit grape;
	private Fruit blueberry;
	protected Fruit[] fruits = new Fruit[5]; //Change this when if more fruits added
	
	/*
	 * Color blocks that we wanted to use
	 */
	private Block yellow;
	private Block orange;
	private Block red;
	private Block purple;
	private Block blue;
	protected Block[] blocks = new Block[5];
	
	/*
	 * 0 = Lemons
	 * 1 = Oranges
	 */
	private int[][] grid = new int[20][20];
	private double start_time;
	private double end_time;
	private double difference;
	private DecimalFormat money_dFormat = new DecimalFormat("###.##");
	private Point[] empty_Spots = new Point[400];
	private int last_Empty = -1; //Index in empty_Spots that is the last empty (lets me know how many empty spots there are)
	
	public final int WIDTH = 800;
	public final int HEIGHT = 600;
	public final int FIRST_BUTTON = 450;
	
	private MainListener main_listener = new MainListener(this);
	
	/* 
	 * Boost menu stuff
	 */
	private JButton boost_menu_button;
	private BoostMenu boost_menu;
    boolean boost_menu_open = false;    
    private double boost_multiplier;
    private boolean boost_multiplier_on;
	private double boost_start_time;
	private double current_time;
	private int[] boost_durations = new int[3]; //CHANGE WHEN ADD MORE UPGRADES\
	private int boost_durations_index;
	
	
	/*
	 * Upgrade window stuff
	 */
	private UpgradeMenu upgrade_menu;
	private JButton upgrade_menu_button;
	private int[] upgrade_multipliers = new int[2];
	
	private boolean stop_for_save = false;
	
	

	/*
	 * Save game stuff
	 */
	private JButton save_game_button;
	private StatsWindow stats_window;
	
	
	/*
	 * Stats stuff
	 */
	
	private JButton show_stats_button;
	
	
	
	/*
	 * TEST CODE TEST CODE TEST CODE
	 */
	
	private DrawingFunctions drawingFunctions = new DrawingFunctions(this);
	private boolean gamePaused = false;
	
	public Game(int choice)
	{
	}
	
	/**
	 * Initialize game state (non graphics)
	 * @return Boolean
	 */
	public Boolean initialize()
	 {

		/* 
		 * Creating new fruits
		 */
		lemon = new Fruit("Lemons", 2, 1000);
		fruits[0] = lemon;
		
		oranges = new Fruit("Oranges", 10, 20);
		fruits[1] = oranges;
		
		apple = new Fruit("Apples", 100 , 30);
		fruits[2] = apple;
		
		grape = new Fruit("Grapes", 1000 , 40);
		fruits[3] = grape;
		
		blueberry = new Fruit("Blueberries", 10000 , 50);
		fruits[4] = blueberry;
		
		/* 
		 * Creating the Blocks that we want to use
		 */
		
		BlockChangePoints bcp = new BlockChangePoints(2, 1000, 
				1, 10000, 
				100, 10000000, 
				1000, 1000000, 
				10000, 10000000);
		
		yellow = new Block(bcp,100);
		blocks[0] = yellow;

		orange = new Block(bcp,1000);
		blocks[1] = orange;

		red = new Block(bcp,1000);
		blocks[2] = red;

		purple = new Block(bcp,10000);
		blocks[3] = purple;

		blue = new Block(bcp,100000);
		blocks[4] = blue;



		util = new Utilities();
		current_money = 10;
		money_rate = 1;
		total_money_made = current_money;
		System.out.println("Welcome to some shitty incremental game!!\n");
		
		//Initializes our grid to zero
		for(int i = 0;i<20;i++)
		{
			for(int j = 0;j<20;j++)
			{
				grid[i][j] = 0;
			}
		}

		/*
		 * Initializes the entire empty_spots array
		 */
		for(int i = 0;i<400;i++)
		{
			empty_Spots[i] = new Point(0,0);				
		}
		
		
		//Gets current time to so we know how much to add later when we want to calculate
		//how much the user has made
		start_time = System.currentTimeMillis();
		
		//For the Boost system
		boost_multiplier = 1;
		boost_multiplier_on = false;
		boost_durations[0] = 3000;
		boost_durations[1] = 5000;
		boost_durations[2] = 7000;
		boost_durations_index = 0;
		
		//For upgrade systems
		upgrade_multipliers[0] = 3;
		upgrade_multipliers[1] = 5;
		
		return true;
	 }
	
	/*
	 * When the game running is a loaded game, we have some extra work to do
	 */
	public void loadedGame()
	{
		start_time = System.currentTimeMillis();
		
		for(int i = 0;i<400;i++)
		{
			empty_Spots[i] = new Point(0,0);				
		}
		
		updateEmptySpots();
		
		main_listener = new MainListener(this);
		//shuffleEmptySpots();
		
	}
	/*
	 * - Does all the time interval calculations to add to current and total money
	 * - Takes our total money and checks whether we are at a break point for a certain colored
	 * 	 block. If we are, call handleBreakPoint to deal with it
	 */
	public void showStats()
	{
		if(gamePaused == false){
		String temp;
		int[] color_break = new int[NUMBER_OF_COLORS];
		int index;

		//Going to calculate how much money the user has made since we last checked 
		end_time = System.currentTimeMillis();
		difference = end_time-start_time;
		start_time = System.currentTimeMillis();
		current_money += money_rate*(difference/1000);
		total_money_made += money_rate*(difference/1000);
		
		//THIS NEEDS TO CHNAGE WHEN EACH FRUIT GETS THEIR OWN BREAKPOINT
		index = blocks[0].getCurrent_mult_index();

		
		/*
		 * Checks the current time against the time we started the upgrade multiplier
		 * but only if there is a multiplier active
		 */
		current_time = System.currentTimeMillis();
		if(current_time - boost_start_time > boost_durations[boost_durations_index] && boost_multiplier_on == true)
		{
			System.out.println("should not see");
			endUpgradeMultiplier();
		}
		
		
		
		/*
		 * Gets and stores all the different breakpoints for the colors we're using
		 */
		for(int i = 0; i<NUMBER_OF_COLORS;i++)
		{
			color_break[i] = (int)(total_money_made/(blocks[i].getNum_per_dollar()*blocks[i].getMultiplier(index).y)
					);
		}
		
		/*
		 * If we are at a breakpoint, we will call the handlbreakpoint method, if we are 
		 * on the last breakpoint before making a new color, don't color our current one, color one of the 
		 * next blocks
		 */
		for(int i = 0;i<NUMBER_OF_COLORS;i++)
		{
			if(color_break[i] > blocks[i].getBreakpoint() && (blocks[i].getBreakpoint()%100 != 9))
			{
				handleBreakPoint(color_break[i], i, blocks[i].getNum_blocks());
			}
			else if(blocks[i].getBreakpoint()%100 == 9 && (color_break[i]>blocks[i].getBreakpoint()))
			{

				blocks[i].setBreakpoint(blocks[i].getBreakpoint() +1);
			}
		}
		
		//Rounds to the nearest 2
		current_money = Double.valueOf(money_dFormat.format(current_money));
        
        //Updates our current amount of money
		String curr_str = Double.toString(current_money);
        currMon_label.setText("Current money: $" + curr_str);
        
        //Updates the total amount of money made
		total_money_made = Double.valueOf(money_dFormat.format(total_money_made));
		temp = Double.toString(total_money_made);
	    totalMon_label.setText("Total money made: $" + temp);
	    
	    if(stop_for_save == true )
	    {
	    	try {
				saveGame();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		}
	    
	}
	
	/*
	 * Input: none
	 * Work: - Scans the grid[][] array and records all the spots
	 *       that have not been colored yet so that we have a list
	 *       to pick from when the game needs to color a new square
	 *       
	 *       - then shuffles the array because we have no use for an array
	 *       in order. DIdn't want to add another fuction because array shuffling
	 *       is fairly exclusive to only two spots in the all game code
	 *       
	 */
	public void updateEmptySpots()
	{
		last_Empty = -1;
		for(int i = 0;i<20;i++)
		{
			for(int j = 0;j<20;j++)
			{
				if(grid[i][j] == 0)
				{
					last_Empty++;
					empty_Spots[last_Empty].x = i;
					empty_Spots[last_Empty].y = j;
				}
			}
		}
		
		if(last_Empty == -1)
		{
			handleFullGrid();
			updateEmptySpots();
			
		}
		
	}
	
	private void shuffleEmptySpots()
	{
		/*
		 * Pseudo shuffles the array 
		 */
		int index;
		
	    for (int i = (last_Empty); i > 0; i--)
	    {
	    	index = util.getRandInt(last_Empty-1, 0);
	    	// Simple swap
	    	Point a = empty_Spots[index];
	    	empty_Spots[index] = empty_Spots[i];
	    	empty_Spots[i] = a;
	    }
	    
	}
	
	/*
	 * Each colored block has breakpoint, so every $x they earn a colored block of a certain
	 * type. This function gets called when a breakpoint is reached, it does not check
	 * whether we are at a break point
	 */
	public void handleBreakPoint(int currentColorBreak, int breakPointIndex, int numColor)
	{
		int numToColor;
		int colored = 0;

		numToColor = currentColorBreak - blocks[0].getBreakpoint();				
				
		if((numToColor + getNumBlocks()) >= MAX_GRID_NUM) //CHANGE WHEN ADDING MORE FRUIT ///DO GETSIZE
		{
			handleFullGrid();
		}
		
		if (isGridFull() == false)
		{
			int x,y = 0;
			
			/*
			 * We have an in-order array of points that we know are empty in grid
			 * so we shuffle it and pick the last element (because it's shuffled) 
			 * and color that square. Update accordingly.
			 * 
			 */
			updateEmptySpots();
			
		
			do{	
				/*
				 * If there are two or less spots then there isn't a reason
				 * to pick a random spot
				 */
				if(last_Empty > 1)
				{
					shuffleEmptySpots();
				}
				x = empty_Spots[last_Empty].x;
				y = empty_Spots[last_Empty].y;
				last_Empty--;
				
				
				if(last_Empty == -1)
				{
					handleFullGrid();
					updateEmptySpots();
				}
				
	        	grid[x][y] = breakPointIndex+1; 
	        	colored++;
	        	blocks[breakPointIndex].setNum_blocks(blocks[breakPointIndex].getNum_blocks()+1);

			}while(colored < numToColor);
			

	        test.repaint(); //Redraw grid 
	        blocks[breakPointIndex].setBreakpoint(currentColorBreak);
			updateBlockLabels(); //Update block label to show the blocks we just generated
			
		}

	}
	
	public void handleFullGrid()
	{
		for(int i = NUMBER_OF_COLORS;i>0;i--)
		{
			if(blocks[i-1].getNum_blocks() >= 100)
			{
				upgradeBlocks(i-1);	
			}
		}

	}
	
	private boolean isGridFull(){
		
		return (getNumBlocks() >= MAX_GRID_NUM);
	}
	
	private int getNumBlocks()
	{
		int totalBlocks = 0;
		
		for(int i=0;i<NUMBER_OF_COLORS;i++)
		{
			totalBlocks += blocks[i].getNum_blocks();
		}
		return totalBlocks;
	}
	
	public void create_frame()
	{
		//Sets up the frame
		frame = new JFrame("Test Box");
		frame.pack();

		frame.setSize(WIDTH, HEIGHT);
	    frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
	public void create_main_panel()
	{
		util.waitMili(100);
		//Sets up the game panel
		test = new GamePanel(grid);
		test.setLayout(null);
		test.setBackground(Color.BLACK);
			
        frame.add(test);
                 
		drawButtons();
		drawLabels();
				
		util.waitMili(300);
        //Shows everything we need		
		frame.setVisible(true);
		
	}
	
	public void createBoostMenu()
	{
		boost_menu = new BoostMenu();
		boost_menu.createMenu();
	}
	
	public void createUpgradeMenu()
	{
		upgrade_menu = new UpgradeMenu();
		upgrade_menu.createMenu();
	}
	
	public void createStatsWindow()
	{
		stats_window = new StatsWindow();
		stats_window.createWindow();
	}
	
	/*
	 * Input: none
	 * Output: none
	 * Purpose: Creates a welcome screen for the user
	 * 			that will return here when choice tell us
	 * 			what the user wants to do
	 */
	public int drawWelcome(){

		/*
		int choice = 0; 
		
		WelcomeLogic welcome_logic = new WelcomeLogic(frame);
		welcome_logic.createWelcomeScreen();
	
		choice = welcome_logic.waitForChoice();
		return choice;
		*/
		return 0;
	}

	
	/*
	 * Converts as many possible blocks from one to the next
	 * 0 = yellow
	 * 1 = orange
	 * 2 = red
	 * ...etc
	 */
	public void upgradeBlocks(int block)
	{		
		Point[] findColors = new Point[MAX_GRID_NUM];
		Point tempPoint;
		int raw_numBlocks_to_upgrade, numBlocks_to_upgrade;
		int nextEmpty = 0;
		int randomNum;
		int m = 0;
		
		raw_numBlocks_to_upgrade = blocks[block].getNum_blocks();
		numBlocks_to_upgrade = raw_numBlocks_to_upgrade/100; //This will have to change if we want different conversion rates instead of 100 per and lower in the function
				
		blocks[block].setNum_blocks(blocks[block].getNum_blocks() - (numBlocks_to_upgrade*100)); //100 blocks = 1 block of the next kind
		blocks[block+1].setNum_blocks(blocks[block+1].getNum_blocks() + (numBlocks_to_upgrade));
		
		updateBlockLabels();
		//NOw...how to update grid
		//I want to create an array that holds all the yellow points(after traversing through the
		// grid, then i can get a random integer from it changed x amount, then redraw the grid
		if(numBlocks_to_upgrade > 0)
		{
			for(int i=0;i<20;i++)
			{
				for(int j=0;j<20;j++)
				{
					if(grid[i][j] == (block+1)) //In grid 0 = black, 1 = yellow, orange=2, etc...
					{
						findColors[nextEmpty] = new Point(i,j);
						nextEmpty++;
					}
				}
			}
			
			//Pseudo shuffle of the locations of the array so that when we removed we don't always remove
			//the first 100
			int index;
		    for (int i = (nextEmpty -1 ) - 1; i > 0; i--)
		    {
		      index = util.getRandInt(nextEmpty-1, 0);
		      // Simple swap
		      Point a = findColors[index];
		      findColors[index] = findColors[i];
		      findColors[i] = a;
		    }
		    
			
		    /*
		     * Takes the first 100*numBlocks upgraded entries of the shuffle array (locations of colors) and decreases
		     * their value by 1 and takes the last couple (number of colors above current colors) 
		     * and increases their value by 1
		     */			
		    for(int i=0;i<(numBlocks_to_upgrade*100);i++)
			{
	    	
				tempPoint = findColors[i];
				if(i < ((numBlocks_to_upgrade*100)-numBlocks_to_upgrade))
				{
					if(block == 0)
						grid[findColors[i].x][findColors[i].y] += -1;
					else
						grid[findColors[i].x][findColors[i].y] = 0;;
				}
				else
				{
					grid[findColors[i].x][findColors[i].y] += 1;
				}
				
			}

		    
			test.repaint(); //Color the grid that we just changed
	    	updateGeneralMessages("Your blocks have been upgraded!");
		}
	    else
	    {
	    	updateGeneralMessages("You do not have enough blocks to upgrade!");
	    }//End of if statement	
	}
	
	/*
	 * When a buy button is pressed, this method "buys" it for the user
	 * and updates everything related to it
	 */
	public void handleFruitPress(int num_button)
	{
		int choice;
		choice = num_button-1;
		
		if((current_money >= 
				fruits[choice].getPrice()))
		{
			money_rate = ((money_rate/boost_multiplier) + fruits[choice].getRate())*boost_multiplier;
			current_money -= fruits[choice].getPrice();
			fruits[choice].setPrice(Math.pow(fruits[choice].getPrice(), 1.04));
			
			fruits[choice].setCount(fruits[choice].getCount() +1 );
		
			if(boost_multiplier_on == false)
			{
				changeBlockGenMultiplier();
			}
			updateMoneyLabels();
			updatePrice(choice);
			
			updateGeneralMessages("You've purchased " + fruits[choice].getName() +"!");
		}
		
	}
	
	
	/*
	 * Input: choice from the upgrade menu that the user chose
	 * Return: n/a
	 * Function: Changes the upgrade multiplier, and stores the time that it started at
	 * 	 		 then multiplies current rate by the multiplier
	 */
	public void handleBoostMenuChoice(int choice) {
		
		int numChanged = 0;
		int[] findColorsToRemove = new int[400];
		int index = 0;
		boost_menu_open = false;
		//menu.tearDown(); //Takes down the upgrade menu
		
		/*
		 * If we have enough blocks to upgrade and there isn't already a multiplier going on
		 * we scan the grid and find the first 10 red blocks and change them to black and update
		 * numBlocks as nessicary
		 */
		if(choice == 0 && boost_multiplier_on == false && blocks[choice].getNum_blocks() >= 250)
		{
			
			//Change if max grid size increases
			for(int i = 0; i<20;i++)
			{
				for(int j = 0;j<20;j++)
				{
					if(grid[i][j] == 1 && numChanged<250)
					{
						grid[i][j] = 0;
						numChanged++;
					}
				}
			}

			blocks[choice].setNum_blocks(blocks[choice].getNum_blocks()-250); //we should have removed 10 blocks by now so so update numBlocks
			updateEmptySpots(); //and update the empty spots
			
			boost_durations_index = 0;
			boost_multiplier = 2;
			boost_start_time = System.currentTimeMillis();
			boost_multiplier_on = true;
			money_rate = money_rate*boost_multiplier; 
			
			updateGeneralMessages("Upgrade Successfully Purchased! x2 for 3 seconds!");
			updateMoneyLabels();
		}		
		else if(choice == 1 && boost_multiplier_on == false && blocks[choice].getNum_blocks() >= 125)
		{
			
			//Change if max grid size increases
			for(int i = 0; i<20;i++)
			{
				for(int j = 0;j<20;j++)
				{
					if(grid[i][j] == 2 && numChanged<125)
					{
						grid[i][j] = 0;
						numChanged++;
					}
				}
			}

			blocks[choice].setNum_blocks(blocks[choice].getNum_blocks()-125); //we should have removed 10 blocks by now so so update numBlocks
			updateEmptySpots(); //and update the empty spots
			
			boost_durations_index = 1;
			boost_multiplier = 4;
			boost_start_time = System.currentTimeMillis();
			boost_multiplier_on = true;
			money_rate = money_rate*boost_multiplier; 
			
			updateGeneralMessages("Upgrade Successfully Purchased! x4 for 5 seconds!");
			updateMoneyLabels();
		}
		else if(choice == 2 && boost_multiplier_on == false && blocks[choice].getNum_blocks() >= 60) //THIS ONE SHOULD REQUIRE THE USER HAVE BEEN PAST A CERTAIN BREAKPOIT
		{
			
			//Change if max grid size increases
			for(int i = 0; i<20;i++)
			{
				for(int j = 0;j<20;j++)
				{
					if(grid[i][j] == 3 && numChanged<60)
					{
						grid[i][j] = 0;
						numChanged++;
					}
				}
			}

			blocks[choice].setNum_blocks(blocks[choice].getNum_blocks()-60); //we should have removed 10 blocks by now so so update numBlocks
			updateEmptySpots(); //and update the empty spots
			
			boost_durations_index = 2;
			boost_multiplier = 8;
			boost_start_time = System.currentTimeMillis();
			boost_multiplier_on = true;
			money_rate = money_rate*boost_multiplier; 
			
			updateGeneralMessages("Upgrade Successfully Purchased! x8 for 7 seconds!");
			updateMoneyLabels();
		}
		
		
		else if(boost_multiplier_on == true)
		{
			updateGeneralMessages("Only 1 block upgarde multiplier can be active at a time");
		}
		else
		{
			updateGeneralMessages("You do not have enough blocks to buy this upgrade!");
		}
		
		
	}
	
	/*
	 * Must do all the checking to make sure the user can buy the upgrade
	 */
	public void handleUpgradeMenuChoice(int choice)
	{
		gamePaused = true;
		String str = "";
		int numChanged = 0;
		
		if(choice == 0) //Purple blocks upgrade
		{
			if(blocks[3].getNum_blocks() >= 80)
			{
				money_rate = money_rate*upgrade_multipliers[choice];
				blocks[3].setNum_blocks(blocks[3].getNum_blocks()-80);
				updateMoneyLabels();

				for(int i = 0; i<20;i++)
				{
					for(int j = 0;j<20;j++)
					{
						if(grid[i][j] == 4 && numChanged<80)
						{
							grid[i][j] = 0;
							numChanged++;
						}
					}
				}
				str = "You're money has been multiplied by x" + upgrade_multipliers[choice] + "!";
			}
			else
			{
				str = "You do not have enough blocks to purchase that!";
			}
		}
		else if (choice == 1)
		{
			if(blocks[4].getNum_blocks() >= 50)
			{
				money_rate = money_rate*upgrade_multipliers[choice];
				blocks[4].setNum_blocks(blocks[4].getNum_blocks()-50);
				updateMoneyLabels();
				str = "You're money has been multiplied by x" + upgrade_multipliers[choice] + "!";
				
				for(int i = 0; i<20;i++)
				{
					for(int j = 0;j<20;j++)
					{
						if(grid[i][j] == 5 && numChanged<80)
						{
							grid[i][j] = 0;
							numChanged++;
						}
					}
				}
			}
			else
			{
				str = "You do not have enough blocks to purchase that!";	
			}
		}
		
		updateGeneralMessages(str);
		changeBlockGenMultiplier();
		updateEmptySpots();
		
		gamePaused = false;
	}
	
	public void endUpgradeMultiplier()
	{
		boost_multiplier_on = false;
		money_rate = money_rate/boost_multiplier; 
		boost_multiplier = 1;
		updateGeneralMessages("Upgrade Ended!");
		updateMoneyLabels();
	}



	/*
	 * Input: none
	 * 
	 * Method makes the program generate less blocks so they are more rare as they 
	 * earn more money per second
	 * 
	 * Future: 
	 *  - Can add an argument that tells the method how to change it
	 *  - Might only want to have specific fruit multiplier changes, but be so hard
	 *    to generate the later blocks
	 *  - Add a pop up message to the user that tells them because it's about to get 
	 *    harder to generate blocks but they get some extra ones for free!
	 */
	public void changeBlockGenMultiplier()
	{
		int i;
		
		i = blocks[0].getCurrent_mult_index();
		if(money_rate > blocks[0].getMultiplier(i).x)
		{
			blocks[0].setCurrent_mult_index(blocks[0].getCurrent_mult_index()+1);
			
			
			for(int j = 0; j<NUMBER_OF_COLORS;j++)
			{
				blocks[j].setBreakpoint(0);
			}
		}
		
		

	}
	
	/*
	 * Updates fruit price, and number of them lables
	 */
	public void updatePrice(int choice)
	{
		//A place holder string just to show price
		String temp;
		JLabel fruit, fruit_num;
		
		fruits[choice].setPrice(Double.valueOf(money_dFormat.format(fruits[choice].getPrice())));
		temp = Double.toString(fruits[choice].getPrice());
		
		if(choice == 0)
		{
			//Could just fill these into an array and access the array instead
			fruit = lemonP_label;
			fruit_num = numLemon_label;
		}
		else if (choice == 1) //have to make this else if when i add more fruit
		{
			fruit = orangeP_label;
			fruit_num = numOranges_label;
		}
		else if (choice == 2)
		{
			fruit = appleP_label;
			fruit_num = numApple_label;
		}
		else if(choice == 3)
		{
			fruit = grapeP_label;
			fruit_num = numGrape_label;	
		}
		else 
		{
			fruit = blueberryP_label;
			fruit_num = numBlueberry_label;	
		}
	    fruit.setText("$" + temp);
	    
	    temp = Integer.toString(fruits[choice].getCount());
	    
	    fruit_num.setText("[ " + temp + " ]");
	    
	}
	
	/*
	 * Updates current money, money rate
	 */
	public void updateMoneyLabels()
	{	
		//A place holder string used for display
		String temp;
		
		current_money = Double.valueOf(money_dFormat.format(current_money));
		temp = Double.toString(current_money);
	    currMon_label.setText("Current money: $" + temp);
	   
	    //change to rate label
		money_rate = Double.valueOf(money_dFormat.format(money_rate));
		temp = Double.toString(money_rate);
	    currRate_label.setText("Current money rate: $" + temp);
	}
	
	
	/* 
	 * Updates the block labels without having to redraw them
	 */
	public void updateBlockLabels()
	{
		String temp;

	    temp = Integer.toString(blocks[0].getNum_blocks());
		numYellow_label.setText("Number of Yellow Blocks: " + temp);
	    
	    //Change Orange block label
		temp = Integer.toString(blocks[1].getNum_blocks());
		numOrange_label.setText("Number of Orange Blocks: " + temp);
	    
	    //Change Red block label
		temp = Integer.toString(blocks[2].getNum_blocks());
		numRed_label.setText("Number of Red Blocks: " + temp);
		
		//Change Purple block label
		temp = Integer.toString(blocks[3].getNum_blocks());
		numPurple_label.setText("Number of Purple Blocks: " + temp);
		
		//Change Blue block label
		temp = Integer.toString(blocks[4].getNum_blocks());
		numBlue_label.setText("Number of Blue Blocks: " + temp);
		
		

	}
	
	public void updateGeneralMessages(String str)
	{
		general_message_label5.setText(general_message_label4.getText());
		general_message_label4.setText(general_message_label3.getText());
		general_message_label3.setText(general_message_label2.getText());
		general_message_label2.setText(general_message_label1.getText());
		general_message_label1.setText(str);
	}
	
	
	
	


	public void drawButtons()
	{
		Font smallButtonFont = new Font("ARIEL", Font.PLAIN, 10);
		Color gray4 = new Color(32,32,32);
		Color SOFT_YELLOW = new Color(253,253,75);
		ImageIcon img = new ImageIcon("C:\\Users\\Brandon\\workspace\\JBGame\\src\\button.png");
		
		
		GradientPaint redtowhite = new GradientPaint(0,0,Color.RED,100, 0,Color.WHITE);
		
	    /*
	     * Button to buy lemons
	     * On press: buys a lemon and updates all things to show that
	     */
	    lemon_button = new JButton(); 
	    test.add(lemon_button);
	    lemon_button.setBounds(15,FIRST_BUTTON, 100, 25);

	    
	    lemon_button.setVisible(true);
	    lemon_button.setText(lemon.getName());
	    lemon_button.addActionListener(main_listener);
	    lemon_button.setActionCommand("lemon");
	    lemon_button.setBackground(Color.GRAY.brighter());
	    lemon_button.setForeground(Color.BLACK);
	    lemon_button.setFocusable(false);

	    /*
	     * Button to buy oranges
	     * On press: buys a orange and updates all things to show that
	     */
	    orange_button = new JButton();
	    test.add(orange_button);
	    orange_button.setBounds(15, FIRST_BUTTON+35,100,25);
	    orange_button.setVisible(true);
	    orange_button.setText(oranges.getName());  
	    orange_button.setBackground(Color.GRAY.brighter());
	    orange_button.setForeground(Color.BLACK);
	    orange_button.addActionListener(main_listener);
	    orange_button.setActionCommand("orange");
	    
	    /*
	     * Button to buy apples
	     * On press: buys a apples and updates all things to show that
	     */
	    apple_button = new JButton();
	    test.add(apple_button);
	    apple_button.setBounds(15, FIRST_BUTTON+70,100,25);
	    apple_button.setVisible(true);
	    apple_button.setText(apple.getName());  
	    apple_button.addActionListener(main_listener);
	    apple_button.setActionCommand("apple");
	    apple_button.setBackground(Color.GRAY.brighter());
	    apple_button.setForeground(Color.BLACK);
	    
	    /*
	     * Button to buy grapes
	     * On press: buys a grapes and updates all things to show that
	     */
	    grape_button = new JButton();
	    test.add(grape_button);
	    grape_button.setBounds(260, FIRST_BUTTON,100,25);
	    grape_button.setVisible(true);
	    grape_button.setText(grape.getName());  
	    grape_button.addActionListener(main_listener);
	    grape_button.setActionCommand("grape");
	    grape_button.setBackground(Color.GRAY.brighter());
	    grape_button.setForeground(Color.BLACK);
	    
	    /*
	     * Button to buy blueberries
	     * On press: buys a blueberries and updates all things to show that
	     */
	    blueberry_button = new JButton();
	    test.add(blueberry_button);
	    blueberry_button.setBounds(260, FIRST_BUTTON+35,100,25);
	    blueberry_button.setVisible(true);
	    blueberry_button.setText(blueberry.getName());  
	    blueberry_button.addActionListener(main_listener);
	    blueberry_button.setActionCommand("blueberry");
	    blueberry_button.setBackground(Color.GRAY.brighter());
	    blueberry_button.setForeground(Color.BLACK);
	    
	    
	    /*
	     * Button to upgrade yellow blocks to orange
	     * 
	     */
	    upgradeYBlocks_button = new JButton();
	    test.add(upgradeYBlocks_button);
	    upgradeYBlocks_button.setBounds(675, 105, 80,18); //y is 4 higher than label y
	    upgradeYBlocks_button.setFont(smallButtonFont);
	    upgradeYBlocks_button.setVisible(true);
	    upgradeYBlocks_button.setText("Upgrade!");
	    upgradeYBlocks_button.setBackground(Color.GRAY.brighter());
	    upgradeYBlocks_button.setForeground(Color.BLACK);
	    upgradeYBlocks_button.addActionListener(main_listener);
	    upgradeYBlocks_button.setActionCommand("upgradeYellow");
	    
	    /*
	     * Button to upgrade orange blocks to red
	     * 
	     */
	    upgradeOBlocks_button = new JButton();
	    test.add(upgradeOBlocks_button);
	    upgradeOBlocks_button.setBounds(675, 145, 80,18); //y is 4 higher than lable y
	    upgradeOBlocks_button.setFont(smallButtonFont);
	    upgradeOBlocks_button.setVisible(true);
	    upgradeOBlocks_button.setText("Upgrade!");
	    upgradeOBlocks_button.setBackground(Color.GRAY.brighter());
	    upgradeOBlocks_button.setForeground(Color.BLACK);
	    upgradeOBlocks_button.addActionListener(main_listener);
	    upgradeOBlocks_button.setActionCommand("upgradeOrange");
	    
	    /*
	     * Button to upgrade red blocks to purple
	     * 
	     */
	    upgradeRBlocks_button = new JButton();
	    test.add(upgradeRBlocks_button);
	    upgradeRBlocks_button.setBounds(675, 185, 80,18); //y is 4 higher than lable y
	    upgradeRBlocks_button.setFont(smallButtonFont);
	    upgradeRBlocks_button.setVisible(true);
	    upgradeRBlocks_button.setText("Upgrade!");
	    upgradeRBlocks_button.setBackground(Color.GRAY.brighter());
	    upgradeRBlocks_button.setForeground(Color.BLACK);
	    upgradeRBlocks_button.addActionListener(main_listener);
	    upgradeRBlocks_button.setActionCommand("upgradeRed");
	    
	    /*
	     * Button to upgrade purple blocks to blue
	     * 
	     */
	    upgradePBlocks_button = new JButton();
	    test.add(upgradePBlocks_button);
	    upgradePBlocks_button.setBounds(675, 225, 80,18); //y is 4 higher than lable y
	    upgradePBlocks_button.setFont(smallButtonFont);
	    upgradePBlocks_button.setVisible(true);
	    upgradePBlocks_button.setText("Upgrade!");
	    upgradePBlocks_button.setBackground(Color.GRAY.brighter());
	    upgradePBlocks_button.setForeground(Color.BLACK);
	    upgradePBlocks_button.addActionListener(main_listener);
	    upgradePBlocks_button.setActionCommand("upgradePurple");
	
	    /*
	     * Button to upgrade blue blocks to idk yet
	     * 
	     */
	    upgradeBBlocks_button = new JButton();
	    test.add(upgradeBBlocks_button);
	    upgradeBBlocks_button.setBounds(675, 265, 80,18); //y is 4 higher than lable y
	    upgradeBBlocks_button.setFont(smallButtonFont);
	    upgradeBBlocks_button.setVisible(true);
	    upgradeBBlocks_button.setText("Upgrade!");
	    upgradeBBlocks_button.setBackground(Color.GRAY.brighter());
	    upgradeBBlocks_button.setForeground(Color.BLACK);
	    upgradeBBlocks_button.addActionListener(main_listener);
	    upgradeBBlocks_button.setActionCommand("upgradeBlue");
	    
	    
       /*
        * Creates boost menu
        */
	    boost_menu_button = new JButton();
	    test.add(boost_menu_button);
	    boost_menu_button.setBounds(480, 35, 125, 25);
	    boost_menu_button.setVisible(true);
	    boost_menu_button.setText("Boosts Menu");
	    boost_menu_button.setBackground(Color.GRAY.brighter());
	    boost_menu_button.setForeground(Color.BLACK);
	    boost_menu_button.addActionListener(main_listener);
	    boost_menu_button.setActionCommand("create_boost_menu");
	    
	    upgrade_menu_button = new JButton();
	    test.add(upgrade_menu_button);
	    upgrade_menu_button.setBounds(630, 35, 125, 25);
	    upgrade_menu_button.setVisible(true);
	    upgrade_menu_button.setText("Upgrades Menu");
	    upgrade_menu_button.setBackground(Color.GRAY.brighter());
	    upgrade_menu_button.setForeground(Color.BLACK);
	    upgrade_menu_button.addActionListener(main_listener);
	    upgrade_menu_button.setActionCommand("create_upgrade_menu");
	    
	    /*
	     * Save game button
	     *
	    save_game_button = new JButton();
	    test.add(save_game_button);
	    save_game_button.setBounds(200, 375, 125, 25);
	    save_game_button.setVisible(true);
	    save_game_button.setText("Save Game");
	    save_game_button.setBackground(Color.GRAY.brighter());
	    save_game_button.setForeground(Color.BLACK);
	    save_game_button.addActionListener(main_listener);
	    save_game_button.setActionCommand("save_game");
	    */
	    /*
	     * show stats button
	     */
	    
	    show_stats_button = new JButton();
	    test.add(show_stats_button);
	    show_stats_button.setBounds(200, 375, 125, 25);
	    show_stats_button.setVisible(true);
	    show_stats_button.setText("Show Stats");
	    show_stats_button.setBackground(Color.GRAY.brighter());
	    show_stats_button.setForeground(Color.BLACK);
	    show_stats_button.addActionListener(main_listener);
	    show_stats_button.setActionCommand("show_stats");
	    
	
	
	}
	
	
	/*
	 * Makes calls to the create_label function in the utilties class to create
	 * fruit and money labels
	 */
	public void drawLabels()
	{	
		drawingFunctions.drawBlockLabels();   
		drawingFunctions.drawFruitLabels();
		drawingFunctions.drawGeneralLabels();
		drawingFunctions.drawMoneyLabels();
	}
	
	public void saveGame() throws IOException
	{
		if(stop_for_save == false)
		{
			stop_for_save = true;
			showStats();
		}
		
		
		File file = new File("savedGame3.txt");
		file.delete();
		
		FileOutputStream fout = new FileOutputStream("savedGame3.txt");
		//fout.write((new String()).getBytes());
		
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(this);
		
		fout.close();
		oos.close();

		stop_for_save = false;

	}
	

	/* 
	 * TEST CODE TEST CODE TEST CODE
	 */
	public double getTotalMoneyMade()
	{
		return total_money_made;
	}
	
	public double getMoneyRate()
	{
		return money_rate;
	}
	
	public double getCurrentMoney()
	{
		return current_money;
	}
	
	
	

	
}


