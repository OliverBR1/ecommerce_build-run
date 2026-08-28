package tech.oliver.ecommerce_build.run.controller.dto;

import tech.oliver.ecommerce_build.run.entities.TagEntity;

public record TagResponseDto(Long tagId,
                             String name) {

    public static TagResponseDto fromEntity(TagEntity entity) {
        return new TagResponseDto(
                entity.getTagId(),
                entity.getName()
        );
    }
}
