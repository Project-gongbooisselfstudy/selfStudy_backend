package com.selfStudy_backend.service;

import com.selfStudy_backend.domain.Scrap;
import com.selfStudy_backend.repository.JDBCScrapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapServiceImple implements ScrapService{

    private final JDBCScrapRepository jdbcScrapRepository;

    public ScrapServiceImple(JDBCScrapRepository jdbcScrapRepository){
        this.jdbcScrapRepository = jdbcScrapRepository;
    }

    @Override
    public int saveScrap(Scrap scrap) {
        jdbcScrapRepository.saveScrap((scrap));
        return scrap.getQuestion_id();
    }

    @Override
    public List<Scrap> findById(String user_id) {
        return null;
    }
}
