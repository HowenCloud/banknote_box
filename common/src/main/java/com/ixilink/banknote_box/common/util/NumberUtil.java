package com.ixilink.banknote_box.common.util;

import java.math.BigDecimal;
import java.util.Stack;
import java.util.regex.Pattern;


public class NumberUtil {
	
	private static String[] number_zh_upper = new String[]{"零","壹","贰","叁","肆","伍","陆","柒","捌","玖","拾"};//拾是多出位
	private static String[] unit_zh_upper = new String[]{"","拾","佰","仟","萬","亿"};
	private static String[] number_zh_lower = new String[]{"零","一","二","三","四","五","六","七","八","九","十"};//十是多出位
	private static String[] unit_zh_lower = new String[]{"","十","百","千","万","亿"};

	public static Double doubleLastNumber2(Double f) {
		BigDecimal b = new BigDecimal(f);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * double转String，保留2位小数
	 * @param f 数值
	 * @return String
	 */
	public static String doubleLastNumber2Str(Double f) {
		return new java.text.DecimalFormat("#.00").format(f);
	}

	/**
	 * double转String
	 * @param f 数值
	 * @return String
	 */
	public static String doubleLastNumber0Str(Double f) {
		return new java.text.DecimalFormat("#").format(f);
	}

	/**
	 * double转String，保留size位小数
	 * @param f 数值
	 * @return String
	 */
	public static String doubleLastNumberOtherStr(Double f,int size) {
		return String.format("%."+size+"f", f);
	}
	
	
	/**
	  * 判断是否为整数 
	  * @param str 传入的字符串 
	  * @return 是整数返回true,否则返回false 
	*/
	  public static boolean isInteger(String str) {  
	        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
	        return pattern.matcher(str).matches();  
	  }


	/**
	 * 数字转中文（大写）
	 * @param number 数值
	 * @return 中文大写
	 */
	public static String numToZh_Upper(Long number){
		StringBuilder sb = new StringBuilder();

		String str = number.toString();
		Stack<String> _stack = new Stack<String>();
		for(int i = 0;i < str.length();i++){
//		_stack.push(number_zh_upper[(int) (number % 10)] );// 带 拾、佰、仟...  修改为  (int) (number % 10) + getUnitZH(Long.valueOf(i))
		_stack.push(number_zh_upper[ Integer.valueOf(str.charAt(i)+"")] + getUnitZHUpper(i));
		number = number / 10;
		}
		while(!_stack.isEmpty()){
		sb.append(_stack.pop());
		}
		return sb.toString();
		}

	/**
	 * 数字转中文（小写）
	 * @param number 数值
	 * @return 中文小写
	 */
	public static String numToZh_Lower(Long number){
		 if(number<10) {
			 return number_zh_lower[number.intValue()%10];
		 }
		 else if(number<11) {
			 return "十";
		 }
		 else if(number<20) {
			 return "十"+number_zh_lower[number.intValue()%10];
		 }
		StringBuilder sb = new StringBuilder();

		String str = number.toString();
		Stack<String> _stack = new Stack<>();
		for(int i = 0;i < str.length();i++){
		_stack.push(number_zh_lower[(int) (number % 10)] + getUnitZHLower(i));
		number = number / 10;
		}
		while(!_stack.isEmpty()){
		sb.append(_stack.pop());
		}
		return sb.toString();
		}
	  
	  
	  	//大写
		private static String getUnitZHUpper(Integer num){
			return getUnitZH(num,true);
		}
		//小写
		private static String getUnitZHLower(Integer num){
			return getUnitZH(num,false);
		}

		private static String getUnitZH(Integer num,boolean flag){
			if(num >= 5 && num < 8){
				return getUnitZHLower(num - 4);
			}else if(num > 8){
				return getUnitZHLower(num - 8);
			}else if(num == 8){
				return flag?unit_zh_upper[5]:unit_zh_lower[5]; //亿
			}else if(num > 17){
				return null;//暂不支持 亿亿
			}else{
				return flag?unit_zh_upper[num]:unit_zh_lower[num];
			}
		}


}
