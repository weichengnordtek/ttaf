package utkereso;

public class Main {

	public static void main(String[] args) {
		RoadNetwork network = new RoadNetwork();
		RoutePlanner routePlanner = new RoutePlanner();
		Controller controller = new Controller();
		
		controller.addModel(network);
		controller.addView(routePlanner);
		
		routePlanner.show();
		routePlanner.addController(controller);
		

	}

}
