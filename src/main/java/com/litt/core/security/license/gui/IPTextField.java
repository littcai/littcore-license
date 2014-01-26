package com.litt.core.security.license.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.StringTokenizer;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class IPTextField extends JTextField
{
    private String st1 = "";
    private String st2 = "";
    private String st3 = "";
    private String st4 = "";
    
    private JTextIPSpace t1;
    private JTextIPSpace t2;
    private JTextIPSpace t3;
    private JTextIPSpace t4;
    
    private JLabel txtCompart1;
    private JLabel txtCompart2;
    private JLabel txtCompart3;
    
    final private static int RADII = 2;
    final private static int SPACEINTERVAL = 2; 

    public IPTextField()
    {
    	this(null);
    }
    
    public IPTextField(String ipAddress)
    {
        super();
        analyzeStr(ipAddress);
        InitIPField();
        setChildComponent(false);
        setLayout(null);
                       
        t1.setPrevNextComponent(t4,t2);
        t2.setPrevNextComponent(t1,t3);
        t3.setPrevNextComponent(t2,t4);
        t4.setPrevNextComponent(t3,t1);
        
        add(t1);          
        add(txtCompart1);
        add(t2);
        add(txtCompart2);
        add(t3);
        add(txtCompart3);
        add(t4);        
                
        addComponentListener(
            new ComponentListener()
            {
                public void componentResized(ComponentEvent e)
                {
                    changeChildComponent();
                }
                public void componentMoved(ComponentEvent e){}
                public void componentShown(ComponentEvent e){}
                public void componentHidden(ComponentEvent e){}                
            }
        );
       setChildComponent(true);              
    }    

    private void InitIPField()
    {
        t1 = new JTextIPSpace(st1,255,true);       
        t2 = new JTextIPSpace(st2,255,true);
        t3 = new JTextIPSpace(st3,255,true);
        t4 = new JTextIPSpace(st4,255,true);  
        
        txtCompart1 = new JLabel(".");
        txtCompart2 = new JLabel(".");
        txtCompart3 = new JLabel(".");
    }
    private void setTextIPField()
    {
        t1.setText(st1);
        t2.setText(st2);
        t3.setText(st3);
        t4.setText(st4);
    }
    /**
      * 重载setText(String text).
      */
     public void setText(String text)
     {
         st1 = "";st2 = "";st3 = "";st4 = "";         
         analyzeStr(text);
         setTextIPField();
     }
     /**
     * 设置显示的IP.
     * @param ip        所显示的IP(字符串形式)
     */
    public void setIP(String ip)
    {
        setText(ip);
    }
    /**
     * 得到Field内的IP.
     * @return            返回IP(字符串形式)
     */
    public String getIP()
    {
         return availIP();
    }
    public String getText()
    {
        return getIP();
    }
    /**
     * 设置Field内的IP值.
     * @param ip        IP的值
     */
    public void setIPValue(int ip)
    {
    }
    /**
     * 得到IP的值.
     * @return            返回IP的值
     */
    public int getIPValue()
    {
        return isIPaddress(getIP());
    }
    /**
     * 将显示的IP转换成标准IP,显示的IP中存在' '.
     * @param ip        显示的IP
     * @return            标准的IP
     */
    private String availIP()
    {
        String strTemp[] = {t1.getText().trim(),t2.getText().trim(),
                            t3.getText().trim(),t4.getText().trim()};
        String returnIP = "";
        for(int i = 0;i<strTemp.length;i++)
        {
            if(!strTemp[i].equals(""))
            {
                if(!returnIP.equals(""))                
                    returnIP += "." + strTemp[i];
                else
                    returnIP = strTemp[i];
            }
        }        
        return returnIP;
     }
     /**
     * 判断IP地址的正确性.
     * @param ipAddress    IP字符串
     * @return            如果IP有效返回IP的值
     *                    否则返回-1
     */
    public static int isIPaddress(String ipAddress)
    {
        int pointNum = 0, numberNum = 0;
        int numberVal = 0;
        int len = ipAddress.length();
        int m = 0;
        int IPVal = 0;
        char tempchar;
        while(m < len && ((tempchar = ipAddress.charAt(m)) == '.' || Character.isDigit(tempchar)))
        {
            m++;
            if((48 <= tempchar) && (tempchar<= 57)) {
                if(numberNum > 2) return -1;
                numberVal *= 10;
                numberVal += tempchar - '0';
                if(numberVal > 255) return -1;
                numberNum ++;
            }
            else {
                if(numberNum == 0) return -1;
                if(pointNum == 3) return -1;
                pointNum++;
                IPVal *= 256;
                IPVal += numberVal;
                numberNum = 0;
                numberVal = 0;
            }
        }
        if(m != len || pointNum != 3 || numberNum == 0) return -1;
        pointNum++;
        IPVal *= 256;
        IPVal += numberVal;
        return IPVal;
    }
    private void analyzeStr(String str) 
    {
    	if(str==null || "".equals(str))
    		return;
        try
        {            
            StringTokenizer st = new StringTokenizer(str, ".");
            
            String tk = "";        
            
            int i = 0;
            while(st.hasMoreTokens() && i<=3)
            {
                i++; 
                tk = st.nextToken();
                if(!tk.equals(""))
                {
                    switch(i)
                    {
                        case 1:
                            st1 = tk;break;
                        case 2:
                            st2 = tk;break;
                        case 3:
                            st3 = tk;break;
                        case 4:
                            st4 = tk;break;                        
                    }
                }                        
                tk = "";
           }
        }
        catch(Exception ex)
        {
            st1 = "";
            st2 = "";
            st3 = "";
            st4 = "";
        }
    }
    private void changeChildComponent()
    {
        Dimension sz = getSize();       
        //int r = Math.min(sz.height,sz.width)/6;
        int r = RADII;
        int spacePoint = 4;
        
        int spaceWidth  = (sz.width - 8*r)/4;
        int spaceHeight = sz.height - 2 * SPACEINTERVAL;
        
        t1.setBounds(0 + r, 0 + SPACEINTERVAL, spaceWidth, spaceHeight);
        txtCompart1.setBounds(spaceWidth+r, 0 + SPACEINTERVAL, spacePoint, spaceHeight);
        t2.setBounds(spaceWidth + 3*r, 0 + SPACEINTERVAL, spaceWidth, spaceHeight);
        txtCompart2.setBounds(2*spaceWidth + 3*r, 0 + SPACEINTERVAL, spacePoint, spaceHeight);
        t3.setBounds(2*spaceWidth + 5*r,0 + SPACEINTERVAL, spaceWidth,spaceHeight);
        txtCompart3.setBounds(3*spaceWidth + 5*r, 0 + SPACEINTERVAL, spacePoint, spaceHeight);
        t4.setBounds(3*spaceWidth + 7*r,0 + SPACEINTERVAL, spaceWidth, spaceHeight);
                
        repaint();                
    }
    private void setChildComponent(boolean isVisible)    
    {
        t1.setVisible(isVisible);
        t2.setVisible(isVisible);
        t3.setVisible(isVisible);
        t4.setVisible(isVisible);        
        repaint();
    }
    public void paintComponent(Graphics g)     
    {            
        Dimension sz = getSize();
        g.setColor(Color.white);        
        g.fillRect(0,0,sz.width,sz.height);        
        
        int spaceWidth  = sz.width/4;
        int spaceHeight = sz.height;
        //int r = Math.min(sz.height,sz.width)/6;
        int r = RADII;
        g.setColor(Color.black);        
        for(int i=1;i<=3;i++)
        {
            g.fillOval(i * spaceWidth,spaceHeight-r,r,r);            
        }        
        //super.paintComponents(g);
    }        

class JTextIPSpace extends JTextField
{    
    public int minValue = 0;
    public int maxValue =255;
    public JTextIPSpace textipspace;
    public boolean bFirstLostFocus = true;
    public boolean isCanFocus = false;
    public JTextField prevComponent;
    public JTextField nextComponent; 
    public JTextIPSpace()
    {
        this(null,255,false);
    }
    public JTextIPSpace(String str)
    {
        this(str,255,true);
    }
    public JTextIPSpace(int maxValue,boolean isCanFocus)
    {
        this(null,maxValue,isCanFocus);
    }    
    public JTextIPSpace(String str,int maxValue,boolean isCanFocus)
    {
        super(str);
        setNoEdge();
        setMidHorizontal();
        addLostFocus();
        addKeySet();
        addComponentChange();
                
        textipspace = this;
        this.maxValue = maxValue;
        this.isCanFocus = isCanFocus;
    }
    
    public void setNoEdge()
    {
        //Border border;
        //border = BorderFactory.createEmptyBorder();
        //setBorder(border1);

        setBorder(null);
        setOpaque(true);        
    }
    public void setPrevNextComponent(JTextField prev,JTextField next)
    {
        this.prevComponent = prev;
        this.nextComponent = next;
    }
    private void addComponentChange()
    {
        ComponentListener cl = new ComponentListener()
        {
            public void componentResized(ComponentEvent e)
            {
                /*            
                Dimension sz = getSize();   
                int fontheight = sz.height;
                int fontwidth = sz.width/2;
                int fontsize = Math.min(fontheight,fontwidth);                                             
                setFont(new Font("Dialog", 0, fontsize));
                */
            }
            public void componentMoved(ComponentEvent e){}
            public void componentShown(ComponentEvent e){}
            public void componentHidden(ComponentEvent e){}  
        };
        addComponentListener(cl);
    }
    private void addLostFocus()
    {
        FocusListener fl = new FocusListener()
        {
            public void focusLost(FocusEvent e)
            {                
                String tmp = getText().trim();
                if(tmp.equals("")) return;
                if(Integer.parseInt(tmp)>maxValue && bFirstLostFocus == true)
                {    
                    bFirstLostFocus = false;
                    JOptionPane.showConfirmDialog(textipspace,
                                            tmp + "不是一个合法的项目，请输入" + minValue + "～" + maxValue + "之间的数值！",
                                            "提示",JOptionPane.CLOSED_OPTION);                    
                    setText(String.valueOf(maxValue));                                                 
                    requestFocus();        
                    repaint();            
                }
            }
            public void focusGained(FocusEvent e)
            {
                bFirstLostFocus = true;
                repaint();
            }
            
        };
        addFocusListener(fl);
    }
    private void addKeySet()
    {
        KeyAdapter ka = new KeyAdapter() 
        {
            public void keyPressed(KeyEvent e) 
            {
                if (e.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    int pos = getCaretPosition();                                        
                    if(pos==0) 
                    {                        
                        gotoComponent(prevComponent);
                    }
                }
                else if(e.getKeyCode() == KeyEvent.VK_UP)
                {
                    if(prevComponent!=null)    prevComponent.selectAll();
                    gotoComponent(prevComponent);                    
                }
                else if(e.getKeyCode() == KeyEvent.VK_DOWN)
                {    
                    if(nextComponent!=null)    nextComponent.selectAll();                
                    gotoComponent(nextComponent);                                            
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                    int pos = getCaretPosition();
                    int maxPos = getText().trim().length();
                    if(pos==maxPos||maxPos==0) 
                    {
                        gotoComponent(nextComponent);                        
                    }
                }
                else if(e.getKeyCode() == KeyEvent. VK_BACK_SPACE)
                {
                    int pos = getCaretPosition();
                    if(pos==0)
                    {
                        gotoComponent(prevComponent);
                    }
                }
                else if(e.getKeyChar() == '.')
                {
                    int maxPos = getText().trim().length();
                    if(maxPos>0)
                    {
                        if(getSelectedText()==null)
                        {
                            if(nextComponent!=null)    nextComponent.selectAll();                
                            gotoComponent(nextComponent);                    
                        }
                    }
                }
            }
            public void keyReleased(KeyEvent e)
            {
                if(Character.isDigit(e.getKeyChar()))
                {
                    int pos = getCaretPosition();
                    //int maxPos = getText().trim().length();
                    if(pos>=3)
                    {
                        gotoComponent(nextComponent);
                    }
                }
            }
        };
        addKeyListener(ka);   
    }
    public void gotoComponent(JComponent jc)
    {
        if(jc!=null) jc.requestFocus();
    }
    public void setMidHorizontal()
    {
        setHorizontalAlignment(JTextField.CENTER);
    }
    protected Document createDefaultModel() 
    {
      return new IPDocument(this);
    }
    public boolean isFocusTraversable()
    {
        return isCanFocus;
    }    
    protected class IPDocument extends PlainDocument 
    {
        public JTextField jtext;
        public IPDocument(JTextField jtext)
        {
            this.jtext = jtext;
        }
        public void insertString(int offset,String str, AttributeSet a)
            throws BadLocationException 
        {
            char[] insertChars = str.toCharArray();  
            boolean valid = true;             
            if(getLength()>=3)
            {
                valid = false;
                return;
            }                          
            for (int i = 0; i < Math.min(insertChars.length,3); i++) 
            {                
                if (!(Character.isDigit(insertChars[i])))
                {
                    valid = false;
                      break;
                  }
                  else
                  {
                      valid = true;
                      break;
                  }
             }                                     
             if (valid) 
                super.insertString(offset, str, a); 
             if(getLength()>3)
               super.remove(offset + str.length(), getLength()-2);                                         
         }                        
    }  

}
}
