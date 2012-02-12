import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ClickerPadThread implements ClickerConsumerInterface {
	/*
	 * 	0xy			mouse move
	1##			mouse button
	1#0				left mouse click
	1#1				right mouse click
	1#2				left mouse down
	1#3				left mouse up
	1#4				right mouse down
	1#5				right mouse up
	2##			keyboard events //not implemented	
	 */


	protected final static String COLON_SEPARATOR      = "`/:";

	private Robot r;
	//	private DatagramSocket ds;
	//	private SocketHandler sh;
	//	private Socket s;
	//	private PrintWriter p;
	private boolean open;
//	private Map<String, KeyboardCode> keyboardValues;

	public ClickerPadThread() {
		try {
			r = new Robot();
			r.setAutoDelay(5);

		} catch (AWTException e) {
			e.printStackTrace();
		}
		//		try {
		//			ds = new DatagramSocket(5678);
		//		} catch (SocketException e) {
		//			e.printStackTrace();
		//		}
		//		sh = shR;
		//		s = sR;
		//		try {
		//			p = new PrintWriter(s.getOutputStream());
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
		open = true;
//		buildHashMap();
	}

	//	public void run() {
	////		p.println("OpenClickPad;5678");
	////		p.flush();
	////		byte[] b = { 0, 0, 0, 0 };
	////		DatagramPacket dp = new DatagramPacket(b, 4);// blank constructor
	//		while (open) {
	//			// System.out.println("trying to receive");
	////			try {
	////				ds.receive(dp);
	////			} catch (IOException e) {
	////				//e.printStackTrace();
	////			}
	////			if (dp != null) {
	////				byte[] data = dp.getData();
	//
	//			
	//			}
	//			try {
	//				Thread.sleep(5);
	//			} catch (InterruptedException e) {}
	//		}
	//	}

//	private void buildHashMap(){
//		keyboardValues = new HashMap<String, KeyboardCode>();
//		//lower case
//		keyboardValues.put("a", new KeyboardCode(KeyEvent.VK_A, false));
//		keyboardValues.put("b", new KeyboardCode(KeyEvent.VK_B, false));
//		keyboardValues.put("c", new KeyboardCode(KeyEvent.VK_C, false));
//		keyboardValues.put("d", new KeyboardCode(KeyEvent.VK_D, false));
//		keyboardValues.put("e", new KeyboardCode(KeyEvent.VK_E, false));
//		keyboardValues.put("f", new KeyboardCode(KeyEvent.VK_F, false));
//		keyboardValues.put("g", new KeyboardCode(KeyEvent.VK_G, false));
//		keyboardValues.put("h", new KeyboardCode(KeyEvent.VK_H, false));
//		keyboardValues.put("i", new KeyboardCode(KeyEvent.VK_I, false));
//		keyboardValues.put("j", new KeyboardCode(KeyEvent.VK_J, false));
//		keyboardValues.put("k", new KeyboardCode(KeyEvent.VK_K, false));
//		keyboardValues.put("l", new KeyboardCode(KeyEvent.VK_L, false));
//		keyboardValues.put("m", new KeyboardCode(KeyEvent.VK_M, false));
//		keyboardValues.put("n", new KeyboardCode(KeyEvent.VK_N, false));
//		keyboardValues.put("o", new KeyboardCode(KeyEvent.VK_O, false));
//		keyboardValues.put("p", new KeyboardCode(KeyEvent.VK_P, false));
//		keyboardValues.put("q", new KeyboardCode(KeyEvent.VK_Q, false));
//		keyboardValues.put("r", new KeyboardCode(KeyEvent.VK_R, false));
//		keyboardValues.put("s", new KeyboardCode(KeyEvent.VK_S, false));
//		keyboardValues.put("t", new KeyboardCode(KeyEvent.VK_T, false));
//		keyboardValues.put("u", new KeyboardCode(KeyEvent.VK_U, false));
//		keyboardValues.put("v", new KeyboardCode(KeyEvent.VK_V, false));
//		keyboardValues.put("w", new KeyboardCode(KeyEvent.VK_W, false));
//		keyboardValues.put("x", new KeyboardCode(KeyEvent.VK_X, false));
//		keyboardValues.put("y", new KeyboardCode(KeyEvent.VK_Y, false));
//		keyboardValues.put("z", new KeyboardCode(KeyEvent.VK_Z, false));
//
//		//upper case
//		keyboardValues.put("A", new KeyboardCode(KeyEvent.VK_A, true));
//		keyboardValues.put("B", new KeyboardCode(KeyEvent.VK_B, true));
//		keyboardValues.put("C", new KeyboardCode(KeyEvent.VK_C, true));
//		keyboardValues.put("D", new KeyboardCode(KeyEvent.VK_D, true));
//		keyboardValues.put("E", new KeyboardCode(KeyEvent.VK_E, true));
//		keyboardValues.put("F", new KeyboardCode(KeyEvent.VK_F, true));
//		keyboardValues.put("G", new KeyboardCode(KeyEvent.VK_G, true));
//		keyboardValues.put("H", new KeyboardCode(KeyEvent.VK_H, true));
//		keyboardValues.put("I", new KeyboardCode(KeyEvent.VK_I, true));
//		keyboardValues.put("J", new KeyboardCode(KeyEvent.VK_J, true));
//		keyboardValues.put("K", new KeyboardCode(KeyEvent.VK_K, true));
//		keyboardValues.put("L", new KeyboardCode(KeyEvent.VK_L, true));
//		keyboardValues.put("M", new KeyboardCode(KeyEvent.VK_M, true));
//		keyboardValues.put("N", new KeyboardCode(KeyEvent.VK_N, true));
//		keyboardValues.put("O", new KeyboardCode(KeyEvent.VK_O, true));
//		keyboardValues.put("P", new KeyboardCode(KeyEvent.VK_P, true));
//		keyboardValues.put("Q", new KeyboardCode(KeyEvent.VK_Q, true));
//		keyboardValues.put("R", new KeyboardCode(KeyEvent.VK_R, true));
//		keyboardValues.put("S", new KeyboardCode(KeyEvent.VK_S, true));
//		keyboardValues.put("T", new KeyboardCode(KeyEvent.VK_T, true));
//		keyboardValues.put("U", new KeyboardCode(KeyEvent.VK_U, true));
//		keyboardValues.put("V", new KeyboardCode(KeyEvent.VK_V, true));
//		keyboardValues.put("W", new KeyboardCode(KeyEvent.VK_W, true));
//		keyboardValues.put("X", new KeyboardCode(KeyEvent.VK_X, true));
//		keyboardValues.put("Y", new KeyboardCode(KeyEvent.VK_Y, true));
//		keyboardValues.put("Z", new KeyboardCode(KeyEvent.VK_Z, true));
//
//		//numerals
//		keyboardValues.put("1", new KeyboardCode(KeyEvent.VK_1, false));
//		keyboardValues.put("2", new KeyboardCode(KeyEvent.VK_2, false));
//		keyboardValues.put("3", new KeyboardCode(KeyEvent.VK_3, false));
//		keyboardValues.put("4", new KeyboardCode(KeyEvent.VK_4, false));
//		keyboardValues.put("5", new KeyboardCode(KeyEvent.VK_5, false));
//		keyboardValues.put("6", new KeyboardCode(KeyEvent.VK_6, false));
//		keyboardValues.put("7", new KeyboardCode(KeyEvent.VK_7, false));
//		keyboardValues.put("8", new KeyboardCode(KeyEvent.VK_8, false));
//		keyboardValues.put("9", new KeyboardCode(KeyEvent.VK_9, false));
//		keyboardValues.put("0", new KeyboardCode(KeyEvent.VK_0, false));
//
//		//shifted numerals
//		keyboardValues.put("!", new KeyboardCode(KeyEvent.VK_1, true));
//		keyboardValues.put("@", new KeyboardCode(KeyEvent.VK_2, true));
//		keyboardValues.put("#", new KeyboardCode(KeyEvent.VK_3, true));
//		keyboardValues.put("$", new KeyboardCode(KeyEvent.VK_4, true));
//		keyboardValues.put("%", new KeyboardCode(KeyEvent.VK_5, true));
//		keyboardValues.put("^", new KeyboardCode(KeyEvent.VK_6, true));
//		keyboardValues.put("&", new KeyboardCode(KeyEvent.VK_7, true));
//		keyboardValues.put("*", new KeyboardCode(KeyEvent.VK_8, true));
//		keyboardValues.put("(", new KeyboardCode(KeyEvent.VK_9, true));
//		keyboardValues.put(")", new KeyboardCode(KeyEvent.VK_0, true));
//
//		//other punctuation
//		keyboardValues.put("`", new KeyboardCode(KeyEvent.VK_BACK_QUOTE, false));
//		keyboardValues.put("~", new KeyboardCode(KeyEvent.VK_BACK_QUOTE, true));
//
//		keyboardValues.put(";", new KeyboardCode(KeyEvent.VK_SEMICOLON, false));
//		keyboardValues.put(":", new KeyboardCode(KeyEvent.VK_SEMICOLON, true));
//
//		keyboardValues.put("'", new KeyboardCode(KeyEvent.VK_QUOTE, false));
//		keyboardValues.put("\"", new KeyboardCode(KeyEvent.VK_QUOTE, true));
//
//		keyboardValues.put(",", new KeyboardCode(KeyEvent.VK_COMMA, false));
//		keyboardValues.put("<", new KeyboardCode(KeyEvent.VK_COMMA, true));
//
//		keyboardValues.put(".", new KeyboardCode(KeyEvent.VK_PERIOD, false));
//		keyboardValues.put(">", new KeyboardCode(KeyEvent.VK_PERIOD, true));
//
//		keyboardValues.put("/", new KeyboardCode(KeyEvent.VK_SLASH, false));
//		keyboardValues.put("?", new KeyboardCode(KeyEvent.VK_SLASH, true));
//
//		keyboardValues.put("[", new KeyboardCode(KeyEvent.VK_OPEN_BRACKET, false));
//		keyboardValues.put("{", new KeyboardCode(KeyEvent.VK_OPEN_BRACKET, true));
//
//		keyboardValues.put("]", new KeyboardCode(KeyEvent.VK_CLOSE_BRACKET, false));
//		keyboardValues.put("}", new KeyboardCode(KeyEvent.VK_CLOSE_BRACKET, true));
//
//		keyboardValues.put("-", new KeyboardCode(KeyEvent.VK_MINUS, false));
//		keyboardValues.put("_", new KeyboardCode(KeyEvent.VK_MINUS, true));
//
//		keyboardValues.put("=", new KeyboardCode(KeyEvent.VK_EQUALS, false));
//		keyboardValues.put("+", new KeyboardCode(KeyEvent.VK_EQUALS, true));
//
//		keyboardValues.put("\\", new KeyboardCode(KeyEvent.VK_BACK_SLASH, false));
//		keyboardValues.put("|", new KeyboardCode(KeyEvent.VK_BACK_SLASH, true));
//
//		keyboardValues.put(" ", new KeyboardCode(KeyEvent.VK_SPACE, false));
//		keyboardValues.put("\n", new KeyboardCode(KeyEvent.VK_ENTER, false));
//		keyboardValues.put("\t", new KeyboardCode(KeyEvent.VK_TAB, false)); 
//		keyboardValues.put("\b", new KeyboardCode(KeyEvent.VK_BACK_SPACE, false));
//	}


//	public void typeCharacter(Robot robot, String letter)  
//	{  
//		try  
//		{  
//			/* boolean upperCase = Character.isUpperCase( letter.charAt(0) );  
//	            //String variableName = "VK_" + letter.toUpperCase();  
//	            String variableName = "VK_"; 
//
//	            KeyEvent ke = new KeyEvent(new JTextField(), 0, 0, 0, 0, ' ');  
//	            Class clazz = ke.getClass();  
//	            Field field = clazz.getField( variableName );  
//	            int keyCode = field.getInt(ke);  
//
//	            //robot.delay(1000);  */
//			System.out.println("char to byte: "+(byte)letter.charAt(0));
//
//			KeyboardCode kc = keyboardValues.get(letter);
//
//			boolean upperCase = kc.getUpperCase();
//			int keyCode = kc.getKeyCode();
//
//			if (upperCase) robot.keyPress( KeyEvent.VK_SHIFT );  
//
//			robot.keyPress( keyCode );  
//			robot.keyRelease( keyCode );  
//
//			if (upperCase) robot.keyRelease( KeyEvent.VK_SHIFT );  
//		}  
//		catch(NullPointerException ne){
//			//invalid character typed, bypass
//			//release shift in the event it was pressed
//			System.out.println("null pointer "+letter);
//			r.keyRelease(KeyEvent.VK_SHIFT);
//		}
//		catch(Exception e)  
//		{  
//			e.printStackTrace(); 
//			r.keyRelease(KeyEvent.VK_SHIFT);
//		}  
//	} 

	
	
	@Override
	public void setParent(ConsumerFrame parent) {

	}

	@Override
	public void setID(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public String declareConsumptions() {
		// TODO Auto-generated method stub
		return "MouseControl`/:All";
	}

	@Override
	public void setActiveStatus(boolean status) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getActiveStatus() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void inputData(Map<String, Map<String, String>> input) {
		//name : index[] : answer
		Iterator<String> nameIterator = input.keySet().iterator();
		Map<String, String> indexToAnswers = input.get(nameIterator.next());
		String[] data = indexToAnswers.get("0").split(COLON_SEPARATOR);
		
				if (data[0].equals("0")) { //mouse move event
					int xMove = Integer.parseInt(data[1]);
					int yMove = Integer.parseInt(data[2]);
					Point p = MouseInfo.getPointerInfo().getLocation();
					int x = p.x;
					int y = p.y;
					r.mouseMove(x + xMove, y + yMove);
				} else if (data[0].equals("1")){ //mouse button event
					if (data[2].equals("0")){ //left mouse click
						r.mousePress(InputEvent.BUTTON1_MASK);
						r.waitForIdle();
						r.mouseRelease(InputEvent.BUTTON1_MASK);
					} else if(data[2].equals("1")){//right mouse click
						r.mousePress(InputEvent.BUTTON3_MASK);
						r.waitForIdle();
						r.mouseRelease(InputEvent.BUTTON3_MASK);
					} else if(data[2].equals("2")){//left mouse down
						r.mousePress(InputEvent.BUTTON1_MASK);
					} else if(data[2].equals("3")){//left mouse up
						r.mouseRelease(InputEvent.BUTTON1_MASK);
					} else if(data[2].equals("4")){//right mouse down
						r.mousePress(InputEvent.BUTTON3_MASK);
					} else if(data[2].equals("5")){//right mouse up
						r.mouseRelease(InputEvent.BUTTON3_MASK);
					}
				}
//				else if (data[0].equals("2")){//keyboard event
//					//System.out.println("received keyboard event: "+(char)(data[2]));
////					typeCharacter(r, Character.toString((char)data[2]) );
//					
//				}
				
	}

	@Override
	public void setQuestion(String question) {
		// TODO Auto-generated method stub

	}
	
//	private class KeyboardCode {
//		private int keyCode;
//		private boolean isUpperCase;
//		public KeyboardCode(int i, boolean b){
//			keyCode = i;
//			isUpperCase = b;
//		}
//		public int getKeyCode(){
//			return keyCode;
//		}
//		public boolean getUpperCase(){
//			return isUpperCase;
//		}
//	}
}
