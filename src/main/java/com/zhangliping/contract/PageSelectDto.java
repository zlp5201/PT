/**
 * 
 */
package com.zhangliping.contract;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zhangliping
 *
 */
@Getter
@Setter
@ToString
public class PageSelectDto {
	
    private String key;
    
    private String value;
    
    private int maxLength;
    
}
