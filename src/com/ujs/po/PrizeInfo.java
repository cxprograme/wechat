package com.ujs.po;


/**
 * 红包中奖信息
 * @author ChenXu
 *
 */
public class PrizeInfo {
	private String openId;
	private int isGet;
	private String time;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public int getIsGet() {
		return isGet;
	}
	public void setIsGet(int isGet) {
		this.isGet = isGet;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "PrizeInfo [openId=" + openId + ", isGet=" + isGet + ", time=" + time + "]";
	}
	
}
