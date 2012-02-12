import java.util.Map;


public class TestClickerPlugin implements ClickerConsumerInterface{
	
	private boolean running;
	private ConsumerFrame parent;
	
	public TestClickerPlugin(){
		running = false;
	}

	@Override
	public void setParent(ConsumerFrame parent) {
		this.parent = parent;
	}

	@Override
	public String declareConsumptions() {
		return "testConsumption";
	}

	@Override
	public void setActiveStatus(boolean status) {
		running = status;
	}

	@Override
	public boolean getActiveStatus() {
		return running;
	}

	@Override
	public void inputData(Map<String, Map<String, String>> input) {
		System.out.println("TestPlugin received data and didn't do anything with it. Bwahahaha!~");
		
	}

	@Override
	public void setID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setQuestion(String question) {
		
	}
}
