package example.loginapidemo.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import example.loginapidemo.R;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

//    ScheduledExecutorService executor = Executors.newScheduledThreadPool ( 1 );
//
//    Runnable r = new Runnable () {
//        @Override
//        public void run () {
//            try {  // Always wrap your Runnable with a try-catch as any uncaught Exception causes the ScheduledExecutorService to silently terminate.
//                System.out.println ( "Now: " + Instant.now () );  // Our task at hand in this example: Capturing the current moment in UTC.
//                if ( Boolean.FALSE ) {  // Add your Boolean test here to see if the external task is fonud to be completed, as described in this Question.
//                    executor.shutdown ();  // 'shutdown' politely asks ScheduledExecutorService to terminate after previously submitted tasks are executed.
//                }
//
//            } catch ( Exception e ) {
//                System.out.println ( "Oops, uncaught Exception surfaced at Runnable in ScheduledExecutorService." );
//            }
//        }
//    };
//
//    try {
//        executor.scheduleAtFixedRate ( r , 0L , 5L , TimeUnit.SECONDS ); // ( runnable , initialDelay , period , TimeUnit )
//        Thread.sleep ( TimeUnit.MINUTES.toMillis ( 1L ) ); // Let things run a minute to witness the background thread working.
//    } catch ( InterruptedException ex ) {
//        Logger.getLogger ( App.class.getName () ).log ( Level.SEVERE , null , ex );
//    } finally {
//        System.out.println ( "ScheduledExecutorService expiring. Politely asking ScheduledExecutorService to terminate after previously submitted tasks are executed." );
//        executor.shutdown ();
//    }
}