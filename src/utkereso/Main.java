package utkereso;

public class Main {

	public static void main(String[] args) {
		RoadNetwork network = new RoadNetwork();
		Utkereso utkereso = new Utkereso();
		Controller controller = new Controller();
		Model model = new Model();
		
		model.addObserver(utkereso);
		
		controller.addView(utkereso);
		controller.addModel(network);
		
		utkereso.show();
		utkereso.addController(controller);
		

	}

}
