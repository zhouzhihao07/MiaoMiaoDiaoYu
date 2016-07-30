package com.zhouzhihao.sxh1.utils;

import java.math.BigDecimal;

public class Common {


	public static  final byte[] keyBytes = {0x11, 0x22, 0x4F, 0x58, (byte)0x88, 0x10, 0x40, 0x38
        , 0x28, 0x25, 0x79, 0x51, (byte)0xCB, (byte)0xDD, 0x55, 0x66
        , 0x77, 0x29, 0x74, (byte)0x98, 0x30, 0x40, 0x36, (byte)0xE2}; 
	//double类型的小数是不精确的浮数点
	  public static Double multiply(double v1, double v2)
	  {	
	      BigDecimal b1 = new BigDecimal(Double.toString(v1));
	      BigDecimal b2 = new BigDecimal(Double.toString(v2));
	      return b1.multiply(b2).doubleValue();
	  }
	  
	    /**    
	      *   提供精确的加法运算。    
	      *   @param   v1   被加数    
	      *   @param   v2   加数    
	      *   @return   两个参数的和    
	      */     
	  
	    public   static   Double   add(double v1,double v2){   
	    	
	            BigDecimal   b1   =   new   BigDecimal(Double.toString(v1));     
	            BigDecimal   b2   =   new   BigDecimal(Double.toString(v2));    
	 
	            return   b1.add(b2).doubleValue();     	           
	    }
	    
	    /** 
	     * 提供精确的小数位四舍五入处理。 
	     *  
	     * @param v 
	     *            需要四舍五入的数字 
	     * @param scale 
	     *            小数点后保留几位 
	     * @return 四舍五入后的结果 
	     */  
	  
	    public static double round(double v, int scale) {  
	        if (scale < 0) {  
	            throw new IllegalArgumentException(  
	                    "The   scale   must   be   a   positive   integer   or   zero");  
	        }  
	        BigDecimal b = new BigDecimal(Double.toString(v));  
	        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue(); 
	        
	        
	    }
}
