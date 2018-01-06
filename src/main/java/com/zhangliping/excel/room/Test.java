/**
 * 
 */
package com.zhangliping.excel.room;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * @author zhangliping
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<DataInfo> users = new ArrayList<DataInfo>();
		users.add(new DataInfo("chang", "24"));
		users.add(new DataInfo("chen", "26"));
		users.add(new DataInfo("sun", "24"));
		
		Predicate<DataInfo> predicate1 = new Predicate<DataInfo>() {
            public boolean apply(DataInfo user) {
                if(user.getPhone().equals("25")){
                    return true;
                }
                return false;
            }
        };
		
        
        
        List<DataInfo> filteredUsers1 = Lists.newArrayList(Iterables.filter(users, predicate1));
		
        
			System.out.println(predicate1.apply(new DataInfo("chen", "26")));
	}

}
