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

        
        
	
        
        
        
	}

}
