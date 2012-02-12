import java.util.HashMap;
import java.util.Map;


public class pluginDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BargraphExternal bar = new BargraphExternal();
		BargraphPassthrough b = new BargraphPassthrough();
		//TextEntryDisplay b = new TextEntryDisplay();
		b.setID("Everyone");
		b.setQuestion("Open`/;34`/;e`/;B`/:A`/:0`/,B`/:B`/:0`/,B`/:C`/:0`/,B`/:D`/:0`/&BarGraph`/:Count`/,`/;`/&Everyone`/:2`/,");
		
		//(Map<String, Map<String, String>> input) {//  Person name, index#, value
		HashMap<String, String> person = new HashMap<String, String>();
		person.put("A", "1");
		person.put("B", "0");
		person.put("C", "0");
		person.put("D", "0");

		HashMap<String, Map<String, String>> bigPerson = new HashMap<String, Map<String, String>>();
		bigPerson.put("Count", person);
		b.inputData(bigPerson);
	}

}
