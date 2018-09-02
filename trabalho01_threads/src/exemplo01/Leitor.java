package exemplo01;

public class Leitor implements Runnable{ 
	private Buffer sharedLocation; 
    
    public Leitor( Buffer shared ){
    	this.sharedLocation = shared;
    } 

	public void run(){
		for ( int x = 0; x<= 120; x++ ){  
			this.sharedLocation.ler();
		}
	} 
}

