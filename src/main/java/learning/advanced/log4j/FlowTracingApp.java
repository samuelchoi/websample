package learning.advanced.log4j;

public class FlowTracingApp {
	
    public static void main( String[] args ) {
    	
        TestService service = new TestService();
        service.retrieveMessage();
        service.retrieveMessage();
        service.exampleException();
    }
}