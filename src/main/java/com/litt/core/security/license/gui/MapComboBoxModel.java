package com.litt.core.security.license.gui;

import java.util.ArrayList;
import java.util.List;

public class MapComboBoxModel
{
	private Object value;
	
	private String display;
	
	public static List getLicenseTypeOptions()
	{
		List list = new ArrayList();		
		list.add(new MapComboBoxModel("DEMO","DEMO - 演示版"));
		list.add(new MapComboBoxModel("TRIAL","TRIAL - 试用版"));
		list.add(new MapComboBoxModel("STANDARD","STANDARD - 标准版"));
		list.add(new MapComboBoxModel("ENTERPRISE","ENTERPRISE - 企业版"));
		list.add(new MapComboBoxModel("DEVELOPMENT","DEVELOPMENT - 开发版"));
		return list;
	}

	public MapComboBoxModel(Object value, String display)
	{
		super();
		this.value = value;
		this.display = display;
	}	
	
	public String toString()
	{
		return display;
	}


	/**
	 * @return the display
	 */
	public String getDisplay()
	{
		return display;
	}


	/**
	 * @param display the display to set
	 */
	public void setDisplay(String display)
	{
		this.display = display;
	}


	/**
	 * @return the value
	 */
	public Object getValue()
	{
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(Object value)
	{
		this.value = value;
	}

}
