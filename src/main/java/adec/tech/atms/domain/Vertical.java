package adec.tech.atms.domain;

import javax.validation.GroupSequence;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import adec.tech.atms.config.Constants;
import adec.tech.atms.validation.annotation.ValidVertical;

@GroupSequence({NotBlank.class, Pattern.class, Min.class, Max.class, ValidVertical.class, Vertical.class})
public class Vertical {

	private Integer id;
	
	@NotBlank(message = Constants.EMPTY_VERTICAL_NAME, groups = NotBlank.class)
	@Size(max = 3, message = "Name can only accept up to 3 characters")
	@ValidVertical
	private String name;
	
	@NotBlank(message = Constants.EMPTY_VERTICAL_CODE, groups = NotBlank.class)
	private String code;
	
	private String description;
	
	private Integer statusId;
	
	private Integer createdBy;
	
	private String createdDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
