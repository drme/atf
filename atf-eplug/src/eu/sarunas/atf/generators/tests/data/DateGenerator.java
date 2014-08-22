package eu.sarunas.atf.generators.tests.data;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import eu.sarunas.atf.meta.sut.basictypes.DateType;
import eu.sarunas.atf.model.checker.ITestDataValidator;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class DateGenerator extends ITypeGenerator
{
	public DateGenerator(Randomizer randomizer)
	{
		super(randomizer);
	};

	public Object generate(Type type, ITestDataValidator validator)
	{
		DateType dateType = (DateType)type;
		
		if (dateType.getNativeType() == "XMLGregorianCalendar")
		{
			try
            {
				GregorianCalendar c = new GregorianCalendar();
				c.setTime(new Date(generate(1000, 1564654)));
				
	            return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            }
            catch (DatatypeConfigurationException ex)
            {
	            ex.printStackTrace();
            }
		}

		return new Date(generate(1000, 1564654));
	};
	
	private long generate(long min, long max)
	{
		double value = min + (max - min) * this.randomizer.getDistribution().getRandomValue();

		return (long)value;
	};	
};
