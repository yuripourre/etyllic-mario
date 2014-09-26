package mario.stages.first;

import mario.item.fruit.RedFruit;
import sound.model.Music;
import br.com.etyllica.core.event.GUIEvent;
import br.com.etyllica.core.event.KeyEvent;
import br.com.etyllica.core.event.PointerEvent;
import br.com.etyllica.core.graphics.Graphic;
import br.com.etyllica.layer.ImageLayer;

public class YoshiHouse extends Stage {

	public YoshiHouse(int w, int h) {
		super(w, h);
	}
	
	private Music music;

	private ImageLayer background;
	
	private RedFruit[] fruits;

	@Override
	public void load() {
				
		loadingPhrase = "Loading Resources...";

		//By default, engine looks for image at /bin/res/images folder
		background = new ImageLayer("yoshihouse.png");
		loading = 20;
				
		fruits = new RedFruit[7];
		fruits[0] = new RedFruit(32, 60);
		fruits[1] = new RedFruit(48, 76);
		fruits[2] = new RedFruit(96, 60);
		fruits[3] = new RedFruit(78, 92);
		fruits[4] = new RedFruit(114, 76);
		fruits[5] = new RedFruit(176, 60);
		fruits[6] = new RedFruit(208, 76);

		loading = 80;

		music = new Music("Yoster Island.mp3");
		music.play();
				
		loading = 90;
		
		super.load();

	}
	
	@Override
	public void timeUpdate(long now) {
		super.timeUpdate(now);
		
		for(RedFruit fruit: fruits){
			fruit.animate(now);	
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
		
		if(event.isKeyDown(KeyEvent.TSK_1)) {
			mario.grow();
		}
		
		return GUIEvent.NONE;
	}

	@Override
	public GUIEvent updateMouse(PointerEvent event) {

		return GUIEvent.NONE;
	}

}
