package com.litt.core.security.license.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.commons.configuration.XMLConfiguration;

import com.litt.core.common.Utility;
import com.litt.core.license.LicenseException;
import com.litt.core.license.LicenseManager;
import com.litt.core.security.license.LicenseFileFilter;

public class ValidatePanel extends JPanel
{
	private JTextField field_pubKeyFilePath;
	private JTextField field_licenseFilePath;
	
	private JLabel label_licenseId;
	private JLabel label_licenseType;
	private JLabel label_productName;
	private JLabel label_companyName;
	private JLabel label_customerName;
	private JLabel label_version;
	private JLabel label_createDate;
	private JLabel label_expiredDate;
	
	

	/**
	 * Create the panel.
	 */
	public ValidatePanel()
	{
		GridBagLayout gbl_validatePanel = new GridBagLayout();
		gbl_validatePanel.columnWidths = new int[]{0, 0, 0};
		gbl_validatePanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_validatePanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_validatePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		this.setLayout(gbl_validatePanel);
		
		JLabel label_10 = new JLabel("公钥文件：");
		GridBagConstraints gbc_label_10 = new GridBagConstraints();
		gbc_label_10.insets = new Insets(0, 0, 5, 5);
		gbc_label_10.anchor = GridBagConstraints.EAST;
		gbc_label_10.gridx = 0;
		gbc_label_10.gridy = 0;
		this.add(label_10, gbc_label_10);
		
		field_pubKeyFilePath = new JTextField();
		field_pubKeyFilePath.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				//fileChooser.setVisible(true);
				LicenseFileFilter fileFilter = new LicenseFileFilter(new String[]{"key"});
				fileChooser.setFileFilter(fileFilter);
				fileChooser.addChoosableFileFilter(fileFilter);
				fileChooser.showOpenDialog(e.getComponent());
				File pubKeyFile = fileChooser.getSelectedFile();
				
				field_pubKeyFilePath.setText(pubKeyFile.getAbsolutePath());
				
			}
		});
		GridBagConstraints gbc_field_pubKeyFilePath = new GridBagConstraints();
		gbc_field_pubKeyFilePath.insets = new Insets(0, 0, 5, 0);
		gbc_field_pubKeyFilePath.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_pubKeyFilePath.gridx = 1;
		gbc_field_pubKeyFilePath.gridy = 0;
		this.add(field_pubKeyFilePath, gbc_field_pubKeyFilePath);
		field_pubKeyFilePath.setColumns(10);
		
		JLabel label_19 = new JLabel("证书文件：");
		GridBagConstraints gbc_label_19 = new GridBagConstraints();
		gbc_label_19.anchor = GridBagConstraints.EAST;
		gbc_label_19.insets = new Insets(0, 0, 5, 5);
		gbc_label_19.gridx = 0;
		gbc_label_19.gridy = 1;
		this.add(label_19, gbc_label_19);
		
		field_licenseFilePath = new JTextField();
		field_licenseFilePath.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				//fileChooser.setVisible(true);
				LicenseFileFilter fileFilter = new LicenseFileFilter(new String[]{"xml"});
				fileChooser.setFileFilter(fileFilter);
				fileChooser.addChoosableFileFilter(fileFilter);
				fileChooser.showOpenDialog(e.getComponent());
				File licenseFile = fileChooser.getSelectedFile();
				
				field_licenseFilePath.setText(licenseFile.getAbsolutePath());
			}
		});
		GridBagConstraints gbc_textField1 = new GridBagConstraints();
		gbc_textField1.insets = new Insets(0, 0, 5, 0);
		gbc_textField1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField1.gridx = 1;
		gbc_textField1.gridy = 1;
		this.add(field_licenseFilePath, gbc_textField1);
		field_licenseFilePath.setColumns(10);
		
		JLabel lblid = new JLabel("证书ID：");
		lblid.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblid = new GridBagConstraints();
		gbc_lblid.insets = new Insets(0, 0, 5, 5);
		gbc_lblid.gridx = 0;
		gbc_lblid.gridy = 2;
		this.add(lblid, gbc_lblid);
		
		label_licenseId = new JLabel("");
		label_licenseId.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_label_licenseId = new GridBagConstraints();
		gbc_label_licenseId.insets = new Insets(0, 0, 5, 0);
		gbc_label_licenseId.gridx = 1;
		gbc_label_licenseId.gridy = 2;
		this.add(label_licenseId, gbc_label_licenseId);
		
		JLabel label_12 = new JLabel("证书类型：");
		GridBagConstraints gbc_label_12 = new GridBagConstraints();
		gbc_label_12.insets = new Insets(0, 0, 5, 5);
		gbc_label_12.gridx = 0;
		gbc_label_12.gridy = 3;
		this.add(label_12, gbc_label_12);
		
		label_licenseType = new JLabel("");
		GridBagConstraints gbc_label_licenseType = new GridBagConstraints();
		gbc_label_licenseType.insets = new Insets(0, 0, 5, 0);
		gbc_label_licenseType.gridx = 1;
		gbc_label_licenseType.gridy = 3;
		this.add(label_licenseType, gbc_label_licenseType);
		
		JLabel label_13 = new JLabel("产品名称：");
		GridBagConstraints gbc_label_13 = new GridBagConstraints();
		gbc_label_13.insets = new Insets(0, 0, 5, 5);
		gbc_label_13.gridx = 0;
		gbc_label_13.gridy = 4;
		this.add(label_13, gbc_label_13);
		
		label_productName = new JLabel("");
		GridBagConstraints gbc_label_productName = new GridBagConstraints();
		gbc_label_productName.insets = new Insets(0, 0, 5, 0);
		gbc_label_productName.gridx = 1;
		gbc_label_productName.gridy = 4;
		this.add(label_productName, gbc_label_productName);
		
		JLabel label_14 = new JLabel("公司名称：");
		GridBagConstraints gbc_label_14 = new GridBagConstraints();
		gbc_label_14.insets = new Insets(0, 0, 5, 5);
		gbc_label_14.gridx = 0;
		gbc_label_14.gridy = 5;
		this.add(label_14, gbc_label_14);
		
		label_companyName = new JLabel("");
		GridBagConstraints gbc_label_companyName = new GridBagConstraints();
		gbc_label_companyName.insets = new Insets(0, 0, 5, 0);
		gbc_label_companyName.gridx = 1;
		gbc_label_companyName.gridy = 5;
		this.add(label_companyName, gbc_label_companyName);
		
		JLabel label_15 = new JLabel("客户名称：");
		GridBagConstraints gbc_label_15 = new GridBagConstraints();
		gbc_label_15.insets = new Insets(0, 0, 5, 5);
		gbc_label_15.gridx = 0;
		gbc_label_15.gridy = 6;
		this.add(label_15, gbc_label_15);
		
		label_customerName = new JLabel("");
		GridBagConstraints gbc_label_customerName = new GridBagConstraints();
		gbc_label_customerName.insets = new Insets(0, 0, 5, 0);
		gbc_label_customerName.gridx = 1;
		gbc_label_customerName.gridy = 6;
		this.add(label_customerName, gbc_label_customerName);
		
		JLabel label_16 = new JLabel("版本：");
		GridBagConstraints gbc_label_16 = new GridBagConstraints();
		gbc_label_16.insets = new Insets(0, 0, 5, 5);
		gbc_label_16.gridx = 0;
		gbc_label_16.gridy = 7;
		this.add(label_16, gbc_label_16);
		
		label_version = new JLabel("");
		GridBagConstraints gbc_label_version = new GridBagConstraints();
		gbc_label_version.insets = new Insets(0, 0, 5, 0);
		gbc_label_version.gridx = 1;
		gbc_label_version.gridy = 7;
		this.add(label_version, gbc_label_version);
		
		JLabel label_11 = new JLabel("创建时间：");
		GridBagConstraints gbc_label_11 = new GridBagConstraints();
		gbc_label_11.insets = new Insets(0, 0, 5, 5);
		gbc_label_11.gridx = 0;
		gbc_label_11.gridy = 8;
		this.add(label_11, gbc_label_11);
		
		label_createDate = new JLabel("");
		GridBagConstraints gbc_label_createDate = new GridBagConstraints();
		gbc_label_createDate.insets = new Insets(0, 0, 5, 0);
		gbc_label_createDate.gridx = 1;
		gbc_label_createDate.gridy = 8;
		this.add(label_createDate, gbc_label_createDate);
		
		JLabel label_17 = new JLabel("过期时间：");
		GridBagConstraints gbc_label_17 = new GridBagConstraints();
		gbc_label_17.insets = new Insets(0, 0, 5, 5);
		gbc_label_17.gridx = 0;
		gbc_label_17.gridy = 9;
		this.add(label_17, gbc_label_17);
		
		label_expiredDate = new JLabel("");
		GridBagConstraints gbc_label_expiredDate = new GridBagConstraints();
		gbc_label_expiredDate.insets = new Insets(0, 0, 5, 0);
		gbc_label_expiredDate.gridx = 1;
		gbc_label_expiredDate.gridy = 9;
		this.add(label_expiredDate, gbc_label_expiredDate);
		
		JButton button_2 = new JButton("校验");
		button_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				String licenseFilePath = Utility.trimNull(field_licenseFilePath.getText());
				String pubKeyFilePath = Utility.trimNull(field_pubKeyFilePath.getText());
				if(Utility.isEmpty(licenseFilePath))
				{
					JOptionPane.showMessageDialog(e.getComponent(), "请选择证书文件!");
					return;
				}
				if(Utility.isEmpty(pubKeyFilePath))
				{
					JOptionPane.showMessageDialog(e.getComponent(), "请选择密钥文件!");
					return;
				}
				File licenseFile = new File(licenseFilePath);
				try
				{					
					XMLConfiguration config = new XMLConfiguration(licenseFile);  
					config.setAutoSave(true);
					
					label_licenseId.setText(config.getString(("licenseId")));
					label_licenseType.setText(config.getString("licenseType"));
					label_productName.setText(config.getString("productName"));
					label_companyName.setText(config.getString("companyName"));
					label_customerName.setText(config.getString("customerName"));
					label_version.setText(config.getString("version"));
					label_createDate.setText(config.getString("createDate"));
					label_expiredDate.setText(config.getString("expiredDate"));
					
				}
				catch (Exception e1)
				{
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(e.getComponent(), e1.getMessage());
				}	
				
				try
				{
					LicenseManager.validateLicense(licenseFile.getAbsolutePath(), pubKeyFilePath);
					JOptionPane.showMessageDialog(e.getComponent(), "校验通过！");
				}
				catch (LicenseException e1)
				{
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(e.getComponent(), e1.getMessage());
				}
				
			}
		});
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.gridx = 1;
		gbc_button_2.gridy = 10;
		this.add(button_2, gbc_button_2);
	}

}
