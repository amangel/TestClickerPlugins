import java.awt.Color;
import java.awt.Graphics;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.JPanel;
import javax.swing.JFrame;


public class TextEntryDisplay extends JPanel implements ClickerConsumerInterface {

	private JFrame frame;
	private ConsumerFrame parent;
	private Map<String, Map<String, String>> currentValueMap;
	private int width;
	private int height;
	private String currentQuestion;
	private int participantCount;
	private String groupID;
	private int size;

	public TextEntryDisplay(){
		frame = new JFrame();
		currentValueMap = Collections.synchronizedMap(new HashMap<String, Map<String, String>>());
		width = 400;
		height = 400;
		size = 0;
		frame.setSize(width, height);
		frame.add(this);
	}
	
	private Map<String, String> calculateValues(){
		Map<String, String> answers = currentValueMap.get("Count");
		System.out.println("CurrentvalueMap keys: "+currentValueMap.keySet());
		return answers;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		int yBuffer = 25;
		int xBuffer = 25;
		
		Map<String, String> answerCounts = new HashMap<String, String>();
		answerCounts = calculateValues();
		g.setColor(Color.BLACK);
		if(answerCounts != null){
			int total = 0;
			size = answerCounts.size() + 1;
			total = participantCount;
			int i = 0;
			
			TreeSet<String> keys = new TreeSet<String>(answerCounts.keySet());
			for(String key : keys){
				Integer value = Integer.parseInt(answerCounts.get(key));
				if(value>0){
					g.drawString(key, xBuffer,  yBuffer + i*15);
					g.drawString(Integer.toString(value), xBuffer + height/2,yBuffer+ i*15);
					i++;
				}
			}
			
		}
	}
	
	@Override
	public void setParent(ConsumerFrame parent) {
		this.parent = parent;
	}

	@Override
	public void setID(String id) {
		groupID = id;
		frame.setTitle(id);
	}

	@Override
	public String declareConsumptions() {
		return "TextEntryDisplay`/:Count";
	}

	@Override
	public void setActiveStatus(boolean status) {
		if(!status){
			frame.setVisible(false);
			frame.dispose();
		}
	}

	@Override
	public boolean getActiveStatus() {
		return false;
	}

	@Override
	public void inputData(Map<String, Map<String, String>> input) {
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
	
	private void startGraph(){
		frame.setVisible(true);
	}
}
