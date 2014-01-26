package com.litt.core.security.license.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.dom4j.Document;
import org.dom4j.Element;

import com.eltima.components.ui.DatePicker;
import com.litt.core.common.Utility;
import com.litt.core.license.License;
import com.litt.core.license.LicenseManager;
import com.litt.core.security.Algorithm;
import com.litt.core.security.ISecurity;
import com.litt.core.security.algorithm.DESTool;
import com.litt.core.security.license.DigitalSignatureTool;
import com.litt.core.security.license.LicenseFileFilter;
import com.litt.core.uid.RandomGUID;
import com.litt.core.util.ResourceUtils;
import com.litt.core.util.XmlUtils;
import com.litt.core.version.Version;

public class LicensePanel extends JPanel
{
	private JTextField textField_priKeyFile;
	private JComboBox field_licenseType;
	private JTextField field_productName;
	private JTextField field_companyName;
	private JTextField field_customerName;
	private JTextField field_version;
	private DatePicker field_expiredDate; 
	private JTextField textField_licenseFile;
	
	/**
	 * Create the panel.
	 */
	public LicensePanel()
	{
		GridBagLayout gbl_licensePanel = new GridBagLayout();
		gbl_licensePanel.columnWidths = new int[]{0, 0, 0};
		gbl_licensePanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_licensePanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_licensePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		this.setLayout(gbl_licensePanel);
		
		JLabel label_9 = new JLabel("密钥文件：");
		GridBagConstraints gbc_label_9 = new GridBagConstraints();
		gbc_label_9.anchor = GridBagConstraints.EAST;
		gbc_label_9.insets = new Insets(0, 0, 5, 5);
		gbc_label_9.gridx = 0;
		gbc_label_9.gridy = 0;
		this.add(label_9, gbc_label_9);
		
		textField_priKeyFile = new JTextField();
		textField_priKeyFile.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				JFileChooser fileChooser = new JFileChooser(textField_priKeyFile.getText());
				//fileChooser.setVisible(true);
				LicenseFileFilter fileFilter = new LicenseFileFilter(new String[]{"key"});
				fileChooser.setFileFilter(fileFilter);
				fileChooser.addChoosableFileFilter(fileFilter);
				fileChooser.showSaveDialog(e.getComponent());
				File priKeyFile = fileChooser.getSelectedFile();	
				textField_priKeyFile.setText(priKeyFile.getAbsolutePath());
			}
		});
		GridBagConstraints gbc_textField_priKeyFile = new GridBagConstraints();
		gbc_textField_priKeyFile.insets = new Insets(0, 0, 5, 0);
		gbc_textField_priKeyFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_priKeyFile.gridx = 1;
		gbc_textField_priKeyFile.gridy = 0;
		this.add(textField_priKeyFile, gbc_textField_priKeyFile);
		textField_priKeyFile.setColumns(10);
		
		JLabel label = new JLabel("证书文件：");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		add(label, gbc_label);
		
		textField_licenseFile = new JTextField();
		textField_licenseFile.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				JFileChooser fileChooser = new JFileChooser(textField_licenseFile.getText());
				//fileChooser.setVisible(true);
				LicenseFileFilter fileFilter = new LicenseFileFilter(new String[]{"xml"});
				fileChooser.setFileFilter(fileFilter);
				fileChooser.addChoosableFileFilter(fileFilter);
				fileChooser.showSaveDialog(e.getComponent());
				File licenseFile = fileChooser.getSelectedFile();	
				textField_licenseFile.setText(licenseFile.getAbsolutePath());
			}
		});
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		add(textField_licenseFile, gbc_textField);
		textField_licenseFile.setColumns(10);
		
		JLabel label_2 = new JLabel("证书类型：");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.EAST;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 2;
		this.add(label_2, gbc_label_2);
		
		field_licenseType = new JComboBox();
		field_licenseType.setModel(new DefaultComboBoxModel(new String[] {"DEMO - 演示版", "TRIAL - 试用版", "STANDARD - 标准版", "ENTERPRISE - 企业版", "DEVELOPMENT - 开发版"}));
		GridBagConstraints gbc_field_licenseType = new GridBagConstraints();
		gbc_field_licenseType.insets = new Insets(0, 0, 5, 0);
		gbc_field_licenseType.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_licenseType.gridx = 1;
		gbc_field_licenseType.gridy = 2;
		this.add(field_licenseType, gbc_field_licenseType);
		
		JLabel label_3 = new JLabel("产品名称：");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.anchor = GridBagConstraints.EAST;
		gbc_label_3.gridx = 0;
		gbc_label_3.gridy = 3;
		this.add(label_3, gbc_label_3);
		
		field_productName = new JTextField();
		GridBagConstraints gbc_field_productName = new GridBagConstraints();
		gbc_field_productName.insets = new Insets(0, 0, 5, 0);
		gbc_field_productName.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_productName.gridx = 1;
		gbc_field_productName.gridy = 3;
		this.add(field_productName, gbc_field_productName);
		field_productName.setColumns(10);
		
		JLabel label_4 = new JLabel("公司名称：");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.anchor = GridBagConstraints.EAST;
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 0;
		gbc_label_4.gridy = 4;
		this.add(label_4, gbc_label_4);
		
		field_companyName = new JTextField();
		GridBagConstraints gbc_field_companyName = new GridBagConstraints();
		gbc_field_companyName.insets = new Insets(0, 0, 5, 0);
		gbc_field_companyName.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_companyName.gridx = 1;
		gbc_field_companyName.gridy = 4;
		this.add(field_companyName, gbc_field_companyName);
		field_companyName.setColumns(10);
		
		JLabel label_5 = new JLabel("客户名称：");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.anchor = GridBagConstraints.EAST;
		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		gbc_label_5.gridx = 0;
		gbc_label_5.gridy = 5;
		this.add(label_5, gbc_label_5);
		
		field_customerName = new JTextField();
		GridBagConstraints gbc_field_customerName = new GridBagConstraints();
		gbc_field_customerName.insets = new Insets(0, 0, 5, 0);
		gbc_field_customerName.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_customerName.gridx = 1;
		gbc_field_customerName.gridy = 5;
		this.add(field_customerName, gbc_field_customerName);
		field_customerName.setColumns(10);
		
		JLabel label_6 = new JLabel("版本：");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.anchor = GridBagConstraints.EAST;
		gbc_label_6.insets = new Insets(0, 0, 5, 5);
		gbc_label_6.gridx = 0;
		gbc_label_6.gridy = 6;
		this.add(label_6, gbc_label_6);
		
		field_version = new JTextField();
		GridBagConstraints gbc_field_version = new GridBagConstraints();
		gbc_field_version.insets = new Insets(0, 0, 5, 0);
		gbc_field_version.fill = GridBagConstraints.HORIZONTAL;
		gbc_field_version.gridx = 1;
		gbc_field_version.gridy = 6;
		this.add(field_version, gbc_field_version);
		field_version.setColumns(10);
		
		JLabel label_7 = new JLabel("过期时间：");
		GridBagConstraints gbc_label_7 = new GridBagConstraints();
		gbc_label_7.insets = new Insets(0, 0, 5, 5);
		gbc_label_7.gridx = 0;
		gbc_label_7.gridy = 7;
		this.add(label_7, gbc_label_7);
		
		field_expiredDate = new DatePicker(new Date(), "yyyy-MM-dd", null, null);
		//field_expiredDate.getInnerButton().setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_field_expiredDate = new GridBagConstraints();
		gbc_field_expiredDate.insets = new Insets(0, 0, 5, 0);
		gbc_field_expiredDate.anchor = GridBagConstraints.WEST;
		gbc_field_expiredDate.gridx = 1;
		gbc_field_expiredDate.gridy = 7;
		this.add(field_expiredDate, gbc_field_expiredDate);
		
		JButton button = new JButton("创建");
		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try
				{
					String licenseFilePath = textField_licenseFile.getText();
					String priKeyFilePath = textField_priKeyFile.getText();
						
					if(Utility.isEmpty(licenseFilePath))
					{
						JFileChooser fileChooser = new JFileChooser();
						//fileChooser.setVisible(true);
						LicenseFileFilter fileFilter = new LicenseFileFilter(new String[]{"xml"});
						fileChooser.setFileFilter(fileFilter);
						fileChooser.addChoosableFileFilter(fileFilter);
						fileChooser.showSaveDialog(e.getComponent());
						File licenseFile = fileChooser.getSelectedFile();	
						
						licenseFilePath = licenseFile.getAbsolutePath();						
					}
					if(Utility.isEmpty(priKeyFilePath))
					{
						JFileChooser fileChooser = new JFileChooser();
						//fileChooser.setVisible(true);
						LicenseFileFilter fileFilter = new LicenseFileFilter(new String[]{"xml"});
						fileChooser.setFileFilter(fileFilter);
						fileChooser.addChoosableFileFilter(fileFilter);
						fileChooser.showSaveDialog(e.getComponent());
						File priKeyFile = fileChooser.getSelectedFile();	
						
						priKeyFilePath = priKeyFile.getAbsolutePath();						
					}
						
						
					File licenseFile = new File(licenseFilePath);
					File priKeyFile = new File(priKeyFilePath);
														
						String licenseId = new RandomGUID().toString();
						String licenseType = Utility.splitStringAll(field_licenseType.getSelectedItem().toString(), " - ")[0];
						String productName = field_productName.getText();
						String companyName = field_companyName.getText();
						String customerName = field_customerName.getText();
						String version = field_version.getText();
						Date expiredDate = Utility.parseDate(field_expiredDate.getText());
											
						License license = new License();
						license.setLicenseId(licenseId);
						license.setLicenseType(licenseType);
						license.setProductName(productName);
						license.setCompanyName(companyName);
						license.setCustomerName(customerName);
						license.setVersion(Version.parseVersion(version));
						license.setCreateDate(new Date());
						license.setExpiredDate(expiredDate);
						
						JOptionPane.showMessageDialog(e.getComponent(), "创建成功！");
				}
				catch (Exception e1)
				{					
					JOptionPane.showMessageDialog(e.getComponent(), e1.getMessage());
				}
				
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.gridx = 1;
		gbc_button.gridy = 8;
		this.add(button, gbc_button);
	}

}
