package com.denyskozii.bulletinboard.repository;

import com.denyskozii.bulletinboard.model.Bulletin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
@Repository
public interface BulletinRepository extends JpaRepository<Bulletin, Long> {
//    @Query(value = "SELECT B.* FROM BOARD M " +
//            "JOIN BOARD_USER BU ON BU.ID_DOARD  = B.ID " +
//            "WHERE BU.ID_USER = :id", nativeQuery = true)
//    List<Board> findAllByUserId(Long id);

    Bulletin findByTitle(String title);

//    Page<Bulletin> getPage(Pageable pageable);
//
//    Page<Bulletin> getPages(PageRequest pageable);


}
