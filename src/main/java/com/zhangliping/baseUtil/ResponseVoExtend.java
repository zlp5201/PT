/**
 * 
 */
package com.zhangliping.baseUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangliping
 *
 */
@Setter
@Getter
public class ResponseVoExtend extends ResponseVo {
	/**
     * 扩展信息(展示具体的报错信息，用于研发关注)
     */
	private Object extendMsg;

}
