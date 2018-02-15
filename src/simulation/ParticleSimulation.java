package simulation;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import utils.MinPriorityQueue;
import utils.List;
import java.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParticleSimulation implements Runnable, ParticleEventHandler {

    private static final long FRAME_INTERVAL_MILLIS = 40;

    private final ParticlesModel          model;
    private final ParticlesView           screen;

    private double                        clock;
    private MinPriorityQueue<Event>       queue;
    private List<Collision>       allCollisions;
    
    /**
     * Constructor.
     */
    public ParticleSimulation(String name, ParticlesModel m) {
        model = m;
        screen = new ParticlesView(name, model);
        
        clock = 1;
        Tick kickoff = new Tick(clock);
        
        queue = new MinPriorityQueue<Event>();
        
        // Predicting all collisions at kickoff
        allCollisions = (List<Collision>) model.predictAllCollisions(clock);
        allCollisions.forEach(c -> queue.add(c));
        
    }

    /**
     * Runs the simulation.
     */
    @Override
    public void run() {
        try {
            SwingUtilities.invokeAndWait(screen);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // TODO complete implementing this method
        while(!queue.isEmpty()) {
            Event currentEvent = queue.remove();
            if (currentEvent.isValid()) {
                clock = currentEvent.time(); // Update time to event's time
                model.moveParticles(clock); // Move particles for this much time
                currentEvent.happen(this); // Let the current event occur
            }
        }
    }
    
    public void reactTo(Tick tick) {
        try {
            Thread.sleep(FRAME_INTERVAL_MILLIS);
        } catch (InterruptedException ex) {
            Logger.getLogger(ParticleSimulation.class.getName()).log(Level.SEVERE, null, ex);
        }

        screen.update();
        queue.add(tick);
        
    }
    
    public void reactTo(Collision c) {
        // Get all particles involved in collision (1 or 2 only)
        Particle[] particlesInvolved = c.getParticles();
        
        Iterable<Collision> newCollisions;
        
        for (int i = 0; i < particlesInvolved.length; i++) {
            // Get all future collisions for the current particles
            newCollisions = model.predictCollisions(particlesInvolved[i], clock);
            // Add each of these collisions to the queue
            newCollisions.forEach(nC -> queue.add(nC));
        }
        
    }
            

}
