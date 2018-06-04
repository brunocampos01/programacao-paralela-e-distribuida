package exemplo01;

public class Escritor implements Runnable{
    private Buffer sharedLocation;     

    public Escritor( Buffer shared ){
    	this.sharedLocation = shared;
    } 

	public void run(){
		for ( int z = 0; z <= 120; z++ ){  
			this.sharedLocation.escrever(z); 
		}
	} 
}