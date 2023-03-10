package stage;

import character.Warrior;
import monster.Monster;
import weapon.Bow;
import java.util.HashMap;
import java.util.List;

import character.Character;

public abstract class GameStage {

	private Character role;
	private int[][] coordinate;
	private HashMap<Integer, Room> map;
	private int cur_room;
	private int x, y;
	
	public GameStage() {
		role = new Warrior(200, 200, 100);
		role.take(new Bow());
		cur_room = 1;
		x = 10;
		y = 10;

	}
	
	public Character getCharacter() {
		return role;
	}
	
	public Room getRoom() {
		return map.get(cur_room);
	}
	
	public List<Monster> getMonsters() {
		return map.get(cur_room).getMonster();
	}
	
	public void setCoordinate(int[][] co) {
		coordinate = co;
	}
	
	public void setMatch(HashMap<Integer, Room> m) {
		map = m;
	}
	
	public int room(int n) {
		int[][] d = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
		return coordinate[y + d[n][0]][x + d[n][1]];
	}
	
	public boolean roomClean() {
		return map.get(cur_room).isClean();
	}
	
	public void nextRoom(int n) {
		cur_room = room(n);
		x = map.get(cur_room).getX();
		y = map.get(cur_room).getY();
	}
	
}
