package eu.sarunas.atf.meta.sut.body;

public class ArrayElementAssignment implements ICodeBodyElement
{
	public ArrayElementAssignment(ArrayConstruct array, int index, Object value)
	{
		this.array = array;
		this.value = value;
		this.index = index;
	};

	public ArrayConstruct getArray()
	{
		return this.array;
	};

	public Object getValue()
	{
		return this.value;
	};
	
	public int getIndex()
	{
		return this.index;
	};

	private ArrayConstruct array = null;
	private Object value = null;
	private int index = 0;
};
