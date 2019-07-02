package ru.ishbuldin.andrei.notes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface NoteRepository extends CrudRepository<Note, Integer> {

    List<Note> findAll();
    void deleteById(Integer id);

    @Query("SELECT n FROM Note n WHERE LOWER(n.title) LIKE LOWER(concat('%', ?1, '%')) OR LOWER(n.body) LIKE LOWER(concat('%', ?1, '%'))")
    List<Note> findLikeTitleAndBody(String matchPhrase);

}