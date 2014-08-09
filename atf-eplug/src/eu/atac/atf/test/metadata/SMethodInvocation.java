package eu.atac.atf.test.metadata;

import java.util.ArrayList;
import java.util.List;
import eu.sarunas.atf.meta.sut.Method;

/**
 * @deprecated duplcates MethodCall class
 */
@Deprecated
public class SMethodInvocation extends SBase{
	private List<SParameter> parameters = new ArrayList<SParameter>(3);
	private Method method;
	private SVariable object;
	
	
	public SMethodInvocation(SVariable object,Method method) {
		super();
		this.method = method;
		this.object = object;
	}


	@Override
	protected void printElement(StringBuilder s) {
		s.append(object.getName()).append('.').append(method.getName()).append('(');
		
		for (int i = 0; i < parameters.size(); i++) {
			s.append(parameters.get(i).getName());
			if(i < parameters.size() - 1){
				s.append(',');
			}
		}
		
		s.append(')');
	}
	
	public void addParameter(SParameter parameter) {
		this.parameters.add(parameter);
	}
	
	public List<SParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<SParameter> parameters) {
		this.parameters = parameters;
	}

}
