package eu.atac.atf.test.metadata;

import java.util.ArrayList;
import java.util.List;

import eu.atac.atf.main.ATFException;

public class MethodObjectRepository {
	private int id = 10000;
	private List<SVariable> variables = new ArrayList<SVariable>();
	
	
	public boolean existByName(String name){
		for (int i = 0; i < variables.size(); i++) {
			if(variables.get(i).getName().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	public SVariable getByName(String name){
		for (int i = 0; i < variables.size(); i++) {
			if(variables.get(i).getName().equals(name)){
				return variables.get(i);
			}
		}
		throw new ATFException(ATFException.ATF_ERROR_CODE_1000, name);
	}
	
	public void addVariable(SVariable variable){
		variables.add(variable);
	}
	
	public int generateNextId(){
		return ++id;
	}
}
