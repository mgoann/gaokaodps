package com.gaokaoyizhantong.dpstools;

public class YuanXiaoDTO {

	private String school_name;

	private Integer school_id;

	private String school_type;

	private Integer year;

	private Double average_score;

	private Double max_score;

	private String batch_name;

	private Integer ranking;

	private Integer plan_hire_num;

	private Integer apply_num;

	private String region_name;

	private Boolean mark_211;

	private Boolean mark_985;

	public String getSchool_type() {
		return school_type;
	}

	public void setSchool_type(String school_type) {
		this.school_type = school_type;
	}

	public Boolean getMark_211() {
		return mark_211;
	}

	public void setMark_211(Boolean mark_211) {
		this.mark_211 = mark_211;
	}

	public Boolean getMark_985() {
		return mark_985;
	}

	public void setMark_985(Boolean mark_985) {
		this.mark_985 = mark_985;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public Integer getSchool_id() {
		return school_id;
	}

	public void setSchool_id(Integer school_id) {
		this.school_id = school_id;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	private Integer enrolled_num;

	private String student_class;

	private Double gold_score;

	private Double tidang_score;

	public Double getTidang_score() {
		return tidang_score;
	}

	public void setTidang_score(Double tidang_score) {
		this.tidang_score = tidang_score;
	}

	public Double getGold_score() {
		return gold_score;
	}

	public void setGold_score(Double gold_score) {
		this.gold_score = gold_score;
	}

	public String getSchool_name() {
		return school_name;
	}

	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Double getAverage_score() {
		return average_score;
	}

	public void setAverage_score(Double average_score) {
		this.average_score = average_score;
	}

	public Double getMax_score() {
		return max_score;
	}

	public void setMax_score(Double max_score) {
		this.max_score = max_score;
	}

	@Override
	public String toString() {
		return "" + school_name + ", " + school_id + ", " + year + ", "
				+ average_score + ", " + max_score + ", " + batch_name + ", "
				+ ranking + ", " + plan_hire_num + ", " + apply_num + ", "
				+ region_name + ", " + mark_211 + ", " + mark_985 + ", "
				+ enrolled_num + ", " + student_class + ", " + gold_score
				+ ", " + tidang_score;
	}

	public Integer getPlan_hire_num() {
		return plan_hire_num;
	}

	public void setPlan_hire_num(Integer plan_hire_num) {
		this.plan_hire_num = plan_hire_num;
	}

	public Integer getApply_num() {
		return apply_num;
	}

	public void setApply_num(Integer apply_num) {
		this.apply_num = apply_num;
	}

	public String getBatch_name() {
		return batch_name;
	}

	public void setBatch_name(String batch_name) {
		this.batch_name = batch_name;
	}

	public Integer getEnrolled_num() {
		return enrolled_num;
	}

	public void setEnrolled_num(Integer enrolled_num) {
		this.enrolled_num = enrolled_num;
	}

	public String getStudent_class() {
		return student_class;
	}

	public void setStudent_class(String student_class) {
		this.student_class = student_class;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((batch_name == null) ? 0 : batch_name.hashCode());
		result = prime * result
				+ ((school_name == null) ? 0 : school_name.hashCode());
		result = prime * result
				+ ((student_class == null) ? 0 : student_class.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		YuanXiaoDTO other = (YuanXiaoDTO) obj;
		if (batch_name == null) {
			if (other.batch_name != null)
				return false;
		} else if (!batch_name.equals(other.batch_name))
			return false;
		if (school_name == null) {
			if (other.school_name != null)
				return false;
		} else if (!school_name.equals(other.school_name))
			return false;
		if (student_class == null) {
			if (other.student_class != null)
				return false;
		} else if (!student_class.equals(other.student_class))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

}
