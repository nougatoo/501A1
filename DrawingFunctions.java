import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class DrawingFunctions {

	private Game game;
	private Utilities util = new Utilities();
	
	
	
	public DrawingFunctions(Game g)
	{
		game = g;
	}
	
	
	
	/*
	 * Makes calls to the create_label function in the utilties class to create
	 * fruit and money labels
	 */
	public void drawLabels()
	{
		
		Color gray1 = new Color(200,200,200);
		Color gray2 = new Color(112,112,112);
		Color gray3 = new Color(72,72,72);
		Color gray4 = new Color(32,32,32);
		
		String temp; 
		
		/*
		 * General message labels that is just used to show the user messages as needed
		 * and each one become more "transparent" than the last
		 */
        //Creates a label for the general message 1 (most recent)
        game.general_message_label1 = util.create_label(470, 540, "", 360, 25, Color.WHITE);
        game.test.add(game.general_message_label1);
		
        game.general_message_label2 = util.create_label(470, 515, "", 360, 25, gray1);
        game.test.add(game.general_message_label2);

        game.general_message_label3 = util.create_label(470, 490, "", 360, 25, gray2);
        game.test.add(game.general_message_label3);
        
        game.general_message_label4 = util.create_label(470, 465, "", 360, 25, gray3);
        game.test.add(game.general_message_label4);
        
        game.general_message_label5 = util.create_label(470, 440, "", 360, 25, gray4);
        game.test.add(game.general_message_label5);

        
		/* 
		 * Money Labels
		 */
        //Creates a label for total money made
        temp = Double.toString(game.getTotalMoneyMade());
        game.totalMon_label = util.create_label(15, 350, "Total Money Made: $" + temp, 250, 25, Color.WHITE);
        game.test.add(game.totalMon_label);

        //Creates a label for the current money 
        temp = Double.toString(game.getCurrentMoney());
        game.currMon_label = util.create_label(15, 400, "Current money: $" + temp, 250, 25, Color.WHITE);
        game.test.add(game.currMon_label);              
    
        //Creates a label for the current money rate
        temp = Double.toString(game.getMoneyRate());
        game.currRate_label = util.create_label(15, 375, "Current money rate: $" + temp, 250, 25, Color.WHITE);
        game.test.add(game.currRate_label);
        
        /*
         * Fruit price Labels
         */
        //Lemons
        
        temp = Double.toString(game.fruits[0].getPrice());
        game.lemonP_label = util.create_label(160, game.FIRST_BUTTON, "$" + temp, 75, 25, Color.WHITE);
        game.test.add(game.lemonP_label);
        
        //Oranges
        temp = Double.toString(game.fruits[1].getPrice());
        game.orangeP_label = util.create_label(160, game.FIRST_BUTTON+35, "$" + temp, 75, 25, Color.WHITE);
        game.test.add(game.orangeP_label);

        //Apples
        temp = Double.toString(game.fruits[2].getPrice());
        game.appleP_label = util.create_label(160, game.FIRST_BUTTON+70, "$" + temp, 75, 25, Color.WHITE);
        game.test.add(game.appleP_label);
        
        //Grapes	
        temp = Double.toString(game.fruits[3].getPrice());
        game.grapeP_label = util.create_label(405, game.FIRST_BUTTON, "$" + temp, 75, 25, Color.WHITE);
        game.test.add(game.grapeP_label);
        
        //Blueberries
        temp = Double.toString(game.fruits[4].getPrice());
        game.blueberryP_label = util.create_label(405, game.FIRST_BUTTON+35, "$" + temp, 75, 25, Color.WHITE);
        game.test.add(game.blueberryP_label);
        
        /*
         * Fruit Count labels
         */
        
        //Lemons
        temp = Integer.toString(game.fruits[0].getCount());
        game.numLemon_label = util.create_label(120, game.FIRST_BUTTON-5, "[ " + temp + " ]", 30, 30, Color.WHITE);
        game.test.add(game.numLemon_label);
                        
        //Oranges
        temp = Integer.toString(game.fruits[1].getCount());
        game.numOranges_label = util.create_label(120, game.FIRST_BUTTON+30, "[ " + temp + " ]", 30, 30, Color.WHITE);
        game.test.add(game.numOranges_label);      
        
        //Apples
        temp = Integer.toString(game.fruits[2].getCount());
        game.numApple_label = util.create_label(120, game.FIRST_BUTTON+65, "[ " + temp + " ]", 30, 30, Color.WHITE);
        game.test.add(game.numApple_label);
        
        //Grapes
        temp = Integer.toString(game.fruits[3].getCount());
        game.numGrape_label = util.create_label(365, game.FIRST_BUTTON-5, "[ " + temp + " ]", 30, 30, Color.WHITE);
        game.test.add(game.numGrape_label);

        //Blueberries
        temp = Integer.toString(game.fruits[4].getCount());
        game.numBlueberry_label = util.create_label(365, game.FIRST_BUTTON+30, "[ " + temp + " ]", 30, 30, Color.WHITE);
        game.test.add(game.numBlueberry_label);
        
        /*
         * Blocks Labels
         */
        
        //Number of yellow blocks label
        temp = Integer.toString(game.blocks[0].getNum_blocks());
        game.numYellow_label = util.create_label(500, 101, "Number of Yellow Blocks: " + temp, 200, 25, Color.WHITE);
        game.test.add(game.numYellow_label);
        
        //Number of orange blocks label
		temp = Integer.toString(game.blocks[1].getNum_blocks());
        game.numOrange_label = util.create_label(500, 141, "Number of Orange Blocks: " + temp, 200, 25, Color.WHITE);
        game.test.add(game.numOrange_label);
        
        //Number of red blocks label
		temp = Integer.toString(game.blocks[2].getNum_blocks());
        game.numRed_label = util.create_label(500, 181, "Number of Red Blocks: " + temp, 200, 25, Color.WHITE);
        game.test.add(game.numRed_label);
        
        //Number of purple blocks label
        temp = Integer.toString(game.blocks[3].getNum_blocks());
        game.numPurple_label = util.create_label(500, 221, "Number of Purple Blocks: " + temp, 200, 25, Color.WHITE);
        game.test.add(game.numPurple_label);
        
        //Number of blue blocks label
        temp = Integer.toString(game.blocks[4].getNum_blocks());
        game.numBlue_label = util.create_label(500, 261, "Number of Blue Blocks: " + temp, 200, 25, Color.WHITE);
        game.test.add(game.numBlue_label);
        
        
        
	
        
        
        
	}

}
