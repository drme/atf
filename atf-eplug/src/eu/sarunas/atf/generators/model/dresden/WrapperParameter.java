package eu.sarunas.atf.generators.model.dresden;

import eu.sarunas.atf.meta.sut.Parameter;
import tudresden.ocl20.pivot.pivotmodel.Operation;
import tudresden.ocl20.pivot.pivotmodel.ParameterDirectionKind;
import tudresden.ocl20.pivot.pivotmodel.Type;
import tudresden.ocl20.pivot.pivotmodel.base.AbstractParameter;

class WrapperParameter extends AbstractParameter
{
	public WrapperParameter(WrapperOperation operation, ParameterDirectionKind direction, Parameter parameter, ProjectModel model)
	{
		this.operation = operation;
		this.direction = direction;
		this.model = model;
		this.parameter = parameter;
	};

	public ParameterDirectionKind getKind()
	{
		return this.direction;
	};

	@Override
	public String getName()
	{
		return this.parameter.getName();
	};

	@Override
	public Operation getOperation()
	{
		return this.operation;
	};

	@Override
	public Type getType()
	{
		return this.model.getType(this.parameter.getType(), null);
	};

	private Parameter parameter;
	private ProjectModel model;
	private WrapperOperation operation;
	private ParameterDirectionKind direction;
};
