package mario.stages.first;

import mario.item.fruit.RedFruit;
import mario.player.Player;
import sound.model.Music;
import br.com.etyllica.animation.AnimationHandler;
import br.com.etyllica.animation.scripts.FrameAnimation;
import br.com.etyllica.context.Application;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.layer.ImageLayer;

public class YoshiHouse extends Application {

	public YoshiHouse(int w, int h) {
		super(w, h);
	}

	private Music music;

	private ImageLayer background;

	private Player mario;

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
			AnimationHandler.getInstance().add(new FrameAnimation(fruit));
		}
		
		loading = 20;

		mario = new Player(30, groundPosition, "mario.png", "marioinv.png");

		loading = 80;

		music = new Music("Yoster Island.mp3");
		//music.play();

		updateAtFixedRate(50);
		
		loading = 100;

	}
	
	@Override
	public void timeUpdate(long now) {
		
		mario.update(now);
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

		mario.handleEvent(event);

		return GUIEvent.NONE;
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		return GUIEvent.NONE;
	}

}
