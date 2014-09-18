package mario.player;

import sound.model.Sound;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.layer.AnimatedLayer;
import br.com.etyllica.layer.StaticLayer;
import br.com.tide.platform.player.PlatformPlayer;
import br.com.tide.platform.player.PlatformPlayerListener;

public class Player extends PlatformPlayer implements PlatformPlayerListener {

	protected AnimatedLayer layer;

	private StaticLayer rightLayer;
	private StaticLayer leftLayer;

	private Sound jump;
	
	private boolean jumping = false;
	private boolean fallen = false;
		
	private int groundPosition = 0;
	
	protected boolean grown = false;

	public Player(int x, int y, String rightLayerPath, String leftLayerPath) {
		super();

		final int TILE_SIZE = 32;
		
		//Configure Attributes
		walkSpeed = 3;
		runSpeed = 5;
		jumpSpeed = 5;
		jumpSize = 32;
		
		this.rightLayer =  new StaticLayer(rightLayerPath);

		this.leftLayer =  new StaticLayer(leftLayerPath);

		layer = new AnimatedLayer(x, y, TILE_SIZE, TILE_SIZE, rightLayer.getPath());
		layer.setSpeed(100);
		//Force player to stand
		stopWalk();
		
		groundPosition = y;
		jumpSize = groundPosition-TILE_SIZE;//groundPosition - 100 pixels
		
		jump = new Sound("jump.wav");
		
		listener = this;
	}

	@Override
	public void update(long now) {
		super.update(now);
		
		layer.animate(now);

		layer.setX(x);
		
		//if jumping
		if(jumping) {

			if(!fallen) {

				if(layer.getY()>jumpSize) {
					layer.setY(layer.getY()-jumpSpeed);	
				}else{
					startFallen();
				}

			}else{

				if(layer.getY()<groundPosition) {
					layer.setY(layer.getY()+jumpSpeed);	
				}else{
					stopJump();
				}
			}
		}

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

	public void jump() {
		if(!jumping) {
			jump.play();
			jumping = true;
	
			layer.setFrames(1);
			layer.setYImage(layer.getNeedleY()+layer.getTileH()*2);
			layer.setXImage(layer.getNeedleX()+0);
		}
	}

	private void startWalking() {
		layer.setFrames(2);
		layer.setStopped(false);
	}	

	private void stopJump() {

		jumping = false;
		fallen = false;

		layer.setYImage(layer.getNeedleY()+0);
		layer.setXImage(layer.getNeedleX()+0);
		
		if(isWalking()) {
			startWalking();
		}
	}

	private void startFallen() {
		fallen = true;

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

	public void handleEvent(KeyEvent event) {
		
		if(event.isKeyDown(KeyEvent.TSK_ESPACO)||event.isKeyDown(KeyEvent.TSK_JOYSTICK_BUTTON_1)) {
			jump();			
		}
		
		if(event.isKeyDown(KeyEvent.TSK_SHIFT_LEFT)||event.isKeyDown(KeyEvent.TSK_JOYSTICK_BUTTON_3)) {
			currentSpeed = 5;
		}else if(event.isKeyUp(KeyEvent.TSK_SHIFT_LEFT)||event.isKeyUp(KeyEvent.TSK_JOYSTICK_BUTTON_3)) {
			currentSpeed = 3;
		}
		
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
	
}
