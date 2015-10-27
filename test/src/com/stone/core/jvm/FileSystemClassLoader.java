package com.stone.core.jvm;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class FileSystemClassLoader extends ClassLoader {
	private String rootDir;

	public FileSystemClassLoader(String rootDir) {
		this.rootDir = rootDir;
		System.out.println("RootDir: " + rootDir);
	}

	public FileSystemClassLoader() {
		this(System.getProperty("user.dir"));
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classData = getClassData(name);
		if (classData == null) {
			throw new ClassNotFoundException();
		}
		return defineClass(name, classData, 0, classData.length);
	}

	private byte[] getClassData(String name) {
		String path = classNameToPath(name);
		try {
			InputStream in = new FileInputStream(path);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int readBytes = 0;
			while ((readBytes = in.read(buffer)) != -1) {
				out.write(buffer, 0, readBytes);
			}
			in.close();
			return out.toByteArray();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String classNameToPath(String name) {
		return rootDir + name.replace(".", "\\") + ".class";
	}
}
