package hw3;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
class WindowDestroyer extends WindowAdapter
{
	public void windowClosing(WindowEvent e)
	{
			System.exit(0);
	}
}
class ButtonDemo implements ActionListener
{
	private String str;
	private TextField x;
	private TextField result;
	private Frame F;
	private Panel Y;
	double res=0;
	int new_flag=0;
	String res_str="";
	String sik="";
	String sik2="";
	private Stack <String> all = new Stack();
	private Stack <String> temper = new Stack();
	private String [][]arr=new String[8][50];
	public ButtonDemo()
	{
		
		F=new Frame("Calculator");
		//x=new TextField(30);
		result=new TextField(30);
		Y=new Panel();
		F.setLayout(new BorderLayout());
		F.add(result,BorderLayout.NORTH);
		F.pack();
		Y.setLayout(new GridLayout(4,5,1,1));
		Arrays.fill(arr[0], "");
		Arrays.fill(arr[1], "");
		Arrays.fill(arr[2], "");
		Arrays.fill(arr[3], "");
		Arrays.fill(arr[4], "");
		Button b1=new Button("1");
		Button b2=new Button("2");
		Button b3=new Button("3");
		Button b4=new Button("4");
		Button b5=new Button("5");
		Button b6=new Button("6");
		Button b7=new Button("7");
		Button b8=new Button("8");
		Button b9=new Button("9");
		Button b0=new Button("0");
		Button ba=new Button(".");
		Button bb=new Button("=");
		Button bc=new Button("/");
		Button bd=new Button("*");
		Button be=new Button("-");
		Button bf=new Button("+");
		Button bg=new Button("C");
		Button bh=new Button("<-");
		Button bi=new Button("(");
		Button bj=new Button(")");
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
		b8.addActionListener(this);
		b9.addActionListener(this);
		b0.addActionListener(this);
		ba.addActionListener(this);
		bb.addActionListener(this);
		bc.addActionListener(this);
		bd.addActionListener(this);
		be.addActionListener(this);
		bf.addActionListener(this);
		bg.addActionListener(this);
		bh.addActionListener(this);
		bi.addActionListener(this);
		bj.addActionListener(this);
		Y.add(b7);Y.add(b8);Y.add(b9);Y.add(bc);Y.add(bg);//F.pack();
		Y.add(b4);Y.add(b5);Y.add(b6);Y.add(bd);Y.add(bh);//F.pack();
		Y.add(b1);Y.add(b2);Y.add(b3);Y.add(be);Y.add(bi);//F.pack();
		Y.add(b0);Y.add(ba);Y.add(bb);Y.add(bf);Y.add(bj);//F.pack();
		F.add(Y);
		F.setSize(300, 400);
		WindowDestroyer listener=new WindowDestroyer();
		F.addWindowListener(listener);
		F.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		int err_flag=0;
		int left_flag=0;
		int right_flag=0;
		int i=0,j=0,max=0;
		int tmp;
		double temp=0;
		String e1=e.getActionCommand();
		
		if(e1.equals("C")==true)
		{
			res_str="";
			res=0;
			Arrays.fill(arr[0], "");
			Arrays.fill(arr[1], "");
			Arrays.fill(arr[2], "");
			Arrays.fill(arr[3], "");
			Arrays.fill(arr[4], "");
			result.setText(res_str);
		}
		else if(e1.equals("<-"))
		{
			if(res_str.length() > 0)
			{
				if(new_flag==1)
				{
					res=0;
					res_str="";
					result.setText(res_str);
				}
				else
				{
					res_str = res_str.substring(0, res_str.length()-2);
					result.setText(res_str);
				}
			}
		}
		else if(new_flag==0&&res_str.length() > 0 && e1.equals("."))
		{
			res_str = res_str.substring(0, res_str.length());
			res_str = res_str + e1;
			result.setText(res_str);
		}
		else if(e1.equals("(") || e1.equals(")"))
		{
			if(new_flag==1)new_flag=0;
			res_str = res_str + " " + e1;
			result.setText(res_str);
		}
		else if(res_str.equals("")&&e1.equals("-"))
		{
			res_str="0 -";
			result.setText(res_str);
		}
		else if(res_str.length() > 0 && (e1.equals("+") || e1.equals("-") || e1.equals("*") || e1.equals("/"))) 
		{
			if(new_flag==1)new_flag=0;
			if(res_str.charAt(res_str.length()-1) == '+'|| res_str.charAt(res_str.length()-1) == '-'
					|| res_str.charAt(res_str.length()-1) == '*'|| res_str.charAt(res_str.length()-1) == '/')
			{
				res_str = res_str.substring(0, res_str.length()-1) + e1;
			}
			else
				res_str = res_str + " " + e1;
			
			result.setText(res_str);
		}
		// When input is number
		else if(e1.equals("0") || e1.equals("1") || e1.equals("2") || e1.equals("3")|| e1.equals("4") 
				|| e1.equals("5") || e1.equals("6") || e1.equals("7")|| e1.equals("8") || e1.equals("9")) 
		{
			if(new_flag==1)
			{
				res_str="";
				new_flag=0;
			}
			if(res_str.length() > 0
					&& (res_str.charAt(res_str.length()-1) == '+'|| res_str.charAt(res_str.length()-1) == '-'
					|| res_str.charAt(res_str.length()-1) == '*'|| res_str.charAt(res_str.length()-1) == '/'
					|| res_str.charAt(res_str.length()-1) == '('|| res_str.charAt(res_str.length()-1) == ')')) 
			{
				res_str = res_str + " " + e1;
				result.setText(res_str);
			}
			else 
			{
				res_str = res_str + e1;
				result.setText(res_str);	
			}
				
		}
		else if(res_str.length() > 0 && e1.equals("=")==true)
		{
			result.setText(res_str + " =");
			if(res_str.charAt(res_str.length()-1) == '+'|| res_str.charAt(res_str.length()-1) == '-'
				|| res_str.charAt(res_str.length()-1) == '*'|| res_str.charAt(res_str.length()-1) == '/')
			{
				err_flag = 1;
			}
			else 
			{
				StringTokenizer tok = new StringTokenizer(res_str);
				res = 0;
				while (tok.hasMoreTokens())
				{
					temper.push(tok.nextToken());
				}
				while (temper.empty()==false)
				{
					sik = temper.pop();
					if (sik.equals("+") || sik.equals("-") || sik.equals("*") || sik.equals("/"))
					{
						all.push(sik);
					}
					else if(sik.equals("(")==true)
					{
						err_flag=1;
						break;
					}
					else if (sik.equals(")")==true)
					{
						i=0;j=0;tmp=0;temp=0;
						right_flag++;
						sik2 = temper.pop();
						while(temper.empty()==false)
						{
							if(sik2.equals("(")==true)
							{
								left_flag++;
								if(right_flag==left_flag)break;
								i--;
								for(j=0;arr[i][j].equals("")==false;j++) {}
								j++;
							}
							else if(sik2.equals(")")==true)
							{
								right_flag++;
								i++;
								max=i;
								j=0;
							}
							else
							{
								arr[i][j]=sik2;
								j++;
							}
							sik2=temper.pop();
						}
						if(sik2.equals("(")==true&&temper.empty()==true)
							left_flag++;
						if(right_flag!=left_flag)
						{
							err_flag=1;
							break;
						}
						res=0;
						for(i=right_flag-1;i>=0;i--)
						{
							res=0;
							for(tmp=0;!(arr[i][tmp].equals("")==true&&arr[i][tmp+1].equals("")==true);tmp++) {}
							for(j=tmp-1;j>=0;j--)
							{
								if(arr[i][j-1].equals("")==false)
								{
									if(arr[i][j].equals("+"))
									{
										res=res+Double.parseDouble(arr[i][j-1]);
										j--;
										temp=res;
									}	
									else if(arr[i][j].equals("-"))
									{
										res=res-Double.parseDouble(arr[i][j-1]);
										j--;
										temp=res;
									}
									else if(arr[i][j].equals("*"))
									{
										res=res*Double.parseDouble(arr[i][j-1]);
										j--;
										temp=res;
									}
									else if(arr[i][j].equals("/"))
									{
										res=res/Double.parseDouble(arr[i][j-1]);
										j--;
										temp=res;
									}
									else
									{
										res=Double.parseDouble(arr[i][j]);
									}
								}
								else
								{
									if(arr[i][j].equals("+"))
									{
										res=res+temp;
										j--;
										temp=res;
									}	
									else if(arr[i][j].equals("-"))
									{
										res=res-temp;
										j--;
										temp=res;
									}
									else if(arr[i][j].equals("*"))
									{
										res=res*temp;
										j--;
										temp=res;
									}
									else if(arr[i][j].equals("/"))
									{
										res=res/temp;
										j--;
										temp=res;
									}
									else 
									{
										err_flag=1;
									}
								}
							}
						}
						all.push(Double.toString(res));
					}
					else
					{
						all.push(sik);
					}
				}
				if(err_flag!=1)//when it is not error
				{
					res = 0;
					res = res+Double.parseDouble(all.pop());
					while (!all.empty()) 
					{
						sik = all.pop();
						if (sik.equals("+")) 
						{
							sik = all.pop();
							res = res+Double.parseDouble(sik);
						} 
						else if (sik.equals("-")) 
						{
							sik = all.pop();
							res = res-Double.parseDouble(sik);
						} 
						else if (sik.equals("*"))
						{
							sik = all.pop();
							res = res*Double.parseDouble(sik);
						} 
						else if (sik.equals("/"))
						{
							sik = all.pop();
							if(sik.equals("0")==true)
								err_flag=1;
							else
							{
								res = res/Double.parseDouble(sik);
							}
						}
					}
				}
			}
			
			if(err_flag == 1) 
			{
				result.setText(res_str + " = " + "Error");
				res_str="";
				res=0;
				while(all.empty()==false)
				{
					all.pop();
				}
				while(temper.empty()==false)
				{
					temper.pop();
				}
				Arrays.fill(arr[0], "");
				Arrays.fill(arr[1], "");
				Arrays.fill(arr[2], "");
				Arrays.fill(arr[3], "");
				Arrays.fill(arr[4], "");
				
			}
			else
			{
				if(res<(int)(res+1)&&res>(int)(res)) 
				{
					result.setText(res_str + " = " + res);
					res_str=Double.toString(res);
				}
				else 
				{
					result.setText(res_str + " = " + ((int)res));
					res_str=Integer.toString((int)res);
				}
			}
			new_flag=1;
		}
	}
}
public class s20141595hw3 extends Frame{
	public s20141595hw3()
	{
		ButtonDemo am;
		am=new ButtonDemo();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		s20141595hw3 f = new s20141595hw3();
	}
}