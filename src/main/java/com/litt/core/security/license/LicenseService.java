package com.litt.core.security.license;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Element;

import com.litt.core.license.License;
import com.litt.core.license.LicenseManager;
import com.litt.core.security.Algorithm;
import com.litt.core.security.ISecurity;
import com.litt.core.security.algorithm.DESTool;
import com.litt.core.security.license.gui.Gui;
import com.litt.core.util.ResourceUtils;
import com.litt.core.util.XmlUtils;

public class LicenseService {
	
	
	/**
	 * 保存License
	 * @param homePath
	 * @param license
	 * @param priKeyFile
	 * @param licenseFile
	 * @throws Exception
	 */
	public void save(String homePath, String productCode, String customerCode, License license, File priKeyFile, File licenseFile) throws Exception
	{		
		//对数据源进行签名
		DigitalSignatureTool utils = new DigitalSignatureTool("DSA");		
		utils.readPriKey(priKeyFile.getAbsolutePath());	//读取私钥
		String signedData = utils.sign(license.toString());	//根据私钥签名数据源
		license.setSignature(signedData);
		
		LicenseManager.writeLicense(license, licenseFile);
		
		String licenseContent = XmlUtils.readXml(licenseFile).asXML();
		//用DES算法进行加密，用于传输
		ISecurity security = new DESTool(license.getLicenseId(), Algorithm.BLOWFISH);
		licenseContent = security.encrypt(licenseContent);
		//更新config.xml文件
		File configFile = ResourceUtils.getFile(homePath + File.separator + "config.xml");
		Document document = XmlUtils.readXml(configFile);
		Element rootE = document.getRootElement();
		Element produceNode = (Element)document.selectSingleNode("//product[@code='"+ productCode +"']");
		
		Element customerElement = produceNode.addElement("customer");
		customerElement.addAttribute("code", customerCode);
		customerElement.addElement("licenseId").setText(license.getLicenseId());
		customerElement.addElement("encryptedLicense").setText(licenseContent);		
		
		XmlUtils.writeXml(configFile, document);
		XmlUtils.formatXml(configFile, XmlUtils.FORMAT_PREETY);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
