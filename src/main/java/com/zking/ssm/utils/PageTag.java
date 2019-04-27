package com.zking.ssm.utils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.Map;

public class PageTag extends BodyTagSupport {

	private static final long serialVersionUID = -6554574361093255758L;

	private PageBean pageBean;

	public PageTag() {
		super();
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
			out.println(this.toHTML());
			return SKIP_BODY;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String toHTML() {
		if (null == pageBean || !pageBean.isPagination()) {
			return "";
		}
		StringBuffer bf = new StringBuffer();
		// ���ɱ�������
		bf.append("<form id='pageBeanForm' action='" + this.pageBean.getUrl() + "' method='post'>");
		bf.append("<input type='hidden' name='page' value=''>");
		String name = null;
		String[] values = null;
		for (Map.Entry<String, String[]> entry : pageBean.getParameterMap().entrySet()) {
			name = entry.getKey();
			if ("page".equals(name)) {
				continue;// ͨ��js�����޸�
			}
			values = entry.getValue();
			for (String value : values) {
				bf.append("<input type='hidden' name='" + name + "' value='" + value + "'>");
			}
		}
		bf.append("</form>");

		// ���ɷ�ҳ��ť
		bf.append("<div style='text-align: right;font-size: 12px;'>ÿҳ" + this.getPageBean().getRows() + "������"
				+ this.pageBean.getTotal() + "������" + pageBean.getPage() + "ҳ����" + pageBean.getMaxPage()
				+ "ҳ&nbsp;&nbsp;<a href='javascript:gotoPage(1)'>��&nbsp;&nbsp;ҳ</a>&nbsp;&nbsp;<a href='javascript:gotoPage("
				+ pageBean.getPreviousPage() + ")'>��һҳ</a>&nbsp;&nbsp;<a href='javascript:gotoPage("
				+ pageBean.getNextPage() + ")'>��һҳ</a>&nbsp;&nbsp;<a href='javascript:gotoPage(" + pageBean.getMaxPage()
				+ ")'>ĩ&nbsp;&nbsp;ҳ</a>&nbsp;&nbsp;<input id='skipPage' type='text' style='text-align: center;font-size: 12px;width:50px;'>&nbsp;&nbsp;<a href='javascript:skipPage();'>GO</a></div>");

		// ����js
		bf.append("<script>");
		bf.append("function gotoPage(page){");
		bf.append("  document.getElementById('pageBeanForm').page.value = page;");
		bf.append("  document.getElementById('pageBeanForm').submit();");
		bf.append("}");
		bf.append("var maxPage="+pageBean.getMaxPage()+";");
		bf.append("function skipPage(){");
		bf.append("  var page = document.getElementById('skipPage').value;");
		bf.append("  if(!page||isNaN(page)||parseInt(page)<1||parseInt(page)>maxPage){");
		bf.append("    alert('������1~8֮�������');");
		bf.append("    return;");
		bf.append("  }");
		bf.append("  gotoPage(page);");
		bf.append("}");
		bf.append("</script>");
		return bf.toString();
	}
}
