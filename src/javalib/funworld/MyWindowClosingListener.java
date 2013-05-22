package javalib.funworld;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * The window closing listener - it stops the timer when the frame is closed
 * and stops playing all tunes - in the tickTunes bucket and in the 
 * keyTunes bucket.
 * 
 * @author Viera K. Proulx
 * @since 5 August 2010
 *
 */
class MyWindowClosingListener extends WindowAdapter{
	World w;
	MyWindowClosingListener(World w){
		this.w = w;
	}
	public void windowClosing(WindowEvent we){
		if (this.w != null && this.w.mytime != null)
			this.w.mytime.stopTimer();
		
		/**
		 if (tunes.MusicBox.SYNTH_READY){ 
		 // clear the tick tune bucket
		 this.w.tickTunes.clearBucket();
		 // clear the key tune bucket
		 this.w.keyTunes.clearBucket();
		 }
		 **/
	}
}
