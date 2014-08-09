package eu.sarunas.projects.atf.eclipse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import eu.sarunas.atf.meta.tests.TestCase;
import eu.sarunas.atf.meta.tests.TestInput;
import eu.sarunas.atf.meta.tests.TestInputParameter;
import eu.sarunas.atf.meta.tests.TestProject;
import eu.sarunas.atf.meta.tests.TestSuite;
import eu.sarunas.projects.atf.db.DataBase;

public class NavigationView extends ViewPart
{
	public void createPartControl(Composite parent)
	{
		this.viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		this.viewer.setContentProvider(new ViewContentProvider());
		this.viewer.setLabelProvider(new ViewLabelProvider());
		this.viewer.setInput(createModel());
		
		hookContextMenu();
	};

	private void hookContextMenu()
	{
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener()
		{
			public void menuAboutToShow(IMenuManager manager)
			{
				NavigationView.this.fillContextMenu(manager);
			};
		});

		Menu menu = menuMgr.createContextMenu(this.viewer.getControl());
		this.viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, this.viewer);
	};
	
	private void fillContextMenu(IMenuManager manager)
	{
		manager.add(new RefreshAction(this.viewer, this.projects));
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	};	
	
    private TreeObject createModel()
    {
    	this.projects = new TreeParent("Projects", null);   	
    	
    	TreeParent root = new TreeParent("Root", null);
        
    	root.addChild(projects);
        
        return root;
    };

	public void setFocus()
	{
		this.viewer.getControl().setFocus();
	};
	
	public static final String ID = "eu.sarunas.projects.atf.eclipse.navigationView";
	private TreeViewer viewer;
	private TreeParent projects = null;
};

class TreeObject
{
	public TreeObject(String name, Object tag)
	{
		this.name = name;
		this.tag = tag;
	};
	
	public String getName()
	{
		return name;
	};
	
	public void setParent(TreeParent parent)
	{
		this.parent = parent;
	};
	
	public TreeParent getParent()
	{
		return this.parent;
	};
	
	public String toString()
	{
		return getName();
	};
	
	public Object getTag()
	{
		return this.tag;
	};
	
	public void clearChildren()
	{
	};
	
	private String name = null;
	private TreeParent parent = null;
	protected Object tag = null;
};

class TreeParent extends TreeObject
{
	public TreeParent(String name, Object tag)
	{
		super(name, tag);
	};
	
	public void addChild(TreeObject child)
	{
		if (null == this.children)
		{
			this.children = new ArrayList<TreeObject>();
		}
		
		this.children.add(child);
		
		child.setParent(this);
	};
	
//	public void removeChild(TreeObject child)
//	{
//		children.remove(child);
//		child.setParent(null);
//	};
	
	public void clearChildren()
	{
		this.children = null;
	};
	
	public TreeObject[] getChildren()
	{
		if (null == this.children)
		{
			this.children = new ArrayList<TreeObject>();
			
			if (null == this.tag)
			{
				for (TestProject project : DataBase.getInstance().getProjects())
				{
					addChild(new TreeParent(project.getName(), project));
				}
			}
			else if (this.tag instanceof TestProject)
			{
				for (TestSuite ts : ((TestProject)this.tag).getTestSuites())
				{
					addChild(new TreeParent(ts.getName()+ "_" + ts.getId(), ts));
				}
			}
			else if (this.tag instanceof TestSuite)
			{
				for (TestCase tc : ((TestSuite)this.tag).getTestCases())
				{
					addChild(new TreeParent(tc.getClassName() + "_" + tc.getClassName(), tc));
				}
			}
			else if (this.tag instanceof TestCase)
			{
				for (TestInput ti : ((TestCase)this.tag).getInputs())
				{
					addChild(new TreeParent(ti.getTestCase().getMethod() + "_" + ti.getId(), ti));
				}
			}
			else if (this.tag instanceof TestInput)
			{
				for (TestInputParameter tip : ((TestInput)this.tag).getInputParameters())
				{
					addChild(new TreeObject(tip.getName() + " = " + tip.getValue(), tip));
				}
			}
		}
		
		return (TreeObject[])this.children.toArray(new TreeObject[children.size()]);
	};
	
	public boolean hasChildren()
	{
		return ((null != getChildren()) && (getChildren().length > 0));
	};

	private List<TreeObject> children = null;
};

class RefreshAction extends AbstractTreeNodeAction
{
	public RefreshAction(TreeViewer viewer, TreeParent root)
	{
		super(viewer, "Refresh", "Reloads elements from database", /*ViewLabelProvider.imageRefreshDescriptor*/ null);
		
		this.root = root;
	};

	public void run()
	{
		//for (TreeObject node : getNodes())
		//{
		//	node.clearChildren();
		//}
		
		this.root.clearChildren();
		this.viewer.refresh();
	};
	
	private TreeParent root = null;
};

abstract class AbstractTreeNodeAction extends Action
{
	protected AbstractTreeNodeAction(TreeViewer viewer, String name, String toolTip, ImageDescriptor image)
	{
		this.viewer = viewer;
		setText(name);
		setToolTipText(toolTip);
		setImageDescriptor(image);		
	};
	
	@SuppressWarnings("unchecked")
	protected List<TreeObject> getNodes()
	{
		IStructuredSelection selection = (IStructuredSelection)this.viewer.getSelection();
		
		List<TreeObject> nodes = new ArrayList<TreeObject>();

		for (Iterator it = selection.iterator(); it.hasNext();)
		{
			Object o = it.next();

			if (o instanceof TreeObject)
			{
				nodes.add((TreeObject)o);
			}
		}

		return nodes;
	};
	
	@SuppressWarnings("unchecked")
	protected List getItems(Class<? extends Object> type)
	{
		ArrayList items = new ArrayList();
		
		for (TreeObject node : getNodes())
		{
			if (null != node.getTag())
			{
				if (true == type.equals(node.getTag().getClass()))
				{
					items.add(node.getTag());
				}
			}
		}
		
		return items;
	};
	
	protected TreeViewer viewer = null;
};



class ViewContentProvider implements IStructuredContentProvider, 
									   ITreeContentProvider {

    public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}
    
	public void dispose() {
	}
    
	public Object[] getElements(Object parent) {
		return getChildren(parent);
	}
    
	public Object getParent(Object child) {
		if (child instanceof TreeObject) {
			return ((TreeObject)child).getParent();
		}
		return null;
	}
    
	public Object[] getChildren(Object parent) {
		if (parent instanceof TreeParent) {
			return ((TreeParent)parent).getChildren();
		}
		return new Object[0];
	}

    public boolean hasChildren(Object parent) {
		if (parent instanceof TreeParent)
			return ((TreeParent)parent).hasChildren();
		return false;
	}
}

class ViewLabelProvider extends LabelProvider {

	public String getText(Object obj) {
		return obj.toString();
	}
	public Image getImage(Object obj) {
		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
		if (obj instanceof TreeParent)
		   imageKey = ISharedImages.IMG_OBJ_FOLDER;
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}
}
