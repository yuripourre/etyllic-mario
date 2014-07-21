

import mario.stages.first.YoshiHouse;
import sound.MultimediaLoader;
import br.com.etyllica.EtyllicaFrame;
import br.com.etyllica.context.Application;

public class MarioExample extends EtyllicaFrame {

	private static final long serialVersionUID = 1L;

	public MarioExample() {
		super(255, 236);
		//initSound = true;
		/*initJoysick = true;*/
	}
	
	public static void main(String[] args){
		MarioExample marioExample = new MarioExample();
		marioExample.init();
	}
	
	@Override
	public Application startApplication() {
		hideCursor();
		
		addLoader(MultimediaLoader.getInstance());
		
		return new YoshiHouse(w,h);
	}
	
}
