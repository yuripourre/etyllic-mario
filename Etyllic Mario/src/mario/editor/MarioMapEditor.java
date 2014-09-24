package mario.editor;

import java.io.FileNotFoundException;

import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.layer.ImageLayer;
import br.com.vite.MapApplication;
import br.com.vite.collection.tileset.land.LandTileSet;
import br.com.vite.editor.OrthogonalMapEditor;
import br.com.vite.export.MapExporter;
import br.com.vite.map.selection.OrthogonalFloorSelection;

public class MarioMapEditor extends MapApplication {
	
	private final String mapFile = "yoshiland2.json";
	
	private final int tileWidth = 16;
	private final int tileHeight = 16;

	private int tileSetOffsetY = 380;
	
	private OrthogonalFloorSelection selectionPlatformMap;
	
	private ImageLayer background;

	public MarioMapEditor(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {

		final int columns = 320;
		final int lines = 27;

		editor = new OrthogonalMapEditor(columns, lines, tileWidth, tileHeight);
		editor.translateMap(0, -152);

		loading = 30;
				
		selectionPlatformMap = new OrthogonalFloorSelection(10, 8, tileWidth, tileHeight);
		selectionPlatformMap.translateMap(13*tileWidth, tileSetOffsetY);
		selectionPlatformMap.setListener(editor);
		selectionPlatformMap.setTileSet(new LandTileSet());
		
		loading = 70;		

		updateAtFixedRate(80);
		
		background = new ImageLayer(0, -152, "platform/bg.png");
		background.setOpacity(100);

		loading = 100;
	}

	@Override
	public void timeUpdate(long now) {
		super.timeUpdate(now);
				
		selectionPlatformMap.update(now);
	}
		
	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
		super.updateKeyboard(event);

		if(event.isKeyDown(KeyEvent.TSK_1)) {
					    
		    MapExporter.export(editor, mapFile);
		}
		
		if(event.isKeyDown(KeyEvent.TSK_2)) {
		    
			try {
				
				int offsetX = editor.getOffsetX();
				int offsetY = editor.getOffsetY();
				
				editor = MapExporter.load(mapFile);
				selectionPlatformMap.setListener(editor);
								
				editor.translateMap(offsetX, offsetY);
								
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		
		return GUIEvent.NONE;
	}
	
	@Override
	protected void offsetMap(int offsetX, int offsetY) {
		super.offsetMap(offsetX, offsetY);
		
		background.setOffset(offsetX, offsetY);
	}
	
	@Override
	public GUIEvent updateMouse(PointerEvent event) {				
		super.updateMouse(event);
		
		selectionPlatformMap.updateMouse(event);
		
		return GUIEvent.NONE;
	}

	@Override
	public void draw(Graphic g) {
		super.draw(g);
		
		background.draw(g);

		selectionPlatformMap.draw(g);
	}

}
