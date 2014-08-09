package eu.sarunas.atf.generators.code.xsd;

import org.apache.commons.lang.NotImplementedException;
import eu.atac.atf.main.XMLElement;
import eu.sarunas.atf.generators.code.ICodeGenerator;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.sut.Field;
import eu.sarunas.atf.meta.sut.Package;
import eu.sarunas.atf.meta.sut.basictypes.*;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class TransformerXSD implements ICodeGenerator
{
	@Override
	public String generateClass(Class cl)
	{
		return null;
	};

	public String generatePackage(Package pckg)
	{
		String namespace = null;

		if (pckg.getName() == null || pckg.getName().trim().length() == 0)
		{
			namespace = DEFAULT_PACKAGE_NAME;
		}
		else
		{
			namespace = pckg.getName().replaceAll("[\\._]", "");
		}

		XMLElement xml = new XMLElement("xs:schema");
		
		xml.addParam("xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"");
		xml.addParam("elementFormDefault=\"qualified\"");
		xml.addParam("xmlns=\"urn:" + namespace + "\"");
		xml.addParam("targetNamespace=\"urn:" + namespace + "\"");
		
		boolean clsObject = false;

		for (Class cls : pckg.getClasses())
		{
			if(cls.getName().equals("Object")){
				clsObject = true;
			}
			
			XMLElement classTag = new XMLElement("xs:complexType");
			classTag.addParam("name=\"" + cls.getName() + "\"");
			XMLElement parrentField = classTag;

			if (cls.getSuperClass() != null)
			{
				XMLElement complexContentTag = new XMLElement("xs:complexContent");
				XMLElement extensionTag = new XMLElement("xs:extension");
				extensionTag.addParam("base=\"" + cls.getSuperClass().getName() + "\"");//type -> base
				complexContentTag.addChild(extensionTag);
				classTag.addChild(complexContentTag);
				parrentField = extensionTag;
			}
//???			
//			else
//			{
//				XMLElement complexContentTag = new XMLElement("xs:complexContent");
//				XMLElement extensionTag = new XMLElement("xs:extension");
//				extensionTag.addParam("base=\"Object\"");//type -> base
//				complexContentTag.addChild(extensionTag);
//				classTag.addChild(complexContentTag);
//				parrentField = extensionTag;
//			}

			if (cls.getFields().size() > 0)
			{
				XMLElement sequenceTag = new XMLElement("xs:sequence");
				parrentField.addChild(sequenceTag);

				for (Field field : cls.getFields())
				{
					XMLElement elementTag = new XMLElement("xs:element");
					elementTag.addParam("name=\"" + field.getName() + "\"");
					Type fieldType = field.getType();
					
					if (fieldType instanceof CollectionType)
					{
						CollectionType ct = (CollectionType) fieldType;
						if(ct.getEnclosingType() instanceof CollectionType)
						{
							XMLElement rootElementTag = elementTag;
							rootElementTag.addParam(" minOccurs=\"0\" ");
							rootElementTag.addParam(" maxOccurs=\"unbounded\" ");
							
							fieldType = ct.getEnclosingType();
							while(fieldType instanceof CollectionType)
							{
								XMLElement complexTypeMultiTag = new XMLElement("xs:complexType");
								XMLElement sequenceMultiTag = new XMLElement("xs:sequence");
								
								complexTypeMultiTag.addChild(sequenceMultiTag);
								elementTag.addChild(complexTypeMultiTag);
								
								ct = (CollectionType) fieldType; 
								fieldType = ct.getEnclosingType();
								
								elementTag = sequenceMultiTag;
							}
							XMLElement elementMultiTag = new XMLElement("xs:element");
							elementMultiTag.addParam("name=\"" + field.getName() + "\"");//?
							elementMultiTag.addParam(" minOccurs=\"0\" ");
							elementMultiTag.addParam(" maxOccurs=\"unbounded\" ");
							
							elementMultiTag.addParam(" type=\"" + getXSDType(ct.getEnclosingType()) + "\" ");
							rootElementTag.addParam(" type=\"" + getXSDType(ct.getEnclosingType()) + "\" ");
							
							elementTag.addChild(elementMultiTag);
							
							elementTag = rootElementTag;
						}
						else
						{
							elementTag.addParam(" type=\"" + getXSDType(ct.getEnclosingType()) + "\" ");
							elementTag.addParam(" minOccurs=\"0\" ");
							elementTag.addParam(" maxOccurs=\"unbounded\" ");
						}
					}
					else
					{
						elementTag.addParam("type=\""+ getXSDType(fieldType)+ "\"");
					}
					
					
					sequenceTag.addChild(elementTag);
				}
			}
			xml.addChild(classTag);
		}
		
		if(!clsObject){
			XMLElement complexTag = new XMLElement("xs:complexType");
			complexTag.addParam("name=\"Object\"");
			xml.addChild(complexTag);
		}
		
		
//		XMLElement textTag = new XMLElement("xs:simpleType");
//		textTag.addParam("name=\"Text\"");
//		XMLElement restrictionTag = new XMLElement("xs:restriction");
//		restrictionTag.addParam("base=\"xs:string\"");
//		XMLElement minlTag = new XMLElement("xs:minLength");
//		minlTag.addParam("value=\"1\"");
//		XMLElement maxlTag = new XMLElement("xs:maxLength");
//		maxlTag.addParam("value=\"100\"");
//		textTag.addChild(restrictionTag);
//		textTag.addChild(minlTag);
//		textTag.addChild(maxlTag);
//		xml.addChild(textTag);
		
		
		XMLElement headTag = new XMLElement("xs:complexType");
		headTag.addParam("name=\"HEAD\"");
		XMLElement sequenceTag = new XMLElement("xs:sequence");
		headTag.addChild(sequenceTag);
		
		XMLElement choiceTag = new XMLElement("xs:choice");
		sequenceTag.addChild(choiceTag);
		sequenceTag = choiceTag;
		
		for (Class cls : pckg.getClasses())
		{
			XMLElement elementTag = new XMLElement("xs:element");
			elementTag.addParam("name=\"" + cls.getName() + "\"");
			elementTag.addParam("type=\"" + cls.getName() + "\"");
			elementTag.addParam("minOccurs=\"0\"");
			elementTag.addParam("maxOccurs=\"unbounded\"");
			sequenceTag.addChild(elementTag);
		}
		xml.addChild(headTag);	
		
		StringBuilder s = new StringBuilder(1000);
		s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		xml.toString(s, "");
		return  s.toString();
	};
	
	private String getXSDType(Type fieldType){
		if (fieldType instanceof Class)
		{
			return fieldType.getName();
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
			//return "Text";
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
//		else if (fieldType instanceof CollectionType)
//		{
//		}
		else if (fieldType instanceof DateType)
		{
			return "xs:date";
		}
		else if (fieldType instanceof ObjectType)
		{
//			return "xs:anyType";
			return "Object";
		}
		else
		{
			//return "string";
			throw new NotImplementedException("TransformerXSD.getXSDType():" + fieldType.getClass());
		}
	}

	public static final String DEFAULT_PACKAGE_NAME = "DEFAULT";
};
