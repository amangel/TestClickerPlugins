import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BarGraph extends JPanel implements ClickerConsumerInterface{

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
	private int yBuffer;
	private Map<String, Map<String, String>> currentValueMap;//  Person name, index#, value
	//private String currentValue;
	private Color steelBlue;
	
	public BarGraph(){
		currentQuestion = "";
		size=0;
		participantCount = 0;
		buffer = 75;
		yBuffer = 25;
		width = 600;
		height = 400;
		steelBlue = new Color(112, 146, 190);
		frame = new JFrame();
		frame.setSize(width+100, height+50);
		this.setMinimumSize(new Dimension(width, height));
		frame.add("Center", this);
		initializeGraph();
	}
	
	/**
	 * Any values that need initialized at startup.
	 */
	private void initializeGraph(){
		currentValueMap = Collections.synchronizedMap(new HashMap<String, Map<String, String>>());
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
	
	@Override
	public void setParent(ConsumerFrame parent) {
		this.parent = parent;
	}

	/**
	 * Declaring consumption is letting the admin know the consumer's name and what sort of data it expects.
	 * Can be either Count, Avg, All.
	 * If Avg, needs to specify how many values can be averaged.
	 */
	@Override
	public String declareConsumptions() {
		return "BarGraph`/:Count";
	}
	
	/**
	 * sets the frame active/inactive
	 */
	@Override
	public void setActiveStatus(boolean status) {
		if(!status){
			frame.setVisible(false);
			frame.dispose();
		}
	}

	@Override
	public boolean getActiveStatus() {
		return running;
	}

	/**
	 * Input from the IMP is received here
	 */
	@Override
	public void inputData(Map<String, Map<String, String>> input) {//  Person name, index#, value
		synchronized(currentValueMap){
			Iterator i = input.keySet().iterator();
			String iNext = "";
			while (i.hasNext()){
				iNext = (String) i.next();
				currentValueMap.put(iNext, input.get(iNext));
			}
		}
		repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		paintLabels(g);
		
		Map<String, String> answerCounts = new HashMap<String, String>();
		answerCounts = calculateValues();
		if(answerCounts != null){
			int total = 0;
			size = answerCounts.size() + 1;
			total = participantCount;
			int i = 0;
			
			TreeSet<String> keys = new TreeSet<String>(answerCounts.keySet());
			
			int stagger = 50;
			for (String key : keys) { 
			   Integer value = Integer.parseInt(answerCounts.get(key));
			   String text; 
			   if (i==size){
				   text = "None";
			   } else {
				   text = key + " : " + value;//"Option " + (i + 1) + " : " + answerCounts.get(i);
			   }
			   int top = (int)((float)((height-72)*((float)value/(float)participantCount)));
			   g.setColor(Color.BLACK);
			   g.drawString(text, buffer+((width/size))*i, height-stagger+yBuffer);
			   drawBar(g, buffer+((width/size))*i, height-72-top+yBuffer, width/(2*size), top);
			   total = total - value;
			   i++;
			}
			int top = height-72;
			if(total > 0){
				top = (int)((float)((height-72)*((float)total/(float)participantCount)));
				g.setColor(Color.BLACK);
				g.drawString("No Answer : "+total, buffer+((width/size))*i, height - 50 + yBuffer);
				drawBar(g, buffer+((width/size))*i, height-72-top + yBuffer, width/(2*size), top);
				
			} else {
				g.setColor(Color.BLACK);
				g.drawString("No Answer : "+total, buffer+((width/size))*i, height - 50 + yBuffer);
				drawBar(g, buffer+((width/size))*i, height-72+yBuffer, width/(2*size), 0);
			}
		}
	}
	
	private void drawBar(Graphics g, int x, int y, int wide, int high){
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(new GradientPaint(x-150,y,Color.white, x+200, y+200, steelBlue, false));
		Rectangle r = new Rectangle(x, y, wide, high);
		g2.fill(r);
		g.setColor(Color.black);
		g.drawRect(x, y, wide, high);
	}
	
	private void paintLabels(Graphics g) {
		int heightToUse = height-72;
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(50, heightToUse+yBuffer, width+50, heightToUse+yBuffer);
		g.drawLine(50, heightToUse+yBuffer, 50, yBuffer);
		g.drawLine(50, (heightToUse)/2+yBuffer, width+50, (heightToUse)/2+yBuffer);
		g.drawLine(50, yBuffer, width+50, yBuffer);
		g.drawLine(50, 3*(heightToUse)/4+yBuffer, width+50, 3*(heightToUse)/4+yBuffer);//25
		g.drawLine(50, (heightToUse)/4+yBuffer, width+50, (heightToUse)/4+yBuffer);//50
		g.setColor(Color.DARK_GRAY);
		g.drawString("0%", 30, height-67+yBuffer);
		g.drawString("25%", 20, 3*(heightToUse)/4+yBuffer+5);
		g.drawString("50%", 20, (heightToUse)/2+5+yBuffer);
		g.drawString("75%", 20, (heightToUse)/4+yBuffer+5);
		g.drawString("100%", 15, yBuffer+5);
	}

	private Map<String, String> calculateValues(){
		Map<String, String> answers = currentValueMap.get("Count");
		System.out.println("CurrentvalueMap keys: "+currentValueMap.keySet());
		return answers;
	}	

	@Override
	public void setID(String id) {
		groupID = id;
		frame.setTitle(id);
	}

	@Override
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
