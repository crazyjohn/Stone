package com.i4joy.util;

import java.io.IOException;
import java.io.OutputStream;

public class MOutput {

	public static void writeLong(long v, OutputStream out) throws IOException {
		byte writeBuffer[] = new byte[8];
		writeBuffer[7] = (byte) (v >>> 56);
		writeBuffer[6] = (byte) (v >>> 48);
		writeBuffer[5] = (byte) (v >>> 40);
		writeBuffer[4] = (byte) (v >>> 32);
		writeBuffer[3] = (byte) (v >>> 24);
		writeBuffer[2] = (byte) (v >>> 16);
		writeBuffer[1] = (byte) (v >>> 8);
		writeBuffer[0] = (byte) (v >>> 0);
		out.write(writeBuffer, 0, 8);		
	}

	public static void writeInt(int v, OutputStream out) throws IOException {
		out.write((v >>> 0) & 0xFF);
		out.write((v >>> 8) & 0xFF);
		out.write((v >>> 16) & 0xFF);
		out.write((v >>> 24) & 0xFF);
	}

	public static void writeShort(int v, OutputStream out) throws IOException {
		out.write((v >>> 0) & 0xFF);
		out.write((v >>> 8) & 0xFF);
	}
	
	
	public static void writeBoolean(boolean v, OutputStream out) throws IOException {
		out.write(v ? 1 : 0);
	}
	
	

	public static void writeUTF(String v, OutputStream out) throws IOException {
		byte[] bytes = v.getBytes("UTF-8");
		MOutput.writeShort(bytes.length, out);
		out.write(bytes);
	}

	/**
	 * 将一个Int 数据，转换为byte数组. JAVA直接使用
	 * 
	 * @param intValue
	 *            Int 数据
	 * @return byte数组.
	 */
	public static byte[] Int2Bytes(int intValue) {
		byte[] result = new byte[4];
		result[0] = (byte) ((intValue & 0xFF000000) >> 24);
		result[1] = (byte) ((intValue & 0x00FF0000) >> 16);
		result[2] = (byte) ((intValue & 0x0000FF00) >> 8);
		result[3] = (byte) ((intValue & 0x000000FF));
		return result;
	}

	/**
	 * 将int转为低字节在前，高字节在后的byte数组 转为C#需要的的数组顺序
	 * 
	 */
	private static byte[] Int2BytesLH(int n) {
		byte[] b = new byte[4];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		b[2] = (byte) (n >> 16 & 0xff);
		b[3] = (byte) (n >> 24 & 0xff);
		return b;
	}

	/**
	 * 将byte[]转为低字节在前，高字节在后的byte数组 从C#收包后转换为JAVA的数组顺序
	 */
	private static byte[] BytestoHL(byte[] n) {
		byte[] b = new byte[4];
		b[3] = n[0];
		b[2] = n[1];
		b[1] = n[2];
		b[0] = n[3];
		return b;
	}

	/**
	 * 将byte数组的数据，转换成Int值. JAVA直接使用
	 * 
	 * @param byteVal
	 *            byte数组
	 * @return Int值.
	 */
	public static int Bytes2Int(byte[] byteVal) {
		int result = 0;
		for (int i = 0; i < byteVal.length; i++) {
			int tmpVal = (byteVal[i] << (8 * (3 - i)));
			switch (i) {
			case 0:
				tmpVal = tmpVal & 0xFF000000;
				break;
			case 1:
				tmpVal = tmpVal & 0x00FF0000;
				break;
			case 2:
				tmpVal = tmpVal & 0x0000FF00;
				break;
			case 3:
				tmpVal = tmpVal & 0x000000FF;
				break;
			}
			result = result | tmpVal;
		}
		return result;
	}
	
	public static void writeByte(int v, OutputStream out) throws IOException {
		out.write(v);
	}
	
	public static void main(String [] args)
	{
		System.out.println(Long.MAX_VALUE);
		System.out.println(Long.MIN_VALUE);
	}

}
