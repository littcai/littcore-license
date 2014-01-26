package com.litt.core.security.license.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.commons.io.FileUtils;

import com.litt.core.security.license.LicenseConfig;
import com.litt.core.util.PropertiesUtils;
import com.litt.core.util.ResourceUtils;

public class Gui
{

	private JFrame frame_security;	
	
	private LicenseConfig currentLicenseConfig;	
	
	public static String HOME_PATH = "D:\\license";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				try
				{
					Gui window = new Gui();
					window.frame_security.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui()
	{
		try {
			Properties props = PropertiesUtils.loadProperties(ResourceUtils.getFile("classpath:init.properties"));
			HOME_PATH = props.getProperty("HOME_PATH");
			
			File configFile =  new File(Gui.HOME_PATH, "config.xml");	
			if(!configFile.exists())
			{
				FileUtils.copyFileToDirectory(ResourceUtils.getFile("classpath:config.xml"), new File(Gui.HOME_PATH));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame_security = new JFrame();
		frame_security.setBounds(100, 100, 640, 480);
		frame_security.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_security.getContentPane().setLayout(new BorderLayout(0, 0));
		frame_security.setTitle("软件授权证书管理器");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame_security.getContentPane().add(tabbedPane, BorderLayout.CENTER);	
		
		//产品化证书管理
		JPanel advancedPanel = new JPanel(new BorderLayout());
		tabbedPane.addTab("产品证书管理", null, advancedPanel, null);
		
		JTabbedPane advanced_tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		advancedPanel.add(advanced_tabbedPane);
		
		
		JPanel configPanel = new ConfigPanel(this);
		advanced_tabbedPane.addTab("更新证书", null, configPanel, null);		
		
		JPanel productPanel = new ProductPanel();
		advanced_tabbedPane.addTab("创建产品", null, productPanel, null);
		
		JPanel customerPanel = new CustomerPanel();
		advanced_tabbedPane.addTab("客户授权", null, customerPanel, null);	
	
		//基础工具
		JPanel basePanel = new JPanel(new BorderLayout());
		tabbedPane.addTab("基础工具", null, basePanel, null);
				
		JTabbedPane base_tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		basePanel.add(base_tabbedPane);
				
		JPanel keyPanel = new KeyPanel();
		base_tabbedPane.addTab("创建密钥", null, keyPanel, null);
		
		JPanel licensePanel = new LicensePanel();
		base_tabbedPane.addTab("创建证书", null, licensePanel, null);
		
		JPanel validatePanel = new ValidatePanel();
		base_tabbedPane.addTab("校验证书", null, validatePanel, null);

		JPanel encryptPanel = new EncryptPanel();
		base_tabbedPane.addTab("加密证书", null, encryptPanel, null);		
				
		JPanel decryptPanel = new DecryptPanel();
		base_tabbedPane.addTab("解密证书", null, decryptPanel, null);
		
	}

	/**
	 * @return the currentLicenseConfig
	 */
	public LicenseConfig getCurrentLicenseConfig()
	{
		return currentLicenseConfig;
	}

	/**
	 * @param currentLicenseConfig the currentLicenseConfig to set
	 */
	public void setCurrentLicenseConfig(LicenseConfig currentLicenseConfig)
	{
		this.currentLicenseConfig = currentLicenseConfig;
	}

}
