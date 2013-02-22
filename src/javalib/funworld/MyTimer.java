package javalib.funworld;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/**
 * The action listener for the timer events.
 * 
 * @author Viera K. Proulx
 * @since August 2, 2007
 */
class MyTimer{
	
	/** the current <code>{@link World World}</code> 
	 * that handles the timer events */
	protected World currentWorld;
	
	/** the <code>Timer</code> that generates the time events */
	protected Timer timer;
	
	public boolean running = true;
	
	/** the timer speed */
	protected int speed;
	
	/**
	 * Create the initial timer for the given 
	 * <code>{@link World World}</code> at the given <code>speed</code>.
	 * 
	 * @param currentWorld the given <code>{@link World World}</code>
	 * @param speed the given <code>speed</code>
	 */
	protected MyTimer(World currentWorld, double speed){
		this.currentWorld = currentWorld;
		this.timer = new Timer((new Double(speed * 1000)).intValue(), 
							   this.timerTasks);
		this.speed = (new Double(speed * 1000)).intValue();
	}
	
	/**
	 * The callback for the timer events
	 */
	protected ActionListener timerTasks = new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
	if (running)
	currentWorld.processTick();
}
};

/**
 * A helper method to convert the <code>speed</code> given as 
 * a delay time into milliseconds
 */
protected void setSpeed(){ 
this.timer.setDelay(this.speed);
}

protected void stopTimer(){
this.running = false;
}
}