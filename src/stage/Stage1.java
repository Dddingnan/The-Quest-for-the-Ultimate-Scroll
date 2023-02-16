package stage;

import java.util.ArrayList;
import java.util.List;
import monster.Monkey;
import monster.Monster;

public class Stage1 extends GameStage {

	private List<Monster> ml;
	
	public Stage1() {
		ml = new ArrayList<>();
		ml.add(new Monkey(100, 100, 500, 2));
		ml.add(new Monkey(300,300, 500, 3));
		
	}
	

	public List<Monster> getMonsters() {
		return ml;
	}
	

}
