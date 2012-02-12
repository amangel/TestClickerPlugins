import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class EchoPlugin implements ClickerConsumerInterface{
	private boolean running;
	private ConsumerFrame parent;
	private String currentQuestion;

	public EchoPlugin(){
		currentQuestion = "";
	}
	
	@Override
	public void setParent(ConsumerFrame parent) {
		this.parent = parent;
	}

	@Override
	public String declareConsumptions() {
		// TODO Auto-generated method stub
		return "Echo";
	}

	@Override
	public void setActiveStatus(boolean status) {
		running = status;
		if(running){
			currentQuestion = parent.getData();
		}
	}

	@Override
	public boolean getActiveStatus() {
		return running;
	}

	@Override
	public void inputData(Map<String, Map<String, String>> input) {
		//<person, <index, value> >
		System.out.println("input data called: "+System.currentTimeMillis());
		Iterator individualIterator = input.keySet().iterator();
		String answer = "";
		String next = "";
		String answerNext = "";
		Map<String, String> currentValue = null;
		while (individualIterator.hasNext()){
			next = (String) individualIterator.next();
			currentValue = input.get(next);
			Iterator answersIterator = currentValue.keySet().iterator();
			answer += next + ": ";
			while (answersIterator.hasNext()){
				answerNext = (String) answersIterator.next();
				answer += currentValue.get(answerNext);
			}
		}
		System.out.println(answer);
		
	}

	@Override
	public void setID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setQuestion(String question) {
		
	}

}
