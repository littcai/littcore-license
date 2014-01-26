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

import com.litt.core.common.Utility;
import com.litt.core.security.Algorithm;
import com.litt.core.security.algorithm.DSATool;
import com.litt.core.security.license.LicenseFileFilter;

/**
 * 
 * 生成密钥与公钥.
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
 * @since 2012-6-12
 * @version 1.0
 */
public class KeyPanel extends JPanel
{
	private JTextField textField_securityKey;
	private JTextField textField_priKeyFilePath;
	private JTextField textField_pubKeyFilePath;

	/**
	 * Create the panel.
	 */
	public KeyPanel()
	{
		GridBagLayout gbl_keyPanel = new GridBagLayout();
		gbl_keyPanel.columnWidths = new int[]{0, 0, 0};
		gbl_keyPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_keyPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_keyPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		this.setLayout(gbl_keyPanel);
		
		JLabel label = new JLabel("密钥：");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		this.add(label, gbc_label);
		
		textField_securityKey = new JTextField();
		GridBagConstraints gbc_textField_securityKey = new GridBagConstraints();
		gbc_textField_securityKey.insets = new Insets(0, 0, 5, 5);
		gbc_textField_securityKey.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_securityKey.gridx = 1;
		gbc_textField_securityKey.gridy = 0;
		this.add(textField_securityKey, gbc_textField_securityKey);
		textField_securityKey.setColumns(10);
		
		JLabel label_1 = new JLabel("私钥路径：");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 1;
		this.add(label_1, gbc_label_1);
		
		textField_priKeyFilePath = new JTextField();
		textField_priKeyFilePath.setEditable(false);
		GridBagConstraints gbc_textField_priKeyFilePath = new GridBagConstraints();
		gbc_textField_priKeyFilePath.insets = new Insets(0, 0, 5, 5);
		gbc_textField_priKeyFilePath.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_priKeyFilePath.gridx = 1;
		gbc_textField_priKeyFilePath.gridy = 1;
		this.add(textField_priKeyFilePath, gbc_textField_priKeyFilePath);
		textField_priKeyFilePath.setColumns(10);
		
		JButton button_priKeySelect = new JButton("选择");

		button_priKeySelect.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				//fileChooser.setVisible(true);
				LicenseFileFilter fileFilter = new LicenseFileFilter(new String[]{"key"});
				fileChooser.setFileFilter(fileFilter);
				fileChooser.addChoosableFileFilter(fileFilter);
				fileChooser.showSaveDialog(e.getComponent());
				File file = fileChooser.getSelectedFile();
				if(file!=null)
				{
					textField_priKeyFilePath.setText(file.getPath());
				}	
			}
		});
		GridBagConstraints gbc_button_priKeySelect = new GridBagConstraints();
		gbc_button_priKeySelect.insets = new Insets(0, 0, 5, 0);
		gbc_button_priKeySelect.gridx = 2;
		gbc_button_priKeySelect.gridy = 1;
		this.add(button_priKeySelect, gbc_button_priKeySelect);
		
		JLabel label_8 = new JLabel("公钥路径：");
		GridBagConstraints gbc_label_8 = new GridBagConstraints();
		gbc_label_8.anchor = GridBagConstraints.EAST;
		gbc_label_8.insets = new Insets(0, 0, 5, 5);
		gbc_label_8.gridx = 0;
		gbc_label_8.gridy = 2;
		this.add(label_8, gbc_label_8);
		
		JButton button_1 = new JButton("创建");
		button_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String securityKey = textField_securityKey.getText();
				String priKeyFilePath = textField_priKeyFilePath.getText();
				String pubKeyFilePath = textField_pubKeyFilePath.getText();
				if(!Utility.isEmpty(priKeyFilePath) && !Utility.isEmpty(pubKeyFilePath))
				{
					try
					{
						DSATool utils = new DSATool(Algorithm.DSA, securityKey);
						utils.saveKeys(priKeyFilePath, pubKeyFilePath);
						JOptionPane.showMessageDialog(e.getComponent(), "创建成功！");
					}
					catch (Exception e1)
					{
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(e.getComponent(), e1.getMessage());
					}					
				}			
			}
		});
		
		textField_pubKeyFilePath = new JTextField();
		textField_pubKeyFilePath.setEditable(false);
		GridBagConstraints gbc_textField_pubKeyFilePath = new GridBagConstraints();
		gbc_textField_pubKeyFilePath.insets = new Insets(0, 0, 5, 5);
		gbc_textField_pubKeyFilePath.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_pubKeyFilePath.gridx = 1;
		gbc_textField_pubKeyFilePath.gridy = 2;
		this.add(textField_pubKeyFilePath, gbc_textField_pubKeyFilePath);
		textField_pubKeyFilePath.setColumns(10);
		
		JButton button_pubKeySelect = new JButton("选择");
		button_pubKeySelect.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				//fileChooser.setVisible(true);
				LicenseFileFilter fileFilter = new LicenseFileFilter(new String[]{"key"});
				fileChooser.setFileFilter(fileFilter);
				fileChooser.addChoosableFileFilter(fileFilter);
				fileChooser.showSaveDialog(e.getComponent());
				File file = fileChooser.getSelectedFile();
				if(file!=null)
				{
					textField_pubKeyFilePath.setText(file.getPath());
				}	
			}
		});
		GridBagConstraints gbc_button_pubKeySelect = new GridBagConstraints();
		gbc_button_pubKeySelect.insets = new Insets(0, 0, 5, 0);
		gbc_button_pubKeySelect.gridx = 2;
		gbc_button_pubKeySelect.gridy = 2;
		this.add(button_pubKeySelect, gbc_button_pubKeySelect);
		GridBagConstraints gbc_button_1_1 = new GridBagConstraints();
		gbc_button_1_1.insets = new Insets(0, 0, 0, 5);
		gbc_button_1_1.gridx = 1;
		gbc_button_1_1.gridy = 3;
		this.add(button_1, gbc_button_1_1);
	}

}
