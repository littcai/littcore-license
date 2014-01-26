package com.litt.core.security.license;

import java.io.File;

/** 
 * 
 * TODO.
 * 
 * <pre><b>描述：</b>
 *    TODO 
 * </pre>
 * 
 * <pre><b>修改记录：</b>
 *    TODO
 * </pre>
 * 
 * @author <a href="mailto:littcai@hotmail.com">蔡源</a>
 * @since 2010-2-6
 * @version 1.0
 *
 */
public class LicenseConfig
{
	private String productCode;
	
	private String customerCode;
	
	/**
	 * The license id.
	 */
	private String licenseId;
	
	private String priKeyFilePath;
	
	private String pubKeyFilePath;
	
	private String licenseFilePath;
	
	private String encryptedLicense;

	private String rootFilePath;
	
	public String getSecurityKey()
	{
		return this.productCode+"-"+this.customerCode;
	}
	
	public String toString()
	{		
		return this.productCode+"-"+this.customerCode;
	}	

	/**
	 * @return the customerName
	 */
	public String getCustomerCode()
	{
		return customerCode;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerCode(String customerName)
	{
		this.customerCode = customerName;
	}

	/**
	 * @return the encryptedLicense
	 */
	public String getEncryptedLicense()
	{
		return encryptedLicense;
	}

	/**
	 * @param encryptedLicense the encryptedLicense to set
	 */
	public void setEncryptedLicense(String encryptedLicense)
	{
		this.encryptedLicense = encryptedLicense;
	}

	/**
	 * @return the licenseFilePath
	 */
	public String getLicenseFilePath()
	{
		return licenseFilePath;
	}

	/**
	 * @param licenseFilePath the licenseFilePath to set
	 */
	public void setLicenseFilePath(String licenseFilePath)
	{
		this.licenseFilePath = licenseFilePath;
	}

	/**
	 * @return the priKeyFilePath
	 */
	public String getPriKeyFilePath()
	{
		return priKeyFilePath;
	}

	/**
	 * @param priKeyFilePath the priKeyFilePath to set
	 */
	public void setPriKeyFilePath(String priKeyFilePath)
	{
		this.priKeyFilePath = priKeyFilePath;
	}

	/**
	 * @return the productName
	 */
	public String getProductCode()
	{
		return productCode;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductCode(String productName)
	{
		this.productCode = productName;
	}

	/**
	 * @return the pubKeyFilePath
	 */
	public String getPubKeyFilePath()
	{
		return pubKeyFilePath;
	}

	/**
	 * @param pubKeyFilePath the pubKeyFilePath to set
	 */
	public void setPubKeyFilePath(String pubKeyFilePath)
	{
		this.pubKeyFilePath = pubKeyFilePath;
	}

	/**
	 * @return the rootFilePath
	 */
	public String getRootFilePath()
	{
		return rootFilePath;
	}

	/**
	 * @param rootFilePath the rootFilePath to set
	 */
	public void setRootFilePath(String rootFilePath)
	{
		this.rootFilePath = rootFilePath;
	}

	/**
	 * @return the licenseId
	 */
	public String getLicenseId()
	{
		return licenseId;
	}

	/**
	 * @param licenseId the licenseId to set
	 */
	public void setLicenseId(String licenseId)
	{
		this.licenseId = licenseId;
	}
	
}
