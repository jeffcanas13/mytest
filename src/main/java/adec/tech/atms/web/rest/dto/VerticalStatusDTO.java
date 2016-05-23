package adec.tech.atms.web.rest.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import adec.tech.atms.config.Constants;
import adec.tech.atms.validation.annotation.ValidVertical;

public class VerticalStatusDTO {
    @NotBlank(message = Constants.INVALID_VERTICAL_ID_FORMAT, groups = NotBlank.class)
    @Pattern(message = Constants.INVALID_VERTICAL_ID_FORMAT, regexp = "\\d+", groups = Pattern.class)
    @Min(message = Constants.INVALID_VERTICAL_ID_FORMAT, value = 1, groups = Min.class)
    @Max(message = Constants.INVALID_VERTICAL_ID_FORMAT, value = Integer.MAX_VALUE, groups = Max.class)
    @ValidVertical(groups = ValidVertical.class)
    private String verticalId;

    @NotBlank(message = Constants.SPECIFY_STATUS, groups = NotBlank.class)
    @Pattern(message = Constants.INVALID_STATUS_ID_FORMAT, regexp = "\\d", groups = Pattern.class)
    @Min(message = Constants.INVALID_STATUS_ID_FORMAT, value = 1, groups = Min.class)
    @Max(message = Constants.INVALID_STATUS_ID_FORMAT, value = 2, groups = Max.class)
    private String statusId;

    public String getVerticalId() {
        return verticalId;
    }

    public void setVerticalId(String verticalId) {
        this.verticalId = verticalId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }
	
}
