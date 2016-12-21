/**
 * 
 */
package com.zhangliping.sqladmin.bean;

/**
 * 实体类
 * @author zhangliping
 *
 */
public class SqlAdminResult {
	/**
	 * 订单号
	 */
	private String order_id;
	/**
	 * 下单时间
	 */
	private String add_time;
	
	/**
	 * 会员编号
	 */
	private String cust_id;
	/**
	 * 订单状态
	 */
	private String order_status_name;
	/**
	 * 最后更新时间
	 */
	private String update_time;
	
	/**
	 * 转化订单号
	 */
	private String extend_order_id;
	/**
	 * 取消人员
	 */
	private String add_uid;
	
	/**
	 * 订单客服
	 */
	private String staff_name;
	/**
	 * 预订城市
	 */
	private String book_city;
	
	/**
	 * 产品编号
	 */
	private String product_id;
	
	/**
	 * 一级品类
	 */
	private String product_class_name;
	
	/**
	 * 二级品类
	 */
	private String product_child_class_name;
	/**
	 * 目的地大类
	 */
	private String dest_type_name;
	
	/**
	 * 目的地分组
	 */
	private String dest_group_name;
	
	/**
	 * 目的地
	 */
	private String dest_name;
	
	/**
	 * 下单金额
	 */
	private String total_price;

	/**
	 * @return the order_id
	 */
	public String getOrder_id() {
		return order_id;
	}

	/**
	 * @param order_id the order_id to set
	 */
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	/**
	 * @return the add_time
	 */
	public String getAdd_time() {
		return add_time;
	}

	/**
	 * @param add_time the add_time to set
	 */
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	/**
	 * @return the cust_id
	 */
	public String getCust_id() {
		return cust_id;
	}

	/**
	 * @param cust_id the cust_id to set
	 */
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	/**
	 * @return the order_status_name
	 */
	public String getOrder_status_name() {
		return order_status_name;
	}

	/**
	 * @param order_status_name the order_status_name to set
	 */
	public void setOrder_status_name(String order_status_name) {
		this.order_status_name = order_status_name;
	}

	/**
	 * @return the update_time
	 */
	public String getUpdate_time() {
		return update_time;
	}

	/**
	 * @param update_time the update_time to set
	 */
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	/**
	 * @return the extend_order_id
	 */
	public String getExtend_order_id() {
		return extend_order_id;
	}

	/**
	 * @param extend_order_id the extend_order_id to set
	 */
	public void setExtend_order_id(String extend_order_id) {
		this.extend_order_id = extend_order_id;
	}

	/**
	 * @return the add_uid
	 */
	public String getAdd_uid() {
		return add_uid;
	}

	/**
	 * @param add_uid the add_uid to set
	 */
	public void setAdd_uid(String add_uid) {
		this.add_uid = add_uid;
	}

	/**
	 * @return the staff_name
	 */
	public String getStaff_name() {
		return staff_name;
	}

	/**
	 * @param staff_name the staff_name to set
	 */
	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	/**
	 * @return the book_city
	 */
	public String getBook_city() {
		return book_city;
	}

	/**
	 * @param book_city the book_city to set
	 */
	public void setBook_city(String book_city) {
		this.book_city = book_city;
	}

	/**
	 * @return the product_id
	 */
	public String getProduct_id() {
		return product_id;
	}

	/**
	 * @param product_id the product_id to set
	 */
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	/**
	 * @return the product_class_name
	 */
	public String getProduct_class_name() {
		return product_class_name;
	}

	/**
	 * @param product_class_name the product_class_name to set
	 */
	public void setProduct_class_name(String product_class_name) {
		this.product_class_name = product_class_name;
	}

	/**
	 * @return the product_child_class_name
	 */
	public String getProduct_child_class_name() {
		return product_child_class_name;
	}

	/**
	 * @param product_child_class_name the product_child_class_name to set
	 */
	public void setProduct_child_class_name(String product_child_class_name) {
		this.product_child_class_name = product_child_class_name;
	}

	/**
	 * @return the dest_type_name
	 */
	public String getDest_type_name() {
		return dest_type_name;
	}

	/**
	 * @param dest_type_name the dest_type_name to set
	 */
	public void setDest_type_name(String dest_type_name) {
		this.dest_type_name = dest_type_name;
	}

	/**
	 * @return the dest_group_name
	 */
	public String getDest_group_name() {
		return dest_group_name;
	}

	/**
	 * @param dest_group_name the dest_group_name to set
	 */
	public void setDest_group_name(String dest_group_name) {
		this.dest_group_name = dest_group_name;
	}

	/**
	 * @return the dest_name
	 */
	public String getDest_name() {
		return dest_name;
	}

	/**
	 * @param dest_name the dest_name to set
	 */
	public void setDest_name(String dest_name) {
		this.dest_name = dest_name;
	}

	/**
	 * @return the total_price
	 */
	public String getTotal_price() {
		return total_price;
	}

	/**
	 * @param total_price the total_price to set
	 */
	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}
}
