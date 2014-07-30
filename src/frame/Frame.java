package frame;


import javax.swing.JFrame;

public class Frame  extends JFrame{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Frame(String titel) {
	        super(titel);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setSize(300, 300);
	        
	        setVisible(true);
	  }
}
