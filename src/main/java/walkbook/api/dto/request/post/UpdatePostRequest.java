package walkbook.api.dto.request.post;

import lombok.Data;
import walkbook.api.domain.model.support.Line;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class UpdatePostRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String startPlace;

    @NotBlank
    private String endPlace;

    @NotBlank
    private String tmi;

    private List<Line> paths = new ArrayList<>();

}
