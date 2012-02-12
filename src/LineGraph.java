import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LineGraph extends JPanel implements ClickerConsumerInterface {

	private String currentQuestion;
	private JFrame parentFrame;
	private int width;
	private int height;
	private ConsumerFrame parent;
	private Map<String, Map<String, String>> currentValueMap;
	
	private float value1;
	private float value2;
	private Color weirdGray;
	private float equationValue1;
	private float equationValue2;
	private int promptCount; 
	
	public LineGraph(){
		
		equationValue1 = -2.0f;
		equationValue2 = 4.0f;
		promptCount = 0;
		value1 = 0f;
		value2 = 0f;
		currentQuestion = "";
		width = 400;
		height = 400;
		parentFrame = new JFrame();
		parentFrame.setSize(width+80, height+80);
		this.setMinimumSize(new Dimension(width, height));
		parentFrame.add("Center", this);
		weirdGray = new Color(0x6E6E6E);
		initializeGraph();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		drawGridLines(g);
		drawGridLabels(g);
		
		g.setColor(Color.BLUE);
		g.drawLine(200, 0, 200, 400);
		g.drawLine(0, 200, 400, 200);
		float lastx = 0;
		float lasty = 0;
		float scalar =10;
		float lastEQy = 0;
		
		for(int z = -200; z<=200; z++){
			g.setColor(Color.RED);
			float x = z/scalar;
			float y = (x)*(value1*scalar) + (value2*scalar);
			float xDraw = z+200;
			float eqY = (x) * (equationValue1*scalar) + (equationValue2 * scalar);
			
			if(z==-200){
				lastx = xDraw;
				lasty = dy(y);
				lastEQy = dy(eqY);
			}
			g.drawLine((int)lastx, (int)lastEQy, (int)xDraw, (int)dy(eqY));
			g.setColor(Color.BLACK);
			g.drawLine((int)lastx, (int)lasty, (int)xDraw, (int)dy(y));
			lastx = xDraw;
			lasty = dy(y);
			lastEQy = dy(eqY);
		}
		g.drawString((value1)+" + "+(value2)+"x", 250, 375);
		System.out.println("-200 : "+(int)(-20 * equationValue1) +" 200 :" +(int)(20*equationValue2));
		if((value1) == equationValue1 && (value2) == equationValue2 && promptCount == 0){
			promptCount++;
			JOptionPane.showMessageDialog(this, "Matched the equation!");
		}
	}
	private void drawGridLabels(Graphics g) {
		g.setColor(weirdGray);
		g.drawString("0", 202, 205);
		g.drawString("2", 202, 185);
		g.drawString("4", 202, 165);
		g.drawString("6", 202, 145);
		g.drawString("8", 202, 125);
		g.drawString("10", 202, 105);
		
		g.drawString("-2", 202, 225);
		g.drawString("-4", 202, 245);
		g.drawString("-6", 202, 265);
		g.drawString("-8", 202, 285);
		g.drawString("-10", 202, 305);
		
		g.drawString("2", 217, 214);
		g.drawString("4", 237, 214);
		g.drawString("6", 257, 214);
		g.drawString("8", 277, 214);
		g.drawString("10", 292, 214);
			
		g.drawString("-2", 177, 214);
		g.drawString("-4", 157, 214);
		g.drawString("-6", 137, 214);
		g.drawString("-8", 117, 214);
		g.drawString("-10", 92, 214);
		
	}

	private void drawGridLines(Graphics g) {	
		for (int i = 0; i < 400; i = i + 10){
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(0, i, 400, i);
			g.drawLine(i, 0, i, 400);
			
			g.setColor(Color.BLUE);
			g.drawLine(198, i, 202, i);
			g.drawLine(i, 198, i, 202);
		}	
	}

	private float dy(float y){
		return 200 - y;
	}

	private void initializeGraph() {
		currentValueMap = Collections.synchronizedMap(new HashMap<String, Map<String, String>>() );
	}

	@Override
	public void setParent(ConsumerFrame parent) {this.parent = parent;}

	@Override
	public void setID(String id) {
	}

	@Override
	public String declareConsumptions() {
		return "LineGraph`/:Avg`/:2";
	}

	@Override
	public void setActiveStatus(boolean status) {
		if(!status){
			parentFrame.setVisible(false);
			parentFrame.dispose();
		}
	}

	@Override
	public boolean getActiveStatus() {
		return false;
	}

	@Override
	public void inputData(Map<String, Map<String, String>> input) {
		synchronized(currentValueMap){
			currentValueMap = input;
		}
		System.out.println("input: "+input);
		
		try{
			value1 = Float.parseFloat(input.get("0").get("Average"));
			value2 = Float.parseFloat(input.get("1").get("Average"));
		} catch (Exception e){
			e.printStackTrace();
		}
		repaint();
	}

	@Override
	public void setQuestion(String question) {
		currentQuestion = question;
		parentFrame.setVisible(true);
	}

}
