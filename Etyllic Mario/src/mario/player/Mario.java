package mario.player;

import br.com.etyllica.animation.AnimationHandler;
import br.com.etyllica.animation.scripts.NeedleAnimation;


public class Mario extends Player {

	protected NeedleAnimation mushroomAnimation;
	
	public Mario(int x, int y) {
		super(x, y, "mario.png", "marioinv.png");
		
		mushroomAnimation = new NeedleAnimation(200);
		mushroomAnimation.setTarget(getLayer());
		mushroomAnimation.setNeedle(64, 0);
		mushroomAnimation.setRepeat(4);
	}
	
	public void grow() {
		
		AnimationHandler.getInstance().add(mushroomAnimation);
		grown = true;
		
	}

	public void setX(int x) {
		layer.setX(x);
	}

}
