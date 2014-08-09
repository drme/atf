package eu.sarunas.projects.atf.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import eu.sarunas.atf.meta.tests.TestProject;
import eu.sarunas.atf.meta.tests.TestSuite;

import junit.extensions.TestSetup;

public class DataBase
{
	public DataBase()
	{
		this.emf = Persistence.createEntityManagerFactory("testsDbPersistence");
		this.em = this.emf.createEntityManager();
	};

	protected void finalize()
	{
		this.em.close();
		this.emf.close();
	};
	
	public static DataBase getInstance()
	{
		if (null == instance)
		{
			instance = new DataBase();
		}
		
		return instance;
	};
	
	@SuppressWarnings("unchecked")
	public List<TestProject> getProjects()
	{
		ArrayList<TestProject> result = new ArrayList<TestProject>();
		
		Query query = this.em.createNativeQuery("SELECT DISTINCT tp.pavadinimas FROM test_projektas tp");

		List<String> projects = query.getResultList();
		
		for (String prj : projects)
		{
			result.add(loadTests(prj));
		}
		
		return result;		
	};
	
	@SuppressWarnings("unchecked")
	public TestProject loadTests(String project)
	{
		TestProject result = new TestProject();
		result.setName(project);
		
		Query query = this.em.createQuery("SELECT tp FROM test_projektas tp WHERE tp.name = ?1");
		query.setParameter(1, project);

		List<TestProject> tps = query.getResultList();
		
		for (TestProject tp : tps)
		{
			result.getTestSuites().addAll(tp.getTestSuites());
		}
		
		return result;
	}
	
	public void saveTests(TestProject tp)
	{
		EntityTransaction tx = this.em.getTransaction();
		
		try
		{
			tx.begin();
			
			this.em.merge(tp);
			
			/*for (TestSuite ts : tp.getTestSuites())
			{
				ts.setTestProject(tp);
				
				this.em.merge(ts);
			}*/
			
			tx.commit();
		}
		catch (Throwable ex)
		{
			ex.printStackTrace();
			
			tx.rollback();
		}
	};
	
	@SuppressWarnings("unchecked")
	public void removeTests(String project)
	{
		EntityTransaction tx = this.em.getTransaction();
		
		try
		{
			tx.begin();
			
			Query query = this.em.createQuery("SELECT tp FROM test_projektas tp WHERE tp.name = ?1");
			query.setParameter(1, project);

			List<TestProject> tps = query.getResultList();
			
			for (TestProject tp : tps)
			{
				this.em.remove(tp);
			}
			
			tx.commit();
		}
		catch (Throwable ex)
		{
			ex.printStackTrace();
			
			tx.rollback();
		}
	};
	
	private static DataBase instance = null;
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
};
