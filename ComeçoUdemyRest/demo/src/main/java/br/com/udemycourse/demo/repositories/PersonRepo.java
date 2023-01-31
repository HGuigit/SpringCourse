package br.com.udemycourse.demo.repositories;


import br.com.udemycourse.demo.Models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {

}
