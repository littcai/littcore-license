package com.litt.core.security.license.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.Element;

import com.litt.core.security.Algorithm;
import com.litt.core.security.algorithm.DSATool;
import com.litt.core.util.XmlUtils;

/**
 * 
 * 产品证书管理.
 * 
 * <pre><b>Description：</b>
 *    按产品为单位进行证书管理
 * </pre>
 * 
 * <pre><b>Changelog：</b>
 *    
 * </pre>
 * 
 * @author <a href="mailto:littcai@hotmail.com">Bob.cai</a>
 * @since 2013-1-11
 * @version 1.0
 */
public class ProductPanel extends JPanel {
	private JTextField textFieldProductName;
	private JTextField textFieldSecurityKey;
	private JTextField textFieldProductCode;
	public ProductPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblProductCode = new JLabel("\u4EA7\u54C1\u7F16\u53F7\uFF1A");
		GridBagConstraints gbc_lblProductCode = new GridBagConstraints();
		gbc_lblProductCode.anchor = GridBagConstraints.EAST;
		gbc_lblProductCode.insets = new Insets(0, 0, 5, 5);
		gbc_lblProductCode.gridx = 0;
		gbc_lblProductCode.gridy = 0;
		add(lblProductCode, gbc_lblProductCode);
		
		textFieldProductCode = new JTextField();
		GridBagConstraints gbc_textFieldProductCode = new GridBagConstraints();
		gbc_textFieldProductCode.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldProductCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldProductCode.gridx = 1;
		gbc_textFieldProductCode.gridy = 0;
		add(textFieldProductCode, gbc_textFieldProductCode);
		textFieldProductCode.setColumns(10);
		
		JLabel lblProductName = new JLabel("\u4EA7\u54C1\u540D\u79F0\uFF1A");
		GridBagConstraints gbc_lblProductName = new GridBagConstraints();
		gbc_lblProductName.insets = new Insets(0, 0, 5, 5);
		gbc_lblProductName.anchor = GridBagConstraints.EAST;
		gbc_lblProductName.gridx = 0;
		gbc_lblProductName.gridy = 1;
		add(lblProductName, gbc_lblProductName);
		
		textFieldProductName = new JTextField();
		GridBagConstraints gbc_textFieldProductName = new GridBagConstraints();
		gbc_textFieldProductName.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldProductName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldProductName.gridx = 1;
		gbc_textFieldProductName.gridy = 1;
		add(textFieldProductName, gbc_textFieldProductName);
		textFieldProductName.setColumns(10);
		
		JLabel lblSecuritykey = new JLabel("\u5BC6\u94A5\uFF1A");
		GridBagConstraints gbc_lblSecuritykey = new GridBagConstraints();
		gbc_lblSecuritykey.anchor = GridBagConstraints.EAST;
		gbc_lblSecuritykey.insets = new Insets(0, 0, 5, 5);
		gbc_lblSecuritykey.gridx = 0;
		gbc_lblSecuritykey.gridy = 2;
		add(lblSecuritykey, gbc_lblSecuritykey);
		
		textFieldSecurityKey = new JTextField();
		GridBagConstraints gbc_textFieldSecurityKey = new GridBagConstraints();
		gbc_textFieldSecurityKey.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldSecurityKey.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSecurityKey.gridx = 1;
		gbc_textFieldSecurityKey.gridy = 2;
		add(textFieldSecurityKey, gbc_textFieldSecurityKey);
		textFieldSecurityKey.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String productCode = textFieldProductCode.getText();
				String productName = textFieldProductName.getText();
				String securityKey = textFieldSecurityKey.getText();				
				
				File productDir = new File(Gui.HOME_PATH, productCode);
				if(productDir.exists() && productDir.isDirectory())
				{
					JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "产品名称已存在！");
				}
				else
				{
					productDir.mkdirs();
					//在配置文件中增加该产品
					File configFile =  new File(Gui.HOME_PATH, "config.xml");								
					
					try {
						Document document = XmlUtils.readXml(configFile);
						Element rootE = document.getRootElement();
						
						Element productE = rootE.addElement("product");
						productE.addAttribute("code", productCode);
						productE.addAttribute("name", productName);			
						productE.addAttribute("securityKey", securityKey);							
						
						XmlUtils.writeXml(configFile, document);
						
						//生成密钥文件
						File priKeyFilePath = new File(productDir, "private.key");
						File pubKeyFilePath = new File(productDir, "license.key");
						DSATool utils = new DSATool(Algorithm.DSA, securityKey);
						utils.saveKeys(priKeyFilePath.getPath(), pubKeyFilePath.getPath());							
						
						
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), e1.getMessage());
					}
//					for(int i=0;i<productList.size();i++)
//					{
//						Element productE = (Element)productList.get(i);							
//						String productCode = productE.attributeValue("code");
//						
//						List customerList = productE.elements();
//						for(int j=0;j<customerList.size();j++)
//						{
//							Element customerE = (Element)customerList.get(j);	
//							
//							LicenseConfig licenseConfig = new LicenseConfig();
//							licenseConfig.setProductCode(productCode);
//							licenseConfig.setCustomerCode(customerE.attributeValue("code"));
//							licenseConfig.setPriKeyFilePath(customerE.elementText("priKeyFilePath"));
//							licenseConfig.setPubKeyFilePath(customerE.elementText("pubKeyFilePath"));
//							licenseConfig.setLicenseId(customerE.elementText("licenseId"));
//							licenseConfig.setLicenseFilePath(customerE.elementText("licenseFilePath"));
//							licenseConfig.setEncryptedLicense(customerE.elementText("encryptedLicense"));
//							licenseConfig.setRootFilePath(Gui.HOME_PATH);							
//							comboBox_config.addItem(licenseConfig);
//							if(i==0&&j==0)
//							{
//								File licenseFile = new File(Gui.HOME_PATH+licenseConfig.getLicenseFilePath());									
//								currentLicenseConfig = licenseConfig;									
//								loadLicense(licenseFile);
//								gui.setCurrentLicenseConfig(currentLicenseConfig);
//							}
//						}
//					}	
					
					JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "创建成功！");
				}
			}
		});
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 5, 0);
		gbc_btnSave.gridx = 1;
		gbc_btnSave.gridy = 3;
		add(btnSave, gbc_btnSave);
	}
	
	

}
