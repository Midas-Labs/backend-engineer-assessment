package com.midas.app.repositories;

import com.midas.app.models.Account;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {

  /**
   * findAccountById finds an account by its id.
   *
   * @param id is the id of the account to be found.
   * @return Optional<Account>
   */
  Optional<Account> findAccountById(UUID id);
}
