package com.zking.ssm.utils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class RownumTag extends BodyTagSupport {

	private static final long serialVersionUID = -6554574361093255758L;

	private PageBean pageBean;

	private int index;

	public RownumTag() {
		super();
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			int rownum = pageBean.getStartIndex() + 1 + index + 1;
			return SKIP_BODY;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
