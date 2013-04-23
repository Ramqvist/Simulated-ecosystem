package chalmers.dax021308.ecosystem.model.environment.mapeditor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the colors used in the MapEditor.
 * 
 * @author Erik Ramqvist.
 *
 */
public class ObstacleColorContainer {
	public final static ObstacleColorContainer 	BLUE;
	public final static ObstacleColorContainer 	BLACK;
	public final static ObstacleColorContainer 	BROWN;
	public final static List<ObstacleColorContainer> COLOR_LIST;
	
	static {
		BLUE = new ObstacleColorContainer(Color.BLUE, "Blue");
		BLACK = new ObstacleColorContainer(Color.BLACK, "Black");
		BROWN = new ObstacleColorContainer(new Color(139,69,19), "Brown");
		COLOR_LIST = new ArrayList<ObstacleColorContainer>();
		COLOR_LIST.add(BLUE);
		COLOR_LIST.add(BLACK);
		COLOR_LIST.add(BROWN);
	}
	public String name;
	public Color c;
	
	public ObstacleColorContainer(Color c, String name) {
		this.name = name;
		this.c = c;
	}
	
	public static ObstacleColorContainer getObstacleColorContainerFromColor(Color c) {
		for(ObstacleColorContainer o : COLOR_LIST) {
			if(o.c.equals(c)) {
				return o;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
