package com.lcomputerstudy.testmvc.vo;

import java.sql.Timestamp;

public class Board {
	private int b_idx; 
	private int b_view;
	private int u_idx;
	private String b_title;
	private String b_content;
	private Timestamp b_date;
	private String b_writer; // 작성자는 user테이블의 user_id를 가져올것
	
	public int getB_idx() {
		return b_idx;
	}
	public void setB_idx(int b_idx) {
		this.b_idx = b_idx;
	}
	public int getB_view() {
		return b_view;
	}
	public void setB_view(int b_view) {
		this.b_view = b_view;
	}
	public String getB_title() {
		return b_title;
	}
	public void setB_title(String b_title) {
		this.b_title = b_title;
	}
	public String getB_content() {
		return b_content;
	}
	public void setB_content(String b_content) {
		this.b_content = b_content;
	}
	public Timestamp getB_date() {
		return b_date;
	}
	public void setB_date(Timestamp b_date) {
		this.b_date = b_date;
	}
	public String getB_writer() {
		return b_writer;
	}
	public void setB_writer(String b_writer) { 
		this.b_writer = b_writer;
	}
	public int getU_idx() {
		return u_idx;
	}
	public void setU_idx(int u_idx) {
		this.u_idx = u_idx;
	}
}
