package eu.sarunas.atf.generators.code.xsd;

import java.util.HashMap;
import org.apache.commons.lang.NotImplementedException;
import eu.sarunas.atf.generators.code.ICodeGenerator;
import eu.sarunas.atf.generators.code.xml.XmlElement;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.sut.Field;
import eu.sarunas.atf.meta.sut.Package;
import eu.sarunas.atf.meta.sut.Project;
import eu.sarunas.atf.meta.sut.basictypes.*;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class TransformerXSD implements ICodeGenerator
{
	@Override
	public String generateClass(Class cl)
	{
		return null;
	};

	private String getNameSpaceName(Package packge)
	{
		if (packge.getName() == null || packge.getName().trim().length() == 0)
		{
			return DEFAULT_PACKAGE_NAME;
		}
		else
		{
			return packge.getName().replaceAll("[\\._]", "");
		}
	};
	
	private String getClassName(Class clss, HashMap<Package, String> packageToNameSpaveMap, Package defaultPackage)
	{
		if (clss.getPackage() == defaultPackage)
		{
			return clss.getName();
		}
		else
		{
			return packageToNameSpaveMap.get(clss.getPackage()) + ":" + clss.getName();
		}
	};
	
	public String generateProject(Project project)
	{
		Package defaultPackage = project.getPackages().iterator().next();

		for (Package packge : project.getPackages())
		{
			if (packge.getClasses().size() > 0)
			{
				defaultPackage = packge;
				break;
			}
		}

		String targetNamespace = getNameSpaceName(defaultPackage);

		HashMap<Package, String> packageToNameSpaveMap = new HashMap<Package, String>();
		packageToNameSpaveMap.put(defaultPackage, targetNamespace);

		XmlElement xml = new XmlElement("xs:schema");

		xml.addParam("xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"");
		xml.addParam("elementFormDefault=\"qualified\"");
		xml.addParam("xmlns=\"urn:" + targetNamespace + "\"");
		xml.addParam("targetNamespace=\"urn:" + targetNamespace + "\"");

		int namespaceId = 0;

		for (Package packge : project.getPackages())
		{
			if (packge != defaultPackage)
			{
				xml.addParam("xmlns:ns" + namespaceId + "=\"urn:" + getNameSpaceName(packge) + "\"");
				packageToNameSpaveMap.put(packge, "ns" + namespaceId);
				namespaceId++;
			}
		}

		boolean clsObject = false;

		for (Package pckg : project.getPackages())
		{

			for (Class cls : pckg.getClasses())
			{
				if (cls.getName().equals("Object"))
				{
					clsObject = true;
				}

				XmlElement classTag = new XmlElement("xs:complexType");
				classTag.addParam("name=\"" + getClassName(cls, packageToNameSpaveMap, defaultPackage) + "\"");
				XmlElement parrentField = classTag;

				if (cls.getSuperClass() != null)
				{
					XmlElement complexContentTag = new XmlElement("xs:complexContent");
					XmlElement extensionTag = new XmlElement("xs:extension");
					extensionTag.addParam("base=\"" + getClassName((Class) cls.getSuperClass(), packageToNameSpaveMap, defaultPackage) + "\"");// type -> base
					complexContentTag.appendChild(extensionTag);
					classTag.appendChild(complexContentTag);
					parrentField = extensionTag;
				}
				// ???
				// else
				// {
				// XMLElement complexContentTag = new XMLElement("xs:complexContent");
				// XMLElement extensionTag = new XMLElement("xs:extension");
				// extensionTag.addParam("base=\"Object\"");//type -> base
				// complexContentTag.addChild(extensionTag);
				// classTag.addChild(complexContentTag);
				// parrentField = extensionTag;
				// }

				if (cls.getFields().size() > 0)
				{
					XmlElement sequenceTag = new XmlElement("xs:sequence");
					parrentField.appendChild(sequenceTag);

					for (Field field : cls.getFields())
					{
						XmlElement elementTag = new XmlElement("xs:element");
						elementTag.addParam("name=\"" + field.getName() + "\"");
						Type fieldType = field.getType();

						if (fieldType instanceof CollectionType)
						{
							CollectionType ct = (CollectionType) fieldType;
							if (ct.getEnclosingType() instanceof CollectionType)
							{
								XmlElement rootElementTag = elementTag;
								rootElementTag.addParam(" minOccurs=\"0\" ");
								rootElementTag.addParam(" maxOccurs=\"unbounded\" ");

								fieldType = ct.getEnclosingType();
								while (fieldType instanceof CollectionType)
								{
									XmlElement complexTypeMultiTag = new XmlElement("xs:complexType");
									XmlElement sequenceMultiTag = new XmlElement("xs:sequence");

									complexTypeMultiTag.appendChild(sequenceMultiTag);
									elementTag.appendChild(complexTypeMultiTag);

									ct = (CollectionType) fieldType;
									fieldType = ct.getEnclosingType();

									elementTag = sequenceMultiTag;
								}
								XmlElement elementMultiTag = new XmlElement("xs:element");
								elementMultiTag.addParam("name=\"" + field.getName() + "\"");// ?
								elementMultiTag.addParam(" minOccurs=\"0\" ");
								elementMultiTag.addParam(" maxOccurs=\"unbounded\" ");

								elementMultiTag.addParam(" type=\"" + getXsdType(ct.getEnclosingType(), packageToNameSpaveMap, defaultPackage) + "\" ");
								rootElementTag.addParam(" type=\"" + getXsdType(ct.getEnclosingType(), packageToNameSpaveMap, defaultPackage) + "\" ");

								elementTag.appendChild(elementMultiTag);

								elementTag = rootElementTag;
							}
							else
							{
								elementTag.addParam(" type=\"" + getXsdType(ct.getEnclosingType(), packageToNameSpaveMap, defaultPackage) + "\" ");
								elementTag.addParam(" minOccurs=\"0\" ");
								elementTag.addParam(" maxOccurs=\"unbounded\" ");
							}
						}
						else
						{
							elementTag.addParam("type=\"" + getXsdType(fieldType, packageToNameSpaveMap, defaultPackage) + "\"");
						}

						sequenceTag.appendChild(elementTag);
					}
				}
				xml.appendChild(classTag);
			}
		}

		if (!clsObject)
		{
			XmlElement complexTag = new XmlElement("xs:complexType");
			complexTag.addParam("name=\"Object\"");
			xml.appendChild(complexTag);
		}

		// XMLElement textTag = new XMLElement("xs:simpleType");
		// textTag.addParam("name=\"Text\"");
		// XMLElement restrictionTag = new XMLElement("xs:restriction");
		// restrictionTag.addParam("base=\"xs:string\"");
		// XMLElement minlTag = new XMLElement("xs:minLength");
		// minlTag.addParam("value=\"1\"");
		// XMLElement maxlTag = new XMLElement("xs:maxLength");
		// maxlTag.addParam("value=\"100\"");
		// textTag.addChild(restrictionTag);
		// textTag.addChild(minlTag);
		// textTag.addChild(maxlTag);
		// xml.addChild(textTag);

		XmlElement headTag = new XmlElement("xs:complexType");
		headTag.addParam("name=\"HEAD\"");

		XmlElement sequenceTag = new XmlElement("xs:sequence");
		headTag.appendChild(sequenceTag);

		XmlElement choiceTag = new XmlElement("xs:choice");
		sequenceTag.appendChild(choiceTag);
		sequenceTag = choiceTag;

		for (Package pckg : project.getPackages())
		{
			for (Class cls : pckg.getClasses())
			{
				XmlElement elementTag = new XmlElement("xs:element");
				elementTag.addParam("name=\"" + getClassName(cls, packageToNameSpaveMap, defaultPackage) + "\"");
				elementTag.addParam("type=\"" + getClassName(cls, packageToNameSpaveMap, defaultPackage) + "\"");
				elementTag.addParam("minOccurs=\"0\"");
				elementTag.addParam("maxOccurs=\"unbounded\"");
				sequenceTag.appendChild(elementTag);
			}
		}

		xml.appendChild(headTag);

		StringBuilder stringBuilder = new StringBuilder(1000);
		stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

		xml.toString(stringBuilder, "");

		return stringBuilder.toString();
	};
	
	private String getXsdType(Type fieldType, HashMap<Package, String> packageToNameSpaveMap, Package defaultPackage)
	{
		if (fieldType instanceof Class)
		{
			return getClassName((Class) fieldType, packageToNameSpaveMap, defaultPackage);
		}
		else if (fieldType instanceof IntegerType)
		{
			return "xs:integer";
		}
		else if (fieldType instanceof BooleanType)
		{
			return "xs:boolean";
		}
		else if (fieldType instanceof StringType)
		{
			return "xs:string";
		}
		else if (fieldType instanceof DoubleType)
		{
			return "xs:double";
		}
		else if (fieldType instanceof LargeNumberType)
		{
			return "xs:double";
		}
		else if (fieldType instanceof FloatType)
		{
			return "xs:double";
		}
		else if (fieldType instanceof LongType)
		{
			return "xs:long";
		}
		else if (fieldType instanceof ByteType)
		{
			return "xs:byte";
		}
		else if (fieldType instanceof ShortType)
		{
			return "xs:short";
		}
		else if (fieldType instanceof CharType)
		{
			return "xs:string";
		}
		// else if (fieldType instanceof CollectionType)
		// {
		// }
		else if (fieldType instanceof DateType)
		{
			return "xs:date";
		}
		else if (fieldType instanceof ObjectType)
		{
			// return "xs:anyType";
			return "Object";
		}
		else
		{
			// return "string";
			throw new NotImplementedException("TransformerXSD.getXSDType():" + fieldType.getClass());
		}
	};

	public static final String DEFAULT_PACKAGE_NAME = "DEFAULT";
};
