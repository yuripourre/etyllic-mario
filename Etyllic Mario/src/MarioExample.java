

import mario.stages.YoshiHouse;
import br.com.etyllica.Etyllica;

public class MarioExample extends Etyllica {

	private static final long serialVersionUID = 1L;

	public MarioExample() {
		super(255, 236);
	}
	
	@Override
	public void startGame() {
		
		setMainApplicacao(new YoshiHouse());
	}
	
}
