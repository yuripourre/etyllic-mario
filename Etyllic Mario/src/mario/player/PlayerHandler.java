package mario.player;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import br.com.etyllica.linear.PointInt2D;
import br.com.tide.PlayerState;
import br.com.tide.platform.player.PlatformPlayerListener;
import br.com.vite.map.Map;
import br.com.vite.tile.Tile;
import br.com.vite.tile.collision.CollisionType;

public class PlayerHandler implements PlatformPlayerListener {

	private List<Player> players;

	private Player currentPlayer;

	private Map map;
	
	private PointInt2D playerPoint = new PointInt2D();
	
	private PointInt2D mapPoint = new PointInt2D();

	private Tile target;
	
	public PlayerHandler() {
		super();

		players = new ArrayList<Player>();
	}

	public void updatePlayers() {
		
		for(Player player: players) {
			currentPlayer = player;
			updatePlayer(player);
		}
	}

	private void updatePlayer(Player player) {
		//Center coordinates
		int marioX = player.getX()+player.getLayer().getTileW()/2+map.getOffsetX();
		int marioY = player.getY()-player.getLayer().getTileH()/2+map.getOffsetY();

		playerPoint.setLocation(marioX, marioY);
		map.updateTarget(marioX, marioY, mapPoint);

		target = map.getTiles()[mapPoint.getY()][mapPoint.getX()];

		if(target.getCollision() != CollisionType.FREE) {
			map.getFiller().setColor(Color.RED);
		} else {
			map.getFiller().setColor(Color.BLUE);
		}

		target = map.getTiles()[mapPoint.getY()][mapPoint.getX()];

		updatePlayerActions(player);
	}
	
	private void updatePlayerActions(Player player) {
		
		Tile downTile = downTile(mapPoint);
		
		if(player.isFalling()) {
			
			if(map.isPlatform(downTile)) {
				//Move mario to tile Up
				int positionY = map.getTileHeight()*(downTile.getY()/map.getTileHeight())+8;
				System.out.println("Mario moved to "+positionY);
				player.setY(positionY);
				
				//Stop falling animation
				player.stopJump();
			}
			
		} else if(!player.isJumping()) {
						
			if(!map.isPlatform(downTile)) {
				player.fall();
			}
		}
		
		Tile currentTile = currentTile(mapPoint);
		
		if(player.hasState(PlayerState.WALK_RIGHT)) {
							
			if(map.isBlock(currentTile)) {
				//Move to far from tile
				int currentTileX = currentTile(mapPoint).getX()-1;
				int positionX = map.getTileWidth()*(currentTileX/map.getTileWidth());
				System.out.println("moveX to "+positionX);
				player.setX(positionX);

				return;
			}
		}
		
		else if(player.hasState(PlayerState.WALK_LEFT)) {
			
			if(map.isBlock(currentTile)) {
				//Move to far from tile
				int currentTileX = currentTile(mapPoint).getX();
				int positionX = map.getTileWidth()*(1+(currentTileX/map.getTileWidth()))-1;
				System.out.println("moveX to "+positionX);
				player.setX(positionX);

				return;
			}
			
		}
	}
	
	private Tile currentTile(PointInt2D point) {
		return map.getTiles()[point.getY()][point.getX()];		
	}
	
	private Tile downTile(PointInt2D point) {
		return map.getTiles()[point.getY()+1][point.getX()];		
	}

	@Override
	public void onTurnLeft() {
		currentPlayer.turnLeft();
	}

	@Override
	public void onTurnRight() {
		currentPlayer.turnRight();
	}

	@Override
	public void onWalkLeft() {
		//If not colliding left
		currentPlayer.startWalking();
	}

	@Override
	public void onWalkRight() {
		//If not colliding right
		currentPlayer.startWalking();
	}

	@Override
	public void onLookUp() {
		currentPlayer.lookUpAction();
	}

	@Override
	public void onStandDown() {
		currentPlayer.standDownAction();
	}

	@Override
	public void onStopWalkLeft() {
		currentPlayer.stopWalk();
	}

	@Override
	public void onStopWalkRight() {
		currentPlayer.stopWalk();
	}

	@Override
	public void onStopLookUp() {
		currentPlayer.stopWalk();
	}

	@Override
	public void onStopStandDown() {
		currentPlayer.stopWalk();
	}

	@Override
	public void onJump() {
		currentPlayer.jumpAction();		
	}

	@Override
	public void onFall() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRun() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopJump() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopRun() {
		// TODO Auto-generated method stub

	}

	public void addPlayer(Player player) {
		players.add(player);
		currentPlayer = player;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	//Just for help debugging
	public PointInt2D getPlayerPoint() {
		return playerPoint;
	}

	public Tile getTarget() {
		return target;
	}
	
	

}
