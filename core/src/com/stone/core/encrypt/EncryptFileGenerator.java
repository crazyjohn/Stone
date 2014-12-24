package com.stone.core.encrypt;

import java.io.File;
import java.io.IOException;

/**
 * 加密一个指定的文件;
 * 
 * @author crazyjohn
 * 
 */
public class EncryptFileGenerator {
	public static void main(String[] args) {
		try {
			if (args.length < 2) {
				throw new RuntimeException("参数不合法");
			}
			genEncryptFile(args[0], args[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void genEncryptFile(String src, String des) throws IOException {
		File fromFile = new File(src);
		if (!fromFile.exists()) {
			throw new RuntimeException("原始文件[" + src + "]不存在");
		}
		File toFile = new File(des);
		if (!toFile.exists()) {
			toFile.createNewFile();
		}
		EncryptUtils.encryptFile(fromFile, toFile);

	}
}
