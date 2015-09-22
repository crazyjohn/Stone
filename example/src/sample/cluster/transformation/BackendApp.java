package sample.cluster.transformation;

public class BackendApp {

	public static void main(String[] args) {
		// starting 2 frontend nodes and 3 backend nodes
		BackendStart.start(new String[] { "2551" });
		BackendStart.start(new String[] { "2552" });
		BackendStart.start(new String[0]);
		// TransformationFrontendMain.main(new String[0]);
	}
}
