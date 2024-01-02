package com.steelroyal.robothumanoid.motion.mapping;

import com.steelroyal.robothumanoid.motion.resource.PageableAllRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageableMapper {
    public Pageable toModel(PageableAllRequest pageableAllRequest){
        return PageRequest.of(
                pageableAllRequest.getPage(),
                pageableAllRequest.getSize(),
                Sort.Direction.fromString(pageableAllRequest.getSortDirection()),
                pageableAllRequest.getSortBy()
        );
    }
}
