import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class BargraphPassthrough implements ClickerConsumerInterface {

	private boolean status;
	private PrintWriter pw;

	public BargraphPassthrough(){
		System.out.println("passthrough created");
		try {
			Socket s = new Socket("134.161.137.190", 9745);//134.161.44.167
			pw = new PrintWriter(s.getOutputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void sendMessage(String s){
		pw.println(s);
		pw.flush();
	}
	
	@Override
	public void setParent(ConsumerFrame parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setID(String id) {
		sendMessage("ID`!:"+id);
	}

	@Override
	public String declareConsumptions() {
		return "BarGraphPassthrough`/:Count";
	}

	@Override
	public void setActiveStatus(boolean status) {
		if(!status){
			sendMessage("STATUS`!:false");
		} else {
			sendMessage("STATUS`!:true");
		}
		this.status = status;
	}

	@Override
	public boolean getActiveStatus() {
		return status;
	}

	@Override
	public void inputData(Map<String, Map<String, String>> input) {
		String iNext = "";
		String toSend = "ANSWERS`!:";
		Map<String, String> next = input.get("Count");
		Iterator i = next.keySet().iterator();
		System.out.println(next);
		while(i.hasNext()){
			iNext = (String) i.next();
			toSend += iNext+","+next.get(iNext)+";";
		}
		System.out.println(toSend);
		
		sendMessage(toSend);
	}

	@Override
	public void setQuestion(String question) {
		sendMessage("QUESTION`!:"+question);
	}

}
