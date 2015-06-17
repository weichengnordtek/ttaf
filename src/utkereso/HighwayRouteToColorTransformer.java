package utkereso;

import java.awt.Color;
import java.awt.Paint;
import java.util.List;

import org.apache.commons.collections15.Transformer;

public class HighwayRouteToColorTransformer implements Transformer<Road, Paint> {

	private List<Road> startToHighway;
	private List<Road> endToHighway;
	private List<Road> highway;

	public HighwayRouteToColorTransformer(List<Road> startToHighway, List<Road> endToHighway, List<Road> highway) {
		this.startToHighway = startToHighway;
		this.endToHighway = endToHighway;
		this.highway = highway;
	}
	
	@Override
    public Paint transform(Road road) {
        System.setProperty("zold", "0X96d72d");
        if(startToHighway.contains(road))
        {
            return Color.getColor("zold");
        }
        else if(endToHighway.contains(road))
        {
            return Color.getColor("zold");
        }
        else if(highway.contains(road))
        {
            return Color.RED;
        }
        else
        {
            return Color.BLACK;
        }
    }

}
