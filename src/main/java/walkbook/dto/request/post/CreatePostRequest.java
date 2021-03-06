package walkbook.dto.request.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import walkbook.domain.Post;
import walkbook.domain.User;
import walkbook.domain.support.Line;
import walkbook.domain.support.Path;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String startPlace;

    @NotBlank
    private String endPlace;

    private String tmi;

    @Size(min = 1)
    private List<Line> paths = new ArrayList<>();

    // 추후 추가
//    private List<PostTag> postTags = new ArrayList<>();

    public Post toEntity(User user) {
        Post post = Post.builder()
                .title(title)
                .description(description)
                .startPlace(startPlace)
                .endPlace(endPlace)
                .tmi(tmi)
                .build();

        post.setUser(user);
        pathSetting(post);

        return post;
    }

    private void pathSetting(Post post) {
        for (Line line : paths) {
            Path path = new Path(post, line);
            post.addPath(path);
        }
    }

}
