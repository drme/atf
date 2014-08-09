// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 29/04/2009 22:01:06
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   PolyApplet.java

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Math2
{
    public Math2()
    {
        dragPoint = -1;
        image = null;
        doOptimize = false;
        points_x = new float[100];
        points_y = new float[100];
        numPoints = 0;
        edges = new Edge[100];
        numEdges = 0;
        triangles = new Triangle[100];
        numTriangles = 0;
    }

    private void clear()
    {
        numPoints = 0;
        numEdges = 0;
        numTriangles = 0;
    }

    int FindEdge(int i, int j)
    {
        int k;
        int l;
        if(i < j)
        {
            k = i;
            l = j;
        } else
        {
            k = j;
            l = i;
        }
        for(int i1 = 0; i1 < numEdges; i1++)
            if(edges[i1].v0 == k && edges[i1].v1 == l)
                return i1;

        return -1;
    }

    void AddEdge(int i, int j, int k)
    {
        int l1 = FindEdge(i, j);
        int j1;
        int k1;
        Edge edge;
        if(l1 < 0)
        {
            if(numEdges == edges.length)
            {
                Edge aedge[] = new Edge[edges.length * 2];
                System.arraycopy(edges, 0, aedge, 0, numEdges);
                edges = aedge;
            }
            j1 = -1;
            k1 = -1;
            l1 = numEdges++;
            edge = edges[l1] = new Edge();
        } else
        {
            edge = edges[l1];
            j1 = edge.t0;
            k1 = edge.t1;
        }
        int l;
        int i1;
        if(i < j)
        {
            l = i;
            i1 = j;
            j1 = k;
        } else
        {
            l = j;
            i1 = i;
            k1 = k;
        }
        edge.v0 = l;
        edge.v1 = i1;
        edge.t0 = j1;
        edge.t1 = k1;
        edge.suspect = true;
    }

    void DeleteEdge(int i, int j)
        throws TriangleException
    {
        int k;
        if(0 > (k = FindEdge(i, j)))
        {
            throw new TriangleException("Attempt to delete unknown edge");
        } else
        {
            edges[k] = edges[--numEdges];
            return;
        }
    }

    void MarkSuspect(int i, int j, boolean flag)
        throws TriangleException
    {
        int k;
        if(0 > (k = FindEdge(i, j)))
        {
            throw new TriangleException("Attempt to mark unknown edge");
        } else
        {
            edges[k].suspect = flag;
            return;
        }
    }

    Edge ChooseSuspect()
    {
        for(int i = 0; i < numEdges; i++)
        {
            Edge edge = edges[i];
            if(edge.suspect)
            {
                edge.suspect = false;
                if(edge.t0 >= 0 && edge.t1 >= 0)
                    return edge;
            }
        }

        return null;
    }

    static float Rho(float f, float f1, float f2, float f3, float f4, float f5)
    {
        float f6 = f4 - f2;
        float f7 = f5 - f3;
        float f8 = f - f4;
        float f9 = f1 - f5;
        float f18 = f6 * f9 - f7 * f8;
        if(f18 > 0.0F)
        {
            if(f18 < 1E-006F)
                f18 = 1E-006F;
            float f12 = f6 * f6;
            float f13 = f7 * f7;
            float f14 = f8 * f8;
            float f15 = f9 * f9;
            float f10 = f2 - f;
            float f11 = f3 - f1;
            float f16 = f10 * f10;
            float f17 = f11 * f11;
            return ((f12 + f13) * (f14 + f15) * (f16 + f17)) / (f18 * f18);
        } else
        {
            return -1F;
        }
    }

    static boolean insideTriangle(float f, float f1, float f2, float f3, float f4, float f5, float f6, float f7)
    {
        float f8 = f4 - f2;
        float f9 = f5 - f3;
        float f10 = f - f4;
        float f11 = f1 - f5;
        float f12 = f2 - f;
        float f13 = f3 - f1;
        float f14 = f6 - f;
        float f15 = f7 - f1;
        float f16 = f6 - f2;
        float f17 = f7 - f3;
        float f18 = f6 - f4;
        float f19 = f7 - f5;
        float f22 = f8 * f17 - f9 * f16;
        float f20 = f12 * f15 - f13 * f14;
        float f21 = f10 * f19 - f11 * f18;
        return (double)f22 >= 0.0D && (double)f21 >= 0.0D && (double)f20 >= 0.0D;
    }

    boolean Snip(int i, int j, int k, int l)
    {
        float f = points_x[V[i]];
        float f1 = points_y[V[i]];
        float f2 = points_x[V[j]];
        float f3 = points_y[V[j]];
        float f4 = points_x[V[k]];
        float f5 = points_y[V[k]];
        if(1E-006F > (f2 - f) * (f5 - f1) - (f3 - f1) * (f4 - f))
            return false;
        for(int i1 = 0; i1 < l; i1++)
            if(i1 != i && i1 != j && i1 != k)
            {
                float f6 = points_x[V[i1]];
                float f7 = points_y[V[i1]];
                if(insideTriangle(f, f1, f2, f3, f4, f5, f6, f7))
                    return false;
            }

        return true;
    }

    public float Area()
    {
        float f = 0.0F;
        int i = numPoints - 1;
        for(int j = 0; j < numPoints;)
        {
            f += points_x[i] * points_y[j] - points_y[i] * points_x[j];
            i = j++;
        }

        return f * 0.5F;
    }

    void Triangulate()
        throws TriangleException
    {
        int i = numPoints;
        if(i < 3)
            return;
        numEdges = 0;
        numTriangles = 0;
        V = new int[i];
        if(0.0D < (double)Area())
        {
            for(int k = 0; k < i; k++)
                V[k] = k;

        } else
        {
            for(int l = 0; l < i; l++)
                V[l] = numPoints - 1 - l;

        }
        int k1 = 2 * i;
        int i1 = i - 1;
        while(i > 2) 
        {
            if(0 >= k1--)
                throw new TriangleException("Bad polygon");
            int j = i1;
            if(i <= j)
                j = 0;
            i1 = j + 1;
            if(i <= i1)
                i1 = 0;
            int j1 = i1 + 1;
            if(i <= j1)
                j1 = 0;
            if(Snip(j, i1, j1, i))
            {
                int l1 = V[j];
                int i2 = V[i1];
                int j2 = V[j1];
                if(numTriangles == triangles.length)
                {
                    Triangle atriangle[] = new Triangle[triangles.length * 2];
                    System.arraycopy(triangles, 0, atriangle, 0, numTriangles);
                    triangles = atriangle;
                }
                triangles[numTriangles] = new Triangle(l1, i2, j2);
                AddEdge(l1, i2, numTriangles);
                AddEdge(i2, j2, numTriangles);
                AddEdge(j2, l1, numTriangles);
                numTriangles++;
                int k2 = i1;
                for(int l2 = i1 + 1; l2 < i; l2++)
                {
                    V[k2] = V[l2];
                    k2++;
                }

                i--;
                k1 = 2 * i;
            }
        }
        V = null;
    }

    void Optimize()
        throws TriangleException
    {
        do
        {
            Edge edge;
            if((edge = ChooseSuspect()) == null)
                break;
            int i1 = edge.v0;
            int k1 = edge.v1;
            int i = edge.t0;
            int j = edge.t1;
            int j1 = -1;
            int l1 = -1;
            for(int k = 0; k < 3; k++)
            {
                int i2 = triangles[i].v[k];
                if(i1 == i2 || k1 == i2)
                    continue;
                l1 = i2;
                break;
            }

            for(int l = 0; l < 3; l++)
            {
                int j2 = triangles[j].v[l];
                if(i1 == j2 || k1 == j2)
                    continue;
                j1 = j2;
                break;
            }

            if(-1 == j1 || -1 == l1)
                throw new TriangleException("can't find quad");
            float f = points_x[i1];
            float f1 = points_y[i1];
            float f2 = points_x[j1];
            float f3 = points_y[j1];
            float f4 = points_x[k1];
            float f5 = points_y[k1];
            float f6 = points_x[l1];
            float f7 = points_y[l1];
            float f8 = Rho(f, f1, f2, f3, f4, f5);
            float f9 = Rho(f, f1, f4, f5, f6, f7);
            float f10 = Rho(f2, f3, f4, f5, f6, f7);
            float f11 = Rho(f2, f3, f6, f7, f, f1);
            if(0.0F > f8 || 0.0F > f9)
                throw new TriangleException("original triangles backwards");
            if(0.0F <= f10 && 0.0F <= f11)
            {
                if(f8 > f9)
                    f8 = f9;
                if(f10 > f11)
                    f10 = f11;
                if(f8 > f10)
                {
                    DeleteEdge(i1, k1);
                    triangles[i].v[0] = j1;
                    triangles[i].v[1] = k1;
                    triangles[i].v[2] = l1;
                    triangles[j].v[0] = j1;
                    triangles[j].v[1] = l1;
                    triangles[j].v[2] = i1;
                    AddEdge(j1, k1, i);
                    AddEdge(k1, l1, i);
                    AddEdge(l1, j1, i);
                    AddEdge(l1, i1, j);
                    AddEdge(i1, j1, j);
                    AddEdge(j1, l1, j);
                    MarkSuspect(j1, l1, false);
                }
            }
        } while(true);
    }







float len(Edge e)
{
	float x1 = this.points_x[e.v0];
	float y1 = this.points_y[e.v0];
	
	float x2 = this.points_x[e.v1];
	float y2 = this.points_y[e.v1];
	
	float x = x2 - x1;
	float y = y2 - y1;

	return (float)java.lang.Math.sqrt(x*x+y*y);
}

public java.util.List<Triangle> triangulate(java.util.List<Point> points) throws Throwable
{
	this.clear();
	this.numPoints = points.size();
	
	for (int i = 0; i < points.size(); i++)
	{
		this.points_x[i] = points.get(i).x;
		this.points_y[i] = points.get(i).y;
	}
	
	this.Triangulate();
	this.Optimize();
	
	ArrayList<Triangle> triangles = new ArrayList<Triangle>();
	
	for (int i = 0; i < this.numTriangles; i++)
	{
		float[] abc = new float[3];
		abc[0] = len(this.edges[this.triangles[i].v[0]]);
		abc[1] = len(this.edges[this.triangles[i].v[1]]);
		abc[2] = len(this.edges[this.triangles[i].v[2]]);
		
		triangles.add(new Triangle(abc));
	}
	
	return triangles;
}

public static void main(String[] args) throws Throwable
{
	Math2 m = new Math2();
	
	ArrayList<Point> points = new ArrayList<Point>();
	
	points.add(new Point(0,0));
	points.add(new Point(0,1));
	points.add(new Point(1,1));
	points.add(new Point(1,0));
	
	List<Triangle> lst = m.triangulate(points);
}


    float points_x[];
    float points_y[];
    int numPoints;
    int dragPoint;
    protected int V[];
    Edge edges[];
    int numEdges;
    Triangle triangles[];
    int numTriangles;
    Image image;
    static final float EPSILON = 1E-006F;
    static final Color lightGray = new Color(0xf0f0f0);
    boolean doOptimize;

}

