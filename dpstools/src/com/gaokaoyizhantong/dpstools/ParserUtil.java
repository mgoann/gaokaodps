package com.gaokaoyizhantong.dpstools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.ParserException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParserUtil {
	static Set<String> regionSet = new HashSet<String>();
	static Set<String> batchSet = new HashSet<String>();
	static Set<String> unkowneSet = new HashSet<String>();
	static Set<String> schoolTypeSet = new HashSet<String>();
	static {
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

		batchSet.add("本科提前批");
		batchSet.add("本科第一批");
		batchSet.add("本科一批B");
		batchSet.add("本科第二批");
		batchSet.add("本科二批B");
		batchSet.add("本科第三批");
		batchSet.add("高职高专批");
		batchSet.add("高职高专3F批");
		batchSet.add("香港");

		schoolTypeSet.add("工科");
		schoolTypeSet.add("政法");
		schoolTypeSet.add("民办");
		schoolTypeSet.add("医药类");
		schoolTypeSet.add("师范类");
		schoolTypeSet.add("民族");
		schoolTypeSet.add("综合");
		schoolTypeSet.add("财经");
		schoolTypeSet.add("体育");
		schoolTypeSet.add("师范");
		schoolTypeSet.add("军事类");
		schoolTypeSet.add("理工类");
		schoolTypeSet.add("医药");
		schoolTypeSet.add("林业");
		schoolTypeSet.add("军事");
		schoolTypeSet.add("西藏");
		schoolTypeSet.add("艺术");
		schoolTypeSet.add("财经");
		schoolTypeSet.add("农业");
		schoolTypeSet.add("综合类");
		schoolTypeSet.add("语言");
	}

	public static void main(String[] args) throws IOException, ParserException {

		File root = new File("E:\\qipfpersonal\\gaokaoDPS\\dpstools\\data\\");
		File[] files = root.listFiles();
		List<YuanXiaoDTO> dtoListAll = new ArrayList<YuanXiaoDTO>();
		for (File file : files) {
			System.out.println();
			File[] yearFiles = file.listFiles();
			for (File yearFile : yearFiles) {
				File[] htmlFiles = yearFile.listFiles();
				for (File htmlFile : htmlFiles) {
					List<YuanXiaoDTO> dtoList = parserhtml(htmlFile,
							Integer.valueOf(yearFile.getName()), file.getName());
					dtoListAll.addAll(dtoList);
				}
			}
		}
		System.out.println(unkowneSet);
		for (YuanXiaoDTO dto : dtoListAll) {
			System.out.println(dto);
		}
	}

	private static List<YuanXiaoDTO> parserhtml(File htmlFile, Integer year,
			String student_class) throws IOException {
		String htmlContent = FileUtils.readFileToString(htmlFile, "GB2312");
		Document doc = Jsoup.parse(htmlContent);
		Elements eles = doc.getElementsByTag("table");
		Iterator<Element> iter = eles.iterator();
		Element tableEle = null;
		List<YuanXiaoDTO> dtoList = new ArrayList<YuanXiaoDTO>();
		while (iter.hasNext()) {
			tableEle = iter.next();
			String className = tableEle.attr("class");
			String width = tableEle.attr("width");
			String border = tableEle.attr("border");
			String cellspacing = tableEle.attr("cellspacing");
			String cellpadding = tableEle.attr("cellpadding");
			if ("sjgtable".equals(className) && "100%".equals(width)
					&& "0".equals(border) && "1".equals(cellspacing)
					&& "0".equals(cellpadding)) {
				break;
			}
		}
		eles = tableEle.getElementsByTag("tr");
		iter = eles.iterator();
		iter.next();
		YuanXiaoDTO dto = null;
		while (iter.hasNext()) {
			dto = new YuanXiaoDTO();
			Element trEle = iter.next();
			if (!trEle.parent().parent().equals(tableEle))
				continue;
			eles = trEle.getElementsByTag("td");
			Iterator<Element> _iter = eles.iterator();
			while (_iter.hasNext()) {
				Element tdEle = _iter.next();
				if (!tdEle.parent().equals(trEle))
					continue;
				String className = tdEle.attr("class");
				String style = tdEle.attr("style");
				String onfocus = tdEle.attr("onfocus");
				String tdContent = tdEle.text();
				String tdHtml = tdEle.toString();
				// 招生计划
				if ("cf63".equals(className)
						&& "text-align: left".equals(style) && onfocus != null
						&& onfocus.startsWith("MM_showHideLayers")) {
					parserEnrolledInfo(dto, tdEle, tdContent, tdHtml);
				} else if (className.equals("") && onfocus.equals("")
						&& "text-align: left".equals(style)
						&& tdHtml.indexOf("university.aspx") != -1) {
					// 院校信息
					parserSchoolInfo(dto, tdEle, tdContent, tdHtml);
				} else if (regionSet.contains(tdContent)) {
					// 地区
					parserRegionName(dto, tdEle, tdContent, tdHtml);
				} else if (batchSet.contains(tdContent)) {
					// 批次
					dto.setBatch_name(tdContent);
				} else if (className.equals("") && style.equals("")
						&& onfocus.startsWith("MM_showHideLayers")) {
					// 分数
					parserScoreInfo(dto, tdEle, tdContent, tdHtml);
				} else if (tdContent.matches("\\d+人")) {
					// 实招人数
					dto.setEnrolled_num(Integer.valueOf(StringUtil.subString(
							tdContent, null, "人")));
				} else if (schoolTypeSet.contains(tdContent)) {
					dto.setSchool_type(tdContent);
				} else if (tdHtml.matches("<td>\\d+</td>")) {
					// 平均分
					dto.setAverage_score(Double.valueOf(tdContent));
				} else if (tdContent.equals("") || tdContent.equals("-")
						|| tdContent.equals("专业查看")) {
				} else {
				}
			}
			dto.setYear(year);
			dto.setStudent_class(student_class);
			if (dto.getSchool_id() == 403)
				dto.setRegion_name("西藏");
			dtoList.add(dto);
		}
		return dtoList;

	}

	private static void parserScoreInfo(YuanXiaoDTO dto, Element tdEle,
			String tdContent, String tdHtml) {
		String tidang = StringUtil.subString(tdContent, "提档分数线：", "分");
		String avg = StringUtil.subString(tdContent, "平均分：", "分");
		String max = StringUtil.subString(tdContent, "最高分：", "分");
		dto.setAverage_score(Double.valueOf(avg));
		dto.setTidang_score(Double.valueOf(tidang));
		dto.setMax_score(Double.valueOf(max));
	}

	private static void parserRegionName(YuanXiaoDTO dto, Element tdEle,
			String tdContent, String tdHtml) {
		dto.setRegion_name(tdContent);
	}

	private static void parserSchoolInfo(YuanXiaoDTO dto, Element tdEle,
			String tdContent, String tdHtml) {
		String[] rankingArr = tdContent.split(":");
		String school_name = tdEle.getElementsByTag("a").get(0).text();
		Boolean mark_211 = tdHtml.indexOf("211院校") == -1 ? false : true;
		Boolean mark_985 = tdHtml.indexOf("985院校") == -1 ? false : true;
		String school_id = StringUtil.subString(tdHtml, "university.aspx?id=",
				"\">");
		if (rankingArr.length == 1) {
			// 无排名
			dto.setRanking(null);
		} else {
			String ranking = tdContent.split(":")[1];
			dto.setRanking(Integer.valueOf(ranking));
		}
		dto.setSchool_name(school_name);
		dto.setMark_211(mark_211);
		dto.setMark_985(mark_985);
		dto.setSchool_id(Integer.valueOf(school_id));

	}

	private static void parserEnrolledInfo(YuanXiaoDTO dto, Element tdElement,
			String tdContent, String tdHtml) {
		String plan_enrolled_num = StringUtil.subString(tdContent, "计划招收人数：",
				"人");
		String enrolled_num = StringUtil.subString(tdContent, "实际报考人数：", "人");
		dto.setPlan_hire_num(Integer.valueOf(plan_enrolled_num));
		dto.setApply_num(Integer.valueOf(enrolled_num));
	}
}

class MyNodeFilter implements NodeFilter {
	Map<String, String> parms;
	String tagName;

	MyNodeFilter(String tagName, Map<String, String> parms) {
		this.tagName = tagName;
		this.parms = parms;
	}

	public boolean accept(Node node) {
		if (node instanceof TableTag) {

		}
		return false;
	}
}
