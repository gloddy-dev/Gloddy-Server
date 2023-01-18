package com.gloddy.server.article.service;

import com.gloddy.server.article.dto.ImageDto;
import com.gloddy.server.article.entity.Article;
import com.gloddy.server.article.entity.ArticleImage;
import com.gloddy.server.article.repository.ArticleImageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ArticleImageJpaRepository articleImageJpaRepository;

    @Transactional
    public void create(Article article, List<String> images) {
        if (!images.isEmpty()) {
            save(article, images);
        }
    }

    private void save(Article article, List<String> images) {
        articleImageJpaRepository.saveAll(images.stream()
                .map(path -> new ArticleImage(article, path))
                .collect(Collectors.toList())
        );
    }

    @Transactional(readOnly = true)
    public List<ImageDto> get(Article article) {
        List<ArticleImage> images = articleImageJpaRepository.findAllByArticle(article);
        if (images.isEmpty()) {
            return Collections.emptyList();
        }
        return generateDto(images);
    }

    private List<ImageDto> generateDto(List<ArticleImage> images) {
        return images.stream()
                .map(it -> new ImageDto(it.getUrl()))
                .collect(Collectors.toList());
    }

    public void delete(Article article) {
         articleImageJpaRepository.deleteAllByArticle(article);
    }
}
