package walkbook.dto.response.post;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostListResponse {

    List<PostResponseDto> posts = new ArrayList<>();

}
