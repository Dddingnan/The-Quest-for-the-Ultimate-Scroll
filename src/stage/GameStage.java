package stage;

import character.Warrior;
import monster.Monster;
import weapon.Sword;

import java.util.List;

import character.Character;

public abstract class GameStage {

	private Character role;
	
	public GameStage() {
		role = new Warrior(200, 200, 100, 5);
		role.take(new Sword());

	}
	
	public Character getCharacter() {
		return role;
	}
	
	public abstract List<Monster> getMonsters();
}
