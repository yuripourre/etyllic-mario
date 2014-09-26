package mario.stages.first;

import java.io.FileNotFoundException;

import br.com.etyllica.cinematics.parallax.ImageParallax;
import br.com.vite.export.MapExporter;

public class YoshiIsland2 extends Stage {
		
	public YoshiIsland2(int w, int h) {
		super(w, h);
	}
	
	public void load() {
		super.load();
	
		try {
			map = MapExporter.loadMap("yoshiland2.json");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		handler.setMap(map);
		
		background = new ImageParallax("background/forest.png");
		background.setProximity(3);
	}
	
}
