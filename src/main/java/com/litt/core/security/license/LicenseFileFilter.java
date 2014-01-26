package com.litt.core.security.license;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/** 
 * 
 * TODO.
 * 
 * <pre><b>描述：</b>
 *    TODO 
 * </pre>
 * 
 * <pre><b>修改记录：</b>
 *    TODO
 * </pre>
 * 
 * @author <a href="mailto:littcai@hotmail.com">蔡源</a>
 * @since 2010-2-2
 * @version 1.0
 *
 */
public class LicenseFileFilter extends FileFilter
{
	private String[] filterSuffix;
	
	public LicenseFileFilter(String[] filterSuffix)
	{
		this.filterSuffix = filterSuffix;
	}	

	public String getDescription()
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<filterSuffix.length; i++)
		{
			sb.append("*.").append(filterSuffix[i]).append(" ");
		}
		return sb.toString();
	}
	  
	  public boolean accept(File file)
	  {
		  if(file.isDirectory())
			  return true;
		  else
		  { 
			  String name = file.getName();
			  for(int i=0; i<filterSuffix.length; i++)
			  {
				  if(name.toLowerCase().endsWith("."+filterSuffix[i]))
					  return true;
			  }
			  return false;
		  } 
	  } 

}
