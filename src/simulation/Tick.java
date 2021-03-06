package simulation;

public class Tick extends AbstractEvent {

    public Tick(double time) {
        super(time); // Calls AbstractEvent constructor
    }

    /**
     * Returns true if this Tick is (still) valid.
     */
    @Override
    public boolean isValid() {
        return true; // ticks are always valid!
    }

    /**
     * Passes self to ParticleEventHandler, which knows
     * how to handle ticks individually
     */
    @Override
    public void happen(ParticleEventHandler h) {
        h.reactTo(this);
        return;
    }
}
