package com.hicon.utils;

import java.security.MessageDigest;
import java.util.Random;

/**
* @ClassName: SecurityUtil 
* @Description: 加密工具，提供对称和非对称两种加密方式
* @author sun_zhen
* @date 2016年4月29日 上午9:11:16 
*
 */
public class SecurityUtil {

	private static String $chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-=_";
	

	public final static String md5(String plainText) {
	    
        // 返回字符串
        String md5Str = null;
        try {
            // 操作字符串
            StringBuffer buf = new StringBuffer();

            MessageDigest md = MessageDigest.getInstance("MD5");

            // 添加要进行计算摘要的信息,使用 plainText 的 byte 数组更新摘要。
            md.update(plainText.getBytes());

            // 计算出摘要,完成哈希计算。
            byte b[] = md.digest();
            int i;
            for (int offset = 0; offset < b.length; offset++) {

                i = b[offset];

                if (i < 0) {
                    i += 256;
                }

                if (i < 16) {
                    buf.append("0");
                }

                // 将整型 十进制 i 转换为16位，用十六进制参数表示的无符号整数值的字符串表示形式。
                buf.append(Integer.toHexString(i));

            }

            // 32位的加密
            md5Str = buf.toString();

            // 16位的加密
            // md5Str = buf.toString().md5Strstring(8,24);

        }  catch (RuntimeException e) {
            throw e;
          } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Str;
    }
	
	/**
	 * 
	* @Title: encrypt 
	* @Description: 
	* @param @param $txt 加密的字符串
	* @param @param $key 解密的秘钥
	* @param @return
	* @return String
	* @throws
	 */
	public static String encrypt(String $txt,String $key) {
		Random r = new Random();
		int $nh = r.nextInt(64);
		char chars_aray[] = $chars.toCharArray();
		char $ch = chars_aray[$nh];
		String $mdKey = md5 ( $key + $ch );
		$mdKey = $mdKey.substring($nh % 8, $nh % 8+$nh % 8 + 7 );
		$txt = Base64.encodeBytes($txt.getBytes());
		$txt = $txt.replace("\n", "");
		$txt = $txt.replace("\r", "");
		char $txt_array[] = $txt.toCharArray();
		char $mdKey_array[] = $mdKey.toCharArray();
		String $tmp = "";
		int $i = 0;
		int $j = 0;
		int $k = 0;
		for($i = 0; $i < $txt.length(); $i ++) {
			$k = $k == $mdKey.length() ? 0 : $k;
			$j = ($nh + $chars.indexOf($txt_array [$i]) + (int)( $mdKey_array [$k ++] )) % 64;
			$tmp = $tmp + chars_aray[$j];
		}
		return $ch + $tmp;
	}

    /**
     * 
    * @Title: decrypt 
    * @Description: 
    * @param @param $txt 解密的字符串
    * @param @param $key 解密的秘钥
    * @param @return
    * @return String
    * @throws
     */
    public static String decrypt(String $txt,String $key) {

		String $ch = $txt.substring(0, 1);
		int $nh = $chars.indexOf($ch );
		String $mdKey = md5( $key + $ch );
		$mdKey = $mdKey.substring($nh % 8,$nh % 8+$nh % 8+7);//$nh % 8,$nh % 8+$nh % 8+7
		$txt = $txt.substring(1);
		char[] $txt2 = $txt.toCharArray();
		char[] $mdKey2 = $mdKey.toCharArray();
		char[] $chars2 = $chars.toCharArray();
		String $tmp = "";
		int $i = 0;
		int $j = 0;
		int $k = 0;
		for($i = 0; $i < $txt.length(); $i ++) {
			$k = $k == $mdKey.length() ? 0 : $k;
			$j = $chars.indexOf($txt2 [$i]) - $nh - (int)( $mdKey2 [$k ++] );
			while ( $j < 0 ){
				$j += 64;
			}
			$tmp += $chars2 [$j];
		}
		return new String(Base64.decode($tmp));
    }


}
