package utkereso;

import java.awt.Color;
import java.awt.Paint;
import java.util.List;

import org.apache.commons.collections15.Transformer;

public class SimpleRoadToColorTransformer implements Transformer<Road, Paint> {

	private List<Road> roadList;

	public SimpleRoadToColorTransformer(List<Road> roadList) {
		this.roadList = roadList;
	}
	
	@Override
	public Paint transform(Road s) {
        if(roadList.contains(s))
        {
            return Color.RED;
        }
        else
        {
            return Color.BLACK;
        }
    }

}
