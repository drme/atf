package eu.sarunas.atf.utils;

public class IXmlWriter
{
	public void openTag(String name)
	{
		System.out.print("\n<" + name + " ");
	};
	
	public void closeTag(String name)
	{
		System.out.println("</" + name + ">");
	};
	
	public void writeAttribute(String name, String value)
	{
		System.out.print(name + "=\"" + value + "\"");
	};
};
