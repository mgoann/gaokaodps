package com.gaokaoyizhantong.dpstools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsxToMongodbData {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		InputStream is = new FileInputStream(
				"E:\\qipfpersonal\\gaokaoDPS\\dpstools\\conf\\import.xlsx");
		XSSFWorkbook hssfWorkbook = new XSSFWorkbook(is);

		List<YuanXiaoDTO> list = new ArrayList<YuanXiaoDTO>();
		List<ZhuanyeDTO> zhuanhyelist = new ArrayList<ZhuanyeDTO>();
		// ѭ��������Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			System.out.println(hssfSheet.getSheetName());
			if (hssfSheet.getSheetName().equals("ԺУ")) {
				// ����ԺУ
				list = parserXlsDto(hssfSheet);
			} else if (hssfSheet.getSheetName().equals("רҵ")) {
				// ����רҵ
				zhuanhyelist = parserZhunaYeDTO(hssfSheet);
			}

		}

		System.out.println("ԺУ�Ϸ�����=" + list.size());
		System.out.println("רҵ�Ϸ�����=" + zhuanhyelist.size());

		Map<String, Double[]> maps = new HashMap<String, Double[]>();
		for (ZhuanyeDTO dto : zhuanhyelist) {
			if (dto.getTidang_score() == 0.0D || dto.getMax_score() == 0.0D) {
				continue;
			}
			String key = dto.getSchool_name() + dto.getYear()
					+ dto.getBatch_name() + dto.getStudent_class();
			Double[] _list;
			if (maps.containsKey(key)) {
				_list = maps.get(key);
				if (dto.getTidang_score() < _list[0])
					_list[0] = dto.getTidang_score();
				if (dto.getMax_score() > _list[1])
					_list[1] = dto.getMax_score();
			} else {
				_list = new Double[] { dto.getTidang_score(),
						dto.getMax_score() };
				maps.put(key, _list);
			}
		}
		System.out.println("��߷���ͷ���Ч���=" + maps.size());
		System.out.println(maps.keySet());
		// ����ԺУ��ݵ�רҵ������ҵ���ͷֺ���߷�
		//
		int num = 0;
		for (YuanXiaoDTO dto : list) {
			String key = dto.getSchool_name() + dto.getYear()
					+ dto.getBatch_name() + dto.getStudent_class();
			if (maps.containsKey(key)) {
				Double[] _list = maps.get(key);
				dto.setMin_score(_list[0]);
				dto.setMax_score(_list[1]);
			} else {
				num++;
				// System.out.println(key);
			}
		}
		System.out.println("û���ҵ���߷ֺ���ͷֵ����=" + num);
	}

	private static List<ZhuanyeDTO> parserZhunaYeDTO(XSSFSheet hssfSheet) {
		List<ZhuanyeDTO> list = new ArrayList<ZhuanyeDTO>();
		// ѭ����Row
		int maxNum = hssfSheet.getLastRowNum();
		System.out.println("רҵ�������=" + maxNum);
		int num = 0;
		for (int rowNum = 1; rowNum <= maxNum; rowNum++) {
			XSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			}
			ZhuanyeDTO xlsDto = new ZhuanyeDTO();
			// ԺУ���
			XSSFCell xh = hssfRow.getCell(0);
			if (xh == null) {
				continue;
			}
			xlsDto.setSchool_name(getValue(xh));
			// ���
			xh = hssfRow.getCell(1);
			if (xh == null) {
				continue;
			}
			xlsDto.setYear(getDoubleValue(xh).intValue());
			// �������
			xh = hssfRow.getCell(2);
			if (xh == null) {
				continue;
			}
			xlsDto.setStudent_class(getValue(xh));
			// ������
			xh = hssfRow.getCell(3);
			if (xh == null) {
				continue;
			}
			xlsDto.setBatch_name(getValue(xh));
			// רҵ
			xh = hssfRow.getCell(4);
			if (xh == null) {
				continue;
			}
			xlsDto.setDomain_name(getValue(xh));
			// ��߷�
			xh = hssfRow.getCell(5);
			if (xh == null) {
				continue;
			}
			xlsDto.setMax_score(getDoubleValue(xh));
			// ƽ���
			xh = hssfRow.getCell(6);
			xlsDto.setAverage_score(getDoubleValue(xh));
			// �ᵵ��
			xh = hssfRow.getCell(7);
			if (xh == null) {
				continue;
			}
			xlsDto.setTidang_score(getDoubleValue(xh));
			// ¼ȡ��
			xh = hssfRow.getCell(8);
			xlsDto.setEnrolled_num(getIntegerValue(xh));
			if (xlsDto.getEnrolled_num() == 0) {
				num++;
			}
			list.add(xlsDto);
		}
		System.out.println("רҵ¼ȡ����Ϊ0�����=" + num);
		return list;
	}

	private static List<YuanXiaoDTO> parserXlsDto(XSSFSheet hssfSheet) {
		List<YuanXiaoDTO> list = new ArrayList<YuanXiaoDTO>();
		// ѭ����Row
		int maxNum = hssfSheet.getLastRowNum();
		System.out.println("ԺУ�������=" + maxNum);
		for (int rowNum = 1; rowNum <= maxNum; rowNum++) {
			XSSFRow hssfRow = hssfSheet.getRow(rowNum);
			YuanXiaoDTO xlsDto = new YuanXiaoDTO();
			// ԺУ���
			XSSFCell xh = hssfRow.getCell(0);
			if (xh == null)
				continue;
			xlsDto.setSchool_name(getValue(xh));
			// ���
			xh = hssfRow.getCell(1);
			xlsDto.setYear(getDoubleValue(xh).intValue());
			// ƽ���
			xh = hssfRow.getCell(2);
			xlsDto.setAverage_score(getDoubleValue(xh));
			// �ᵵ��
			xh = hssfRow.getCell(3);
			xlsDto.setTidang_score(getDoubleValue(xh));
			// �������
			xh = hssfRow.getCell(4);
			xlsDto.setStudent_class(getValue(xh));
			// ¼ȡ���
			xh = hssfRow.getCell(5);
			xlsDto.setBatch_name(getValue(xh));
			// ¼ȡ��
			xh = hssfRow.getCell(6);
			xlsDto.setEnrolled_num(getIntegerValue(xh));

			list.add(xlsDto);
		}
		return list;
	}

	private static Integer getIntegerValue(XSSFCell hssfCell) {
		if (hssfCell == null) {
			return 0;
		}
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// ������ֵ���͵�ֵ
			return (int) hssfCell.getNumericCellValue();
		} else {
			// �����ַ����͵�ֵ
			return Integer.parseInt(hssfCell.getStringCellValue());
		}
	}

	private static String getValue(XSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// ������ֵ���͵�ֵ
			return String.valueOf(hssfCell.getNumericCellValue()).trim();
		} else {
			// �����ַ����͵�ֵ
			return String.valueOf(hssfCell.getStringCellValue()).trim();
		}
	}

	private static Double getDoubleValue(XSSFCell hssfCell) {
		if (hssfCell == null) {
			return 0.00D;
		}
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// ������ֵ���͵�ֵ
			return hssfCell.getNumericCellValue();
		} else {
			// �����ַ����͵�ֵ
			return Double.parseDouble(hssfCell.getStringCellValue());
		}
	}

}
