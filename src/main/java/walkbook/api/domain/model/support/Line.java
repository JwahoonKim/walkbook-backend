package walkbook.api.domain.model.support;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter @Setter
public class Line {

    private Double startX;
    private Double startY;
    private Double endX;
    private Double endY;

    public Line(Path path) {
        Line line = path.getLine();
        startX = line.getStartX();
        startY = line.getStartY();
        endX = line.getEndX();
        endY = line.getEndY();
    }

    @Override
    public String toString() {
        return "Line{" +
                "startX=" + startX +
                ", startY=" + startY +
                ", endX=" + endX +
                ", endY=" + endY +
                '}';
    }
}
