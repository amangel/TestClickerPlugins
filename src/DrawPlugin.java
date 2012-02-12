import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class DrawPlugin implements ClickerConsumerInterface{
	private boolean running;
	private ConsumerFrame parent;
	private JFrame jf;
	private JLabel label;
	private JLabel questionLabel;
	private String currentQuestion;
	
	public DrawPlugin(){
		currentQuestion = "";
		jf = new JFrame();
	}
	
	@Override
	public void setParent(ConsumerFrame parent) {
		this.parent = parent;
	}
	
	@Override
	public String declareConsumptions() {
		// TODO Auto-generated method stub
		return "drawing";
	}

	@Override
	public void setActiveStatus(boolean status) {
		running = status;
		if(running){
			jf = new JFrame();
			jf.setSize(200,200);
			JPanel labelPanel = new JPanel();
			labelPanel.setLayout(new GridLayout(2,1));
			label = new JLabel();
			questionLabel = new JLabel();
			
			labelPanel.add(questionLabel);
			labelPanel.add(label);
			jf.add(labelPanel);
			currentQuestion = parent.getData();
			questionLabel.setText(currentQuestion);
		} else {
			jf.setVisible(false);
		}
	}
	
	@Override
	public boolean getActiveStatus() {
		return running;
	}
	
	@Override
	public void inputData(Map<String, Map<String, String>> input) {
		String str = null;
		label.setText(str);
		jf.repaint();
	}

	@Override
	public void setID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setQuestion(String question) {
		
	}
}
