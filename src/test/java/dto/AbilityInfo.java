package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AbilityInfo {

    public AbilityDto ability;
    public Boolean is_hidden;
    public Integer slot;
}
