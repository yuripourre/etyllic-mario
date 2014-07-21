package mario.item.fruit;

import br.com.etyllica.layer.AnimatedLayer;

public abstract class Fruit extends AnimatedLayer{

	public Fruit(int x, int y) {
		super(x,y, 16, 16, "fruits.png");
		this.frames = 3;
		this.speed = 500;
	}	
	
}
