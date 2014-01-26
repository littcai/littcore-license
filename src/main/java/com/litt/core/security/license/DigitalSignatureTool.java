package com.litt.core.security.license;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import com.litt.core.common.Utility;
import com.litt.core.license.LicenseManager;
import com.litt.core.security.DecryptFailedException;
import com.litt.core.security.EncryptFailedException;
import com.litt.core.util.ByteUtils;
import com.litt.core.util.ResourceUtils;

/**
 * 
 * DSA数字签名.
 * <pre><b>描述：</b>
 *    非对称加密,数字签名是利用私钥加密，公钥解密，用以保证不可否认性和完整性
 *    支持算法：
 *    1、DSA 
 *    2、SHA1withDSA
 *    3、MD2withRSA
 *    4、MD5withRSA 
 *    5、SHA1withRSA
 * </pre>
 * 
 * @author <a href="mailto:littcai@hotmail.com">空心大白菜</a>
 * @since 2008-12-12,2009-03-10
 * @version 1.0,2.0
 */
public class DigitalSignatureTool 
{	
	/** 签名算法. */
	private String algorithm;

	/** 公钥. */
	private PublicKey pubKey;

	/** 私钥. */
	private PrivateKey priKey;

	/** 公钥文件存放路径. */
	public String pubFileName;

	/** 私钥文件存放路径. */
	public String priFileName;

	public DigitalSignatureTool(String algorithm) {
		this.algorithm = algorithm;
	}



	/**
	 * 从磁盘读取密钥信息.
	 * 
	 * @param fileName
	 *            密钥存放路径
	 * 
	 * @return 返回密钥信息，为 Object 类，然后可根据具体的 PublicKey 或 PrivateKey 进行强制类型转换
	 * 
	 * @throws DecryptFailedException
	 *             the decrypt failed exception
	 */
	public Object readKey(String fileName) throws DecryptFailedException
	{
		Object obj = null;
		try {
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = ois.readObject();
			ois.close();
		} catch (IOException e) {
			throw new DecryptFailedException("读取密钥对信息出错！",e);		
		} catch (ClassNotFoundException e) {
			throw new DecryptFailedException("读取密钥对信息出错！",e);		
		}
		return obj;
	}
	
	/**
	 * 从磁盘读取密钥信息.
	 * 
	 * @param file
	 *            密钥文件
	 * 
	 * @return 返回密钥信息，为 Object 类，然后可根据具体的 PublicKey 或 PrivateKey 进行强制类型转换
	 * 
	 * @throws DecryptFailedException
	 *             the decrypt failed exception
	 */
	public Object readKey(File file) throws DecryptFailedException
	{
		Object obj = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = ois.readObject();
			ois.close();
		} catch (IOException e) {
			throw new DecryptFailedException("读取密钥对信息出错！",e);		
		} catch (ClassNotFoundException e) {
			throw new DecryptFailedException("读取密钥对信息出错！",e);		
		}
		return obj;
	}	

	/**
	 * 获取公钥，如果当前公钥为空，则从磁盘文件读取，并将其设置为当前公钥，否则直接返回当前公钥.
	 * 
	 * @return PublicKey 公钥
	 * 
	 * @throws DecryptFailedException
	 *             the decrypt failed exception
	 */
	public PublicKey readPubKey() throws DecryptFailedException
	{
		if (pubKey != null)
			return pubKey;
		if (!Utility.isEmpty(pubFileName)) 
		{
			pubKey = (PublicKey) this.readKey(pubFileName);
			return this.pubKey;
		} else {
			throw new IllegalArgumentException("公钥路径不正确！");						
		}
	}

	/**
	 * 获取公钥，如果当前公钥为空，则从磁盘文件读取，并将其设置为当前公钥，否则直接返回当前公钥.
	 * 
	 * @param pubFileName
	 *            公钥文件路径
	 * 
	 * @return PublicKey 公钥
	 * 
	 * @throws DecryptFailedException
	 *             the decrypt failed exception
	 */
	public PublicKey readPubKey(String pubFileName) throws DecryptFailedException
	{
		pubKey = (PublicKey) this.readKey(pubFileName);
		return this.pubKey;
	}
	
	/**
	 * 获取公钥，如果当前公钥为空，则从磁盘文件读取，并将其设置为当前公钥，否则直接返回当前公钥.
	 * 
	 * @param pubFile
	 *            公钥文件
	 * 
	 * @return PublicKey 公钥
	 * 
	 * @throws DecryptFailedException
	 *             the decrypt failed exception
	 */
	public PublicKey readPubKey(File pubFile) throws DecryptFailedException
	{
		pubKey = (PublicKey) this.readKey(pubFile);
		return this.pubKey;
	}	


	/**
	 * 获取私钥，如果当前私钥为空，则从磁盘文件读取，并将其设置为当前私钥，否则直接返回当前私钥.
	 * 
	 * @return PrivateKey私钥
	 * 
	 * @throws DecryptFailedException
	 *             the decrypt failed exception
	 */
	public PrivateKey readPriKey() throws DecryptFailedException
	{
		if (priKey != null)
			return priKey;
		if (!Utility.isEmpty(priFileName)) 
		{
			priKey = (PrivateKey) this.readKey(priFileName);
			return this.priKey;
		} else {
			throw new IllegalArgumentException("私钥路径不正确！");				
		}
	}
	
	/**
	 * 获取私钥，如果当前私钥为空，则从磁盘文件读取，并将其设置为当前私钥，否则直接返回当前私钥.
	 * 
	 * @param priFileName
	 *            私钥文件存放路径
	 * 
	 * @return PrivateKey私钥
	 * 
	 * @throws DecryptFailedException
	 *             the decrypt failed exception
	 */
	public PrivateKey readPriKey(String priFileName)  throws DecryptFailedException
	{
		priKey = (PrivateKey) this.readKey(priFileName);
		return this.priKey;
	}
	
	/**
	 * 利用当前私钥对信息进行签名.
	 * 
	 * @param source
	 *            需要签名的字符串
	 * 
	 * @return 签名信息（byte类型）
	 */
	public String sign(String source) throws EncryptFailedException
	{
		return this.sign(source.getBytes());
	}

	/**
	 * 利用当前私钥对信息进行签名.
	 * 
	 * @param source            
	 *            需要签名的字符串
	 * 
	 * @return 签名信息（byte类型）
	 * 
	 * @throws EncryptFailedException
	 *             the encrypt failed exception
	 */
	public String sign(byte[] source) throws EncryptFailedException
	{		
		if (priKey == null) 
		{
			throw new EncryptFailedException("提取密钥信息错误，无法完成签名！");			
		}
		try {
			Signature signet = Signature.getInstance(algorithm);	//没有默认算法名，必须指定名称
			signet.initSign(priKey);
			signet.update(source);
			return ByteUtils.toHexString(signet.sign());
		} catch (NoSuchAlgorithmException e) {
			throw new EncryptFailedException(e);
		} catch (InvalidKeyException e) {
			throw new EncryptFailedException("签名错误，无效的密钥！",e);
		} catch (SignatureException e) 
		{
			throw new EncryptFailedException(e);
		}		
	}


	/**
	 * 验证签名信息.
	 * 
	 * @param source
	 *            待验证的信息
	 * @param signed
	 *            该验证信息的签名
	 * 
	 * @return 若验证正常，则返回 true , 否则返回 false
	 */
	public boolean validateSign(String source, String signed) throws DecryptFailedException
	{
		if (pubKey == null) {
			throw new DecryptFailedException("提取密钥信息错误，无法完成签名");			
		}
		try 
		{
			Signature checkSignet = Signature.getInstance(algorithm);	
			checkSignet.initVerify(pubKey);
			checkSignet.update(source.getBytes());
			if (checkSignet.verify(ByteUtils.hexStringToByteArray(signed))) 
			{				
				return true;
			} 
			else 
			{
				throw new DecryptFailedException("签名验证失败！");				
			}
		} catch (NoSuchAlgorithmException e) 
		{
			throw new DecryptFailedException(e);
		} catch (InvalidKeyException e) 
		{
			throw new DecryptFailedException("签名验证失败，无效的密钥！",e);
			
		} catch (SignatureException e) {
			throw new DecryptFailedException("签名验证失败！",e);
		}		
	}

	public static void main(String[] args) throws Exception
	{
		String source = "Evaluation/enterprise/littcore/heyuan/1.0/20080915/20081030";	
		//创建默认私钥公钥
//		DSATools utils = new DSATools();
//		utils.saveKeys("D:\\pirKey.key", "D:\\pubKey.key");
//		//对数据源进行签名
//		DigitalSignatureTool utils = new DigitalSignatureTool("DSA");		
//		utils.readPriKey("priKey.dat");	//读取私钥
//		String signedData = utils.sign(source);	//根据私钥签名数据源
//		System.out.println(signedData);
		//进行签名校验	
		File licenseFile = ResourceUtils.getFile("classpath:teamwork/litt/license.xml");
		File pubKeyFile = ResourceUtils.getFile("classpath:teamwork/litt/littPub.key");
		
		LicenseManager.validateLicense(licenseFile.getAbsolutePath(), pubKeyFile.getAbsolutePath());

	}

	/**
	 * @return the algorithm
	 */
	public String getAlgorithm()
	{
		return algorithm;
	}

	/**
	 * @param algorithm the algorithm to set
	 */
	public void setAlgorithm(String algorithm)
	{
		this.algorithm = algorithm;
	}

}
