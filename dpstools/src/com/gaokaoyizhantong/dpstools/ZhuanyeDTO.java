package com.gaokaoyizhantong.dpstools;

public class ZhuanyeDTO {

	private String school_name;

	private Integer year;

	private String student_class;

	private String batch_name;

	private String domain_name;

	private Double max_score;

	private Double min_score;

	private Double tidang_score;

	private Integer enrolled_num;

	private Double average_score;

	public Double getAverage_score() {
		return average_score;
	}

	public void setAverage_score(Double average_score) {
		this.average_score = average_score;
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

	public String getStudent_class() {
		return student_class;
	}

	public void setStudent_class(String student_class) {
		this.student_class = student_class;
	}

	public String getBatch_name() {
		return batch_name;
	}

	public void setBatch_name(String batch_name) {
		this.batch_name = batch_name;
	}

	public String getDomain_name() {
		return domain_name;
	}

	public void setDomain_name(String domain_name) {
		this.domain_name = domain_name;
	}

	public Double getMax_score() {
		return max_score;
	}

	public void setMax_score(Double max_score) {
		this.max_score = max_score;
	}

	public Double getMin_score() {
		return min_score;
	}

	public void setMin_score(Double min_score) {
		this.min_score = min_score;
	}

	public Double getTidang_score() {
		return tidang_score;
	}

	public void setTidang_score(Double tidang_score) {
		this.tidang_score = tidang_score;
	}

	public Integer getEnrolled_num() {
		return enrolled_num;
	}

	public void setEnrolled_num(Integer enrolled_num) {
		this.enrolled_num = enrolled_num;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((batch_name == null) ? 0 : batch_name.hashCode());
		result = prime * result
				+ ((domain_name == null) ? 0 : domain_name.hashCode());
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
		ZhuanyeDTO other = (ZhuanyeDTO) obj;
		if (batch_name == null) {
			if (other.batch_name != null)
				return false;
		} else if (!batch_name.equals(other.batch_name))
			return false;
		if (domain_name == null) {
			if (other.domain_name != null)
				return false;
		} else if (!domain_name.equals(other.domain_name))
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
