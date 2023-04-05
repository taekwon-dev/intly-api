package com.youn.intly.tag.application;

import com.youn.intly.tag.domain.Tag;
import com.youn.intly.tag.domain.repository.TagRepository;
import com.youn.intly.tag.dto.TagsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public List<Tag> findOrCreateTags(final TagsDto tagsDto) {
        if (Objects.isNull(tagsDto)) {
            return emptyList();
        }
        List<String> tagNames = tagsDto.getTagNames();
        List<Tag> tags = new ArrayList<>();

        for (String tagName : tagNames) {
            Tag tag = new Tag(tagName);
            tagRepository.findByName(tag.getName())
                    .ifPresentOrElse(
                            tags::add,
                            () -> tags.add(tagRepository.save(tag))
                    );
        }
        return tags;
    }
}