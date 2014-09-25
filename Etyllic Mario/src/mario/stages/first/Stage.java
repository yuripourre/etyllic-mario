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
	
	protected int groundPosition = 392; //8px offset
	
	protected ImageParallax background;
	
	public Stage(int w, int h) {
		super(w, h);
	}

	@Override
	public void load() {
		
		mario = new Mario(364, groundPosition);
		
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
	
	private PointInt2D marioPoint = new PointInt2D();
	
	private PointInt2D targetPoint = new PointInt2D();
	
	private Tile target;
	
	@Override
	public void timeUpdate(long now) {
		mario.update(now);
			
		int offset = mario.getX()-LOCK_SCENE;
		
		if(offset>0) {
			mario.getLayer().setX(LOCK_SCENE);
			map.setOffsetX(-offset);
			background.setOffset(offset);
		}
		
		//Center coordinates
		int marioX = mario.getX()+mario.getLayer().getTileW()/2+map.getOffsetX();
		int marioY = mario.getY()+mario.getLayer().getTileH()/2+map.getOffsetY();
				
		marioPoint.setLocation(marioX, marioY);
		
		map.updateTarget(marioX, marioY-16*2, targetPoint);
				
		target = map.getTiles()[targetPoint.getY()][targetPoint.getX()];
		
		if(target.getCollision() != CollisionType.FREE) {
			map.getFiller().setColor(Color.RED);
		} else {
			map.getFiller().setColor(Color.BLUE);
		}
		
		target = map.getTiles()[targetPoint.getY()][targetPoint.getX()];
						
		updateMario();
	}
	
	private void updateMario() {
		
		Tile downTile = downTile(targetPoint);
		
		if(mario.isFalling()) {
			
			if(isPlatform(downTile)) {
				//Move mario to tile Up
				int positionY = map.getTileHeight()*(downTile.getY()/16)+8;
				System.out.println("Mario moved to "+positionY);
				mario.setY(positionY);
				
				//Stop falling animation
				mario.stopJump();
			}
			
		} else if(!mario.isJumping()) {
			if(!isPlatform(downTile)) {
				mario.fall();
			}
		}
	}
	
	private boolean isPlatform(Tile tile) {
		
		CollisionType collision = tile.getCollision();
			
		boolean isFixed = collision == CollisionType.UPPER || 
				          collision == CollisionType.UPPER_RIGHT || 
				          collision == CollisionType.UPPER_LEFT ||
				          collision == CollisionType.BLOCK;
		
		return isFixed;
		
	}
	
	private Tile currentTile(PointInt2D point) {
		return map.getTiles()[point.getY()][point.getX()];		
	}
	
	private Tile downTile(PointInt2D point) {
		return map.getTiles()[point.getY()+1][point.getX()];		
	}

	@Override
	public void draw(Graphic g) {
		
		g.setTransform(AffineTransform.getTranslateInstance(0, 38));
		
		background.draw(g);
		
		map.draw(g);
		
		mario.draw(g);
		
		map.drawTileFiller(g, target);
		
		g.setBasicStroke(2f);
		g.setColor(Color.BLACK);
		g.drawCircle(marioPoint.getX(), marioPoint.getY(), 8);
	}
	
}
