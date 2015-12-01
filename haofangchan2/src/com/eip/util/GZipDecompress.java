package com.eip.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.eip.base64.Base64;

public class GZipDecompress {

	/**
	 * 
	 * 浣跨敤gzip杩涜鍘嬬缉
	 */
	
	public static byte[] gzip(String primStr) {
		if (primStr == null || primStr.length() == 0) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(primStr.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return out.toByteArray();
	}

	/**
	 * 
	 * <p>
	 * Description:浣跨敤gzip杩涜瑙ｅ帇缂�
	 * </p>
	 * 
	 * @param compressedStr
	 * @return
	 */
	public static String gunzip(byte[] bs) {

		if (bs == null) {
			System.out.println("bs==0");
			return null;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;
		byte[] compressed = null;
		String decompressed = null;
		try {
			compressed = bs;
			in = new ByteArrayInputStream(compressed);
			ginzip = new GZIPInputStream(in);

			byte[] buffer = new byte[1024];
			int offset = -1;

			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString("UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ginzip != null) {
				try {
					ginzip.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return decompressed;
	}

	/**
	 * 瀛楃涓插帇缂╄В鍘�
	 * 
	 * @param str
	 *            base64缂栫爜鐨勫瓧绗︿覆
	 * @return
	 * @throws IOException
	 */
	public static String zipDecompressBase64Decoding(String str)
			throws IOException {
		if (str == null || str.length() == 0) {
			return null;
		}

		byte[] bs = Base64.decodeBase64(String.format("H4sIAAAA%s", str));
		return gunzip(bs);
	}

	/**
	 * 浣跨敤zip杩涜鍘嬬缉
	 * 
	 * @param str
	 *            鍘嬬缉鍓嶇殑鏂囨湰
	 * @return 杩斿洖鍘嬬缉鍚庣殑鏂囨湰
	 */
	public static final String zip(String str) {
		if (str == null)
			return null;
		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;
		String compressedStr = null;
		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(str.getBytes());
			zout.closeEntry();
			compressed = out.toByteArray();
			compressedStr = new sun.misc.BASE64Encoder()
					.encodeBuffer(compressed);
		} catch (IOException e) {
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return compressedStr;
	}

	/**
	 * 浣跨敤zip杩涜瑙ｅ帇缂�
	 * 
	 * @param compressed
	 *            鍘嬬缉鍚庣殑鏂囨湰
	 * @return 瑙ｅ帇鍚庣殑瀛楃涓�
	 */
	public static final String unzip(String compressedStr) {
		if (compressedStr == null) {
			return null;
		}

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed = null;
		try {
			byte[] compressed = new sun.misc.BASE64Decoder()
					.decodeBuffer(compressedStr);
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return decompressed;
	}
	
	/**
	 * 瀛楃涓插帇缂╄繑鍥濨ase64缂栫爜鍚庣殑瀛楃涓�
	 * @param str
	 * @return
	 * @throws IOException
	 */
	public static String zipCompressBase64Encoding(String str) throws IOException{
		
		byte[] bs = gzip(str);
		String tmp = Base64.encodeBase64String(bs);
		return tmp.substring(8); 
	}
}