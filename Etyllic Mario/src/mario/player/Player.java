package mario.player;

import sound.model.Sound;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.layer.AnimatedLayer;
import br.com.etyllica.layer.StaticLayer;

public class Player {

	private AnimatedLayer layer;

	private StaticLayer rightLayer;
	private StaticLayer leftLayer;

	private Sound jump;
	
	private boolean right = true;
	private boolean walking = false;
	private boolean jumping = false;
	private boolean fallen = false;

	private boolean looking = false;
	
	private int walkSpeed = 3;
	private int jumpSpeed = 5;
	private int jumpSize = 32;
	private int groundPosition = 0;
	
	private boolean grown = false;

	public Player(int x, int y, String rightLayerPath, String leftLayerPath) {
		super();

		final int TILE_SIZE = 32;

		this.rightLayer =  new StaticLayer(rightLayerPath);

		this.leftLayer =  new StaticLayer(leftLayerPath);

		layer = new AnimatedLayer(x, y, TILE_SIZE, TILE_SIZE, rightLayer.getPath());
		layer.setSpeed(100);
		//Force player to stand
		stopWalk();
		
		groundPosition = y;
		jumpSize = groundPosition-TILE_SIZE;//groundPosition - 100 pixels
		
		jump = new Sound("jump.wav");
	}

	public void update(long now) {

		layer.animate(now);

		//if walking
		if(walking) {
			//if walking to Right
			if(right) {
				layer.setX(layer.getX()+walkSpeed);
				//if walking to Left
			}else{
				layer.setX(layer.getX()-walkSpeed);
			}
		}

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
	private void stand(){
		layer.setYImage(0);
		layer.setXImage(0);

		looking = false;
	}

	private void lookUp(){
		layer.setYImage(layer.getTileH());
		layer.setXImage(0);

		looking = true;
	}

	private void standDown(){
		layer.setXImage(layer.getTileW());
		layer.setYImage(layer.getTileH());

		looking = true;
	}

	private void startJump(){
		if(!jumping){
			jump.play();
			jumping = true;

			layer.setYImage(layer.getTileH()*2);
			layer.setXImage(0);
		}
	}

	private void turnRight(){
		layer.cloneLayer(rightLayer);
		right = true;
	}

	private void turnLeft(){
		layer.cloneLayer(leftLayer);
		right = false;
	}

	private void startWalking(){
		layer.setFrames(2);
		layer.setStopped(false);
		walking = true;
	}	

	private void stopJump(){

		jumping = false;
		fallen = false;

		layer.setYImage(0);
		layer.setXImage(0);

	}

	private void startFallen(){
		fallen = true;

		layer.setYImage(layer.getTileH()*2);
		layer.setXImage(layer.getTileW());
	}

	private void stopWalk(){
		layer.setFrames(1);
		layer.setStopped(true);
		walking = false;

		layer.setXImage(0);
	}

	public void handleEvent(KeyEvent event) {
		
		if(!walking){

			//UP Arrow
			if(event.isKeyDown(KeyEvent.TSK_UP_ARROW)||event.isKeyDown(KeyEvent.TSK_JOYSTICK_UP)){
				lookUp();				
			}else if(event.isKeyUp(KeyEvent.TSK_UP_ARROW)||event.isKeyUp(KeyEvent.TSK_JOYSTICK_CENTER_Y)){
				stand();
			}

			//DOWN Arrow
			if(event.isKeyDown(KeyEvent.TSK_DOWN_ARROW)||event.isKeyDown(KeyEvent.TSK_JOYSTICK_DOWN)){
				standDown();
			}else if(event.isKeyUp(KeyEvent.TSK_DOWN_ARROW)||event.isKeyDown(KeyEvent.TSK_JOYSTICK_CENTER_Y)){
				stand();
			}
		}

		if(!looking){

			//RIGHT Arrow
			if(event.isKeyDown(KeyEvent.TSK_RIGHT_ARROW)||event.isKeyDown(KeyEvent.TSK_JOYSTICK_RIGHT)){
				turnRight();
				startWalking();

			}else if(event.isKeyUp(KeyEvent.TSK_RIGHT_ARROW)||event.isKeyDown(KeyEvent.TSK_JOYSTICK_CENTER_X)){
				stopWalk();
			}

			//LEFT Arrow
			if(event.isKeyDown(KeyEvent.TSK_LEFT_ARROW)||event.isKeyDown(KeyEvent.TSK_JOYSTICK_LEFT)){
				turnLeft();
				startWalking();

			}else if(event.isKeyUp(KeyEvent.TSK_LEFT_ARROW)||event.isKeyDown(KeyEvent.TSK_JOYSTICK_CENTER_X)){
				stopWalk();
			}

		//If Looking up or down
		}else{
			
			//RIGHT ARROW
			if(event.isKeyDown(KeyEvent.TSK_RIGHT_ARROW)||event.isKeyDown(KeyEvent.TSK_JOYSTICK_RIGHT)){
				turnRight();
			}
			
			//LEFT ARROW
			else if(event.isKeyDown(KeyEvent.TSK_LEFT_ARROW)||event.isKeyDown(KeyEvent.TSK_JOYSTICK_LEFT)){
				turnLeft();
			}
		}

		if(event.isKeyDown(KeyEvent.TSK_ESPACO)||event.isKeyDown(KeyEvent.TSK_JOYSTICK_BUTTON_1)){
			startJump();
		}
		
		if(event.isKeyDown(KeyEvent.TSK_SHIFT_LEFT)||event.isKeyDown(KeyEvent.TSK_JOYSTICK_BUTTON_3)){
			walkSpeed = 5;
		}else if(event.isKeyUp(KeyEvent.TSK_SHIFT_LEFT)||event.isKeyUp(KeyEvent.TSK_JOYSTICK_BUTTON_3)){
			walkSpeed = 3;
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
	
}
