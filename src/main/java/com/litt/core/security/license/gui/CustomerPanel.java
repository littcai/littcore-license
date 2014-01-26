package com.litt.core.security.license.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import com.eltima.components.ui.DatePicker;
import com.litt.core.common.Utility;
import com.litt.core.format.FormatDateTime;
import com.litt.core.io.util.ZipUtils;
import com.litt.core.license.License;
import com.litt.core.security.license.LicenseService;
import com.litt.core.uid.RandomGUID;
import com.litt.core.util.ResourceUtils;
import com.litt.core.util.XmlUtils;
import com.litt.core.version.Version;
import javax.swing.border.EmptyBorder;

/**
 * 
 * 创建客户证书.
 * 
 * <pre><b>Description：</b>
 *    
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
public class CustomerPanel extends JPanel {
	
	private JTextField textFieldCustomerName;
	private JComboBox comboBoxProduct;
	private JComboBox comboBoxLicenseType;
	private DatePicker datePickerExpiredDate;
	private JTextField textFieldCompanyName;
	private JTextField textFieldCustomerCode;
	private VersionTextField textFieldProductVersion;
	
	
	
	private void initData()
	{	
		initProduct();		
		comboBoxLicenseType.setModel(new DefaultComboBoxModel(new String[] {"DEMO - 演示版", "TRIAL - 试用版", "STANDARD - 标准版", "ENTERPRISE - 企业版", "DEVELOPMENT - 开发版"}));
	}

	private void initProduct() {
		try {
			//读取产品信息
			comboBoxProduct.removeAllItems();
			
			comboBoxProduct.addItem("请选择...");
			
			File configFile =  new File(Gui.HOME_PATH, "config.xml");								
			
			Document document = XmlUtils.readXml(configFile);
			Element rootE = document.getRootElement();
			List<Element> productNodeList = rootE.elements();	
			for(Element productNode : productNodeList)
			{
				String productCode = productNode.attributeValue("code");
				String productName = productNode.attributeValue("name");
				comboBoxProduct.addItem(productCode+"-"+productName);
			}
		} catch (Exception e) {			
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
	
	public CustomerPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		setLayout(gridBagLayout);
		
		JLabel lblProduct = new JLabel("\u4EA7\u54C1\uFF1A");
		GridBagConstraints gbc_lblProduct = new GridBagConstraints();
		gbc_lblProduct.insets = new Insets(0, 0, 5, 5);
		gbc_lblProduct.anchor = GridBagConstraints.EAST;
		gbc_lblProduct.gridx = 0;
		gbc_lblProduct.gridy = 0;
		add(lblProduct, gbc_lblProduct);
		
		comboBoxProduct = new JComboBox();
		GridBagConstraints gbc_comboBoxProduct = new GridBagConstraints();
		gbc_comboBoxProduct.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxProduct.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxProduct.gridx = 1;
		gbc_comboBoxProduct.gridy = 0;
		add(comboBoxProduct, gbc_comboBoxProduct);
		
		JButton btnRefresh = new JButton("");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initProduct();				
			}
		});
		btnRefresh.setIcon(new ImageIcon(CustomerPanel.class.getResource("/images/icon_refresh.png")));
		GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
		gbc_btnRefresh.insets = new Insets(0, 0, 5, 0);
		gbc_btnRefresh.gridx = 2;
		gbc_btnRefresh.gridy = 0;
		add(btnRefresh, gbc_btnRefresh);
		
		JLabel lblProductVersion = new JLabel("\u4EA7\u54C1\u7248\u672C\uFF1A");
		GridBagConstraints gbc_lblProductVersion = new GridBagConstraints();
		gbc_lblProductVersion.anchor = GridBagConstraints.EAST;
		gbc_lblProductVersion.insets = new Insets(0, 0, 5, 5);
		gbc_lblProductVersion.gridx = 0;
		gbc_lblProductVersion.gridy = 1;
		add(lblProductVersion, gbc_lblProductVersion);
		
		textFieldProductVersion = new VersionTextField();
		textFieldProductVersion.setColumns(10);
		GridBagConstraints gbc_textFieldProductVersion = new GridBagConstraints();
		gbc_textFieldProductVersion.anchor = GridBagConstraints.WEST;
		gbc_textFieldProductVersion.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldProductVersion.gridx = 1;
		gbc_textFieldProductVersion.gridy = 1;
		add(textFieldProductVersion, gbc_textFieldProductVersion);
		
		JLabel lblCompanyName = new JLabel("\u516C\u53F8\u540D\u79F0\uFF1A");
		GridBagConstraints gbc_lblCompanyName = new GridBagConstraints();
		gbc_lblCompanyName.anchor = GridBagConstraints.EAST;
		gbc_lblCompanyName.insets = new Insets(0, 0, 5, 5);
		gbc_lblCompanyName.gridx = 0;
		gbc_lblCompanyName.gridy = 2;
		add(lblCompanyName, gbc_lblCompanyName);
		
		textFieldCompanyName = new JTextField();
		GridBagConstraints gbc_textFieldCompanyName = new GridBagConstraints();
		gbc_textFieldCompanyName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCompanyName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCompanyName.gridx = 1;
		gbc_textFieldCompanyName.gridy = 2;
		add(textFieldCompanyName, gbc_textFieldCompanyName);
		textFieldCompanyName.setColumns(10);
		
		JLabel lblCustomerCode = new JLabel("\u5BA2\u6237\u7F16\u53F7\uFF1A");
		GridBagConstraints gbc_lblCustomerCode = new GridBagConstraints();
		gbc_lblCustomerCode.anchor = GridBagConstraints.EAST;
		gbc_lblCustomerCode.insets = new Insets(0, 0, 5, 5);
		gbc_lblCustomerCode.gridx = 0;
		gbc_lblCustomerCode.gridy = 3;
		add(lblCustomerCode, gbc_lblCustomerCode);
		
		textFieldCustomerCode = new JTextField();
		GridBagConstraints gbc_textFieldCustomerCode = new GridBagConstraints();
		gbc_textFieldCustomerCode.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCustomerCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCustomerCode.gridx = 1;
		gbc_textFieldCustomerCode.gridy = 3;
		add(textFieldCustomerCode, gbc_textFieldCustomerCode);
		textFieldCustomerCode.setColumns(10);
		
		JLabel lblCustomerName = new JLabel("\u5BA2\u6237\u540D\u79F0\uFF1A");
		GridBagConstraints gbc_lblCustomerName = new GridBagConstraints();
		gbc_lblCustomerName.anchor = GridBagConstraints.EAST;
		gbc_lblCustomerName.insets = new Insets(0, 0, 5, 5);
		gbc_lblCustomerName.gridx = 0;
		gbc_lblCustomerName.gridy = 4;
		add(lblCustomerName, gbc_lblCustomerName);
		
		textFieldCustomerName = new JTextField();
		GridBagConstraints gbc_textFieldCustomerName = new GridBagConstraints();
		gbc_textFieldCustomerName.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCustomerName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCustomerName.gridx = 1;
		gbc_textFieldCustomerName.gridy = 4;
		add(textFieldCustomerName, gbc_textFieldCustomerName);
		textFieldCustomerName.setColumns(10);
		
		JLabel lblLicenseType = new JLabel("\u8BC1\u4E66\u7C7B\u578B\uFF1A");
		GridBagConstraints gbc_lblLicenseType = new GridBagConstraints();
		gbc_lblLicenseType.anchor = GridBagConstraints.EAST;
		gbc_lblLicenseType.insets = new Insets(0, 0, 5, 5);
		gbc_lblLicenseType.gridx = 0;
		gbc_lblLicenseType.gridy = 5;
		add(lblLicenseType, gbc_lblLicenseType);
		
		comboBoxLicenseType = new JComboBox();
		GridBagConstraints gbc_comboBoxLicenseType = new GridBagConstraints();
		gbc_comboBoxLicenseType.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxLicenseType.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxLicenseType.gridx = 1;
		gbc_comboBoxLicenseType.gridy = 5;
		add(comboBoxLicenseType, gbc_comboBoxLicenseType);
		
		JLabel lblExpiredDate = new JLabel("\u8FC7\u671F\u65F6\u95F4\uFF1A");
		GridBagConstraints gbc_lblExpiredDate = new GridBagConstraints();
		gbc_lblExpiredDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblExpiredDate.gridx = 0;
		gbc_lblExpiredDate.gridy = 6;
		add(lblExpiredDate, gbc_lblExpiredDate);
		
		datePickerExpiredDate = new DatePicker(new Date(), "yyyy-MM-dd", null, null);
		GridBagConstraints gbc_datePicker = new GridBagConstraints();
		gbc_datePicker.anchor = GridBagConstraints.WEST;
		gbc_datePicker.insets = new Insets(0, 0, 5, 5);
		gbc_datePicker.gridx = 1;
		gbc_datePicker.gridy = 6;
		add(datePickerExpiredDate, gbc_datePicker);
		
		JButton btnSave = new JButton("\u4FDD\u5B58");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//根据选择的产品获得产品名称和公司名称
					if(comboBoxProduct.getSelectedIndex()<=0)
					{
						JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "请选择一个产品！");
						return;
					}
					String[] array = StringUtils.split(comboBoxProduct.getSelectedItem().toString(), '-');
					String productCode = array[0];
					String productName = array[1];
					String customerCode = textFieldCustomerCode.getText();
									
					//检查客户编号是否存在
					File configFile = ResourceUtils.getFile(Gui.HOME_PATH + File.separator + "config.xml");
					Document document = XmlUtils.readXml(configFile);
					Element customerNode = (Element)document.selectSingleNode("//product[@code='"+ productCode +"']/customer[@code='"+ customerCode +"']");
					if(customerNode!=null)
					{
						JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "客户编号已存在，请重新输入！");
						return;
					}
					
					String licenseId = new RandomGUID().toString();
					String licenseType = Utility.splitStringAll(comboBoxLicenseType.getSelectedItem().toString(), " - ")[0];				
					String companyName = textFieldCompanyName.getText();
					String customerName = textFieldCustomerName.getText();
					String version = textFieldProductVersion.getText();
					Date expiredDate = Utility.parseDate(datePickerExpiredDate.getText());
										
					License license = new License();
					license.setLicenseId(licenseId);
					license.setLicenseType(licenseType);
					license.setProductName(productName);
					license.setCompanyName(companyName);
					license.setCustomerName(customerName);
					license.setVersion(Version.parseVersion(version));
					license.setCreateDate(new Date());
					license.setExpiredDate(expiredDate);
					
					LicenseService service = new LicenseService();
					String productPath = Gui.HOME_PATH + File.separator + productCode;
					File licenseDir = new File(productPath, customerCode);
					if(!licenseDir.exists())
					{
						licenseDir.mkdir();
						//复制公钥到该文件夹
						FileUtils.copyFileToDirectory(new File(productPath, "license.key"), licenseDir);
					}
					File priKeyFile = new File(Gui.HOME_PATH + File.separator + productCode, "private.key");
					File licenseFile = new File(licenseDir, "license.xml");				
				
					service.save(Gui.HOME_PATH, productCode, customerCode, license, priKeyFile, licenseFile);
					
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
					ZipUtils.zip(licensePath, new File(licensePath.getParentFile(), customerCode+"-"+currentTime+".zip"));
					//删除license临时目录
					FileUtils.deleteDirectory(licensePath);
					
					JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "创建成功！");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), e1.getMessage());
				}
				
			}
		});
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 1;
		gbc_btnSave.gridy = 7;
		add(btnSave, gbc_btnSave);
		
		//初始化数据
		initData();
	}

}
