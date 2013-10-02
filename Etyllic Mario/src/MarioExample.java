

import mario.stages.YoshiHouse;
import br.com.etyllica.EtyllicaFrame;

public class MarioExample extends EtyllicaFrame {

	private static final long serialVersionUID = 1L;

	public MarioExample() {
		super(255, 236);
		initSound = true;
		/*initJoysick = true;*/
	}
	
	public static void main(String[] args){
		MarioExample marioExample = new MarioExample();
		marioExample.init();
		marioExample.setVisible(true);
	}
	
	@Override
	public void startGame() {
		hideCursor();
		setMainApplication(new YoshiHouse(w,h));
	}
	
}
