package com.stone.core.encrypt;

import java.io.File;

/**
 * 加密一个文件目录下的所有文件;
 * 
 * @author crazyjohn
 * 
 */
public class EncryptFileDirGenerator {
	public static void main(String[] args) {
		try {
			if (args.length < 2) {
				throw new RuntimeException("参数不合法");
			}
			genEncryptFiles(args[0], args[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void genEncryptFiles(String _fromDir, String _toDir) {
		File fromDir = new File(_fromDir);

		if (!fromDir.exists() || !fromDir.isDirectory()) {
			throw new RuntimeException("原始目录[" + _fromDir + "]不存在");
		}
		File toDir = new File(_toDir);
		if (!toDir.exists() || !toDir.isDirectory()) {
			toDir.mkdir();
		}
		File[] files = fromDir.listFiles();
		for (File file : files) {
			String fileName = file.getName();
			File newFile = new File(_toDir, fileName);
			EncryptUtils.encryptFile(file, newFile);
		}
	}
}
