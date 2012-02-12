import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Map;


public class LoggerPlugin implements ClickerConsumerInterface{

	private PrintWriter pw;
	private boolean running;
	private ConsumerFrame parent;
	private File currentLog;
	private String currentQuestion;
	
	public LoggerPlugin(){
		currentQuestion = "";
	}
	
	@Override
	public void setParent(ConsumerFrame parent) {
		this.parent = parent;
	}

	@Override
	public String declareConsumptions() {
		return "Logging";
	}

	@Override
	public void setActiveStatus(boolean status) {
		running = status;
		if(running){
			File logDirectory = new File("./logs");
			if(!logDirectory.exists()){
				if(logDirectory.mkdir()){
					System.out.println("Made a directory at: "+logDirectory.toString());
				} else {
					System.out.println("Log directory is: "+ logDirectory.toString());
				}
			}
			currentLog = new File("./logs/"+System.currentTimeMillis()+".txt");
			try {
				currentLog.createNewFile();
			} catch (IOException e) {
				System.out.println("Error creating new log file");
				e.printStackTrace();
			}
			try {
				pw = new PrintWriter(currentLog);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			currentQuestion = parent.getData();
		}
	}
	
	@Override
	public boolean getActiveStatus() {
		return running;
	}

	@Override
	public void inputData(Map<String, Map<String, String>> input) {
		String str = null;
		pw.println(new Timestamp(System.currentTimeMillis()).toString()+": "+str);
		pw.flush();
	}

	@Override
	public void setID(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setQuestion(String question) {
		
	}

}
