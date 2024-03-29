package com.spring.redditclone.service;

import com.spring.redditclone.dto.SubredditDto;
import com.spring.redditclone.exceptions.RedditException;
import com.spring.redditclone.mapper.SubredditMapper;
import com.spring.redditclone.model.Subreddit;
import com.spring.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto){
        Subreddit save = subredditRepository.save(mapSubredditDto(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;

    }
    private Subreddit mapSubredditDto(SubredditDto subredditDto){
        return Subreddit.builder().name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .build();

    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private SubredditDto mapToDto(Subreddit subreddit){
        return SubredditDto.builder().name(subreddit.getName())
                .description(subreddit.getDescription())
                .build();
    }

    private SubredditDto getSubredditDto(Long id){
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new RedditException("No Subreddit found with ID: " + id));
        return subredditMapper.mySubredditDto(subreddit);
    }

}
