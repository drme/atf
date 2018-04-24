package eu.sarunas.atf.generators.model.dresden;

import java.util.ArrayList;
import java.util.List;
import tudresden.ocl20.pivot.pivotmodel.Parameter;
import tudresden.ocl20.pivot.pivotmodel.ParameterDirectionKind;
import tudresden.ocl20.pivot.pivotmodel.Type;
import tudresden.ocl20.pivot.pivotmodel.base.AbstractOperation;

class WrapperOperation extends AbstractOperation
{
	public WrapperOperation(eu.sarunas.atf.meta.sut.Method method, WrapperType parent, ProjectModel model)
	{
		this.method = method;
		this.parent = parent;
		this.model = model;
		this.model.addMethod(method, this);
		
		this.returnParameter = new WrapperParameter(this, ParameterDirectionKind.RETURN, null, model);

		for (eu.sarunas.atf.meta.sut.Parameter parameter : this.method.getParameters())
		{
			this.parameters.add(new WrapperParameter(this, ParameterDirectionKind.IN, parameter, model));
		}

		this.parameters.add(this.returnParameter);
	};

	@Override
	public String getName()
	{
		return this.method.getName();
	};

	@Override
	public List<Parameter> getOwnedParameter()
	{
		return this.parameters;
	};

	@Override
	public Type getOwningType()
	{
		return this.parent;
	};

	@Override
	public Type getType()
	{
		return this.model.getType(this.method.getReturnType(), null);
	};

	@Override
	public Parameter getReturnParameter()
	{
		return this.returnParameter;
	};

	public boolean isStatic()
	{
		return this.method.getModifier().contains(eu.sarunas.atf.meta.sut.Modifier.Static);
	};
	
	@Override
	public boolean hasMatchingSignature(List<Type> paramTypes)
	{
		// TODO Auto-generated method stub
		return super.hasMatchingSignature(paramTypes);
	}

	private List<Parameter> parameters = new ArrayList<>();
	private WrapperParameter returnParameter;
	private eu.sarunas.atf.meta.sut.Method method;
	private WrapperType parent;
	private ProjectModel model;
};
