package utkereso;

public class Main {

	public static void main(String[] args) {
		RoadNetwork network = new RoadNetwork();
		Router utkereso = new Router();
		Controller controller = new Controller();
		Model model = new Model();
		
		model.addObserver(utkereso);
		
		controller.addModel(network);
		controller.addView(utkereso);
		
		utkereso.show();
		utkereso.addController(controller);
		

	}

}
