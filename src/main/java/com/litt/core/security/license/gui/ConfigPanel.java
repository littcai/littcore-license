package com.litt.core.security.license.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.dom4j.Document;
import org.dom4j.Element;

import com.eltima.components.ui.DatePicker;
import com.litt.core.common.Utility;
import com.litt.core.format.FormatDateTime;
import com.litt.core.io.util.FileUtils;
import com.litt.core.io.util.ZipUtils;
import com.litt.core.license.License;
import com.litt.core.license.LicenseManager;
import com.litt.core.security.Algorithm;
import com.litt.core.security.ISecurity;
import com.litt.core.security.algorithm.DESTool;
import com.litt.core.security.license.DigitalSignatureTool;
import com.litt.core.security.license.LicenseConfig;
import com.litt.core.util.XmlUtils;
import com.litt.core.version.Version;

public class ConfigPanel extends JPanel
{
	private JPanel self = this;
	private JComboBox comboBox_config;
	private JTextField textField_licenseId;
	private JComboBox comboBox_licenseType ;
	private JTextField textField_productName;
	private JTextField textField_companyName;
	private JTextField textField_customerName;
	private JTextField textField_version;
	DatePicker datePicker_expiredDate ;
	
	private JTextArea txt_encryptContent;
	
	private LicenseConfig currentLicenseConfig;
	private JButton btnDelete;	

	/**
	 * Create the panel.
	 */
	public ConfigPanel(final Gui gui)
	{
		GridBagLayout gbl_configPanel = new GridBagLayout();
		gbl_configPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_configPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_configPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_configPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		this.setLayout(gbl_configPanel);
		
		JLabel label_18 = new JLabel("配置：");
		GridBagConstraints gbc_label_18 = new GridBagConstraints();
		gbc_label_18.insets = new Insets(0, 0, 5, 5);
		gbc_label_18.anchor = GridBagConstraints.EAST;
		gbc_label_18.gridx = 0;
		gbc_label_18.gridy = 0;
		this.add(label_18, gbc_label_18);
		
		comboBox_config = new JComboBox();
		comboBox_config.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED)
				{
					try
					{
						LicenseConfig licenseConfig = (LicenseConfig)e.getItem();
						File licenseFile = new File(licenseConfig.getLicenseFilePath());									
						currentLicenseConfig = licenseConfig;									
						loadLicense(licenseFile);
						gui.setCurrentLicenseConfig(currentLicenseConfig);
					}
					catch (Exception e1)
					{
						JOptionPane.showMessageDialog(self, e1.getMessage());
					}					
				}
			}
		});
		GridBagConstraints gbc_comboBox_config = new GridBagConstraints();
		gbc_comboBox_config.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_config.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_config.gridx = 1;
		gbc_comboBox_config.gridy = 0;
		this.add(comboBox_config, gbc_comboBox_config);
		
		JButton button_3 = new JButton("读取");
		button_3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
					try
					{
						comboBox_config.removeAllItems();
						
						File configFile =  new File(Gui.HOME_PATH+ File.separator+ "config.xml");								
						
						Document document = XmlUtils.readXml(configFile);
						Element rootE = document.getRootElement();
						List productList = rootE.elements();
						for(int i=0;i<productList.size();i++)
						{
							Element productE = (Element)productList.get(i);							
							String productCode = productE.attributeValue("code");
							
							List customerList = productE.elements();
							for(int j=0;j<customerList.size();j++)
							{
								Element customerE = (Element)customerList.get(j);	
								String customerCode = customerE.attributeValue("code");
								
								LicenseConfig licenseConfig = new LicenseConfig();
								licenseConfig.setProductCode(productCode);
								licenseConfig.setCustomerCode(customerCode);								
								licenseConfig.setLicenseId(customerE.elementText("licenseId"));								
								licenseConfig.setEncryptedLicense(customerE.elementText("encryptedLicense"));
								licenseConfig.setRootFilePath(Gui.HOME_PATH);	
								licenseConfig.setLicenseFilePath(Gui.HOME_PATH + File.separator + productCode + File.separator + customerCode + File.separator + "license.xml");
								licenseConfig.setPriKeyFilePath(Gui.HOME_PATH + File.separator + productCode + File.separator + "private.key");
								licenseConfig.setPubKeyFilePath(Gui.HOME_PATH + File.separator + productCode + File.separator + "license.key");
								comboBox_config.addItem(licenseConfig);
								if(i==0&&j==0)
								{
									File licenseFile = new File(licenseConfig.getLicenseFilePath());									
									currentLicenseConfig = licenseConfig;									
									loadLicense(licenseFile);
									gui.setCurrentLicenseConfig(currentLicenseConfig);
									
									btnDelete.setEnabled(true);
								}
							}
						}	
						
					}
					catch (Exception e1)
					{
						e1.printStackTrace();
						JOptionPane.showMessageDialog(self, e1.getMessage());
					}				
				
			}
		});
		
		btnDelete = new JButton();
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentLicenseConfig!=null)
				{
					try {
						String productCode = currentLicenseConfig.getProductCode();
						String customerCode = currentLicenseConfig.getCustomerCode();
						//删除congfig.xml中的定义
						File configFile =  new File(Gui.HOME_PATH+ File.separator+ "config.xml");						
						Document document = XmlUtils.readXml(configFile);
						
						Element customerNode = (Element)document.selectSingleNode("//product[@code='"+ productCode +"']/customer[@code='"+ customerCode +"']");
						if(customerNode!=null)
						{
							customerNode.detach();
							XmlUtils.writeXml(configFile, document);
						}
						//删除文件						
						File licensePathFile = new File(currentLicenseConfig.getLicenseFilePath());
						if(licensePathFile.exists())
						{
							FileUtils.deleteDirectory(licensePathFile.getParentFile());
						}
						
						//删除combobox的item
						comboBox_config.removeItemAt(comboBox_config.getSelectedIndex());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(comboBox_config.getItemCount()<=0)
					{
						btnDelete.setEnabled(false);
					}
					
				}
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setIcon(new ImageIcon(ConfigPanel.class.getResource("/images/icon_delete.png")));
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(0, 0, 5, 0);
		gbc_btnDelete.gridx = 2;
		gbc_btnDelete.gridy = 0;
		add(btnDelete, gbc_btnDelete);
		GridBagConstraints gbc_button_3 = new GridBagConstraints();
		gbc_button_3.insets = new Insets(0, 0, 5, 5);
		gbc_button_3.gridx = 1;
		gbc_button_3.gridy = 1;
		this.add(button_3, gbc_button_3);
		
		JLabel lblid = new JLabel("证书ID：");
		GridBagConstraints gbc_lblid = new GridBagConstraints();
		gbc_lblid.anchor = GridBagConstraints.EAST;
		gbc_lblid.insets = new Insets(0, 0, 5, 5);
		gbc_lblid.gridx = 0;
		gbc_lblid.gridy = 2;
		this.add(lblid, gbc_lblid);
		
		textField_licenseId = new JTextField();
		GridBagConstraints gbc_textField_licenseId = new GridBagConstraints();
		gbc_textField_licenseId.insets = new Insets(0, 0, 5, 5);
		gbc_textField_licenseId.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_licenseId.gridx = 1;
		gbc_textField_licenseId.gridy = 2;
		this.add(textField_licenseId, gbc_textField_licenseId);
		textField_licenseId.setColumns(10);
		
		JLabel label = new JLabel("证书类型：");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 3;
		add(label, gbc_label);
		
		comboBox_licenseType = new JComboBox(MapComboBoxModel.getLicenseTypeOptions().toArray());
		GridBagConstraints gbc_comboBox_licenseType = new GridBagConstraints();
		gbc_comboBox_licenseType.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_licenseType.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_licenseType.gridx = 1;
		gbc_comboBox_licenseType.gridy = 3;
		add(comboBox_licenseType, gbc_comboBox_licenseType);
		
		JLabel label_23 = new JLabel("产品名称：");
		GridBagConstraints gbc_label_23 = new GridBagConstraints();
		gbc_label_23.anchor = GridBagConstraints.EAST;
		gbc_label_23.insets = new Insets(0, 0, 5, 5);
		gbc_label_23.gridx = 0;
		gbc_label_23.gridy = 4;
		this.add(label_23, gbc_label_23);
		
		textField_productName = new JTextField();
		GridBagConstraints gbc_textField_productName = new GridBagConstraints();
		gbc_textField_productName.insets = new Insets(0, 0, 5, 5);
		gbc_textField_productName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_productName.gridx = 1;
		gbc_textField_productName.gridy = 4;
		this.add(textField_productName, gbc_textField_productName);
		textField_productName.setColumns(10);
		
		JLabel label_24 = new JLabel("公司名称：");
		GridBagConstraints gbc_label_24 = new GridBagConstraints();
		gbc_label_24.anchor = GridBagConstraints.EAST;
		gbc_label_24.insets = new Insets(0, 0, 5, 5);
		gbc_label_24.gridx = 0;
		gbc_label_24.gridy = 5;
		this.add(label_24, gbc_label_24);
		
		textField_companyName = new JTextField();
		GridBagConstraints gbc_textField_companyName = new GridBagConstraints();
		gbc_textField_companyName.insets = new Insets(0, 0, 5, 5);
		gbc_textField_companyName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_companyName.gridx = 1;
		gbc_textField_companyName.gridy = 5;
		this.add(textField_companyName, gbc_textField_companyName);
		textField_companyName.setColumns(10);
		
		JLabel label_25 = new JLabel("客户名称：");
		GridBagConstraints gbc_label_25 = new GridBagConstraints();
		gbc_label_25.anchor = GridBagConstraints.EAST;
		gbc_label_25.insets = new Insets(0, 0, 5, 5);
		gbc_label_25.gridx = 0;
		gbc_label_25.gridy = 6;
		this.add(label_25, gbc_label_25);
		
		textField_customerName = new JTextField();
		GridBagConstraints gbc_textField_customerName = new GridBagConstraints();
		gbc_textField_customerName.insets = new Insets(0, 0, 5, 5);
		gbc_textField_customerName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_customerName.gridx = 1;
		gbc_textField_customerName.gridy = 6;
		this.add(textField_customerName, gbc_textField_customerName);
		textField_customerName.setColumns(10);
		
		JLabel label_26 = new JLabel("版本：");
		GridBagConstraints gbc_label_26 = new GridBagConstraints();
		gbc_label_26.anchor = GridBagConstraints.EAST;
		gbc_label_26.insets = new Insets(0, 0, 5, 5);
		gbc_label_26.gridx = 0;
		gbc_label_26.gridy = 7;
		this.add(label_26, gbc_label_26);
		
		textField_version = new JTextField();
		GridBagConstraints gbc_textField_version = new GridBagConstraints();
		gbc_textField_version.insets = new Insets(0, 0, 5, 5);
		gbc_textField_version.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_version.gridx = 1;
		gbc_textField_version.gridy = 7;
		this.add(textField_version, gbc_textField_version);
		textField_version.setColumns(10);
		
		JLabel label_27 = new JLabel("过期时间：");
		GridBagConstraints gbc_label_27 = new GridBagConstraints();
		gbc_label_27.insets = new Insets(0, 0, 5, 5);
		gbc_label_27.gridx = 0;
		gbc_label_27.gridy = 8;
		this.add(label_27, gbc_label_27);
		
		datePicker_expiredDate = new DatePicker(new Date(), "yyyy-MM-dd", null, null);
		//datePicker_expiredDate.setTimePanleVisible(false);
		GridBagConstraints gbc_datePicker_expiredDate = new GridBagConstraints();
		gbc_datePicker_expiredDate.anchor = GridBagConstraints.WEST;
		gbc_datePicker_expiredDate.insets = new Insets(0, 0, 5, 5);
		gbc_datePicker_expiredDate.gridx = 1;
		gbc_datePicker_expiredDate.gridy = 8;
		this.add(datePicker_expiredDate, gbc_datePicker_expiredDate);
		
		JButton button_5 = new JButton("更新");
		button_5.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				if(currentLicenseConfig!=null)
				{	
					try
					{
						File priKeyFile = new File(currentLicenseConfig.getPriKeyFilePath());
						File licenseFile = new File(currentLicenseConfig.getLicenseFilePath());
						
						//更新证书信息
						License license = new License();
						license.setLicenseId(textField_licenseId.getText());
						license.setLicenseType(((MapComboBoxModel)comboBox_licenseType.getSelectedItem()).getValue().toString());
						license.setProductName(textField_productName.getText());
						license.setCompanyName(textField_companyName.getText());
						license.setCustomerName(textField_customerName.getText());
						license.setVersion(Version.parseVersion(textField_version.getText()));
						license.setCreateDate(new Date());
						license.setExpiredDate(Utility.parseDate(datePicker_expiredDate.getText()));
							
						//对数据源进行签名
						DigitalSignatureTool utils = new DigitalSignatureTool("DSA");		
						utils.readPriKey(priKeyFile.getAbsolutePath());	//读取私钥
						String signedData = utils.sign(license.toString());	//根据私钥签名数据源
						license.setSignature(signedData);
						
						LicenseManager.writeLicense(license, licenseFile);
						//更新config.xml文件
						String licenseContent = XmlUtils.readXml(licenseFile).asXML();
						//用DES算法进行加密，用于传输
						ISecurity security = new DESTool(currentLicenseConfig.getLicenseId(), Algorithm.BLOWFISH);
						licenseContent = security.encrypt(licenseContent);
						
						txt_encryptContent.setText(licenseContent);
						currentLicenseConfig.setEncryptedLicense(licenseContent);						
						System.out.println(licenseContent);
						File configFile = new File(Gui.HOME_PATH+ File.separator+ "config.xml");				
						
						XMLConfiguration config = new XMLConfiguration(configFile);  
						config.setAutoSave(true);
						
						config.setProperty("product.customer.encryptedLicense", licenseContent);
						
						//将证书跟公钥文件打包成zip，方便分发
						File customerPath = licenseFile.getParentFile();
						//将证书文件放到一个名为license的目录下再压缩，否则目录名不对
						File licensePath = new File(customerPath.getParent(), "license");
						if(!licensePath.exists() && !licensePath.isDirectory())
						{
							licensePath.mkdir();							
						}
						else
						{
							FileUtils.cleanDirectory(licensePath);
						}
						
						//复制文件
						FileUtils.copyDirectory(customerPath, licensePath);
						String currentTime = FormatDateTime.formatDateTimeNum(new Date());
						ZipUtils.zip(licensePath, new File(licensePath.getParentFile(), currentLicenseConfig.getCustomerCode()+"-"+currentTime+".zip"));
						
						//删除license临时目录
						FileUtils.deleteDirectory(licensePath);
						
						JOptionPane.showMessageDialog(self, "更新成功！");
					}
					catch (Exception e1)
					{
						e1.printStackTrace();
						JOptionPane.showMessageDialog(self, e1.getMessage());
					}
				}
				else
				{
					JOptionPane.showMessageDialog(self, "请先读取配置！");
				}
			}
		});
		GridBagConstraints gbc_button_5 = new GridBagConstraints();
		gbc_button_5.insets = new Insets(0, 0, 5, 5);
		gbc_button_5.gridx = 1;
		gbc_button_5.gridy = 9;
		this.add(button_5, gbc_button_5);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 10;
		this.add(scrollPane, gbc_scrollPane);
		
		txt_encryptContent = new JTextArea();
		txt_encryptContent.setLineWrap(true);
		scrollPane.setViewportView(txt_encryptContent);
		
//		txt_encryptContent = new JTextArea(20, 20);
//		GridBagConstraints gbc_6 = new GridBagConstraints();
//		gbc_6.gridx = 1;
//		gbc_6.gridy = 10;
//		this.add(txt_encryptContent, gbc_6);
		
	}


	/**
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	private void loadLicense(File licenseFile) throws FileNotFoundException, ConfigurationException
	{						
		XMLConfiguration config = new XMLConfiguration(licenseFile);  
		
							
		String licenseId = config.getString("licenseId");
		String licenseType = config.getString("licenseType");
		String productName = config.getString("productName");
		String companyName = config.getString("companyName");
		String customerName = config.getString("customerName");
		String version = config.getString("version");
		Date createDate = Utility.parseDate(config.getString("createDate"));
		Date expiredDate = Utility.parseDate(config.getString("expiredDate"));
		
								
		textField_licenseId.setText(licenseId);
		int typeCount = comboBox_licenseType.getItemCount();
		for(int i=0;i<typeCount;i++)
		{
			if(((MapComboBoxModel)comboBox_licenseType.getItemAt(i)).getValue().equals(licenseType))
			{
				comboBox_licenseType.setSelectedIndex(i);
				break;
			}
		}
		textField_productName.setText(productName);
		textField_companyName.setText(companyName);
		textField_customerName.setText(customerName);
		textField_version.setText(version);
		datePicker_expiredDate.getInnerTextField().setText(FormatDateTime.formatDate(expiredDate));
	
	}

}
