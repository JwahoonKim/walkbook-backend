package walkbook.api.domain.model.support;

import javax.persistence.Embeddable;

@Embeddable
public class Line {

    private Double startX;
    private Double startY;
    private Double endX;
    private Double endY;

}
