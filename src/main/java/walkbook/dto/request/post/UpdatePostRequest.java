package walkbook.dto.request.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import walkbook.domain.support.Line;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Size(min = 1)
    private List<Line> paths = new ArrayList<>();

}
