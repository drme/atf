package eu.sarunas.atf.utils;


public abstract class ISerializable
{
	public abstract void saveXml(IXmlWriter writer);
	public abstract void fromXml(String xml);
};
