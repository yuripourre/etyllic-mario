package mario.player;

import sound.model.Sound;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.layer.AnimatedLayer;
import br.com.etyllica.layer.StaticLayer;
import br.com.tide.platform.player.PlatformPlayer;
import br.com.tide.platform.player.PlatformPlayerListener;

public class Player extends PlatformPlayer implements PlatformPlayerListener {

	private static final int TILE_SIZE = 32;
	
	protected AnimatedLayer layer;

	private StaticLayer rightLayer;
	private StaticLayer leftLayer;

	private Sound jump;

	private boolean hasSound = false;

	protected boolean grown = false;

	public Player(int x, int y, String rightLayerPath, String leftLayerPath) {
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

		layer = new AnimatedLayer(x, y, TILE_SIZE, TILE_SIZE, rightLayer.getPath());
		layer.setSpeed(100);
		
		jump = new Sound("jump.wav");

		listener = this;
		
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
		layer.draw(g);
	}

	//Player Actions
	@Override
	public void stand() {
		layer.setYImage(layer.getNeedleY()+0);
		layer.setXImage(layer.getNeedleX()+0);
	}

	private void startWalking() {
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

	@Override
	public void onTurnLeft() {
		layer.cloneLayer(leftLayer);
	}

	@Override
	public void onTurnRight() {
		layer.cloneLayer(rightLayer);
	}

	@Override
	public void onWalkLeft() {
		startWalking();
	}

	@Override
	public void onWalkRight() {
		startWalking();
	}

	@Override
	public void onLookUp() {
		layer.setYImage(layer.getNeedleY()+layer.getTileH());
		layer.setXImage(layer.getNeedleX()+0);
	}

	@Override
	public void onStandDown() {
		layer.setXImage(layer.getNeedleX()+layer.getTileW());
		layer.setYImage(layer.getNeedleY()+layer.getTileH());

		layer.setFrames(1);
		layer.setStopped(true);
	}

	@Override
	public void onStopWalkLeft() {
		stopWalk();		
	}

	@Override
	public void onStopWalkRight() {
		stopWalk();
	}

	@Override
	public void onStopLookUp() {
		stopWalk();
	}

	@Override
	public void onStopStandDown() {
		stopWalk();
	}

	@Override
	public void onJump() {
		if(hasSound)
			jump.play();

		layer.setFrames(1);
		layer.setYImage(layer.getNeedleY()+layer.getTileH()*2);
		layer.setXImage(layer.getNeedleX()+0);		
		
	}

	@Override
	public void onFall() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRun() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopJump() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopRun() {
		// TODO Auto-generated method stub

	}

}
