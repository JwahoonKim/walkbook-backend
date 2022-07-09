package walkbook.api.dto.request.post;

import lombok.Data;
import walkbook.api.domain.model.Post;
import walkbook.api.domain.model.support.Line;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreatePostRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String startPlace;

    @NotBlank
    private String endPlace;

    private String tmi;

    private List<Line> paths = new ArrayList<>();

    // 추후 추가
//    private List<PostTag> postTags = new ArrayList<>();

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .description(description)
                .startPlace(startPlace)
                .endPlace(endPlace)
                .tmi(tmi)
                .build();
    }

}
