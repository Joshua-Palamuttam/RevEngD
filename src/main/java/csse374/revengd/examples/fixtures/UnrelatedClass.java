package csse374.revengd.examples.fixtures;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnrelatedClass {
	List<String> genericList;
	List<List<List<String>>> weirdList;

	Map<String, Integer> randomMap;
	Map<String, Map<Integer, List<Map<String, Double>>>> weirdMap;
	
	List<String>[] listArray;
	Map<String, Integer>[] singleMap;

	List<String>[][] listDoubleArray;
	int[] intArray;
	
	Map<String, List<String>[]> mapListArray;
	
	int hiInt;
	String myString;
	
	List<CalculatorA> ca;

	public UnrelatedClass() {
		this.genericList = Arrays.asList("Aloha! What a beautiful day!", "Hey!");
		this.randomMap = new HashMap<>();
	}
	
	public Map<String, Integer> checkObject1(List<String> value2) {
		return null;
	}


	public String checkObject2(String[] value1, Map<Integer, List<String>> value2, Double[][] value3) {
		return null;
	}

	
	public Map<String, Integer> getMap(List<String> list, Map<String, Integer> map, int i[], double j) {
		return this.randomMap;
	}
	
	public void sayConditionalHello(boolean goodMood, CalculatorA ca) {
		if(goodMood)
			System.out.println(genericList.get(0));
		else
			System.out.println(genericList.get(1));
		
		System.out.println("Just adding some numbers: " + new CalculatorA().add(1,2,3));
	}
	
	public List<CalculatorB> doIt() {
		return null;
	}
}
