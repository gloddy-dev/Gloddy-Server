package com.gloddy.server.search.school.infra.respository;

import com.gloddy.server.search.school.School;
import com.gloddy.server.search.school.infra.respository.custom.SchoolJpaRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolJpaRepository extends JpaRepository<School, Long>, SchoolJpaRepositoryCustom {
}
