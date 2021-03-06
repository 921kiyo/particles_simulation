package simulation;

public class ParticleWallCollision extends Collision {

    private Wall w;

    // Constructor calls Collision, but must send particle as array
    public ParticleWallCollision(Particle p, Wall wall, double time) {
        super(time, packageParticle(p));
        
        w = wall;
    }

    private static Particle[] packageParticle(Particle part) {
        Particle[] particle = {part};
        return particle;
    }

    /**
     * Passes self to ParticleEventHandler, which knows
     * how to handle collisions individually
     */
    @Override
    public void happen(ParticleEventHandler h) {
        // collide() is a static method... no need to call on p directly
        Particle.collide(particles[0], w);
        h.reactTo(this);
        return;
    }

}
