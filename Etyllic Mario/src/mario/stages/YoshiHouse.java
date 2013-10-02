package mario.stages;

import mario.item.fruit.RedFruit;
import br.com.etyllica.animation.scripts.FrameAnimationScript;
import br.com.etyllica.core.application.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.video.Graphic;
import br.com.etyllica.layer.AnimatedLayer;
import br.com.etyllica.layer.ImageLayer;
import br.com.etyllica.layer.StaticLayer;
import br.com.etyllica.multimedia.Music;
import br.com.etyllica.multimedia.Sound;

public class YoshiHouse extends Application{

	public YoshiHouse(int w, int h) {
		super(w, h);
	}

	private Sound jump;
	private Music music;

	private ImageLayer background;

	private StaticLayer marioLeft;
	private StaticLayer marioRight;

	private AnimatedLayer mario;

	private boolean right = true;
	private boolean walking = false;
	private boolean jumping = false;
	private boolean fallen = false;

	private boolean looking = false;

	private int walkSpeed = 3;
	private int jumpSpeed = 5;
	private int jumpSize = 32;

	private int groundPosition = 163;
	
	private RedFruit[] fruits;

	@Override
	public void load() {

		loadingPhrase = "Loading Resources...";

		//By default, engine looks for image at /bin/res/images folder
		background = new ImageLayer("yoshihouse.png");
		
		fruits = new RedFruit[7];
		fruits[0] = new RedFruit(32, 60);
		fruits[1] = new RedFruit(48, 76);
		fruits[2] = new RedFruit(96, 60);
		fruits[3] = new RedFruit(78, 92);
		fruits[4] = new RedFruit(114, 76);
		fruits[5] = new RedFruit(176, 60);
		fruits[6] = new RedFruit(208, 76);
		
		for(RedFruit fruit: fruits){
			animation.add(new FrameAnimationScript(fruit));
		}
		
		loading = 20;

		marioRight = new StaticLayer("mario.png");
		marioLeft = new StaticLayer("marioinv.png");

		mario = new AnimatedLayer(30,groundPosition,32,32,marioRight.getPath());
		mario.setFrames(2);

		jumpSize = groundPosition-32;//groundPosition - 100 pixels 

		loading = 60;

		jump = new Sound("jump.wav");

		loading = 80;


		music = new Music("Yoster Island.mp3");
		//music.play();

		updateAtFixedRate(40);
		
		loading = 100;

	}
	
	@Override
	public void timeUpdate(){
		
		mario.preAnima();
		
		//if walking
		if(walking){
			//if walking to Right
			if(right){
				mario.setX(mario.getX()+walkSpeed);
				//if walking to Left
			}else{
				mario.setX(mario.getX()-walkSpeed);
			}
		}

		//if jumping
		if(jumping){

			if(!fallen){

				if(mario.getY()>jumpSize){
					mario.setY(mario.getY()-jumpSpeed);	
				}else{
					startFallen();
				}

			}else{

				if(mario.getY()<groundPosition){
					mario.setY(mario.getY()+jumpSpeed);	
				}else{
					stopJump();
				}

			}
		}

	}

	@Override
	public void draw(Graphic g) {

		background.draw(g);
		
		for(RedFruit fruit: fruits){
			fruit.draw(g);	
		}
		
		
		mario.draw(g);

	}

	@Override
	public GUIEvent updateKeyboard(KeyEvent event) {

		if(!walking){

			//UP Arrow
			if(event.onKeyDown(KeyEvent.TSK_UP_ARROW)||event.onKeyDown(KeyEvent.TSK_JOYSTICK_UP)){
				lookUp();				
			}else if(event.onKeyUp(KeyEvent.TSK_UP_ARROW)||event.onKeyUp(KeyEvent.TSK_JOYSTICK_CENTER_Y)){
				stand();
			}

			//DOWN Arrow
			if(event.onKeyDown(KeyEvent.TSK_DOWN_ARROW)||event.onKeyDown(KeyEvent.TSK_JOYSTICK_DOWN)){
				standDown();
			}else if(event.onKeyUp(KeyEvent.TSK_DOWN_ARROW)||event.onKeyDown(KeyEvent.TSK_JOYSTICK_CENTER_Y)){
				stand();
			}
		}

		if(!looking){

			//RIGHT Arrow
			if(event.onKeyDown(KeyEvent.TSK_RIGHT_ARROW)||event.onKeyDown(KeyEvent.TSK_JOYSTICK_RIGHT)){
				turnRight();
				startWalking();

			}else if(event.onKeyUp(KeyEvent.TSK_RIGHT_ARROW)||event.onKeyDown(KeyEvent.TSK_JOYSTICK_CENTER_X)){
				stopWalk();
			}

			//LEFT Arrow
			if(event.onKeyDown(KeyEvent.TSK_LEFT_ARROW)||event.onKeyDown(KeyEvent.TSK_JOYSTICK_LEFT)){
				turnLeft();
				startWalking();

			}else if(event.onKeyUp(KeyEvent.TSK_LEFT_ARROW)||event.onKeyDown(KeyEvent.TSK_JOYSTICK_CENTER_X)){
				stopWalk();
			}

		//If Looking up or down
		}else{
			
			//RIGHT ARROW
			if(event.onKeyDown(KeyEvent.TSK_RIGHT_ARROW)||event.onKeyDown(KeyEvent.TSK_JOYSTICK_RIGHT)){
				turnRight();
			}
			
			//LEFT ARROW
			else if(event.onKeyDown(KeyEvent.TSK_LEFT_ARROW)||event.onKeyDown(KeyEvent.TSK_JOYSTICK_LEFT)){
				turnLeft();
			}
		}

		if(event.onKeyDown(KeyEvent.TSK_ESPACO)||event.onKeyDown(KeyEvent.TSK_JOYSTICK_BUTTON_1)){
			startJump();
		}
		
		if(event.onKeyDown(KeyEvent.TSK_SHIFT_LEFT)||event.onKeyDown(KeyEvent.TSK_JOYSTICK_BUTTON_3)){
			walkSpeed = 5;
		}else if(event.onKeyUp(KeyEvent.TSK_SHIFT_LEFT)||event.onKeyUp(KeyEvent.TSK_JOYSTICK_BUTTON_3)){
			walkSpeed = 3;
		}

		return GUIEvent.NONE;
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		return GUIEvent.NONE;
	}

	//Methods to represent Player Actions
	private void stand(){
		mario.setYImage(0);
		mario.setXImage(0);

		looking = false;
	}

	private void lookUp(){
		mario.setYImage(mario.getYTile());
		mario.setXImage(0);

		looking = true;
	}

	private void standDown(){
		mario.setXImage(mario.getXTile());
		mario.setYImage(mario.getYTile());

		looking = true;
	}

	private void startJump(){
		if(!jumping){
			jump.play();
			jumping = true;

			mario.setYImage(mario.getYTile()*2);
			mario.setXImage(0);
		}
	}

	private void turnRight(){
		mario.cloneLayer(marioRight);
		right = true;
	}

	private void turnLeft(){
		mario.cloneLayer(marioLeft);
		right = false;
	}

	private void startWalking(){
		mario.setFrames(2);
		mario.setStopped(false);
		walking = true;
	}	

	private void stopJump(){

		jumping = false;
		fallen = false;

		mario.setYImage(0);
		mario.setXImage(0);

	}

	private void startFallen(){
		fallen = true;

		mario.setYImage(mario.getYTile()*2);
		mario.setXImage(mario.getXTile());
	}

	private void stopWalk(){
		mario.setFrames(1);
		mario.setStopped(true);
		walking = false;

		mario.setXImage(0);
	}

}
