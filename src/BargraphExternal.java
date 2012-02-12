import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * Displays the results of a question in a bargraph.
 * @author Aaron
 *
 */
public class BargraphExternal extends JPanel{

	private boolean running;
	private ConsumerFrame parent;
	private String currentQuestion;
	private String groupID;
	private JFrame frame;
	private int size;
	private int participantCount;
	private int buffer;
	private int width;
	private int height;
	private Map<String, String> currentValueMap;//  Person name, index#, value
	private String currentValue;
	private BufferedReader in;
	private ServerSocket s;
	private boolean runRead;
	
	public BargraphExternal(){
		System.out.println("external started");
		currentQuestion = "";
		size=0;
		participantCount = 0;
		buffer = 10;
		width = 600;
		height = 400;
		frame = new JFrame();
		//graphPanel = new JPanel();
		frame.setSize(width+80, height+80);
		this.setMinimumSize(new Dimension(width, height));
		frame.add("Center", this);
		runRead = true;
		initializeGraph();
		//validWidgetPositions = new ArrayList<Integer>(8);
		try {
			s = new ServerSocket(9745);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					Socket client = s.accept();
					in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					client.setSoTimeout(100);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				while(runRead){
					try{
						String input = in.readLine();
						
						String[] inputParts = input.split("`!:");
						if(inputParts[0].equalsIgnoreCase("ID") ){
							setID(inputParts[1]);
						} else if(inputParts[0].equalsIgnoreCase("STATUS")){
							setActiveStatus(Boolean.parseBoolean(inputParts[1]));
						} else if (inputParts[0].equalsIgnoreCase("QUESTION")){
							setQuestion(inputParts[1]);
						} else if (inputParts[0].equalsIgnoreCase("ANSWERS")){
							inputData(inputParts[1]);
						} else {
							System.out.println("Unknonwn answer received: "+input);
						}
					} catch (SocketTimeoutException e){
						
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			}}).start();
	}
	
	
	
	/**
	 * Any values that need initialized at startup.
	 */
	private void initializeGraph(){
		currentValueMap = Collections.synchronizedMap(new HashMap<String, String>());
		//frame.setVisible(true);
	}
	
	/**
	 * Once a question is active and running, this method will display the frame to the users.
	 */
	private void startGraph(){
		System.out.println("starting graph");
		frame.setVisible(true);
	}
	
	/**
	 * When a question is closed, this will hide the frame until it can be disposed.
	 */
	private void endGraph(){
		frame.setVisible(false);
	}
	
	
	public void setParent(ConsumerFrame parent) {
		this.parent = parent;
	}

	/**
	 * Declaring consumption is letting the admin know the consumer's name and what sort of data it expects.
	 * Can be either Count, Avg, All.
	 * If Avg, needs to specify how many values can be averaged.
	 */
	
	public String declareConsumptions() {
		return "BarGraph`/:Count";
	}
	
	/**
	 * sets the frame active/inactive
	 */
	
	public void setActiveStatus(boolean status) {
		if(!status){
			frame.setVisible(false);
			frame.dispose();
			runRead = false;
		}
	}

	
	public boolean getActiveStatus() {
		return running;
	}

	/**
	 * Input from the IMP is received here
	 */
	
	public void inputData(String input) {//  Person name, index#, value
		synchronized(currentValueMap){
			String[] answers = input.split(";");
			for (String answer : answers){
				String[] parts = answer.split(",");
				currentValueMap.put(parts[0], parts[1]);
			}
//			Iterator i = input.keySet().iterator();
//			String iNext = "";
//			while (i.hasNext()){
//				iNext = (String) i.next();
//				currentValueMap.put(iNext, input.get(iNext));
//			}
		}
		repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Map<String, String> answerCounts = new HashMap<String, String>();
		answerCounts = calculateValues();
		if(answerCounts != null){
			int total = 0;
			size = answerCounts.size() + 1;
			total = participantCount;
			int i = 0;
			Iterator iterator = answerCounts.keySet().iterator();
			String iteratorNext = "";
			
			TreeSet<String> keys = new TreeSet<String>(answerCounts.keySet());
			
			int stagger = 50;
			for (String key : keys) { 
			   Integer value = Integer.parseInt(answerCounts.get(key));
			   String text;
			   //System.out.println("key: "+key + " value: "+ value+ " participantCount: "+participantCount);
			   
			   if (i==size){
				   text = "None";
			   } else {
				   text = key + " : " + value;//"Option " + (i + 1) + " : " + answerCounts.get(i);
			   }
			   int top = (int)((float)((height-75)*((float)value/(float)participantCount)));
			   g.setColor(Color.BLACK);
			   g.drawString(text, buffer+((width/size))*i, height-stagger);
			   g.setColor(Color.BLUE);
			   g.fillRect(buffer+((width/size))*i, height-75-top, width/(2*size), top );
			   total = total - value;
			   i++;
			}
			if(total > 0){
				int top = (int)((float)((height-75)*((float)total/(float)participantCount)));
				g.setColor(Color.BLACK);
				g.drawString("No Answer : "+total, buffer+((width/size))*i, height - 50);
				g.setColor(Color.BLUE);
				g.fillRect(buffer+((width/size))*i, height-75-top, width/(2*size), top);
			}
		}

	}
	
	private Map<String, String> calculateValues(){
		Map<String, String> answers = currentValueMap;
		System.out.println("CurrentvalueMap keys: "+currentValueMap.keySet());
		return answers;
	/*	Iterator iteratePerson = currentValueMap.keySet().iterator();//  Person name, index#, value
		String iteratePersonNext = "";
		Map<String, Integer> totals = new HashMap<String, Integer>();
		//int totalCount = 0;
		String[] questionParts = currentQuestion.split("`/;");
		String[] widgets = questionParts[3].split("`/,");
		for (String widget : widgets){
			String[] widgetParts = widget.split("`/:");
			if(widgetParts[0].equals("B") || widgetParts[0].equals("TOG")){
				totals.put(widgetParts[1], 0);
			} else if (widgetParts[0].equals("COMBO")){
				//widgetParts[2] = options
				String[] comboOptions = widgetParts[2].split("`/~");
				for (String s : comboOptions){
					totals.put(s, 0);
				}
			}
		}
		while(iteratePerson.hasNext()){
			//totalCount++;
			iteratePersonNext = (String) iteratePerson.next();
			Iterator iterateIndex = currentValueMap.get(iteratePersonNext).keySet().iterator();
			String iterateIndexNext = "";
			while (iterateIndex.hasNext()){
				iterateIndexNext = (String) iterateIndex.next();
				String nextValue = currentValueMap.get(iteratePersonNext).get(iterateIndexNext);
				//System.out.println("nextValue: "+nextValue);
				if(!nextValue.equals(" ")){
					if(totals.containsKey(nextValue)){
						totals.put(nextValue, totals.get(nextValue) + 1);
					} else {
						totals.put(nextValue, 1);
					}
				}
			}
		}
		//participantCount = totalCount;*/
		//return totals;
	}	

	
	public void setID(String id) {
		groupID = id;
		frame.setTitle(id);
		
	}

	
	public void setQuestion(String question) {
		currentQuestion = question;
		String[] questionParts = question.split("`/&");
		String[] groupParts = questionParts[2].split("`/,");
		for(String s : groupParts){
			String[] st = s.split("`/:");
			if(st[0].equals(groupID)){
				participantCount = Integer.parseInt(st[1]);
			}
		}
		startGraph();
		
	}
}
