package eu.sarunas.atf.utils;

import java.io.PrintStream;
import java.util.List;
import eu.sarunas.atf.meta.sut.Constructor;
import eu.sarunas.atf.meta.sut.Field;
import eu.sarunas.atf.meta.sut.Class;
import eu.sarunas.atf.meta.sut.ILibrary;
import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.atf.meta.sut.Modifier;
import eu.sarunas.atf.meta.sut.Parameter;
import eu.sarunas.atf.meta.sut.Project;
import eu.sarunas.atf.meta.sut.Package;

public class Printer
{
	
	public void print(Project project)
	{
		out.println("Begin Project: " + project.getName());

	//	for (ILibrary library : project.getLibraries())
		//{
			//print(library);
		//}
		
		out.println("End Project: " + project.getName());
	};
	
	public void print(ILibrary library)
	{
		out.println("Begin library: " + library.getName());
		
		for (Package p : library.getPackages())
		{
			print(p);
		}
		
		out.println("End library: " + library.getName());
	};
	
	public void print(Package packagee)
	{
		out.println("package " + packagee.getName() + "\n{");
		
		for (Class classs : packagee.getClasses())
		{
			print(classs);
		}
		
		out.println("\n}; //" + packagee.getName());
	};
	
	public void print(Class classs)
	{
		out.println(Modifier.toString(classs.getModifier()) + " class " + classs.getName() + "\n{");
		
		for (Constructor construtor : classs.getConstructors())
		{
			print(construtor);
		}
		
		for (Method method : classs.getMethods())
		{
			print(method);
		}
		
		for (Field field : classs.getFields())
		{
			print(field);
		}
		
		out.println("};\n");
	};
	
	public void print(Field field)
	{
		out.println(field.getModifiers().toString() + " " + field.getType().getName() + " " + field.getName() + ";");
	};
	
	public void print(Constructor constructor)
	{
		out.println(constructor.getModifier() + " " + constructor.getName() + "(" + formatParameters(constructor.getParameters()) + ")\n{");
		
		out.println("};\n");
	};

	public void print(Method method)
	{
		out.println(method.getModifier() + " " + method.getReturnType().getName() + " " + method.getName() + "(" + formatParameters(method.getParameters()) + ")\n{");
		
		out.println("};\n");
	};
	
	public String formatParameters(List<Parameter> parameters)
	{
		String params = "";
		
		if (parameters.size() > 0)
		{
			params += parameters.get(0).getType().getName() + " " + parameters.get(0).getName();
			
			for (int i = 1; i < parameters.size(); i++)
			{
				params += ", " + parameters.get(i).getType().getName() + " " + parameters.get(i).getName();
			}
		}
		
		params += "";
		
		return params;
	};
	
	private static PrintStream out = System.out;
};
