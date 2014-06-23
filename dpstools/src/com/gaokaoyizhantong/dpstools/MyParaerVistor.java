package com.gaokaoyizhantong.dpstools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.Remark;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.visitors.NodeVisitor;

public class MyParaerVistor extends NodeVisitor {

	Map<String, String> params = new HashMap<String, String>();

	public MyParaerVistor() {
		params.put("width", "100%");
		params.put("border", "0");
		params.put("cellspacing", "1");
		params.put("cellpadding", "0");
		params.put("class", "sjgtable");
		regionSet.add("辽宁");
		regionSet.add("湖南");
		regionSet.add("内蒙古");
		regionSet.add("浙江");
		regionSet.add("安徽");
		regionSet.add("贵州");
		regionSet.add("黑龙江");
		regionSet.add("陕西");
		regionSet.add("广西");
		regionSet.add("湖北");
		regionSet.add("福建");
		regionSet.add("山西");
		regionSet.add("江西");
		regionSet.add("海南");
		regionSet.add("江苏");
		regionSet.add("云南");
		regionSet.add("河南");
		regionSet.add("山东");
		regionSet.add("新疆");
		regionSet.add("青海");
		regionSet.add("吉林");
		regionSet.add("河北");
		regionSet.add("宁夏");
		regionSet.add("北京");
		regionSet.add("天津");
		regionSet.add("广东");
		regionSet.add("上海");
		regionSet.add("四川");
		regionSet.add("重庆");
		regionSet.add("甘肃");
		regionSet.add("香港");
	}

	Set<String> regionSet = new HashSet<String>();

	private YuanXiaoDTO dto;

	public YuanXiaoDTO getDto() {
		return dto;
	}

	public void setDto(YuanXiaoDTO dto) {
		this.dto = dto;
	}

	private Tag parentTag;

	private TableRow trTag;

	private TableColumn tdTag;

	private List<YuanXiaoDTO> listDTO = new ArrayList<YuanXiaoDTO>();

	public void beginParsing() {
		super.beginParsing();
	}

	@Override
	public void finishedParsing() {
		super.finishedParsing();
	}

	@Override
	public boolean shouldRecurseChildren() {
		return super.shouldRecurseChildren();
	}

	@Override
	public boolean shouldRecurseSelf() {
		return super.shouldRecurseSelf();
	}

	@Override
	public void visitRemarkNode(Remark remark) {
		super.visitRemarkNode(remark);
	}

	@Override
	public void visitStringNode(Text string) {
		super.visitStringNode(string);
	}

	@Override
	public void visitEndTag(Tag tag) {
		if (tag instanceof TableRow) {
			org.htmlparser.Node parentNode = tag.getParent();
			if (parentNode.equals(this.parentTag)) {
				listDTO.add(this.dto);
			}
		} else if (tag instanceof TableColumn) {

		}
	}

	@Override
	public void visitTag(Tag tag) {
		if (tag instanceof TableTag) {
			if (params != null) {
				Iterator<Entry<String, String>> iter = this.params.entrySet()
						.iterator();
				boolean isValided = true;
				while (iter.hasNext()) {
					Entry<String, String> me = iter.next();
					String key = me.getKey();
					String value = me.getValue();
					String tagValue = tag.getAttribute(key);
					if (!value.equalsIgnoreCase(tagValue)) {
						isValided = false;
					}
				}
				if (isValided) {
					this.parentTag = tag;
				}
			}
		} else if (tag instanceof TableRow) {
			org.htmlparser.Node parentNode = tag.getParent();
			if (parentNode.equals(this.parentTag)) {
				dto = new YuanXiaoDTO();
				trTag = (TableRow) tag;
			}
		} else if (tag instanceof TableColumn) {
			Node parentNode = tag.getParent();
			// System.out
			// .println("***************************************************************************");
			// // System.out.println(parentNode.toHtml());
			// System.out
			// .println("***************************************************************************");
			if (parentNode.equals(trTag)) {
				tdTag = (TableColumn) tag;
				String className = tdTag.getAttribute("class");
				if ("cf63".equalsIgnoreCase(className)) {
					parserPlan(tdTag);
					return;
				}
				String style = tdTag.getAttribute("style");
				if ("text-align: left".equalsIgnoreCase(style)) {
					parserSchoolInfo(tdTag);
					return;
				}
				String tagText = tdTag.getText();
				if (tagText.equals("td")) {
				}
			}
		}
	}

	private void parserSchoolInfo(TableColumn tdTag2) {
		String html = tdTag2.toHtml();
		System.out.println(html);
		String school_id = StringUtil.subString(html,
				"href=\"university.aspx?id=\"", "\">");
		String school_name = StringUtil.subString(html,
				"href=\"university.aspx?id=\"" + school_id + "\">",
				"</a></strong>");
		String ranking = StringUtil.subString(html, "title='院校综合排名:", "'>");
		Boolean mark_211 = html.indexOf("211院校") == -1 ? false : true;
		Boolean mark_985 = html.indexOf("985院校") == -1 ? false : true;
		this.dto.setSchool_id(Integer.valueOf(school_id));
		this.dto.setSchool_name(school_name);
		this.dto.setRanking(Integer.valueOf(ranking));
		this.dto.setMark_211(mark_211);
		this.dto.setMark_985(mark_985);
	}

	private void parserPlan(TableColumn tdTag2) {
		String planNum = tdTag2.toHtml();
		String planStr = StringUtil.subString(planNum,
				"<td colspan=\"3\"><strong>计划招收人数", "<strong>");
		this.dto.setPlan_enrolled_num(Integer.valueOf(StringUtil.subString(
				planStr, "：", "人")));
		this.dto.setEnrolled_num(Integer.valueOf(StringUtil.subString(planStr,
				"实际报考人数：", "人")));
	}

	public static void main(String[] args) {
		String str = "<td>综合</td>";
		System.out.println(str.matches("<td>.*</td>"));
	}
}
