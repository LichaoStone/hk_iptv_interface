package com.hicon.frame;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量接口
 * @作者 栗超
 * @说明
 */
public abstract class Constants {

	/**默认排序:10000**/
	public static final String ORDER_NUMBER_DEFAULT="10000";

	/**
	 * 系统状态码
	 * @作者 栗超
	 * @说明
	 */
    public static  class CODE{
    	/**成功**/
    	public static final Integer  SUCCESS=200;
    	/**失败**/
    	public static final Integer  FAIL=500;
    }
    
    
    /**
     * 业务逻辑码
     * @作者 栗超
     * @说明
     */
    public static class RET{
		/**当前用户未绑定IPTV账号，请重新绑定**/
		public static final Integer  NOT_BOUND_IPTV=101;
    	/**成功**/
    	public static final Integer  SUCCESS=200;
    	/**失败**/
    	public static final Integer  FAIL=500;
    	/**未知异常**/
    	public static final Integer UNKNOWN_FAIL = -1 ;
    	/**请求非法**/
    	public static final Integer  ILLEGAL=201;
    	/**请求过期失效**/
    	public static final Integer  LOSE_EFFICACY=202;
    	/**缺少参数:_timestamp或_sign**/
    	public static final Integer  MISSING_PARAMETER=203;
    	
//    	/**大屏模块名称不正确**/
//    	public static final Integer  MODULE_ERROR=204;
//    	/**请求ACTION行为不存在:simple,detail,localVideoCall和notice**/
//    	public static final Integer  ACTION_ERROR=205;
//    	/**视频通话已经被锁定**/
//    	public static final Integer  VIDEO_CALL_LOCK=206;
//    	/**发送消息到大屏失败**/
//    	public static final Integer  SEND_MESSAGE_ERROR=207;
//    	/**不允许自动更新数据**/
//    	public static final Integer  NOT_ALLOW_AUTO_REFRESH_SCREEN=208;
//    	/**占线...**/
//    	public static final Integer  CALL_BUSSY=3001;
//    	/**视频通话时长不够**/
//    	public static final Integer  NO_MORE_CALLTIME=3002;
    	/**登陆密码错误**/
    	public static final Integer ERROR_PASSWORD=401;
    	/**账号错误**/
    	public static final Integer NO_USER=402;
    	/**session过期,长时间未操作下线**/
    	public static final Integer SESSION_LOSE_EFFICACY=403;
    	/**其他用户登陆，强制下线**/
    	public static final Integer MUST_OFFLINE=405;
    	/**用户未登录**/
    	public static final Integer ERROR_ROLE=406;
//    	/**用户禁用**/
//    	public static final Integer USER_LOCKED=407;
    	/**令牌无效**/
    	public static final Integer TOKEN_INVALID=501;
    	/**无访问权限**/
    	public static final Integer NOT_PERMISSION=502;
    	/**已绑定该IPTV账号**/
    	public static final Integer IS_BIND_YES=601;
    	/**已绑定其它IPTV账号**/
    	public static final Integer IS_BIND_OTHER_ACCOUNT=602;
    	/**IPTV账号已被其它用户绑定**/
    	public static final Integer OTHER_ACCOUNT_IS_BIND=603;
        /**已绑定机顶盒运营商侧不存在**/
        public static final Integer GOOD_NOT_EXITS=701;
		/**订单兑换，珍珠不足**/
		public static final Integer PEARL_INSUFFICIENT=801;
		/**	订单兑换，商品已售罄**/
		public static final Integer GOODS_SOLD_OUT=802;
		/**	订单兑换，每人限量已用完**/
		public static final Integer GOODS_LIMIT=803;
		/**	订单兑换，用户等级不够**/
		public static final Integer NOT_ENOUGH_GRADE=804;
		/**	订单兑换，收件人地址错误**/
		public static final Integer ORDERS_ADDRESS=805;
		/**	订单兑换，优惠期已过期**/
		public static final Integer GOODS_COUPON_EXPIRE=806;
		/**	订单兑换，未找到商品信息**/
		public static final Integer GOODS_NONE=807;
		/**	订单兑换，商品已下架**/
		public static final Integer GOODS_LOWER_SHELF=808;
		/**	订单兑换，商品已过期**/
		public static final Integer GOODS_BE_OVERDUE=809;
		/**	订单兑换，商品已删除**/
		public static final Integer GOODS_DELETE=810;
		/**	订单兑换，用户未绑定IPTV账号*/
		public static final Integer ACCOUNT_IS_NOT_BIND=811;
		/**	订单兑换，用户所属运营商信息为空*/
		public static final Integer ACCOUNT_IS_NOT_OPERATOR_BELONG=812;
		/**	订单兑换，用户和商品所属运营商信息不一致*/
		public static final Integer OPERATOR_BELONG_NOT_FIT=813;
		/**	订单兑换，请勿频繁提交订单*/
		public static final Integer GOOD_EXCHANGE_TIME=814;
		/**	订单兑换，提交订单失败*/
		public static final Integer GOOD_EXCHANGE_ERROR=815;
		/**	订单兑换，该商品不存在优惠券-券码详情数据*/
		public static final Integer GOOD_COUPON_CODE_ERROR=816;
		/**	订单兑换，扣减积分失败*/
		public static final Integer DEDUCTION_INTEGRAL_CODE_ERRROR=817;
		
		/**奖品已被领取**/
		public static final Integer LOTTERY_PRIZE_IS_RECEIVE=901;
		/**收件人地址错误**/
		public static final Integer LOTTERY_RECEIVE_NOT_EXIST=902;
		/**活动信息有误（无此活动）**/
		public static final Integer LOTTERY_ACTIVITY_NOT_EXIST=903;
		/**奖品信息有误（无此奖品）**/
		public static final Integer LOTTERY_PRIZE_NOT_EXIST=904;
    }


    /**
     * 访问权限验证是否开启	
     * @作者 栗超
     * @说明
     */
	public  static  class ACCESS_PERMISSIONS{
		/**开启**/
		public static final String  ON="ON";
		/**关闭**/
		public static final String  OFF="OFF";
	}
    
	
	/**
     * 时间类型
     * @作者 栗超
     * @说明
     */
	public  static  class DATE_TYPE{
		/**昨天**/
		public static final String  YESTERDAY="0";
		/**最近七天**/
		public static final String  THE_LAST_SEVEN_DAYS="1";
		/**最近三十天**/
		public static final String  THE_LAST_THIRTY_DAYS="2";
		/**本月**/
		public static final String  CURRENT_MONTH="3";
		/**今天**/
		public static final String  TODAY="4";
	}
	
	
	/**
	 * 数据字典
	 * @author lichao
	 *
	 */
	public  static  class  DICTIONARIES{
		/**
		 * 样例
		 */
		public static final String DEMO="demo";
	}
        
    public static final Map<Integer,Object> CODE_INFO = new HashMap<Integer,Object>();
    public static final Map<Integer,Object> RET_INFO = new HashMap<Integer,Object>();
    public static final Map<String,Integer> USER_CENTER_CODE = new HashMap<String,Integer>();
    
    static{
    	//系统状态码信息
    	CODE_INFO.put(CODE.SUCCESS,"成功");
    	CODE_INFO.put(CODE.FAIL,"失败");

    	//业务逻辑状态码信息
    	RET_INFO.put(RET.SUCCESS,"成功");
    	RET_INFO.put(RET.FAIL,"失败");
    	
    	RET_INFO.put(RET.ILLEGAL,"请求不合法");
    	RET_INFO.put(RET.LOSE_EFFICACY,"请求过期失效");
    	RET_INFO.put(RET.MISSING_PARAMETER,"缺少参数");
//    	RET_INFO.put(RET.MODULE_ERROR,"模块名称错误");
//    	RET_INFO.put(RET.VIDEO_CALL_LOCK,"视频通话已经被锁定");
//    	RET_INFO.put(RET.SEND_MESSAGE_ERROR,"发送消息到大屏失败,请确定大屏是否正常打开");
//    	RET_INFO.put(RET.CALL_BUSSY,"正在占线...");
//    	RET_INFO.put(RET.NO_MORE_CALLTIME,"剩余通话时长不足");
//    	RET_INFO.put(RET.NOT_ALLOW_AUTO_REFRESH_SCREEN,"大屏已停止自动更新");
//    	RET_INFO.put(RET.ACTION_ERROR,"请求ACTION行为不存在");
//    	
    	RET_INFO.put(RET.ERROR_PASSWORD,"密码错误");
    	RET_INFO.put(RET.NO_USER,"该用户不存在");
    	RET_INFO.put(RET.SESSION_LOSE_EFFICACY,"登陆过期");
    	RET_INFO.put(RET.MUST_OFFLINE,"强制下线");
    	RET_INFO.put(RET.ERROR_ROLE,"用户未登录");
    	//RET_INFO.put(RET.USER_LOCKED,"用户被禁用");
		RET_INFO.put(RET.PEARL_INSUFFICIENT,"订单兑换，珍珠不足");
		RET_INFO.put(RET.GOODS_SOLD_OUT,"订单兑换，商品已售罄");
		RET_INFO.put(RET.GOODS_LIMIT,"订单兑换，每人限量已用完");
		RET_INFO.put(RET.NOT_ENOUGH_GRADE,"订单兑换，用户等级不够");
		RET_INFO.put(RET.ORDERS_ADDRESS,"订单兑换，收件人地址错误");
		RET_INFO.put(RET.GOODS_COUPON_EXPIRE,"订单兑换，优惠券已过期");
		RET_INFO.put(RET.GOODS_NONE,"订单兑换，未找到商品信息");
		RET_INFO.put(RET.GOODS_LOWER_SHELF,"订单兑换，商品已下架");
		RET_INFO.put(RET.GOODS_BE_OVERDUE,"订单兑换，商品已过期");
		RET_INFO.put(RET.GOODS_DELETE,"订单兑换，商品已删除");
		RET_INFO.put(RET.ACCOUNT_IS_NOT_BIND,"订单兑换，用户未绑定IPTV账号");
		RET_INFO.put(RET.ACCOUNT_IS_NOT_OPERATOR_BELONG,"订单兑换，用户所属运营商信息为空");
		RET_INFO.put(RET.OPERATOR_BELONG_NOT_FIT,"订单兑换，用户和商品所属运营商信息不一致");
		RET_INFO.put(RET.GOOD_EXCHANGE_TIME,"订单兑换，请勿频繁提交订单,30秒后请重试");
		RET_INFO.put(RET.GOOD_EXCHANGE_ERROR,"订单兑换，提交订单失败");
		RET_INFO.put(RET.GOOD_COUPON_CODE_ERROR,"订单兑换，该商品不存在优惠券-券码详情数据");
		RET_INFO.put(RET.DEDUCTION_INTEGRAL_CODE_ERRROR,"订单兑换，扣减积分失败");


		RET_INFO.put(RET.UNKNOWN_FAIL,"未知错误");
		RET_INFO.put(RET.TOKEN_INVALID,"令牌无效");
		RET_INFO.put(RET.NOT_PERMISSION,"无访问权限");
		
		RET_INFO.put(RET.IS_BIND_YES,"已绑定该IPTV账号");
		RET_INFO.put(RET.IS_BIND_OTHER_ACCOUNT,"已绑定其它IPTV账号");
		RET_INFO.put(RET.OTHER_ACCOUNT_IS_BIND,"IPTV账号已被其它用户绑定");
        RET_INFO.put(RET.GOOD_NOT_EXITS,"已绑定机顶盒运营商侧不存在");

        RET_INFO.put(RET.LOTTERY_PRIZE_IS_RECEIVE,"奖品已被领取");
        RET_INFO.put(RET.LOTTERY_RECEIVE_NOT_EXIST,"收件人地址错误");
        RET_INFO.put(RET.LOTTERY_ACTIVITY_NOT_EXIST,"活动信息有误（无此活动）");
        RET_INFO.put(RET.LOTTERY_PRIZE_NOT_EXIST,"奖品信息有误（无此奖品）");
		RET_INFO.put(RET.NOT_BOUND_IPTV,"当前用户未绑定IPTV账号，请重新绑定");


		USER_CENTER_CODE.put("-1", -1);
		USER_CENTER_CODE.put("200", 200);
		USER_CENTER_CODE.put("400", 203);
		USER_CENTER_CODE.put("401", 501);
		USER_CENTER_CODE.put("403", 502);
    }


	/**
	 * 订单状态：0待发货，1已发货、2已完成、3已关闭
	 */
	public static class ORDER_STATUS{
		/**0待发货**/
		public static final Byte  STATUS_ZREO=0;
		/**1已发货**/
		public static final Byte  STATUS_ONE=1;
		/**2已完成**/
		public static final Byte  STATUS_TWO=2;
		/**3已关闭**/
		public static final Byte  STATUS_THREE=3;
	}

	/**
	 * 用户来源：1移动，2联通、3电信、4其它
	 */
	public static class OPERATOR_BELONG{
		/**1移动**/
		public static final Byte  MOBILE=1;
		/**2联通**/
		public static final Byte  UNICOM=2;
		/**3电信**/
		public static final Byte  TELECOM=3;
		/**4其它**/
		public static final Byte  OTHER=4;
	}

	public static class ORDER{
		/**1移动**/
		public static final byte  ORDER_COME_MOBILE=1;
		/**2联通**/
		public static final byte  ORDER_COME_UNICOM=2;
		/**3电信**/
		public static final byte  ORDER_COME_TELECOM=3;
		/**4其它**/
		public static final byte  ORDER_COME_OTHER=4;
		/**订单号首位**/
		public static final String  FIRST_ORDER_NO="8";
	}

	public static class CUSTOMER{
		/**1移动**/
		public static final byte  OPERATOR_BELONG_MOBILE=2;
		/**2联通**/
		public static final byte  OPERATOR_BELONG_UNICOM=4;
		/**3电信**/
		public static final byte  OPERATOR_BELONG_TELECOM=8;
		/**4其它**/
		public static final byte  OPERATOR_BELONG_OTHER=16;
	}

	public static class GOODS{
		/**大屏图片**/
		public static final byte GOODS_IMAGE_TYPE_BIG = 0 ;
		/**小屏图片**/
		public static final byte  GOODS_IMAGE_TYPE_LITTLE = 1 ;
		/**商品类型 实物**/
		public static final byte GOODS_TYPE_OBJECT = 1 ;
		/**商品类型 优惠券**/
		public static final byte GOODS_TYPE_COUPON =2 ;
		/**配送方式：0自提**/
		public static final byte GOODS_SELF_MENTION =0 ;
		/**配送方式：1邮寄**/
		public static final byte GOODS_MAIL =1 ;
		/**	订单兑换，商品上架**/
		public static final byte STATUS_UPPER_SHELF=0;
		/**	订单兑换，商品已下架**/
		public static final byte STATUS_LOWER_SHELF=1;
		/**	订单兑换，商品已过期**/
		public static final byte STATUS_BE_OVERDUE=2;
		/**	订单兑换，商品已删除**/
		public static final byte STATUS_DELETE=3;
		/**	优惠券展示样式：1只有券码**/
		public static final byte STYLE_ONE=1;
		/**	优惠券展示样式2券码+条形码，**/
		public static final byte STYLE_TWO=2;
		/**	优惠券展示样式：3券码+二维码**/
		public static final byte STYLE_THREE=3;
		/**	兑换等级 无限制**/
		public static final byte GRADE_ZERO=0;
	}
	public static class COUPON_DETAIL{
		/**	状态：0已兑换**/
		public static final byte STATUS_CONVERTIBILITY=0;
		/**	1未兑换**/
		public static final byte STATUS_UNCONVERTED=1;
		/**	2已过期**/
		public static final byte STATUS_EXPIRED=2;
	}

	public static class CCS_USER_LEVEL{
		/**	状态：0已兑换**/
		public static final byte STATUS_CONVERTIBILITY=0;
		/**	1未兑换**/
		public static final byte STATUS_UNCONVERTED=1;
		/**	2已过期**/
		public static final byte STATUS_EXPIRED=2;
	}

	public static class INTEGRAL_DETAIL{
		/**	兑换商品**/
		public static final byte TYPE_EXCHANGE=5;
		/**	类型（1:收入）**/
		public static final byte CLASSIFY_INCOME=1;
		/**	类型（2:支出）**/
		public static final byte CLASSIFY_PAY=2;
	}
	//公钥
	public static String CERTIFICATE = 
			"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDG5J4kog+K6raqBbbA5YXDcKF+\n" + 
			"FimteAfcMcTg/f9Js3QxWqVC1IKQu1wYXocHerT/UzJPHR7zEgukIMItSL+Efn8u\n" + 
			"/PyweBWA5CxdXEboNXlL//AsBBd4Jd77jWII2+6mztJ/6ZBdiGK5CxB1lR1x0fbw\n" + 
			"H6S/7oB36jkGP14orQIDAQAB\n" ;
	public static String REDIS_LOGIN_TOKEN_KEY= "integrate:customer:login:" ;
}
