package eu.sarunas.atf.generators.code.xml;

import java.util.ArrayList;
import java.util.List;

public class XmlElement
{
	private String value;
	private String tag;
	private List<XmlElement> childs = new ArrayList<XmlElement>();
	private List<String> parameters;

	public XmlElement(String tag)
	{
		super();
		this.tag = tag;
	}

	public XmlElement(String tag, String value)
	{
		super();
		this.value = value;
		this.tag = tag;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public void appendChild(XmlElement xmlElement)
	{
		childs.add(xmlElement);
	}

	public void addParam(String param)
	{
		if (parameters == null)
		{
			parameters = new ArrayList<String>(3);
		}
		parameters.add(param);
	}

	public int getParamCount()
	{
		if (parameters == null)
		{
			return 0;
		}
		return parameters.size();
	}

	public void toString(StringBuilder stringBuilder, String tab)
	{
		tab += " ";

		if (value != null)
		{
			stringBuilder.append("\n").append(tab).append('<').append(tag).append(">");

			if (!value.equals(""))
			{
				stringBuilder.append("<![CDATA[").append(value).append("]]>");
			}
			stringBuilder.append("</").append(tag).append(">");
		}
		else if (childs.size() > 0)
		{
			stringBuilder.append("\n").append(tab).append("<").append(tag);

			if (parameters != null)
			{
				for (String param : parameters)
				{
					stringBuilder.append(' ').append(param).append(' ');
				}
			}

			stringBuilder.append(">");

			for (int i = 0; i < childs.size(); i++)
			{
				childs.get(i).toString(stringBuilder, tab);
			}

			stringBuilder.append("\n").append(tab).append("</").append(tag).append(">");
		}
		else
		{
			if (parameters != null)
			{
				stringBuilder.append("\n").append(tab).append("<").append(tag);

				for (String param : parameters)
				{
					stringBuilder.append(' ').append(param).append(' ');
				}

				stringBuilder.append("/>");
			}
			else
			{
				stringBuilder.append("\n").append(tab).append('<').append(tag).append("/>");
			}
		}
	};

	private String stripAll(String s)
	{
		if (s == null || s.length() == 0)
		{
			return s;
		}
		char[] oldChars = new char[s.length()];
		s.getChars(0, s.length(), oldChars, 0);
		char[] newChars = new char[s.length()];
		int newLen = 0;
		for (int j = 0; j < s.length(); j++)
		{
			char ch = oldChars[j];
			if (ch >= ' ')
			{
				newChars[newLen] = ch;
				newLen++;
			}
		}
		s = new String(newChars, 0, newLen);
		return s;
	}
};
