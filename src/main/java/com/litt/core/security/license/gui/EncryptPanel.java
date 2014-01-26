package com.litt.core.security.license.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.litt.core.security.Algorithm;
import com.litt.core.security.EncryptFailedException;
import com.litt.core.security.ISecurity;
import com.litt.core.security.algorithm.DESTool;
import com.litt.core.security.license.LicenseFileFilter;

/**
 * 
 * 证书加密.
 * 
 * <pre><b>Description：</b>
 *    通过DES算法加密整个证书文件，用于网络传递
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
public class EncryptPanel extends JPanel
{

	private JTextField textField_licenseFilePath;
	
	private JTextArea textArea_encrypt;
	private JTextArea textArea_decrypt;
	private JTextField textField_licenseId;
	
	/**
	 * Create the panel.
	 */
	public EncryptPanel()
	{
		GridBagLayout gbl_decryptPanel = new GridBagLayout();
		gbl_decryptPanel.columnWidths = new int[]{0, 0, 0};
		gbl_decryptPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_decryptPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_decryptPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		this.setLayout(gbl_decryptPanel);
		
		JLabel lblidLicense = new JLabel("证书文件：");
		GridBagConstraints gbc_lblidLicense = new GridBagConstraints();
		gbc_lblidLicense.anchor = GridBagConstraints.EAST;
		gbc_lblidLicense.insets = new Insets(0, 0, 5, 5);
		gbc_lblidLicense.gridx = 0;
		gbc_lblidLicense.gridy = 0;
		add(lblidLicense, gbc_lblidLicense);
		
		textField_licenseFilePath = new JTextField();
		GridBagConstraints gbc_textField_licenseFile = new GridBagConstraints();
		gbc_textField_licenseFile.insets = new Insets(0, 0, 5, 5);
		gbc_textField_licenseFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_licenseFile.gridx = 1;
		gbc_textField_licenseFile.gridy = 0;
		add(textField_licenseFilePath, gbc_textField_licenseFile);
		textField_licenseFilePath.setColumns(10);
		
		textField_licenseFilePath.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				//fileChooser.setVisible(true);
				LicenseFileFilter fileFilter = new LicenseFileFilter(new String[]{"xml"});
				fileChooser.setFileFilter(fileFilter);
				fileChooser.addChoosableFileFilter(fileFilter);
				fileChooser.showSaveDialog(e.getComponent());
				File file = fileChooser.getSelectedFile();
				if(file!=null)
				{
					textField_licenseFilePath.setText(file.getPath());
					
					
				}	
			}
		});
		
		JLabel lblid = new JLabel("证书ID：");
		GridBagConstraints gbc_lblid = new GridBagConstraints();
		gbc_lblid.anchor = GridBagConstraints.EAST;
		gbc_lblid.insets = new Insets(0, 0, 5, 5);
		gbc_lblid.gridx = 0;
		gbc_lblid.gridy = 1;
		add(lblid, gbc_lblid);
		
		textField_licenseId = new JTextField();
		GridBagConstraints gbc_textField_licenseId = new GridBagConstraints();
		gbc_textField_licenseId.insets = new Insets(0, 0, 5, 0);
		gbc_textField_licenseId.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_licenseId.gridx = 1;
		gbc_textField_licenseId.gridy = 1;
		add(textField_licenseId, gbc_textField_licenseId);
		textField_licenseId.setColumns(10);
		
		JLabel label_20 = new JLabel("原始内容：");
		GridBagConstraints gbc_label_20 = new GridBagConstraints();
		gbc_label_20.insets = new Insets(0, 0, 5, 5);
		gbc_label_20.anchor = GridBagConstraints.EAST;
		gbc_label_20.gridx = 0;
		gbc_label_20.gridy = 2;
		this.add(label_20, gbc_label_20);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		this.add(scrollPane, gbc_scrollPane);
		
		textArea_encrypt = new JTextArea();
		textArea_encrypt.setLineWrap(true);
		scrollPane.setViewportView(textArea_encrypt);
		
		JLabel label_21 = new JLabel("加密内容：");
		GridBagConstraints gbc_label_21 = new GridBagConstraints();
		gbc_label_21.insets = new Insets(0, 0, 5, 5);
		gbc_label_21.gridx = 0;
		gbc_label_21.gridy = 3;
		this.add(label_21, gbc_label_21);
		
		JScrollPane scrollPane2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane2 = new GridBagConstraints();
		gbc_scrollPane2.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane2.gridx = 1;
		gbc_scrollPane2.gridy = 3;
		this.add(scrollPane2, gbc_scrollPane2);
		
		textArea_decrypt = new JTextArea();
		textArea_decrypt.setLineWrap(true);
		scrollPane2.setViewportView(textArea_decrypt);
		
		JButton button_4 = new JButton("加密");
		button_4.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//用DES算法进行解密
				try
				{
					ISecurity security = new DESTool(textField_licenseId.getText(), Algorithm.BLOWFISH);
					textArea_decrypt.setText(security.encrypt(textArea_encrypt.getText()));
				}
				catch (NoSuchAlgorithmException e1)
				{
					e1.printStackTrace();
					JOptionPane.showMessageDialog(e.getComponent(), e1.getMessage());
				}
				catch (EncryptFailedException e1)
				{
					e1.printStackTrace();
					JOptionPane.showMessageDialog(e.getComponent(), e1.getMessage());
				}
			}
		});
		GridBagConstraints gbc_button_4_1 = new GridBagConstraints();
		gbc_button_4_1.gridx = 1;
		gbc_button_4_1.gridy = 4;
		this.add(button_4, gbc_button_4_1);
	}

}
