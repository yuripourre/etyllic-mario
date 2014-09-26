package mario.player;

import sound.model.Sound;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.layer.AnimatedLayer;
import br.com.etyllica.layer.StaticLayer;
import br.com.tide.platform.player.PlatformPlayer;

public class Player extends PlatformPlayer {

	private static final int TILE_SIZE = 16;
	
	protected AnimatedLayer layer;

	private StaticLayer rightLayer;
	private StaticLayer leftLayer;

	private Sound jump;

	private boolean hasSound = false;

	protected boolean grown = false;

	public Player(int x, int y, String rightLayerPath, String leftLayerPath, PlayerHandler handler) {
		super();

		this.x = x;
		this.y = y;
		
		//Configure Attributes
		walkSpeed = 3;
		runSpeed = 5;
		jumpSpeed = 5;
		jumpHeight = 64;

		this.rightLayer = new StaticLayer(rightLayerPath);

		this.leftLayer = new StaticLayer(leftLayerPath);

		layer = new AnimatedLayer(x, y, TILE_SIZE, TILE_SIZE*2, rightLayer.getPath());
		layer.setSpeed(100);
		
		jump = new Sound("jump.wav");

		listener = handler;
		
		//Force player to stand
		stand();
	}

	@Override
	public void update(long now) {
		super.update(now);

		layer.animate(now);

		layer.setCoordinates(x, y);		
	}

	public void draw(Graphic g) {
		layer.draw(g, 0, -40);
	}
	
	//Player Actions
	@Override
	public void stand() {
		layer.setYImage(layer.getNeedleY()+0);
		layer.setXImage(layer.getNeedleX()+0);
	}

	public void startWalking() {
		layer.setFrames(2);
		layer.setStopped(false);
	}

	@Override
	public void stopJump() {
		super.stopJump();

		stand();

		if(isWalking()) {
			startWalking();
		}
	}

	@Override
	public void fall() {
		super.fall();
		
		layer.setYImage(layer.getNeedleY()+layer.getTileH()*2);
		layer.setXImage(layer.getNeedleX()+layer.getTileW());
	}

	@Override
	public void stopWalk() {
		super.stopWalk();

		layer.setFrames(1);
		layer.setStopped(true);

		layer.setXImage(layer.getNeedleX()+0);
	}

	public AnimatedLayer getLayer() {
		return layer;
	}

	public boolean isGrown() {
		return grown;
	}

	public void setGrown(boolean grown) {
		this.grown = grown;
	}
	
	public void turnLeft() {
		layer.cloneLayer(leftLayer);
	}
	public void turnRight() {
		layer.cloneLayer(rightLayer);
	}
	
	public void lookUpAction() {
		super.lookUp();
		layer.setYImage(layer.getNeedleY()+layer.getTileH());
		layer.setXImage(layer.getNeedleX()+0);
	}
	
	public void standDownAction() {
		layer.setXImage(layer.getNeedleX()+layer.getTileW());
		layer.setYImage(layer.getNeedleY()+layer.getTileH());

		layer.setFrames(1);
		layer.setStopped(true);
	}
	
	public void jumpAction() {
		//Move to listener
		if(hasSound)
			jump.play();

		layer.setFrames(1);
		layer.setYImage(layer.getNeedleY()+layer.getTileH()*2);
		layer.setXImage(layer.getNeedleX()+0);
	}

}
