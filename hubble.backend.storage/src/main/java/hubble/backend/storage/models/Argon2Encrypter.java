package hubble.backend.storage.models;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

/**
 *
 * @author Martín Straus <a href="mailto:martinstraus@gmail.com">martinstraus@gmail.com</a>
 */
public class Argon2Encrypter {

    private static final int ITERATIONS = 2;
    private static final int MEMORY = 65536;
    private static final int PARALLELISM = 1;

    private final Argon2 argon2;
    private final int iterations;
    private final int memory;
    private final int parallelism;

    public Argon2Encrypter() {
        this(Argon2Factory.create(), ITERATIONS, MEMORY, PARALLELISM);
    }

    public Argon2Encrypter(Argon2 argon2, int iterations, int memory, int parallelism) {
        this.argon2 = argon2;
        this.iterations = iterations;
        this.memory = memory;
        this.parallelism = parallelism;
    }

    public char[] encrypt(char[] texto) {
        return argon2.hash(iterations, memory, parallelism, texto).toCharArray();
    }

    public boolean verify(String hash, char[] texto) {
        return argon2.verify(hash, texto);
    }

}
