

import mario.editor.MarioMapEditor;
import mario.stages.first.YoshiIsland2;
import br.com.etyllica.EtyllicaFrame;
import br.com.etyllica.context.Application;

public class MarioExample extends EtyllicaFrame {

	private static final long serialVersionUID = 1L;

	public MarioExample() {
		//super(255, 236);
		//super(800, 600);
		super(512, 500);
		
		//initJoysick = true;
	}
	
	public static void main(String[] args){
		MarioExample marioExample = new MarioExample();
		marioExample.init();
	}
	
	@Override
	public Application startApplication() {
		
		/*hideCursor();
		addLoader(MultimediaLoader.getInstance());
		return new YoshiHouse(w,h);*/
		
		return new YoshiIsland2(w,h);
		
		//return new MarioMapEditor(w, h);
	}
	
}
