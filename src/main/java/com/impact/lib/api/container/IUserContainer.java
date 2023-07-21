package pl.impact.lib.api.container;

import java.util.Optional;
import java.util.UUID;

public interface IUserContainer<E, ID> {

    /**
     * @param uid
     * @return
     */
    Optional<E> get(ID uid);

    /**
     * @param mcUid
     * @return
     */
    Optional<E> get(UUID mcUid);

    /**
     * @param uid
     * @return
     */
    E load(ID uid);

    /**
     * @param user
     * @return
     */
    boolean remove(E user);

    /**
     * @param user
     * @return
     */
    boolean save(E user);

}
