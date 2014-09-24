package mario.stages.first;

import java.awt.Color;
import java.awt.geom.AffineTransform;

import mario.player.Mario;
import br.com.etyllica.cinematics.parallax.ImageParallax;
import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.linear.PointInt2D;
import br.com.tide.input.controller.Controller;
import br.com.tide.input.controller.EasyController;
import br.com.vite.map.Map;
import br.com.vite.tile.Tile;
import br.com.vite.tile.collision.CollisionType;

public class Stage extends Application {

	protected Map map;
	
	protected Controller controller;
	
	protected Mario mario;
	
	protected int groundPosition = 392;
	
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
	
	private PointInt2D targetPoint = new PointInt2D();
	
	private Tile target;
	
	@Override
	public void timeUpdate(long now) {
		mario.update(now);
			
		int offset = mario.getX()-LOCK_SCENE;
		
		if(offset>0) {
			mario.setX(LOCK_SCENE);
			map.setOffsetX(-offset);
			background.setOffset(offset);
		}
		
		//Center coordinates
		int marioX = mario.getX()+mario.getLayer().getTileW()/2+map.getOffsetX();
		int marioY = mario.getY()+mario.getLayer().getTileH();
				
		map.updateTarget(marioX, marioY-16*3, targetPoint);
				
		target = map.getTiles()[targetPoint.getY()][targetPoint.getX()];
								
		if(target.getCollision() != CollisionType.FREE) {
			map.getFiller().setColor(Color.RED);
		} else {
			map.getFiller().setColor(Color.BLUE);
		}
		
		target = map.getTiles()[targetPoint.getY()+2][targetPoint.getX()];
		
	}

	@Override
	public void draw(Graphic g) {
		
		g.setTransform(AffineTransform.getTranslateInstance(0, 38));
		
		background.draw(g);
		
		map.draw(g);
		
		mario.draw(g);
		
		map.drawTileFiller(g, target);
	}
	
}
