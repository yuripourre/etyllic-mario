package mario.stages.first;

import mario.player.Mario;
import br.com.etyllica.cinematics.parallax.ImageParallax;
import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.tide.input.controller.Controller;
import br.com.tide.input.controller.EasyController;
import br.com.vite.map.Map;

public class Stage extends Application {

	protected Map map;
	
	protected Controller controller;
	
	protected Mario mario;
	
	protected int groundPosition = 354;
	
	protected ImageParallax background;
	
	public Stage(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {
				
		mario = new Mario(30, groundPosition);
		
		controller = new EasyController(mario);
		
		updateAtFixedRate(50);
	}
	
	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {
				
		//mario.handleEvent(event);
		controller.handleEvent(event);

		return GUIEvent.NONE;
	}
	
	private static final int LOCK_SCENE = 192;
	
	@Override
	public void timeUpdate(long now) {
		mario.update(now);
			
		int offset = mario.getX()-LOCK_SCENE;
		
		if(offset>0) {
			mario.setX(LOCK_SCENE);
			map.setOffsetX(-offset);
			background.setOffset(offset);
		}
	}

	@Override
	public void draw(Graphic g) {
		background.draw(g);
		
		map.draw(g);
		
		mario.draw(g);
	}
	
}
