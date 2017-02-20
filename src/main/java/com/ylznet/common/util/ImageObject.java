package com.ylznet.common.util;

import java.io.Serializable;

/**
 * <b style="color:#e94d08;">socket 传输图片数据包类</b>
 *
 * @author zxl
 *
 */
public class ImageObject implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String savePath = "";
	private String fileName = "";
	private String fileType = "";
	private byte[] imagebt = null;

	public ImageObject(String savePath, String fileName, String fileType, byte[] imagebt)
	{
		super();
		this.savePath = savePath;
		this.fileName = fileName;
		this.fileType = fileType;
		this.imagebt = imagebt;
	}

	public String getSavePath()
	{
		return savePath;
	}

	public void setSavePath(String savePath)
	{
		this.savePath = savePath;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getFileType()
	{
		return fileType;
	}

	public void setFileType(String fileType)
	{
		this.fileType = fileType;
	}

	public byte[] getImagebt()
	{
		return imagebt;
	}

	public void setImagebt(byte[] imagebt)
	{
		this.imagebt = imagebt;
	}

}