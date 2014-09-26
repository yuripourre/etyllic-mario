package mario.stages.first;

import java.awt.Color;
import java.awt.geom.AffineTransform;

import mario.player.Mario;
import mario.player.PlayerHandler;
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
	
	protected int groundPosition = 392; //8px offset
	
	protected ImageParallax background;
	
	protected PlayerHandler handler;
	
	public Stage(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {
		
		handler = new PlayerHandler();
		
		mario = new Mario(1800, groundPosition, handler);
		
		controller = new EasyController(mario);
		
		handler.addPlayer(mario);
		
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
			mario.getLayer().setX(LOCK_SCENE);
			map.setOffsetX(-offset);
			background.setOffset(offset);
		}
		
		handler.updatePlayers();		
	}
	
	@Override
	public void draw(Graphic g) {
		
		g.setTransform(AffineTransform.getTranslateInstance(0, 38));
		
		background.draw(g);
		
		map.draw(g);
		
		mario.draw(g);
		
		map.drawTileFiller(g, handler.getTarget());
		
		g.setBasicStroke(2f);
		g.setColor(Color.BLACK);
		g.drawCircle(handler.getPlayerPoint().getX(), handler.getPlayerPoint().getY(), 8);
	}
	
}
