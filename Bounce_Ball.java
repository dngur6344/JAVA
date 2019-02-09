package hw4;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.Semaphore;
class WindowDestroyer extends WindowAdapter
{
	public void windowClosing(WindowEvent e)
	{
			System.exit(0);
	}
}
public class s20141595hw4 extends Frame implements ActionListener 
{
	private static Semaphore sema;
	private Canvas_2 canvas;
	private ArrayList<Ball> list1=new ArrayList();
	public s20141595hw4(String title)
	{
		super(title);
		canvas = new Canvas_2();
		add("Center", canvas);
		Panel p = new Panel();
		Button s = new Button("Start");
		Button c = new Button("Close");
		p.add(s); p.add(c);
		sema = new Semaphore(1);
		s.addActionListener(this);
		c.addActionListener(this);
		add("South", p); 
	}
	class Canvas_2 extends Canvas{
		public Canvas_2(){
			super();
		}
		public void paint(Graphics g){
			g.setColor(Color.BLACK);
			for(int i=0; i<list1.size(); i++){
				if(list1.get(i).XSIZE >=2)
					g.fillOval(list1.get(i).x, list1.get(i).y, list1.get(i).XSIZE, list1.get(i).YSIZE);
			}
		}
	}
	public void actionPerformed(ActionEvent evt) 
	{
		if (evt.getActionCommand() == "Start") 
		{
			for(int i=0; i<5; i++){
				list1.add(new Ball(canvas,10));
				list1.get(list1.size()-1).x = list1.get(list1.size()-1).x + 10* list1.get(list1.size()-1).dx;
				list1.get(list1.size()-1).y = list1.get(list1.size()-1).y + 10* list1.get(list1.size()-1).dy;
				list1.get(list1.size()-1).start();
			}
		}
		else if (evt.getActionCommand() == "Close")
			System.exit(0); 
	}
	public static void main(String[] args)
	{
		Frame f = new s20141595hw4("Bounce Thread");
		f.setSize(400, 300);
		WindowDestroyer listener = new WindowDestroyer();
		f.addWindowListener(listener);
		f.setVisible(true); 
	}
	class Ball extends Thread
	{
		private Canvas box;
		private int XSIZE = 10;
		private int YSIZE = 10;
		private int x = 0;
		private int y = 0;
		private int dx = 2;
		private int dy = 2;
		public Ball(Canvas c, int size)
		{
			box = c;
			XSIZE = YSIZE = size;
			x = box.getWidth()/2;
			y = box.getHeight()/2;
			dx = (int)(Math.random()*10)-5;
			dy = (int)(Math.random()*10)-5;
			if(dx == 0) dx = 5;
			if(dy == 0) dy = 5;
		}
		/*public void draw() 
		{
			Graphics g = box.getGraphics();
			g.fillOval(x, y, XSIZE, YSIZE);
			g.dispose(); 
		}*/
		public void move() 
		{
			//Graphics g = box.getGraphics();
			//g.setXORMode(box.getBackground());
			//g.fillOval(x, y, XSIZE, YSIZE);
			x += dx;
			y += dy;
			Dimension d = box.getSize();
			if (x < 0) 
			{ 
				x = 0; 
				dx = -dx; 
			}
			if (x + XSIZE >= d.width)
			{
				x = d.width - XSIZE; 
				dx = -dx;
			}
			if (y < 0) 
			{
				y = 0; 
				dy = -dy;
			}
			if (y + YSIZE >= d.height)
			{
				y = d.height - YSIZE; 
				dy = -dy; 
			}
			//g.fillOval(x, y, XSIZE, YSIZE);
			//g.dispose(); 
		}
		public boolean checking(int j)
		{
			if(Ball.currentThread().equals(list1.get(j))==false&&Math.pow(Math.abs((x+XSIZE/2)-(list1.get(j).x+list1.get(j).XSIZE/2)), 2)+Math.pow(Math.abs((y+YSIZE/2)-(list1.get(j).y+list1.get(j).YSIZE/2)),2)<=XSIZE+list1.get(j).XSIZE)
				return true;
			else return false;
		}
		public void crash()
		{
			//Graphics g=box.getGraphics();
			//box.repaint(x-XSIZE/2,y-YSIZE/2,x+XSIZE/2,y+YSIZE/2);
			for(int j=0;j<list1.size();j++)
			{
				if(checking(j)==true)
				{
					XSIZE /= 2;
					YSIZE /= 2;
					dx = -dx;
					dy = -dy;//1개
					list1.get(j).XSIZE /= 2;
					list1.get(j).YSIZE /= 2;
					list1.get(j).dx = -list1.get(j).dx;
					list1.get(j).dy = -list1.get(j).dy;//2개
					
					list1.add(new Ball(canvas, XSIZE));//3개
					list1.get(list1.size()-1).x = ((x+list1.get(j).x)/2) + 5*(-dx);
					list1.get(list1.size()-1).y = ((y+list1.get(j).y)/2) + 5*(-list1.get(j).dx);
					list1.get(list1.size()-1).dx = -dx;
					list1.get(list1.size()-1).dy = -list1.get(j).dx;
					list1.get(list1.size()-1).start();
					
					list1.add(new Ball(canvas,YSIZE));//4개
					list1.get(list1.size()-1).x = ((x+list1.get(j).x)/2) + 5*(-list1.get(j).dy);
					list1.get(list1.size()-1).y = ((y+list1.get(j).y)/2) + 5*(-dy);
					list1.get(list1.size()-1).dx = -list1.get(j).dy;
					list1.get(list1.size()-1).dy = -dy;
					list1.get(list1.size()-1).start();
				}
			}
		}
		public void run() 
		{
			Graphics g = box.getGraphics();
			//draw();
			g.fillOval(x, y, XSIZE, YSIZE);
			for (;;)
			{
				try {
					sema.acquire();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				move();
				crash();
				if(XSIZE<2 && YSIZE<2)
				{
					sema.release();
					System.exit(0);
				}
				box.repaint();
				g.fillOval(x, y, XSIZE, YSIZE);
				sema.release(); 
				try //일단 한다.
				{ 
					Thread.sleep(15); 
				}
				catch(InterruptedException e) {}//오류나면 여기로 오는
			}
		}
	}
}