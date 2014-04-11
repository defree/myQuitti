package com.utu.myquitti.pojos;

import java.io.Serializable;


/**
 * User can give tags to receipts as an extra info
 * @author saminurmi
 *
 */
public class Tag implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String tag;

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
