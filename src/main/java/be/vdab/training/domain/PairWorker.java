package be.vdab.training.domain;

public class PairWorker<E1 extends Worker, E2 extends Worker> {
    private final E1 person1;
    private final E2 person2;

    public PairWorker(E1 person1, E2 person2) {
        if (person1.getClass() != person2.getClass()) {
            throw new IllegalArgumentException("invalid pair of workers");
        }
        this.person1 = person1;
        this.person2 = person2;
    }

    public void pairProgramming() {
        System.out.println(person1.getFirstName() + " works together with " + person2.getFirstName());
    }
}
